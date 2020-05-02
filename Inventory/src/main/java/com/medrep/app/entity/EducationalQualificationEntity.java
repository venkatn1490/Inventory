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

@Entity
@Table(name = "P_EDU_QUALIFICAION", uniqueConstraints = @UniqueConstraint(columnNames = "ID") )
public class EducationalQualificationEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	Integer id;
	@Column(name = "COLLEGE_NAME")
	String collegeName;
	@Column(name = "COURSE")
	String course;
	@Column(name="DEGREE")
	String degree;
	@Column(name="AGGREGATE")
	Double aggregate;
	@Column(name="YEAR_OF_PASSOUT")
	String yearOfPassout;
	@Column(name="SPECIALIZATION")
	String specialization;
	@ManyToOne
	@JoinColumn(name = "DOCTOR_ID")
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

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public Double getAggregate() {
		return aggregate;
	}

	public void setAggregate(Double aggregate) {
		this.aggregate = aggregate;
	}

	public String getYearOfPassout() {
		return yearOfPassout;
	}

	public void setYearOfPassout(String yearOfPassout) {
		this.yearOfPassout = yearOfPassout;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

}