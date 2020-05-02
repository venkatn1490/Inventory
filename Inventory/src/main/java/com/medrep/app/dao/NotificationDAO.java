package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigInteger;
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

import com.medrep.app.entity.NotificationDetailsEntity;
import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.util.MedrepException;

/**
 * Home object for domain model class TNotification.
 * @see com.medrep.app.dao.NotificationEntity
 * @author Hibernate Tools
 */
@Repository
public class NotificationDAO extends MedRepDAO<NotificationEntity>{

	private static final Log log = LogFactory.getLog(NotificationDAO.class);

	
	public List<NotificationEntity> getAdminNotificationsList(){
		List<NotificationEntity> notificationList =new ArrayList<NotificationEntity>();
		log.info("getting TNotification list");
		try
		{
			notificationList = entityManager.createQuery("SELECT p FROM NotificationEntity p", NotificationEntity.class).getResultList();
			
		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return notificationList;
	}
	
	public List<NotificationEntity> findByStatus(String status)
	{
		List<NotificationEntity> notificationList = new ArrayList<NotificationEntity>();
		log.info("getting TNotification list");
		try{
			notificationList = entityManager.createQuery("SELECT p FROM NotificationEntity p where p.status=:status", NotificationEntity.class).setParameter("status", status).getResultList();
		}catch(RuntimeException ex){
			log.error("get failed", ex);
		}
		return notificationList;
	}
	
	public List<NotificationEntity> findByPushStatus(String status)
	{
		List<NotificationEntity> notificationList = new ArrayList<NotificationEntity>();
		log.info("getting TNotification list");
		try{
			notificationList = entityManager.createQuery("SELECT p FROM NotificationEntity p where p.status=:status and p.pushNotification = 'PUSH' ", NotificationEntity.class).setParameter("status", status).getResultList();
		}catch(RuntimeException ex){
			log.error("get failed", ex);
		}
		return notificationList;
	}
	
	public List<Object[]> findNotificationStats(Integer notificationId)
	{
		List<Object[]> notificationList = new ArrayList<Object[]>();
		log.debug("getting TNotification list");
		try{
			notificationList = entityManager.createNativeQuery("SELECT COUNT(VIEW_STATUS) AS COUNT, VIEW_STATUS AS STATUS, 'NOTIFICATION' AS TYPE FROM T_DOCTOR_NOTIFICATION WHERE NOTIFICATION_ID = :NId1 GROUP BY VIEW_STATUS UNION SELECT COUNT(STATUS), STATUS, 'APPOINTMENT' AS TYPE FROM T_DOCTOR_APPOINTMENT WHERE NOTIFICATION_ID = :NId2 GROUP BY STATUS").setParameter("NId1", notificationId).setParameter("NId2", notificationId).getResultList();
		}catch(RuntimeException ex){
			log.error("get failed", ex);
		}
		return notificationList;
	}
	
	
}
