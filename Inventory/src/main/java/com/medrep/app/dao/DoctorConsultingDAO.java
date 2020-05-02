package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.entity.DoctorConsultingsEntity;




@Repository
public class DoctorConsultingDAO  extends MedRepDAO<DoctorConsultingsEntity>{

	private static final Log log = LogFactory.getLog(DoctorConsultingDAO.class);
	
	public List<DoctorConsultingsEntity> findByDoctorId(Integer doctorId,String consultingsType) {
		log.info("getting DoctorConsultings instance with doctorId: " + doctorId);
		List<DoctorConsultingsEntity> instances = new ArrayList<DoctorConsultingsEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorConsultingsEntity d Where d.doctorId = :doctorId and d.consultingsType =:consultingsType ",DoctorConsultingsEntity.class).setParameter("doctorId", doctorId).setParameter("consultingsType", consultingsType).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}
	
	public List<DoctorConsultingsEntity> findByLocationId(String location,String city,String type) {
		log.info("getting DoctorConsultings instance with location: " + location);
		List<DoctorConsultingsEntity> instances = new ArrayList<DoctorConsultingsEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorConsultingsEntity d Where d.locatity = :location and d.city = :city and d.consultingsType = :type ",DoctorConsultingsEntity.class).setParameter("location", location)
					.setParameter("city", city)
					.setParameter("type",type)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}
	
	public DoctorConsultingsEntity findByConsultingDay(Integer consultingId,String dayName) {
		log.info("getting DoctorConsultings instance with ConsultingId: " + consultingId);
		DoctorConsultingsEntity instances=null ;
		try {// 
			instances = entityManager.createQuery("select d from DoctorConsultingsEntity d Where (UPPER(d.consultingsDays) like :dayName) and d.consultingsId = :consultingId ",DoctorConsultingsEntity.class).setParameter("consultingId", consultingId)
					.setParameter("dayName", "%"+dayName.toUpperCase().replaceAll("\\s+", "%").trim()+"%")					.getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}
	
	
	public int findByDoctorsConnections  (String locatity,String city) {
		List result= findByLocationId(locatity,city,"F2F");
		return result!=null?result.size():0;
	}
	
	public int findByOnlineDoctorsConnections  (String locatity,String city,String type) {
		List result= findByLocationId(locatity,city,type);
		return result!=null?result.size():0;
	}
	
	
}