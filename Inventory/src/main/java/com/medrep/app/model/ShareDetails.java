package com.medrep.app.model;

import java.util.Date;
import java.util.List;

public class ShareDetails implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6578248881910208558L;
	private Integer id;
	private Integer topic_id;
	private Integer doctor_id;
	private String doctor_Name;
	private Integer likes_count;
	private Integer comment_count;
	private Integer share_count;
	private Date posted_on;
	private String content_type;
	private String detail_desc;
	private String short_desc;
	private String source;
	private String title_desc;
	private Integer transform_post_id;
	private String url;
	private Boolean like;
	public String getShareMesssage() {
		return shareMesssage;
	}

	public void setShareMesssage(String shareMesssage) {
		this.shareMesssage = shareMesssage;
	}
	private String shareMesssage;
	private Likes likes;
	private Comments comments;
	private PostMessage postMessage;
	private List messages;

	Integer group_id;
	Integer contact_id;

	private String displayPicture;
	private Doctor doctor;
	private UserDetails userDetails;


	public String getShare_comment_last() {
		return share_comment_last;
	}
	private String therapeutic_area;
	public String getTherapeutic_area() {
		return therapeutic_area;
	}

	public void setTherapeutic_area(String therapeutic_area) {
		this.therapeutic_area = therapeutic_area;
	}
	public void setShare_comment_last(String share_comment_last) {
		this.share_comment_last = share_comment_last;
	}

	public String getShare_comment_doctor_therapeutic_area() {
		return share_comment_doctor_therapeutic_area;
	}

	public void setShare_comment_doctor_therapeutic_area(
			String share_comment_doctor_therapeutic_area) {
		this.share_comment_doctor_therapeutic_area = share_comment_doctor_therapeutic_area;
	}

	public String getShare_comment_doctorname() {
		return share_comment_doctorname;
	}

	public void setShare_comment_doctorname(String share_comment_doctorname) {
		this.share_comment_doctorname = share_comment_doctorname;
	}

	private String share_comment_last;
	private String share_comment_doctor_therapeutic_area;
	private String share_comment_doctorname;
	
	public String getShare_doctor_connection() {
		return share_doctor_connection;
	}

	public void setShare_doctor_connection(String share_doctor_connection) {
		this.share_doctor_connection = share_doctor_connection;
	}
	private String share_doctor_connection;

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getDoctor_Name() {
		return doctor_Name;
	}

	public void setDoctor_Name(String doctor_Name) {
		this.doctor_Name = doctor_Name;
	}

	public PostMessage getPostMessage() {
		return postMessage;
	}

	public void setPostMessage(PostMessage postMessage) {
		this.postMessage = postMessage;
	}

	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}

	public Likes getLikes() {
		return likes;
	}

	public void setLikes(Likes likes) {
		this.likes = likes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(Integer topic_id) {
		this.topic_id = topic_id;
	}

	public Integer getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(Integer doctor_id) {
		this.doctor_id = doctor_id;
	}

	public Integer getLikes_count() {
		return likes_count;
	}

	public void setLikes_count(Integer likes_count) {
		this.likes_count = likes_count;
	}

	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}

	public Integer getShare_count() {
		return share_count;
	}

	public void setShare_count(Integer share_count) {
		this.share_count = share_count;
	}

	public Date getPosted_on() {
		return posted_on;
	}

	public void setPosted_on(Date posted_on) {
		this.posted_on = posted_on;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getDetail_desc() {
		return detail_desc;
	}

	public void setDetail_desc(String detail_desc) {
		this.detail_desc = detail_desc;
	}

	public String getShort_desc() {
		return short_desc;
	}

	public void setShort_desc(String short_desc) {
		this.short_desc = short_desc;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitle_desc() {
		return title_desc;
	}

	public void setTitle_desc(String title_desc) {
		this.title_desc = title_desc;
	}

	public Integer getTransform_post_id() {
		return transform_post_id;
	}

	public void setTransform_post_id(Integer transform_post_id) {
		this.transform_post_id = transform_post_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDisplayPicture() {
		return displayPicture;
	}

	public void setDisplayPicture(String displayPicture) {
		this.displayPicture = displayPicture;
	}

	public Integer getGroup_id() {
		return group_id;
	}

	public Integer getContact_id() {
		return contact_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public void setContact_id(Integer contact_id) {
		this.contact_id = contact_id;
	}

	public Boolean isLike() {
		return like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}

	public List getMessages() {
		return messages;
	}

	public void setMessages(List messages) {
		this.messages = messages;
	}

}