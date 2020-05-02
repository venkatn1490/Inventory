package com.medrep.app.model;


public class CompanySurvey implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer surveyId;
	private Integer notifiedDoctorsCount;
	private Integer doctorResponseCount;
	private String surveyTitle;
	private String surveyUrl;
	private String createdOn;
	private String status;
	private String scheduledStart;
	private String scheduledFinish;
	private Integer companyId;
	private Integer therapeuticId;
	private String companyName;
	private String therapeuticName;
	private String surveyDescription;
	
	

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTherapeuticName() {
		return therapeuticName;
	}
	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}
	public String getSurveyDescription() {
		return surveyDescription;
	}
	public void setSurveyDescription(String surveyDescription) {
		this.surveyDescription = surveyDescription;
	}
	public Integer getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}
	public String getSurveyTitle() {
		return surveyTitle;
	}
	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}
	public String getSurveyUrl() {
		return surveyUrl;
	}
	public void setSurveyUrl(String surveyUrl) {
		this.surveyUrl = surveyUrl;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getScheduledStart() {
		return scheduledStart;
	}
	public void setScheduledStart(String scheduledStart) {
		this.scheduledStart = scheduledStart;
	}
	public String getScheduledFinish() {
		return scheduledFinish;
	}
	public void setScheduledFinish(String scheduledFinish) {
		this.scheduledFinish = scheduledFinish;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(Integer therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	public Integer getNotifiedDoctorsCount() {
		return notifiedDoctorsCount;
	}
	public void setNotifiedDoctorsCount(Integer notifiedDoctorsCount) {
		this.notifiedDoctorsCount = notifiedDoctorsCount;
	}
	public Integer getDoctorResponseCount() {
		return doctorResponseCount;
	}
	public void setDoctorResponseCount(Integer doctorResponseCount) {
		this.doctorResponseCount = doctorResponseCount;
	}

	

}
