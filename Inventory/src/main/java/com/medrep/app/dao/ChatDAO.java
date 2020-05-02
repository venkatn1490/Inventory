package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.ChatEntity;
import com.medrep.app.entity.VideoEntity;

/**
 * DAO object for domain model class Area.
 * @see com.medrep.app.dao.AreaEntity
 * @author Hibernate Tools
 */
@Repository
public class ChatDAO extends MedRepDAO<ChatEntity>{

	private static final Log log = LogFactory.getLog(ChatEntity.class);

	public List<ChatEntity> getDocChatConsultings(Integer doctorId){
		List<ChatEntity> chatEntites =new ArrayList<ChatEntity>();
		log.info("getting Device list");
		try
		{
			chatEntites = entityManager.createQuery("SELECT p FROM ChatEntity p  where p.doctorId = :doctorId and p.docStatus !=: docStatus  ", ChatEntity.class)
					.setParameter("doctorId", doctorId).setParameter("docStatus", "Y").getResultList();


		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return chatEntites;
	}
	public List<ChatEntity> getPatChatConsultings(Integer patientId){
		List<ChatEntity> chatEntites =new ArrayList<ChatEntity>();
		log.info("getting Device list");
		try
		{
			chatEntites = entityManager.createQuery("SELECT p FROM ChatEntity p  where p.patientId = :patientId and p.patStatus =:patStatus  ", ChatEntity.class)
					.setParameter("patientId", patientId).setParameter("patStatus", "Y").getResultList();


		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return chatEntites;
	}	
	
	public List<ChatEntity> findByAppoinmentId(Integer appointId){
		List<ChatEntity> chatEntites =new ArrayList<ChatEntity>();
		log.info("getting Device list");
		try
		{
			chatEntites = entityManager.createQuery("SELECT p FROM ChatEntity p  where p.appointmentId = :appointId ", ChatEntity.class)
					.setParameter("appointId", appointId).getResultList();


		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return chatEntites;
	}	
	
}
