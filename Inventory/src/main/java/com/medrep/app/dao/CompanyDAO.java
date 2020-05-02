package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;

/**
 * DAO object for domain model class Company.
 * @see com.medrep.app.dao.CompanyEntity
 * @author Hibernate Tools
 */
@Repository
public class CompanyDAO extends MedRepDAO<CompanyEntity>{

	private static final Log log = LogFactory.getLog(CompanyDAO.class);

	public List<CompanyEntity> findAllActive() {
		log.info("getting All Company instances");
		List<CompanyEntity> companyInstances = new ArrayList<CompanyEntity>();
		try {
			
			companyInstances = entityManager.createQuery("select a from CompanyEntity a where a.status = 'Active'",CompanyEntity.class).getResultList();
			log.info("get successful");			
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return companyInstances;
	}
	
	public List<CompanyEntity> findAll() {
		log.info("getting All Company instances");
		List<CompanyEntity> companyInstances = new ArrayList<CompanyEntity>();
		try {
			companyInstances = entityManager.createQuery("select a from CompanyEntity a",CompanyEntity.class).getResultList();
			log.info("get successful");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return companyInstances;
	}
	
	public CompanyEntity findByCompanyId(Integer companyId){
		
		log.debug("getting CompanyName");
		CompanyEntity companyEntity = null;
		try {
			companyEntity = entityManager.createQuery("select a from CompanyEntity a where a.companyId = :companyId",CompanyEntity.class).setParameter("companyId", companyId).getSingleResult();
			log.debug("get successful");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return companyEntity;
		
	}
	
}
