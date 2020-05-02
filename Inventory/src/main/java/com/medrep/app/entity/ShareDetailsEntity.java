package com.medrep.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_POST_TOPIC")
public class ShareDetailsEntity implements java.io.Serializable {

	private Integer id;
	private Integer doctor_id;
	private Integer likes_count;
	private Integer comment_count;
	private Integer share_count;
	private Date posted_on;
	private String content_type;
	private String detail_desc;
	private String short_desc;
	private PostTypeEntity source;
	private String title_desc;
	private Integer transform_post_id;
	private String url;
	private Integer receiverId;
	private Integer groupId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "DOCTOR_ID")
	public Integer getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(Integer doctor_id) {
		this.doctor_id = doctor_id;
	}

	@Column(name = "LIKES_COUNT")
	public Integer getLikes_count() {
		return likes_count;
	}

	public void setLikes_count(Integer likes_count) {
		this.likes_count = likes_count;
	}

	@Column(name = "COMMENT_COUNT")
	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}

	@Column(name = "SHARE_COUNT")
	public Integer getShare_count() {
		return share_count;
	}

	public void setShare_count(Integer share_count) {
		this.share_count = share_count;
	}

	@Column(name = "POSTED_ON")
	public Date getPosted_on() {
		return posted_on;
	}

	public void setPosted_on(Date posted_on) {
		this.posted_on = posted_on;
	}

	@Column(name = "CONTENT_TYPE")
	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	@Column(name = "DETAIL_DESC")
	public String getDetail_desc() {
		return detail_desc;
	}

	public void setDetail_desc(String detail_desc) {
		this.detail_desc = detail_desc;
	}

	@Column(name = "SHORT_DESC")
	public String getShort_desc() {
		return short_desc;
	}

	public void setShort_desc(String short_desc) {
		this.short_desc = short_desc;
	}

	@ManyToOne
	@JoinColumn(name="SOURCE")
	public PostTypeEntity getSource() {
		return source;
	}

	public void setSource(PostTypeEntity source) {
		this.source = source;
	}

	@Column(name = "TITLE_DESC")
	public String getTitle_desc() {
		return title_desc;
	}

	public void setTitle_desc(String title_desc) {
		this.title_desc = title_desc;
	}

	@Column(name = "TRANSFORM_POST_ID")
	public Integer getTransform_post_id() {
		return transform_post_id;
	}

	public void setTransform_post_id(Integer transform_post_id) {
		this.transform_post_id = transform_post_id;
	}

	@Column(name = "URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="RECEIVER_ID")
	public Integer getReceiverId() {
		return receiverId;
	}

	@Column(name="GROUP_ID")
	public Integer getGroupId() {
		return groupId;
	}


	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}



}
