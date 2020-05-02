package com.medrep.app.model;

import java.util.Set;
import java.util.List;
public class Company implements java.io.Serializable {
	
	private Integer companyId;
	private String companyName;
	private String companyDesc;
	private String contactName;
	private String contactNo;
	private DisplayPicture displayPicture;
	private Location location;
	private String companyUrl;
	private String status;  
	
	private String companylogourl;
	private Set<TherapeuticArea> therapeuticAreas;
	private List<TherapeuticArea> therapeuticAresIU;

	public String getCompanylogourl() {
		return companylogourl;
	}

	public void setCompanylogourl(String companylogourl) {
		this.companylogourl = companylogourl;
	}

	public List<TherapeuticArea> getTherapeuticAresIU() {
		return therapeuticAresIU;
	}

	public void setTherapeuticAresIU(List<TherapeuticArea> therapeuticAresIU) {
		this.therapeuticAresIU = therapeuticAresIU;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyDesc() {
		return companyDesc;
	}
	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public Set<TherapeuticArea> getTherapeuticAreas() {
		return therapeuticAreas;
	}

	public void setTherapeuticAreas(Set<TherapeuticArea> therapeuticAreas) {
		this.therapeuticAreas = therapeuticAreas;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public DisplayPicture getDisplayPicture() {
		return displayPicture;
	}

	public void setDisplayPicture(DisplayPicture displayPicture) {
		this.displayPicture = displayPicture;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}	
	
	/*public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getDpId() {
		return dpId;
	}

	public void setDpId(Integer dpId) {
		this.dpId = dpId;
	}	*/

}
