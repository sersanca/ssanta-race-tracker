package com.ssanta.racetracker.repository;

import org.hibernate.Session;

import com.ssanta.racetracker.model.Event;
import com.ssanta.racetracker.model.Horse;

public class RaceRepository extends BaseRepository {

	
	public void createEvent(Event inEvent) {

		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		if (session.get(Horse.class, inEvent.getHorse().getId()) == null) {
			session.save(inEvent.getHorse());
		}
		
		session.save(inEvent);

		session.getTransaction().commit();
		session.close();
		 

	}
	
	
	
}
