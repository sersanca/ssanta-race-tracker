package com.ssanta.racetracker;

import com.ssanta.racetracker.repository.RaceRepository;
import com.ssanta.racetracker.services.ChannelSettings;
import com.ssanta.racetracker.services.EventMapper;
import com.ssanta.racetracker.services.IRaceChannel;
import com.ssanta.racetracker.services.RaceChannel;
import com.ssanta.racetracker.services.RaceTracker;

/**
 * Hello world!
 *
 */
public class RaceTrackerApp 
{
	
	public static void main( String[] args )
    {
        RaceRepository repo = new RaceRepository();
    
    	ChannelSettings settings = new ChannelSettings();
    	settings.setTargetUrl(ApplicationProperties.getProps().getProperty(ApplicationProperties.SOURCE_URL, "http://35.207.169.147/results"));
        
        IRaceChannel channel = new RaceChannel(settings, new EventMapper());
        
		new RaceTracker(repo, channel).start();
    }
}
