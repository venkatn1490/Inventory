package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.json.simple.JSONArray;

@Entity
@Table(name = "P_CAMPAIGN_FEEDBACK_QUESTIONS", uniqueConstraints = @UniqueConstraint(columnNames = "CAREER_DOC_ID") )
public class DoctorCareerEntity implements java.io.Serializable{


	private Integer careerDocId;
	private Integer doctorId;
	private String qualification;
	private Integer workExpyears;
	private Integer workExpMonths;
	private JSONArray detailtedWorkExperience;
	private JSONArray awards;
	private JSONArray publications;
	private JSONArray webinars;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CAREER_DOC_ID", nullable = false)
	public Integer getCareerDocId() {
		return careerDocId;
	}
	public void setCareerDocId(Integer careerDocId) {
		this.careerDocId = careerDocId;
	}
	@Column(name = "DOCTOR_ID")

	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	@Column(name = "QUALIFICATION")

	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	@Column(name = "WORK_EXP_YEARS")

	public Integer getWorkExpyears() {
		return workExpyears;
	}
	public void setWorkExpyears(Integer workExpyears) {
		this.workExpyears = workExpyears;
	}
	@Column(name = "WORK_EXP_MONTH")

	public Integer getWorkExpMonths() {
		return workExpMonths;
	}
	public void setWorkExpMonths(Integer workExpMonths) {
		this.workExpMonths = workExpMonths;
	}
	@Column(name = "DETAILED_WORK_EXPERIENCE")

	public JSONArray getDetailtedWorkExperience() {
		return detailtedWorkExperience;
	}
	public void setDetailtedWorkExperience(JSONArray detailtedWorkExperience) {
		this.detailtedWorkExperience = detailtedWorkExperience;
	}
	@Column(name = "AWARDS")

	public JSONArray getAwards() {
		return awards;
	}
	public void setAwards(JSONArray awards) {
		this.awards = awards;
	}
	@Column(name = "PUBLICATIONS")

	public JSONArray getPublications() {
		return publications;
	}
	public void setPublications(JSONArray publications) {
		this.publications = publications;
	}
	@Column(name = "WEBINARS")

	public JSONArray getWebinars() {
		return webinars;
	}
	public void setWebinars(JSONArray webinars) {
		this.webinars = webinars;
	}
	
	

}
