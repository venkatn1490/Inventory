package com.medrep.app.entity;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TArea generated by hbm2java
 */
@Entity
@Table(name = "T_NEWS", uniqueConstraints = @UniqueConstraint(columnNames = "ID") )
public class NewsEntity implements java.io.Serializable {

	private Integer newsId;
	private String title;
	private String tagDesc;
	private String newsDesc;
	private Integer sourceId;
	private Integer therapeuticId;
	private String postUrl;
	private String coverImgUrl;
	private String innerImgUrl;
	private String videoUrl;
	private Integer userId;
	private Date createdOn;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false)
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "TAG_DESC")
	public String getTagDesc() {
		return tagDesc;
	}
	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}
	
	@Column(name = "NEWS_DESC")
	public String getNewsDesc() {
		return newsDesc;
	}
	public void setNewsDesc(String newsDesc) {
		this.newsDesc = newsDesc;
	}
	
	@Column(name = "SOURCE_ID")
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	@Column(name = "POST_URL")
	public String getPostUrl() {
		return postUrl;
	}
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}
	
	@Column(name = "COVER_IMG_URL")
	public String getCoverImgUrl() {
		return coverImgUrl;
	}
	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}
	
	@Column(name = "INNER_IMG_URL")
	public String getInnerImgUrl() {
		return innerImgUrl;
	}
	public void setInnerImgUrl(String innerImgUrl) {
		this.innerImgUrl = innerImgUrl;
	}
	
	@Column(name = "VIDEO_URL")
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	@Column(name = "CREATED_BY")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "CREATED_ON")
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	@Column(name = "THERAPEUTIC_ID")
	public Integer getTherapeuticId() {
		return therapeuticId;
	}
	public void setTherapeuticId(Integer therapeuticId) {
		this.therapeuticId = therapeuticId;
	}
	

}
