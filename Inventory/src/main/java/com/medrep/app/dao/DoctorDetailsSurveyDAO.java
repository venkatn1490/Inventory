package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorDetailsSurveyEntity;

/**
 * Home object for domain model class TDoctorSurvey.
 * @see com.medrep.app.dao.DoctorDetailsSurveyEntity
 * @author Hibernate Tools
 */
@Repository
public class DoctorDetailsSurveyDAO extends MedRepDAO<DoctorDetailsSurveyEntity>{

	private static final Log log = LogFactory.getLog(DoctorDetailsSurveyDAO.class);

	
	
	public List<DoctorDetailsSurveyEntity> findBySurveyId(int surveyId) {
		log.debug("getting DoctorSurvey instance with surveyId: " + surveyId);
		List<DoctorDetailsSurveyEntity> doctorSurveys = null;
		try 
		{
			doctorSurveys = entityManager.createQuery("select d from DoctorDetailsSurveyEntity d where d.survey.surveyId = :surveyId", DoctorDetailsSurveyEntity.class).setParameter("surveyId",surveyId).getResultList();
			log.debug("get successful");
			return doctorSurveys;
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		
		return doctorSurveys;
	}
	
	public List<DoctorDetailsSurveyEntity> findBySurveyIdStatus(int surveyId,String status) {
		log.debug("getting DoctorSurvey instance with surveyId: " + surveyId);
		System.out.println("getting DoctorSurvey instance with surveyId: " + surveyId);
		List<DoctorDetailsSurveyEntity> doctorSurveys = null;
		try 
		{
			//doctorSurveys = entityManager.createQuery("select d from DoctorDetailsSurveyEntity d where d.surveyId = :surveyId and d.status = :status", DoctorDetailsSurveyEntity.class).setParameter("surveyId",surveyId).setParameter("status", status).getResultList();
			doctorSurveys = entityManager.createQuery("select d from DoctorDetailsSurveyEntity d", DoctorDetailsSurveyEntity.class).getResultList();
			log.debug("get successful");
			return doctorSurveys;
		} catch (RuntimeException re) {
			log.error("get failed", re);
		re.getMessage();
		re.getLocalizedMessage();
		}
		
		return doctorSurveys;
	}
	
	public List<DoctorDetailsSurveyEntity> findByStatus(String status) {
		log.debug("getting DoctorSurvey instance with status: " + status);
		List<DoctorDetailsSurveyEntity> doctorSurveys = null;
		try 
		{
			doctorSurveys = entityManager.createQuery("select d from DoctorDetailsSurveyEntity d where d.status = :status", DoctorDetailsSurveyEntity.class).setParameter("status", status).getResultList();
			log.debug("get successful");
			return doctorSurveys;
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		
		return doctorSurveys;
	}
	
	
	public DoctorDetailsSurveyEntity findByDoctorSurveyId(Integer doctorId, Integer surveyId) {
		log.info("getting DoctorSurvey instance with doctorId: " + doctorId + " and SUrvey Id : " + surveyId);
		DoctorDetailsSurveyEntity doctorSurvey = new DoctorDetailsSurveyEntity();
		try 
		{
			doctorSurvey = entityManager.createQuery("select d from DoctorDetailsSurveyEntity d where d.doctorId = :doctorId and d.survey.surveyId = :surveyId", DoctorDetailsSurveyEntity.class).setParameter("doctorId",doctorId).setParameter("surveyId", surveyId).getSingleResult();
			log.info("get successful");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			
		}
		return doctorSurvey;
	}
}
