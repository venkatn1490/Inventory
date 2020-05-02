package com.medrep.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Publication {
	Integer id;
	String articleName;
	String year;
	String publication;
	String url;
	public Integer getId() {
		return id;
	}
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getArticleName() {
		return articleName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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