package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DiagnosticsEntity;
import com.medrep.app.entity.VideoEntity;

@Repository
public class VideoDAO extends MedRepDAO<VideoEntity> {

	private static final Log log = LogFactory.getLog(VideoDAO.class);

	public List<VideoEntity> getDocVideoConsultings(Integer doctorId){
		List<VideoEntity> videoEntites =new ArrayList<VideoEntity>();
		log.info("getting Device list");
		try
		{
			videoEntites = entityManager.createQuery("SELECT p FROM VideoEntity p  where p.doctorId = :doctorId and p.docStatus != :docStatus  ", VideoEntity.class)
					.setParameter("doctorId", doctorId).setParameter("docStatus", "Y").getResultList();


		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return videoEntites;
	}
	public List<VideoEntity> getPatVideoConsultings(Integer patientId){
		List<VideoEntity> videoEntites =new ArrayList<VideoEntity>();
		log.info("getting Device list");
		try
		{
			videoEntites = entityManager.createQuery("SELECT p FROM VideoEntity p  where p.patientId = :patientId and p.patStatus != :patStatus  ", VideoEntity.class)
					.setParameter("patientId", patientId).setParameter("patStatus", "Y").getResultList();


		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return videoEntites;
	}
}