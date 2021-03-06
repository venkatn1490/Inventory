package com.medrep.app.entity;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * TDoctorNotification generated by hbm2java
 */
@Entity
@Table(name = "T_DOCTOR_NOTIFICATION", uniqueConstraints = { @UniqueConstraint(columnNames = "DOCTOR_ID"),
		@UniqueConstraint(columnNames = "NOTIFICATION_ID") })
public class DoctorNotificationEntity implements java.io.Serializable {

	private Integer userNotificationId;
	private NotificationEntity notificationEntity;
	private Integer doctorId;
	private Integer therapeuticId;
	private Integer companyId;
	private String viewStatus;
	private Date createdOn;
	private Boolean favourite;
	private String rating;
	private String prescribe;
	private String recomend;
	private Date viewedOn;
	private String remindMe;
	private Date reminderTime;

	private Integer viewCount;

	public DoctorNotificationEntity() {
	}

//	public DoctorNotificationEntity(Integer doctorId, Integer therapeuticId, Integer companyId,
//			String viewStatus, Date createdOn, String favourite, String rating, Date viewedOn) {
//
//		this.doctorId = doctorId;
//		this.therapeuticId = therapeuticId;
//		this.companyId = companyId;
//		this.viewStatus = viewStatus;
//		this.createdOn = createdOn;
//		this.favourite = favourite;
//		this.rating = rating;
//		this.viewedOn = viewedOn;
//	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_NOTIFICATION_ID", nullable = false)
	public Integer getUserNotificationId() {
		return this.userNotificationId;
	}

	public void setUserNotificationId(Integer userNotificationId) {
		this.userNotificationId = userNotificationId;
	}

	@Column(name = "DOCTOR_ID", unique = true)
	public Integer getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "THERAPEUTIC_ID")
	public Integer getTherapeuticId() {
		return this.therapeuticId;
	}

	public void setTherapeuticId(Integer therapeuticId) {
		this.therapeuticId = therapeuticId;
	}

	@Column(name = "COMPANY_ID")
	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Column(name = "VIEW_STATUS")
	public String getViewStatus() {
		return this.viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "FAVOURITE")
	public Boolean getFavourite() {
		return this.favourite;
	}

	public void setFavourite(Boolean favourite) {
		this.favourite = favourite;
	}


	@Column(name = "RATING")
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@Column(name = "PRESCRIBE")
	public String getPrescribe() {
		return prescribe;
	}

	public void setPrescribe(String prescribe) {
		this.prescribe = prescribe;
	}

	@Column(name = "RECOMEND")
	public String getRecomend() {
		return recomend;
	}


	public void setRecomend(String recomend) {
		this.recomend = recomend;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VIEWED_ON", length = 19)
	public Date getViewedOn() {
		return this.viewedOn;
	}

	public void setViewedOn(Date viewedOn) {
		this.viewedOn = viewedOn;
	}

	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="NOTIFICATION_ID",nullable=false,unique=true)
	public NotificationEntity getNotificationEntity() {
		return notificationEntity;
	}

	public void setNotificationEntity(NotificationEntity notificationEntity) {
		this.notificationEntity = notificationEntity;
	}

	@Column(name = "REMIND_ME")
	public String getRemindMe() {
		return remindMe;
	}

	public void setRemindMe(String remindMe) {
		this.remindMe = remindMe;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REMINDER_TIME")
	public Date getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(Date reminderTime) {
		this.reminderTime = reminderTime;
	}

	@Column(name="VIEW_COUNT")
	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}


}
