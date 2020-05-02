package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.medrep.app.model.Doctor;

@Entity
@Table(name="T_PENDING_COUNTS")
public class PendingCountsEntity {

	Integer userId;
	Integer idPendingCount;
	Integer surveysCount;
	Integer notificationsCount;
	Integer doctorPlusCount;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="ID_PENDING_COUNT")
	public Integer getIdPendingCount() {
		return idPendingCount;
	}
	public void setIdPendingCount(Integer idPendingCount) {
		this.idPendingCount = idPendingCount;
	}


	@Column(name="USER_ID")
	public Integer getUserId() {
		return userId;
	}
	 public void setUserId(Integer idUser) {
		this.userId = idUser;
	}

	@Column(name="SURVEYS_COUNT")
	public Integer getSurveysCount() {
		return surveysCount;
	}
	public void setSurveysCount(Integer surveys) {
		this.surveysCount = surveys;
	}
	@Column(name="NOTIFICATIONS_COUNT")
	public Integer getNotificationsCount() {
		return notificationsCount;
	}
	public void setNotificationsCount(Integer notifications) {
		this.notificationsCount = notifications;
	}

	@Column(name="DOCTOR_PLUS_COUNT")
	public Integer getDoctorPlusCount() {
		return doctorPlusCount;
	}
	public void setDoctorPlusCount(Integer doctorPlus) {
		this.doctorPlusCount = doctorPlus;
	}
}