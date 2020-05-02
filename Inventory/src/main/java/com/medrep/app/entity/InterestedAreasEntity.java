package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name = "P_INTERESTED_AREAS", uniqueConstraints = @UniqueConstraint(columnNames = "ID") )
@JsonRootName(value = "interests")
public class InterestedAreasEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	Integer id;
	@Column(name = "NAME")
	String name;

	@ManyToOne
	@JoinColumn(name = "DOCTOR_ID")
	@JsonIgnore
	DoctorEntity doctorEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DoctorEntity getDoctorEntity() {
		return doctorEntity;
	}

	public void setDoctorEntity(DoctorEntity doctorEntity) {
		this.doctorEntity = doctorEntity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}