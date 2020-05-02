package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.PharmaRepNotificationEntity;

/**
 * DAO object for domain model class TDoctorNotification.
 * @see com.medrep.app.dao.DoctorNotificationEntity
 * @author Hibernate Tools
 */
@Repository
public class PharmaRepNotificationDAO extends MedRepDAO<PharmaRepNotificationEntity>{

	private static final Log log = LogFactory.getLog(PharmaRepNotificationDAO.class);

	public List<PharmaRepNotificationEntity> findByPharmaRepDate(Integer pharmaRepID, Date findByDate) {
		log.info("getting pharmaRep notification with Id and date : " + pharmaRepID +  " : " + findByDate);

		List<PharmaRepNotificationEntity> pharmaRepNotificationEntities = new ArrayList<PharmaRepNotificationEntity>();
		try
		{
			pharmaRepNotificationEntities = entityManager.createQuery("select d from PharmaRepNotificationEntity d,PharmaRepEntity p where p.repId=d.pharmaRepId and p.companyId=d.companyId and d.pharmaRepId = :pharmaRepId and d.createdOn >= :findByDate", PharmaRepNotificationEntity.class).setParameter("pharmaRepId",pharmaRepID).setParameter("findByDate",findByDate).getResultList();
			log.info("get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return pharmaRepNotificationEntities;
	}

	public PharmaRepNotificationEntity findByPharmaRepNotification(Integer pharmaRepID, Integer notificationID) {
		log.info("getting Doctors notification with Id and date : " + pharmaRepID +  " : " + notificationID);

		PharmaRepNotificationEntity pharmaRepNotificationEntity = new PharmaRepNotificationEntity();
		try
		{
			pharmaRepNotificationEntity = entityManager.createQuery("select d from PharmaRepNotificationEntity d ,PharmaRepEntity p where p.repId=d.pharmaRepId and p.companyId=d.companyId and d.pharmaRepId = :pharmaRepId and d.notificationEntity.notificationId = :notificationID", PharmaRepNotificationEntity.class).setParameter("pharmaRepId",pharmaRepID).setParameter("notificationID",notificationID).getSingleResult();
			log.info("get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return pharmaRepNotificationEntity;
	}


	public List<PharmaRepNotificationEntity> findByStatus(Integer pharmaRepID, String status) {

		List<PharmaRepNotificationEntity> pharmaRepNotificationEntities = new ArrayList<PharmaRepNotificationEntity>();
		try
		{
			pharmaRepNotificationEntities = entityManager.createQuery("select d from PharmaRepNotificationEntity d,PharmaRepEntity p where p.repId=d.pharmaRepId and p.companyId=d.companyId and d.pharmaRepId = :pharmaRepId  and d.viewStatus=:status", PharmaRepNotificationEntity.class).setParameter("pharmaRepId",pharmaRepID).setParameter("status",status).getResultList();

		}
		catch (RuntimeException re)
		{
			re.printStackTrace();
		}

		return pharmaRepNotificationEntities;
	}

}
