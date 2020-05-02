package com.medrep.app.model;

import java.io.Serializable;
import java.util.Date;

public class ChatMessages implements Serializable {

	
	private Integer userType;
	private String text;
	private String msgType;
	private String thumbImg;
	private String orgImg;
	private String imgName;

	private String sDate;
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer sender) {
		this.userType = sender;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String getThumbImg() {
		return thumbImg;
	}
	public void setThumbImg(String thumbImg) {
		this.thumbImg = thumbImg;
	}
	public String getOrgImg() {
		return orgImg;
	}
	public void setOrgImg(String orgImg) {
		this.orgImg = orgImg;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

}
