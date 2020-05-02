package com.medrep.app.model;

import java.io.Serializable;

public class RegisterDeviceToken  implements Serializable{

	public Integer docId;
	public String regDeviceToken;
	public String platform;

	

	public String getRegDeviceToken() {
		return regDeviceToken;
	}

	public void setRegDeviceToken(String regDeviceToken) {
		this.regDeviceToken = regDeviceToken;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
