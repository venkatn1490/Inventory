package com.venkat.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.venkat.app.entity.RoleEntity;
import com.venkat.app.entity.UserEntity;

/**
 * Home object for domain model class MRole.
 * @see com.venkat.app.dao.RoleEntity
 * @author Hibernate Tools
 */
@Repository
public class RoleDAO extends VenkatDAO<RoleEntity>{

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
	public RoleEntity findByRoleId(Integer roleId) {
		RoleEntity roleEntity =  new RoleEntity();
		try
		{
			roleEntity = entityManager.createQuery("select s from RoleEntity s Where s.roleId = :roleId",RoleEntity.class).setParameter("roleId", roleId).getSingleResult();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return roleEntity;
	}
}
