package com.medrep.app.model;

import java.util.Date;

import com.medrep.app.entity.PharmaCampaginEntity;

public class PatientRegisteredCampaign {

	private Integer registeredPatCampaignId;
	private String therapeuticName;
	private PharmaCampaginEntity pharmaCampaginEntity;
	private Integer doctorId;
	private Integer therapeuticId;
	private Integer patientId;
	private String patientName;
	private String locatity;
	private String patientContactNumber;
	private String registeredDate;
	private String registeredTimefrom;
	private String registeredTimeto;
	private String status;
	private String dpImageUrl;
	
	private Integer campaignId;
	private String doctorName;
	
	public String getTherapeuticName() {
		return therapeuticName;
	}
	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}
	public String getDpImageUrl() {
		return dpImageUrl;
	}
	public void setDpImageUrl(String dpImageUrl) {
		this.dpImageUrl = dpImageUrl;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	

	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
	
	public Integer getRegisteredPatCampaignId() {
		return registeredPatCampaignId;
	}
	public void setRegisteredPatCampaignId(Integer registeredPatCampaignId) {
		this.registeredPatCampaignId = registeredPatCampaignId;
	}
	public PharmaCampaginEntity getPharmaCampaginEntity() {
		return pharmaCampaginEntity;
	}
	public void setPharmaCampaginEntity(PharmaCampaginEntity pharmaCampaginEntity) {
		this.pharmaCampaginEntity = pharmaCampaginEntity;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public Integer getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(Integer therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getLocatity() {
		return locatity;
	}
	public void setLocatity(String locatity) {
		this.locatity = locatity;
	}
	public String getPatientContactNumber() {
		return patientContactNumber;
	}
	public void setPatientContactNumber(String patientContactNumber) {
		this.patientContactNumber = patientContactNumber;
	}
	public String getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	public String getRegisteredTimefrom() {
		return registeredTimefrom;
	}
	public void setRegisteredTimefrom(String registeredTimefrom) {
		this.registeredTimefrom = registeredTimefrom;
	}
	public String getRegisteredTimeto() {
		return registeredTimeto;
	}
	public void setRegisteredTimeto(String registeredTimeto) {
		this.registeredTimeto = registeredTimeto;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
