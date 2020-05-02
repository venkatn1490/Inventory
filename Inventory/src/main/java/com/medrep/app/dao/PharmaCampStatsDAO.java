
package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.PharmaCampaginEntity;
import com.medrep.app.entity.PharmaCampaignStatsEntity;

@Repository("pharmaCampStatsDAO")

public class PharmaCampStatsDAO extends MedRepDAO<PharmaCampaignStatsEntity> {
	
	private static final Log log = LogFactory.getLog(PharmaCampStatsDAO.class);

	@PersistenceContext
	EntityManager entityManger;
	
	public PharmaCampaignStatsEntity findByDoctorCampaign(Integer doctorID, Integer pharmacampId) {
		log.info("getting Doctors Camp with Id and date : " + doctorID +  " : " + pharmacampId);

		PharmaCampaignStatsEntity pharmaCampaignStats = null;
		try
		{
			pharmaCampaignStats = entityManager.createQuery("select d from PharmaCampaignStatsEntity d where d.doctorId = :doctorId and d.pharmaCampaginEntity.campaignId = :pharmacampId", PharmaCampaignStatsEntity.class).setParameter("doctorId",doctorID).setParameter("pharmacampId",pharmacampId).getSingleResult();
			log.info("get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return pharmaCampaignStats;
	}
	
	public PharmaCampaignStatsEntity findByPatientCampaign(Integer patientID, Integer pharmacampId) {
		log.info("getting Patient Camp with Id and date : " + patientID +  " : " + pharmacampId);

		PharmaCampaignStatsEntity pharmaCampaignStats =null;
		try
		{
			pharmaCampaignStats = entityManager.createQuery("select d from PharmaCampaignStatsEntity d where d.patientId = :patientID and d.pharmaCampaginEntity.campaignId = :pharmacampId", PharmaCampaignStatsEntity.class).setParameter("patientID",patientID).setParameter("pharmacampId",pharmacampId).getSingleResult();
			log.info("get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return pharmaCampaignStats;
	}

	public List<PharmaCampaignStatsEntity> findByDoctorId(Integer doctorID) {
		log.info("Getting Camp with Docotor  Id  " + doctorID );

		List<PharmaCampaignStatsEntity> pharmaCampaignStatsEntity = new ArrayList<PharmaCampaignStatsEntity>();
		try
		{
			pharmaCampaignStatsEntity = entityManager.createQuery("select d from PharmaCampaignStatsEntity d where d.doctorId = :doctorID ", PharmaCampaignStatsEntity.class).setParameter("doctorID",doctorID).getResultList();
			log.info("get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return pharmaCampaignStatsEntity;
	}
	
	public List<PharmaCampaignStatsEntity> findByPatientId(Integer patientId) {
		log.info("Getting Camp with patient  Id  " + patientId );

		List<PharmaCampaignStatsEntity> pharmaCampaignStatsEntity = new ArrayList<PharmaCampaignStatsEntity>();
		try
		{
			pharmaCampaignStatsEntity = entityManager.createQuery("select d from PharmaCampaignStatsEntity d where d.patientId = :patientId ", PharmaCampaignStatsEntity.class).setParameter("patientId",patientId).getResultList();
			log.info("get successful");


		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}

		return pharmaCampaignStatsEntity;
	}
	public List<Object[]>  findPatSentPatCountByCampaignId(Integer campaignId)
	{
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			log.info("Campaign Id  :"+ campaignId);

			activities = entityManager.createNativeQuery("SELECT COUNT(*) FROM P_CAMPAIGN_STATS WHERE CAMPAIGN_ID = :campaignId and DOCTOR_ID IS  null ").setParameter("campaignId", campaignId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}
	public List<Object[]>  findDocSentPatCountByCampaignId(Integer campaignId)
	{
		List<Object[]> activities = new ArrayList<Object[]>();
		try
		{
			log.info("Campaign Id  :"+ campaignId);

			activities = entityManager.createNativeQuery("SELECT COUNT(*) FROM P_CAMPAIGN_STATS WHERE CAMPAIGN_ID = :campaignId and PATIENT_ID IS  null").setParameter("campaignId", campaignId).getResultList();
		}
		catch(Exception e)
		{
			log.error("get failed", e);
		}
		return activities;
	}
	
	public List<Integer> getDistinctUntreatedPatients(Integer campaignId)
	{
		List<Integer> categoriesList = new ArrayList<Integer>();
		log.debug("getting DistinctUntreatedPatients list");
		try{
			categoriesList = entityManager.createQuery("SELECT distinct p.patientId FROM PharmaCampaignStatsEntity p where p.pharmaCampaginEntity.campaignId = :campaignId and p.status = :status  and  p.patientId != null").setParameter("campaignId", campaignId)
					.setParameter("status", "UnRegistered").getResultList();
		}catch(Exception ex){
			log.error("get failed", ex);
		}
		return categoriesList;
	}
	public PharmaCampaignStatsEntity  findByCampaginByPatient(String campaignId,Integer patientId)
	{
		PharmaCampaignStatsEntity categoriesList = null;
		log.debug("getting DistinctUntreatedPatients list");
		try{
			categoriesList = entityManager.createQuery("SELECT  p FROM PharmaCampaignStatsEntity p where p.pharmaCampaginEntity.campaignId = :campaignId and   p.patientId = :patientId",PharmaCampaignStatsEntity.class).setParameter("patientId", patientId).setParameter("campaignId", Integer.parseInt(campaignId)).getSingleResult();
		}catch(Exception ex){
			log.error("get failed", ex);
		}
		return categoriesList;
	}
	
}