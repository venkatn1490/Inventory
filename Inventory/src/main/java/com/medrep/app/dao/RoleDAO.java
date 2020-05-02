package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.RoleEntity;

/**
 * Home object for domain model class MRole.
 * @see com.medrep.app.dao.RoleEntity
 * @author Hibernate Tools
 */
@Repository
public class RoleDAO extends MedRepDAO<RoleEntity>{

	private static final Log log = LogFactory.getLog(RoleDAO.class);

	public List<RoleEntity> findAll() {
		log.info("getting All role instances");
		List<RoleEntity> instances = new ArrayList<RoleEntity>();
		try {
			instances = entityManager.createQuery("select r from RoleEntity r",RoleEntity.class).getResultList();
			log.info("get successful");			
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instances;
	}
}
