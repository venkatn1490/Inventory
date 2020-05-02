package com.medrep.app.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PostMessage implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int message_id;
	private int member_id;
	private int group_id;
	private String message;
	private String message_type;
	private Date post_date;
	private int receiver_id;
	private int topic_id;
	private String fileName;
	private String fileData;
	private Integer[] groupId;
	private Integer[] receiverId;
	Integer postType;
	private String doctor_Name;
	private String displayPicture;


	public String getDoctor_Name() {
		return doctor_Name;
	}
	public void setDoctor_Name(String doctor_Name) {
		this.doctor_Name = doctor_Name;
	}
	public void setPostType(Integer postType) {
		this.postType = postType;
	}
	public Integer getPostType() {
		return postType;
	}
	public String getFileName() {
		return fileName;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

	private String fileUrl;

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	private String share_date;

	public int getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(int topic_id) {
		this.topic_id = topic_id;
	}

	public PostMessage() {
		// TODO Auto-generated constructor stub
	}

	public String getShare_date() {
		return share_date;
	}

	public void setShare_date(String share_date) {
		this.share_date = share_date;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public Date getPost_date() {
		return post_date;
	}

	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}

	public int getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(int receiver_id) {
		this.receiver_id = receiver_id;
	}

	public Integer[] getGroupId() {
		return groupId;
	}


	public void setGroupId(Integer[] groupId) {
		this.groupId = groupId;
	}

	public Integer[] getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer[] receiverId) {
		this.receiverId = receiverId;
	}
	public String getDisplayPicture() {
		return displayPicture;
	}
	public void setDisplayPicture(String displayPicture) {
		this.displayPicture = displayPicture;
	}

}
