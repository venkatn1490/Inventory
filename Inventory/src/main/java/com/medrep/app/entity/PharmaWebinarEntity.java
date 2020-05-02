package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="T_PHARMA_WEBINARS")
public class PharmaWebinarEntity implements java.io.Serializable {

	
	private Integer webinarId;
	private String webinarName;
	private String webinarDesc;
	private Integer companyId;
	private String companyName;
	private String status;
	private Date vdate;
	private Date cDate;
	private String vurl;
	private String thumbImgUrl;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false)
	public Integer getWebinarId() {
		return webinarId;
	}
	public void setWebinarId(Integer webinarId) {
		this.webinarId = webinarId;
	}
	
	@Column(name = "WEBINAR_NAME")
	public String getWebinarName() {
		return webinarName;
	}
	public void setWebinarName(String webinarName) {
		this.webinarName = webinarName;
	}
	@Column(name = "WEBINAR_DESC")
	public String getWebinarDesc() {
		return webinarDesc;
	}
	public void setWebinarDesc(String webinarDesc) {
		this.webinarDesc = webinarDesc;
	}
	@Column(name = "COMPANY_ID")
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "V_DATE")
	public Date getVdate() {
		return vdate;
	}
	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}
	@Column(name = "C_DATE")
	public Date getcDate() {
		return cDate;
	}
	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}
	@Column(name = "URL")
	public String getVurl() {
		return vurl;
	}
	public void setVurl(String vurl) {
		this.vurl = vurl;
	}
	@Column(name = "THUMB_IMAGE_URL")
	public String getThumbImgUrl() {
		return thumbImgUrl;
	}
	public void setThumbImgUrl(String thumbImgUrl) {
		this.thumbImgUrl = thumbImgUrl;
	}	
}
