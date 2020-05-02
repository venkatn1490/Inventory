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
@Table(name = "P_PATIENT_REGISTERED_CAMPAIGN", uniqueConstraints = 
				@UniqueConstraint(columnNames = "CAMPAIGN_ID")  )
public class PatientRegisteredCampaignEntity implements java.io.Serializable{

	
	private Integer registeredPatCampaignId;
	private PharmaCampaginEntity pharmaCampaginEntity;

	private Integer doctorId;
	private Integer therapeuticId;
	private Integer patientId;
	private String patientName;
	private String locatity;
	private String patientContactNumber;
	private Date registeredDate;
	private String registeredTimefrom;
	private String registeredTimeto;
	private String status;
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REGISTERED_CAMPAIGN_ID", nullable = false)
	public Integer getRegisteredPatCampaignId() {
		return registeredPatCampaignId;
	}
	public void setRegisteredPatCampaignId(Integer registeredPatCampaignId) {
		this.registeredPatCampaignId = registeredPatCampaignId;
	}
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="CAMPAIGN_ID",nullable=false,unique=true)
	public PharmaCampaginEntity getPharmaCampaginEntity() {
		return pharmaCampaginEntity;
	}
	public void setPharmaCampaginEntity(PharmaCampaginEntity pharmaCampaginEntity) {
		this.pharmaCampaginEntity = pharmaCampaginEntity;
	}
	@Column(name = "DOC_ID")
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	@Column(name = "THERAPEUTIC_ID")
	public Integer getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(Integer therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	@Column(name = "PATIENT_ID")

	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	@Column(name = "PATIENT_NAME")

	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	@Column(name = "LOCATION")

	public String getLocatity() {
		return locatity;
	}
	public void setLocatity(String locatity) {
		this.locatity = locatity;
	}
	@Column(name = "PATIENT_CONTACT_NUMBER")

	public String getPatientContactNumber() {
		return patientContactNumber;
	}
	public void setPatientContactNumber(String patientContactNumber) {
		this.patientContactNumber = patientContactNumber;
	}
	@Column(name = "REGISTERED_DATE")

	public Date getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}
	@Column(name = "REGISTERED_TIME_FROM")

	public String getRegisteredTimefrom() {
		return registeredTimefrom;
	}
	public void setRegisteredTimefrom(String registeredTimefrom) {
		this.registeredTimefrom = registeredTimefrom;
	}
	@Column(name = "REGISTERED_TIME_TO")

	public String getRegisteredTimeto() {
		return registeredTimeto;
	}
	public void setRegisteredTimeto(String registeredTimeto) {
		this.registeredTimeto = registeredTimeto;
	}
	@Column(name = "STATUS")

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}