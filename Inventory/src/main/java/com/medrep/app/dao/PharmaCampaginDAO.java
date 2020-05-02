package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.PharmaCampaginEntity;

@Repository("pharmaCampaginDAO")

public class PharmaCampaginDAO extends MedRepDAO<PharmaCampaginEntity> {
	
	
	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(PharmaCampaginDAO.class);
	
	public List<PharmaCampaginEntity> getAdminCampaginList(){
		List<PharmaCampaginEntity> pharmaCampaginlist =new ArrayList<PharmaCampaginEntity>();
		log.info("getting TNotification list");
		try
		{
			pharmaCampaginlist = entityManager.createQuery("SELECT p FROM PharmaCampaginEntity p", PharmaCampaginEntity.class).getResultList();
			
		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return pharmaCampaginlist;
	}
	
	
	
	public PharmaCampaginEntity getCampaignById(Integer campaignId){
		PharmaCampaginEntity	 pharmaCampaginlist =null;
		log.info("getting TNotification list");
		try
		{
			pharmaCampaginlist = entityManager.createQuery("SELECT p FROM PharmaCampaginEntity p where p.campaignId= :campaignId", PharmaCampaginEntity.class).setParameter("campaignId",campaignId).getSingleResult();
			
		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return pharmaCampaginlist;
	}
	public List<PharmaCampaginEntity> getCampaignByCompanyId(Integer companyId){
		List<PharmaCampaginEntity>	 pharmaCampaginlist =null;
		log.info("getting TNotification list");
		try
		{
			pharmaCampaginlist = entityManager.createQuery("SELECT p FROM PharmaCampaginEntity p where p.companyId= :companyId", PharmaCampaginEntity.class).setParameter("companyId",companyId).getResultList();
			
		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return pharmaCampaginlist;
	}

}
