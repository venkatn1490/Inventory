package com.medrep.app.model;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1



import java.util.ArrayList;
import java.util.Collection;


public class User extends UserSecurityContext implements java.io.Serializable {
	private static final long serialVersionUID = 2542911871310426635L;

	private Integer roleId;
	private String roleName;
	private String firstName;
	private String middleName;
	private String lastName;
	private String alias;
	private String title;
	private String phoneNo;
	private String mobileNo;
	private String emailId;
	private String alternateEmailId;
	private DisplayPicture profilePicture;
	private String status;
	private String displayName;
	private Integer userId;
	private String dPicture;
	public Integer getUserId() {
		return userId;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	private Integer userSecurityId;
	private Location location;



	public Integer getUserSecurityId() {
		return userSecurityId;
	}
	public void setUserSecurityId(Integer userSecurityId) {
		this.userSecurityId = userSecurityId;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	private Collection<Location> locations = new ArrayList<Location>();


	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAlternateEmailId() {
		return alternateEmailId;
	}
	public void setAlternateEmailId(String alternateEmailId) {
		this.alternateEmailId = alternateEmailId;
	}
	public Collection<Location> getLocations() {
		return locations;
	}
	public void setLocations(Collection<Location> locations) {
		this.locations = locations;
	}
	public DisplayPicture getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(DisplayPicture profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getdPicture() {
		return dPicture;
	}
	public void setdPicture(String dPicture) {
		this.dPicture = dPicture;
	}
	public String getDisplayName() {
		displayName="";
		if(this.title!=null)
			displayName+=this.title;
		if(this.firstName!=null)
			displayName=(displayName.isEmpty()?"":" ")+this.firstName;
		if(this.lastName!=null)
			displayName=displayName+" "+lastName;

		return displayName;
	}

}
