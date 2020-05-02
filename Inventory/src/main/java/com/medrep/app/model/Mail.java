package com.medrep.app.model;

import java.util.Map;

public class Mail 
{
	
	private String mailTo;
	
	private String[] multipleMailTo;
	private String mailCc;
	private String mailBcc;
	private String templateName;
	private Map<String,String> valueMap;
	
	
	public String[] getMultipleMailTo() {
		return multipleMailTo;
	}
	public void setMultipleMailTo(String[] multipleMailTo) {
		this.multipleMailTo = multipleMailTo;
	}
	
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getMailCc() {
		return mailCc;
	}
	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}
	public String getMailBcc() {
		return mailBcc;
	}
	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}
	
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public Map<String, String> getValueMap() {
		return valueMap;
	}
	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}
	
	
	
}
