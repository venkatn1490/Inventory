package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "T_DOCTOR_INVITATIONS", uniqueConstraints = { @UniqueConstraint(columnNames = "DOCTOR_ID"),
		@UniqueConstraint(columnNames = "INVITED_DOCTOR_ID") })
public class DoctorInvitationEntity implements java.io.Serializable {

	private Integer invitationId;
	private Integer doctorId;
	private Integer invitedDoctorId;
	private Date invitedOn;
	private String inviteStatus;
	private Date acceptedOn;

	public DoctorInvitationEntity(){

	}
	public DoctorInvitationEntity(Integer invitationId, Integer doctorId,
			Integer invitedDoctorId, Date invitedOn, String inviteStatus,
			Date acceptedOn) {
		super();
		this.invitationId = invitationId;
		this.doctorId = doctorId;
		this.invitedDoctorId = invitedDoctorId;
		this.invitedOn = invitedOn;
		this.inviteStatus = inviteStatus;
		this.acceptedOn = acceptedOn;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "INVITATION_ID", nullable = false)
	public Integer getInvitationId() {
		return invitationId;
	}
	public void setInvitationId(Integer invitationId) {
		this.invitationId = invitationId;
	}

	@Column(name = "DOCTOR_ID")
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "INVITED_DOCTOR_ID")
	public Integer getInvitedDoctorId() {
		return invitedDoctorId;
	}
	public void setInvitedDoctorId(Integer invitedDoctorId) {
		this.invitedDoctorId = invitedDoctorId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INVITED_ON", length = 19)
	public Date getInvitedOn() {
		return invitedOn;
	}
	public void setInvitedOn(Date invitedOn) {
		this.invitedOn = invitedOn;
	}

	@Column(name = "INVITE_STATUS")
	public String getInviteStatus() {
		return inviteStatus;
	}
	public void setInviteStatus(String inviteStatus) {
		this.inviteStatus = inviteStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACCEPTED_ON", length = 19)
	public Date getAcceptedOn() {
		return acceptedOn;
	}
	public void setAcceptedOn(Date acceptedOn) {
		this.acceptedOn = acceptedOn;
	}


	/*@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="INVITED_DOCTOR_ID",nullable=false,unique=true)
	public DoctorEntity getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}*/


	/*@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name = "DOCTOR_ID",insertable = false, updatable = false)
	private DoctorEntity doctor;*/

/*	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name = "INVITED_DOCTOR_ID",insertable = false, updatable = false)
	private DoctorEntity invitedDoctor;*/

	/*public DoctorEntity getInvitedDoctor() {
		return invitedDoctor;
	}
	public void setInvitedDoctor(DoctorEntity invitedDoctor) {
		this.invitedDoctor = invitedDoctor;
	}*/
	/*public DoctorEntity getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}*/




}
