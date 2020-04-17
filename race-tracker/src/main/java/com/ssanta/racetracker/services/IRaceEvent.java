package com.ssanta.racetracker.services;

import com.ssanta.racetracker.model.Event;

public interface IRaceEvent {
  
	enum ESourceStatus{
		SourceNotAvailable,
		NoEvents,
		Stopped
		
	}
	void onNewEvent(Event inEvent);
	
	void onSourceStatus(ESourceStatus inStatus);
}
