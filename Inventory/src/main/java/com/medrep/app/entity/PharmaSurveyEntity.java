package com.medrep.app.entity;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TDoctorSurvey generated by hbm2java
 */
@Entity
@Table(name = "T_REP_SURVEY", uniqueConstraints = { @UniqueConstraint(columnNames = "REP_ID"),
		@UniqueConstraint(columnNames = "SURVEY_ID") })
public class PharmaSurveyEntity implements java.io.Serializable {

	private Integer repSurveyId;
	private Integer repId;
	private String status;
	private String createdOn;
	private SurveyEntity survey;




	public PharmaSurveyEntity() {
	}

	public PharmaSurveyEntity(Integer repId) {
		this.repId = repId;
	}

	public PharmaSurveyEntity(int repSurveyId, Integer repId, String status, String createdOn) {
		this.repSurveyId = repSurveyId;
		this.repId = repId;
		this.status = status;
		this.createdOn = createdOn;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REP_SURVEY_ID", nullable = false)
	public Integer getRepSurveyId() {
		return this.repSurveyId;
	}

	public void setRepSurveyId(Integer repSurveyId) {
		this.repSurveyId = repSurveyId;
	}


	@Column(name = "REP_ID", unique = true)
	public Integer getRepId() {
		return this.repId;
	}

	public void setRepId(Integer repId) {
		this.repId = repId;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATED_ON")
	public String getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="SURVEY_ID",nullable=false,unique=false)
	public SurveyEntity getSurvey() {
		return survey;
	}

	public void setSurvey(SurveyEntity survey) {
		this.survey = survey;
	}
}
