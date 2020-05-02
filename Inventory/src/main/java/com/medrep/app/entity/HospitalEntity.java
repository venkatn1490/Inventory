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
@Table(name="M_HOSPITALS")
public class HospitalEntity implements java.io.Serializable {

	private int hospital_id;
	private String  hospital_name;
	private String address1;
	private String address2;
	private String locatity;
	private String city;
	private String state;
	private String zipcode;
	private String mobileNo;
	private String latitude;
	private String longtitude;
	private String therapeutic_id_list;
	private Date createdOn;

	private String hospitalsLogo;
	private String hospitalsImage;

	


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="HOSPITAL_ID")
	public int getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(int hospital_id) {
		this.hospital_id = hospital_id;
	}
	
	@Column(name="HOSTPITAL_NAME")
	public String getHospital_name() {
		return hospital_name;
	}
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
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
	
	@Column(name="THERAPEUTIC_ID_LIST")	
	public String getTherapeutic_id_list() {
		return therapeutic_id_list;
	}
	public void setTherapeutic_id_list(String therapeutic_id_list) {
		this.therapeutic_id_list = therapeutic_id_list;
	}
	
	@Column(name="HOSPITAL_THUMD_URL")	

	public String getHospitalsLogo() {
		return hospitalsLogo;
	}
	public void setHospitalsLogo(String hospitalsLogo) {
		this.hospitalsLogo = hospitalsLogo;
	}
	@Column(name="HOSPITAL_IMAGE_URL")	

	public String getHospitalsImage() {
		return hospitalsImage;
	}
	public void setHospitalsImage(String hospitalsImage) {
		this.hospitalsImage = hospitalsImage;
	}
	@Column(name="POST_DATE")
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
