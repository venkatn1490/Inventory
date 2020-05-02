package com.medrep.app.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Group implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int admin_id;
	private int group_id;
	private String group_name;
	private String group_long_desc;
	private String group_short_desc;
	private String is_Admin;
	private String imgData;
	public String fileName;
	public int pendingMembers;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getImgData() {
		return imgData;
	}

	public void setImgData(String imgData) {
		this.imgData = imgData;
	}

	private String imageUrl;
	private List<Member> member;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getIs_Admin() {
		return is_Admin;
	}

	public void setIs_Admin(String is_Admin) {
		this.is_Admin = is_Admin;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public List<Member> getMember() {
		return member;
	}

	public void setMember(List<Member> member) {
		this.member = member;
	}

	public int getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getGroup_long_desc() {
		return group_long_desc;
	}

	public void setGroup_long_desc(String group_long_desc) {
		this.group_long_desc = group_long_desc;
	}

	public String getGroup_short_desc() {
		return group_short_desc;
	}

	public void setGroup_short_desc(String group_short_desc) {
		this.group_short_desc = group_short_desc;
	}

	public int getPendingMembers() {
		return pendingMembers;
	}

	public void setPendingMembers(int pendingMembers) {
		this.pendingMembers = pendingMembers;
	}
}