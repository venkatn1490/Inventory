package com.medrep.app.model;

import java.io.Serializable;
import java.util.Date;

import com.medrep.app.entity.NotificationEntity;

public class PharmaRepNotification implements Serializable{


	private Integer notificationId;
	private Integer pharmaRepId;
	private Integer therapeuticId;
	private Integer companyId;
	private String viewStatus;
	private String createdOn;
	private Boolean favourite;
	private String feedback;
	private String viewedOn;
	private Integer userNotificationId;



	public Integer getUserNotificationId() {
		return userNotificationId;
	}
	public void setUserNotificationId(Integer userNotificationId) {
		this.userNotificationId = userNotificationId;
	}

	public Integer getPharmaRepId() {
		return pharmaRepId;
	}
	public void setPharmaRepId(Integer pharmaRepId) {
		this.pharmaRepId = pharmaRepId;
	}

	public Integer getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(Integer therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getViewStatus() {
		return viewStatus;
	}
	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public Boolean getFavourite() {
		return favourite;
	}
	public void setFavourite(Boolean favourite) {
		this.favourite = favourite;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getViewedOn() {
		return viewedOn;
	}
	public void setViewedOn(String viewedOn) {
		this.viewedOn = viewedOn;
	}
	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}


}
