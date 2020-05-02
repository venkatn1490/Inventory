package com.venkat.app.model;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1



import java.util.Collection;


public class MasterItemModel extends UserSecurityContext implements java.io.Serializable {

	private Integer id;
	private String itemName;
	private String description;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
