package com.medrep.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "P_PATIENT_CONTACTS", uniqueConstraints = @UniqueConstraint(columnNames = "PATIENT_ID"))
public class PatientContactEntity {
	
	
	private Integer contactId;
	private Integer patientId;
	private String contacType;
	private String contactNumber;
	private Integer priority;
	private String contactPersonName;
	private String city;
	private String facilityName;
	private String relattionShipToPatient;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONTACT_ID")	
	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	@Column(name="PATIENT_ID")
	
	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	@Column(name="CONTACT_TYPE")
	public String getContacType() {
		return contacType;
	}

	public void setContacType(String contacType) {
		this.contacType = contacType;
	}
	@Column(name="CONTACT_NUMBER")
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	@Column(name="PRIORITY")
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name="CONTACT_PERSON_NAME")
	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	@Column(name="CITY")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="FACILITY_NAME")
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	@Column(name="RELATIONSHIP_TO_PATIENT")
	public String getRelattionShipToPatient() {
		return relattionShipToPatient;
	}
	public void setRelattionShipToPatient(String relattionShipToPatient) {
		this.relattionShipToPatient = relattionShipToPatient;
	}	

}
