package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.NotificationTypeEntity;

/**
 * Home object for domain model class MNotificationType.
 * @see com.medrep.app.dao.MNotificationType
 * @author Hibernate Tools
 */
@Repository
public class NotificationTypeDAO extends MedRepDAO<NotificationTypeEntity>{

	private static final Log log = LogFactory.getLog(NotificationTypeDAO.class);
	
	public List<NotificationTypeEntity> findAll() {
		log.info("getting All NotificationTypeEntity instances");
		List<NotificationTypeEntity> instances=new ArrayList<NotificationTypeEntity>();
		try {
			instances = entityManager.createQuery("select a from NotificationTypeEntity a",NotificationTypeEntity.class).getResultList();
			log.info("get successful");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instances;
	}
}
