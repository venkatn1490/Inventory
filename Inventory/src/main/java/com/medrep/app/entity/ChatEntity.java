package com.medrep.app.entity;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.medrep.app.model.ChatMessages;


/**
 * TArea generated by hbm2java
 */

@Entity
@Table(name = "M_CHAT_MESSAGES")
public class ChatEntity implements java.io.Serializable{
private Integer chatId;


private Integer doctorId;
private Integer patientId;
private Date  createdDate;
private String chatType;
private String patStatus;
private String docStatus;
private Integer appointmentId;
 private ChatMessages chatMessages;



/*protected ChatMessageModel2 messages;
*/
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CHAT_MESSAGE_ID", nullable = false)
	public Integer getChatId() {
		return chatId;
	}
	public void setChatId(Integer chatId) {
		this.chatId = chatId;
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
	
	@Column(name = "CHAT_TEXT_TYPE")	
	public String getChatType() {
		return chatType;
	}
	public void setChatType(String chatType) {
		this.chatType = chatType;
	}
	@Column(name = "CREATED_DATE")	

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
		@Column(name="CHAT_MESSAGE")
		@Type(type = "com.medrep.app.util.MyJsonType")
		public ChatMessages getChatMessages() {
			return chatMessages;
		}

		public void setChatMessages(ChatMessages chatMessages) {
			this.chatMessages = chatMessages;
		}
		
		@Column(name = "PATSTATUS")

		public String getPatStatus() {
			return patStatus;
		}

		public void setPatStatus(String patStatus) {
			this.patStatus = patStatus;
		}
		@Column(name = "DOCSTATUS")

		public String getDocStatus() {
			return docStatus;
		}

		public void setDocStatus(String docStatus) {
			this.docStatus = docStatus;
		}
		@Column(name = "APPOINTMENT_ID")	
		public Integer getAppointmentId() {
			return appointmentId;
		}
		public void setAppointmentId(Integer appointmentId) {
			this.appointmentId = appointmentId;
		}
}
