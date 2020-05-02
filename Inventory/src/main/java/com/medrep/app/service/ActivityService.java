package com.medrep.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.ActivityDAO;
import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DoctorAppointmentDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.NotificationDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.RepAppointmentDAO;
import com.medrep.app.dao.SurveyDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.entity.ActivityEntity;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorRepEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.SurveyEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.Activity;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorActivity;
import com.medrep.app.model.Location;
import com.medrep.app.model.NotificationActivity;
import com.medrep.app.model.User;
import com.medrep.app.util.ActivityUtil;
import com.medrep.app.util.Util;

@Service("activityService")
@Transactional
public class ActivityService {

	@Autowired
	DoctorAppointmentDAO doctorAppointmentDAO;

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	PharmaRepDAO pharmaRepDAO;

	@Autowired
	RepAppointmentDAO repAppointmentDAO;

	@Autowired
	ActivityDAO activityDAO;

	@Autowired
	NotificationDAO notificationDAO;

	@Autowired
	CompanyDAO companyDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;


	@Autowired
	SurveyDAO surveyDAO;

	private static final Log log = LogFactory.getLog(ActivityService.class);

	public void createOrUpdateActivity(Activity activity,String loginId) {

		ActivityEntity activityEntity = null;
		Integer userId = 0;

		if(activity.getUserType().equals("DOCTOR"))
		{

			System.out.println("Doctor Status Update");

			DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);

			if(doctorEntity!=null && doctorEntity.getDoctorId() != null)
			{

				userId = doctorEntity.getDoctorId();
			}
		}
		else if (activity.getUserType().equals("REP"))
		{

			System.out.println("REP Status Update");

			PharmaRepEntity repEntity = pharmaRepDAO.findByLoginId(loginId);

			if(repEntity!=null && repEntity.getRepId() != null)
			{

				userId = repEntity.getRepId();
			}

		}

		if (userId != null)
		{


				activityEntity = activityDAO.findByUserActivity(userId, activity.getActivityType(),activity.getActivityTypeId());

				if (activityEntity!=null && activityEntity.getActivityId() != null)
				{

					activityEntity.setActivityStatus(activity.getActivityStatus());
					activityEntity.setLastUpdateDateTime(activity.getLastUpdateDateTime());

				}
				else
				{

					activityEntity = new ActivityEntity();
					activityEntity.setActivityStatus(activity.getActivityStatus());
					activityEntity.setActivityType(activity.getActivityType());
					if("DS".equalsIgnoreCase(activityEntity.getActivityType()))
					{

						SurveyEntity surveyEntity =surveyDAO.findById(SurveyEntity.class, activity.getActivityTypeId());
						if(surveyEntity != null)
						{
							activityEntity.setCompanyId(surveyEntity.getCompanyId());
						}
					}
					else
					{
						NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, activity.getActivityTypeId());
						if(notificationEntity != null)
						{
							activityEntity.setCompanyId(notificationEntity.getCompanyId());
						}

					}

					activityEntity.setUserId(userId);
					activityEntity.setActivityTypeId(activity.getActivityTypeId());
					activityEntity.setUserType(activity.getUserType());
					activityEntity.setLastUpdateDateTime(activity.getLastUpdateDateTime());

				}

				activityDAO.merge(activityEntity);
			}

	}

	public void createOrUpdateActivity(Activity activity)
	{
		ActivityEntity activityEntity = null;
		Integer userId = activity.getUserId();

		if (userId != null)
		{

				activityEntity = activityDAO.findByUserActivity(userId, activity.getActivityType(),activity.getActivityTypeId());
				if (activityEntity!=null && activityEntity.getActivityId() != null)
				{

					activityEntity.setActivityStatus(activity.getActivityStatus());
					activityEntity.setLastUpdateDateTime(activity.getLastUpdateDateTime());

				}
				else
				{

					activityEntity = new ActivityEntity();
					activityEntity.setActivityStatus(activity.getActivityStatus());
					activityEntity.setActivityType(activity.getActivityType());
					activityEntity.setUserId(userId);
					activityEntity.setActivityTypeId(activity.getActivityTypeId());
					activityEntity.setUserType(activity.getUserType());
					activityEntity.setLastUpdateDateTime(activity.getLastUpdateDateTime());
					if("DS".equalsIgnoreCase(activityEntity.getActivityType()))
					{

						SurveyEntity surveyEntity =surveyDAO.findById(SurveyEntity.class, activity.getActivityTypeId());
						if(surveyEntity != null)
						{
							activityEntity.setCompanyId(surveyEntity.getCompanyId());
						}
					}
					else
					{
						NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, activityEntity.getActivityTypeId());
						if(notificationEntity != null)
						{
							activityEntity.setCompanyId(notificationEntity.getCompanyId());
						}

					}
				}

				activityDAO.merge(activityEntity);
			}

	}

	public DoctorActivity getDocotrActivityByCompany(Integer doctorId, String loginId)
	{
		DoctorActivity doctorActivity = new DoctorActivity();

		DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, doctorId);
		PharmaRepEntity repEntity = pharmaRepDAO.findByLoginId(loginId);
		if(repEntity != null  && repEntity.getRepId() != null)
		{

			Integer companyId = repEntity.getCompanyId();
			if(doctorEntity != null && doctorEntity.getDoctorId() != null)
			{
				doctorActivity.setDoctorId(doctorEntity.getDoctorId());
				UserEntity user = doctorEntity.getUser();
				if(user != null)
				{
					doctorActivity.setDoctorName(Util.getTitle(user.getTitle())+user.getFirstName() + " " + user.getLastName());

				}

				List<Object[]> activities = activityDAO.findActivityByUserIdCompanyId(doctorId, companyId);
				Map<String, Integer> doctorActivities = new HashMap<String, Integer>();
				doctorActivities.put("Notification", 0);
				doctorActivities.put("Feedback", 0);
				doctorActivities.put("Appointment", 0);
				doctorActivities.put("Survey", 0);
				doctorActivity.setActivities(doctorActivities);
				Integer totalScore = 0;
				for(Object[] data : activities)
				{

					String activityType = (String)data[1];
					Integer score = ActivityUtil.getScore(activityType);
					Integer value = ((BigInteger)data[0]).intValue();
					Integer tmpScore=value*score;

					if("DA".equalsIgnoreCase(activityType) || "DAC".equalsIgnoreCase(activityType))
					{
						Integer appointmentScore  = doctorActivities.get("Appointment");
						tmpScore+=appointmentScore;
					}

					while(tmpScore>100){
						tmpScore/=10;
					}
					totalScore = totalScore + value*score;
					if("DN".equalsIgnoreCase(activityType))
					{
						doctorActivities.put("Notification", tmpScore);

					}
					else if ("DNF".equalsIgnoreCase(activityType))
					{
						doctorActivities.put("Feedback", tmpScore);
					}
					else if("DA".equalsIgnoreCase(activityType) || "DAC".equalsIgnoreCase(activityType))
					{
						doctorActivities.put("Appointment", tmpScore);
					}
					else if("DS".equalsIgnoreCase(activityType))
					{
						doctorActivities.put("Survey",tmpScore);
					}
				}
				doctorActivity.setTotalScore(doctorActivities.get("Notification")+doctorActivities.get("Feedback")
				+doctorActivities.get("Appointment")+doctorActivities.get("Survey"));
				CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, companyId);
				if(companyEntity != null)
				{
					doctorActivity.setCompanyId(companyId);
					doctorActivity.setCompanyName(companyEntity.getCompanyName());
				}
			}
		}

		return doctorActivity;

	}

	public DoctorActivity getDocotrActivity(Integer doctorId)
	{
		DoctorActivity doctorActivity = new DoctorActivity();

		DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, doctorId);
		if(doctorEntity !=null && doctorEntity.getDoctorId() != null)
		{
			doctorActivity.setDoctorId(doctorEntity.getDoctorId());
			UserEntity user = doctorEntity.getUser();
			if(user != null)
			{
				doctorActivity.setDoctorName(Util.getTitle(user.getTitle())+user.getFirstName() + " " + user.getLastName());

			}

			List<Object[]> activities = activityDAO.findActivityByUserId(doctorId);
			Map<String, Integer> doctorActivities = new HashMap<String, Integer>();
			doctorActivities.put("Notification", 0);
			doctorActivities.put("Feedback", 0);
			doctorActivities.put("Appointment", 0);
			doctorActivities.put("Survey", 0);
			doctorActivity.setActivities(doctorActivities);
//			Integer totalScore = 0;
			for(Object[] data : activities)
			{

				String activityType = (String)data[1];
				Integer score = ActivityUtil.getScore(activityType);
				Integer value = ((BigInteger)data[0]).intValue();
				Integer tmpScore=value*score;

				if("DA".equalsIgnoreCase(activityType) || "DAC".equalsIgnoreCase(activityType))
				{
					Integer appointmentScore  = doctorActivities.get("Appointment");
					tmpScore+=appointmentScore;
				}
				while(tmpScore>100){
					tmpScore/=10;
				}
//				totalScore = totalScore + tmpScore;
				if("DN".equalsIgnoreCase(activityType))
				{
					doctorActivities.put("Notification", tmpScore);

				}
				else if ("DNF".equalsIgnoreCase(activityType))
				{
					doctorActivities.put("Feedback", tmpScore);
				}
				else if("DA".equalsIgnoreCase(activityType) || "DAC".equalsIgnoreCase(activityType))
				{
					doctorActivities.put("Appointment", tmpScore);
				}
				else if("DS".equalsIgnoreCase(activityType))
				{
					doctorActivities.put("Survey", tmpScore);
				}
			}
			doctorActivity.setTotalScore(doctorActivities.get("Notification")+doctorActivities.get("Feedback")+doctorActivities.get("Appointment")
					+doctorActivities.get("Survey"));
		}

		return doctorActivity;

	}

	public List<DoctorActivity> getDocotrActivity(String loginId)
	{

		List<DoctorActivity> activityList = new ArrayList<DoctorActivity>();
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		if(doctorEntity !=null && doctorEntity.getDoctorId() != null)
		{
			List<Object[]> activities = activityDAO.findActivityByDoctorId(doctorEntity.getDoctorId());
			Map<Integer,DoctorActivity> companyActivityMap=new HashMap<Integer, DoctorActivity>();

			for(Object[] data : activities)
			{
				Integer _companyId = (Integer)data[2];
				DoctorActivity dactivity = companyActivityMap.get(_companyId);
				if(Util.isEmpty(dactivity)){
					dactivity = new DoctorActivity();

					Map activityMap=new HashMap();
					activityMap.put("Notification", 0);
					activityMap.put("Feedback", 0);
					activityMap.put("Appointment", 0);
					activityMap.put("Survey", 0);
					dactivity.setActivities(activityMap);
					CompanyEntity company = companyDAO.findById(CompanyEntity.class, _companyId);
					if(company != null)
					{
						dactivity.setCompanyId(_companyId);
						dactivity.setCompanyName(company.getCompanyName());
					}

					UserEntity user = doctorEntity.getUser();
					dactivity.setDoctorId(doctorEntity.getDoctorId());
					if(user != null)
						dactivity.setDoctorName(Util.getTitle(user.getTitle())+user.getFirstName() + " " + user.getLastName());

					dactivity.setTotalScore(0);
					companyActivityMap.put(_companyId, dactivity);
					activityList.add(dactivity);
				}



				String activityType = (String)data[1];
				Integer score = ActivityUtil.getScore(activityType);
				Integer value = ((BigInteger)data[0]).intValue();
				Integer tmpScore=value*score;

				if("DA".equalsIgnoreCase(activityType) || "DAC".equalsIgnoreCase(activityType))
				{
					Integer appointmentScore  = (Integer) dactivity.getActivities().get("Appointment");
					tmpScore+=appointmentScore;
				}

				while(tmpScore>100){
					tmpScore/=10;
				}

				if("DN".equalsIgnoreCase(activityType))
				{
					dactivity.getActivities().put("Notification", tmpScore);

				}
				else if ("DNF".equalsIgnoreCase(activityType))
				{
					dactivity.getActivities().put("Feedback", tmpScore);
				}
				else if("DA".equalsIgnoreCase(activityType) || "DAC".equalsIgnoreCase(activityType))
				{
					dactivity.getActivities().put("Appointment", tmpScore);
				}
				else if("DS".equalsIgnoreCase(activityType))
				{
					dactivity.getActivities().put("Survey", tmpScore);
				}


				dactivity.setTotalScore(dactivity.getActivities().get("Notification")+dactivity.getActivities().get("Feedback")+dactivity.getActivities().get("Appointment")
						+dactivity.getActivities().get("Survey"));
			}
		}

		return activityList;

	}

	public NotificationActivity getNotificationActivity(Integer notificationId)
	{
		NotificationActivity notificationActivity = new NotificationActivity();

		NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, notificationId);
		if(notificationEntity !=null && notificationEntity.getNotificationId() != null)
		{
			notificationActivity.setNotificationId(notificationEntity.getNotificationId());
			notificationActivity.setNotificationName(notificationEntity.getNotificationName());
			if(notificationEntity.getCompanyId() != null)
			{
				CompanyEntity company = companyDAO.findById(CompanyEntity.class,notificationEntity.getCompanyId());
				if(company != null && company.getCompanyId() != null)
				{
					notificationActivity.setCompanyId(company.getCompanyId());
					notificationActivity.setCompanyName(company.getCompanyName());
				}

			}
			if(notificationEntity.getTherapeuticId() != null)
			{
				TherapeuticAreaEntity areaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, notificationEntity.getTherapeuticId());
				if(areaEntity != null)
				{
					notificationActivity.setTherapeuticId(areaEntity.getTherapeuticId());
					notificationActivity.setTherapeuticName(areaEntity.getTherapeuticName());
				}
			}

			List<Object[]> activities = activityDAO.findActivityByNotificationId(notificationId);
			Map<String, Integer> notificationActivities = new HashMap<String, Integer>();
			notificationActivities.put("Notification", 0);
			notificationActivities.put("Feedback", 0);
			notificationActivities.put("Appointment", 0);
			notificationActivity.setActivities(notificationActivities);
			Integer totalScore = 0;
			for(Object[] data : activities)
			{

				String activityType = (String)data[1];
				Integer score = ActivityUtil.getScore(activityType);
				Integer value = ((BigInteger)data[0]).intValue();
				if("DN".equalsIgnoreCase(activityType))
				{
					notificationActivities.put("Notification", value*score);
					totalScore = totalScore + value*score;

				}
				else if ("DNF".equalsIgnoreCase(activityType))
				{
					notificationActivities.put("Feedback", value*score);
					totalScore = totalScore + value*score;
				}
				else if("DA".equalsIgnoreCase(activityType) || "DAC".equalsIgnoreCase(activityType))
				{
					Integer appointmentScore  = notificationActivities.get("Appointment");
					notificationActivities.put("Appointment", appointmentScore + value*score);
					totalScore = totalScore + value*score;
				}
			}
			notificationActivity.setTotalScore(totalScore);
		}

		return notificationActivity;

	}


	public List<Doctor> getCompnayDoctors(String loginId)
	{
		List<Doctor> doctors = new ArrayList<Doctor>();
		PharmaRepEntity pharmaRep = pharmaRepDAO.findByLoginId(loginId);
		if(pharmaRep != null)
		{
			Integer companyId = pharmaRep.getCompanyId();
			List<Object[]> doctorIds= activityDAO.findUserByCompany(companyId);

			for(Object doctorId : doctorIds)
			{
				Integer id = (Integer)doctorId;
				DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, id);

				if(doctorEntity != null)
				{
					Doctor doctor = new Doctor();
					UserEntity user = doctorEntity.getUser();

					if (user != null)
					{
						doctor.setEmailId(user.getEmailId());
						doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//						doctor.setMiddleName(user.getMiddleName());
						doctor.setLastName(user.getLastName());
						doctor.setPhoneNo(user.getPhoneNo());
						doctor.setAlias(user.getAlias());
						doctor.setTitle(user.getTitle());
						doctor.setAlternateEmailId(user.getAlternateEmailId());
						doctor.setMobileNo(user.getMobileNo());

						if(user.getRole() != null)
						{
							doctor.setRoleId(user.getRole().getRoleId());
							doctor.setRoleName(user.getRole().getRoleName());
						}

						List<Location> locations = new ArrayList<Location>();
						if(user.getLocations()!=null)
							for(LocationEntity locationEntity : user.getLocations())
							{
								Location location = new Location();
								location.setAddress1(locationEntity.getAddress1());
								location.setAddress2(locationEntity.getAddress2());
								location.setCity(locationEntity.getCity());
								location.setCountry(locationEntity.getCountry());
								location.setState(locationEntity.getState());
								location.setType(Util.getLocationType(locationEntity.getType()));
								location.setZipcode(locationEntity.getZipcode());
								locations.add(location);

							}
						doctor.setLocations(locations);
					}
					if(doctorEntity.getTherapeuticId() != null)
					{
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(doctorEntity.getTherapeuticId()));
						if(therapeuticAreaEntity != null)
						{
							doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}

					}
					doctor.setQualification(doctorEntity.getQualification());
					doctor.setRegistrationNumber(doctorEntity.getRegistrationNumber());
					doctor.setRegistrationYear(doctorEntity.getRegistrationYear());
					doctor.setStateMedCouncil(doctorEntity.getStateMedCouncil());
					doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
					doctor.setDoctorId(doctorEntity.getDoctorId());
					doctors.add(doctor);
				}
			}
		}


		return doctors;

	}
}
