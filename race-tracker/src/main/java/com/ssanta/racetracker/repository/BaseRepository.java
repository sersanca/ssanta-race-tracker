package com.ssanta.racetracker.repository;

import javax.persistence.PersistenceException;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BaseRepository {

	final StandardServiceRegistry registry;
	SessionFactory sessionFactory;

	public BaseRepository() throws PersistenceException {
		registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();

		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {

			StandardServiceRegistryBuilder.destroy(registry);
			throw new  PersistenceException(ex);
		}

	}
	
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
