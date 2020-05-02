package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.NewsSourceEntity;
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
public class TransformDAO  extends MedRepDAO<TransformEntity>{

	private static final Log LOG = LogFactory.getLog(TransformDAO.class);

	public List<TransformEntity> getTransformList(){
		List<TransformEntity> transformList =new ArrayList<TransformEntity>();
		LOG.info("getting Transform list");
		try
		{
			transformList = entityManager.createQuery("SELECT p FROM TransformEntity p order by p.createdOn desc", TransformEntity.class)
					.setFirstResult(0).setMaxResults(30).getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return transformList;
	}

	@Deprecated
	private List<TransformCategoryEntity> getAllTransformCategories()
	{
		List<TransformCategoryEntity> categoriesList = new ArrayList<TransformCategoryEntity>();
		LOG.debug("getting TNotification list");
		try{
			categoriesList = entityManager.createQuery("SELECT p FROM TransformCategoryEntity p order by p.createdOn", TransformCategoryEntity.class).getResultList();
		}catch(RuntimeException ex){
			LOG.error("get failed", ex);
		}
		return categoriesList;
	}

	public List<String> getDistinctTransformCategories()
	{
		List<String> categoriesList = new ArrayList<String>();
		LOG.debug("getting TNotification list");
		try{
			categoriesList = entityManager.createQuery("SELECT distinct p.categoryName FROM TransformCategoryEntity p order by p.createdOn").getResultList();
		}catch(Exception ex){
			LOG.error("get failed", ex);
		}
		return categoriesList;
	}

	public void createTransformCategory(TransformCategoryEntity categoryEntity)throws RuntimeException {
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

	public List<TransformSourceEntity> getAllTransformSources()
	{
		List<TransformSourceEntity> categoriesList = new ArrayList<TransformSourceEntity>();
		LOG.debug("getting TNotification list");
		try{
			categoriesList = entityManager.createQuery("SELECT p FROM TransformSourceEntity p order by p.createdOn", TransformSourceEntity.class).getResultList();
		}catch(RuntimeException ex){
			LOG.error("get failed", ex);
		}
		return categoriesList;
	}

	public TransformSourceEntity getTransformSourceById(Integer id)
	{
		TransformSourceEntity categoriesList = null;
		LOG.debug("getting TNotification list");
		try{
			categoriesList = entityManager.createQuery("SELECT p FROM TransformSourceEntity p where p.transformId=:id", TransformSourceEntity.class)
					.setParameter("id", id).getSingleResult();
		}catch(Exception ex){
			LOG.error("get failed", ex);
		}
		return categoriesList;
	}

	public void createTransformSource(TransformSourceEntity sourceEntity)throws RuntimeException {
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
			List<TransformCategoryEntity> categoriesList = entityManager.createQuery("SELECT p FROM TransformCategoryEntity p where p.categoryName=:categoryName", TransformCategoryEntity.class)
					.setParameter("categoryName", categoryName).getResultList();
			if(!Util.isEmpty(categoriesList))
				return categoriesList.get(0).getCategoryId();

		}catch(RuntimeException ex){
			LOG.error("get failed", ex);
		}
		return id;
	}

}