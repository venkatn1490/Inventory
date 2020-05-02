package com.medrep.app.model;

import java.sql.Date;

public class Patient  extends User implements java.io.Serializable  {
	private static final long serialVersionUID = 2542911871310426635L;

	
	private Integer patientId;
	private String dateofBirth;
	private String sex;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String height;
    private String weight;
    private String marriedstatus;
	private String bloodGroup;
	private String aadharCardNumber;
	
	private PatientContactModel patientContactModel;


	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public String getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAadharCardNumber() {
		return aadharCardNumber;
	}

	public void setAadharCardNumber(String aadharCardNumber) {
		this.aadharCardNumber = aadharCardNumber;
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getMarriedstatus() {
		return marriedstatus;
	}
	public void setMarriedstatus(String marriedstatus) {
		this.marriedstatus = marriedstatus;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public PatientContactModel getPatientContactModel() {
		return patientContactModel;
	}
	public void setPatientContactModel(PatientContactModel patientContactModel) {
		this.patientContactModel = patientContactModel;
	}
}
