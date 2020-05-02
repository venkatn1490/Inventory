package com.medrep.app.entity;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table( name="t_medicaldevices")

public class MedicalDeviceEntity implements java.io.Serializable {
	
	private int device_id;
	private String device_name;
	private String device_desc;
	private String therapeuticId;
	private String companyId;
	private String features;
	private int device_price;
	private int device_unit;
	private String companyUrl;
	private String deviceUrl;
	private Date createdOn;
	private String createdBy;
	private String status;
	
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false)
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	@Column(name="device_name")
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	@Column(name="device_DESC")
	public String getDevice_desc() {
		return device_desc;
	}
	public void setDevice_desc(String device_desc) {
		this.device_desc = device_desc;
	}
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="THERAPEUTIC_ID")
	public String getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(String therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	
	@Column(name="company_ID")
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Column(name="features")
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	
	@Column(name="device_price")
	public int getDevice_price() {
		return device_price;
	}
	public void setDevice_price(int device_price) {
		this.device_price = device_price;
	}
	
	@Column(name="device_unit")
	public int getDevice_unit() {
		return device_unit;
	}
	public void setDevice_unit(int device_unit) {
		this.device_unit = device_unit;
	}
	
	
	@Column(name="company_IMG_URL")
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	@Column(name="device_IMG_URL")
	public String getDeviceUrl() {
		return deviceUrl;
	}
	public void setDeviceUrl(String deviceUrl) {
		this.deviceUrl = deviceUrl;
	}
	@Column(name="CREATED_ON")
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
	
	

}
