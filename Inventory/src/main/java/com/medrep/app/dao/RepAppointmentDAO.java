package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.entity.RepAppointmentEntity;

/**
 * Home object for domain model class TRepAppointment.
 * @see com.medrep.app.dao.RepAppointmentEntity
 * @author Hibernate Tools
 */
@Repository
public class RepAppointmentDAO extends MedRepDAO<RepAppointmentEntity>{

	private static final Log log = LogFactory.getLog(RepAppointmentDAO.class);

	public List<RepAppointmentEntity> findByPharmaRepId(Integer pharmaRepId, Date startDate) {
		log.info("getting DoctorAppointment instance with doctorId: " + pharmaRepId);
		List<RepAppointmentEntity> instances = new ArrayList<RepAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from RepAppointmentEntity d Where d.repId = :pharmaRepId and d.startDate >= :startDate",RepAppointmentEntity.class).setParameter("pharmaRepId", pharmaRepId).setParameter("startDate", startDate).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<RepAppointmentEntity> findByPharmaRepId(Integer pharmaRepId, String status) {
		log.info("getting DoctorAppointment instance with doctorId: " + pharmaRepId);
		List<RepAppointmentEntity> instances = new ArrayList<RepAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from RepAppointmentEntity d Where d.repId = :pharmaRepId and d.status = :status order by createdOn desc",RepAppointmentEntity.class).setParameter("pharmaRepId", pharmaRepId).setParameter("status", status).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<RepAppointmentEntity> findByPharmaManagerId(String emailId, String status) {
		log.info("getting DoctorAppointment instance with Manager Id : " + emailId);
		List<RepAppointmentEntity> instances = new ArrayList<RepAppointmentEntity>();
		try {
			List reps = entityManager.createNativeQuery("SELECT REP_ID FROM T_PHARMA_REP WHERE MANAGER_EMAIL = :emailId").setParameter("emailId", emailId).getResultList();
			instances = entityManager.createQuery("select distinct d from RepAppointmentEntity d Where d.repId in :pharmaReps and d.status = :status",RepAppointmentEntity.class).setParameter("pharmaReps", reps).setParameter("status", status).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public RepAppointmentEntity findByRepAppointmentId(Integer pharmaRepId, Integer appointmentId) {
		log.info("getting DoctorAppointment instance with doctorId: " + pharmaRepId +",Appointment ID:"+appointmentId);
		RepAppointmentEntity instances = null;
		try {
			instances = entityManager.createQuery("select d from RepAppointmentEntity d Where d.repId = :pharmaRepId and d.appointmentId = :appointmentId",RepAppointmentEntity.class).setParameter("pharmaRepId", pharmaRepId).setParameter("appointmentId", appointmentId).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re) {
		}
		return instances;
	}

	public void updateStatusByAppointmentId(String status, Integer appointmentId, Integer repId) {
		log.info("getting DoctorAppointment instance with doctorId: " + appointmentId);
		try {
			entityManager.createNativeQuery("update T_REP_APPOINTMENT SET STATUS = '"+status+ "' where APPOINTMENT_ID = " + appointmentId + " and REP_ID != " + repId).executeUpdate();
			log.info("get successful");

		} catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
	}

	public List<RepAppointmentEntity> findAppointmentForMedRep(Integer repId, Date startDate) {
		List<RepAppointmentEntity> instances = new ArrayList<RepAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from RepAppointmentEntity d Where d.repId = :repId and d.startDate = :startDate ",RepAppointmentEntity.class)
					.setParameter("repId", repId)
					.setParameter("startDate", startDate)
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findAppointmentForMedRep(Integer pharmaRepId, Date startDate,Date endDate) {
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select distinct d from DoctorAppointmentEntity d Where d.pharmaRepId = :pharmaRepId and (d.startDate=:startDate or d.startDate between :startDate and :endDate or d.startDate+(d.duration/(24*60)) between :startDate and :endDate)",DoctorAppointmentEntity.class)
					.setParameter("pharmaRepId", pharmaRepId)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findAppointmentForMedRep(Integer pharmaRepId, Date startDate,Date endDate,Integer appointmentId) {
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.pharmaRepId = :pharmaRepId and d.appointmentId!=:appointmentId and (d.startDate between :startDate and :endDate or d.startDate+(d.duration/(24*60)) between :startDate and :endDate)",DoctorAppointmentEntity.class)
					.setParameter("pharmaRepId", pharmaRepId)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("appointmentId", appointmentId)
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return instances;
	}
}