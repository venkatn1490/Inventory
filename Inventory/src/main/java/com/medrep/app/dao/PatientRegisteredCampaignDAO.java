package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DiagnosticsEntity;
import com.medrep.app.entity.DoctorRegisteredCampaignsEntity;
import com.medrep.app.entity.PatientRegisteredCampaignEntity;
@Repository
public class PatientRegisteredCampaignDAO extends MedRepDAO<PatientRegisteredCampaignEntity>{
	
	private static final Log LOG = LogFactory.getLog(PatientRegisteredCampaignDAO.class);

	
	public PatientRegisteredCampaignEntity findByPatientAndCampId(Integer  patientId,Integer CampId){
		PatientRegisteredCampaignEntity doctorRegisteredCampaignsEntity =null;
		LOG.info("getting campaigns by patientid and campId");
		try
		{
			doctorRegisteredCampaignsEntity = entityManager.createQuery("SELECT p FROM PatientRegisteredCampaignEntity p  where p.patientId = :patientId  ", PatientRegisteredCampaignEntity.class)
					.setParameter("patientId", patientId).setParameter("campaignID", CampId).getSingleResult();


		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return doctorRegisteredCampaignsEntity;
	}
	public List<PatientRegisteredCampaignEntity> findRegisteredCampaign(Integer  patientId){
		List<PatientRegisteredCampaignEntity> patientRegisteredCampaignEntity =null;
		LOG.info("getting campaigns by patientid ");
		try
		{
			patientRegisteredCampaignEntity = entityManager.createQuery("SELECT p FROM PatientRegisteredCampaignEntity p  where p.patientId = :patientId  ", PatientRegisteredCampaignEntity.class)
					.setParameter("patientId", patientId).getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return patientRegisteredCampaignEntity;
	}
	
	public List<PatientRegisteredCampaignEntity> findRegisteredCampaignByDocIdandCampId(Integer  doctorId,Integer campId){
		List<PatientRegisteredCampaignEntity> patientRegisteredCampaignEntity =null;
		LOG.info("getting campaigns by patientid ");
		try
		{
			patientRegisteredCampaignEntity = entityManager.createQuery("SELECT p FROM PatientRegisteredCampaignEntity p  where p.doctorId = :doctorId  and p.pharmaCampaginEntity.campaignId = :campId", PatientRegisteredCampaignEntity.class)
					.setParameter("doctorId", doctorId).setParameter("campId", campId).getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return patientRegisteredCampaignEntity;
	}
	public List<Object[]>  findRegisteredCountByCampaignId(Integer campaignId)
	{
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			LOG.info("Campaign Id  :"+ campaignId);

			activities = entityManager.createNativeQuery("SELECT COUNT(*) FROM P_PATIENT_REGISTERED_CAMPAIGN WHERE CAMPAIGN_ID = :campaignId ").setParameter("campaignId", campaignId).getResultList();
		}
		catch(Exception e)
		{
			LOG.error("get failed", e);
		}
		return activities;
	}
	
	public List<PatientRegisteredCampaignEntity>  findRegPatByCampAndDoct(Integer campaignId,Integer doctorId)
	{
		List<PatientRegisteredCampaignEntity> activities =null;
		try
		{
			LOG.info("Campaign Id  :"+ campaignId);

			activities = entityManager.createQuery("SELECT p FROM PatientRegisteredCampaignEntity p  where p.pharmaCampaginEntity.campaignId = :campaignId and p.doctorId = :doctorId  ", PatientRegisteredCampaignEntity.class)
					.setParameter("doctorId", doctorId).setParameter("campaignId", campaignId).getResultList();		}
		catch(Exception e)
		{
			LOG.error("get failed", e);
		}
		return activities;
	}
	public int findRegPatCountByCampAndDoct  (Integer campaignId,Integer doctorId) {
		List result= findRegPatByCampAndDoct(campaignId,doctorId);
		return result!=null?result.size():0;
	}
	
}
