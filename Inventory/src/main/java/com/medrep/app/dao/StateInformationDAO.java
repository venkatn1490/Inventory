package com.medrep.app.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.StateInformationEntity;
@Repository
public class StateInformationDAO extends MedRepDAO<StateInformationEntity>  {
	private static final Log log = LogFactory.getLog(StateInformationDAO.class);
	
	public List<String> fetchAllStateData() {
		List<String> activities = null;
		try {
			activities = entityManager.createNativeQuery("SELECT DISTINCT STATENAME FROM M_STATE_INFORMATION  order by STATENAME ").getResultList();

			log.info("get successful");

		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
		}
		return activities;
	}
	
	public List<String> findDistrictByState(String stateId) {
		List<String> activities = null;
		try {
			activities = entityManager.createNativeQuery("SELECT DISTINCT DISTRICTNAME  FROM M_STATE_INFORMATION WHERE STATENAME= :stateId  order by DISTRICTNAME").setParameter("stateId", stateId).getResultList();

			log.info("get successful");

		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
		}
		return activities;
	}
	
	public List<String> findSubDistrictByDistrict(String stateid,String  districtName) {
		List<String> activities = null;
		try {
			activities = entityManager.createNativeQuery("SELECT DISTINCT SUBDISTRICTNAME FROM M_STATE_INFORMATION WHERE  STATENAME= :stateId and DISTRICTNAME= :districtName  order by SUBDISTRICTNAME").setParameter("stateId", stateid).setParameter("districtName", districtName).getResultList();

			log.info("get successful");

		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
		}
		return activities;
	}
	


}
