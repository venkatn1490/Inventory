package com.medrep.app.model;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DisplayPicture implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer dpId;
	private String loginId;
	private Integer securityId;
	private String dPicture;
	private String imgData;
	private String fileName;
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}
	
	
	public String getImgData() {
		return imgData;
	}
	public void setImgData(String imgData) {
		this.imgData = imgData;
	}
	public String getdPicture() {
		return dPicture;
	}
	public void setdPicture(String imageUrl) {
		this.dPicture = imageUrl;
	}
	public Integer getDpId() {
		return dpId;
	}
	public void setDpId(Integer dpId) {
		this.dpId = dpId;
	}
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	@JsonIgnore
	public Integer getSecurityId() {
		return securityId;
	}
	public void setSecurityId(Integer securityId) {
		this.securityId = securityId;
	}




}
