package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.GroupEntity;
import com.medrep.app.entity.LikeEntity;

@Repository
public class LikesDAO extends MedRepDAO<LikeEntity>{
	private static final Log log = LogFactory.getLog(LikesDAO.class);
	@SuppressWarnings("unchecked")
	public List<Object[]> getPostedLikes(String like_status) {
		List<Object[]> likeMessages =  new ArrayList<Object[]>();
		try
		{
			likeMessages =  entityManager.createNativeQuery("SELECT ID, USER_ID, LIKE_STATUS, MESSAGE_ID, LIKE_TIME FROM T_LIKES WHERE LIKE_STATUS = :like_status").setParameter("like_status", like_status).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return likeMessages;
	}
	public LikeEntity findByTopicIdAndDoctorId(Integer topicId, Integer doctorId) {
		LikeEntity likeEntity=null;
		try
		{
			likeEntity = entityManager.createQuery("select l from LikeEntity l where l.user_id = :docId and l.message_id=:topicId", LikeEntity.class)
					.setParameter("docId",doctorId)
					.setParameter("topicId", topicId).getSingleResult();

		} catch (Exception e)
		{
//			e.printStackTrace();
		}
		return likeEntity;
	}
}
