package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "P_CAMPAIGN_STATS",uniqueConstraints = { 
		@UniqueConstraint(columnNames = "CAMPAIGN_ID") })
public class PharmaCampaignStatsEntity implements java.io.Serializable{

	

		private Integer campaignStatId;
		private Integer doctorId;
		private Integer patientId;
		private Date sentDate;
		private String status;
		
		private String regStatus;
		private PharmaCampaginEntity pharmaCampaginEntity;
		@Id
		@GeneratedValue(strategy = IDENTITY)
		
		@Column(name = "CAMPAIGNSTATS_ID", nullable = false)
		public Integer getCampaignStatId() {
			return campaignStatId;
		}
		public void setCampaignStatId(Integer campaignStatId) {
			this.campaignStatId = campaignStatId;
		}
		
		@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	    @JoinColumn(name="CAMPAIGN_ID",nullable=false,unique=true)
		public PharmaCampaginEntity getPharmaCampaginEntity() {
			return pharmaCampaginEntity;
		}
		public void setPharmaCampaginEntity(PharmaCampaginEntity pharmaCampaginEntity) {
			this.pharmaCampaginEntity = pharmaCampaginEntity;
		}
		
		
		@Column(name = "DOCTOR_ID")	
		public Integer getDoctorId() {
			return doctorId;
		}
		public void setDoctorId(Integer doctorId) {
			this.doctorId = doctorId;
		}
		@Column(name = "PATIENT_ID")
		public Integer getPatientId() {
			return patientId;
		}
		public void setPatientId(Integer patientId) {
			this.patientId = patientId;
		}
		@Column(name = "SENT_DATE")
	
		public Date getSentDate() {
			return sentDate;
		}
		public void setSentDate(Date sentDate) {
			this.sentDate = sentDate;
		}
		@Column(name = "STATUS")
	
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	
}
