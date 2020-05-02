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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "P_PATIENT_DOC_APPOINTMENT", uniqueConstraints = { @UniqueConstraint(columnNames = "PATIENT_ID"),
		
		@UniqueConstraint(columnNames = "DOCTOR_ID") })
public class PatientDocAppoiEntity {

	private Integer appointmentId;
	
	private PatientEntity patientEntity;
	private DoctorEntity doctorEntity;
	
	private Integer consultingId;
	private Date appointmentDate;
	private Date appointmentTime;
	private String confirmationNo;
	private String communicationMode;
	
	
	private String consultingType;
	private String contactDetails;
	private String appointmentStatus;
	private String followUpFlag;
	private String shareProfile;
		private String remindTime;
		
		private Integer videoId;
		
		private Integer chatId;
	public PatientDocAppoiEntity(){

	}
	
	
	private String imgPrescriptionUrl1;
	private String imgPrescriptionUrl2;

	
	@Column(name = "VIDEO_ID")

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	
	@Column(name = "CONSULTING_TYPE")
	public String getConsultingType() {
		return consultingType;
	}
	public void setConsultingType(String consultingType) {
		this.consultingType = consultingType;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "APPOINTMENT_ID", nullable = false)
	public Integer getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="PATIENT_ID",nullable=false,unique=true)
	public PatientEntity getPatientEntity() {
		return patientEntity;
	}

	public void setPatientEntity(PatientEntity patientEntity) {
		this.patientEntity = patientEntity;
	}

	
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="DOCTOR_ID",nullable=false,unique=true)
	public DoctorEntity getDoctorEntity() {
		return doctorEntity;
	}

	public void setDoctorEntity(DoctorEntity doctorEntity) {
		this.doctorEntity = doctorEntity;
	}
	
	
	@Column(name = "CONSULTING_ID")
	public Integer getConsultingId() {
		return consultingId;
	}
	public void setConsultingId(Integer consultingId) {
		this.consultingId = consultingId;
	}
	
	@Column(name = "APPOINTMENT_DATE")
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	@Column(name = "APPOINTMENT_TIME")
	public Date getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	@Column(name = "CONFIRMATION_NO")
	public String getConfirmationNo() {
		return confirmationNo;
	}
	public void setConfirmationNo(String confirmationNo) {
		this.confirmationNo = confirmationNo;
	}
	@Column(name = "COMMUNICATION_MODE")
	public String getCommunicationMode() {
		return communicationMode;
	}
	public void setCommunicationMode(String communicationMode) {
		this.communicationMode = communicationMode;
	}
	@Column(name = "CONTACT_DETAILS")
	public String getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}
	@Column(name = "APPOINTMENT_STATUS")
	public String getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	@Column(name = "FOLLOW_UP_FLAG")
	public String getFollowUpFlag() {
		return followUpFlag;
	}
	public void setFollowUpFlag(String followUpFlag) {
		this.followUpFlag = followUpFlag;
	}
	@Column(name = "SHARE_PROFILE")
	public String getShareProfile() {
		return shareProfile;
	}
	public void setShareProfile(String shareProfile) {
		this.shareProfile = shareProfile;
	}
	@Column(name = "REMINDER_TIME")
	public String getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
	@Column(name = "CHAT_ID")
	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}
	
	@Column(name = "V_IMG_PRE_1")
	public String getImgPrescriptionUrl1() {
		return imgPrescriptionUrl1;
	}

	public void setImgPrescriptionUrl1(String imgPrescriptionUrl1) {
		this.imgPrescriptionUrl1 = imgPrescriptionUrl1;
	}
	@Column(name = "V_IMG_PRE_2")

	public String getImgPrescriptionUrl2() {
		return imgPrescriptionUrl2;
	}

	public void setImgPrescriptionUrl2(String imgPrescriptionUrl2) {
		this.imgPrescriptionUrl2 = imgPrescriptionUrl2;
	}
}
