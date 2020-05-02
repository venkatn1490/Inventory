package com.medrep.app.model;

public class HospitalModel {
	

	private int hospital_id;
	private String  hospital_name;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode;
	private String mobileNo;
	private String latitude;
	private String longtitude;
	private String therapeutic_id_list;
	private String createdOn;
	private String hospitalsLogo;
	private String hospitalsImage;

	public String getLocatity() {
		return locatity;
	}
	public void setLocatity(String locatity) {
		this.locatity = locatity;
	}
	private String locatity;
	
	
	public int getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(int hospital_id) {
		this.hospital_id = hospital_id;
	}

	public String getHospital_name() {
		return hospital_name;
	}
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
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
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}
	public String getTherapeutic_id_list() {
		return therapeutic_id_list;
	}
	public void setTherapeutic_id_list(String therapeutic_id_list) {
		this.therapeutic_id_list = therapeutic_id_list;
	}
	
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getHospitalsLogo() {
		return hospitalsLogo;
	}
	public void setHospitalsLogo(String hospitalsLogo) {
		this.hospitalsLogo = hospitalsLogo;
	}
	public String getHospitalsImage() {
		return hospitalsImage;
	}
	public void setHospitalsImage(String hospitalsImage) {
		this.hospitalsImage = hospitalsImage;
	}
}
