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

import com.venkat.app.entity.UserEntity;
import com.venkat.app.entity.UserSecurityEntity;
import com.venkat.app.model.User;

/**
 * Home object for domain model class TUser.
 * @see com.venkat.app.dao.UserEntity
 * @author Hibernate Tools
 */
@Repository
public class UserDAO extends VenkatDAO<UserEntity>{

	private static final Log log = LogFactory.getLog(UserDAO.class);

	public UserEntity findByEmailId(String emailId) {
		log.info("Getting UserEntity Instance with emailId: " + emailId);
		UserEntity userEntity =  new UserEntity();
		try
		{
			userEntity = entityManager.createQuery("select s from UserEntity s Where s.security.loginId = :emailId",UserEntity.class).setParameter("emailId", emailId).getSingleResult();
			log.info("get successfull");
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntity;
	}
	

	
	
	public UserEntity findByMobileNo(String mobileNo) {
		UserEntity userEntity =  new UserEntity();
		try
		{
			userEntity = entityManager.createQuery("select s from UserEntity s Where s.mobileNo = :mobileNo",UserEntity.class).setParameter("mobileNo", mobileNo).getSingleResult();
			log.info("get successfull");
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntity;
	}

	public UserEntity findBySecurityId(Integer id) {
		UserEntity userEntity =  new UserEntity();
		log.info("Security Id:"+id);
		try
		{
			userEntity = entityManager.createQuery("select s from UserEntity s Where s.security.userSecId = :userSecId",UserEntity.class).setParameter("userSecId", id).getSingleResult();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntity;
	}

	public List<UserEntity> findByStatus(String status) {
		log.info("Getting UserEntity based on status : " +status);
		List<UserEntity> userEntities =  new ArrayList<UserEntity>();
		try
		{
			userEntities = entityManager.createQuery("select s from UserEntity s Where s.security.status = :status",UserEntity.class).setParameter("status", status).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntities;
	}
	

	public UserEntity findByUserId(Integer id) {
		UserEntity userEntity =  new UserEntity();
		log.info("Security Id:"+id);
		try
		{
			userEntity = entityManager.createQuery("select s from UserEntity s Where s.userId = :userId",UserEntity.class).setParameter("userId", id).getSingleResult();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntity;
	}
	

	@SuppressWarnings("unchecked")
	public List<UserEntity> getUserDetails(String userName) {
		List<UserEntity> userEntity =  new ArrayList<UserEntity>();
		log.info("user name="+userName);
		try
		{
			userEntity =  entityManager.createQuery("select s from UserEntity s Where s.emailId = :emailId",UserEntity.class).setParameter("emailId", userName).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntity;
	}

	public List<UserEntity> findUsersByRole(Integer roleId){
		List<UserEntity> entites = null;
		try {
			entites = entityManager.createQuery("SELECT d FROM UserEntity d WHERE d.role=:roleId", UserEntity.class).setParameter("roleId", roleId).getResultList();
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return entites;
		}
	
	public List<UserEntity> findAllUsers(){
		List<UserEntity> entites = null;
		try {
			entites = entityManager.createQuery("SELECT d FROM UserEntity ", UserEntity.class).getResultList();
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return entites;
		}
	public UserEntity findByUserId(String rhcId){
		UserEntity entites = null;
		try {
			
			entites = entityManager.createQuery("select d from UserEntity d where d.security.loginId = :rhcId", UserEntity.class).setParameter("rhcId", rhcId).getSingleResult();
			
		}catch(Exception e) {
			e.printStackTrace();
					}
		
		return entites;
		}
}
