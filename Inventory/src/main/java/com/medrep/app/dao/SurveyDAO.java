package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorSurveyEntity;
import com.medrep.app.entity.SurveyEntity;

/**
 * Home object for domain model class TSurvey.
 * @see com.medrep.app.dao.SurveyEntity
 * @author Hibernate Tools
 */
@Repository
public class SurveyDAO extends MedRepDAO<SurveyEntity>{

	private static final Log log = LogFactory.getLog(SurveyDAO.class);

	public List<SurveyEntity> getAdminSurveysList()
	{
		List<SurveyEntity> list = new ArrayList<SurveyEntity>();
		log.info("getting TSurvey list");
		try{
			list = entityManager.createQuery("SELECT p FROM SurveyEntity p",SurveyEntity.class ).getResultList();

		}
		catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return list;
	}

	public List<SurveyEntity> findSurveysByStatus(String status)
	{
		List<SurveyEntity> list = new ArrayList<SurveyEntity>();
		log.info("getting Survey list for status : " + status);
		try{

			list= entityManager.createQuery("SELECT p FROM SurveyEntity p where p.status = :status",SurveyEntity.class ).setParameter("status", status).getResultList();
			log.info("getting Survey Successful for status : " + status);
		}
		catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return list;
	}

	public List<SurveyEntity> findSurveysByPushStatus(String status)
	{
		List<SurveyEntity> list = new ArrayList<SurveyEntity>();
		log.info("getting Survey list for status : " + status);
		try{

			list= entityManager.createQuery("SELECT p FROM SurveyEntity p where p.status = :status and p.pushNotification = 'PUSH' ",SurveyEntity.class ).setParameter("status", status).getResultList();
			log.info("getting Survey Successful for status : " + status);
		}
		catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return list;
	}

	public List<SurveyEntity> findSurveysByCompanyId(Integer companyId)
	{
		List<SurveyEntity> list = new ArrayList<SurveyEntity>();
		log.debug("getting Survey list for Company : " + companyId);
		try
		{

			list= entityManager.createQuery("SELECT p FROM SurveyEntity p where p.companyId = :companyId order by p.createdOn desc",SurveyEntity.class ).setParameter("companyId", companyId).getResultList();
			log.debug("getting Survey Successful for Company : " + companyId);
		}
		catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return list;
	}


	public List<Object[]>  findStatsBySurveyId(Integer surveyId)
	{
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			log.info("Survey Id  :"+ surveyId);

			activities = entityManager.createNativeQuery("SELECT COUNT(STATUS), STATUS FROM T_DOCTOR_SURVEY WHERE SURVEY_ID = :surveyId GROUP BY STATUS").setParameter("surveyId", surveyId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}

	public List<Object[]> findSurveysByDoctorId(Integer doctorId) {
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			activities = entityManager.createNativeQuery("SELECT STATUS,COUNT(STATUS) FROM T_DOCTOR_SURVEY WHERE DOCTOR_ID = :doctorId GROUP BY STATUS").setParameter("doctorId", doctorId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}

	public List<DoctorSurveyEntity> findSurveysByPushStatus(Integer doctorId, String status) {
		List list = null;
		log.info("getting Survey list for status : " + status);
		try{

			list= entityManager.createQuery("SELECT ds FROM DoctorSurveyEntity  ds WHERE ds.doctorId = :doctorId  and ds.status=:status").setParameter("status", status)
					.setParameter("doctorId", doctorId).getResultList();
			log.info("getting Survey Successful for status : " + status);
		}
		catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return list;
	}

	public List<DoctorSurveyEntity> findPendingSurveys(Integer doctorId) {
		List list = null;
		try{

			list= entityManager.createQuery("SELECT ds FROM DoctorSurveyEntity  ds WHERE ds.doctorId = :doctorId  and ds.status!='Completed' order by ds.createdOn desc")
					.setParameter("doctorId", doctorId).getResultList();
		}
		catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return list;
	}

	public List<Object[]> findSurveysByRepId(Integer repId) {
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			activities = entityManager.createNativeQuery("SELECT STATUS,COUNT(STATUS) FROM T_REP_SURVEY WHERE REP_ID = :repId GROUP BY STATUS").setParameter("repId", repId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}



}
