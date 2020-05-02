package com.venkat.app.model;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1



import java.util.Collection;


public class CustomerOrderModel extends UserSecurityContext implements java.io.Serializable {

	private Integer orderId;
	private Integer customerId;
	private Integer purchaserId;
	private Integer salesmanId;
	private String salesmanName;
	private String salesmanMobile;
	private String purchaserName;
	private String purchaserMobile;
	private String customerName;
	private String customerMobile;
	private String createdDate;

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Integer getPurchaserId() {
		return purchaserId;
	}
	public void setPurchaserId(Integer purchaserId) {
		this.purchaserId = purchaserId;
	}
	public String getPurchaserName() {
		return purchaserName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	public String getPurchaserMobile() {
		return purchaserMobile;
	}
	public void setPurchaserMobile(String purchaserMobile) {
		this.purchaserMobile = purchaserMobile;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getSalesmanId() {
		return salesmanId;
	}
	public void setSalesmanId(Integer salesmanId) {
		this.salesmanId = salesmanId;
	}
	public String getSalesmanName() {
		return salesmanName;
	}
	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}
	public String getSalesmanMobile() {
		return salesmanMobile;
	}
	public void setSalesmanMobile(String salesmanMobile) {
		this.salesmanMobile = salesmanMobile;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	
}
