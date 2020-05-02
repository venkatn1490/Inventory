package com.venkat.app.model;

import java.io.Serializable;

public class LoginRequestModel implements Serializable{
	 protected static final long serialVersionUID = -1740566795590994197L;
	  private String username;
	  private String password;
	  private String mobileNo;
	  private String deviceToken;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	  
}
