package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.entity.DoctorNotificationEntity;

/**
 * DAO object for domain model class TDoctorNotification.
 * @see com.medrep.app.dao.DoctorNotificationEntity
 * @author Hibernate Tools
 */
@Repository
public class DoctorNotificationDAO extends MedRepDAO<DoctorNotificationEntity>{

	private static final Log log = LogFactory.getLog(DoctorNotificationDAO.class);

	public List<DoctorNotificationEntity> findByDoctorDate(Integer doctorID, Date findByDate) {
		log.info("getting Doctors notification with Id and date : " + doctorID +  " : " + findByDate);

		List<DoctorNotificationEntity> doctorNotificationEntities = new ArrayList<DoctorNotificationEntity>();
		try
		{
			doctorNotificationEntities = entityManager.createQuery("select d from DoctorNotificationEntity d where d.doctorId = :doctorId and d.createdOn >= :findByDate order by d.createdOn desc", DoctorNotificationEntity.class).setParameter("doctorId",doctorID).setParameter("findByDate",findByDate).getResultList();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return doctorNotificationEntities;
	}

	public DoctorNotificationEntity findByDoctorNotification(Integer doctorID, Integer notificationID) {
		log.info("getting Doctors notification with Id and date : " + doctorID +  " : " + notificationID);

		DoctorNotificationEntity doctorNotificationEntity = new DoctorNotificationEntity();
		try
		{
			doctorNotificationEntity = entityManager.createQuery("select d from DoctorNotificationEntity d where d.doctorId = :doctorId and d.notificationEntity.notificationId = :notificationID", DoctorNotificationEntity.class).setParameter("doctorId",doctorID).setParameter("notificationID",notificationID).getSingleResult();
			log.info("get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return doctorNotificationEntity;
	}
	public List<DoctorNotificationEntity> findByDoctorNotification(Integer doctorID) {
		log.info("getting Doctors notification with Id and date : " + doctorID +  " : " );

		List<DoctorNotificationEntity> doctorNotificationEntity = new ArrayList<DoctorNotificationEntity>();
		try
		{
			doctorNotificationEntity = entityManager.createQuery("select d from DoctorNotificationEntity d where d.doctorId = :doctorId ", DoctorNotificationEntity.class).setParameter("doctorId",doctorID).getResultList();
			log.info("get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return doctorNotificationEntity;
	}
	
	public List<Object[]> findDoctorNotificationStats(Integer notificationId)
	{
		List<Object[]> notificationList = new ArrayList<Object[]>();
		log.debug("getting TNotification list");
		try{
			notificationList = entityManager.createNativeQuery("SELECT ROUND(SUM(RATING)/COUNT(RATING),1) AS AVERAGE, COUNT(RATING) AS TOTAL, 'RATING' AS ENTITY  FROM T_DOCTOR_NOTIFICATION WHERE VIEW_STATUS !='Pending' AND NOTIFICATION_ID = :NID1 UNION SELECT COUNT(FAVOURITE) AS AVERAGE, FAVOURITE AS TOTAL, 'FAVOURITE' AS ENTITY  FROM T_DOCTOR_NOTIFICATION WHERE VIEW_STATUS !='Pending' AND NOTIFICATION_ID = :NID2 GROUP BY FAVOURITE UNION SELECT COUNT(PRESCRIBE) AS AVERAGE, PRESCRIBE AS TOTAL, 'PRESCRIBE' AS ENTITY  FROM T_DOCTOR_NOTIFICATION WHERE VIEW_STATUS !='Pending' AND NOTIFICATION_ID = :NID3 GROUP BY PRESCRIBE UNION SELECT COUNT(RECOMEND) AS AVERAGE, RECOMEND AS TOTAL, 'RECOMEND' AS ENTITY  FROM T_DOCTOR_NOTIFICATION WHERE VIEW_STATUS !='Pending' AND NOTIFICATION_ID = :NID4 GROUP BY RECOMEND ").setParameter("NID1", notificationId).setParameter("NID2", notificationId).setParameter("NID3", notificationId).setParameter("NID4", notificationId).getResultList();
		}catch(RuntimeException ex){
			log.error("get failed", ex);
		}
		return notificationList;
	}

	public List<DoctorNotificationEntity> getAllByNotificationId(Integer notificationID) {
		log.info("getting all Doctors notification by notification id  : " +  notificationID);

		List<DoctorNotificationEntity> doctorNotificationEntitys = new ArrayList<DoctorNotificationEntity>();
		try
		{
			doctorNotificationEntitys = entityManager.createQuery("select d from DoctorNotificationEntity d where d.notificationEntity.notificationId = :notificationID and (d.viewStatus ='Pending' or d.viewStatus = 'Viewed') order by d.viewStatus", DoctorNotificationEntity.class).setParameter("notificationID",notificationID).getResultList();
			log.info("get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return doctorNotificationEntitys;
	}

	public List<DoctorNotificationEntity> findDoctorNotificationsWithRemindMe(Date date) {
	List<DoctorNotificationEntity> doctorNotificationEntitys = null;
	try
	{
		doctorNotificationEntitys = entityManager.createQuery("select d from DoctorNotificationEntity d where d.remindLater=:remindLater", DoctorNotificationEntity.class).setParameter("remindLater",date).getResultList();
		log.info("get successful");
	}
	catch (RuntimeException re)
	{
		log.error("get failed", re);
	}
	return doctorNotificationEntitys;
	}

	public List findByStatus(Integer doctorId, String status) {
		List<DoctorNotificationEntity> doctorNotificationEntitys = new ArrayList<DoctorNotificationEntity>();
		try
		{
			doctorNotificationEntitys = entityManager.createQuery("select d from DoctorNotificationEntity d where d.viewStatus =:status and d.doctorId=:doctorId order by d.createdOn desc", DoctorNotificationEntity.class).setParameter("status",status)
					.setParameter("doctorId",doctorId).getResultList();

		}
		catch (RuntimeException re)
		{
			re.printStackTrace();
		}

		return doctorNotificationEntitys;
	}

	public List<DoctorNotificationEntity> findRemindersOn(Date date) {
		List<DoctorNotificationEntity> doctorNotificationEntitys = new ArrayList<DoctorNotificationEntity>();
		try
		{
			doctorNotificationEntitys = entityManager.createQuery("select d from DoctorNotificationEntity d Where d.reminderTime between :date and :ceilDate order by d.reminderTime asc",DoctorNotificationEntity.class)
			.setParameter("date", date)
			.setParameter("ceilDate", new Date(date.getTime()+24*60*60*1000))
					.getResultList();

		}
		catch (RuntimeException re)
		{
			re.printStackTrace();
		}

		return doctorNotificationEntitys;
	}

	public List<DoctorNotificationEntity> findByNotificationId(Integer notificationId) {

		List<DoctorNotificationEntity> doctorNotificationEntity = null;
		try
		{
			doctorNotificationEntity = entityManager.createQuery("select d from DoctorNotificationEntity d where d.notificationEntity.notificationId = :notificationID order by d.createdOn desc", DoctorNotificationEntity.class)
					.setParameter("notificationID",notificationId).getResultList();
		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return doctorNotificationEntity;
	}
	
	public List<DoctorNotificationEntity> getAllViewByNotificationId(Integer notificationID) {
		log.info("getting all View Doctors notification by notification id  : " +  notificationID);

		List<DoctorNotificationEntity> doctorNotificationEntitys = new ArrayList<DoctorNotificationEntity>();
		try
		{
			doctorNotificationEntitys = entityManager.createQuery("select d from DoctorNotificationEntity d where d.notificationEntity.notificationId = :notificationID and d.viewStatus = 'Viewed' order by d.viewStatus", DoctorNotificationEntity.class).setParameter("notificationID",notificationID).getResultList();
			log.info("getting all View Doctors notification  get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return doctorNotificationEntitys;
	}
	
	public List<DoctorNotificationEntity> getAllViewByDoctorId(Integer doctorId) {
		log.info("getting all View Doctors notification by notification id  : " +  doctorId);

		List<DoctorNotificationEntity> doctorNotificationEntitys = new ArrayList<DoctorNotificationEntity>();
		try
		{
			doctorNotificationEntitys = entityManager.createQuery("select d from DoctorNotificationEntity d where d.doctorId = :doctorId ", DoctorNotificationEntity.class).setParameter("doctorId",doctorId).getResultList();
			log.info("getting all View Doctors notification  get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return doctorNotificationEntitys;
	}


}
