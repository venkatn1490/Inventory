package com.medrep.app.dao;

import java.util.List;

import javax.xml.stream.events.Comment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.CommentEntity;
import com.medrep.app.entity.ShareDetailsEntity;

@Repository
public class CommentDAO extends MedRepDAO<CommentEntity>{
	private static final Log log = LogFactory.getLog(CommentDAO.class);

	public List<CommentEntity> findByMessageId(int messageId,int doctorId) {
		List<CommentEntity> instance = null;
		try {
			instance = entityManager.createQuery("select ce from CommentEntity ce where ce.message_id=:messageId and (ce.member_id in(select ce.connID from ConnectionEntity ce where ce.docID=:doctorId and ce.status='ACTIVE') or ce.group_id in (select me.group_id from MemberEntity me where me.member_id=:doctorId and me.status='ACTIVE'))) order by ce.posted_date", CommentEntity.class)
					.setParameter("messageId", messageId)
					.setParameter("doctorId", doctorId).getResultList();

		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return instance;
	}

}
