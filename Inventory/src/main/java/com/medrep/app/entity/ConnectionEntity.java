package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "T_CONNECTIONS")
public class ConnectionEntity implements java.io.Serializable {

	private int connID;
	private int docID;
	private String status;
	public int id;
	
	public ConnectionEntity() {
		
	}
	
	@Id
	 @GeneratedValue(strategy = IDENTITY)
	
	@Column(name = "Id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "CONNECTION_ID", nullable = false)
	public int getConnID() {
		return connID;
	}

	public void setConnID(int connID) {
		this.connID = connID;
	}

	@Column(name = "DOCTOR_ID", nullable = false)
	public int getDocID() {
		return docID;
	}

	public void setDocID(int docID) {
		this.docID = docID;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
