package com.medrep.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_DEVICE_TOKEN")
public class DeviceTokenEntity implements java.io.Serializable {

	public String deviceToken;
	public int docId;
	public int repId;
	public int repMgrId;
	public Integer patId;
	public String platform;
	

	public Date cdate;

	

	

	@Id
	@Column(name = "DEVICE_TOKEN", nullable = false)
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Column(name = "DOC_ID", nullable = true)
	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	@Column(name = "PLATFORM", nullable = true)
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	@Column(name = "CDATE", nullable = true)
	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	@Column(name = "REP_ID", nullable = true)
	public int getRepId() {
		return repId;
	}

	public void setRepId(int medRepId) {
		this.repId = medRepId;
	}

	@Column(name = "REP_MGR_ID", nullable = true)
	public int getRepMgrId() {
		return repMgrId;
	}

	public void setRepMgrId(int medRepMgrId) {
		this.repMgrId = medRepMgrId;
	}

	@Column(name = "PATIENT_ID", nullable = true)
	public Integer getPatId() {
		return patId;
	}

	public void setPatId(Integer patId) {
		this.patId = patId;
	}



}
