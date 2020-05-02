package com.medrep.app.model;

import java.io.Serializable;
import java.util.List;

public class ConnectionDetails implements Serializable {

	private Integer connectionId;
	private Integer doctorId;
	private String status;
	private List<Integer> connList;

	public List<Integer> getConnList() {
		return connList;
	}

	public void setConnList(List<Integer> connList) {
		this.connList = connList;
	}

	public ConnectionDetails() {
		// TODO Auto-generated constructor stub
	}

	public Integer getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Integer connectionId) {
		this.connectionId = connectionId;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
