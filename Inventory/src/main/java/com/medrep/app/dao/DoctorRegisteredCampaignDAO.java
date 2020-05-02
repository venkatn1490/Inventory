package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DiagnosticsEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorRegisteredCampaignsEntity;
import com.medrep.app.entity.PatientRegisteredCampaignEntity;
import com.medrep.app.entity.PharmaCampaginEntity;
@Repository
public class DoctorRegisteredCampaignDAO extends MedRepDAO<DoctorRegisteredCampaignsEntity>{
	
	private static final Log LOG = LogFactory.getLog(DoctorRegisteredCampaignDAO.class);

	public List<DoctorRegisteredCampaignsEntity> getDoctorRegByCamginId(Integer campaignID){
		List<DoctorRegisteredCampaignsEntity> doctorRegisteredCampaignsEntity =new ArrayList<DoctorRegisteredCampaignsEntity>();
		LOG.info("getting TNotification list");
		try
		{
			doctorRegisteredCampaignsEntity = entityManager.createQuery("SELECT p FROM DoctorRegisteredCampaignsEntity p where p.pharmaCampaginEntity.campaignId  = :campaignID", DoctorRegisteredCampaignsEntity.class).setParameter("campaignID", campaignID).getResultList();
			
		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return doctorRegisteredCampaignsEntity;
	}
	
	
	public DoctorRegisteredCampaignsEntity findByDocAndCampId(Integer  doctorId,Integer CampId){
		DoctorRegisteredCampaignsEntity doctorRegisteredCampaignsEntity =null;
		LOG.info("getting campaigns by doctorId and campId");
		try
		{
			doctorRegisteredCampaignsEntity = entityManager.createQuery("SELECT p FROM DoctorRegisteredCampaignsEntity p  where p.doctorId = :doctorId  and p.campaignID = :campaignID", DoctorRegisteredCampaignsEntity.class)
					.setParameter("doctorId", doctorId).setParameter("campaignID", CampId).getSingleResult();


		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return doctorRegisteredCampaignsEntity;
	}
	
	public List<DoctorRegisteredCampaignsEntity> findRegisteredCampaign(Integer  doctorId){
		List<DoctorRegisteredCampaignsEntity> doctorRegisteredCampaignsEntity =new ArrayList<DoctorRegisteredCampaignsEntity>();
		LOG.info("getting campaigns by patientid ");
		try
		{
			doctorRegisteredCampaignsEntity = entityManager.createQuery("SELECT p FROM DoctorRegisteredCampaignsEntity p  where p.doctorId = :doctorId  ", DoctorRegisteredCampaignsEntity.class)
					.setParameter("doctorId", doctorId).getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return doctorRegisteredCampaignsEntity;
	}
	public List<DoctorRegisteredCampaignsEntity> findByCampId(Integer CampId){
		List<DoctorRegisteredCampaignsEntity> doctorRegisteredCampaignsEntity =new ArrayList<DoctorRegisteredCampaignsEntity>();
		LOG.info("getting campaigns by doctorId and campId");
		try
		{
			doctorRegisteredCampaignsEntity = entityManager.createQuery("SELECT p FROM DoctorRegisteredCampaignsEntity p  where p.pharmaCampaginEntity.campaignId = :campaignID", DoctorRegisteredCampaignsEntity.class)
					.setParameter("campaignID", CampId).getResultList();


		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return doctorRegisteredCampaignsEntity;
	}
	
	public List<Object[]>  findRegisteredCountByCampaignId(Integer campaignId)
	{
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			LOG.info("Campaign Id  :"+ campaignId);

			activities = entityManager.createNativeQuery("SELECT COUNT(*) FROM P_DOCTOR_REGISTERED_CAMPAIGNS WHERE CAMPAIGN_ID = :campaignId ").setParameter("campaignId", campaignId).getResultList();
		}
		catch(Exception e)
		{
			LOG.error("get failed", e);
		}
		return activities;
	}
}
