package com.medrep.app.model;

import java.util.List;

public class SurveyStatistics {

	private Integer surveyId;
	private Integer totalSent;
	private Integer totalPending;
	private Integer totalCompleted;
	private List<Doctor> completed;
	private List<Doctor> pending;
	private List<Doctor> sent;
	public void setSent(List<Doctor> sent) {
		this.sent = sent;
	}
	public List<Doctor> getSent() {
		return sent;
	}


	public List<Doctor> getCompleted() {
		return completed;
	}
	public void setCompleted(List<Doctor> completed) {
		this.completed = completed;
	}
	public List<Doctor> getPending() {
		return pending;
	}
	public void setPending(List<Doctor> pending) {
		this.pending = pending;
	}
	public Integer getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
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
