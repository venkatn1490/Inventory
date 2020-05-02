package com.medrep.app.model;

import org.json.simple.JSONArray;

public class DiagnosticsModel {

	
	private int diagnostics_id;
	private String  diagnostics_name;
	private String address1;
	private String address2;
	public String getLocatity() {
		return locatity;
	}
	public void setLocatity(String locatity) {
		this.locatity = locatity;
	}
	private String locatity;
	private String city;
	private String state;
	private String zipcode;
	private String mobileNo;
	private String latitude;
	private String longtitude;
	private String therapeutic_id_list;	
	private JSONArray  services_list;
	private String createdOn;
	
	private String homeCollection;
	private String dignosticsLogo;
	public String getDignosticsLogo() {
		return dignosticsLogo;
	}
	public void setDignosticsLogo(String dignosticsLogo) {
		this.dignosticsLogo = dignosticsLogo;
	}
	public String getDignosticsImage() {
		return dignosticsImage;
	}
	public void setDignosticsImage(String dignosticsImage) {
		this.dignosticsImage = dignosticsImage;
	}
	private String dignosticsImage;
	public int getDiagnostics_id() {
		return diagnostics_id;
	}
	public void setDiagnostics_id(int diagnostics_id) {
		this.diagnostics_id = diagnostics_id;
	}
	public String getDiagnostics_name() {
		return diagnostics_name;
	}
	public void setDiagnostics_name(String diagnostics_name) {
		this.diagnostics_name = diagnostics_name;
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
	public JSONArray getServices_list() {
		return services_list;
	}
	public void setServices_list(JSONArray services_list) {
		this.services_list = services_list;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getHomeCollection() {
		return homeCollection;
	}
	public void setHomeCollection(String homeCollection) {
		this.homeCollection = homeCollection;
	}
	
}
