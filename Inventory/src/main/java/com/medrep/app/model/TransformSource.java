package com.medrep.app.model;

import java.util.Date;

public class TransformSource {
	private Integer transformId;
	private String transformSourceName;
	private String transformSourceUrl;	
	private Integer userId;
	/**
	 * @return the transformId
	 */
	public Integer getTransformId() {
		return transformId;
	}
	/**
	 * @param transformId the transformId to set
	 */
	public void setTransformId(Integer transformId) {
		this.transformId = transformId;
	}
	/**
	 * @return the transformSourceName
	 */
	public String getTransformSourceName() {
		return transformSourceName;
	}
	/**
	 * @param transformSourceName the transformSourceName to set
	 */
	public void setTransformSourceName(String transformSourceName) {
		this.transformSourceName = transformSourceName;
	}
	/**
	 * @return the transformSourceUrl
	 */
	public String getTransformSourceUrl() {
		return transformSourceUrl;
	}
	/**
	 * @param transformSourceUrl the transformSourceUrl to set
	 */
	public void setTransformSourceUrl(String transformSourceUrl) {
		this.transformSourceUrl = transformSourceUrl;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
