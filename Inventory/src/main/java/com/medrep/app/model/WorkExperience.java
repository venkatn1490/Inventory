package com.medrep.app.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medrep.app.util.JsonDateDeserializer;
import com.medrep.app.util.JsonDateSerializer;

@JsonInclude(Include.NON_NULL)
public class WorkExperience {
	Integer id;

	String designation;
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@DateTimeFormat(pattern="MMM yyyy")
	Date fromDate;
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@DateTimeFormat(pattern="MMM yyyy")
	Date toDate;
	String hospital;
	String location;
	boolean currentJob;
	String summary;
	public Integer getId() {
		return id;
	}
	public String getDesignation() {
		return designation;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public String getHospital() {
		return hospital;
	}
	public String getLocation() {
		return location;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean getCurrentJob() {
		return currentJob;
	}
	public String getSummary() {
		return summary;
	}
	public void setCurrentJob(boolean currentJob) {
		this.currentJob = currentJob;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}