package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.entity.ActivityEntity;

/**
 * DOA object for domain model class TDoctor.
 * @see com.medrep.app.dao.ActivityEntity
 * @author Hibernate Tools
 */
@Repository
public class ActivityDAO  extends MedRepDAO<ActivityEntity>{

	private static final Log log = LogFactory.getLog(ActivityDAO.class);

	public List<ActivityEntity> findByUserId(Integer userId)
	{
		List<ActivityEntity> instance = new ArrayList<ActivityEntity>();
		log.info("getting Activity instance with id: " + userId);
		try
		{
			System.out.println("Find by user id : "+userId);
			instance = entityManager.createQuery("select d from ActivityEntity d where d.userId = :userId", ActivityEntity.class).setParameter("userId",userId).getResultList();

			log.info("get successful");
			return instance;
		} catch (RuntimeException re)
		{
			log.error("get failed", re);

		}
		return instance;
	}

	public ActivityEntity findByActivityId(Integer activityId) {
		log.info("getting Activity instance with id: " + activityId);
		ActivityEntity instance = new ActivityEntity();
		try
		{

			instance = entityManager.createQuery("select d from ActivityEntity d where d.activityId = :activityId", ActivityEntity.class).setParameter("activityId",activityId).getSingleResult();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}


	public List<ActivityEntity> findByActivityStatus(String status)
	{
		log.debug("getting Activity instance with id: " + status);
		List<ActivityEntity> instance = new ArrayList<ActivityEntity>();
		try
		{
			System.out.println("Find by status id : "+status);
			instance = entityManager.createQuery("select d from ActivityEntity d where d.activityStatus = :status", ActivityEntity.class).setParameter("status",status).getResultList();

			log.debug("get successful");
		} catch (RuntimeException re)
		{
			log.error("get failed", re);

		}

		return instance;
	}

	public ActivityEntity findByUserActivity(Integer userId, String activityType, Integer activityTypeId) {
		log.info("getting Activity instance");
		ActivityEntity instance = new ActivityEntity();

		log.info("UserId : " + userId + " activityType : " + activityType + " activityTypeId : " + activityTypeId);
		try
		{
			instance = entityManager.createQuery("select d from ActivityEntity d where d.userId = :userId and d.activityType = :activityType and d.activityTypeId = :activityTypeId", ActivityEntity.class).setParameter("userId",userId).setParameter("activityType",  activityType).setParameter("activityTypeId", activityTypeId).getSingleResult();
			log.debug("get successful");
		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}


	public void updateActivityStatus(ActivityEntity  updateInstance, String status)
	{
		try
		{
			log.info("Status :"+status);
			updateInstance.setActivityStatus(status);
			entityManager.flush();
			entityManager.persist(updateInstance);
			entityManager.flush();


		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}

	}


	public List<Object[]>  findActivityByNotificationId(Integer notificationId)
	{
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			log.info("Notification Id  :"+ notificationId);

			activities = entityManager.createNativeQuery("SELECT COUNT(ACTIVITY_TYPE), ACTIVITY_TYPE FROM T_ACTIVITY WHERE ACTIVITY_TYPE_ID = :activityTypeId GROUP BY ACTIVITY_TYPE").setParameter("activityTypeId", notificationId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}

	public List<Object[]> findActivityByUserId(Integer userId)
	{
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			log.info("Notification Id  :"+ userId);
			activities = entityManager.createNativeQuery("SELECT COUNT(ACTIVITY_TYPE), ACTIVITY_TYPE FROM T_ACTIVITY WHERE  USER_ID = :userId GROUP BY ACTIVITY_TYPE").setParameter("userId", userId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}

	public List<Object[]> findActivityByDoctorId(Integer doctorId)
	{
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			log.info("Notification Id  :"+ doctorId);
			activities = entityManager.createNativeQuery("SELECT COUNT(ACTIVITY_TYPE), ACTIVITY_TYPE, COMPANY_ID FROM T_ACTIVITY WHERE  COMPANY_ID in (SELECT comp.COMPANY_ID from T_COMPANY comp where comp.STATUS='Active') and USER_ID = :doctorId GROUP BY ACTIVITY_TYPE, COMPANY_ID ORDER BY COMPANY_ID").setParameter("doctorId", doctorId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}

	public List<Object[]> findActivityByUserIdCompanyId(Integer userId, Integer companyId)
	{
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			log.info("Notification Id  :"+ userId);
			activities = entityManager.createNativeQuery("SELECT COUNT(ACTIVITY_TYPE), ACTIVITY_TYPE FROM T_ACTIVITY WHERE USER_ID = :userId and COMPANY_ID = :companyId GROUP BY ACTIVITY_TYPE").setParameter("userId", userId).setParameter("companyId", companyId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}

	public List<Object[]> findUserByCompany(Integer companyId)
	{
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			log.info("Company Id  :"+ companyId);
			activities = entityManager.createNativeQuery("SELECT DISTINCT USER_ID FROM T_ACTIVITY WHERE USER_TYPE = 'DOCTOR' AND COMPANY_ID =:companyId").setParameter("companyId", companyId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}
}