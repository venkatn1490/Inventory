package com.medrep.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.simple.JSONArray;

@Entity
@Table(name="M_DIAGNOSTICS")
public class DiagnosticsEntity implements java.io.Serializable {

	private Integer diagnostics_id;
	private String  diagnostics_name;
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
	private JSONArray  services_list;
	private Date createdOn;
	private String homeCollection;

	private String dignosticsLogo;
	private String dignosticsImage;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DIAGNOSTIC_ID")
	public Integer getDiagnostics_id() {
		return diagnostics_id;
	}
	public void setDiagnostics_id(Integer diagnostics_id) {
		this.diagnostics_id = diagnostics_id;
	}
	
	@Column(name="DIAGNOSTIC_NAME")
	public String getDiagnostics_name() {
		return diagnostics_name;
	}
	public void setDiagnostics_name(String diagnostics_name) {
		this.diagnostics_name = diagnostics_name;
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
	
	@Column(name="POST_DATE")
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Column(name="THERAPEUTIC_ID_LIST")	
	public String getTherapeutic_id_list() {
		return therapeutic_id_list;
	}
	public void setTherapeutic_id_list(String therapeutic_id_list) {
		this.therapeutic_id_list = therapeutic_id_list;
	}
	
	@Column(name="SERVICES_LIST")
	public JSONArray getServices_list() {
		return services_list;
	}
	public void setServices_list(JSONArray services_list) {
		this.services_list = services_list;
	}
	@Column(name="DIAGNOSTIC_THUMB_LOGO")
	public String getDignosticsLogo() {
		return dignosticsLogo;
	}
	public void setDignosticsLogo(String dignosticsLogo) {
		this.dignosticsLogo = dignosticsLogo;
	}
	@Column(name="DIAGNOSTIC_IMAGE_URL")
	public String getDignosticsImage() {
		return dignosticsImage;
	}
	public void setDignosticsImage(String dignosticsImage) {
		this.dignosticsImage = dignosticsImage;
	}
	@Column(name="HOMECOLLECTIONFLAG")	
	public String getHomeCollection() {
		return homeCollection;
	}
	public void setHomeCollection(String homeCollection) {
		this.homeCollection = homeCollection;
	}
	
}

