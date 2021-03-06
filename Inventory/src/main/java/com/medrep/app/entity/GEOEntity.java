package com.medrep.app.entity;

// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TZone generated by Suresh Pinnaka.
 */
@Entity
@Table(name = "T_M_GEO", uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
public class GEOEntity implements java.io.Serializable {

	private int id;
	private String stateCode;
	private String stateName;
	private String cityName;

	public GEOEntity() {

	}

	public GEOEntity(int id) {
		this.id = id;
	}

	public GEOEntity(String stateCode, String stateName, String cityName) {
		this.stateCode = stateCode;
		this.stateName = stateName;
		this.cityName = cityName;
	}
	
	@Id

	@Column(name = "ID", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "STATE_CODE")
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@Column(name = "STATE_NAME")
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Column(name = "CITY_NAME")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
