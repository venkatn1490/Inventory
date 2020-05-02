package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.constants.Status;
import com.medrep.app.entity.HospitalEntity;
@Repository

public class HospitalDAO  extends MedRepDAO<HospitalEntity>{
	
	private static final Log LOG = LogFactory.getLog(HospitalDAO.class);

	
	public List<HospitalEntity> getHospitalBasedOnLocatity(String locatity,String city){
		List<HospitalEntity> hospitallist =new ArrayList<HospitalEntity>();
		LOG.info("getting Device list");
		try
		{
			hospitallist = entityManager.createQuery("SELECT p FROM HospitalEntity p  where p.locatity = :locatity and p.city = :city ", HospitalEntity.class)
					.setParameter("locatity", locatity).setParameter("city", city).getResultList();


		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return hospitallist;
	}
	
	public int findByHosptialConnections  (String locatity,String city) {
		List result= getHospitalBasedOnLocatity(locatity,city);
		return result!=null?result.size():0;
	}
	
	public List<HospitalEntity> getHospitalList(){
		List<HospitalEntity> hospitallist =new ArrayList<HospitalEntity>();
		LOG.info("getting Device list");
		try
		{
			hospitallist = entityManager.createQuery("SELECT p FROM HospitalEntity p   order by p.createdOn desc  ", HospitalEntity.class)
					.setFirstResult(0).setMaxResults(500).getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return hospitallist;
	}

}
