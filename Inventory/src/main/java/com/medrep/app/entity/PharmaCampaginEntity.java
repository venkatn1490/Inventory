package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "P_CAMPAIGN" )
public class PharmaCampaginEntity implements java.io.Serializable {

	
	private Integer campaignId ;
	private String campaignTitle;
	private String campaignDetails;
	private Integer companyId;
	private String therapeuticId;
	private Date campaignStartDate;
	private Date campaignEndDate;
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
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CAMPAIGN_ID", nullable = false)
	public Integer getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	@Column(name = "CAMPAIGN_TITLE")

	public String getCampaignTitle() {
		return campaignTitle;
	}
	public void setCampaignTitle(String campaignTitle) {
		this.campaignTitle = campaignTitle;
	}
	@Column(name = "CAMPAIGN_DETAILS")

	public String getCampaignDetails() {
		return campaignDetails;
	}
	public void setCampaignDetails(String campaignDetails) {
		this.campaignDetails = campaignDetails;
	}
	@Column(name = "COMPANY_ID")
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	      
	@Column(name = "THERAPEUTIC_ID")

	public String getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(String therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	@Column(name = "CAMPAIGN_START_DATE")

	public Date getCampaignStartDate() {
		return campaignStartDate;
	}
	public void setCampaignStartDate(Date campaignStartDate) {
		this.campaignStartDate = campaignStartDate;
	}
	@Column(name = "CAMPAIGN_END_DATE")

	public Date getCampaignEndDate() {
		return campaignEndDate;
	}
	public void setCampaignEndDate(Date campaignEndDate) {
		this.campaignEndDate = campaignEndDate;
	}
	@Column(name = "CAMPAIGN_THUMB_NAIL_URL")

	public String getCampaignThumdNailUrl() {
		return campaignThumdNailUrl;
	}
	public void setCampaignThumdNailUrl(String campaignThumdNailUrl) {
		this.campaignThumdNailUrl = campaignThumdNailUrl;
	}
	@Column(name = "CAMPAIGN_IMAGE_URL")

	public String getCampaignImageUrl() {
		return campaignImageUrl;
	}
	public void setCampaignImageUrl(String campaignImageUrl) {
		this.campaignImageUrl = campaignImageUrl;
	}
	@Column(name = "TARGET_STATE")

	public String getTargetState() {
		return targetState;
	}
	public void setTargetState(String targetState) {
		this.targetState = targetState;
	}
	@Column(name = "TARGET_CITY")

	public String getTargetCity() {
		return targetCity;
	}
	public void setTargetCity(String targetCity) {
		this.targetCity = targetCity;
	}
	@Column(name = "TARGET_AREA")

	public String getTargetArea() {
		return targetArea;
	}
	public void setTargetArea(String targetArea) {
		this.targetArea = targetArea;
	}
	@Column(name = "TARGET_AREA_ZIP")

	public String getTargetAreaZip() {
		return targetAreaZip;
	}
	public void setTargetAreaZip(String targetAreaZip) {
		this.targetAreaZip = targetAreaZip;
	}    
	    
	@Column(name = "COMPANY_EMAIL")
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	
	@Column(name = "CONTACT_PERSON_NAME")
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}	
	@Column(name = "CONTACT_PERSON_EMAIL")
	public String getContactPersonEmail() {
		return contactPersonEmail;
	}

	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}
	@Column(name = "CONTACT_PERSON_PHONE")

	public String getContactPersonPhone() {
		return contactPersonPhone;
	}
	

	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}
	
	@Column(name = "PRICE")

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	@Column(name = "REQUEST_SAMPLE")

	public String getRequestSample() {
		return requestSample;
	}

	public void setRequestSample(String requestSample) {
		this.requestSample = requestSample;
	}
	@Column(name = "STATUS")

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DOCTORPUBLISH")

	public String getDoctorStatus() {
		return doctorStatus;
	}
	public void setDoctorStatus(String doctorStatus) {
		this.doctorStatus = doctorStatus;
	}
	
	@Column(name = "PATIENTPUBLISH")

	public String getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus;
	}
	@Column(name = "ADDRESS")

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
