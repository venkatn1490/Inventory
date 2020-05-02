package com.medrep.app.model;

import java.util.List;

public class InviteContacts implements java.io.Serializable {

	public String fromMobileContact;
	public List<String> toMobileContactList;

	public String message;
	public String url;


	public String getFromMobileContact() {
		return fromMobileContact;
	}

	public void setFromMobileContact(String fromMobileContact) {
		this.fromMobileContact = fromMobileContact;
	}

	public List<String> getToMobileContactList() {
		return toMobileContactList;
	}

	public void setToMobileContactList(List<String> toMobileContactList) {
		this.toMobileContactList = toMobileContactList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
