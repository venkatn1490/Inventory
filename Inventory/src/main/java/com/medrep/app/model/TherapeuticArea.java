package com.medrep.app.model;

import java.util.List;
import java.util.Set;

public class TherapeuticArea implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer therapeuticId;
	private String therapeuticName;
	private String therapeuticDesc;
	private List<Integer> therapeuticIds;
	private Set<Company> companies;

	public Integer getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(Integer therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	public String getTherapeuticName() {
		return therapeuticName;
	}
	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}
	public String getTherapeuticDesc() {
		return therapeuticDesc;
	}
	public void setTherapeuticDesc(String therapeuticDesc) {
		this.therapeuticDesc = therapeuticDesc;
	}
	public Set<Company> getCompanies() {
		return companies;
	}
	public void setCompanies(Set<Company> companies) {
		this.companies = companies;
	}
	public List<Integer> getTherapeuticIds() {
		return therapeuticIds;
	}
	public void setTherapeuticIds(List<Integer> therapeuticIds) {
		this.therapeuticIds = therapeuticIds;
	}
}