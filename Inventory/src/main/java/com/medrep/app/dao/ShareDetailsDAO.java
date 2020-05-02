package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.ShareDetailsEntity;
import com.medrep.app.util.Util;

@Repository
public class ShareDetailsDAO extends MedRepDAO<ShareDetailsEntity> {
	private static final Log log = LogFactory.getLog(ShareDetailsDAO.class);

	public List<ShareDetailsEntity> fetchAllShareTopicData() {
		List<ShareDetailsEntity> instance = null;
		try {
			instance = entityManager.createQuery("select d from ShareDetailsEntity d where d.source.postType!='Message' order by d.posted_on DESC", ShareDetailsEntity.class)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
		}
		return instance;
	}

	public ShareDetailsEntity fetchShareByTopic(Integer id) {
		ShareDetailsEntity instance = null;
		try {
			instance = entityManager.createQuery("select d from ShareDetailsEntity d where d.id = :id order by d.posted_on DESC", ShareDetailsEntity.class)
					.setParameter("id", id)
					.getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
		}
		return instance;
	}

	public ShareDetailsEntity fetchShare(Integer doctorId, Integer topicId) {
		ShareDetailsEntity instance = null;
		try {
			instance = entityManager.createQuery("select d from ShareDetailsEntity d where d.doctor_id = :doctorId and d.transform_post_id=:topicId order by d.posted_on DESC", ShareDetailsEntity.class)
					.setParameter("doctorId", doctorId)
					.setParameter("topicId", topicId)
					.getSingleResult();
		} catch (RuntimeException re) {
			log.warn("Topic("+topicId+") not found for Doctor("+doctorId+")");
		}
		return instance;
	}

	public List<ShareDetailsEntity> findByDoctorId(Integer doctorId) {
		List<ShareDetailsEntity> entities= null;
		try {
			entities = entityManager.createQuery("select d from ShareDetailsEntity d where d.doctor_id = :doctorId order by d.posted_on DESC", ShareDetailsEntity.class)
					.setParameter("doctorId", doctorId).getResultList();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
		}
		return entities;
	}

	public List<ShareDetailsEntity> findByGroupId(Integer groupId) {
		List<ShareDetailsEntity> entities= null;
		try {
			entities = entityManager.createQuery("select d from ShareDetailsEntity d where d.groupId = :groupId or d.id in (select me.topic_id from MessageEntity me where me.group_id=:groupId) order by d.posted_on DESC", ShareDetailsEntity.class)
					.setParameter("groupId", groupId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
		}
		return entities;
	}

	public List<ShareDetailsEntity> showPostsByDoctorToReceiver(Integer doctorId, Integer receiverId) {
		List<ShareDetailsEntity> entities= null;
		try {
			entities = entityManager.createQuery("select d from ShareDetailsEntity d where (d.doctor_id = :doctorId and d.receiverId =:receiverId) or (d.doctor_id = :receiverId and d.receiverId =:doctorId) or d.id in(select me.topic_id from MessageEntity me where (me.receiver_id=:doctorId and me.member_id=:receiverId) or (me.receiver_id=:receiverId and me.member_id=:doctorId)) order by d.posted_on DESC", ShareDetailsEntity.class)
					.setParameter("doctorId", doctorId).setParameter("receiverId", receiverId).getResultList();



			log.info("get successful");

		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
		}
		return entities;
	}

	public List<ShareDetailsEntity> showPostsToDoctor(Integer doctorId) {
		List<ShareDetailsEntity> entities= null;
		try {
			entities = entityManager.createQuery("select d from ShareDetailsEntity d where  d.receiverId =:doctorId order by d.posted_on DESC", ShareDetailsEntity.class)
					.setParameter("doctorId", doctorId).getResultList();

		} catch (Exception re) {
			re.printStackTrace();
		}
		return entities;
	}


	public List<ShareDetailsEntity> showSharedPostsToDoctor(Integer doctorId,Date timeStamp) {
		List<ShareDetailsEntity> entities= null;
		try {
			if(Util.isEmpty(timeStamp)){
				entities = entityManager.createQuery("select d from ShareDetailsEntity d where d.receiverId =:doctorId or d.id in(select me.topic_id from MessageEntity me where me.receiver_id=:doctorId) order by d.posted_on DESC", ShareDetailsEntity.class)
						.setParameter("doctorId", doctorId).getResultList();
			}else{
				entities = entityManager.createQuery("select d from ShareDetailsEntity d where d.posted_on>=:timeStamp and (d.receiverId =:doctorId or d.id in(select me.topic_id from MessageEntity me where me.receiver_id=:doctorId)) order by d.posted_on DESC", ShareDetailsEntity.class)
						.setParameter("doctorId", doctorId)
						.setParameter("timeStamp", timeStamp).getResultList();
			}


		} catch (Exception re) {
			re.printStackTrace();
		}
		return entities;
	}


	public List<ShareDetailsEntity> showSharedPostsToDoctorInGroups(Integer doctorId,Date timeStamp) {
		List<ShareDetailsEntity> entities= null;
		try {
			entities = entityManager.createQuery("select d from ShareDetailsEntity d where   d.posted_on>=:timeStamp and (d.groupId in (select me.group_id from MemberEntity me  where me.member_id=:doctorId and me.status='ACTIVE')) order by d.posted_on DESC", ShareDetailsEntity.class)
					.setParameter("doctorId", doctorId)
					.setParameter("timeStamp", timeStamp).getResultList();

		} catch (Exception re) {
			re.printStackTrace();
		}
		return entities;
	}
}
