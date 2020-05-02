package com.medrep.app.dao;

import org.springframework.stereotype.Repository;

import com.medrep.app.entity.PostTypeEntity;

@Repository
public class PostTypeDao  extends MedRepDAO<PostTypeEntity>{

	public PostTypeEntity findById(int postTypeId) {
		return findById(PostTypeEntity.class,postTypeId);
	}

}