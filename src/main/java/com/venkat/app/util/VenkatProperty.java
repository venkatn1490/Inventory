package com.venkat.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VenkatProperty {
	
	private static VenkatProperty instance = new VenkatProperty();
	private Properties properties;
	
	private VenkatProperty()
	{
		InputStream is = this.getClass().getResourceAsStream("Configuration.properties");
		properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static VenkatProperty getInstance()
	{
		return instance;
	}
	
	public String getProperties(String key)
	{
		return properties.getProperty(key);
	}

}
