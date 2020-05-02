package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.NotificationDetailsEntity;
import com.medrep.app.entity.NotificationEntity;

/**
 * Home object for domain model class TNotificationDetails.
 * @see com.medrep.app.dao.NotificationDetailsEntity
 * @author Hibernate Tools
 */
@Repository
public class NotificationDetailsDAO extends MedRepDAO<NotificationDetailsEntity>{

	private static final Log log = LogFactory.getLog(NotificationDetailsDAO.class);

	public NotificationDetailsEntity findByNotificationId(Integer notificationId) {
		NotificationDetailsEntity notificationDetailsEntity =new NotificationDetailsEntity();
		log.info("getting TNotification list");
		try
		{
			notificationDetailsEntity = entityManager.createQuery("SELECT p FROM NotificationDetailsEntity p where p.notificationEntity.notificationId=:notificationId", NotificationDetailsEntity.class)
					.setParameter("notificationId", notificationId).getSingleResult();

		}catch(RuntimeException ex)
		{
			log.error("Zero/More Records found.Expected:1", ex);
		}
		return notificationDetailsEntity;
	}

}
