package com.medrep.app.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Notification implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer notificationId;
	private String notificationDesc;
	private String notificationName;
	private Integer typeId;
	private Integer therapeuticId;
	private Integer companyId;
	private String updatedOn;
	private Integer updatedBy;
	private String createdOn;
	private Integer createdBy;
	private String validUpto;
	private String status;
	private String externalRef;
	private String companyName;
	private String therapeuticName;
	private List<NotificationDetails> notificationDetails = new ArrayList<NotificationDetails>();

	private Integer totalSentNotification;
	private Integer totalPendingNotifcation;
	private Integer totalViewedNotifcation;
	private Integer totalConvertedToAppointment;
	private Integer viewCount;
	private String dPicture;
	private boolean favourite;
	private String remindMe;
	private String viewStatus;
	private Integer totalCntFavouriteNotification;
	private Integer totalnotificationRemainders;




	public String getViewStatus() {
		return viewStatus;
	}
	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}
	public boolean isFavourite() {
		return favourite;
	}
	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}
	public String getdPicture() {
		return dPicture;
	}
	public void setdPicture(String dPicture) {
		this.dPicture = dPicture;
	}

	 private List<MultipartFile> fileList;
	 //private List<MultipartFile> fileList1;

	 private String therapeuticDropDownValues;

	 private String docsDropDownValues;

	//publish
	 private String publishNotificationId;
	 private String[] publishTareaIds;
	 private String[] publishDocsIds;

	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	public String getNotificationDesc() {
		return notificationDesc;
	}
	public void setNotificationDesc(String notificationDesc) {
		this.notificationDesc = notificationDesc;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(Integer therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getNotificationName() {
		return notificationName;
	}
	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}
	public String getStatus() {
		return status;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getValidUpto() {
		return validUpto;
	}
	public void setValidUpto(String validUpto) {
		this.validUpto = validUpto;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExternalRef() {
		return externalRef;
	}
	public void setExternalRef(String externalRef) {
		this.externalRef = externalRef;
	}
	public List<NotificationDetails> getNotificationDetails() {
		return notificationDetails;
	}
	public String getCompanyName() {
		return companyName;
	}
	public Integer getTotalSentNotification() {
		return totalSentNotification;
	}

	public String getTherapeuticDropDownValues() {
		return therapeuticDropDownValues;
	}

	public void setTherapeuticDropDownValues(String therapeuticDropDownValues) {
		this.therapeuticDropDownValues = therapeuticDropDownValues;
	}
	public void setTotalSentNotification(Integer totalSentNotification) {
		this.totalSentNotification = totalSentNotification;
	}
	public Integer getTotalPendingNotifcation() {
		return totalPendingNotifcation;
	}
	public void setTotalPendingNotifcation(Integer totalPendingNotifcation) {
		this.totalPendingNotifcation = totalPendingNotifcation;
	}
	public Integer getTotalViewedNotifcation() {
		return totalViewedNotifcation;
	}
	public void setTotalViewedNotifcation(Integer totalViewedNotifcation) {
		this.totalViewedNotifcation = totalViewedNotifcation;
	}
	public Integer getTotalConvertedToAppointment() {
		return totalConvertedToAppointment;
	}
	public void setTotalConvertedToAppointment(Integer totalConvertedToAppointment) {
		this.totalConvertedToAppointment = totalConvertedToAppointment;
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
	@JsonIgnore
	public List<MultipartFile> getFileList() {
		return fileList;
	}
	public void setFileList(List<MultipartFile> fileList) {
		this.fileList = fileList;
	}
	public void setNotificationDetails(List<NotificationDetails> notificationDetails) {
		this.notificationDetails = notificationDetails;
	}
	public String getDocsDropDownValues() {
		return docsDropDownValues;
	}
	public void setDocsDropDownValues(String docsDropDownValues) {
		this.docsDropDownValues = docsDropDownValues;
	}
	public String getPublishNotificationId() {
		return publishNotificationId;
	}
	public void setPublishNotificationId(String publishNotificationId) {
		this.publishNotificationId = publishNotificationId;
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
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public String getRemindMe() {
		return remindMe;
	}
	public void setRemindMe(String remindMe) {
		this.remindMe = remindMe;
	}
	public Integer getTotalCntFavouriteNotification() {
		return totalCntFavouriteNotification;
	}
	public void setTotalCntFavouriteNotification(Integer totalCntFavouriteNotification) {
		this.totalCntFavouriteNotification = totalCntFavouriteNotification;
	}
	public Integer getTotalnotificationRemainders() {
		return totalnotificationRemainders;
	}
	public void setTotalnotificationRemainders(Integer totalnotificationRemainders) {
		this.totalnotificationRemainders = totalnotificationRemainders;
	}
	/*public List<MultipartFile> getFileList1() {
		return fileList1;
	}
	public void setFileList1(List<MultipartFile> fileList1) {
		this.fileList1 = fileList1;
	}*/


}
