package com.medrep.app.model;

import org.json.simple.JSONArray;

public class Doctor extends User implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private String registrationYear;
	private String registrationNumber;
	private String stateMedCouncil;
	private String therapeuticId;
	private String qualification;
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
	private String doctorName;
	private String therapeuticName;
	private Integer doctorId;
	private String status;
	private Integer userId;
	private boolean favouriteNotification;
	private String remindNotification;
	private Integer totalnosurveys;	
	private Integer totalnotificationviewd;
	private Integer totalNoappointents;
	private Integer totalnooffeedback;

	private Integer workExpYears;
	private Integer workExpMonths;
	private JSONArray 	awards;
	private JSONArray 	webinars;
	
	public Integer getWorkExpYears() {
		return workExpYears;
	}
	public void setWorkExpYears(Integer workExpYears) {
		this.workExpYears = workExpYears;
	}
	public Integer getWorkExpMonths() {
		return workExpMonths;
	}
	public void setWorkExpMonths(Integer workExpMonths) {
		this.workExpMonths = workExpMonths;
	}
	public JSONArray getAwards() {
		return awards;
	}
	public void setAwards(JSONArray awards) {
		this.awards = awards;
	}
	public JSONArray getWebinars() {
		return webinars;
	}
	public void setWebinars(JSONArray webinars) {
		this.webinars = webinars;
	}
	private Integer score;
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getRemindNotification() {
		return remindNotification;
	}
	public void setRemindNotification(String remindNotification) {
		this.remindNotification = remindNotification;
	}
	public boolean getFavouriteNotification() {
		return favouriteNotification;
	}
	public void setFavouriteNotification(boolean favouriteNotification) {
		this.favouriteNotification = favouriteNotification;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTherapeuticName() {
		return therapeuticName;
	}
	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}
	public String getRegistrationYear() {
		return registrationYear;
	}
	public void setRegistrationYear(String registrationYear) {
		this.registrationYear = registrationYear;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getStateMedCouncil() {
		return stateMedCouncil;
	}
	public void setStateMedCouncil(String stateMedCouncil) {
		this.stateMedCouncil = stateMedCouncil;
	}
	public String getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(String therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTotalnosurveys() {
		return totalnosurveys;
	}
	public void setTotalnosurveys(Integer totalnosurveys) {
		this.totalnosurveys = totalnosurveys;
	}
	public Integer getTotalnotificationviewd() {
		return totalnotificationviewd;
	}
	public void setTotalnotificationviewd(Integer totalnotificationviewd) {
		this.totalnotificationviewd = totalnotificationviewd;
	}
	public Integer getTotalNoappointents() {
		return totalNoappointents;
	}
	public void setTotalNoappointents(Integer totalNoappointents) {
		this.totalNoappointents = totalNoappointents;
	}
	public Integer getTotalnooffeedback() {
		return totalnooffeedback;
	}
	public void setTotalnooffeedback(Integer totalnooffeedback) {
		this.totalnooffeedback = totalnooffeedback;
	}
	
}
