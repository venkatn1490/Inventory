package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.PharmaSurveyEntity;

/**
 * Home object for domain model class TDoctorSurvey.
 * @see com.medrep.app.dao.DoctorSurveyEntity
 * @author Hibernate Tools
 */
@Repository
public class PharmaRepSurveyDAO extends MedRepDAO<PharmaSurveyEntity> {

	private static final Log log = LogFactory.getLog(PharmaRepSurveyDAO.class);

	public List<PharmaSurveyEntity> findByRepId(int repId) {
		log.info("getting DoctorSurvey instance with doctorId: " + repId);
		List<PharmaSurveyEntity> repSurveys = new ArrayList<PharmaSurveyEntity>();
		try
		{

			repSurveys = entityManager.createQuery("select d from PharmaSurveyEntity d where d.repId = :repId and d.status = 'NEW'", PharmaSurveyEntity.class).setParameter("repId",repId).getResultList();

			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return repSurveys;
	}

	public List<PharmaSurveyEntity> findPendingSurveys(Integer repId) {
		List list = null;
		try{

			list= entityManager.createQuery("SELECT ps FROM PharmaSurveyEntity ps WHERE ps.repId = :repId  and ps.status!='Completed'")
					.setParameter("repId", repId).getResultList();
		}
		catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return list;
	}

	public PharmaSurveyEntity findBySurveyIdForPharmaRep(String loginId, Integer surveyId) {
		PharmaSurveyEntity repSurveys = new PharmaSurveyEntity();
		try
		{
			repSurveys = entityManager.createQuery("select pse from PharmaSurveyEntity pse,PharmaRepEntity pre, UserEntity ue where pse.repId = pre.repId and pre.user.security.loginId=:loginId and pse.survey.surveyId=:surveyId", PharmaSurveyEntity.class)
					.setParameter("loginId",loginId)
					.setParameter("surveyId", surveyId).getSingleResult();

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return repSurveys;
	}

	public PharmaSurveyEntity findByPharmaRepSurvey(Integer repId, Integer surveyId) {
		PharmaSurveyEntity repSurveys = null;
		try
		{
			repSurveys = entityManager.createQuery("select pse from PharmaSurveyEntity pse where pse.repId = :repId and pse.survey.surveyId=:surveyId", PharmaSurveyEntity.class)
					.setParameter("repId",repId)
					.setParameter("surveyId", surveyId).getSingleResult();

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return repSurveys;
	}
}