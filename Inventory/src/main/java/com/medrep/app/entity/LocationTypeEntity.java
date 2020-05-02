package com.medrep.app.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "T_LOCATION_TYPES")
public class LocationTypeEntity {

	private Integer locationtypeId;
	private String locationType;
	
	public LocationTypeEntity(Integer locationtypeId, String locationType) {
		super();
		this.locationtypeId = locationtypeId;
		this.locationType = locationType;
	}

	public LocationTypeEntity() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID_LOCATION_TYPE", nullable = false)
	public Integer getLocationtypeId() {
		return locationtypeId;
	}

	public void setLocationtypeId(Integer locationtypeId) {
		this.locationtypeId = locationtypeId;
	}
	@Column(name = "NAME")
	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	
	
	
}
