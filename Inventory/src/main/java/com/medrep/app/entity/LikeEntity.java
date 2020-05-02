package com.medrep.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_LIKES")
public class LikeEntity {

	private int id;
	private int user_id;
	private String like_status;
	private int message_id;
	private Date like_Time;
	
	public LikeEntity() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="USER_ID")
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Column(name="MESSAGE_ID")
	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	@Column(name="LIKE_TIME")
	public Date getLike_Time() {
		return like_Time;
	}

	public void setLike_Time(Date like_Time) {
		this.like_Time = like_Time;
	}

	@Column(name="LIKE_STATUS")
	public String getLike_status() {
		return like_status;
	}

	public void setLike_status(String like_status) {
		this.like_status = like_status;
	}
	
	
	
	
}
