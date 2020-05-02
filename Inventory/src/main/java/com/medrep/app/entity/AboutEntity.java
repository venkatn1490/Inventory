package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "P_ABOUT", uniqueConstraints = @UniqueConstraint(columnNames = "ID") )
@Deprecated
public class AboutEntity {
@Id
@GeneratedValue(strategy = IDENTITY)
@Column(name="ID")
Integer id;
@Column(name="NAME")
String name;
@Column(name="LOCATION")
String location;
@Column(name="DESIGNATION")
String designation;
@OneToOne
@JoinColumn(name="DOCTOR_ID")
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
