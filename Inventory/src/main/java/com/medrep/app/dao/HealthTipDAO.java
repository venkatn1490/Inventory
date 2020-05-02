package com.medrep.app.dao;
//Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.HealthTipCategoryEntity;
import com.medrep.app.entity.HealthTipEntity;
import com.medrep.app.entity.HealthTipSourceEntity;
import com.medrep.app.entity.TransformCategoryEntity;
import com.medrep.app.entity.TransformEntity;
import com.medrep.app.entity.TransformSourceEntity;
import com.medrep.app.util.Util;

/**
* DOA object for domain model class News.
* @see com.medrep.app.dao.TransformEntity
* @author Hibernate Tools
*/
@Repository
public class HealthTipDAO  extends MedRepDAO<HealthTipEntity>{

	private static final Log LOG = LogFactory.getLog(HealthTipDAO.class);

	public List<HealthTipEntity> getHealthTipList(){
		List<HealthTipEntity> transformList =new ArrayList<HealthTipEntity>();
		LOG.info("getting Transform list");
		try
		{
			transformList = entityManager.createQuery("SELECT p FROM HealthTipEntity p order by p.createdOn desc", HealthTipEntity.class)
					.setFirstResult(0).setMaxResults(30).getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return transformList;
	}


	public List<HealthTipCategoryEntity> getIdByHealthTipCategory(String categoryName) {
		List<HealthTipCategoryEntity> sourceList = new ArrayList<HealthTipCategoryEntity>();
		LOG.debug("getting TNotification list");
		try {
			sourceList = entityManager
					.createQuery("SELECT p FROM HealthTipCategoryEntity p where p.categoryName  = :categoryName", HealthTipCategoryEntity.class)
					.setParameter("categoryName", categoryName)
					.getResultList();
		} catch (RuntimeException ex) {
			LOG.error("get failed", ex);
		}
		return sourceList;
	}
	
	public HealthTipCategoryEntity getNameByHealthTipCategory(Integer categoryId) {
		HealthTipCategoryEntity sourceList = new HealthTipCategoryEntity();
		LOG.debug("getting TNotification list");
		try {
			sourceList = entityManager
					.createQuery("SELECT p FROM HealthTipCategoryEntity p where p.categoryId  = :categoryId", HealthTipCategoryEntity.class)
					.setParameter("categoryId", categoryId)
					.getSingleResult();
		} catch (RuntimeException ex) {
			LOG.error("get failed", ex);
		}
		return sourceList;
	}
	public List<HealthTipEntity> findByCategoryId(Integer id, Date timestamp) {
		List<HealthTipEntity> sourceList = new ArrayList<HealthTipEntity>();
		try {
			sourceList = entityManager.createQuery("SELECT p FROM HealthTipEntity p where p.sourceId=:categoryId or p.categoryId=:categoryId and p.createdOn>:timestamp order by p.createdOn desc", HealthTipEntity.class)
					.setParameter("categoryId", id)
					.setParameter("timestamp", timestamp)
					.setMaxResults(100)
					.getResultList();
		} catch (RuntimeException ex) {
			LOG.error("get failed", ex);
		}
		return sourceList;
	}
	public HealthTipCategoryEntity getCategoryById(Integer categoryId) {
		HealthTipCategoryEntity category = new HealthTipCategoryEntity();
		LOG.debug("getting TransformEntity list");
		try {
			category = entityManager.createQuery("SELECT p FROM HealthTipCategoryEntity p where p.categoryId=:categoryId", HealthTipCategoryEntity.class)
					.setParameter("categoryId", categoryId)
					.getSingleResult();
		} catch (RuntimeException ex) {
			LOG.error("get failed", ex);
		}
		return category;
	}
	public List<HealthTipEntity> findByCategoryId(Integer categoryId) {
		List<HealthTipEntity> sourceList = new ArrayList<HealthTipEntity>();
		LOG.debug("getting TransformEntity list");
		try {
			sourceList = entityManager.createQuery("SELECT p FROM HealthTipEntity p where p.sourceId=:categoryId or p.categoryId=:categoryId order by p.createdOn desc ", HealthTipEntity.class)
					.setParameter("categoryId", categoryId)
					.setMaxResults(100)
					.getResultList();
		} catch (RuntimeException ex) {
			LOG.error("get failed", ex);
		}
		return sourceList;
	}
	@Deprecated
	private List<HealthTipCategoryEntity> getAllHealthTipCategories()
	{
		List<HealthTipCategoryEntity> categoriesList = new ArrayList<HealthTipCategoryEntity>();
		LOG.debug("getting TNotification list");
		try{
			categoriesList = entityManager.createQuery("SELECT p FROM HealthTipCategoryEntity p order by p.createdOn", HealthTipCategoryEntity.class).getResultList();
		}catch(RuntimeException ex){
			LOG.error("get failed", ex);
		}
		return categoriesList;
	}
	
	

	public List<String> getDistinctHealthTipCategories()
	{
		List<String> categoriesList = new ArrayList<String>();
		LOG.debug("getting TNotification list");
		try{
			categoriesList = entityManager.createQuery("SELECT distinct p.categoryName FROM HealthTipCategoryEntity p order by p.createdOn").getResultList();
		}catch(Exception ex){
			LOG.error("get failed", ex);
		}
		return categoriesList;
	}

	public void createHealthTipCategory(HealthTipCategoryEntity categoryEntity)throws RuntimeException {
		LOG.info("Persisting Entity Instance");
		try
		{
			categoryEntity.setCreatedOn(Calendar.getInstance().getTime());
			entityManager.persist(categoryEntity);
			LOG.info("persist successful");
		} catch (RuntimeException re) {
			LOG.error("persist failed", re);
			throw re;
		}
	}

	public List<HealthTipSourceEntity> getAllHealthTipSources()
	{
		List<HealthTipSourceEntity> categoriesList = new ArrayList<HealthTipSourceEntity>();
		LOG.debug("getting TNotification list");
		try{
			categoriesList = entityManager.createQuery("SELECT p FROM HealthTipSourceEntity p order by p.createdOn", HealthTipSourceEntity.class).getResultList();
		}catch(RuntimeException ex){
			LOG.error("get failed", ex);
		}
		return categoriesList;
	}

	public HealthTipSourceEntity getHealthTipSourceById(Integer id)
	{
		HealthTipSourceEntity categoriesList = null;
		LOG.debug("getting TNotification list");
		try{
			categoriesList = entityManager.createQuery("SELECT p FROM HealthTipSourceEntity p where p.transformId=:id", HealthTipSourceEntity.class)
					.setParameter("id", id).getSingleResult();
		}catch(Exception ex){
			LOG.error("get failed", ex);
		}
		return categoriesList;
	}

	public void createHealthTipSource(HealthTipSourceEntity sourceEntity)throws RuntimeException {
		LOG.info("Persisting Entity Instance : createTransformSource");
		try
		{
			sourceEntity.setCreatedOn(Calendar.getInstance().getTime());
			entityManager.persist(sourceEntity);
			LOG.info("persist successful : createTransformSource");
		} catch (RuntimeException re) {
			LOG.error("persist failed : createTransformSource", re);
			throw re;
		}
	}

	public Integer getCategoryId(String categoryName) {
		Integer id=null;
		LOG.debug("getting TNotification list");
		try{
			List<HealthTipCategoryEntity> categoriesList = entityManager.createQuery("SELECT p FROM HealthTipCategoryEntity p where p.categoryName=:categoryName", HealthTipCategoryEntity.class)
					.setParameter("categoryName", categoryName).getResultList();
			if(!Util.isEmpty(categoriesList))
				return categoriesList.get(0).getCategoryId();

		}catch(RuntimeException ex){
			LOG.error("get failed", ex);
		}
		return id;
	}

}