package com.medrep.app.model;

import java.util.Date;
import com.medrep.app.util.DateConvertor;

public class DoctorAppointment implements java.io.Serializable {

	private static final long serialVersionUID = 2542911871310426635L;
	private Integer appointmentId;
	private String title;
	private String appointmentDesc;
	private Integer doctorId;
	private String doctorName;
	private Integer pharmaRepId;
	private String pharmaRepName;
	private boolean forceAccept;
	private boolean rescheduled;

	private Integer notificationId;
	private String startDate;
	private Integer duration;
	private String feedback;
	private String status;
	private Date createdOn;
	private String location;

	private Integer companyId;
	private String companyname;

	private Integer therapeuticId;
	private String therapeuticName;
	private String remindMe;
	private Date reminderTime;

	public boolean isRescheduled() {
		return rescheduled;
	}
	public void setRescheduled(boolean rescheduled) {
		this.rescheduled = rescheduled;
	}

	public boolean isForceAccept() {
		return forceAccept;
	}
	public void setForceAccept(boolean forceAccept) {
		this.forceAccept = forceAccept;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
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

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getPharmaRepName() {
		return pharmaRepName;
	}

	public void setPharmaRepName(String pharmaRepName) {
		this.pharmaRepName = pharmaRepName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAppointmentDesc() {
		return appointmentDesc;
	}

	public void setAppointmentDesc(String appointmentDesc) {
		this.appointmentDesc = appointmentDesc;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public Integer getPharmaRepId() {
		return pharmaRepId;
	}

	public void setPharmaRepId(Integer pharmaRepId) {
		this.pharmaRepId = pharmaRepId;
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {

		this.startDate = startDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getRemindMe() {
		return remindMe;
	}

	public void setRemindMe(String remindMe) {
		this.remindMe = remindMe;
	}

	public Date getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(Date reminderTime) {
		this.reminderTime = reminderTime;
	}

}
