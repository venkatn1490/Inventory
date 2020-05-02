package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorInvitationEntity;
import com.medrep.app.entity.DoctorNotificationEntity;
import com.medrep.app.model.DoctorInvitation;


@Repository
public class DoctorPlusDAO  extends MedRepDAO<DoctorInvitationEntity>{

	private static final Log log = LogFactory.getLog(DoctorPlusDAO.class);
	
	public List<DoctorInvitationEntity> findByDoctorId(Integer doctorId,String status) {
		log.info("getting DoctorAppointment instance with doctorId: " + doctorId);
		List<DoctorInvitationEntity> instances = new ArrayList<DoctorInvitationEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorInvitationEntity d Where d.doctorId = :doctorId  and d.inviteStatus = :status",DoctorInvitationEntity.class).setParameter("doctorId", doctorId).setParameter("status", status).getResultList();
			log.info("get successful");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			
		}
		return instances;
	}

	@Transactional
	public List<DoctorInvitationEntity> findDoctorConnections(Integer doctorId,String status) {
		log.info("getting DoctorAppointment instance with doctorId: " + doctorId);
		List<DoctorInvitationEntity> instances = new ArrayList<DoctorInvitationEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorInvitationEntity d Where (d.doctorId = :doctorId or d.invitedDoctorId = :doctorId) and d.inviteStatus = :status",DoctorInvitationEntity.class).setParameter("doctorId", doctorId).setParameter("status", status).getResultList();
			log.info("get successful");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			
		}
		return instances;
	}
	
	public List<DoctorInvitationEntity> findPendingConnections(Integer doctorId,String status) {
		log.info("getting DoctorAppointment instance with doctorId: " + doctorId);
		List<DoctorInvitationEntity> instances = new ArrayList<DoctorInvitationEntity>();
		try {
			instances = entityManager.createQuery("select d from DoctorInvitationEntity d Where ( d.invitedDoctorId = :doctorId) and d.inviteStatus = :status",DoctorInvitationEntity.class).setParameter("doctorId", doctorId).setParameter("status", status).getResultList();
			log.info("get successful");
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			
		}
		return instances;
	}

	
	public DoctorInvitationEntity findByDoctorInvitation(Integer doctorID, Integer invitedDoctorId) {
		log.info("getting Doctors Invitaion with Id and date : " + doctorID +  " : " + invitedDoctorId);
		
		DoctorInvitationEntity doctorInvitationEntity = new DoctorInvitationEntity();
		try
		{
			doctorInvitationEntity = entityManager.createQuery("select d from DoctorInvitationEntity d where d.doctorId = :doctorId and d.invitedDoctorId = :invitedDoctorId", DoctorInvitationEntity.class).setParameter("doctorId",doctorID).setParameter("invitedDoctorId",invitedDoctorId).getSingleResult();
			log.info("get successful");
			
			
		} 
		catch (RuntimeException re) 
		{
			log.error("get failed", re);
		}
		
		return doctorInvitationEntity;
	}
	
	
	public List<DoctorInvitationEntity> findDoctorAlreadyRegistered(Integer doctorID, Integer invitedDoctorId) {
		log.info("getting Doctors Invitaion with Id and date : " + doctorID +  " : " + invitedDoctorId);
		
		List<DoctorInvitationEntity> instances = new ArrayList<DoctorInvitationEntity>();
		try
		{
			instances = entityManager.createQuery("select d from DoctorInvitationEntity d where (d.doctorId = :doctorId and d.invitedDoctorId = :invitedDoctorId) or (d.doctorId = :invitedDoctorId and d.invitedDoctorId = :doctorId)", DoctorInvitationEntity.class).setParameter("doctorId",doctorID).setParameter("invitedDoctorId",invitedDoctorId).getResultList();
			log.info("get successful");
			
			
		} 
		catch (RuntimeException re) 
		{
			log.error("get failed", re);
		}
		
		return instances;
	}
}
