package com.medrep.app.model;

import org.json.simple.JSONArray;

public class DoctorCareerModel {
	
	private Integer careerDocId;
	private Integer doctorId;
	private String qualification;
	private Integer workExpYears;
	private Integer workNooFMonths;
	private JSONArray detailedWorkExperience;
	private JSONArray awards;
	private JSONArray publications;
	private JSONArray webinars;
	public Integer getCareerDocId() {
		return careerDocId;
	}
	public void setCareerDocId(Integer careerDocId) {
		this.careerDocId = careerDocId;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public Integer getWorkExpYears() {
		return workExpYears;
	}
	public void setWorkExpYears(Integer workExpYears) {
		this.workExpYears = workExpYears;
	}
	public Integer getWorkNooFMonths() {
		return workNooFMonths;
	}
	public void setWorkNooFMonths(Integer workNooFMonths) {
		this.workNooFMonths = workNooFMonths;
	}
	public JSONArray getDetailedWorkExperience() {
		return detailedWorkExperience;
	}
	public void setDetailedWorkExperience(JSONArray detailedWorkExperience) {
		this.detailedWorkExperience = detailedWorkExperience;
	}
	public JSONArray getAwards() {
		return awards;
	}
	public void setAwards(JSONArray awards) {
		this.awards = awards;
	}
	public JSONArray getPublications() {
		return publications;
	}
	public void setPublications(JSONArray publications) {
		this.publications = publications;
	}
	public JSONArray getWebinars() {
		return webinars;
	}
	public void setWebinars(JSONArray webinars) {
		this.webinars = webinars;
	}
	

}
