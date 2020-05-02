package com.medrep.app.model;

import java.io.Serializable;
import java.util.List;

public class Contacts implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int connID;
	private int docID;
	private String status;	
	List<Doctor> doctor;
	
	List<Integer> connIdList;
	
	
	public List<Integer> getConnIdList() {
		return connIdList;
	}
	public void setConnIdList(List<Integer> connIdList) {
		this.connIdList = connIdList;
	}
	public int getConnID() {
		return connID;
	}
	public void setConnID(int connID) {
		this.connID = connID;
	}
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Doctor> getDoctor() {
		return doctor;
	}
	public void setDoctor(List<Doctor> doctor) {
		this.doctor = doctor;
	}	

}
