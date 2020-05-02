package com.medrep.app.model;

import org.springframework.web.multipart.MultipartFile;

public class MedicalDevicesModel {
	
	private int device_id;
	private String device_name;
	private String device_desc;
	private String therapeuticId;
	private String therapeuticName;
	private String companyId;
	private String companyName;
	private String features;
	private int device_price;
	private int device_unit;
	private MultipartFile companyLogo;
	private MultipartFile deviceLogo;
	private String companyUrl;
	private String deviceUrl;
	private String createdOn;
	private String createdBy;
	private String status;
	private String doctorname;
	
	
	public String getDoctorname() {
		return doctorname;
	}
	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getDevice_desc() {
		return device_desc;
	}
	public void setDevice_desc(String device_desc) {
		this.device_desc = device_desc;
	}
	
	
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public int getDevice_price() {
		return device_price;
	}
	public void setDevice_price(int device_price) {
		this.device_price = device_price;
	}
	public int getDevice_unit() {
		return device_unit;
	}
	public void setDevice_unit(int device_unit) {
		this.device_unit = device_unit;
	}
	public MultipartFile getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(MultipartFile companyLogo) {
		this.companyLogo = companyLogo;
	}
	public MultipartFile getDeviceLogo() {
		return deviceLogo;
	}
	public void setDeviceLogo(MultipartFile deviceLogo) {
		this.deviceLogo = deviceLogo;
	}
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	public String getDeviceUrl() {
		return deviceUrl;
	}
	public void setDeviceUrl(String deviceUrl) {
		this.deviceUrl = deviceUrl;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
	
	

}
