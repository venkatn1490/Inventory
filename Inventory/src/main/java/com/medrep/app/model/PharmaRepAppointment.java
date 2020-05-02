package com.medrep.app.model;

import java.util.Date;

public class PharmaRepAppointment {
	
	private Integer repAppointmentId;
	private String appointmentId;
	private String repId;
	private String doctorId;
	private String createdOn;
	private Date status;
	
	
	public Integer getRepAppointmentId() {
		return repAppointmentId;
	}
	public void setRepAppointmentId(Integer repAppointmentId) {
		this.repAppointmentId = repAppointmentId;
	}
	public String getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getRepId() {
		return repId;
	}
	public void setRepId(String repId) {
		this.repId = repId;
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public Date getStatus() {
		return status;
	}
	public void setStatus(Date status) {
		this.status = status;
	}

}
