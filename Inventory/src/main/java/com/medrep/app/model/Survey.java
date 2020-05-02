package com.medrep.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Survey implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer surveyId;
	private Integer doctorId;
	private Integer repId;
	private Integer doctorSurveyId;
	private Integer repSurveyId;
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
	private String therapeuticDropDownValues;
	private String doctorName;
	private boolean reminder_sent;
	private boolean reportsStatus;
	private boolean reportsAvailable;
	private String reportUrl;

	private Integer totalSent;
	private Integer totalPending;
	private Integer totalCompleted;

	
	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
	public String getReportUrl() {
		return reportUrl;
	}
	public boolean isReportsAvailable() {
		return reportsAvailable;
	}
	public void setReportsAvailable(boolean reportsAvailable) {
		this.reportsAvailable = reportsAvailable;
	}

	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public Integer getRepId() {
		return repId;
	}
	public void setRepId(Integer repId) {
		this.repId = repId;
	}
	public Integer getDoctorSurveyId() {
		return doctorSurveyId;
	}
	public void setDoctorSurveyId(Integer doctorSurveyId) {
		this.doctorSurveyId = doctorSurveyId;
	}

	public Integer getRepSurveyId() {
		return repSurveyId;
	}
	public void setRepSurveyId(Integer repSurveyId) {
		this.repSurveyId = repSurveyId;
	}
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
	public String getTherapeuticDropDownValues() {
		return therapeuticDropDownValues;
	}
	public void setTherapeuticDropDownValues(String therapeuticDropDownValues) {
		this.therapeuticDropDownValues = therapeuticDropDownValues;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public boolean isReportsStatus() {
		return reportsStatus;
	}
	public void setReportsStatus(boolean reportsStatus) {
		this.reportsStatus = reportsStatus;
	}
	public boolean isReminder_sent() {
		return reminder_sent;
	}
	public void setReminder_sent(boolean reminder_sent) {
		this.reminder_sent = reminder_sent;
	}

	 private String publishSurveyId;
	 private String[] publishTareaIds;
	 private String[] publishDocsIds;

	public String getPublishSurveyId() {
		return publishSurveyId;
	}
	public void setPublishSurveyId(String publishSurveyId) {
		this.publishSurveyId = publishSurveyId;
	}
	public String[] getPublishTareaIds() {
		return publishTareaIds;
	}
	public void setPublishTareaIds(String[] publishTareaIds) {
		this.publishTareaIds = publishTareaIds;
	}
	public String[] getPublishDocsIds() {
		return publishDocsIds;
	}
	public void setPublishDocsIds(String[] publishDocsIds) {
		this.publishDocsIds = publishDocsIds;
	}
	public Integer getTotalSent() {
		return totalSent;
	}
	public void setTotalSent(Integer totalSent) {
		this.totalSent = totalSent;
	}
	public Integer getTotalPending() {
		return totalPending;
	}
	public void setTotalPending(Integer totalPending) {
		this.totalPending = totalPending;
	}
	public Integer getTotalCompleted() {
		return totalCompleted;
	}
	public void setTotalCompleted(Integer totalCompleted) {
		this.totalCompleted = totalCompleted;
	}




}
