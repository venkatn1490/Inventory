package com.medrep.app.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class PharmaWebinar implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;
	
	
	private Integer webinarId;
	private String webinarName;
	private String webinarDesc;
	private Integer companyId;
	private String companyName;
	private String status;
	private String vdate;
	private String cDate;
	private String vurl;
	private MultipartFile thumbImage;
	private String thumbImgUrl;
	
	public Integer getWebinarId() {
		return webinarId;
	}
	public void setWebinarId(Integer webinarId) {
		this.webinarId = webinarId;
	}
	public String getWebinarName() {
		return webinarName;
	}
	public void setWebinarName(String webinarName) {
		this.webinarName = webinarName;
	}
	public String getWebinarDesc() {
		return webinarDesc;
	}
	public void setWebinarDesc(String webinarDesc) {
		this.webinarDesc = webinarDesc;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVdate() {
		return vdate;
	}
	public void setVdate(String vdate) {
		this.vdate = vdate;
	}
	public String getcDate() {
		return cDate;
	}
	public void setcDate(String cDate) {
		this.cDate = cDate;
	}
	
	public String getVurl() {
		return vurl;
	}
	public void setVurl(String vurl) {
		this.vurl = vurl;
	}
	public MultipartFile getThumbImage() {
		return thumbImage;
	}
	public void setThumbImage(MultipartFile thumbImage) {
		this.thumbImage = thumbImage;
	}
	public String getThumbImgUrl() {
		return thumbImgUrl;
	}
	public void setThumbImgUrl(String thumbImgUrl) {
		this.thumbImgUrl = thumbImgUrl;
	}

}
