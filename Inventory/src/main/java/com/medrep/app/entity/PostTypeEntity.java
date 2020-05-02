package com.medrep.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "T_POST_TYPE",uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
public class PostTypeEntity {

	@Id
	@Column(name = "ID")
	Integer id;
	@Column(name = "POST_TYPE")
	String postType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}


}