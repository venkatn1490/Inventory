package com.medrep.app.model;

import java.util.Date;

public class DoctorInvitation implements java.io.Serializable {
	
	private static final long serialVersionUID = 2542911871310426635L;
	private Integer invitationId;
	private Integer doctorId;
	private String invitedOn;
	private String inviteStatus;
	private String acceptedOn;
	private String doctorName;
	private Integer therapeuticId;
	private String therapeuticName;
	
	public Integer getInvitationId() {
		return invitationId;
	}
	public void setInvitationId(Integer invitationId) {
		this.invitationId = invitationId;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	
	public String getInviteStatus() {
		return inviteStatus;
	}
	public void setInviteStatus(String inviteStatus) {
		this.inviteStatus = inviteStatus;
	}
	
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
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
	public String getInvitedOn() {
		return invitedOn;
	}
	public void setInvitedOn(String invitedOn) {
		this.invitedOn = invitedOn;
	}
	public String getAcceptedOn() {
		return acceptedOn;
	}
	public void setAcceptedOn(String acceptedOn) {
		this.acceptedOn = acceptedOn;
	}
	
	
	

}
