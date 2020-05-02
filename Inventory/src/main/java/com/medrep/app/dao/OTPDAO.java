package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.engine.UserValidator;
import com.medrep.app.entity.OTPEntity;
import com.medrep.app.entity.UserSecurityEntity;

/**
 * Home object for domain model class OTPEntity.
 * @see com.medrep.app.dao.OTPDAO
 * @author Hibernate Tools
 */
@Repository
public class OTPDAO extends MedRepDAO<OTPEntity>{
	private static final Log log = LogFactory.getLog(UserValidator.class);

	public OTPEntity findByVerificationId(String verificationId) {
		log.info("getting OTP instance with verification Id: " + verificationId);
		OTPEntity instance = new OTPEntity();
		try
		{

			instance = entityManager.createQuery("select s from OTPEntity s Where s.verificationId = :verificationId",OTPEntity.class).setParameter("verificationId", verificationId).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instance;
	}

	public List<OTPEntity> findBySecurityId(Integer securityId) {
		log.info("getting OTP instance with security Id: " + securityId);
		List<OTPEntity> instances = new ArrayList<OTPEntity>();
		try {

			instances = entityManager.createQuery("select s from OTPEntity s Where s.securityId = :securityId",OTPEntity.class).setParameter("securityId", securityId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}


}
