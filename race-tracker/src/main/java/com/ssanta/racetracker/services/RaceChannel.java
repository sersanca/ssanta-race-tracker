package com.ssanta.racetracker.services;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssanta.racetracker.model.Event;
import com.ssanta.racetracker.services.IRaceEvent.ESourceStatus;

public class RaceChannel implements IRaceChannel {

	final Logger logger = LoggerFactory.getLogger(RaceChannel.class);

	List<IRaceEvent> listeners = new CopyOnWriteArrayList<IRaceEvent>();

	private final ChannelSettings settings;
	private final IEventMapper mapper;

	public RaceChannel(ChannelSettings inSettings, IEventMapper inMapper) {
		settings = inSettings;
		mapper = inMapper;
	}
	
	@Override
	public void subscribe() {

		try {
			connect();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private void processSuccess(String inBody) {

		logger.info("responseReceived: " + inBody);
		Event event = mapper.toEvent(inBody);
		if (event != null) {
			fireNewMessage(event);
		}

	}

	public void connect() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

			HttpGet getRequest = new HttpGet(settings.getTargetUrl());

			// Set the API media type in http accept header
			getRequest.addHeader("accept", "application/json");

	
			HttpResponse response = httpclient.execute(getRequest);

			// verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();

			switch (statusCode) {
			case 200:
			
				HttpEntity httpEntity = response.getEntity();
				processSuccess(EntityUtils.toString(httpEntity));
				break;
				
			case 204:
				fireSourceStatus(ESourceStatus.NoEvents);
				break;
			default:
				logger.info("Unexpected response code" + statusCode);
				fireSourceStatus(ESourceStatus.SourceNotAvailable);
			}

		} finally {
			httpclient.close();
		}
	}



	private void fireSourceStatus(ESourceStatus inStatus) {

		listeners.forEach(l -> l.onSourceStatus(inStatus));
	}

	private void fireNewMessage(Event inEvent) {

		listeners.forEach(l -> l.onNewEvent(inEvent));
	}

	@Override
	public void addRaceEventListener(IRaceEvent inListener) {
		listeners.add(inListener);

	}

	@Override
	public void stopSubscription() {
		fireSourceStatus(ESourceStatus.Stopped);
		
	}

}
