package com.medrep.app.model;

import java.io.Serializable;
import java.util.Date;

import com.medrep.app.entity.NotificationEntity;

public class DoctorNotification implements Serializable{


	private Integer notificationId;
	private Integer doctorId;
	private Integer therapeuticId;
	private Integer companyId;
	private String viewStatus;
	private String createdOn;
	private Boolean favourite;
	private String rating;
	private String prescribe;
	private String recomend;

	private String viewedOn;
	private Integer userNotificationId;
	private String remindMe;
	private String doctorName;
	private Integer viewCount;
	private String notificationName;




	public String getRemindMe() {
		return remindMe;
	}
	public void setRemindMe(String remindMe) {
		this.remindMe = remindMe;
	}
	public Integer getUserNotificationId() {
		return userNotificationId;
	}
	public void setUserNotificationId(Integer userNotificationId) {
		this.userNotificationId = userNotificationId;
	}

	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
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


	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getPrescribe() {
		return prescribe;
	}
	public void setPrescribe(String prescribe) {
		this.prescribe = prescribe;
	}
	public String getRecomend() {
		return recomend;
	}
	public void setRecomend(String recomend) {
		this.recomend = recomend;
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
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public String getNotificationName() {
		return notificationName;
	}
	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}


}
