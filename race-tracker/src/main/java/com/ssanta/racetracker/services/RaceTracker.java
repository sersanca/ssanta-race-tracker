package com.ssanta.racetracker.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssanta.racetracker.model.Event;
import com.ssanta.racetracker.repository.RaceRepository;

public class RaceTracker implements IRaceEvent {

	private final Logger logger = LoggerFactory.getLogger(RaceTracker.class);
	private final RaceRepository raceRepository;
	private final IRaceChannel channel;
	private boolean started = false;
	
	public RaceTracker(RaceRepository inRepositiory, IRaceChannel inChannel) {
		raceRepository = inRepositiory;
		channel = inChannel;
	}
	
	public void start() {
		logger.info("Starting tracking...");

		channel.addRaceEventListener(this);
		started = true;
		channel.subscribe();

	}
	
	public void stop() {
		channel.stopSubscription();
	}
	@Override
	public void onNewEvent(Event inEvent) {
		
		raceRepository.createEvent(inEvent);
		if (started)
			channel.subscribe();
		
	}
	@Override
	public void onSourceStatus(ESourceStatus inStatus) {
		switch (inStatus) {
		case NoEvents:
			logger.info("no events at the moment, subscribing again");
			channel.subscribe();
			break;
		case SourceNotAvailable:
			logger.error("Source no available. Stopping subscribtion");
			break;
			
		case Stopped:
			started = false;
			logger.info("Stopping subscribtion");
			break;
			
		}
		
		
	}

	 
	
}
