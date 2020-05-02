package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.entity.PatientDocAppoiEntity;

@Repository
public class PatDocAppointDAO extends MedRepDAO<PatientDocAppoiEntity> {
	
	private static final Log log = LogFactory.getLog(PatDocAppointDAO.class);


	public int findNoOfAppoiDay(Integer consultingId,Date date1) {
		log.info("getting DoctorConsultings instance with consultingId: " + consultingId);
		List<PatientDocAppoiEntity> instances = new ArrayList<PatientDocAppoiEntity>();
		try {
			instances = entityManager.createQuery("select d from PatientDocAppoiEntity d Where d.doctorId = :doctorId and d.consultingId =:consultingId  and d.appointmentDate = :date1",PatientDocAppoiEntity.class).setParameter("date1", date1).setParameter("consultingId", consultingId).getResultList();

			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances!=null?instances.size():0;
	}
	
	public List<PatientDocAppoiEntity>  checkAppoinments(Integer patientId,Integer consultingId,Date date1) {
		log.info("getting DoctorConsultings instance with consultingId: " + consultingId);
		List<PatientDocAppoiEntity> instances = new ArrayList<PatientDocAppoiEntity>();
		try {
			instances = entityManager.createQuery("select d from PatientDocAppoiEntity d Where d.patientEntity.patientId = :patientId and d.consultingId =:consultingId  and d.appointmentDate = :date1",PatientDocAppoiEntity.class).setParameter("date1", date1).setParameter("consultingId", consultingId).setParameter("patientId", patientId).getResultList();

			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}
	
	
	
	public List<PatientDocAppoiEntity> findByPatientId(Integer patientId,String communicationMode) {
		log.info("getting Patient Appointment instance with PatientId: " + patientId);
		List<PatientDocAppoiEntity> instances = new ArrayList<PatientDocAppoiEntity>();
		try {
			instances = entityManager.createQuery("select d from PatientDocAppoiEntity d Where d.patientEntity.patientId = :patientId and d.communicationMode =:communicationMode order by d.appointmentDate desc",PatientDocAppoiEntity.class).setParameter("communicationMode", communicationMode).setParameter("patientId", patientId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}
	
	public List<PatientDocAppoiEntity> findByDoctorId(Integer doctorId,String communicationMode) {
		log.info("getting Patient Appointment instance with DoctorId: " + doctorId);
		List<PatientDocAppoiEntity> instances = new ArrayList<PatientDocAppoiEntity>();
		try {
			instances = entityManager.createQuery("select d from PatientDocAppoiEntity d Where d.doctorEntity.doctorId = :doctorId  and d.communicationMode =:communicationMode order by d.appointmentDate desc",PatientDocAppoiEntity.class).setParameter("communicationMode", communicationMode).setParameter("doctorId", doctorId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}
}
