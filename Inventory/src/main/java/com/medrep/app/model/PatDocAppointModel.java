package com.medrep.app.model;

public class PatDocAppointModel implements java.io.Serializable {

	private Integer appointmentId;
	private Integer patientId;
	private Integer doctorId;
	
	private String mobileNo;
	private String emailId;
	private String dateOfBirth;
	private String dpImageUrl;
	private String docImageUrl;
	private String remindTime;
	private String therapeuticName;
	private String patientName;

	private Integer consultingId;
	private String appointmentDate;
	private String appointmentTime;
	private String confirmationNo;
	private String communicationMode;
	private String contactDetails;
	private String appointmentStatus;
	private String followUpFlag;
	private String shareProfile;
	private String consultingType;
	private String doctorName;
	private VideoModel videoModel;
	


	private String imgPrescriptionUrl1;
	private String imgPrescriptionUrl2;


/*	private ChatMessageModel chatModel;
*/
	
/*	private Integer chatId;
*/	
	/*public Integer getChatId() {
		return chatId;
	}
	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}*/
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDpImageUrl() {
		return dpImageUrl;
	}
	public void setDpImageUrl(String dpImageUrl) {
		this.dpImageUrl = dpImageUrl;
	}
	public String getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
	
	public String getDocImageUrl() {
		return docImageUrl;
	}
	public void setDocImageUrl(String docImageUrl) {
		this.docImageUrl = docImageUrl;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getTherapeuticName() {
		return therapeuticName;
	}
	public void setTherapeuticName(String therapeuticName) {
		this.therapeuticName = therapeuticName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	

	public String getConsultingType() {
		return consultingType;
	}
	public void setConsultingType(String consultingType) {
		this.consultingType = consultingType;
	}
	public Integer getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public Integer getConsultingId() {
		return consultingId;
	}
	public void setConsultingId(Integer consultingId) {
		this.consultingId = consultingId;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public String getConfirmationNo() {
		return confirmationNo;
	}
	public void setConfirmationNo(String confirmationNo) {
		this.confirmationNo = confirmationNo;
	}
	public String getCommunicationMode() {
		return communicationMode;
	}
	public void setCommunicationMode(String communicationMode) {
		this.communicationMode = communicationMode;
	}
	public String getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}
	public String getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	public String getFollowUpFlag() {
		return followUpFlag;
	}
	public void setFollowUpFlag(String followUpFlag) {
		this.followUpFlag = followUpFlag;
	}
	public String getShareProfile() {
		return shareProfile;
	}
	public void setShareProfile(String shareProfile) {
		this.shareProfile = shareProfile;
	}
	public VideoModel getVideoModel() {
		return videoModel;
	}
	public void setVideoModel(VideoModel videoModel) {
		this.videoModel = videoModel;
	}
	/*public ChatMessageModel getChatModel() {
		return chatModel;
	}
	public void setChatModel(ChatMessageModel chatModel) {
		this.chatModel = chatModel;
	}*/

	public String getImgPrescriptionUrl1() {
		return imgPrescriptionUrl1;
	}
	public void setImgPrescriptionUrl1(String imgPrescriptionUrl1) {
		this.imgPrescriptionUrl1 = imgPrescriptionUrl1;
	}
	public String getImgPrescriptionUrl2() {
		return imgPrescriptionUrl2;
	}
	public void setImgPrescriptionUrl2(String imgPrescriptionUrl2) {
		this.imgPrescriptionUrl2 = imgPrescriptionUrl2;
	}
	
}
