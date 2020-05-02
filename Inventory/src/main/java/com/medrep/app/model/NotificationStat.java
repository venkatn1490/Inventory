package com.medrep.app.model;

import java.util.List;

public class NotificationStat {

	private Integer notificationId;
	private String notificationName;
	private Integer totalSentNotification;
	private Integer totalPendingNotifcation;
	private Integer totalViewedNotifcation;
	private Integer totalConvertedToAppointment;
	private List notificationsSent;
	private List<Doctor> appointments;
	private List<Doctor> pending;
	private List<Doctor> viewed;
	private Integer totalremainders;
	private Integer totalfavorites;



	public List getNotificationsSent() {
		return notificationsSent;
	}
	public void setNotificationsSent(List notificationsSent) {
		this.notificationsSent = notificationsSent;
	}


	public List<Doctor> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<Doctor> appointments) {
		this.appointments = appointments;
	}
	public List<Doctor> getPending() {
		return pending;
	}
	public void setPending(List<Doctor> pending) {
		this.pending = pending;
	}
	public List<Doctor> getViewed() {
		return viewed;
	}
	public void setViewed(List<Doctor> viewed) {
		this.viewed = viewed;
	}
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
	public Integer getTotalSentNotification() {
		return totalSentNotification;
	}
	public void setTotalSentNotification(Integer totalSentNotification) {
		this.totalSentNotification = totalSentNotification;
	}
	public Integer getTotalPendingNotifcation() {
		return totalPendingNotifcation;
	}
	public void setTotalPendingNotifcation(Integer totalPendingNotifcation) {
		this.totalPendingNotifcation = totalPendingNotifcation;
	}
	public Integer getTotalViewedNotifcation() {
		return totalViewedNotifcation;
	}
	public void setTotalViewedNotifcation(Integer totalViewedNotifcation) {
		this.totalViewedNotifcation = totalViewedNotifcation;
	}
	public Integer getTotalConvertedToAppointment() {
		return totalConvertedToAppointment;
	}
	public void setTotalConvertedToAppointment(Integer totalConvertedToAppointment) {
		this.totalConvertedToAppointment = totalConvertedToAppointment;
	}
	public Integer getTotalremainders() {
		return totalremainders;
	}
	public void setTotalremainders(Integer totalremainders) {
		this.totalremainders = totalremainders;
	}
	public Integer getTotalfavorites() {
		return totalfavorites;
	}
	public void setTotalfavorites(Integer totalfavorites) {
		this.totalfavorites = totalfavorites;
	}
	
	
	
}
