package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.constants.Status;
import com.medrep.app.entity.ConnectionEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.util.Util;

@Repository
public class ConnectionDAO extends MedRepDAO<ConnectionEntity>{

	private static final Log log = LogFactory.getLog(ConnectionDAO.class);

	public List<ConnectionEntity> findAll() {
		log.info("getting All ConnectionEntity instances");
		List<ConnectionEntity> instances = new ArrayList<ConnectionEntity>();
		try {
			instances = entityManager.createQuery("select a from ConnectionEntity a",ConnectionEntity.class).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public List<ConnectionEntity> findByDocID(Integer docID) {
		log.info("getting All ConnectionEntity instances");
		List<ConnectionEntity> instances = new ArrayList<ConnectionEntity>();
		try {
			instances = entityManager.createQuery("select a from ConnectionEntity a where a.docID = :docID",ConnectionEntity.class)
					.setParameter("docID", docID)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}
	public ConnectionEntity findByDocIDandConID(Integer docID,Integer connID) {
		log.info("getting All ConnectionEntity instances");
		ConnectionEntity instances = new ConnectionEntity();
		try {
			instances = entityManager.createQuery("select a from ConnectionEntity a where a.docID = :docID and a.connID = :connID ",ConnectionEntity.class)
					.setParameter("docID", docID)
					.setParameter("connID", connID)
					.getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public List<ConnectionEntity> findActiveByDocID(Integer docID) {
		log.info("getting All ConnectionEntity instances");
		List<ConnectionEntity> instances = new ArrayList<ConnectionEntity>();
		try {
			instances = entityManager.createQuery("select a from ConnectionEntity a where a.docID = :docID and a.status = 'ACTIVE' ",ConnectionEntity.class)
					.setParameter("docID", docID)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public List<ConnectionEntity> findActiveAndPendingByDocID(Integer docID) {
		log.info("getting All ConnectionEntity instances");
		List<ConnectionEntity> instances = new ArrayList<ConnectionEntity>();
		try {
			instances = entityManager.createQuery("select distinct a from ConnectionEntity a where (a.docID = :docID and a.status IN ('ACTIVE','PENDING')) or (a.connID=:docID and a.status='PENDING')",ConnectionEntity.class)
					.setParameter("docID", docID)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public List<ConnectionEntity> findByConnectionId(Integer docID) {
		log.info("getting All ConnectionEntity instances");
		List<ConnectionEntity> instances = new ArrayList<ConnectionEntity>();
		try {
			instances = entityManager.createQuery("select a from ConnectionEntity a where a.docID = :docID",ConnectionEntity.class)
					.setParameter("docID", docID)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public List<ConnectionEntity> findByConnectionStatus(Integer docID, String status) {
		log.info("getting All ConnectionEntity instances");
		List<ConnectionEntity> instances = new ArrayList<ConnectionEntity>();
		try {
			instances = entityManager.createQuery("select a from ConnectionEntity a where a.docID = :docID and a.status = :status ",ConnectionEntity.class)
					.setParameter("status", status)
					.setParameter("docID", docID)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public List<ConnectionEntity> findByPendingConnectionStatus(Integer docID, String status) {
		log.info("getting All ConnectionEntity instances");
		List<ConnectionEntity> instances = new ArrayList<ConnectionEntity>();
		try {
			instances = entityManager.createQuery("select a from ConnectionEntity a where a.connID = :docID and a.status = :status ",ConnectionEntity.class)
					.setParameter("status", status)
					.setParameter("docID", docID)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public int findPendingConnectionsCount(Integer docID) {
		List result= findByPendingConnectionStatus(docID, Status.PENDING);
		return result!=null?result.size():0;
	}

	public ConnectionEntity findByConnectionAndDocId(Integer docID, Integer connId) {
		log.info("getting All ConnectionEntity instances");
		ConnectionEntity instances = new ConnectionEntity();
		try {
			instances = entityManager.createQuery("select a from ConnectionEntity a where a.docID = :docID and a.connID = :connId",ConnectionEntity.class)
					.setParameter("docID", docID)
					.setParameter("connId", connId)
					.getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	@Override
	public void persist(ConnectionEntity e) throws RuntimeException {
		log.info("Persisting Entity Instance");
		try
		{
			entityManager.persist(e);
			log.info("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
}
