package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.util.Util;

@Repository
public class DeviceTokenDAO extends MedRepDAO<DeviceTokenEntity> {

	private static final Log log = LogFactory.getLog(DeviceTokenDAO.class);

	public List<DeviceTokenEntity> findAll() {
		log.info("getting DeviceTokenEntity instances according to Doctor ID's");

		List<DeviceTokenEntity> instance = new ArrayList<DeviceTokenEntity>();
		try {
			instance = entityManager.createQuery("select d from DeviceTokenEntity d", DeviceTokenEntity.class).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;

	}

	public List<DeviceTokenEntity> findByDoctorId(Integer doctorId) {
		List<DeviceTokenEntity> instance = new ArrayList<DeviceTokenEntity>();
		try {
			instance = entityManager.createQuery("select d from DeviceTokenEntity d,DoctorEntity de where d.docId=:docId and d.docId=de.doctorId and de.status='Active' and de.user.status='Active'", DeviceTokenEntity.class).setParameter("docId", doctorId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;
	}

	public List<DeviceTokenEntity> findActiveDoctors() {
		List<DeviceTokenEntity> instance = new ArrayList<DeviceTokenEntity>();
		try {
			instance = entityManager.createQuery("select d from DeviceTokenEntity d where d.docId in (select de.doctorId from DoctorEntity de where de.status='Active' and de.user.status='Active') ", DeviceTokenEntity.class).getResultList();
			log.info("get successful");

		} catch (Exception re) {
			re.printStackTrace();
		}
		return instance;
	}

	public List<DeviceTokenEntity> findBySingleDoctorId(int doctorId) {
		List<DeviceTokenEntity> instance = null;
		try {
			instance = entityManager.createQuery("select d from DeviceTokenEntity d where d.docId=:docId order by d.cdate desc ", DeviceTokenEntity.class).setParameter("docId", doctorId).getResultList();
			log.info("get successful");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;
	}
	
	public List<DeviceTokenEntity> findBySinglePatientId(Integer patId) {
		List<DeviceTokenEntity> instance = null;
		try {
			instance = entityManager.createQuery("select d from DeviceTokenEntity d where d.patId=:patId order by d.cdate desc ", DeviceTokenEntity.class).setParameter("patId", patId).getResultList();
			log.info("get successful");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;
	}
	
	private List<DeviceTokenEntity> findByDeviceToken(String deviceToken) {
		List<DeviceTokenEntity> instance = new ArrayList<DeviceTokenEntity>();
		try {
			instance = entityManager.createQuery("select d from DeviceTokenEntity d where d.deviceToken=:deviceToken ", DeviceTokenEntity.class)
					.setParameter("deviceToken", deviceToken).getResultList();

		} catch (Exception re) {
			re.printStackTrace();
		}
		return instance;

	}

	public List<DeviceTokenEntity> findByRepId(Integer repId) {
		List<DeviceTokenEntity> instance = new ArrayList<DeviceTokenEntity>();
		try {
			instance = entityManager.createQuery("select d from DeviceTokenEntity d,PharmaRepEntity p where p.repId=:repId and p.repId=d.repId and p.status='Active' and p.user.status='Active')", DeviceTokenEntity.class).setParameter("repId", repId).getResultList();
		} catch (RuntimeException re) {
		}
		return instance;
	}

	public List<DeviceTokenEntity> findByManagerId(Integer repId) {
		List<DeviceTokenEntity> instance = new ArrayList<DeviceTokenEntity>();
		try {
			instance = entityManager.createQuery("select d from DeviceTokenEntity d,PharmaRepEntity p where p.repId=:repId and p.repId=d.repMgrId and p.status='Active' and p.user.status='Active')", DeviceTokenEntity.class).setParameter("repId", repId).getResultList();
		} catch (RuntimeException re) {
		}
		return instance;
	}

	public void deleteById(String canonicalId) {
		DeviceTokenEntity d=findById(DeviceTokenEntity.class, canonicalId);
		if(!Util.isEmpty(d))
			remove(d);


		// TODO Auto-generated method stub

	}

}
