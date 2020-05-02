package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.MessageEntity;

@Repository
public class MessageDAO extends MedRepDAO<MessageEntity>{

	private static final Log log = LogFactory.getLog(MessageDAO.class);

	@SuppressWarnings("unchecked")
	public List<Object[]> fetchPostedMessage(Integer member_id) {
		List<Object[]> postMessages =  new ArrayList<Object[]>();
		try
		{
			postMessages =  entityManager.createNativeQuery("SELECT MESSAGE_ID, MEMBER_ID, GROUP_ID, MESSAGE, MESSAGE_TYPE,POST_DATE,RECEIVER_ID,FILE_URL FROM T_MESSAGE WHERE MEMBER_ID= :member_id").setParameter("member_id", member_id).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return postMessages;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> fetchShare() {
		List<Object[]> postMessages =  new ArrayList<Object[]>();
		try
		{
			postMessages =  entityManager.createNativeQuery("SELECT MESSAGE_ID, MEMBER_ID, GROUP_ID, MESSAGE, MESSAGE_TYPE,POST_DATE,RECEIVER_ID,TOPIC_ID FROM T_MESSAGE;").getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return postMessages;
	}

	public List<MessageEntity> fetchShareByTopic(Integer topicId) {
		log.info("getting All MessageEntity instances");
		List<MessageEntity> instances = new ArrayList<MessageEntity>();
		try {
			instances = entityManager.createQuery("select a from MessageEntity a where a.topic_id = :topicId order by a.post_date DESC",MessageEntity.class)
					.setParameter("topicId", topicId)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}
	public List<MessageEntity> fetchShareById(int Id) {
		log.info("getting All TherapeuticArea instances");
		List<MessageEntity> instance = null;
		try {
			instance = entityManager.createQuery("select a from MessageEntity a where a.topic_id=:id  ORDER BY a.post_date DESC LIMIT 1",MessageEntity.class)
					.setParameter("id", Id).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instance;
	}
	public List<MessageEntity> findByDate(Date post_date) {
		log.info("getting All MessageEntity instances");
		List<MessageEntity> instances = new ArrayList<MessageEntity>();
		try {
			instances = entityManager.createQuery("select a from MessageEntity a where a.post_date >= :post_date ",MessageEntity.class)
					.setParameter("post_date", post_date, TemporalType.DATE)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public List<MessageEntity> findByMember(Integer memberId) {
		log.info("getting All MessageEntity instances");
		List<MessageEntity> instances = new ArrayList<MessageEntity>();
		try {
			instances = entityManager.createQuery("select a from MessageEntity a where a.member_id = :memberId order by a.post_date DESC",MessageEntity.class)
					.setParameter("memberId", memberId)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public List<MessageEntity> findByGroup(Integer groupId) {
		log.info("getting All MessageEntity instances");
		List<MessageEntity> instances = new ArrayList<MessageEntity>();
		try {
			instances = entityManager.createQuery("select a from MessageEntity a where a.group_id = :groupId order by a.post_date DESC",MessageEntity.class)
					.setParameter("groupId", groupId)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}

		return instances;
	}

	public List<MessageEntity> fetchShareByTopicMembers(Integer groupId,Integer topicId) {
		List<MessageEntity> instances = new ArrayList<MessageEntity>();
		try {
			instances = entityManager.createQuery("select a from MessageEntity a where a.group_id = :groupId and a.topic_id=:topicId order by a.post_date DESC",MessageEntity.class)
					.setParameter("groupId", groupId)
					.setParameter("topicId", topicId)
					.getResultList();

		} catch (Exception  re) {
			re.printStackTrace();
		}

		return instances;
	}

	public List<MessageEntity> fetchShareByTopicToDoctor(Integer receiverId,Integer memberId,Integer topicId) {
		List<MessageEntity> instances = new ArrayList<MessageEntity>();
		try {
			instances = entityManager.createQuery("select a from MessageEntity a where ((a.receiver_id = :receiverId and a.member_id=:memberId) or (a.receiver_id = :memberId and a.member_id=:receiverId) ) and a.topic_id=:topicId order by a.post_date DESC",MessageEntity.class)
					.setParameter("receiverId", receiverId)
					.setParameter("memberId", memberId)
					.setParameter("topicId", topicId)
					.getResultList();

		} catch (Exception  re) {
			re.printStackTrace();
		}

		return instances;
	}

}
