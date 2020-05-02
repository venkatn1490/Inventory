package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.PatientEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.entity.UserSecurityEntity;
import com.medrep.app.model.Location;
import com.medrep.app.model.User;

/**
 * Home object for domain model class TUser.
 * @see com.medrep.app.dao.UserEntity
 * @author Hibernate Tools
 */
@Repository
public class UserDAO extends MedRepDAO<UserEntity>{

	private static final Log log = LogFactory.getLog(UserDAO.class);

	public UserEntity findByEmailId(String emailId) {
		log.info("Getting UserEntity Instance with emailId: " + emailId);
		UserEntity userEntity =  new UserEntity();
		try
		{
			userEntity = entityManager.createQuery("select s from UserEntity s Where s.security.loginId = :emailId",UserEntity.class).setParameter("emailId", emailId).getSingleResult();
			log.info("get successfull");
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntity;
	}
	

	
	
	public UserEntity findByMobileNo(String mobileNo) {
		UserEntity userEntity =  new UserEntity();
		try
		{
			userEntity = entityManager.createQuery("select s from UserEntity s Where s.mobileNo = :mobileNo",UserEntity.class).setParameter("mobileNo", mobileNo).getSingleResult();
			log.info("get successfull");
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntity;
	}

	public UserEntity findBySecurityId(Integer id) {
		UserEntity userEntity =  new UserEntity();
		log.info("Security Id:"+id);
		try
		{
			userEntity = entityManager.createQuery("select s from UserEntity s Where s.security.userSecId = :userSecId",UserEntity.class).setParameter("userSecId", id).getSingleResult();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntity;
	}

	public List<UserEntity> findByStatus(String status) {
		log.info("Getting UserEntity based on status : " +status);
		List<UserEntity> userEntities =  new ArrayList<UserEntity>();
		try
		{
			userEntities = entityManager.createQuery("select s from UserEntity s Where s.status = :status",UserEntity.class).setParameter("status", status).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntities;
	}
	public void updateRegistrationToken(String regToken, String username) {
		try {
			entityManager.createNativeQuery("UPDATE T_USER SET REG_TOKEN = '"+regToken+ "' WHERE EMAIL_ID ='" + username+"'" ).executeUpdate();
			log.info("get successful");

		} catch (RuntimeException re)
		{
			log.error("get failed", re);
		}
	}

	public UserEntity findByUserId(Integer id) {
		UserEntity userEntity =  new UserEntity();
		log.info("Security Id:"+id);
		try
		{
			userEntity = entityManager.createQuery("select s from UserEntity s Where s.userId = :userId",UserEntity.class).setParameter("userId", id).getSingleResult();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntity;
	}


	@SuppressWarnings("unchecked")
	public List<UserEntity> getUserDetails(String userName) {
		List<UserEntity> userEntity =  new ArrayList<UserEntity>();
		log.info("user name="+userName);
		try
		{
			userEntity =  entityManager.createQuery("select s from UserEntity s Where s.emailId = :emailId",UserEntity.class).setParameter("emailId", userName).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userEntity;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getLocationDetails(Integer userId) {
		List<Object[]> locationEntity =  new ArrayList<Object[]>();
		log.info("user name="+userId);
		try
		{
			locationEntity =  entityManager.createNativeQuery("SELECT LOCATION_ID, ADDRESS1,ADDRESS2, CITY, STATE, COUNTRY, ZIPCODE, TYPE, USER_ID FROM T_LOCATION WHERE  USER_ID=:userId").setParameter("userId", userId).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return locationEntity;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getUserId(String loginId) {
		List<Object[]> userId =  new ArrayList<Object[]>();
		log.info("user name="+loginId);
		try
		{
			userId =  entityManager.createNativeQuery("SELECT usr.USER_ID, usr.SECURITY_ID, usr.ROLE_ID, usr.FIRST_NAME, usr.MIDDLE_NAME, usr.LAST_NAME, usr.ALIAS, usr.TITLE, usr.PHONE_NO, usr.MOBILE_NO, usr.EMAIL_ID, usr.ALTERNATE_EMAIL_ID, usr.DP_ID, usr.STATUS, usr.REG_TOKEN FROM T_USER usr,T_USER_SECURITY sec WHERE sec.LOGIN_ID=:loginId and sec.USER_SEC_ID=usr.SECURITY_ID").setParameter("loginId", loginId).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userId;
	}

	@SuppressWarnings("unchecked")
	public List<DoctorEntity> getAllMyContacts(Integer id) {
		List<DoctorEntity> conDetails =  new ArrayList<DoctorEntity>();
		List<LocationEntity> locDetails= new ArrayList<LocationEntity>();
		Location location=new Location();
		log.info("Security Id:"+id);
		try
		{
			conDetails =  entityManager.createQuery("select s from DoctorEntity s Where s.user.userId = :userId",DoctorEntity.class).setParameter("userId", id).getResultList();
			/*select  doctor.doctor_id, user.user_id, therapeutic.therapeutic_name,location.city,dp.data
			from UserEntity user,DoctorEntity doctor,LocationEntity location,T_Display_picture dp,m_therapeutic_area therapeutic
			where user.user_id=doctor.user_id
			and location.user_id=doctor.user_id
			and dp.dp_id=user.dp_id
			and therapeutic.therapeutic_id=doctor.therapeutic_id
			and user.user_id=2
			and doctor.doctor_id=1
			and location.city='dd';*/
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return conDetails;
	}

	@SuppressWarnings("unchecked")
	public List<DoctorEntity> getMyContactList(Integer id) {
		List<DoctorEntity> conDetails =  new ArrayList<DoctorEntity>();
		log.info("Security Id:"+id);
		try
		{
			conDetails =  entityManager.createQuery("select s from DoctorEntity s Where s.user.userId = :userId",DoctorEntity.class).setParameter("userId", id).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return conDetails;
	}

	@SuppressWarnings("unchecked")
	public List<DoctorEntity> getDoctorId(Integer id) {
		List<DoctorEntity> conDetails =  new ArrayList<DoctorEntity>();
		log.info("Security Id:"+id);
		try
		{
			conDetails =  entityManager.createQuery("select s from DoctorEntity s Where s.user.userId = :userId",DoctorEntity.class).setParameter("userId", id).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return conDetails;
	}
	
	@SuppressWarnings("unchecked")
	public List<PatientEntity> getPatientId(Integer id) {
		List<PatientEntity> conDetails =  new ArrayList<PatientEntity>();
		log.info("Security Id:"+id);
		try
		{
			conDetails =  entityManager.createQuery("select s from PatientEntity s Where s.user.userId = :userId",PatientEntity.class).setParameter("userId", id).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return conDetails;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> therapeuticDetails(Integer therapeuticId) {
		List<Object[]> theraDetails =  new ArrayList<Object[]>();
		log.info("user name="+therapeuticId);
		try
		{
			theraDetails =  entityManager.createNativeQuery("SELECT THERAPEUTIC_ID, THERAPEUTIC_NAME, THERAPEUTIC_DESC FROM M_THERAPEUTIC_AREA WHERE THERAPEUTIC_ID=:therapeuticId").setParameter("therapeuticId", therapeuticId).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return theraDetails;
	}

	@SuppressWarnings("unchecked")
	public List<DoctorEntity> suggestedContacts(Integer id) {
		List<DoctorEntity> conDetails =  new ArrayList<DoctorEntity>();
		log.info("Security Id:"+id);
		try
		{
			conDetails =  entityManager.createQuery("select s from DoctorEntity s Where s.user.userId = :userId",DoctorEntity.class).setParameter("userId", id).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return conDetails;
	}

	@SuppressWarnings("unchecked")
	public List<DoctorEntity> pendinContacts(Integer id) {
		List<DoctorEntity> conDetails =  new ArrayList<DoctorEntity>();
		log.info("Security Id:"+id);
		try
		{
			conDetails =  entityManager.createQuery("select s from DoctorEntity s Where s.user.userId = :userId",DoctorEntity.class).setParameter("userId", id).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return conDetails;
	}



	@SuppressWarnings("unchecked")
	public List<Object[]> getConnectionDetails() {
		List<Object[]> conDetails =  new ArrayList<Object[]>();
		String status="Pending";
		try
		{
			conDetails =  entityManager.createNativeQuery("SELECT CONNECTION_ID, DOCTOR_ID, STATUS FROM T_CONNECTIONS WHERE STATUS= :status").setParameter("status", status).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return conDetails;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> docInfoWithTherapeuticArea(Integer theId) {
		List<Object[]> docDetails =  new ArrayList<Object[]>();
		try
		{
			docDetails =  entityManager.createNativeQuery("SELECT DOCTOR_ID, USER_ID, REGISTRATION_YEAR, REGISTRATION_NUMBER, STATE_MED_COUNCIL, THERAPEUTIC_ID, QUALIFICATION, STATUS FROM T_DOCTOR WHERE THERAPEUTIC_ID= :theId").setParameter("theId", theId).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return docDetails;
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> getDocDetailsByCity(String city) {
		List<Object[]> docDetailsByCIty =  new ArrayList<Object[]>();
		try
		{
			docDetailsByCIty =  entityManager.createNativeQuery("SELECT USER_ID, CITY from T_LOCATION WHERE CITY= :city").setParameter("city", city).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return docDetailsByCIty;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getActiveConnectionDetails() {
		List<Object[]> conDetails =  new ArrayList<Object[]>();
		String status="Active";
		try
		{
			conDetails =  entityManager.createNativeQuery("SELECT CONNECTION_ID, DOCTOR_ID, STATUS FROM T_CONNECTIONS WHERE STATUS= :status").setParameter("status", status).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return conDetails;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getConIdByDocId(Integer doctorId) {
		List<Object[]> conDetails =  new ArrayList<Object[]>();
		try
		{
			conDetails =  entityManager.createNativeQuery("SELECT CONNECTION_ID, DOCTOR_ID, STATUS FROM T_CONNECTIONS WHERE DOCTOR_ID= :doctorId").setParameter("doctorId", doctorId).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return conDetails;
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> getNames(Integer uid){
		List<Object[]> names =  new ArrayList<Object[]>();
		try
		{
			names =  entityManager.createNativeQuery("SELECT FIRST_NAME, LAST_NAME FROM T_USER WHERE USER_ID= :uid").setParameter("uid", uid).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return names;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getDocID(int connectionId) {
		List<Object[]> contactDetails =  new ArrayList<Object[]>();
		try
		{
			contactDetails =  entityManager.createNativeQuery("SELECT DOCTOR_ID, USER_ID, STATUS FROM T_DOCTOR WHERE DOCTOR_ID= :connectionId").setParameter("connectionId", connectionId).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return contactDetails;
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> getUserValues(int doctId) {
		List<Object[]> contactDetails =  new ArrayList<Object[]>();
		try
		{
			contactDetails =  entityManager.createNativeQuery("SELECT USER_ID FROM T_USER WHERE USER_ID= :doctId").setParameter("doctId", doctId).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return contactDetails;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getUserNames(Integer userId1) {
		List<Object[]> userId =  new ArrayList<Object[]>();
		log.info("user name="+userId1);
		try
		{
			userId =  entityManager.createNativeQuery("SELECT USER_ID, SECURITY_ID, ROLE_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, ALIAS, TITLE, PHONE_NO, MOBILE_NO, EMAIL_ID, ALTERNATE_EMAIL_ID, DP_ID, STATUS, REG_TOKEN FROM T_USER WHERE USER_ID=:userId1").setParameter("userId1", userId1).getResultList();
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return userId;
	}

}
