package com.medrep.app.model;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NotificationDetails implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer detailId;
	private Integer notificationId;
	private String detailTitle;
	private String detailDesc;
	private String contentType;
	private Integer contentSeq;
	private String contentLocation;
	private String contentName;
	private MultipartFile fileList;
	
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Integer getContentSeq() {
		return contentSeq;
	}
	public void setContentSeq(Integer contentSeq) {
		this.contentSeq = contentSeq;
	}
	//@JsonIgnore
	public String getContentLocation() {
		return contentLocation;
	}
	public void setContentLocation(String contentLocation) {
		this.contentLocation = contentLocation;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getDetailTitle() {
		return detailTitle;
	}
	public void setDetailTitle(String detailTitle) {
		this.detailTitle = detailTitle;
	}
	public String getDetailDesc() {
		return detailDesc;
	}
	public void setDetailDesc(String detailDesc) {
		this.detailDesc = detailDesc;
	}
	public MultipartFile getFileList() {
		return fileList;
	}
	public void setFileList(MultipartFile fileList) {
		this.fileList = fileList;
	}


}
