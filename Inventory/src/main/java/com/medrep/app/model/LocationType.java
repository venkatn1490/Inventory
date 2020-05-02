package com.medrep.app.model;

public class LocationType implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer locationtypeId;
	private String locationType;
	
	public LocationType(Integer locationtypeId,String locationType)
	{
		this.locationtypeId=locationtypeId;
		this.locationType=locationType;
	}

	public LocationType() {
		// TODO Auto-generated constructor stub
	}

	public Integer getLocationtypeId() {
		return locationtypeId;
	}

	public void setLocationtypeId(Integer locationtypeId) {
		this.locationtypeId = locationtypeId;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	
	
}
