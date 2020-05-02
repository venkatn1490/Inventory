package com.medrep.app.model;

import java.util.List;

import com.medrep.app.entity.MedicalDeviceEntity;
import com.medrep.app.entity.UserEntity;

public class OrderMedicalDevice extends User implements java.io.Serializable   {
	
	private String deviceId;
	private String deviceName;
	private String companyID;
	private String companyName;
	private String doctorName;
	private String orderDoctorId;
	private String mobileNumber;
	private String emailId;
	private String orderOn;
	private String address;
	private String comments;
	private Integer orderid;
	private String therapeuticId;
	private String therapeuticName;
	private String companyURL;
	private String addressflag;
	private List<MedicalDevicesModel> medicaldevicemodel;
	public List<MedicalDevicesModel> getMedicalDevicesModel() {
		return medicaldevicemodel;
	}
	public void setMedicalDevicesModel(List<MedicalDevicesModel> medicaldevicemodel) {
		this.medicaldevicemodel = medicaldevicemodel;
	}
	public String getAddressflag() {
		return addressflag;
	}
	public void setAddressflag(String addressflag) {
		this.addressflag = addressflag;
	}
	public String getCompanyURL() {
		return companyURL;
	}
	public void setCompanyURL(String companyURL) {
		this.companyURL = companyURL;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getOrderDoctorId() {
		return orderDoctorId;
	}
	public void setOrderDoctorId(String orderDoctorId) {
		this.orderDoctorId = orderDoctorId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getOrderOn() {
		return orderOn;
	}
	public void setOrderOn(String orderOn) {
		this.orderOn = orderOn;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public String getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(String therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	public String getTherapeuticName() {
		return therapeuticName;
	}
	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}
}
