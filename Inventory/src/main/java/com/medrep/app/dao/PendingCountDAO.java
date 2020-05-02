package com.medrep.app.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.PendingCountsEntity;

@Repository
public class PendingCountDAO extends MedRepDAO<PendingCountsEntity> {

	private static final Log log = LogFactory.getLog(PendingCountDAO.class);

	public PendingCountsEntity findByUserId(Integer userId) {
		PendingCountsEntity pendingCountsEntity=null;
		try {
			pendingCountsEntity = entityManager
					.createQuery("select pc from PendingCountsEntity pc where pc.userId = :userId", PendingCountsEntity.class)
					.setParameter("userId", userId).getSingleResult();
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return pendingCountsEntity;
	}

	public PendingCountsEntity findByLoginId(String loginId) {
		PendingCountsEntity pendingCountsEntity=null;
		try {
			pendingCountsEntity = entityManager
					.createQuery("select pc from PendingCountsEntity pc,UserEntity ue where pc.userId = ue.userId and ue.security.loginId=:loginId", PendingCountsEntity.class)
					.setParameter("loginId", loginId).getSingleResult();
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return pendingCountsEntity;
	}

}