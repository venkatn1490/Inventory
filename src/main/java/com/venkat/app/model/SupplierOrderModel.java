package com.venkat.app.model;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1



import java.util.Collection;


public class SupplierOrderModel extends UserSecurityContext implements java.io.Serializable {

	private Integer orderId;
	private Integer supplierId;
	private Integer purchaserId;
	private String purchaserName;
	private String purchaserMobile;
	private String createdDate;
	

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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
}
