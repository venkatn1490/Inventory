package com.medrep.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="M_AMBULANCES")
public class AmbulancesEntity implements java.io.Serializable {

	private int ambulance_id;
	private int hospital_id;
	private String  ambulance_name;
	private String address1;
	private String address2;
	
	private String locatity;
	private String city;
	private String state;
	private String zipcode;
	private String mobileNo;
	private String latitude;
	private String longtitude;
	private String ambulancesLogo;
	private String ambulancesImage;
	private String subdistrict;
	private String Fee;
	private Date createdOn;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AMBULANCE_ID")
	public int getAmbulance_id() {
		return ambulance_id;
	}
	public void setAmbulance_id(int ambulance_id) {
		this.ambulance_id = ambulance_id;
	}
	
	
	@Column(name="AMBULANCE_NAME")
	public String getAmbulance_name() {
		return ambulance_name;
	}
	public void setAmbulance_name(String ambulance_name) {
		this.ambulance_name = ambulance_name;
	}
	@Column(name = "FEE")
	public String getFee() {
		return Fee;
	}
	public void setFee(String fee) {
		Fee = fee;
	}
	@Column(name = "ADDRESS1")
	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "ADDRESS2")
	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	@Column(name = "LOCATITY")
	public String getLocatity() {
		return locatity;
	}
	public void setLocatity(String locatity) {
		this.locatity = locatity;
	}
	@Column(name = "CITY")
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STATE")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	@Column(name = "ZIP")
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	@Column(name = "LATITUDE")

	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@Column(name = "LONGITUDE")
	public String getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}
	@Column(name = "CONTACT_NUMBER")
	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	@Column(name = "AMBULANCE_THUMB_LOGO")
	public String getAmbulancesLogo() {
		return ambulancesLogo;
	}
	public void setAmbulancesLogo(String ambulancesLogo) {
		this.ambulancesLogo = ambulancesLogo;
	}
	@Column(name="POST_DATE")
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Column(name = "AMBULANCE_IMAGE_URL")
	public String getAmbulancesImage() {
		return ambulancesImage;
	}
	public void setAmbulancesImage(String ambulancesImage) {
		this.ambulancesImage = ambulancesImage;
	}
	
}
