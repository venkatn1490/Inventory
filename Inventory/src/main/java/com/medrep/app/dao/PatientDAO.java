package com.medrep.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.PatientEntity;

@Repository("patientDAO")

public class PatientDAO extends MedRepDAO<PatientEntity>{
	
	@PersistenceContext
	EntityManager entityManger;
	
	private static final Log log = LogFactory.getLog(PatientDAO.class);


	public PatientEntity findBySecurityId(Integer userSecId) {
		log.info("getting TDoctor instance with id: " + userSecId);
		PatientEntity instance = new PatientEntity();
		try
		{

			instance = entityManager.createQuery("select d from PatientEntity d where d.user.security.userSecId = :userSecId", PatientEntity.class).setParameter("userSecId",userSecId).getSingleResult();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);

		}
		return instance;
	}
	
	public List<PatientEntity> findbyLocationAndThreId(String city, String Id) {
		log.info("Getting PatientEntity Instance with city: " + city);
		List<PatientEntity> l=null ;
		try
		{
			l = entityManager.createQuery("select DISTINCT p  from PatientEntity  p  join p.user u join u.locations as t where t.city=:city  ", PatientEntity.class).setParameter("city", city).getResultList();
			log.info("get successfull");
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return l;
	}
	
	public PatientEntity findByLoginId(String loginId)
	{
		log.info("getting TDoctor instance with id: " + loginId);
		PatientEntity instance = new PatientEntity();
		try
		{
			instance = entityManager.createQuery("select d from PatientEntity d where d.user.security.loginId = :loginId", PatientEntity.class).setParameter("loginId",loginId).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}
	
	public PatientEntity findByPatientId(int patId)
	{
		log.info("getting TDoctor instance with id: " + patId);
		PatientEntity instance = new PatientEntity();
		try
		{
			instance = entityManager.createQuery("select d from PatientEntity d where d.patientId = :patId", PatientEntity.class).setParameter("patId",patId).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

}
