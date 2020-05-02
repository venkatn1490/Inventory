package com.venkat.app.model;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1



import java.util.Collection;


public class SupplierOrderItemModel extends UserSecurityContext implements java.io.Serializable {

	private Integer orderId;
	private Integer itemId;
	private String itemName;
	private Integer stock;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	
}
