package com.medrep.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class ChatMessageModel implements Serializable {

	
		private Integer chatId;
		private Integer doctorId;
		
		private Integer patientId;
		private Integer appointmentId;
		private String imgName;
		protected ChatMessages chatMessages;
		private String patStatus;
		private String docStatus;

		public Integer getDoctorId() {
			return doctorId;
		}
		public void setDoctorId(Integer doctorId) {
			this.doctorId = doctorId;
		}
		public Integer getPatientId() {
			return patientId;
		}
		public void setPatientId(Integer patientId) {
			this.patientId = patientId;
		}
		
		
		
		
		public Integer getChatId() {
			return chatId;
		}
		public void setChatId(Integer chatId) {
			this.chatId = chatId;
		}
		
		public ChatMessages getChatMessages() {
			return chatMessages;
		}

		public void setChatMessages(ChatMessages chatMessages) {
			this.chatMessages = chatMessages;
		}
		public String getPatStatus() {
			return patStatus;
		}

		public void setPatStatus(String patStatus) {
			this.patStatus = patStatus;
		}

		public String getDocStatus() {
			return docStatus;
		}

		public void setDocStatus(String docStatus) {
			this.docStatus = docStatus;
		}
		public Integer getAppointmentId() {
			return appointmentId;
		}
		public void setAppointmentId(Integer appointmentId) {
			this.appointmentId = appointmentId;
		}
		public String getImgName() {
			return imgName;
		}
		public void setImgName(String imgName) {
			this.imgName = imgName;
		}
}