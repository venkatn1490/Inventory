package com.medrep.app.model;

public class NotificationType implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer typeId;
	private String typeName;
	private String typeDesc;
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	

}
