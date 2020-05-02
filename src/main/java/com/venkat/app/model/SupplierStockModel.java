package com.venkat.app.model;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1



import java.util.Collection;


public class SupplierStockModel extends UserSecurityContext implements java.io.Serializable {

	private Integer itemId;
	private String itemName;
	private String totalStock;
	private String availableStock;
	
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
	public String getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(String totalStock) {
		this.totalStock = totalStock;
	}
	public String getAvailableStock() {
		return availableStock;
	}
	public void setAvailableStock(String availableStock) {
		this.availableStock = availableStock;
	}
	

	
}
