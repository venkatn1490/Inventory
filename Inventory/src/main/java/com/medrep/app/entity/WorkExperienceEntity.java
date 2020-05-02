package com.medrep.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "P_WORKEXPERIENCE", uniqueConstraints = @UniqueConstraint(columnNames = "ID") )
public class WorkExperienceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	Integer id;

	@Column(name = "DESIGNATION")
	String designation;
	@Column(name = "FROM_DATE")
	Date fromDate;
	@Column(name = "TO_DATE")
	Date toDate;
	@Column(name = "HOSPITAL")
	String hospital;
	@Column(name = "LOCATION")
	String location;
	@Column(name = "CURRENT_JOB")
	boolean currentJob;
	@Column(name = "SUMMARY")
	String summary;


	@ManyToOne
	@JoinColumn(name = "DOCTOR_ID")
	DoctorEntity doctorEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public DoctorEntity getDoctorEntity() {
		return doctorEntity;
	}

	public void setDoctorEntity(DoctorEntity doctorEntity) {
		this.doctorEntity = doctorEntity;
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