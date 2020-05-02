package com.medrep.app.model;

import java.io.Serializable;

public class DoctorConsultingsModel implements Serializable {
	
	private Integer consultingsId;
	private Integer doctorId;
	private String consultingsType;
	
	private Integer addressId;
	private String contactNumber;
	private Integer maxAppointments;
	private Integer fee;
	private String consultingsFrom;
	private String consultingsTo;
	private String consultingsDays;
	private String responsetimefrom;
	private String  responsetimeto;
	private String threpeauticName;
	private String noOfYearsExperience;
	public String getNoOfYearsExperience() {
		return noOfYearsExperience;
	}
	public void setNoOfYearsExperience(String noOfYearsExperience) {
		this.noOfYearsExperience = noOfYearsExperience;
	}
	public String getDoctorEmailId() {
		return doctorEmailId;
	}
	public void setDoctorEmailId(String doctorEmailId) {
		this.doctorEmailId = doctorEmailId;
	}
	public String getStarRating() {
		return starRating;
	}
	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}
	private String doctorEmailId;
	private String starRating;
	public String getThrepeauticName() {
		return threpeauticName;
	}
	public void setThrepeauticName(String threpeauticName) {
		this.threpeauticName = threpeauticName;
	}
	public String getDpImageUrl() {
		return dpImageUrl;
	}
	public void setDpImageUrl(String dpImageUrl) {
		this.dpImageUrl = dpImageUrl;
	}
	private String dpImageUrl;
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	private String doctorName;
	private String address1;
	
	private String address2;
	private String locatity;
	private String city;
	private String state;
	public Integer getConsultingsId() {
		return consultingsId;
	}
	public void setConsultingsId(Integer consultingsId) {
		this.consultingsId = consultingsId;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public String getConsultingsType() {
		return consultingsType;
	}
	public void setConsultingsType(String consultingsType) {
		this.consultingsType = consultingsType;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public Integer getMaxAppointments() {
		return maxAppointments;
	}
	public void setMaxAppointments(Integer maxAppointments) {
		this.maxAppointments = maxAppointments;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public String getConsultingsFrom() {
		return consultingsFrom;
	}
	public void setConsultingsFrom(String consultingsFrom) {
		this.consultingsFrom = consultingsFrom;
	}
	public String getConsultingsTo() {
		return consultingsTo;
	}
	public void setConsultingsTo(String consultingsTo) {
		this.consultingsTo = consultingsTo;
	}
	public String getConsultingsDays() {
		return consultingsDays;
	}
	public void setConsultingsDays(String consultingsDays) {
		this.consultingsDays = consultingsDays;
	}
	public String getResponsetimefrom() {
		return responsetimefrom;
	}
	public void setResponsetimefrom(String responsetimefrom) {
		this.responsetimefrom = responsetimefrom;
	}
	public String getResponsetimeto() {
		return responsetimeto;
	}
	public void setResponsetimeto(String responsetimeto) {
		this.responsetimeto = responsetimeto;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
