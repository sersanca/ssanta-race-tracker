package com.ssanta.racetracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {

	final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);
	public static final String SOURCE_URL = "ssanta.source.url";

	private static ApplicationProperties instance = null;
	private Properties props;

	private static ApplicationProperties getInstance() {
		if (instance == null) {
			instance = new ApplicationProperties();
			instance.init();

		}
		return instance;
	}

	private void init() {

		try {

			props = new Properties();
			
			InputStream stream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("application.properties");

			

			// load a properties file
			props.load(stream);
			
		

		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}

	private ApplicationProperties() {

	}

	public static Properties getProps() {
		return getInstance().props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
}
