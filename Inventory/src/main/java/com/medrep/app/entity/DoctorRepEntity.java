package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * TDoctor generated by hbm2java
 */
@Entity
@Table(name = "T_DOCTOR_REP", uniqueConstraints = @UniqueConstraint(columnNames = "DOCTOR_REP_ID") )
public class DoctorRepEntity implements java.io.Serializable{

	private Integer doctorRepId;
	private DoctorEntity doctor;
	private PharmaRepEntity rep;
	private String status;
	private Date createdOn;
	private String createdBy;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "DOCTOR_REP_ID", nullable = false)
	public Integer getDoctorRepId() {
		return doctorRepId;
	}
	public void setDoctorRepId(Integer doctorRepId) {
		this.doctorRepId = doctorRepId;
	}
	
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="DOCTOR_ID",nullable=false,unique=true)
	public DoctorEntity getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="REP_ID",nullable=false,unique=true)
	public PharmaRepEntity getRep() {
		return rep;
	}
	public void setRep(PharmaRepEntity rep) {
		this.rep = rep;
	}
	
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON", length = 19)
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
}
