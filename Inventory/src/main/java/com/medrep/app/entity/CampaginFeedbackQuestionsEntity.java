package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.json.simple.JSONArray;

@Entity
@Table(name = "P_CAMPAIGN_FEEDBACK_QUESTIONS", uniqueConstraints = @UniqueConstraint(columnNames = "QUESTION_ID") )
public class CampaginFeedbackQuestionsEntity implements java.io.Serializable{

	private Integer campaignID;
	private Integer questionId;
	private String questionType;
	private String questionText;
	private JSONArray questionOptionJSON;
	private Date createdDate;
	
	@Column(name = "CAMPAIGN_ID")

	public Integer getCampaignID() {
		return campaignID;
	}
	public void setCampaignID(Integer campaignID) {
		this.campaignID = campaignID;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "QUESTION_ID", nullable = false)
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	@Column(name = "QUESTION_TYPE")

	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	@Column(name = "QUESTION_TEXT")

	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	@Column(name = "QUESTION_OPTIONS")

	public JSONArray getQuestionOptionJSON() {
		return questionOptionJSON;
	}
	public void setQuestionOptionJSON(JSONArray questionOptionJSON) {
		this.questionOptionJSON = questionOptionJSON;
	}
	@Column(name = "CREATED_DATE")

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
