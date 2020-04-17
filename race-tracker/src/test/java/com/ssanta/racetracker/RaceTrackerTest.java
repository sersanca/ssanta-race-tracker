package com.ssanta.racetracker;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.ssanta.racetracker.model.Event;
import com.ssanta.racetracker.model.Horse;
import com.ssanta.racetracker.repository.RaceRepository;
import com.ssanta.racetracker.services.IEventMapper;
import com.ssanta.racetracker.services.IRaceChannel;
import com.ssanta.racetracker.services.IRaceEvent.ESourceStatus;
import com.ssanta.racetracker.services.RaceTracker;


//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;

public class RaceTrackerTest {
	
	@Mock
	IEventMapper eventMapper;
	@Mock
	IRaceChannel channel;
	 
	RaceRepository repo;
	
	RaceTracker raceTracker;
	
	
	@Before
	public void setUp() {
		 MockitoAnnotations.initMocks(this);

		repo = new RaceRepository();
		raceTracker = new RaceTracker(repo, channel);
	}
	/**
	 * Test when receiving a new event from the channel is saved persited on db.
	 * 
	 * Integration test.
	 * Testing the RaceTracker and RaceRepositiory component.
	 */
	@Test
	public void testEventReceivedIsSaved()  {

	 
		final Event e = generateEvent(5);
		
		//Will simulate to trigger one event
		Mockito.doAnswer( new Answer<Object>()
		{
			@Override
			public Object answer( InvocationOnMock invocation ) throws Throwable
			{
				
			
				raceTracker.onSourceStatus(ESourceStatus.Stopped);
				
				raceTracker.onNewEvent(e);
				
				return null;
			}
		} ).when( channel ).subscribe( );
		
		raceTracker.start();
		
		//Test the generated event has been saved in db
		assertNotNull(e.getId());

		 
	}
	
	

	@Test
	public void testPersistence()  {
		Event event1 = generateEvent(1);
		Event event2 = generateEvent(2);
		Event event3 = generateEvent(3);
		
		repo.createEvent(event1);
		repo.createEvent(event2);
		repo.createEvent(event3);
		
		assertNotNull(event1.getId());
		assertNotNull(event2.getId());
		assertNotNull(event3.getId());
	}
	
	@Test
	public void testEventsOnSameHorse()  {
		Event event1 = generateEvent(100);
		Event event2 = generateEvent(100);

		
		repo.createEvent(event1);
		repo.createEvent(event2);
		
		assertNotNull(event1.getId());
		assertNotNull(event2.getId());
	}
	
	private Event generateEvent(int horseId) {
		Event e = new Event();
		e.setStatus("Started");
		Horse horse = new Horse();
		horse.setId(horseId);
		horse.setName("matador-"+horseId);
		e.setHorse(horse);
		
		return e;
	}
	

}
