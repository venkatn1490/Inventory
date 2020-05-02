package com.venkat.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.venkat.app.entity.RoleEntity;
import com.venkat.app.entity.SalesUserEntity;
import com.venkat.app.entity.SupplierOrdersEnity;
import com.venkat.app.entity.UserEntity;
import com.venkat.app.entity.UserSecurityEntity;
import com.venkat.app.model.User;

/**
 * Home object for domain model class TUser.
 * @see com.venkat.app.dao.UserEntity
 * @author Hibernate Tools
 */
@Repository
public class SalesUserDAO extends VenkatDAO<SalesUserEntity>{

	private static final Log log = LogFactory.getLog(SalesUserDAO.class);

	
	public List<SalesUserEntity> findAllUsers(){
		List<SalesUserEntity> entites = null;
		try {
			entites = entityManager.createQuery("select r from SalesUserEntity r",SalesUserEntity.class).getResultList();
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return entites;
		}
	

	public SalesUserEntity findByUserId(Integer userId){
		SalesUserEntity entites = null;
		try {
			entites = entityManager.createQuery("select s from SalesUserEntity s Where s.user.userId = :userId",SalesUserEntity.class).setParameter("userId", userId).getSingleResult();

		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return entites;
		}
}
