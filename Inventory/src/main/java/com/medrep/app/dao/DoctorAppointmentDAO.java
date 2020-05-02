package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorAppointmentEntity;

/**
 * DAO object for domain model class TDoctorAppointment.
 * @see com.medrep.app.dao.DoctorAppointmentDAO
 * @author Hibernate Tools
 */
@Repository
public class DoctorAppointmentDAO  extends MedRepDAO<DoctorAppointmentEntity>{

	private static final Log log = LogFactory.getLog(DoctorAppointmentDAO.class);


	public DoctorAppointmentEntity findByDoctorNotificationId(Integer doctorId, Integer notificationId) {
		log.info("getting DoctorAppointment instance with doctorId: " + doctorId + " and notificationId: " + notificationId);
		DoctorAppointmentEntity instance = new DoctorAppointmentEntity();
		try {
			instance = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.doctorId = :doctorId and d.notificationId = :notificationId order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("doctorId", doctorId).setParameter("notificationId", notificationId).getSingleResult();
			log.info("get successful");
		}
		catch (Exception re)
		{
			log.error("No Entity found");
		}
		return instance;
	}


	public List<DoctorAppointmentEntity> findAppointmentsByNotificationId(Integer notificationId) {
		List<DoctorAppointmentEntity> instance = null;
		try {
			instance = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.notificationId = :notificationId order by d.startDate desc",DoctorAppointmentEntity.class)
					.setParameter("notificationId", notificationId).getResultList();
		}
		catch (Exception re)
		{
			log.error(re);
		}
		return instance;
	}
	
	public List<DoctorAppointmentEntity> findAppointmentsByDoctorId(Integer doctorId) {
		List<DoctorAppointmentEntity> instance = null;
		try {
			instance = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.doctorId = :doctorId order by d.startDate desc",DoctorAppointmentEntity.class)
					.setParameter("doctorId", doctorId).getResultList();
		}
		catch (Exception re)
		{
			log.error(re);
		}
		return instance;
	}

	public DoctorAppointmentEntity findByRepNotificationId(Integer repId, Integer notificationId) {
		log.info("getting DoctorAppointment instance with repId: " + repId + " and notificationId: " + notificationId);
		DoctorAppointmentEntity instance = new DoctorAppointmentEntity();
		try {
			instance = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.pharmaRepId = :repId and d.notificationId = :notificationId order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("repId", repId).setParameter("notificationId", notificationId).getSingleResult();
			log.info("get successful");
		}
		catch (Exception re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<DoctorAppointmentEntity> findByDoctorId(Integer doctorId, Date startDate) {
		log.info("getting DoctorAppointment instance with doctorId: " + doctorId);
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.doctorId = :doctorId and d.startDate >= :startDate order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("doctorId", doctorId).setParameter("startDate", startDate).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findByDoctorId(Integer doctorId, Date startDate,String status) {
		log.info("getting DoctorAppointment instance with doctorId: " + doctorId);
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.doctorId = :doctorId  and d.status != :status and d.startDate >= :startDate order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("doctorId", doctorId).setParameter("startDate", startDate).setParameter("status", status).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findByDoctorId(Integer doctorId) {
		log.info("getting DoctorAppointment instance with doctorId: " + doctorId);
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.doctorId = :doctorId order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("doctorId", doctorId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findByDoctorId(Integer doctorId,String status) {
		log.info("getting DoctorAppointment instance with doctorId: " + doctorId);
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.doctorId = :doctorId and d.status != :status order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("doctorId", doctorId).setParameter("status", status).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findByRepId(Integer repId, Date startDate) {
		log.info("getting Appointment instance with repId: " + repId);
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.pharmaRepId = :repId and d.startDate >= :startDate order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("repId", repId).setParameter("startDate", startDate).getResultList();
			log.info("get successful");

		} catch (RuntimeException re)
		{
			log.error("get failed", re);

		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findByRepId(Integer repId) {
		log.info("getting Appointment instance with repId: " + repId);
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.pharmaRepId = :repId order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("repId", repId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findByRepIdStatus(Integer repId, String status) {
		log.info("getting Appointment instance with repId: " + repId);
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.pharmaRepId = :repId and d.status = :status order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("repId", repId).setParameter("status", status).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findByManagerId(String managerId, Date startDate) {
		log.info("getting Appointment instance with repId: " + managerId);
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {

			List reps = entityManager.createNativeQuery("SELECT REP_ID FROM T_PHARMA_REP WHERE MANAGER_EMAIL = :managerId").setParameter("managerId", managerId).getResultList();
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.pharmaRepId in :reps and d.startDate >= :startDate order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("reps", reps).setParameter("startDate", startDate).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findByStatus(String status) {
		log.info("getting Appointment instance with status: " + status);
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.status = :status",DoctorAppointmentEntity.class).setParameter("status", status).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findByNotificationId(Integer notificationId) {
		log.info("getting Appointment instance with notificationId: " + notificationId);
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.notificationId = :notificationId order by d.startDate desc",DoctorAppointmentEntity.class).setParameter("notificationId", notificationId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findAppointmentForDoctor(Integer doctorId, Date startDate,Date endDate) {
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.doctorId = :doctorId and (d.startDate between :startDate and :endDate or d.startDate+(d.duration/(24*60)) between :startDate and :endDate)",DoctorAppointmentEntity.class)
					.setParameter("doctorId", doctorId)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return instances;
	}

	public List<DoctorAppointmentEntity> findAppointmentsOn(Date date) {
		List<DoctorAppointmentEntity> instances = new ArrayList<DoctorAppointmentEntity>();
		try {
//			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d,RepAppointmentEntity r Where d.appointmentId=r.appointmentId and r.status='Accepted' and d.reminderTime>:date and d.reminderTime<(:date+1) order by d.reminderTime asc",DoctorAppointmentEntity.class)
			instances = entityManager.createQuery("select d from DoctorAppointmentEntity d Where d.reminderTime between :date and :ceilDate order by d.reminderTime asc",DoctorAppointmentEntity.class)
			.setParameter("date", date)
			.setParameter("ceilDate", new Date(date.getTime()+24*60*60*1000))
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return instances;
	}

}