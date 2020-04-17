package com.ssanta.racetracker.services;

import com.ssanta.racetracker.model.Event;

public interface IEventMapper {

	Event toEvent(String inMessage);
}
