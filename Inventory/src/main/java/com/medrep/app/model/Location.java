package com.medrep.app.model;

import java.util.Set;

public class Location implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer locationId;
	private String address1;
	private String address2;
	private String locatity;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String type;
	private Set<Integer> locationIds;
	private String locationType;

	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<Integer> getLocationIds() {
		return locationIds;
	}
	public void setLocationIds(Set<Integer> locationIds) {
		this.locationIds = locationIds;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	
}