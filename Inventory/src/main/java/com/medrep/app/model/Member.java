package com.medrep.app.model;

import java.util.List;

public class Member implements java.io.Serializable {

	public int group_id;
	public int member_id;
	public String status;
	public boolean is_admin;
	public List<Integer> memberList;
	public List<Integer> groupList;

	private String firstName;
	private String lastName;
	private String alias;
	private String data;
	private String mimeType;
	private String therapeuticName;
	private String therapeuticDesc;
	private int roleId;
	private String roleName;
	private String stateMedCouncil;
	private String imageUrl;
	

	public List<Integer> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Integer> groupList) {
		this.groupList = groupList;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getStateMedCouncil() {
		return stateMedCouncil;
	}

	public void setStateMedCouncil(String stateMedCouncil) {
		this.stateMedCouncil = stateMedCouncil;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getTherapeuticName() {
		return therapeuticName;
	}

	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}

	public String getTherapeuticDesc() {
		return therapeuticDesc;
	}

	public void setTherapeuticDesc(String therapeuticDesc) {
		this.therapeuticDesc = therapeuticDesc;
	}

	public List<Integer> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Integer> memberList) {
		this.memberList = memberList;
	}

	public List<Doctor> doctor;

	public List<Doctor> getDoctor() {
		return doctor;
	}

	public void setDoctor(List<Doctor> doctor) {
		this.doctor = doctor;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isIs_admin() {
		return is_admin;
	}

	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
