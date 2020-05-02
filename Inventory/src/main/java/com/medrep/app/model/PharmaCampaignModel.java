package com.medrep.app.model;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

public class PharmaCampaignModel {
	

	private Integer campaignId ;
	private String campaignTitle;
	private String campaignDetails;
	private Integer companyId;
	
	private String companyName;
	private String therapeuticId;
	private String therapeuticName;
	private String campaignStartDate;
	private String campaignEndDate;
	private String campaignThumdNailUrl;
	private String campaignImageUrl;
	private String targetState;
	private String targetCity;
	private String targetArea;
	private String targetAreaZip;
	
	private String address;
	private String companyEmail;
	private String contactPersonName;
	private String contactPersonEmail;
	private String contactPersonPhone;
	private String price;
	private String requestSample;
	private String status;
	
	private String doctorStatus;
	private String patientStatus;
	private String[] publishTareaIds;
	 private String[] publishDocsIds;
	

	public String[] getPublishDocsIds() {
		return publishDocsIds;
	}
	public void setPublishDocsIds(String[] publishDocsIds) {
		this.publishDocsIds = publishDocsIds;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDoctorStatus() {
		return doctorStatus;
	}
	public void setDoctorStatus(String doctorStatus) {
		this.doctorStatus = doctorStatus;
	}
	public String getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus;
	}
	
	public String[] getPublishTareaIds() {
		return publishTareaIds;
	}
	public void setPublishTareaIds(String[] publishTareaIds) {
		this.publishTareaIds = publishTareaIds;
	}
	
	private MultipartFile campaignThumdNail;
	private MultipartFile campaignImage;
	
	private Date campaignStartDateFormat;
	private Date campaignEndDateFormat;
	
	
	public Date getCampaignStartDateFormat() {
		return campaignStartDateFormat;
	}
	public void setCampaignStartDateFormat(Date campaignStartDateFormat) {
		this.campaignStartDateFormat = campaignStartDateFormat;
	}
	public Date getCampaignEndDateFormat() {
		return campaignEndDateFormat;
	}
	public void setCampaignEndDateFormat(Date campaignEndDateFormat) {
		this.campaignEndDateFormat = campaignEndDateFormat;
	}
	public MultipartFile getCampaignThumdNail() {
		return campaignThumdNail;
	}
	public void setCampaignThumdNail(MultipartFile campaignThumdNail) {
		this.campaignThumdNail = campaignThumdNail;
	}
	public MultipartFile getCampaignImage() {
		return campaignImage;
	}
	public void setCampaignImage(MultipartFile campaignImage) {
		this.campaignImage = campaignImage;
	}
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	public String getCampaignTitle() {
		return campaignTitle;
	}
	public void setCampaignTitle(String campaignTitle) {
		this.campaignTitle = campaignTitle;
	}
	public String getCampaignDetails() {
		return campaignDetails;
	}
	public void setCampaignDetails(String campaignDetails) {
		this.campaignDetails = campaignDetails;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(String therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	public String getTherapeuticName() {
		return therapeuticName;
	}
	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}
	public String getCampaignStartDate() {
		return campaignStartDate;
	}
	public void setCampaignStartDate(String campaignStartDate) {
		this.campaignStartDate = campaignStartDate;
	}
	public String getCampaignEndDate() {
		return campaignEndDate;
	}
	public void setCampaignEndDate(String campaignEndDate) {
		this.campaignEndDate = campaignEndDate;
	}
	public String getCampaignThumdNailUrl() {
		return campaignThumdNailUrl;
	}
	public void setCampaignThumdNailUrl(String campaignThumdNailUrl) {
		this.campaignThumdNailUrl = campaignThumdNailUrl;
	}
	public String getCampaignImageUrl() {
		return campaignImageUrl;
	}
	public void setCampaignImageUrl(String campaignImageUrl) {
		this.campaignImageUrl = campaignImageUrl;
	}
	public String getTargetState() {
		return targetState;
	}
	public void setTargetState(String targetState) {
		this.targetState = targetState;
	}
	public String getTargetCity() {
		return targetCity;
	}
	public void setTargetCity(String targetCity) {
		this.targetCity = targetCity;
	}
	public String getTargetArea() {
		return targetArea;
	}
	public void setTargetArea(String targetArea) {
		this.targetArea = targetArea;
	}
	public String getTargetAreaZip() {
		return targetAreaZip;
	}
	public void setTargetAreaZip(String targetAreaZip) {
		this.targetAreaZip = targetAreaZip;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getContactPersonEmail() {
		return contactPersonEmail;
	}
	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}
	public String getContactPersonPhone() {
		return contactPersonPhone;
	}
	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRequestSample() {
		return requestSample;
	}
	public void setRequestSample(String requestSample) {
		this.requestSample = requestSample;
	}

}
