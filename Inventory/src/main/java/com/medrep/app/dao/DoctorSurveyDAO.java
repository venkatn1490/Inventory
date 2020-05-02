package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorSurveyEntity;

/**
 * Home object for domain model class TDoctorSurvey.
 * @see com.medrep.app.dao.DoctorSurveyEntity
 * @author Hibernate Tools
 */
@Repository
public class DoctorSurveyDAO extends MedRepDAO<DoctorSurveyEntity>{

	private static final Log log = LogFactory.getLog(DoctorSurveyDAO.class);

	public List<DoctorSurveyEntity> findByDoctorId(int doctorId) {
		log.info("getting DoctorSurvey instance with doctorId: " + doctorId);
		List<DoctorSurveyEntity> doctorSurveys = new ArrayList<DoctorSurveyEntity>();
		try
		{
			doctorSurveys = entityManager.createQuery("select d from DoctorSurveyEntity d where d.doctorId = :doctorId and d.status = 'Publish' order by d.createdOn desc", DoctorSurveyEntity.class).setParameter("doctorId",doctorId).getResultList();
			log.info("get successful");
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return doctorSurveys;
	}

	public List<DoctorSurveyEntity> findByStatusAndDoctorId(int doctorId,String status) {
		log.info("getting DoctorSurvey instance with doctorId: " + doctorId);
		List<DoctorSurveyEntity> doctorSurveys = new ArrayList<DoctorSurveyEntity>();
		try
		{
			doctorSurveys = entityManager.createQuery("select d from DoctorSurveyEntity d where d.doctorId = :doctorId and d.status = :status order by d.createdOn desc", DoctorSurveyEntity.class).setParameter("doctorId",doctorId)
					.setParameter("status", status).getResultList();
			log.info("get successful");
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return doctorSurveys;
	}

	public List<DoctorSurveyEntity> findBySurveyId(int surveyId) {
		log.debug("getting DoctorSurvey instance with surveyId: " + surveyId);
		List<DoctorSurveyEntity> doctorSurveys = null;
		try
		{
			doctorSurveys = entityManager.createQuery("select distinct d from DoctorSurveyEntity d where d.survey.surveyId = :surveyId  and d.status = 'Publish' order by d.createdOn desc", DoctorSurveyEntity.class).setParameter("surveyId",surveyId).getResultList();
			log.debug("get successful");
			return doctorSurveys;
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return doctorSurveys;
	}


	public List<DoctorSurveyEntity>  findStatsBySurveyId(Integer surveyId)
	{
		List<DoctorSurveyEntity> activities = new ArrayList<DoctorSurveyEntity>();
		try
		{
			log.info("Survey Id  :"+ surveyId);

			activities = entityManager.createQuery("select ds FROM DoctorSurveyEntity ds where  ds.survey.surveyId = :surveyId",DoctorSurveyEntity.class)
					.setParameter("surveyId", surveyId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}

	public List<DoctorSurveyEntity> findBySurveyIdStatus(int surveyId,String status) {
		log.debug("getting DoctorSurvey instance with surveyId: " + surveyId);
		List<DoctorSurveyEntity> doctorSurveys = null;
		try
		{
			doctorSurveys = entityManager.createQuery("select distinct d from DoctorSurveyEntity d where d.survey.surveyId = :surveyId and d.status = :status", DoctorSurveyEntity.class).setParameter("surveyId",surveyId).setParameter("status", status).getResultList();
			log.debug("get successful");
			return doctorSurveys;
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return doctorSurveys;
	}

	public List<DoctorSurveyEntity> findByStatus(String status) {
		log.debug("getting DoctorSurvey instance with status: " + status);
		List<DoctorSurveyEntity> doctorSurveys = null;
		try
		{
			doctorSurveys = entityManager.createQuery("select d from DoctorSurveyEntity d where d.status = :status", DoctorSurveyEntity.class).setParameter("status", status).getResultList();
			log.debug("get successful");
			return doctorSurveys;
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return doctorSurveys;
	}


	public DoctorSurveyEntity findByDoctorSurveyId(Integer doctorId, Integer surveyId) {
		log.info("getting DoctorSurvey instance with doctorId: " + doctorId + " and SUrvey Id : " + surveyId);
		DoctorSurveyEntity doctorSurvey = null;
		try
		{
			doctorSurvey = entityManager.createQuery("select d from DoctorSurveyEntity d where d.doctorId = :doctorId and d.survey.surveyId = :surveyId order by d.createdOn desc", DoctorSurveyEntity.class).setParameter("doctorId",doctorId).setParameter("surveyId", surveyId).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return doctorSurvey;
	}
	public List<DoctorSurveyEntity> findByDoctorSurveyId(Integer doctorId) {
		log.info("getting DoctorSurvey instance with doctorId: " + doctorId );
		List<DoctorSurveyEntity> doctorSurvey = new ArrayList<DoctorSurveyEntity>();
		try
		{
			doctorSurvey = entityManager.createQuery("select d from DoctorSurveyEntity d where d.doctorId = :doctorId  order by d.createdOn desc", DoctorSurveyEntity.class).setParameter("doctorId",doctorId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return doctorSurvey;
	}
}
