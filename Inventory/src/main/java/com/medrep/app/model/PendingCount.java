package com.medrep.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PendingCount {

	Integer surveyCount;
	Integer notificationsCount;
	Integer doctorPlusCount;
	Boolean resetSurveyCount;
	Boolean resetNotificationCount;
	Boolean resetDoctorPlusCount;

	public Integer getSurveyCount() {
		return surveyCount;
	}
	public void setSurveyCount(Integer surveyCount) {
		this.surveyCount = surveyCount;
	}
	public Integer getNotificationsCount() {
		return notificationsCount;
	}
	public void setNotificationsCount(Integer notificationsCount) {
		this.notificationsCount = notificationsCount;
	}
	public Integer getDoctorPlusCount() {
		return doctorPlusCount;
	}
	public void setDoctorPlusCount(Integer doctorPlusCount) {
		this.doctorPlusCount = doctorPlusCount;
	}

	public Boolean getResetSurveyCount() {
		return resetSurveyCount;
	}

	public void setResetSurveyCount(Boolean resetSurveyCount) {
		this.resetSurveyCount = resetSurveyCount;
	}

	public Boolean getResetNotificationCount() {
		return resetNotificationCount;
	}

	public void setResetNotificationCount(Boolean resetNotificationCount) {
		this.resetNotificationCount = resetNotificationCount;
	}

	public Boolean getResetDoctorPlusCount() {
		return resetDoctorPlusCount;
	}
	public void setResetDoctorPlusCount(Boolean resetDoctorPlusCount) {
		this.resetDoctorPlusCount = resetDoctorPlusCount;
	}



}
