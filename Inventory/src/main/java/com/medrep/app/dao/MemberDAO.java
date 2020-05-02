package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.GroupEntity;
import com.medrep.app.entity.MemberEntity;

@Repository
public class MemberDAO extends MedRepDAO<MemberEntity> {

	private static final Log log = LogFactory.getLog(MemberDAO.class);

	public List<MemberEntity> findAll() {
		log.info("getting All GroupEntity instances");
		return null;
	}

	public List<MemberEntity> findByDocID(Integer docID) {
		log.info("getting GroupEntity instances according to Doctor ID's");

		List<MemberEntity> instance = new ArrayList<MemberEntity>();
		try {
			instance = entityManager
					.createQuery("select d from MemberEntity d where d.member_id = :docID and d.status = 'ACTIVE' ", MemberEntity.class)
					.setParameter("docID", docID).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;
	}

	public MemberEntity getID(Integer memberID, Integer groupID) {
		log.info("getting GroupEntity instances according to Group ID's");

		MemberEntity instance = new MemberEntity();
		try {
			instance = entityManager
					.createQuery("select d from MemberEntity d where d.group_id = :groupID and d.member_id = :memberID",
							MemberEntity.class)
					.setParameter("groupID", groupID).setParameter("memberID", memberID).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;
	}

	public List<MemberEntity> findByGroupID(Integer groupID) {
		log.info("getting GroupEntity instances according to Group ID's");

		List<MemberEntity> instance = new ArrayList<MemberEntity>();
		try {
			instance = entityManager
					.createQuery("select d from MemberEntity d where d.group_id = :groupID and d.status in('ACTIVE','PENDING')", MemberEntity.class)
					.setParameter("groupID", groupID).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;
	}

	public List<MemberEntity> findActiveGroupMembers(Integer groupID) {
		log.info("getting GroupEntity instances according to Group ID's");

		List<MemberEntity> instance = new ArrayList<MemberEntity>();
		try {
			instance = entityManager
					.createQuery("select d from MemberEntity d where d.group_id = :groupID and d.status = :status ", MemberEntity.class)
					.setParameter("groupID", groupID)
					.setParameter("status", "ACTIVE")
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;
	}

	public List<MemberEntity> findPendingGroupMembers(Integer docId) {
		List<MemberEntity> instance = new ArrayList<MemberEntity>();
		try {
			instance = entityManager
					.createQuery("select  distinct d from MemberEntity d where d.member_id = :docId and d.group_id in(select ge.group_id from GroupEntity ge) and d.status = :status ", MemberEntity.class)
					.setParameter("docId", docId)
					.setParameter("status", "PENDING")
					.getResultList();
		} catch (RuntimeException re) {
			log.error("Error in fetching pendingGroups for Doctor", re);
		}
		return instance;
	}

	public List<MemberEntity> findPendingMembersOfGroup(Integer groupId) {
		log.info("getting GroupEntity instances according to Group ID's");

		List<MemberEntity> instance = new ArrayList<MemberEntity>();
		try {
			instance = entityManager
					.createQuery("select d from MemberEntity d where d.status = :status and d.group_id = :groupId", MemberEntity.class)
					.setParameter("groupId", groupId)
					.setParameter("status", "PENDING")
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;
	}

	public void removeMember(int group_id, int i) {
		log.info("getting Group ID's");

		try {
			entityManager.createNativeQuery("DELETE FROM T_MEMBERS WHERE GROUP_ID = " + group_id + " and MEMBER_ID = " + i).executeUpdate();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
	}

	public void removeGroupAllMembers(int group_id) {
		log.info("getting Group ID's");

		try {
			entityManager.createNativeQuery("DELETE FROM T_MEMBERS WHERE GROUP_ID = " + group_id).executeUpdate();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
	}

	public void updateMemberStatus(MemberEntity member) {
		log.info("getting Group ID's");

		try {
			entityManager.createNativeQuery("UPDATE T_MEMBERS SET STATUS = '" + member.getStatus() +"' WHERE GROUP_ID= "+member.getGroup_id()+" AND MEMBER_ID = "+member.getMember_id()).executeUpdate();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
	}

	public MemberEntity findByDocAndConnId(int group_id, int member_id) {
		MemberEntity instance = new MemberEntity();
		try {
			instance = entityManager
					.createQuery("select d from MemberEntity d where d.member_id = :member_id and d.group_id = :group_id ", MemberEntity.class)
					.setParameter("member_id", member_id)
					.setParameter("group_id", group_id)
					.getSingleResult();

			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return instance;
	}

	public List<MemberEntity> getMembersForGroup(int group_id, String status) {
		List<MemberEntity> members=new ArrayList<MemberEntity>();
		try {
			members=entityManager.createQuery("select m from MemberEntity m where m.group_id=:groupId and m.status=:status")
			.setParameter("groupId", group_id).setParameter("status", status).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return members;
	}

}
