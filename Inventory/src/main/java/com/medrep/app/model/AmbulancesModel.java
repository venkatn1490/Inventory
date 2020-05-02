package com.medrep.app.model;

/**
 * @author VIKAS
 *
 */
public class AmbulancesModel {
	
	
	private int ambulance_id;
	private String  ambulance_name;
	private String address1;
	private String address2;
	private String ambulancesLogo;
	private String ambulancesImage;
	public String getLocatity() {
		return locatity;
	}
	public void setLocatity(String locatity) {
		this.locatity = locatity;
	}
	private String locatity;
	
	public String getFee() {
		return Fee;
	}
	public void setFee(String fee) {
		Fee = fee;
	}
	private String Fee;
	
	private String city;
	private String state;
	private String zipcode;
	private String mobileNo;
	private String latitude;
	private String longtitude;
	private String createdOn;
	
	public int getAmbulance_id() {
		return ambulance_id;
	}
	public void setAmbulance_id(int ambulance_id) {
		this.ambulance_id = ambulance_id;
	}
	public String getAmbulance_name() {
		return ambulance_name;
	}
	public void setAmbulance_name(String ambulance_name) {
		this.ambulance_name = ambulance_name;
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
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getAmbulancesLogo() {
		return ambulancesLogo;
	}
	public void setAmbulancesLogo(String ambulancesLogo) {
		this.ambulancesLogo = ambulancesLogo;
	}
	public String getAmbulancesImage() {
		return ambulancesImage;
	}
	public void setAmbulancesImage(String ambulancesImage) {
		this.ambulancesImage = ambulancesImage;
	}

}
