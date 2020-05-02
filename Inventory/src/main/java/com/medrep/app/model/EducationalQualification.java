package com.medrep.app.model;

public class EducationalQualification {
	Integer id;
	String collegeName;
	String course;
	Double aggregate;
	String degree;
	String specialization;
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	String yearOfPassout;

	public Integer getId() {
		return id;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public String getCourse() {
		return course;
	}

	public Double getAggregate() {
		return aggregate;
	}

	public String getYearOfPassout() {
		return yearOfPassout;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public void setAggregate(Double aggregate) {
		this.aggregate = aggregate;
	}

	public void setYearOfPassout(String yearOfPassout) {
		this.yearOfPassout = yearOfPassout;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}


}