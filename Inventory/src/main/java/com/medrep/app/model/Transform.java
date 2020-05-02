package com.medrep.app.model;

import java.io.Serializable;
import java.util.Date;

public class Transform implements Serializable{
	
	private int transform_id;
	private String content;
	private String content_type;
	private Date posted_date;
	private String source;
	private String category;
	private String source_url;
	private String thumbnail;
	private String actual;
	
	public Transform() {
		// TODO Auto-generated constructor stub
	}

	public int getTransform_id() {
		return transform_id;
	}

	public void setTransform_id(int transform_id) {
		this.transform_id = transform_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public Date getPosted_date() {
		return posted_date;
	}

	public void setPosted_date(Date posted_date) {
		this.posted_date = posted_date;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}	

}
