package com.medrep.app.model;

import java.io.Serializable;
import java.util.Date;

import org.json.simple.JSONArray;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class VideoModel  implements Serializable {

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
   
	public Integer getVideoId() {
		return videoId;
	}
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public Integer getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Integer getApiKey() {
		return apiKey;
	}
	public void setApiKey(Integer apiKey) {
		this.apiKey = apiKey;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getPatToken() {
		return patToken;
	}
	public void setPatToken(String patToken) {
		this.patToken = patToken;
	}
	public String getDocToken() {
		return docToken;
	}
	public void setDocToken(String docToken) {
		this.docToken = docToken;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPatStatus() {
		return patStatus;
	}

	public void setPatStatus(String patStatus) {
		this.patStatus = patStatus;
	}
	public String getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}
	 
	
}
