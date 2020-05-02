package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.GEOEntity;
import com.medrep.app.entity.SurveyEntity;

/**
 * Home object for domain model class TSurvey.
 * @see com.medrep.app.dao.SurveyEntity
 * @author Hibernate Tools
 */
@Repository
public class GEODAO extends MedRepDAO<GEOEntity>{

	private static final Log log = LogFactory.getLog(SurveyDAO.class);

	public List<GEOEntity> getAllStates()
	{
		List<GEOEntity> results = new ArrayList<GEOEntity>();
		log.info("getting TSurvey list");
		try{
			List<Object[]> list = entityManager.createNativeQuery("SELECT DISTINCT STATE_CODE,STATE_NAME from T_M_GEO order by STATE_CODE").getResultList();
			for(Object obj : list){
				Object objArray[] = (Object [])obj;
				GEOEntity entity = new GEOEntity();
				entity.setStateCode(objArray[0]!=null ? objArray[0].toString() : "");
				entity.setStateName(objArray[1]!=null ? objArray[1].toString() : "");
				results.add(entity);
			}
			
		}
		catch(RuntimeException ex)
		{
			log.error("get failed", ex);
			
		}
		return results;
	}
	
	public List<GEOEntity> findCitiesByState(String state)
	{
		List<GEOEntity> results = new ArrayList<GEOEntity>();
		log.info("getting Citites By State  : " + state);
		try{
			
			results= entityManager.createQuery("SELECT p FROM GEOEntity p where p.stateCode = :code",GEOEntity.class ).setParameter("code", state).getResultList();
			log.info("getting Cities Successful for status : " + state);
		}
		catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return results;
	}
	
}
