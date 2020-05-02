package com.medrep.app.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class News implements java.io.Serializable {

	private Integer newsId;
	private String title;
	private String tagDesc;
	private String newsDesc;
	private Integer sourceId;
	private String sourceName;
	private Integer therapeuticId;
	private String therapeuticName;
	private String postUrl;
	private String coverImgUrl;
	private String innerImgUrl;
	private String videoUrl;
	private Integer userId;
	private String createdOn;

	private MultipartFile coverImgFile;
	private MultipartFile innerImgFile;
	private MultipartFile videoFile;
	private int contentType;


	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTagDesc() {
		return tagDesc;
	}

	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}

	public String getNewsDesc() {
		return newsDesc;
	}

	public void setNewsDesc(String newsDesc) {
		this.newsDesc = newsDesc;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public String getCoverImgUrl() {
		return coverImgUrl;
	}

	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}

	public String getInnerImgUrl() {
		return innerImgUrl;
	}

	public void setInnerImgUrl(String innerImgUrl) {
		this.innerImgUrl = innerImgUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getTherapeuticId() {
		return therapeuticId;
	}

	public void setTherapeuticId(Integer therapeuticId) {
		this.therapeuticId = therapeuticId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getTherapeuticName() {
		return therapeuticName;
	}

	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}

	public MultipartFile getCoverImgFile() {
		return coverImgFile;
	}

	public void setCoverImgFile(MultipartFile coverFile) {
		this.coverImgFile = coverFile;
	}

	public MultipartFile getInnerImgFile() {
		return innerImgFile;
	}

	public void setInnerImgFile(MultipartFile innerFile) {
		this.innerImgFile = innerFile;
	}

	public MultipartFile getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(MultipartFile videoFile) {
		this.videoFile = videoFile;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}
}