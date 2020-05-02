package com.medrep.app.model;

import java.util.Date;
import java.util.List;

import com.medrep.app.entity.PharmaCampaginEntity;

public class PharmaCampStats implements java.io.Serializable {

	private Integer campaignStatId;
	private String campaignID;
	
	private String threpeauticId;
	private Integer doctorId;
	private Integer patientId;
	
	private List<Integer> patientIds;
	private Date sentDate;
	private String status;
	
	private String ComapnyName;
	
	public String getComapnyName() {
		return ComapnyName;
	}
	public void setComapnyName(String comapnyName) {
		ComapnyName = comapnyName;
	}
	private String threpeauticName;
	public String getThrepeauticName() {
		return threpeauticName;
	}
	public void setThrepeauticName(String threpeauticName) {
		this.threpeauticName = threpeauticName;
	}
	private PharmaCampaginEntity pharmaCampaginEntity;

	public PharmaCampaginEntity getPharmaCampaginEntity() {
		return pharmaCampaginEntity;
	}
	public void setPharmaCampaginEntity(PharmaCampaginEntity pharmaCampaginEntity) {
		this.pharmaCampaginEntity = pharmaCampaginEntity;
	}
	public String getLocatity() {
		return locatity;
	}
	public void setLocatity(String locatity) {
		this.locatity = locatity;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	private String locatity;
	private String city;
	
	private String audienceType;
	
	public Integer getCampaignStatId() {
		return campaignStatId;
	}
	public void setCampaignStatId(Integer campaignStatId) {
		this.campaignStatId = campaignStatId;
	}
	public String getCampaignID() {
		return campaignID;
	}
	public void setCampaignID(String campaignID) {
		this.campaignID = campaignID;
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
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAudienceType() {
		return audienceType;
	}
	public void setAudienceType(String audienceType) {
		this.audienceType = audienceType;
	}
	public String getThrepeauticId() {
		return threpeauticId;
	}
	public void setThrepeauticId(String threpeauticId) {
		this.threpeauticId = threpeauticId;
	}
	public List<Integer> getPatientIds() {
		return patientIds;
	}
	public void setPatientIds(List<Integer> patientIds) {
		this.patientIds = patientIds;
	}
}
