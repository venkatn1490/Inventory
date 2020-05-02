package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.GroupEntity;
import com.medrep.app.entity.LocationTypeEntity;

/**
 * DOA object for domain model class TDoctor.
 * @see com.medrep.app.dao.DoctorEntity
 * @author Hibernate Tools
 */
@Repository("doctorDAO")
public class DoctorDAO  extends MedRepDAO<DoctorEntity>{

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(DoctorDAO.class);

	public DoctorEntity findByLoginId(String loginId)
	{
		log.info("getting TDoctor instance with id: " + loginId);
		DoctorEntity instance = new DoctorEntity();
		try
		{
			instance = entityManager.createQuery("select d from DoctorEntity d where d.user.security.loginId = :loginId", DoctorEntity.class).setParameter("loginId",loginId).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<DoctorEntity> findbyLocationAndThreId(String city, String Id) {
		log.info("Getting UserEntity Instance with emailId: " + city);
		List<DoctorEntity> l=null ;
		try
		{
			l = entityManager.createQuery("select DISTINCT p  from DoctorEntity  p  join p.user u join u.locations as t where t.city=:city and p.therapeuticId = :therapeuticId ", DoctorEntity.class).setParameter("therapeuticId", Id).setParameter("city", city).getResultList();

			//userEntity = entityManager.createQuery("select DISTINCT   p from UserEntity p join p.locations as t where t.city=:city ",UserEntity.class).setParameter("city", city).getResultList();
			log.info("get successfull");
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return l;
	}
	public DoctorEntity findByDoctorId(int docId)
	{
		log.info("getting TDoctor instance with id: " + docId);
		DoctorEntity instance = new DoctorEntity();
		try
		{
			instance = entityManager.createQuery("select d from DoctorEntity d where d.doctorId = :docId", DoctorEntity.class).setParameter("docId",docId).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}


	public DoctorEntity findByDoctorIdWithUserEntity(int docId)
	{
		log.info("getting TDoctor instance with id: " + docId);
		DoctorEntity instance = new DoctorEntity();
		try
		{
			instance = entityManager.createQuery("select d from DoctorEntity d join fetch d.user where d.doctorId = :docId", DoctorEntity.class).setParameter("docId",docId).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public DoctorEntity findBySecurityId(Integer userSecId) {
		log.info("getting TDoctor instance with id: " + userSecId);
		DoctorEntity instance = new DoctorEntity();
		try
		{

			instance = entityManager.createQuery("select d from DoctorEntity d where d.user.security.userSecId = :userSecId", DoctorEntity.class).setParameter("userSecId",userSecId).getSingleResult();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);

		}
		return instance;
	}


	public List<DoctorEntity> findByName(String name) {
		log.info("getting Doctors instance with name: " + name);
		List<DoctorEntity> doctorEntities = new ArrayList<DoctorEntity>();
		try
		{
			StringTokenizer tokenizer = new StringTokenizer(name, " ");
			String first = tokenizer.nextToken();
			String last = "";
			if(tokenizer.hasMoreTokens())
			{
				last = tokenizer.nextToken();
			}
			doctorEntities = entityManager.createQuery("select d from DoctorEntity d where SOUNDEX(d.user.firstName) like SOUNDEX(:first) or SOUNDEX(d.user.lastName) like SOUNDEX(:last) or SOUNDEX(d.user.firstName) like SOUNDEX(:last) or SOUNDEX(d.user.lastName) like SOUNDEX(:first)", DoctorEntity.class).setParameter("first",first).setParameter("last",last).getResultList();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return doctorEntities;
	}

	public DoctorEntity findByMobileNumber(String  mobileNo) {
		log.info("getting TDoctor instance with id: " + mobileNo);
		DoctorEntity instance = null;
		try
		{

			instance = entityManager.createQuery("select d from DoctorEntity d where d.user.mobileNo = :mobileNo", DoctorEntity.class).setParameter("mobileNo",mobileNo).getSingleResult();
			log.info("get successful");
		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	public List<DoctorEntity> findByStatus(String status) {
		log.info("getting Doctors instance with status: " + status);
		List<DoctorEntity> doctorEntities = new ArrayList<DoctorEntity>();
		try
		{
			log.info("Status := \'"+status+"\'");
			doctorEntities = entityManager.createQuery("select d from DoctorEntity d where d.status = :status", DoctorEntity.class).setParameter("status",status).getResultList();
			log.info("get successful");

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return doctorEntities;
	}

	public List<DoctorEntity> findAllExceptDeleted(){
		log.info("getting Doctors instance with no deleted status");
		List<DoctorEntity> doctorEntities = new ArrayList<DoctorEntity>();
		try
		{
			String status="Deleted";
			doctorEntities = entityManager.createQuery("select d from DoctorEntity d where d.status != :status", DoctorEntity.class).setParameter("status",status).getResultList();
			log.info("get successful");
		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return doctorEntities;
	}

	public List<DoctorEntity> findByTAreaId(String tareaId) {
		List<DoctorEntity> doctorEntities = new ArrayList<DoctorEntity>();
		try
		{
			doctorEntities = entityManager.createQuery("select d from DoctorEntity d where d.therapeuticId = :tid", DoctorEntity.class).setParameter("tid",tareaId).getResultList();
			log.info("get successful");
		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return doctorEntities;

	}

	@SuppressWarnings("unchecked")
	public List<DoctorEntity> findAllActiveDoctorsExceptHimself(int doctorId){
		log.info("getting Doctors instance ");
		List<DoctorEntity> doctorEntities = new ArrayList<DoctorEntity>();
		try
		{
			//Active
			/*String status="New"; //select d from DoctorEntity d where d.status = :status and doctorId!=:doctorId
			doctorEntities = entityManager.createNativeQuery("", DoctorEntity.class).setParameter("status",status).setParameter("DOCTOR_ID",doctorId).getResultList();
			log.info("get successful");*/

			String status="New";
			String sql="select STATUS, DOCTOR_ID from T_DOCTOR where STATUS =':status' and DOCTOR_ID!=:doctorId;";
			System.out.println(sql);
			Query q = entityManager
					.createNativeQuery(sql, DoctorEntity.class);
			System.out.println("Query.."+q.toString());
			q.setParameter("status", status);
			q.setParameter("doctorId", doctorId);
			return (List<DoctorEntity>)q.getResultList();

		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return doctorEntities;
	}

	public List<DoctorEntity> findAllDoctorsExceptHimself(int doctorId){
		log.info("getting Doctors instance ");
		List<DoctorEntity> doctorEntities = new ArrayList<DoctorEntity>();
		try
		{
			doctorEntities = entityManager.createQuery("select d from DoctorEntity d where d.status = 'New' and d.doctorId!=:doctorId", DoctorEntity.class)
					.setParameter("doctorId",doctorId).getResultList();
		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return doctorEntities;
	}

	public List<DoctorEntity> findDoctorsbySearch(int doctorId, String name){
		log.info("getting Doctors instance ");
		List<DoctorEntity> doctorEntities = new ArrayList<DoctorEntity>();
		try
		{
			doctorEntities = entityManager.createQuery("select d from DoctorEntity d where d.doctorId!=:doctorId and d.status='Active' and (UPPER(d.user.firstName) like :name or UPPER(d.user.middleName) like :name or UPPER(d.user.lastName) like :name)", DoctorEntity.class)
					.setParameter("doctorId",doctorId)
					.setParameter("name", "%"+name.toUpperCase().replaceAll("\\s+", "%").trim()+"%").getResultList();
		}
		catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return doctorEntities;
	}


	public DoctorEntity findByUserId(int userId)
	{
		log.info("getting TDoctor instance with id: " + userId);
		DoctorEntity instance = new DoctorEntity();
		try
		{
			instance = entityManager.createQuery("select d from DoctorEntity d where d.user.userId = :userId", DoctorEntity.class).setParameter("userId",userId).getSingleResult();
			log.info("get successful");

		} catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}
	
	
	
	public List<LocationTypeEntity>  findlocationType()
	{
		
		List<LocationTypeEntity> instance = new ArrayList<LocationTypeEntity>();
		try
		{
			instance =  entityManager.createQuery("select d from LocationTypeEntity d order by d.locationType", LocationTypeEntity.class).getResultList();
			log.info("get successful");

		} catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
		return instance;
	}

	
	/*@SuppressWarnings("unchecked")
	public List<DoctorEntityNew> getDoctorInfo(Long doctorId) {
		Query q = entityManager
				.createNativeQuery(
						"SELECT DOCTOR_ID,"
								+ "       USER_ID,\n"
								+ "       REGISTRATION_YEAR,\n"
								+ "       REGISTRATION_NUMBER,\n"
								+ "       STATE_MED_COUNCIL,\n"
								+ "       THERAPEUTIC_ID,\n"
								+ "       QUALIFICATION,\n"
								+ "       STATUS\n"
								+ "  FROM T_DOCTOR where DOCTOR_ID= :doctorId",
								DoctorEntityNew.class);
		q.setParameter("doctorId", doctorId);
		return (List<DoctorEntityNew>) q.getResultList();
	}*/

}
