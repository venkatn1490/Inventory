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
@Table(name = "P_DOCTOR_REGISTERED_CAMPAIGNS", uniqueConstraints = @UniqueConstraint(columnNames = "CAMPAIGN_ID") )
public class DoctorRegisteredCampaignsEntity implements java.io.Serializable{

	private Integer registeredDocCampaignId;
	private PharmaCampaginEntity pharmaCampaginEntity;
	private Integer doctorId;
	private String locatity;
	private String startTime;
	private String endTime;
	
	private Integer addressId;
	
	private Date registeredDate;
	private Float consultationFee;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REGISTERED_CAMPAIGN_ID", nullable = false)
	public Integer getRegisteredDocCampaignId() {
		return registeredDocCampaignId;
	}
	public void setRegisteredDocCampaignId(Integer registeredDocCampaignId) {
		this.registeredDocCampaignId = registeredDocCampaignId;
	}
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="CAMPAIGN_ID",nullable=false,unique=true)
	public PharmaCampaginEntity getPharmaCampaginEntity() {
		return pharmaCampaginEntity;
	}
	public void setPharmaCampaginEntity(PharmaCampaginEntity pharmaCampaginEntity) {
		this.pharmaCampaginEntity = pharmaCampaginEntity;
	}
	@Column(name = "DOCTOR_ID")

	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	@Column(name = "LOCATION")

	public String getLocatity() {
		return locatity;
	}
	public void setLocatity(String locatity) {
		this.locatity = locatity;
	}
	@Column(name = "START_TIME")

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(name = "END_TIME")

	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(name = "REGISTERED_DATE")

	public Date getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}
	@Column(name = "CONSULTATION_FEE")

	public Float getConsultationFee() {
		return consultationFee;
	}
	public void setConsultationFee(Float consultationFee) {
		this.consultationFee = consultationFee;
	}
	@Column(name = "ADDRESS_ID")
	
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	
}