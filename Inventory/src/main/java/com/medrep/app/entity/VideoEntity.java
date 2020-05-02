package com.medrep.app.entity;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
	@Entity
	@Table(name = "M_VIDEO" )
	public class VideoEntity implements java.io.Serializable {

		private Integer videoId;
		private Integer doctorId;
		

		private Integer patientId;
		private Integer appointmentId;
		private Integer apiKey;
		private String sessionId;
		private String patToken;
		private String docToken;
		private Date createdDate;
	    

		private String patStatus;
	    private String docStatus;
	    
	    private String imgPrescriptionUrl1;
		private String imgPrescriptionUrl2;

		@Id
		@GeneratedValue(strategy = IDENTITY)
		@Column(name = "VIDEO_ID", nullable = false)
		public Integer getVideoId() {
			return videoId;
		}

		public void setVideoId(Integer videoId) {
			this.videoId = videoId;
		}
		@Column(name = "DOCTOR_ID")

		public Integer getDoctorId() {
			return doctorId;
		}

		public void setDoctorId(Integer doctorId) {
			this.doctorId = doctorId;
		}
		@Column(name = "PATIENT_ID")
		public Integer getPatientId() {
			return patientId;
		}

		public void setPatientId(Integer patientId) {
			this.patientId = patientId;
		}
		
		@Column(name = "APPOINTMENT_ID")
	
		public Integer getAppointmentId() {
			return appointmentId;
		}

		public void setAppointmentId(Integer appointmentId) {
			this.appointmentId = appointmentId;
		}
		@Column(name = "APIKEY")

		public Integer getApiKey() {
			return apiKey;
		}

		public void setApiKey(Integer apiKey) {
			this.apiKey = apiKey;
		}
		@Column(name = "SESSION_ID")

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}
		@Column(name = "PAT_TOKEN")

		public String getPatToken() {
			return patToken;
		}

		public void setPatToken(String patToken) {
			this.patToken = patToken;
		}
		@Column(name = "DOC_TOKEN")

		public String getDocToken() {
			return docToken;
		}

		public void setDocToken(String docToken) {
			this.docToken = docToken;
		}
		@Column(name = "CREATED_DATE")

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		@Column(name = "PATSTATUS")

		public String getPatStatus() {
			return patStatus;
		}

		public void setPatStatus(String patStatus) {
			this.patStatus = patStatus;
		}
		@Column(name = "DOCSTATUS")

		public String getDocStatus() {
			return docStatus;
		}

		public void setDocStatus(String docStatus) {
			this.docStatus = docStatus;
		}
		
	}
