package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.NewsEntity;
import com.medrep.app.entity.NewsSourceEntity;
import com.medrep.app.entity.TransformCategoryEntity;

/**
 * DOA object for domain model class News.
 * @see com.medrep.app.dao.NewsEntity
 * @author Hibernate Tools
 */
@Repository
public class NewsDAO  extends MedRepDAO<NewsEntity>{

	private static final Log log = LogFactory.getLog(NewsDAO.class);

	public List<NewsEntity> getNewsList(){
		List<NewsEntity> newsList =new ArrayList<NewsEntity>();
		log.info("getting TNotification list");
		try
		{
			newsList = entityManager.createQuery("SELECT p FROM NewsEntity p order by p.createdOn desc", NewsEntity.class).getResultList();

		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return newsList;
	}

	public List<NewsEntity> getNewsList(Date timestamp){
		List<NewsEntity> newsList =new ArrayList<NewsEntity>();
		log.info("getting TNotification list");
		try
		{
			newsList = entityManager.createQuery("SELECT p FROM NewsEntity p  where p.createdOn<:timestamp order by p.createdOn desc", NewsEntity.class)
					.setParameter("timestamp", timestamp).getResultList();

		}catch(RuntimeException ex)
		{
			log.error("get failed", ex);
		}
		return newsList;
	}

	public List<NewsSourceEntity> getAllNewsSources()
	{
		List<NewsSourceEntity> sourceList = new ArrayList<NewsSourceEntity>();
		log.debug("getting TNotification list");
		try{
			sourceList = entityManager.createQuery("SELECT p FROM NewsSourceEntity p where p.rss IS FALSE  order by p.createdOn desc", NewsSourceEntity.class).getResultList();
		}catch(RuntimeException ex){
			log.error("get failed", ex);
		}
		return sourceList;
	}

	public Integer deleteAllNews()
	{
		int result = 0;
		log.debug("getting TNotification list");
		try{
			entityManager.createNativeQuery("DELETE FROM T_NEWS  where T_NEWS.CREATED_BY is null and T_NEWS.ID not in(select distinct T_POST_TOPIC.TRANSFORM_POST_ID from T_POST_TOPIC where T_POST_TOPIC.SOURCE=5)").executeUpdate();
			result = 1;
		}catch(RuntimeException ex){
			result = 0;
			log.error("get failed", ex);
		}
		return result;
	}


	public void createNewsSource(NewsSourceEntity sourceEntity)throws RuntimeException {
		log.info("Persisting Entity Instance");
		try
		{
			sourceEntity.setCreatedOn(Calendar.getInstance().getTime());
			entityManager.persist(sourceEntity);
			log.info("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public NewsEntity findBytitle(String title) {
		NewsEntity newsEntity=null;
		try
		{
			newsEntity = entityManager.createQuery("SELECT p FROM NewsEntity p where p.title=:title", NewsEntity.class).getSingleResult();
		} catch (Exception re) {
			re.printStackTrace();
		}
		return newsEntity;
	}

	public NewsSourceEntity getNewsSourceById(Integer sourceId) {
		NewsSourceEntity source = null;

		try{
			source = entityManager.createQuery("SELECT p FROM NewsSourceEntity p where p.sourceId=:sourceId", NewsSourceEntity.class)
					.setParameter("sourceId", sourceId).getSingleResult();
		}catch(Exception ex){
			log.error("Unable to fetch newsSource with id "+sourceId, ex);
		}
		return source;
	}



}