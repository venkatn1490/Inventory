package com.medrep.app.model;

import java.util.Map;

public class SMS {
	
	private String phoneNumber;
	private Map<String, String> valueMap;
	private String template;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Map<String, String> getValueMap() {
		return valueMap;
	}
	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}
	
	
	
}
