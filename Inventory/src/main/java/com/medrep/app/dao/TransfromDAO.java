package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.TransformCategoryEntity;
import com.medrep.app.entity.TransformEntity;

@Repository
public class TransfromDAO extends MedRepDAO<TransformEntity> {

	private static final Log log = LogFactory.getLog(TransfromDAO.class);

	public int deleteAllTranform() {
		int result = 0;
		log.debug("getting TNotification list");
		try {
			entityManager.createNativeQuery("DELETE FROM T_TRANSFORM where T_TRANSFORM.CREATED_BY is null and T_TRANSFORM.ID not in (select distinct T_POST_TOPIC.TRANSFORM_POST_ID from T_POST_TOPIC where T_POST_TOPIC.SOURCE=0 )").executeUpdate();
			result = 1;
		} catch (RuntimeException ex) {
			result = 0;
			log.error("get failed", ex);
		}
		return result;
	}

	public List<TransformCategoryEntity> getAllTransformSources() {
		List<TransformCategoryEntity> sourceList = new ArrayList<TransformCategoryEntity>();
		log.debug("getting TNotification list");
		try {
			sourceList = entityManager
					.createQuery("SELECT p FROM TransformCategoryEntity p", TransformCategoryEntity.class)
					.getResultList();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return sourceList;
	}

	public List<TransformCategoryEntity> getIdByTransformCategory(String categoryName) {
		List<TransformCategoryEntity> sourceList = new ArrayList<TransformCategoryEntity>();
		log.debug("getting TNotification list");
		try {
			sourceList = entityManager
					.createQuery("SELECT p FROM TransformCategoryEntity p where p.categoryName  = :categoryName", TransformCategoryEntity.class)
					.setParameter("categoryName", categoryName)
					.getResultList();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return sourceList;
	}

	public List<TransformEntity> findByCategoryId(Integer categoryId) {
		List<TransformEntity> sourceList = new ArrayList<TransformEntity>();
		log.debug("getting TransformEntity list");
		try {
			sourceList = entityManager.createQuery("SELECT p FROM TransformEntity p where p.sourceId=:categoryId or p.categoryId=:categoryId order by p.createdOn desc ", TransformEntity.class)
					.setParameter("categoryId", categoryId)
					.setMaxResults(100)
					.getResultList();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return sourceList;
	}

	public List<TransformEntity> findByCategoryId(List<Integer> categoryId) {
		List<TransformEntity> sourceList = new ArrayList<TransformEntity>();
		log.debug("getting TransformEntity list");
		try {
			sourceList = entityManager.createQuery("SELECT p FROM TransformEntity p where p.sourceId  IN (:categoryId) or p.categoryId IN (:categoryId) order by p.createdOn desc ", TransformEntity.class)
					.setParameter("categoryId", categoryId)
					.setMaxResults(100)
					.getResultList();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return sourceList;
	}

	public TransformCategoryEntity getCategoryById(Integer categoryId) {
		TransformCategoryEntity category = new TransformCategoryEntity();
		log.debug("getting TransformEntity list");
		try {
			category = entityManager.createQuery("SELECT p FROM TransformCategoryEntity p where p.categoryId=:categoryId", TransformCategoryEntity.class)
					.setParameter("categoryId", categoryId)
					.getSingleResult();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return category;
	}
	
	

	public List<String> getSubCategories(String category) {
		List<String> result = new ArrayList<String>();
		log.debug("getting TransformEntity list");
		try {
			result = entityManager.createQuery("SELECT distinct p.subCategory FROM TransformCategoryEntity p where p.categoryName=:category", String.class)
					.setParameter("category", category)
					.setMaxResults(100)
					.getResultList();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return result;
	}

	public List<TransformEntity> findByCategoryId(List<Integer> id, Date timestamp) {
		List<TransformEntity> sourceList = new ArrayList<TransformEntity>();
		try {
			sourceList = entityManager.createQuery("SELECT p FROM TransformEntity p where p.sourceId  IN (:categoryId) and p.createdOn>:timestamp order by p.createdOn desc", TransformEntity.class)
					.setParameter("categoryId", id)
					.setParameter("timestamp", timestamp)
					.setMaxResults(100)
					.getResultList();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return sourceList;
	}

	public List<TransformEntity> findByCategoryId(Integer id, Date timestamp) {
		List<TransformEntity> sourceList = new ArrayList<TransformEntity>();
		try {
			sourceList = entityManager.createQuery("SELECT p FROM TransformEntity p where p.sourceId=:categoryId or p.categoryId=:categoryId and p.createdOn>:timestamp order by p.createdOn desc", TransformEntity.class)
					.setParameter("categoryId", id)
					.setParameter("timestamp", timestamp)
					.setMaxResults(100)
					.getResultList();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return sourceList;
	}

	public TransformEntity findByTitle(String title) {
		TransformEntity sourceList = null;
		try {
			sourceList = entityManager.createQuery("SELECT p FROM TransformEntity p where p.title=:title", TransformEntity.class)
					.setParameter("title", title).getSingleResult();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return sourceList;
	}

	public List<TransformEntity> findByTherapeuticArea() {
		List<TransformEntity> sourceList = null;
		try {
			sourceList = entityManager.createQuery("SELECT p FROM TransformEntity p where p.therapeuticId is not null", TransformEntity.class)
					.getResultList();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return sourceList;
	}

	public List<TransformEntity> findByTherapeuticArea(Date date) {
		List<TransformEntity> sourceList = null;
		try {
			sourceList = entityManager.createQuery("SELECT p FROM TransformEntity p where p.therapeuticId is not null and p.createdOn>:date", TransformEntity.class)
					.setParameter("date", date)
					.getResultList();
		} catch (RuntimeException ex) {
			log.error("get failed", ex);
		}
		return sourceList;
	}
}
