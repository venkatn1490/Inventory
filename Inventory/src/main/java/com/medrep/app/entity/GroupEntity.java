package com.medrep.app.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "M_GROUPS")
public class GroupEntity implements java.io.Serializable {

	private int group_id;
	private int admin_id;
	private String group_name;
	private String group_long_desc;
	private String group_short_desc;
	private String imageUrl;
	
	
	@Column(name="IMAGE_URL")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public GroupEntity() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	
	@Column(name = "GROUP_ID", nullable = false)
	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	@Column(name = "ADMIN_ID", nullable = false)
	public int getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}

	@Column(name = "GROUP_NAME", nullable = false)
	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	@Column(name = "GROUP_LONG_DESC")
	public String getGroup_long_desc() {
		return group_long_desc;
	}

	public void setGroup_long_desc(String group_long_desc) {
		this.group_long_desc = group_long_desc;
	}

	@Column(name = "GROUP_SHORT_DESC")
	public String getGroup_short_desc() {
		return group_short_desc;
	}

	public void setGroup_short_desc(String group_short_desc) {
		this.group_short_desc = group_short_desc;
	}
}
