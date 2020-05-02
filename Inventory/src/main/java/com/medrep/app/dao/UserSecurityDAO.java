package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.UserSecurityEntity;

/**
 * Home object for domain model class TUserSecurity.
 * @see com.medrep.app.dao.UserSecurityEntity
 * @author Hibernate Tools
 */
@Repository
public class UserSecurityDAO extends MedRepDAO<UserSecurityEntity>{

	private static final Log log = LogFactory.getLog(UserSecurityDAO.class);

	public UserSecurityEntity findByLoginId(String userSecLoginId) {
		log.info("User Sec Login Id:"+userSecLoginId);
		UserSecurityEntity securityEntity =  new UserSecurityEntity();
		try
		{
			securityEntity = entityManager.createQuery("select s from UserSecurityEntity s Where s.loginId = :loginId",UserSecurityEntity.class).setParameter("loginId", userSecLoginId).getSingleResult();
		}
		catch(Exception e)
		{
			log.error("Failed:"+e);
		}
		return securityEntity;
	}

}
