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
@Table(name = "P_PUBLICATION", uniqueConstraints = @UniqueConstraint(columnNames = "ID") )
public class PublicationEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	Integer id;
	@Column(name = "ARTICLE_NAME")
	String articleName;
	@Column(name="PUBLICATION")
	String publication;
	@Column(name="YEAR")
	String year;
	@Column(name="URL")
	String url;
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

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

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public DoctorEntity getDoctorEntity() {
		return doctorEntity;
	}

	public void setDoctorEntity(DoctorEntity doctorEntity) {
		this.doctorEntity = doctorEntity;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}