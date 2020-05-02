package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorRepEntity;

/**
 * DOA object for domain model class TDoctor.
 * @see com.medrep.app.dao.DoctorEntity
 * @author Hibernate Tools
 */
@Repository
public class DoctorRepDAO extends MedRepDAO<DoctorRepEntity>{

	private static final Log log = LogFactory.getLog(DoctorRepDAO.class);

	public List<DoctorRepEntity> findByDoctorId(Integer doctorId) 
	{
		log.info("getting DoctorRepEntity instance with doctorId: " + doctorId);
		List<DoctorRepEntity> instances = new ArrayList<DoctorRepEntity>();
		try 
		{
			instances = entityManager.createQuery("select d from DoctorRepEntity d where d.doctor.doctorId = :doctorId", DoctorRepEntity.class).setParameter("doctorId",doctorId).getResultList();
			
			log.info("get successful");
			
		} catch (RuntimeException re) 
		{
			log.error("get failed", re);
		}
		return instances;
	}
	
	public List<DoctorRepEntity> findByRepId(Integer repId) 
	{
		log.info("getting DoctorRepEntity instance with repId: " + repId);
		List<DoctorRepEntity> instances = new ArrayList<DoctorRepEntity>();
		try 
		{
			instances = entityManager.createQuery("select d from DoctorRepEntity d where d.rep.repId = :repId", DoctorRepEntity.class).setParameter("repId",repId).getResultList();
			
			log.info("get successful");
			
		} catch (RuntimeException re) 
		{
			log.error("get failed", re);
		}
		return instances;
	}
	
	
}
