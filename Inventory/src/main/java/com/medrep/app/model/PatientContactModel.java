package com.medrep.app.model;

public class PatientContactModel {

	private Integer contactId;
	private String contacType;
	private String contactNumber;
	private Integer priority;
	private String contactPersonName;
	private String city;
	private String facilityName;
	private String relattionShipToPatient;
	
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public String getContacType() {
		return contacType;
	}
	public void setContacType(String contacType) {
		this.contacType = contacType;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getRelattionShipToPatient() {
		return relattionShipToPatient;
	}
	public void setRelattionShipToPatient(String relattionShipToPatient) {
		this.relattionShipToPatient = relattionShipToPatient;
	}
	
}
