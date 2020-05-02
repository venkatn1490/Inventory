package com.medrep.app.model;

public class PharmaRep extends User implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer repId;
	private Integer companyId;
	private String companyName;
	private String coveredArea;
	private Integer managerId;
	private String coveredZone;
	private String therapeuticId;
	private String therapeuticName;
	private String managerEmail;

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}


	public Integer getRepId() {
		return repId;
	}
	public void setRepId(Integer repId) {
		this.repId = repId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCoveredArea() {
		return coveredArea;
	}
	public void setCoveredArea(String coveredArea) {
		this.coveredArea = coveredArea;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public String getCoveredZone() {
		return coveredZone;
	}
	public void setCoveredZone(String coveredZone) {
		this.coveredZone = coveredZone;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
