package com.medrep.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MedRepProperty {
	
	private static MedRepProperty instance = new MedRepProperty();
	private Properties properties;
	
	private MedRepProperty()
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
	
	public static MedRepProperty getInstance()
	{
		return instance;
	}
	
	public String getProperties(String key)
	{
		return properties.getProperty(key);
	}

}
