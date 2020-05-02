package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.util.Util;

/**
 * Home object for domain model class TPharmaRep.
 * @see com.medrep.app.dao.PharmaRepEntity
 * @author Hibernate Tools
 */
@Repository
public class PharmaRepDAO extends MedRepDAO<PharmaRepEntity>{

	private static final Log log = LogFactory.getLog(PharmaRepDAO.class);


	public PharmaRepEntity findByLoginId(String loginId)
	{
		log.info("getting PharmaRepEntity instance with id: " + loginId);
		PharmaRepEntity instance = new PharmaRepEntity();
		try
		{
			instance = entityManager.createQuery("select p from PharmaRepEntity p where p.user.security.loginId = :loginId", PharmaRepEntity.class).setParameter("loginId",loginId).getSingleResult();
			log.info("get successful");
		} catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public PharmaRepEntity findByRepId(String pharmaRepId) {
		log.info("getting pharmarep instance with status: " + pharmaRepId);
		PharmaRepEntity pharmaRepEntity = new PharmaRepEntity();
		try
		{
			Integer medRepId = Integer.parseInt(pharmaRepId);
			pharmaRepEntity = entityManager.createQuery("select d from PharmaRepEntity d where d.repId = :repID", PharmaRepEntity.class).setParameter("repID",medRepId).getSingleResult();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return pharmaRepEntity;
	}

	public PharmaRepEntity findBySecurityId(Integer userSecId) {
		log.info("getting PharmaRepEntity instance with id: " + userSecId);
		PharmaRepEntity instance = new PharmaRepEntity();
		try
		{

			instance = entityManager.createQuery("select p from PharmaRepEntity p where p.user.security.userSecId = :userSecId", PharmaRepEntity.class).setParameter("userSecId",userSecId).getSingleResult();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}


	public List<PharmaRepEntity> findByName(String name) {
		log.info("getting PharmaRepEntities instance with name: " + name);
		List<PharmaRepEntity> repEntities = new ArrayList<PharmaRepEntity>();
		try
		{
			StringTokenizer tokenizer = new StringTokenizer(name, " ");
			String first = tokenizer.nextToken();
			String last = "";
			if(tokenizer.hasMoreTokens())
			{
				last = tokenizer.nextToken();
			}
			repEntities = entityManager.createQuery("select p from PharmaRepEntity p where SOUNDEX(p.user.firstName) like SOUNDEX(:first) or SOUNDEX(p.user.lastName) like SOUNDEX(:last) or SOUNDEX(p.user.firstName) like SOUNDEX(:last) or SOUNDEX(p.user.lastName) like SOUNDEX(:first)", PharmaRepEntity.class).setParameter("first",first).setParameter("last",last).getResultList();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);

		}
		return repEntities;
	}

	public PharmaRepEntity findByMobileNumber(String  mobileNo) {
		log.info("getting PharmaRepEntity instance with id: " + mobileNo);
		PharmaRepEntity instance = new PharmaRepEntity();
		try
		{

			instance = entityManager.createQuery("select p from PharmaRepEntity p where p.user.mobileNo = :mobileNo", PharmaRepEntity.class).setParameter("mobileNo",mobileNo).getSingleResult();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<PharmaRepEntity> findByStatus(String status) {
		log.info("getting PharmaRepEntity instance with status: " + status);
		List<PharmaRepEntity> instance = new ArrayList<PharmaRepEntity>();
		try
		{

			instance = entityManager.createQuery("select p from PharmaRepEntity p where p.status = :status", PharmaRepEntity.class).setParameter("status",status).getResultList();
			log.info("get successful");
		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<PharmaRepEntity> findAllExceptDeleted() {
		log.info("getting PharmaRepEntity instance except deleted ");
		List<PharmaRepEntity> instance = new ArrayList<PharmaRepEntity>();
		try
		{
			String status = "Deleted";
			instance = entityManager.createQuery("select p from PharmaRepEntity p where p.status != :status", PharmaRepEntity.class).setParameter("status",status).getResultList();
			log.info("get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<PharmaRepEntity> findByCompanyId(Integer companyId) {
		log.info("getting PharmaRepEntity instance based on company Id :" + companyId);
		List<PharmaRepEntity> instance = new ArrayList<PharmaRepEntity>();
		try
		{
			instance = entityManager.createQuery("select p from PharmaRepEntity p where p.companyId = :companyId", PharmaRepEntity.class).setParameter("companyId",companyId).getResultList();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<PharmaRepEntity> findByRoleIdCompanyId(Integer companyId, Integer roleId) {
		log.info("getting PharmaRepEntity instance based on company Id :" + companyId);
		List<PharmaRepEntity> instance = new ArrayList<PharmaRepEntity>();
		try
		{
			instance = entityManager.createQuery("select p from PharmaRepEntity p where p.companyId = :companyId and p.user.role.roleId = :roleId", PharmaRepEntity.class).setParameter("companyId",companyId).setParameter("roleId",roleId).getResultList();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<PharmaRepEntity> findRepByManagerId(String managerEmail)
	{
		log.info("getting PharmaRepEntityManager instance based on manager Id :" + managerEmail);
		List<PharmaRepEntity> instance = new ArrayList<PharmaRepEntity>();
		try
		{
			instance = entityManager.createQuery("select p from PharmaRepEntity p where p.managerEmail = :managerEmail", PharmaRepEntity.class).setParameter("managerEmail",managerEmail).getResultList();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<PharmaRepEntity> findRepsByLocation(List<String> zipCodes,List<String> cities) {
		List<PharmaRepEntity> instance = new ArrayList<PharmaRepEntity>();
		try
		{
			instance = entityManager.createQuery("select pre from PharmaRepEntity pre,LocationEntity le where le.user.userId=pre.user.userId and pre.user.role.roleId=3 and le.zipcode in (:zipCodes) or le.city in (:cities)", PharmaRepEntity.class)
					.setParameter("zipCodes",zipCodes)
					.setParameter("cities", cities).getResultList();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<PharmaRepEntity> findRepsByLocationInCompany(List<String> zipCodes,List<String> cities,Integer companyId) {
		List<PharmaRepEntity> instance = null;
		try
		{
			instance = entityManager.createQuery("select distinct pre from PharmaRepEntity pre,LocationEntity le where  pre.companyId=:companyId and pre.user.role.roleId=3 and pre.status='Active' and le.user.userId=pre.user.userId and (le.zipcode in (:zipCodes) or le.city in (:cities))", PharmaRepEntity.class)
					.setParameter("zipCodes",zipCodes)
					.setParameter("companyId", companyId)
					.setParameter("cities", cities).getResultList();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<PharmaRepEntity> findByCoveredAreaInCompany(String location,Integer companyId) {
		List<PharmaRepEntity> instance = null;
		try
		{
			if(!Util.isEmpty(location))
			instance = entityManager.createQuery("select distinct pre from PharmaRepEntity pre where pre.companyId=:companyId  and pre.user.role.roleId=3 and pre.status='Active' and (LOCATE( UPPER(pre.coveredZone),:location)>0 or LOCATE(UPPER(pre.coveredArea),:location)>0)", PharmaRepEntity.class)
					.setParameter("location",location.toUpperCase())
					.setParameter("companyId", companyId)
					 .getResultList();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		log.info("found "+((instance!=null&&instance.isEmpty())?0:instance.size())+" pharma reps in the city");
		return instance;
	}
}