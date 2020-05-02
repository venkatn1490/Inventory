package com.medrep.app.model;

import java.io.Serializable;
import java.util.List;

public class UserDetails implements Serializable{

	private Integer userId;
	private String firstName;
	private String lastName;
	private String status;
	private String therapeuticArea;
	private String city;
	private Integer roleId;
	private String dPicture;
	private Integer doctorId;
	private String therapeuticName;
	private Integer contactId;
	private ConnectionDetails pendingDetails;
	private List<ConnectionDetails> listOfPendingDetails;
	private List<Doctor> doctorDetails;
	private String connStatus;
	private Integer pendingConnections;


	public Integer getPendingConnections() {
		return pendingConnections;
	}

	public void setPendingConnections(Integer pendingConnections) {
		this.pendingConnections = pendingConnections;
	}

	public String getConnStatus() {
		return connStatus;
	}

	public void setConnStatus(String connStatus) {
		this.connStatus = connStatus;
	}

	public UserDetails() {
		// TODO Auto-generated constructor stub
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTherapeuticArea() {
		return therapeuticArea;
	}

	public void setTherapeuticArea(String therapeuticArea) {
		this.therapeuticArea = therapeuticArea;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getdPicture() {
		return dPicture;
	}

	public void setdPicture(String dPicture) {
		this.dPicture = dPicture;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getTherapeuticName() {
		return therapeuticName;
	}

	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public ConnectionDetails getPendingDetails() {
		return pendingDetails;
	}

	public void setPendingDetails(ConnectionDetails pendingDetails) {
		this.pendingDetails = pendingDetails;
	}

	public List<ConnectionDetails> getListOfPendingDetails() {
		return listOfPendingDetails;
	}

	public void setListOfPendingDetails(List<ConnectionDetails> listOfPendingDetails) {
		this.listOfPendingDetails = listOfPendingDetails;
	}

	public List<Doctor> getDoctorDetails() {
		return doctorDetails;
	}

	public void setDoctorDetails(List<Doctor> doctorDetails) {
		this.doctorDetails = doctorDetails;
	}

}
