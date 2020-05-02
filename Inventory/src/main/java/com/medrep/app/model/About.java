package com.medrep.app.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class About  implements Serializable{
Integer id;
Integer doctorId;
String name;
String location;
String designation;
Integer workExpMonth;
Integer workExpYear;
public Integer getWorkExpMonth() {
	return workExpMonth;
}
public void setWorkExpMonth(Integer workExpMonth) {
	this.workExpMonth = workExpMonth;
}
public Integer getWorkExpYear() {
	return workExpYear;
}
public void setWorkExpYear(Integer workExpYear) {
	this.workExpYear = workExpYear;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getDoctorId() {
	return doctorId;
}
public void setDoctorId(Integer doctorId) {
	this.doctorId = doctorId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}


}
