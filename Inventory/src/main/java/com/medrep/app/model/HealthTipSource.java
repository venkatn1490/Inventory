

package com.medrep.app.model;

import java.util.Date;

public class HealthTipSource {
	private Integer healthtipId;
	
	private String healthtipSourceName;
	private String healthtipSourceUrl;	
	private Integer userId;
	/**
	 * @return the transformId
	 */
	public Integer getHealthtipId() {
		return healthtipId;
	}
	public void setHealthtipId(Integer healthtipId) {
		this.healthtipId = healthtipId;
	}
	public String getHealthtipSourceName() {
		return healthtipSourceName;
	}
	public void setHealthtipSourceName(String healthtipSourceName) {
		this.healthtipSourceName = healthtipSourceName;
	}
	public String getHealthtipSourceUrl() {
		return healthtipSourceUrl;
	}
	public void setHealthtipSourceUrl(String healthtipSourceUrl) {
		this.healthtipSourceUrl = healthtipSourceUrl;
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

