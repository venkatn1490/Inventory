package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name="t_order_medicaldevices")

public class OrderMedicalDeviceEntity  implements java.io.Serializable {

	
	private int order_id;
	private String therapeuticId;
	private String device_id;
	private String companyId;
	private String orderBy;
	private Date orderOn;
	private String comments;
	private String mobileNo;
	private String emailId;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false)
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	@Column(name="therapeutic_Id")
	public String getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(String therapeuticId) {
		this.therapeuticId = therapeuticId;
	}

	@Column(name="device_ID")
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	@Column(name="company_Id")
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	

	@Column(name="order_Doctor_Id")
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	@Column(name="order_On")
	public Date getOrderOn() {
		return orderOn;
	}
	public void setOrderOn(Date orderOn) {
		this.orderOn = orderOn;
	}
	
	
	@Column(name="COMMENTS")
	public String getCOMMENTS() {
		return comments;
	}
	public void setCOMMENTS(String comments) {
		this.comments = comments;
	}
;
	@Column(name="MOBILE_NO")
	public String getMOBILENO() {
		return mobileNo;
	}
	public void setMOBILENO(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	
	@Column(name="EMAIL_ID")
	public String getEMAILID() {
		return emailId;
	}
	public void setEMAILID(String emailId) {
		this.emailId = emailId;
	}
}
