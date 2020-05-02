package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.PharmaWebinarEntity;

@Repository
public class PharmaWebinarDAO extends MedRepDAO<PharmaWebinarEntity> {

	private static final Log log = LogFactory.getLog(PharmaWebinarDAO.class);

	public List<PharmaWebinarEntity> getAdminWebinarsList(){
		List<PharmaWebinarEntity> webinarList =new ArrayList<PharmaWebinarEntity>();
		log.info("getting webinar List ");
		try
		{
			webinarList = entityManager.createQuery("SELECT p FROM PharmaWebinarEntity p", PharmaWebinarEntity.class).getResultList();
			
		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return webinarList;
	}
	
	public List<PharmaWebinarEntity> getWebinarsList(String status,Integer companyId){
		List<PharmaWebinarEntity> webinarList =new ArrayList<PharmaWebinarEntity>();
		log.info("getting webinar List  by type");
		try
		{
			webinarList = entityManager.createQuery("SELECT p FROM PharmaWebinarEntity p  where status = :status and companyId = :companyId", PharmaWebinarEntity.class)
					.setParameter("status", status).setParameter("companyId", companyId).getResultList();
			
		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return webinarList;
	}
	
}
