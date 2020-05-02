package com.venkat.app.model;

import java.io.Serializable;


public class LoginResponseModel implements Serializable {
	 protected static final long serialVersionUID = -1740566795590994197L;
	 private Integer userId;
	 private String status;
	 private Integer empId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	 
}
