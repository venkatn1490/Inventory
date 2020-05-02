package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.MedicalDeviceEntity;
import com.medrep.app.entity.TransformEntity;

@Repository
public class MedicalDeviceDao extends MedRepDAO<MedicalDeviceEntity>{
	
	private static final Log LOG = LogFactory.getLog(MedicalDeviceDao.class);
	
	public List<MedicalDeviceEntity> getDeviceList(){
		List<MedicalDeviceEntity> deviceList =new ArrayList<MedicalDeviceEntity>();
		LOG.info("getting Device list");
		try
		{
			deviceList = entityManager.createQuery("SELECT p FROM MedicalDeviceEntity p where  p.status = :status  order by p.createdOn desc  ", MedicalDeviceEntity.class).setParameter("status", "Active")
					.setFirstResult(0).setMaxResults(30).getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return deviceList;
	}
	
public MedicalDeviceEntity findBydevice_id(Integer deviceId){
		
		LOG.debug("getting Medical Device Name");
		MedicalDeviceEntity medicalDeviceEntity = null;
		try {
			medicalDeviceEntity = entityManager.createQuery("select a from MedicalDeviceEntity a where a.device_id = :deviceId",MedicalDeviceEntity.class).setParameter("device_Id", deviceId).getSingleResult();
			LOG.debug("get successful");
			
		} catch (RuntimeException re) {
			LOG.error("get failed", re);
		}
		return medicalDeviceEntity;
		
	}

public List<MedicalDeviceEntity> findByCompanyId(String companyId,String therapeutic_id){
	
	LOG.debug("getting Medical Device Name");
	List<MedicalDeviceEntity> medicalDeviceEntity = new ArrayList<MedicalDeviceEntity>();
	try {
		
		medicalDeviceEntity = entityManager.createQuery("select a from MedicalDeviceEntity a where a.companyId = :companyId and a.therapeuticId = :therapeutic_id ",MedicalDeviceEntity.class).setParameter("companyId", companyId).setParameter("therapeutic_id", therapeutic_id).getResultList();
		LOG.debug("get successful");
		
	} catch (RuntimeException re) {
		LOG.error("get failed", re);
	}
	return medicalDeviceEntity;
	
}

public List<Object[]> findByCompanyIdwithTheraputicid(Integer companyId){
	
	LOG.debug("getting Medical Device Name");
	List<Object[]> activities = new ArrayList<Object[]>();
	try {
		
		activities=entityManager.createQuery("SELECT distinct p.therapeuticId FROM MedicalDeviceEntity p WHERE p.companyId =:companyId").setParameter("companyId",Integer.toString( companyId)).getResultList();
	
		LOG.debug("get successful");
		
	} catch (RuntimeException re) {
		LOG.error("get failed", re);
	}
	return activities;
	
}

}
