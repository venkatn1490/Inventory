package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.AmbulancesEntity;
import com.medrep.app.entity.HospitalEntity;
@Repository
public class AmbulancesDAO extends MedRepDAO<AmbulancesEntity>{
	
	private static final Log LOG = LogFactory.getLog(AmbulancesDAO.class);

	
	public List<AmbulancesEntity> getAmbulancesBasedOnLocatity(String locatity,String city){
		List<AmbulancesEntity> ambulanceslist =new ArrayList<AmbulancesEntity>();
		LOG.info("getting Device list");
		try
		{
			ambulanceslist = entityManager.createQuery("SELECT p FROM AmbulancesEntity p  where p.locatity = :locatity and p.city = :city  ", AmbulancesEntity.class)
					.setParameter("locatity", locatity).setParameter("city", city).getResultList();


		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return ambulanceslist;
	}
	
	public int findByAmbulancesConnections  (String locatity,String city) {
		List result= getAmbulancesBasedOnLocatity(locatity,city);
		return result!=null?result.size():0;
	}
	
	public List<AmbulancesEntity> getAmbulancesList(){
		List<AmbulancesEntity> ambulancesList =new ArrayList<AmbulancesEntity>();
		LOG.info("getting Device list");
		try
		{
			ambulancesList = entityManager.createQuery("SELECT p FROM AmbulancesEntity p   order by p.createdOn desc  ", AmbulancesEntity.class)
					.setFirstResult(0).setMaxResults(500).getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return ambulancesList;
	}
}
