package com.medrep.app.model;

import com.medrep.app.entity.PharmaCampaginEntity;

public class DoctorRegisteredCampaign {

	private Integer registeredDocCampaignId;
	private Integer campaignId;
	private Integer doctorId;
	private String locatity;
	private String startTime;
	private String endTime;
	private String registeredDate;
	private Float consultationFee;
	private String dpImageUrl;
	private Integer addressId;
	
	private Location location;

	
	public String getDpImageUrl() {
		return dpImageUrl;
	}
	public void setDpImageUrl(String dpImageUrl) {
		this.dpImageUrl = dpImageUrl;
	}
	public String getExperienceNo() {
		return experienceNo;
	}
	public void setExperienceNo(String experienceNo) {
		this.experienceNo = experienceNo;
	}
	private String experienceNo;
	private String doctorName;
	public String getTherapeuticName() {
		return therapeuticName;
	}
	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}
	private String therapeuticName;
	public Integer getNoofAppoinments() {
		return noofAppoinments;
	}
	public void setNoofAppoinments(Integer noofAppoinments) {
		this.noofAppoinments = noofAppoinments;
	}
	private Integer noofAppoinments;
	public PharmaCampaginEntity getPharmaCampaginEntity() {
		return pharmaCampaginEntity;
	}
	public void setPharmaCampaginEntity(PharmaCampaginEntity pharmaCampaginEntity) {
		this.pharmaCampaginEntity = pharmaCampaginEntity;
	}
	private PharmaCampaginEntity pharmaCampaginEntity;

	
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Integer getRegisteredDocCampaignId() {
		return registeredDocCampaignId;
	}
	public void setRegisteredDocCampaignId(Integer registeredDocCampaignId) {
		this.registeredDocCampaignId = registeredDocCampaignId;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public String getLocatity() {
		return locatity;
	}
	public void setLocatity(String locatity) {
		this.locatity = locatity;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Float getConsultationFee() {
		return consultationFee;
	}
	public void setConsultationFee(Float consultationFee) {
		this.consultationFee = consultationFee;
	}
	public String getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
}
