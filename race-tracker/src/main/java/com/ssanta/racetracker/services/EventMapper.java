package com.ssanta.racetracker.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssanta.racetracker.model.Event;

public class EventMapper implements IEventMapper {

	final Logger logger = LoggerFactory.getLogger(RaceChannel.class);

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Event toEvent(String inMessage) {
		try {
			return objectMapper.readValue(inMessage, Event.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}

}
