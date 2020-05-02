package com.medrep.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

	
	@JsonInclude(Include.NON_NULL)
	public class Webinars {
		Integer id;
		String webinarName;
		String year;
		String webinarType;
		String url;
		
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getWebinarName() {
			return webinarName;
		}
		public void setWebinarName(String webinarName) {
			this.webinarName = webinarName;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getWebinarType() {
			return webinarType;
		}
		public void setWebinarType(String webinarType) {
			this.webinarType = webinarType;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		

	}