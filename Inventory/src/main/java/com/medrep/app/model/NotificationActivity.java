package com.medrep.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationActivity {
	
	private Integer notificationId;
	private String notificationName;
	private Integer companyId;
	private String companyName;
	private Integer therapeuticId;
	private String therapeuticName;
	private Map<String, Integer> activities = new HashMap<String, Integer>();
	private Integer totalScore;
	
	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	public String getNotificationName() {
		return notificationName;
	}
	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(Integer therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	public String getTherapeuticName() {
		return therapeuticName;
	}
	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}
	public Map<String, Integer> getActivities() {
		return activities;
	}
	public void setActivities(Map<String, Integer> activities) {
		this.activities = activities;
	}
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	
	
	
}
