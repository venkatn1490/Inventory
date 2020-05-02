package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.GroupEntity;

@Repository
public class GroupDAO extends MedRepDAO<GroupEntity> {

	private static final Log log = LogFactory.getLog(GroupDAO.class);

	public List<GroupEntity> findAll() {
		log.info("getting GroupEntity instances according to Doctor ID's");

		List<GroupEntity> instance = new ArrayList<GroupEntity>();
		try {
			instance = entityManager.createQuery("select d from GroupEntity d", GroupEntity.class).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;

	}

	public List<GroupEntity> findSuggestedGroups(Integer docId) {
		log.info("getting GroupEntity instances according to Doctor ID's");
// select ge from GroupEntity ge  where ge.group_id  in (select me.group_id from MemberEntity  me where me.member_id in (select ce.connID from ConnectionEntity ce where ce.docID=:docId) and member_id!=:docId)
		List<GroupEntity> instance = new ArrayList<GroupEntity>();
		try {
			instance = entityManager.createQuery("select distinct  ge from GroupEntity ge  where ge.group_id  in (select  distinct me.group_id from MemberEntity  me where me.member_id in (select distinct ce.connID from ConnectionEntity ce where ce.docID=:docId) and me.group_id not in(select  distinct me2.group_id from MemberEntity me2 where me2.member_id=:docId and me2.status in ('ACTIVE','PENDING')))", GroupEntity.class)
					.setParameter("docId", docId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
		}
		return instance;

	}

	public List<GroupEntity> findMoreGroups(Integer docId) {
		log.info("getting GroupEntity instances according to Doctor ID's");

		List<GroupEntity> instance = new ArrayList<GroupEntity>();
		try {
			instance = entityManager.createQuery("select distinct g from GroupEntity g where g.group_id not in (select distinct m.group_id from MemberEntity m where m.member_id=:docId and m.status in ('ACTIVE','PENDING'))", GroupEntity.class)
					.setParameter("docId", docId).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
		}
		return instance;

	}
	public List<GroupEntity> findByDocID(Integer docID) {
		log.info("getting GroupEntity instances according to Doctor ID's");

		List<GroupEntity> instance = new ArrayList<GroupEntity>();
		try {
			instance = entityManager
					.createQuery("select d from GroupEntity d,MemberEntity m where m.member_id = :docID and m.group_id=d.group_id ", GroupEntity.class)
					.setParameter("docID", docID).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;

	}

	public List<GroupEntity> findByDocAndGroupID(Integer docID, Integer groupID) {
		log.info("getting GroupEntity instances according to Doctor ID's");

		List<GroupEntity> instance = new ArrayList<GroupEntity>();
		try {
			instance = entityManager
					.createQuery("select d from GroupEntity d where d.admin_id = :docID and d.group_id = :groupID",
							GroupEntity.class)
					.setParameter("docID", docID).setParameter("groupID", groupID).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;

	}

	public List<GroupEntity> findByGroupID(Integer groupID) {
		log.info("getting GroupEntity instances according to Group ID's");

		List<GroupEntity> instance = new ArrayList<GroupEntity>();
		try {
			instance = entityManager.createQuery("select d from GroupEntity d where d.group_id = :groupID", GroupEntity.class)
					.setParameter("groupID", groupID).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;

	}

	public int deleteGroupUsingGroupID(Integer grpID) {
		log.info("getting Group ID's");

		int instance = 0;
		try {
			instance = entityManager.createNativeQuery("DELETE FROM M_GROUPS WHERE GROUP_ID = " + grpID)
					.executeUpdate();
			log.info("get successful");

		} catch (RuntimeException re) {
			instance = 2;
			log.error("get failed", re);
		}
		return instance;

	}

}
