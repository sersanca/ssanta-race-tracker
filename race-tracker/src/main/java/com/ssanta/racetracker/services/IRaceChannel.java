package com.ssanta.racetracker.services;

public interface IRaceChannel {

	void subscribe();
	
	void stopSubscription();

	void addRaceEventListener(IRaceEvent inListener);
}
