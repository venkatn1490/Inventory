package com.medrep.app.model;

import java.io.Serializable;
import java.util.Date;

public class Likes implements Serializable{

	private int id;
	private int user_id;
	private String like_status;
	private int message_id;
	private Date like_time;
	
	
	public Likes() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public String getLike_status() {
		return like_status;
	}

	public void setLike_status(String like_status) {
		this.like_status = like_status;
	}

	public Date getLike_time() {
		return like_time;
	}

	public void setLike_time(Date like_time) {
		this.like_time = like_time;
	}
	
}
