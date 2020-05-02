package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.TherapeuticAreaEntity;

/**
 * Home object for domain model class MTherapeuticArea.
 * @see com.medrep.app.dao.TherapeuticAreaEntity
 * @author Hibernate Tools
 */
@Repository
public class TherapeuticAreaDAO extends MedRepDAO<TherapeuticAreaEntity>{

	private static final Log log = LogFactory.getLog(TherapeuticAreaDAO.class);

	public List<TherapeuticAreaEntity> findAll() {
		log.info("getting All TherapeuticArea instances");
		List<TherapeuticAreaEntity> instances = new ArrayList<TherapeuticAreaEntity>();
		try {
			instances = entityManager.createQuery("select a from TherapeuticAreaEntity a order by a.therapeuticName",TherapeuticAreaEntity.class).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public TherapeuticAreaEntity findById(int Id) {
		System.out.println("id is "+Id);
		log.info("getting All TherapeuticArea instances");
		TherapeuticAreaEntity instance = null;
		try {
			instance = entityManager.createQuery("select a from TherapeuticAreaEntity a where a.therapeuticId=:id",TherapeuticAreaEntity.class)
					.setParameter("id", Id).getSingleResult();
			System.out.println(instance);
			log.info("get successful");

		} catch (RuntimeException re) {
			System.out.println("Exception"+re);
			log.error("get failed", re);
		}

		return instance;
	}
	public TherapeuticAreaEntity findByName(String name) {
		TherapeuticAreaEntity instance = null;
		try {
			instance = entityManager.createQuery("select a from TherapeuticAreaEntity a where a.therapeuticName=:name",TherapeuticAreaEntity.class)
					.setParameter("name", name).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instance;
	}
}
