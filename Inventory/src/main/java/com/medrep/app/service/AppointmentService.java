package com.medrep.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DeviceTokenDAO;
import com.medrep.app.dao.DoctorAppointmentDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.NotificationDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.RepAppointmentDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.RepAppointmentEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.DoctorAppointment;
import com.medrep.app.model.SMS;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.Util;

@Service("appointmentService")
@Transactional
public class AppointmentService {

	@Autowired
	DoctorAppointmentDAO doctorAppointmentDAO;

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	PharmaRepDAO pharmaRepDAO;

	@Autowired
	RepAppointmentDAO repAppointmentDAO;

	@Autowired
	SMSService smsService;

	@Autowired
	NotificationDAO notificationDAO;

	@Autowired
	CompanyDAO companyDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	@Autowired
	UserDAO userDAO;
	@Autowired
	NotificationService notificationService;
	@Autowired
	DeviceTokenDAO deviceTokenDAO;

	private static final Log log = LogFactory.getLog(AppointmentService.class);

	public ArrayList<DeviceTokenEntity> createDoctorAppointment(DoctorAppointment doctorAppointment, String userType, String loginId) throws Exception {
		boolean newAppointment=false;
		ArrayList<DeviceTokenEntity> devicesList = new ArrayList<DeviceTokenEntity>();
		DoctorAppointmentEntity appointmentEntity = null;
		DoctorEntity doctorEntity = null;
		PharmaRepEntity pharmaRepEntity = null;
		log.info("doctor appointment creating..");
		if ("pharmarep".equals(userType))
		{
			pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);

			if (pharmaRepEntity != null && pharmaRepEntity.getRepId() != null)
			{
				Date startDate=DateConvertor.convertStringToDate(doctorAppointment.getStartDate(), DateConvertor.YYYYMMDDHHMISS);
				Date endDate=DateUtils.addMinutes(startDate,doctorAppointment.getDuration()==null?0:doctorAppointment.getDuration());

//				List<RepAppointmentEntity> appointments = repAppointmentDAO.findAppointmentForMedRep(doctorEntity.getDoctorId(),startDate,endDate);
//				if(appointments!=null && !appointments.isEmpty())
//					throw new Exception("Appointment Conflicted");

				appointmentEntity = doctorAppointmentDAO.findByRepNotificationId(pharmaRepEntity.getRepId(),doctorAppointment.getNotificationId());
			}
		}
		else if ("doctor".equals(userType))
		{

			doctorEntity = doctorDAO.findByLoginId(loginId);

			if(doctorEntity != null && doctorEntity.getDoctorId() != null &&doctorEntity.getDoctorId()!=0)
			{
				doctorAppointment.setDoctorId(doctorEntity.getDoctorId());

				while(doctorAppointment.getStartDate()!=null && doctorAppointment.getStartDate().length()!=14){
					doctorAppointment.setStartDate(doctorAppointment.getStartDate()+"0");
				}

				Date startDate=DateConvertor.convertStringToDate(doctorAppointment.getStartDate(), DateConvertor.YYYYMMDDHHMISS);

				log.info("startDate::"+doctorAppointment.getStartDate()+"::"+startDate);
				log.info("duration::"+doctorAppointment.getDuration());

				Date endDate=DateUtils.addMinutes(startDate,doctorAppointment.getDuration()==null?0:doctorAppointment.getDuration());
				log.info("APPOINTMENT::"+startDate+"::"+endDate);
				List<DoctorAppointmentEntity> appointments = doctorAppointmentDAO.findAppointmentForDoctor(doctorEntity.getDoctorId(),startDate,endDate);
//				if(appointments!=null && !appointments.isEmpty())
//					throw new Exception("Appointment Conflicted");
				appointmentEntity = doctorAppointmentDAO.findByDoctorNotificationId(doctorEntity.getDoctorId(), doctorAppointment.getNotificationId());
			}

		}

		if (Util.isEmpty(appointmentEntity) || Util.isZeroOrNull(appointmentEntity.getAppointmentId()))
		{
			log.info("NEW_APPOINTMENT");
			newAppointment=true;
			appointmentEntity = new DoctorAppointmentEntity();

				NotificationEntity n=notificationDAO.findById(NotificationEntity.class, doctorAppointment.getNotificationId());
				if(n!=null){
					if(n.getNotificationDesc()!=null&& !n.getNotificationDesc().isEmpty())
						appointmentEntity.setAppointmentDesc(n.getNotificationDesc());
					if(n.getNotificationName()!=null &&!n.getNotificationName().isEmpty())
						appointmentEntity.setTitle(n.getNotificationName());
				}

				log.info(">>companyId:"+doctorAppointment.getCompanyId());
				doctorAppointment.setCompanyId(n.getCompanyId());
				log.info(">>>companyId.."+doctorAppointment.getCompanyId());
				appointmentEntity.setStartDate(DateConvertor.convertStringToDate(doctorAppointment.getStartDate(), DateConvertor.YYYYMMDDHHMISS));

				log.info("APPOINTMENT..::"+appointmentEntity.getStartDate());
				doctorAppointmentDAO.persist(appointmentEntity);
		}else
			return rescheduleAppointment(doctorAppointment, userType,loginId);



		appointmentEntity.setCreatedOn(new Date());
		if(doctorEntity != null)
		{
			appointmentEntity.setDoctorId(doctorEntity.getDoctorId());
		}
		if(pharmaRepEntity != null)
		{
			appointmentEntity.setPharmaRepId(pharmaRepEntity.getRepId());
		}
		if(doctorAppointment.getDuration() != null)
		{
			appointmentEntity.setDuration(doctorAppointment.getDuration());
		}
		if(doctorAppointment.getFeedback() != null)
		{
			appointmentEntity.setFeedback(doctorAppointment.getFeedback());
		}
		if(doctorAppointment.getLocation() != null)
		{
			appointmentEntity.setLocation(doctorAppointment.getLocation());
		}
		if(doctorAppointment.getNotificationId() != null)
		{
			appointmentEntity.setNotificationId(doctorAppointment.getNotificationId());
		}
		log.info("XX-1");
		if(doctorAppointment.getStartDate() != null && doctorAppointment.getStartDate().length() == 14)
		{

//			Date oldDate=appointmentEntity.getStartDate();
			appointmentEntity.setStartDate(DateConvertor.convertStringToDate(doctorAppointment.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
			//if(newAppointment==false){
//				if(appointmentEntity.getStartDate().compareTo(oldDate)!=0){


//			if(newAppointment){
				List<String> cities=new ArrayList<String>();
				List<String> zipcodes=new ArrayList<String>();
				for( LocationEntity locationEntity:doctorEntity.getUser().getLocations()){
					if(locationEntity.getZipcode()!=null && !locationEntity.getZipcode().isEmpty())
						zipcodes.add(locationEntity.getZipcode());
					if(locationEntity.getCity()!=null && !locationEntity.getCity().isEmpty())
						cities.add(locationEntity.getCity());
				}
				List<PharmaRepEntity> pharmaRepEntities=pharmaRepDAO.findRepsByLocationInCompany(zipcodes,cities,doctorAppointment.getCompanyId());
				log.info("Reps found in location::"+(Util.isEmpty(pharmaRepEntities)?0:pharmaRepEntities.size()));
				if(Util.isEmpty(pharmaRepEntities))
					pharmaRepEntities=new ArrayList<PharmaRepEntity>();

				log.info("Location:appointmentId"+appointmentEntity.getLocation()+":"+doctorAppointment.getCompanyId());

				List<PharmaRepEntity> reps=pharmaRepDAO.findByCoveredAreaInCompany(appointmentEntity.getLocation(),doctorAppointment.getCompanyId());
				if(!Util.isEmpty(reps))
					pharmaRepEntities.addAll(reps);

				log.info("Reps Found in Covered area::"+pharmaRepEntities.size());

				for(PharmaRepEntity pRepEntity:pharmaRepEntities){

					RepAppointmentEntity repAppointmentEntity = repAppointmentDAO.findByRepAppointmentId(
							pRepEntity.getRepId(),appointmentEntity.getAppointmentId());
					if (repAppointmentEntity != null) {
						repAppointmentEntity.setAppointmentId(appointmentEntity.getAppointmentId());
						repAppointmentEntity.setCreatedOn(new Date());
						repAppointmentEntity.setDoctorId(doctorAppointment.getDoctorId());
						repAppointmentEntity.setRepId(pRepEntity.getRepId());
						repAppointmentEntity.setStatus("Pending");
						log.info("REPID::"+repAppointmentEntity.getRepId());
//						repAppointmentDAO.merge(repAppointmentEntity);
					} else {
						repAppointmentEntity = new RepAppointmentEntity();
						repAppointmentEntity.setAppointmentId(appointmentEntity.getAppointmentId());
						repAppointmentEntity.setCreatedOn(new Date());
						repAppointmentEntity.setDoctorId(doctorAppointment.getDoctorId());
						repAppointmentEntity.setRepId(pRepEntity.getRepId());
						repAppointmentEntity.setStatus("Pending");
						log.info("REPID_NEW::"+repAppointmentEntity.getRepId());
						repAppointmentDAO.persist(repAppointmentEntity);
						log.info("Persisted ID"+repAppointmentEntity.getRepAppointmentId());
					}



					List<DeviceTokenEntity> deviceTokenEntities=deviceTokenDAO.findByRepId(pRepEntity.getRepId());
					if(deviceTokenEntities!=null && !deviceTokenEntities.isEmpty()){
						devicesList.addAll(deviceTokenEntities);
					}

					PharmaRepEntity manager=null;
					if(!Util.isZeroOrNull(pRepEntity.getManagerId()))
							manager=pharmaRepDAO.findById(PharmaRepEntity.class, pRepEntity.getManagerId());

					if(Util.isEmpty(manager)|| Util.isZeroOrNull(manager.getRepId()))
						if(!Util.isEmpty(pRepEntity.getManagerEmail()))
						manager=pharmaRepDAO.findByLoginId(pRepEntity.getManagerEmail());

					if(!Util.isEmpty(manager) && !Util.isZeroOrNull(manager.getRepId())){

						 RepAppointmentEntity mgrAppointmentEntity = repAppointmentDAO.findByRepAppointmentId(manager.getRepId(),appointmentEntity.getAppointmentId());
						if (mgrAppointmentEntity != null) {
							mgrAppointmentEntity.setAppointmentId(appointmentEntity.getAppointmentId());
							mgrAppointmentEntity.setCreatedOn(new Date());
							mgrAppointmentEntity.setDoctorId(doctorAppointment.getDoctorId());
							mgrAppointmentEntity.setRepId(manager.getRepId());
							mgrAppointmentEntity.setStatus("Pending");
							log.info("REPID::"+repAppointmentEntity.getRepId());
//							repAppointmentDAO.merge(repAppointmentEntity);
						} else {
							mgrAppointmentEntity = new RepAppointmentEntity();
							mgrAppointmentEntity.setAppointmentId(appointmentEntity.getAppointmentId());
							mgrAppointmentEntity.setCreatedOn(new Date());
							mgrAppointmentEntity.setDoctorId(doctorAppointment.getDoctorId());
							mgrAppointmentEntity.setRepId(manager.getRepId());
							mgrAppointmentEntity.setStatus("Pending");
							repAppointmentDAO.persist(mgrAppointmentEntity);
						}



						List<DeviceTokenEntity> deviceTokenEntities1=deviceTokenDAO.findByManagerId(manager.getRepId());
						if(!Util.isEmpty(deviceTokenEntities1)){
							devicesList.addAll(deviceTokenEntities1);
						}

					}
				}

//			}
										/*
					PharmaRepEntity pharmaRepEntity1  = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
					if(pharmaRepEntity1!=null){

						if( pharmaRepEntity1.getManagerId()>0){
							UserEntity managerUserEntity  = userDAO.findById(UserEntity.class, pharmaRepEntity1.getManagerId());
							if(managerUserEntity==null || managerUserEntity.getRegToken()==null || "".equalsIgnoreCase(managerUserEntity.getRegToken()))
							{
							}else{
								devicesList.add(managerUserEntity.getRegToken());
							}
						}
						if(pharmaRepEntity1.getUser().getRegToken()==null || "".equalsIgnoreCase(pharmaRepEntity1.getUser().getRegToken()))
						{
						}else{
							devicesList.add(pharmaRepEntity1.getUser().getRegToken());
						}
					}
				*/
					//}
			//}
		}
		if(doctorAppointment.getStatus() != null)
		{
			appointmentEntity.setStatus(doctorAppointment.getStatus());
		}

		if(doctorAppointment.getAppointmentDesc() != null)
		{
			appointmentEntity.setAppointmentDesc(doctorAppointment.getAppointmentDesc());
		}
		if(doctorAppointment.getTitle() != null)
		{
			appointmentEntity.setTitle(doctorAppointment.getTitle());
		}

		if(doctorAppointment.getRemindMe()!= null)
		{
			appointmentEntity.setRemindMe(doctorAppointment.getRemindMe());
		}
//		if (appointmentEntity.getAppointmentId() == null)
//		{
//			doctorAppointmentDAO.persist(appointmentEntity);
//
//		} else
//		{
//			doctorAppointmentDAO.merge(appointmentEntity);
//		}
//		doctorAppointment.setAppointmentId(appointmentEntity.getAppointmentId());

		log.info("DevicesList is beeing returned.");
		return devicesList;

//		if(devicesList!=null && devicesList.size()>0){
//			notificationService.push("Dr."+doctorEntity.getUser().getFirstName() +doctorEntity.getUser().getLastName()+" has scheduled an appointment on"+appointmentEntity.getStartDate(), devicesList);
////			Notify.sendNotification("Doctor Appointment Date Changed", devicesList);
//		}

	}

	private ArrayList<DeviceTokenEntity> rescheduleAppointment(DoctorAppointment doctorAppointment, String userType, String loginId) {

		ArrayList<DeviceTokenEntity> devicesList = new ArrayList<DeviceTokenEntity>();
		DoctorAppointmentEntity appointmentEntity = null;
		DoctorEntity doctorEntity = null;
		PharmaRepEntity pharmaRepEntity = null;
		boolean isAppointmentChanged=false;
		doctorAppointment.setRescheduled(true);
		if ("pharmarep".equals(userType))
		{
			pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);

			if (pharmaRepEntity != null && pharmaRepEntity.getRepId() != null)
			{
				if(doctorAppointment.getAppointmentId() != null)
				{
					appointmentEntity = doctorAppointmentDAO.findById(DoctorAppointmentEntity.class, doctorAppointment.getAppointmentId());
				}
				else
				{
					appointmentEntity = doctorAppointmentDAO.findByRepNotificationId(pharmaRepEntity.getRepId(),doctorAppointment.getNotificationId());
				}
			}
		}
		else if ("doctor".equals(userType))
		{

			doctorEntity = doctorDAO.findByLoginId(loginId);

			if(doctorEntity != null && doctorEntity.getDoctorId() != null)
			{

				if(doctorAppointment.getAppointmentId() != null)
				{
					appointmentEntity = doctorAppointmentDAO.findById(DoctorAppointmentEntity.class, doctorAppointment.getAppointmentId());
				}
				else
				{
					appointmentEntity = doctorAppointmentDAO.findByDoctorNotificationId(doctorEntity.getDoctorId(), doctorAppointment.getNotificationId());
				}
			}

		}

		if (!Util.isEmpty(appointmentEntity)&& !Util.isZeroOrNull(appointmentEntity.getAppointmentId()))
		{

			if(doctorAppointment.getAppointmentDesc() != null)
			{
				appointmentEntity.setAppointmentDesc(doctorAppointment.getAppointmentDesc());
			}
			if(doctorEntity != null)
			{
				appointmentEntity.setDoctorId(doctorEntity.getDoctorId());
			}
			if(pharmaRepEntity != null)
			{
				appointmentEntity.setPharmaRepId(pharmaRepEntity.getRepId());
			}
			if(doctorAppointment.getDuration() != null)
			{
				appointmentEntity.setDuration(doctorAppointment.getDuration());
			}
			if(doctorAppointment.getFeedback() != null)
			{
				appointmentEntity.setFeedback(doctorAppointment.getFeedback());
			}
			if(doctorAppointment.getLocation() != null)
			{
				appointmentEntity.setLocation(doctorAppointment.getLocation());
			}
			if(doctorAppointment.getNotificationId() != null)
			{
				appointmentEntity.setNotificationId(doctorAppointment.getNotificationId());
			}

			System.out.println("StartDate="+doctorAppointment.getStartDate());

			if(doctorAppointment.getStartDate() != null && doctorAppointment.getStartDate().length() == 14)
			{
				Date oldDate=appointmentEntity.getStartDate();

				appointmentEntity.setStartDate(DateConvertor.convertStringToDate(doctorAppointment.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
					if(appointmentEntity.getStartDate().compareTo(oldDate)!=0){
						isAppointmentChanged=true;
						if(!Util.isZeroOrNull(appointmentEntity.getPharmaRepId())){

							PharmaRepEntity pharmaRepEntity1  = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
							if(pharmaRepEntity1!=null&& !Util.isZeroOrNull(pharmaRepEntity1.getRepId())){

								if(!Util.isZeroOrNull( pharmaRepEntity1.getManagerId())){
//									UserEntity managerUserEntity  = userDAO.findById(UserEntity.class, pharmaRepEntity1.getManagerId());
//									if(managerUserEntity==null || managerUserEntity.getRegToken()==null || "".equalsIgnoreCase(managerUserEntity.getRegToken()))
//									{
//									}else{
										ArrayList<DeviceTokenEntity> devices=(ArrayList<DeviceTokenEntity>) deviceTokenDAO.findByRepId(pharmaRepEntity1.getManagerId());
										if(devices!=null)
										devicesList.addAll(devices);
//									}
								}
//								if(pharmaRepEntity1.getUser().getRegToken()==null || "".equalsIgnoreCase(pharmaRepEntity1.getUser().getRegToken()))
//								{
//								}else{
									ArrayList<DeviceTokenEntity> devices=(ArrayList<DeviceTokenEntity>) deviceTokenDAO.findByRepId(pharmaRepEntity1.getRepId());
									if(devices!=null)
									devicesList.addAll(devices);
//								}
						}
						}else{
							//To all reps in area

							List<String> cities=new ArrayList<String>();
							List<String> zipcodes=new ArrayList<String>();
							for( LocationEntity locationEntity:doctorEntity.getUser().getLocations()){
								if(locationEntity.getZipcode()!=null && !locationEntity.getZipcode().isEmpty())
									zipcodes.add(locationEntity.getZipcode());
								if(locationEntity.getCity()!=null && !locationEntity.getCity().isEmpty())
									cities.add(locationEntity.getCity());
							}
							List<PharmaRepEntity> pharmaRepEntities=pharmaRepDAO.findRepsByLocationInCompany(zipcodes,cities,doctorAppointment.getCompanyId());
							log.info("Reps found in location::"+(Util.isEmpty(pharmaRepEntities)?0:pharmaRepEntities.size()));
							if(Util.isEmpty(pharmaRepEntities))
								pharmaRepEntities=new ArrayList<PharmaRepEntity>();

							List<PharmaRepEntity> reps=pharmaRepDAO.findByCoveredAreaInCompany(appointmentEntity.getLocation(),doctorAppointment.getCompanyId());
							if(!Util.isEmpty(reps))
								pharmaRepEntities.addAll(reps);

							log.info("Reps Found in Covered area::"+pharmaRepEntities.size());

							for(PharmaRepEntity pRepEntity:pharmaRepEntities){

								RepAppointmentEntity repAppointmentEntity = repAppointmentDAO.findByRepAppointmentId(
										pRepEntity.getRepId(),appointmentEntity.getAppointmentId());
								if (repAppointmentEntity != null) {
									repAppointmentEntity.setAppointmentId(appointmentEntity.getAppointmentId());
									repAppointmentEntity.setCreatedOn(new Date());
									repAppointmentEntity.setDoctorId(doctorAppointment.getDoctorId());
									repAppointmentEntity.setRepId(pRepEntity.getRepId());
									repAppointmentEntity.setStatus("Pending");
								} else {
									repAppointmentEntity = new RepAppointmentEntity();
									repAppointmentEntity.setAppointmentId(appointmentEntity.getAppointmentId());
									repAppointmentEntity.setCreatedOn(new Date());
									repAppointmentEntity.setDoctorId(doctorAppointment.getDoctorId());
									repAppointmentEntity.setRepId(pRepEntity.getRepId());
									repAppointmentEntity.setStatus("Pending");
									repAppointmentDAO.persist(repAppointmentEntity);
								}



								List<DeviceTokenEntity> deviceTokenEntities=deviceTokenDAO.findByRepId(pRepEntity.getRepId());
								if(deviceTokenEntities!=null && !deviceTokenEntities.isEmpty()){
									devicesList.addAll(deviceTokenEntities);
								}


								PharmaRepEntity manager=null;
								if(!Util.isZeroOrNull(pRepEntity.getManagerId()))
										manager=pharmaRepDAO.findById(PharmaRepEntity.class, pRepEntity.getManagerId());

								if(Util.isEmpty(manager)|| Util.isZeroOrNull(manager.getRepId()))
									if(!Util.isEmpty(pRepEntity.getManagerEmail()))
									manager=pharmaRepDAO.findByLoginId(pRepEntity.getManagerEmail());


								if(!Util.isEmpty(manager) &&!Util.isZeroOrNull(manager.getRepId())){

									RepAppointmentEntity mgrAppointmentEntity = repAppointmentDAO.findByRepAppointmentId(manager.getRepId(),appointmentEntity.getAppointmentId());
									if (mgrAppointmentEntity != null) {
										mgrAppointmentEntity.setAppointmentId(appointmentEntity.getAppointmentId());
										mgrAppointmentEntity.setCreatedOn(new Date());
										mgrAppointmentEntity.setDoctorId(doctorAppointment.getDoctorId());
										mgrAppointmentEntity.setRepId(manager.getRepId());
										mgrAppointmentEntity.setStatus("Pending");
										log.info("REPID::"+repAppointmentEntity.getRepId());
//										repAppointmentDAO.merge(repAppointmentEntity);
									} else {
										mgrAppointmentEntity = new RepAppointmentEntity();
										mgrAppointmentEntity.setAppointmentId(appointmentEntity.getAppointmentId());
										mgrAppointmentEntity.setCreatedOn(new Date());
										mgrAppointmentEntity.setDoctorId(doctorAppointment.getDoctorId());
										mgrAppointmentEntity.setRepId(manager.getRepId());
										mgrAppointmentEntity.setStatus("Pending");
										repAppointmentDAO.persist(mgrAppointmentEntity);
									}



									List<DeviceTokenEntity> deviceTokenEntities1=deviceTokenDAO.findByManagerId(manager.getRepId());
									if(!Util.isEmpty(deviceTokenEntities1)){
										devicesList.addAll(deviceTokenEntities1);
									}

								}

							}


							//to all reps in area end
						}
					}
			}
			if(doctorAppointment.getStatus() != null)
			{
				appointmentEntity.setStatus(doctorAppointment.getStatus());
			}
			if(doctorAppointment.getTitle() != null)
			{
				appointmentEntity.setTitle(doctorAppointment.getTitle());
			}

			if(doctorAppointment.getRemindMe()!=null)
				appointmentEntity.setRemindMe(doctorAppointment.getRemindMe());

			if(appointmentEntity.getRemindMe()!=null){
				long t=appointmentEntity.getStartDate().getTime();
				if(appointmentEntity.getRemindMe().equals("1h"))
					t=t-60*60*1000;
				else if(appointmentEntity.getRemindMe().equals("1d"))
					t=t-24*60*60*1000;
				else if(appointmentEntity.getRemindMe().equals("1w"))
					t=t-7*24*60*60*1000;
				else if(appointmentEntity.getRemindMe().equals("1m"))
					t=t-30*24*60*60*1000;

				appointmentEntity.setReminderTime(new Date(t));
			}
			appointmentEntity.setStatus("Scheduled");
			RepAppointmentEntity repAppointmentEntity=repAppointmentDAO.findByRepAppointmentId(appointmentEntity.getPharmaRepId(), appointmentEntity.getAppointmentId());
			if(!Util.isEmpty(repAppointmentEntity)){
				repAppointmentEntity.setStatus("Accepted");
			}

			doctorAppointmentDAO.merge(appointmentEntity);

			doctorAppointment.setAppointmentId(appointmentEntity.getAppointmentId());
			doctorAppointment.setDoctorId(appointmentEntity.getDoctorId());
			doctorAppointment.setNotificationId(appointmentEntity.getNotificationId());

			DoctorEntity doctor=doctorDAO.findByDoctorId(appointmentEntity.getDoctorId());

			if(isAppointmentChanged &&(devicesList!=null && devicesList.size()>0)){
				return devicesList;
//				notificationService.push("Your Appointment with Dr."+doctor.getUser().getFirstName() +doctor.getUser().getLastName()+" has been changed to "+appointmentEntity.getStartDate(), devicesList);
//				Notify.sendNotification("Doctor Appointment Date Changed", devicesList);
			}
		}
		return null;


		//		return updateDoctorAppointment(doctorAppointment, userType, loginId);
	}

	public ArrayList<DeviceTokenEntity> updateDoctorAppointment(DoctorAppointment doctorAppointment, String userType, String loginId) {
		ArrayList<DeviceTokenEntity> devicesList = new ArrayList<DeviceTokenEntity>();
		DoctorAppointmentEntity appointmentEntity = null;
		DoctorEntity doctorEntity = null;
		PharmaRepEntity pharmaRepEntity = null;
		boolean isAppointmentChanged=false;
		if ("pharmarep".equals(userType))
		{
			pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);

			if (pharmaRepEntity != null && pharmaRepEntity.getRepId() != null)
			{
				if(doctorAppointment.getAppointmentId() != null)
				{
					appointmentEntity = doctorAppointmentDAO.findById(DoctorAppointmentEntity.class, doctorAppointment.getAppointmentId());
				}
				else
				{
					appointmentEntity = doctorAppointmentDAO.findByRepNotificationId(pharmaRepEntity.getRepId(),doctorAppointment.getNotificationId());
				}
			}
		}
		else if ("doctor".equals(userType))
		{

			doctorEntity = doctorDAO.findByLoginId(loginId);

			if(doctorEntity != null && doctorEntity.getDoctorId() != null)
			{

				if(!Util.isZeroOrNull(doctorAppointment.getAppointmentId()))
				{
					appointmentEntity = doctorAppointmentDAO.findById(DoctorAppointmentEntity.class, doctorAppointment.getAppointmentId());
				}
				else
				{
					appointmentEntity = doctorAppointmentDAO.findByDoctorNotificationId(doctorEntity.getDoctorId(), doctorAppointment.getNotificationId());
				}
			}

		}

		if (!Util.isEmpty(appointmentEntity) && !Util.isZeroOrNull(appointmentEntity.getAppointmentId()))
		{

			if(doctorAppointment.getAppointmentDesc() != null)
			{
				appointmentEntity.setAppointmentDesc(doctorAppointment.getAppointmentDesc());
			}
			if(doctorEntity != null)
			{
				appointmentEntity.setDoctorId(doctorEntity.getDoctorId());
			}
			if(pharmaRepEntity != null)
			{
				appointmentEntity.setPharmaRepId(pharmaRepEntity.getRepId());
			}
			if(doctorAppointment.getDuration() != null)
			{
				appointmentEntity.setDuration(doctorAppointment.getDuration());
			}
			if(doctorAppointment.getFeedback() != null)
			{
				appointmentEntity.setFeedback(doctorAppointment.getFeedback());
			}
			if(doctorAppointment.getLocation() != null)
			{
				appointmentEntity.setLocation(doctorAppointment.getLocation());
			}
			if(doctorAppointment.getNotificationId() != null)
			{
				appointmentEntity.setNotificationId(doctorAppointment.getNotificationId());
			}

			System.out.println("StartDate="+doctorAppointment.getStartDate());

			if(doctorAppointment.getStartDate() != null && doctorAppointment.getStartDate().length() == 14)
			{
				Date oldDate=appointmentEntity.getStartDate();

				appointmentEntity.setStartDate(DateConvertor.convertStringToDate(doctorAppointment.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
					if(appointmentEntity.getStartDate().compareTo(oldDate)!=0){
						isAppointmentChanged=true;
						PharmaRepEntity pharmaRepEntity1  = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
						if(pharmaRepEntity1!=null){

							if(!Util.isZeroOrNull( pharmaRepEntity1.getManagerId())){
//								UserEntity managerUserEntity  = userDAO.findById(UserEntity.class, pharmaRepEntity1.getManagerId());
//								if(managerUserEntity==null || managerUserEntity.getRegToken()==null || "".equalsIgnoreCase(managerUserEntity.getRegToken()))
//								{
//								}else{
									ArrayList<DeviceTokenEntity> devices=(ArrayList<DeviceTokenEntity>) deviceTokenDAO.findByRepId(pharmaRepEntity1.getManagerId());
									if(devices!=null)
									devicesList.addAll(devices);
//								}
							}
//							if(pharmaRepEntity1.getUser().getRegToken()==null || "".equalsIgnoreCase(pharmaRepEntity1.getUser().getRegToken()))
//							{
//							}else{
								ArrayList<DeviceTokenEntity> devices=(ArrayList<DeviceTokenEntity>) deviceTokenDAO.findByRepId(pharmaRepEntity1.getRepId());
								if(devices!=null)
								devicesList.addAll(devices);
//							}
						}
					}
			}
			if(doctorAppointment.getStatus() != null)
			{
				appointmentEntity.setStatus(doctorAppointment.getStatus());
			}
			if(doctorAppointment.getTitle() != null)
			{
				appointmentEntity.setTitle(doctorAppointment.getTitle());
			}

			if(doctorAppointment.getRemindMe()!=null)
				appointmentEntity.setRemindMe(doctorAppointment.getRemindMe());

			if(appointmentEntity.getRemindMe()!=null){
				long t=appointmentEntity.getStartDate().getTime();
				if(appointmentEntity.getRemindMe().equals("1h"))
					t=t-60*60*1000;
				else if(appointmentEntity.getRemindMe().equals("1d"))
					t=t-24*60*60*1000;
				else if(appointmentEntity.getRemindMe().equals("1w"))
					t=t-7*24*60*60*1000;
				else if(appointmentEntity.getRemindMe().equals("1m"))
					t=t-30*24*60*60*1000;

				appointmentEntity.setReminderTime(new Date(t));
			}
			appointmentEntity.setStatus(doctorAppointment.getStatus());
			RepAppointmentEntity repAppointmentEntity=repAppointmentDAO.findByRepAppointmentId(appointmentEntity.getPharmaRepId(), appointmentEntity.getAppointmentId());
			if(!"Completed".equals(doctorAppointment.getStatus()) && !Util.isEmpty(repAppointmentEntity)){
				repAppointmentEntity.setStatus("Pending");
			}

			doctorAppointmentDAO.merge(appointmentEntity);

			doctorAppointment.setAppointmentId(appointmentEntity.getAppointmentId());
			doctorAppointment.setDoctorId(appointmentEntity.getDoctorId());
			doctorAppointment.setNotificationId(appointmentEntity.getNotificationId());

			DoctorEntity doctor=doctorDAO.findByDoctorId(appointmentEntity.getDoctorId());

			if(isAppointmentChanged &&(devicesList!=null && devicesList.size()>0)){
				return devicesList;
//				notificationService.push("Your Appointment with Dr."+doctor.getUser().getFirstName() +doctor.getUser().getLastName()+" has been changed to "+appointmentEntity.getStartDate(), devicesList);
//				Notify.sendNotification("Doctor Appointment Date Changed", devicesList);
			}
		}
		return null;

	}


	public List<DoctorAppointment> getAppointmentForDoctor(String loginId, Date startdate) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
		List<DoctorAppointmentEntity> appointmentEntities = null;
		try
		{
			System.out.println("DoctorId="+doctorEntity.getDoctorId());
			System.out.println("startdate="+startdate);
			appointmentEntities = doctorAppointmentDAO.findByDoctorId(doctorEntity.getDoctorId(), startdate,"Completed");

		} catch (Exception e)
		{
			System.out.println("Entry not found");
		}
		for (DoctorAppointmentEntity appointmentEntity : appointmentEntities)
		{
			DoctorAppointment appointment = new DoctorAppointment();
			appointment.setAppointmentId(appointmentEntity.getAppointmentId());
			appointment.setAppointmentDesc(appointmentEntity.getAppointmentDesc());
			appointment.setCreatedOn(appointmentEntity.getCreatedOn());
			if(doctorEntity.getUser() != null)
			{
				appointment.setDoctorName(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName());
			}
			appointment.setDoctorId(appointmentEntity.getDoctorId());
			if(appointmentEntity.getPharmaRepId() != null)
			{
				appointment.setPharmaRepId(appointmentEntity.getPharmaRepId());
				PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
				if(pharmaRepEntity.getUser()  != null)
				{
					appointment.setPharmaRepName(pharmaRepEntity.getUser().getFirstName() + " " + pharmaRepEntity.getUser().getLastName());
				}
			}
			appointment.setDuration(appointmentEntity.getDuration());
			appointment.setFeedback(appointmentEntity.getFeedback());
			appointment.setLocation(appointmentEntity.getLocation());
			appointment.setRemindMe(appointmentEntity.getRemindMe());
			appointment.setNotificationId(appointmentEntity.getNotificationId());
			if(appointmentEntity.getNotificationId() != null)
			{
				NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, appointmentEntity.getNotificationId());
				if(notificationEntity != null)
				{
					if(notificationEntity.getCompanyId() != null)
					{
						CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, notificationEntity.getCompanyId());
						if(companyEntity != null)
						{
							appointment.setCompanyId(companyEntity.getCompanyId());
							appointment.setCompanyname(companyEntity.getCompanyName());
						}
					}
					if(notificationEntity.getTherapeuticId() != null)
					{
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, notificationEntity.getTherapeuticId());
						if(therapeuticAreaEntity != null)
						{
							appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
							appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}
					}
				}
			}
			appointment.setStartDate(DateConvertor.convertDateToString(appointmentEntity.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
			appointment.setStatus(appointmentEntity.getStatus());
			appointment.setTitle(appointmentEntity.getTitle());
			appointments.add(appointment);
		}
			sort(appointments,Sorting.ASC);
			return appointments;
	}

	public List<DoctorAppointment> getAppointmentForDoctor(String loginId) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
		List<DoctorAppointmentEntity> appointmentEntities = null;
		try
		{
			appointmentEntities = doctorAppointmentDAO.findByDoctorId(doctorEntity.getDoctorId(),"Completed");

		} catch (Exception e)
		{
			System.out.println("Entry not found");
		}
		for (DoctorAppointmentEntity appointmentEntity : appointmentEntities)
		{
			DoctorAppointment appointment = new DoctorAppointment();
			appointment.setAppointmentId(appointmentEntity.getAppointmentId());
			appointment.setAppointmentDesc(appointmentEntity.getAppointmentDesc());
			appointment.setCreatedOn(appointmentEntity.getCreatedOn());
			if(doctorEntity.getUser() != null)
			{
				appointment.setDoctorName(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName());
			}
			appointment.setDoctorId(appointmentEntity.getDoctorId());
			if(appointmentEntity.getPharmaRepId() != null)
			{
				appointment.setPharmaRepId(appointmentEntity.getPharmaRepId());
				PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
				if(!Util.isEmpty(pharmaRepEntity) && !Util.isZeroOrNull(pharmaRepEntity.getRepId()) && !Util.isEmpty(pharmaRepEntity.getUser()))
				{
					appointment.setPharmaRepName(pharmaRepEntity.getUser().getFirstName() + " " + pharmaRepEntity.getUser().getLastName());
				}
			}
			appointment.setDuration(appointmentEntity.getDuration());
			appointment.setFeedback(appointmentEntity.getFeedback());
			appointment.setLocation(appointmentEntity.getLocation());
			appointment.setRemindMe(appointmentEntity.getRemindMe());
			appointment.setNotificationId(appointmentEntity.getNotificationId());
			if(appointmentEntity.getNotificationId() != null)
			{
				NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, appointmentEntity.getNotificationId());
				if(notificationEntity != null)
				{
					if(notificationEntity.getCompanyId() != null)
					{
						CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, notificationEntity.getCompanyId());
						if(companyEntity != null)
						{
							appointment.setCompanyId(companyEntity.getCompanyId());
							appointment.setCompanyname(companyEntity.getCompanyName());
						}
					}
					if(notificationEntity.getTherapeuticId() != null)
					{
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, notificationEntity.getTherapeuticId());
						if(therapeuticAreaEntity != null)
						{
							appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
							appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}
					}
				}
			}
			appointment.setStartDate(DateConvertor.convertDateToString(appointmentEntity.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
			appointment.setStatus(appointmentEntity.getStatus());
			appointment.setTitle(appointmentEntity.getTitle());
			appointments.add(appointment);
		}
			sort(appointments,Sorting.ASC);
			return appointments;
	}

	public List<DoctorAppointment> getAppointmentForRep(String loginId, Date startdate)
	{
		List<DoctorAppointmentEntity> appointmentEntities = null;
		List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
		try
		{
			PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);
			if(pharmaRepEntity != null)
			{
				appointmentEntities = doctorAppointmentDAO.findByRepId(pharmaRepEntity.getRepId(), startdate);
				for (DoctorAppointmentEntity appointmentEntity : appointmentEntities)
				{
					DoctorAppointment appointment = new DoctorAppointment();
					appointment.setAppointmentId(appointmentEntity.getAppointmentId());
					appointment.setAppointmentDesc(appointmentEntity.getAppointmentDesc());
					appointment.setCreatedOn(appointmentEntity.getCreatedOn());
					if(appointmentEntity.getDoctorId() != null)
					{
						appointment.setDoctorId(appointmentEntity.getDoctorId());
						DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, appointmentEntity.getDoctorId());
						if(doctorEntity.getUser()  != null)
						{
							appointment.setPharmaRepName("Dr."+doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName());
						}
						if(doctorEntity!=null && doctorEntity.getTherapeuticId() != null)
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, Integer.parseInt(doctorEntity.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
								appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
					}
					appointment.setDuration(appointmentEntity.getDuration());
					appointment.setFeedback(appointmentEntity.getFeedback());
					appointment.setLocation(appointmentEntity.getLocation());
					appointment.setNotificationId(appointmentEntity.getNotificationId());
					if(appointmentEntity.getNotificationId() != null)
					{
						NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, appointmentEntity.getNotificationId());
						if(notificationEntity != null)
						{
							if(notificationEntity.getCompanyId() != null)
							{
								CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, notificationEntity.getCompanyId());
								if(companyEntity != null)
								{
									appointment.setCompanyId(companyEntity.getCompanyId());
									appointment.setCompanyname(companyEntity.getCompanyName());
								}
							}
							/*if(notificationEntity.getTherapeuticId() != null)
							{
								TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, notificationEntity.getTherapeuticId());
								if(therapeuticAreaEntity != null)
								{
									appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
									appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
								}
							}*/
						}
					}
					appointment.setStartDate(DateConvertor.convertDateToString(appointmentEntity.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
					appointment.setStatus(appointmentEntity.getStatus());
					appointment.setTitle(appointmentEntity.getTitle());
					if(appointmentEntity.getPharmaRepId() != null)
					{
						PharmaRepEntity repEntity = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
						if(repEntity.getUser() != null)
						{
							String name = repEntity.getUser().getFirstName() + " " + repEntity.getUser().getLastName();
							appointment.setPharmaRepName(name);
						}
					}
					appointments.add(appointment);
				}
			}
			sort(appointments,Sorting.DESC);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return appointments;
	}

	public List<DoctorAppointment> getAppointmentForRep(String loginId)
	{
		List<DoctorAppointmentEntity> appointmentEntities = null;
		List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
		try
		{
			PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);
			if(pharmaRepEntity != null)
			{
				appointmentEntities = doctorAppointmentDAO.findByRepId(pharmaRepEntity.getRepId());
				for (DoctorAppointmentEntity appointmentEntity : appointmentEntities)
				{
					DoctorAppointment appointment = new DoctorAppointment();
					appointment.setAppointmentId(appointmentEntity.getAppointmentId());
					appointment.setAppointmentDesc(appointmentEntity.getAppointmentDesc());
					appointment.setCreatedOn(appointmentEntity.getCreatedOn());
					if(appointmentEntity.getDoctorId() != null)
					{
						appointment.setDoctorId(appointmentEntity.getDoctorId());
						DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, appointmentEntity.getDoctorId());
						if(doctorEntity.getUser()  != null)
						{
							appointment.setPharmaRepName("Dr."+doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName());
						}
						if(doctorEntity!=null && doctorEntity.getTherapeuticId() != null)
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, Integer.parseInt(doctorEntity.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
								appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
					}
					appointment.setDuration(appointmentEntity.getDuration());
					appointment.setFeedback(appointmentEntity.getFeedback());
					appointment.setLocation(appointmentEntity.getLocation());
					appointment.setNotificationId(appointmentEntity.getNotificationId());
					if(appointmentEntity.getNotificationId() != null)
					{
						NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, appointmentEntity.getNotificationId());
						if(notificationEntity != null)
						{
							if(notificationEntity.getCompanyId() != null)
							{
								CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, notificationEntity.getCompanyId());
								if(companyEntity != null)
								{
									appointment.setCompanyId(companyEntity.getCompanyId());
									appointment.setCompanyname(companyEntity.getCompanyName());
								}
							}

						}
					}
					appointment.setStartDate(DateConvertor.convertDateToString(appointmentEntity.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
					appointment.setStatus(appointmentEntity.getStatus());
					appointment.setTitle(appointmentEntity.getTitle());
					if(appointmentEntity.getPharmaRepId() != null)
					{
						PharmaRepEntity repEntity = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
						if(repEntity.getUser() != null)
						{
							String name = repEntity.getUser().getFirstName() + " " + repEntity.getUser().getLastName();
							appointment.setPharmaRepName(name);
						}
					}
					appointments.add(appointment);
				}
			}
			sort(appointments,Sorting.DESC);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return appointments;
	}

	public List<DoctorAppointment> getAppointmentForRep(String loginId, String  status)
	{
		List<DoctorAppointmentEntity> appointmentEntities = null;
		List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
		try
		{
			PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);
			if(pharmaRepEntity != null)
			{
				appointmentEntities = doctorAppointmentDAO.findByRepIdStatus(pharmaRepEntity.getRepId(), status);
				for (DoctorAppointmentEntity appointmentEntity : appointmentEntities)
				{
					DoctorAppointment appointment = new DoctorAppointment();
					appointment.setAppointmentId(appointmentEntity.getAppointmentId());
					appointment.setAppointmentDesc(appointmentEntity.getAppointmentDesc());
					appointment.setCreatedOn(appointmentEntity.getCreatedOn());
					if(appointmentEntity.getDoctorId() != null)
					{
						appointment.setDoctorId(appointmentEntity.getDoctorId());
						DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, appointmentEntity.getDoctorId());
						System.out.println("**************************** doctorEntity " + doctorEntity + "  " + doctorEntity.getUser());
						if(doctorEntity != null && doctorEntity.getUser() != null)
						{
							String name = Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName();
							appointment.setDoctorName(name);
							System.out.println("**************************** doctorEntity " + name);
						}

						if(doctorEntity!=null && doctorEntity.getTherapeuticId() != null)
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, Integer.parseInt(doctorEntity.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
								appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
					}
					appointment.setDuration(appointmentEntity.getDuration());
					appointment.setFeedback(appointmentEntity.getFeedback());
					appointment.setLocation(appointmentEntity.getLocation());
					appointment.setNotificationId(appointmentEntity.getNotificationId());
					if(appointmentEntity.getNotificationId() != null)
					{
						NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, appointmentEntity.getNotificationId());
						if(notificationEntity != null)
						{
							if(notificationEntity.getCompanyId() != null)
							{
								CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, notificationEntity.getCompanyId());
								if(companyEntity != null)
								{
									appointment.setCompanyId(companyEntity.getCompanyId());
									appointment.setCompanyname(companyEntity.getCompanyName());
								}
							}

						}
					}
					appointment.setStartDate(DateConvertor.convertDateToString(appointmentEntity.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
					appointment.setStatus(appointmentEntity.getStatus());
					appointment.setTitle(appointmentEntity.getTitle());
					if(appointmentEntity.getPharmaRepId() != null)
					{
						PharmaRepEntity repEntity = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
						if(repEntity != null && repEntity.getUser() != null)
						{
							String name = repEntity.getUser().getFirstName() + " " + repEntity.getUser().getLastName();
							appointment.setPharmaRepName(name);
							appointment.setPharmaRepId(repEntity.getRepId());
						}
					}
					appointments.add(appointment);
				}
			}
			if("Scheduled".equals(status))
				sort(appointments,Sorting.ASC);
			else
				sort(appointments,Sorting.DESC);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return appointments;
	}


	public List<DoctorAppointment> getAppointmentForRep(Integer repId)
	{
		List<DoctorAppointmentEntity> appointmentEntities = null;
		List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
		try
		{
			PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findById(PharmaRepEntity.class,repId);
			if(pharmaRepEntity != null)
			{
				appointmentEntities = doctorAppointmentDAO.findByRepId(pharmaRepEntity.getRepId());
				for (DoctorAppointmentEntity appointmentEntity : appointmentEntities)
				{
					DoctorAppointment appointment = new DoctorAppointment();
					appointment.setAppointmentId(appointmentEntity.getAppointmentId());
					appointment.setAppointmentDesc(appointmentEntity.getAppointmentDesc());
					appointment.setCreatedOn(appointmentEntity.getCreatedOn());
					System.out.println("Setting Doctor Name for Id="+appointmentEntity.getDoctorId());
					if(appointmentEntity.getDoctorId() != null)
					{
						appointment.setDoctorId(appointmentEntity.getDoctorId());
						DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, appointmentEntity.getDoctorId());
						if(doctorEntity.getUser()  != null)
						{
							System.out.println("Setting Doctor Name");
							appointment.setDoctorName(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName());
						}
					}
					appointment.setDuration(appointmentEntity.getDuration());
					appointment.setFeedback(appointmentEntity.getFeedback());
					appointment.setLocation(appointmentEntity.getLocation());
					appointment.setNotificationId(appointmentEntity.getNotificationId());
					if(appointmentEntity.getNotificationId() != null)
					{
						NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, appointmentEntity.getNotificationId());
						if(notificationEntity != null)
						{
							if(notificationEntity.getCompanyId() != null)
							{
								CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, notificationEntity.getCompanyId());
								if(companyEntity != null)
								{
									appointment.setCompanyId(companyEntity.getCompanyId());
									appointment.setCompanyname(companyEntity.getCompanyName());
								}
							}
							if(notificationEntity.getTherapeuticId() != null)
							{
								TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, notificationEntity.getTherapeuticId());
								if(therapeuticAreaEntity != null)
								{
									appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
									appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
								}
							}
						}
					}
					appointment.setStartDate(DateConvertor.convertDateToString(appointmentEntity.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
					appointment.setStatus(appointmentEntity.getStatus());
					appointment.setTitle(appointmentEntity.getTitle());
					if(appointmentEntity.getPharmaRepId() != null)
					{
						PharmaRepEntity repEntity = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
						if(repEntity.getUser() != null)
						{
							String name = repEntity.getUser().getFirstName() + " " + repEntity.getUser().getLastName();
							appointment.setPharmaRepName(name);
						}
					}
					appointments.add(appointment);
				}
			}
			sort(appointments,Sorting.DESC);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return appointments;
	}

	public List<DoctorAppointment> getAppointmentForMyTeam(String loginId, Date startdate)
	{
		List<DoctorAppointmentEntity> appointmentEntities = null;
		List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
		try
		{
			PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);
			if(pharmaRepEntity != null)
			{
				appointmentEntities = doctorAppointmentDAO.findByManagerId(loginId, startdate);
				for (DoctorAppointmentEntity appointmentEntity : appointmentEntities)
				{
					DoctorAppointment appointment = new DoctorAppointment();
					appointment.setAppointmentId(appointmentEntity.getAppointmentId());
					appointment.setAppointmentDesc(appointmentEntity.getAppointmentDesc());
					appointment.setCreatedOn(appointmentEntity.getCreatedOn());
					if(appointmentEntity.getDoctorId() != null)
					{
						appointment.setDoctorId(appointmentEntity.getDoctorId());
						DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, appointmentEntity.getDoctorId());
						if(doctorEntity.getUser()  != null)
						{
							appointment.setPharmaRepName("Dr."+doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName());
						}
						if(doctorEntity!=null && doctorEntity.getTherapeuticId() != null)
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, Integer.parseInt(doctorEntity.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
								appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
					}
					appointment.setDuration(appointmentEntity.getDuration());
					appointment.setFeedback(appointmentEntity.getFeedback());
					appointment.setLocation(appointmentEntity.getLocation());
					appointment.setNotificationId(appointmentEntity.getNotificationId());
					if(appointmentEntity.getNotificationId() != null)
					{
						NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, appointmentEntity.getNotificationId());
						if(notificationEntity != null)
						{
							if(notificationEntity.getCompanyId() != null)
							{
								CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, notificationEntity.getCompanyId());
								if(companyEntity != null)
								{
									appointment.setCompanyId(companyEntity.getCompanyId());
									appointment.setCompanyname(companyEntity.getCompanyName());
								}
							}
							/*if(notificationEntity.getTherapeuticId() != null)
							{
								TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, notificationEntity.getTherapeuticId());
								if(therapeuticAreaEntity != null)
								{
									appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
									appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
								}
							}*/
						}
					}
					appointment.setStartDate(DateConvertor.convertDateToString(appointmentEntity.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
					appointment.setStatus(appointmentEntity.getStatus());
					appointment.setTitle(appointmentEntity.getTitle());
					if(appointmentEntity.getPharmaRepId() != null)
					{
						PharmaRepEntity repEntity = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
						if(repEntity.getUser() != null)
						{
							String name = repEntity.getUser().getFirstName() + " " + repEntity.getUser().getLastName();
							appointment.setPharmaRepName(name);
						}
					}
					appointments.add(appointment);
				}
			}
			sort(appointments,Sorting.DESC);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return appointments;
	}

	public List<DoctorAppointment> getRepPendingAppointment(String repLoginId)
	{
		List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
		try
		{
			PharmaRepEntity repEntity = pharmaRepDAO.findByLoginId(repLoginId);
			if(repEntity != null && repEntity.getRepId() !=null)
			{
				Integer repId = repEntity.getRepId();
				List<RepAppointmentEntity> repAppointments = repAppointmentDAO.findByPharmaRepId(repId, "Pending");
				for(RepAppointmentEntity repAppointmentEntity : repAppointments)
				{
					System.out.println("Fetching appointmentId::"+repAppointmentEntity.getAppointmentId());
					Integer appointmentId = repAppointmentEntity.getAppointmentId();
					if(!Util.isZeroOrNull(appointmentId)){
						DoctorAppointmentEntity appointmentEntity = doctorAppointmentDAO.findById(DoctorAppointmentEntity.class, appointmentId);

						DoctorAppointment appointment = new DoctorAppointment();
						appointment.setAppointmentId(appointmentEntity.getAppointmentId());
						appointment.setAppointmentDesc(appointmentEntity.getAppointmentDesc());
						appointment.setDoctorId(appointmentEntity.getDoctorId());
						if(appointmentEntity.getDoctorId() != null)
						{
							DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, appointmentEntity.getDoctorId());
							if(doctorEntity != null && doctorEntity.getUser() != null)
							{
								String name = Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName();
								appointment.setDoctorName(name);
								appointment.setDoctorId(doctorEntity.getDoctorId());
							}
							if(doctorEntity!=null && doctorEntity.getTherapeuticId() != null)
							{
								TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, Integer.parseInt(doctorEntity.getTherapeuticId()));
								if(therapeuticAreaEntity != null)
								{
									appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
									appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
								}
							}
						}

						String name = repEntity.getUser().getFirstName() + " " + repEntity.getUser().getLastName();
						appointment.setPharmaRepName(name);
						appointment.setPharmaRepId(repEntity.getRepId());

						appointment.setDuration(appointmentEntity.getDuration());
						appointment.setFeedback(appointmentEntity.getFeedback());
						appointment.setLocation(appointmentEntity.getLocation());
						appointment.setNotificationId(appointmentEntity.getNotificationId());
						if(appointmentEntity.getNotificationId() != null)
						{
							NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, appointmentEntity.getNotificationId());
							if(notificationEntity != null)
							{
								if(notificationEntity.getCompanyId() != null)
								{
									CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, notificationEntity.getCompanyId());
									if(companyEntity != null)
									{
										appointment.setCompanyId(companyEntity.getCompanyId());
										appointment.setCompanyname(companyEntity.getCompanyName());
									}
								}

							}
						}
						appointment.setStartDate(DateConvertor.convertDateToString(appointmentEntity.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
						appointment.setStatus(appointmentEntity.getStatus());
						appointment.setTitle(appointmentEntity.getTitle());
						if("Published".equalsIgnoreCase(appointmentEntity.getStatus()))
						{
							appointments.add(appointment);
						}
						else if( "Scheduled".equalsIgnoreCase(appointmentEntity.getStatus()))
						{
							repAppointmentEntity.setStatus("Scheduled");
							repAppointmentDAO.merge(repAppointmentEntity);
						}

					}
				}
			}
			sort(appointments,Sorting.DESC);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return appointments;
	}

	public List<DoctorAppointment> getMyTeamPendingAppointment(String repLoginId)
	{
		List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
		try
		{
			PharmaRepEntity repEntity = pharmaRepDAO.findByLoginId(repLoginId);
			if(repEntity != null && repEntity.getRepId() !=null)
			{
				Integer repId = repEntity.getRepId();
				List<RepAppointmentEntity> repAppointments = repAppointmentDAO.findByPharmaManagerId(repLoginId, "Pending");
				Set<Integer> appointmentIds=new java.util.HashSet<Integer>();
				for(RepAppointmentEntity repAppointmentEntity : repAppointments)
				{
					if(appointmentIds.add(repAppointmentEntity.getAppointmentId())){
						Integer appointmentId = repAppointmentEntity.getAppointmentId();

						DoctorAppointmentEntity appointmentEntity = doctorAppointmentDAO.findById(DoctorAppointmentEntity.class, appointmentId);
						DoctorAppointment appointment = new DoctorAppointment();
						appointment.setAppointmentId(appointmentEntity.getAppointmentId());
						appointment.setAppointmentDesc(appointmentEntity.getAppointmentDesc());
						appointment.setDoctorId(appointmentEntity.getDoctorId());
						if(appointmentEntity.getDoctorId() != null)
						{
							DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, appointmentEntity.getDoctorId());
							if(doctorEntity.getUser() != null)
							{
								String name = Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName();
								appointment.setDoctorName(name);
							}
							if(doctorEntity!=null && doctorEntity.getTherapeuticId() != null)
							{
								TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, Integer.parseInt(doctorEntity.getTherapeuticId()));
								if(therapeuticAreaEntity != null)
								{
									appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
									appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
								}
							}
						}
						appointment.setDuration(appointmentEntity.getDuration());
						appointment.setFeedback(appointmentEntity.getFeedback());
						appointment.setLocation(appointmentEntity.getLocation());
						appointment.setNotificationId(appointmentEntity.getNotificationId());
						appointment.setNotificationId(appointmentEntity.getNotificationId());
						if(appointmentEntity.getNotificationId() != null)
						{
							NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, appointmentEntity.getNotificationId());
							if(notificationEntity != null)
							{
								if(notificationEntity.getCompanyId() != null)
								{
									CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, notificationEntity.getCompanyId());
									if(companyEntity != null)
									{
										appointment.setCompanyId(companyEntity.getCompanyId());
										appointment.setCompanyname(companyEntity.getCompanyName());
									}
								}
								/*if(notificationEntity.getTherapeuticId() != null)
								{
									TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, notificationEntity.getTherapeuticId());
									if(therapeuticAreaEntity != null)
									{
										appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
										appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
									}
								}*/
							}
						}
						appointment.setStartDate(DateConvertor.convertDateToString(appointmentEntity.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
						appointment.setStatus(appointmentEntity.getStatus());
						appointment.setTitle(appointmentEntity.getTitle());
						if(appointmentEntity.getStatus() != null && "Published".equalsIgnoreCase(appointmentEntity.getStatus()))
						{
							appointments.add(appointment);
						}
						else if(appointmentEntity.getStatus() != null && "Scheduled".equalsIgnoreCase(appointmentEntity.getStatus()))
						{
							repAppointmentEntity.setStatus("Scheduled");
							repAppointmentDAO.merge(repAppointmentEntity);
						}
					}
				}
			}

			sort(appointments,Sorting.DESC);

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return appointments;
	}

	private void sort(List<DoctorAppointment> appointments,int order) {
		if(!Util.isEmpty(appointments)){
			switch(order){
			case Sorting.ASC:
				Collections.sort(appointments,new Comparator<DoctorAppointment>() {

					@Override
					public int compare(DoctorAppointment o1, DoctorAppointment o2) {
						return o1.getStartDate().compareTo(o2.getStartDate());
					}
				});
				break;
			case Sorting.DESC:
				Collections.sort(appointments,new Comparator<DoctorAppointment>() {

					@Override
					public int compare(DoctorAppointment o1, DoctorAppointment o2) {
						return o2.getStartDate().compareTo(o1.getStartDate());
					}
				});
				break;
			}
		}
	}

	public String acceptAppointmentByRep(DoctorAppointment doctorAppointment,String repLoginId) throws Exception
	{
		PharmaRepEntity repEntity = pharmaRepDAO.findByLoginId(repLoginId);
		String result = "";
		if(repEntity != null)
		{
			if(doctorAppointment.getAppointmentId() != null)
			{
				RepAppointmentEntity repAppointmentEntity = repAppointmentDAO.findByRepAppointmentId(repEntity.getRepId(), doctorAppointment.getAppointmentId());
				if("Accepted".equalsIgnoreCase(doctorAppointment.getStatus()))
				{
					if(repAppointmentEntity != null)
					{
						if("Pending".equalsIgnoreCase(repAppointmentEntity.getStatus()))
						{
							DoctorAppointmentEntity doctorAppointmentEntity = doctorAppointmentDAO.findById(DoctorAppointmentEntity.class, doctorAppointment.getAppointmentId());
							if(doctorAppointmentEntity != null && "Published".equalsIgnoreCase(doctorAppointmentEntity.getStatus()))
							{
								//Conflicted
								Date startDate=doctorAppointmentEntity.getStartDate();
								Date endDate=DateUtils.addMinutes(startDate,doctorAppointmentEntity.getDuration()==null?0:doctorAppointmentEntity.getDuration());
								log.info("startDate-->"+startDate);
								log.info("endDate-->"+endDate);
								log.info("appointmentId-->"+doctorAppointment.getAppointmentId());
								List<DoctorAppointmentEntity> repAppointments=repAppointmentDAO.findAppointmentForMedRep(repEntity.getRepId(), startDate,endDate);

								boolean hasAppointments=!Util.isEmpty(repAppointments);
								boolean currentAppointment=hasAppointments && repAppointments.size()==1 && doctorAppointment.getAppointmentId().equals(repAppointments.get(0).getAppointmentId());
								boolean hasMoreAppointments=hasAppointments && repAppointments.size()>1;


								log.info("repAppointments.size()="+(Util.isEmpty(repAppointments)?0:repAppointments.size()));
								log.info("currentAppointment,hasMoreAppointment::"+currentAppointment+","+hasMoreAppointments);
								log.info("Force Accept-->>"+doctorAppointment.isForceAccept());

								boolean conflictingAppointment=!doctorAppointment.isForceAccept() && hasAppointments && !currentAppointment ;
								boolean tooManyAppointments=!doctorAppointment.isForceAccept() && hasMoreAppointments;
								log.info("hasAppointments::"+hasAppointments);
								log.info("conflictingAppointment::"+conflictingAppointment);
								log.info("tooManyAppointments::"+tooManyAppointments);

								if(conflictingAppointment|| tooManyAppointments){
										return "You already had an appointment at the stipulated time";
								}
								//Conflict end
								doctorAppointmentEntity.setStatus("Scheduled");
								doctorAppointmentEntity.setPharmaRepId(repEntity.getRepId());
								repAppointmentEntity.setStatus("Accepted");
								repAppointmentDAO.merge(repAppointmentEntity);
								repAppointmentDAO.updateStatusByAppointmentId("Scheduled", doctorAppointment.getAppointmentId(), repEntity.getRepId());

								if(Util.isZeroOrNull(repEntity.getManagerId())){
									PharmaRepEntity mgr=pharmaRepDAO.findByLoginId(repEntity.getManagerEmail());
									if(!Util.isEmpty(mgr)&& !Util.isZeroOrNull(mgr.getRepId()))
										repEntity.setManagerId(mgr.getManagerId());
								}

								RepAppointmentEntity mgrAppointment=repAppointmentDAO.findByRepAppointmentId(repEntity.getManagerId(), doctorAppointment.getAppointmentId());
								if(!Util.isEmpty(mgrAppointment)){
									mgrAppointment.setStatus("Scheduled");
								}
								doctorAppointmentDAO.merge(doctorAppointmentEntity);
								result = "Successful";
								if(doctorAppointment.getDoctorId() != null)
								{
									DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, doctorAppointment.getDoctorId());
									if(doctorEntity != null)
									{
										UserEntity user = doctorEntity.getUser();
										if(user != null)
										{
											SMS sms = new SMS();
											sms.setPhoneNumber(user.getMobileNo());
											sms.setTemplate(SMSService.CONFIRM_APPOINTMENT);
											Map<String, String> valueMap = new HashMap<String, String>();
											valueMap.put("NAME",Util.getTitle(user.getTitle())+user.getFirstName() + " " + user.getLastName());
											if(doctorAppointmentEntity.getNotificationId() != null)
											{
												NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, doctorAppointmentEntity.getNotificationId());
												if(notificationEntity != null)
												{
													valueMap.put("NOTIFICATION", notificationEntity.getNotificationName());
												}
												else
												{
													valueMap.put("NOTIFICATION", doctorAppointmentEntity.getTitle());
												}
											}
											smsService.sendSMS(sms);
										}
									}
								}


							}
							else
							{
								result = "Appointment has already been accepted.";
							}
						}
						else
						{
							result = "Appointment is already setup.";
						}
					}
					else
					{
						result = "No appointment found for given appointment id.";
					}
				}
				//The status is not Accepted, so assume it is to reject.
				else
				{
					repAppointmentEntity.setStatus("Declined");
					repAppointmentDAO.merge(repAppointmentEntity);
					result = "Successful";
				}



			}
		}
		return result;
	}


	public List<DoctorAppointment> getAppointmentByNotificationId(Integer notificationId)
	{
		List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
		try
		{


			List<DoctorAppointmentEntity> appointmentEntities = doctorAppointmentDAO.findByNotificationId(notificationId);
			for(DoctorAppointmentEntity appointmentEntity : appointmentEntities)
			{
				DoctorAppointment appointment = new DoctorAppointment();
				appointment.setAppointmentId(appointmentEntity.getAppointmentId());
				appointment.setAppointmentDesc(appointmentEntity.getAppointmentDesc());
				appointment.setDoctorId(appointmentEntity.getDoctorId());
				if(appointmentEntity.getDoctorId() != null)
				{
					DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, appointmentEntity.getDoctorId());
					if(doctorEntity.getUser() != null)
					{
						String name =Util.getTitle(doctorEntity.getUser().getTitle())+ doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName();
						appointment.setDoctorName(name);
					}
				}
				appointment.setDuration(appointmentEntity.getDuration());
				appointment.setFeedback(appointmentEntity.getFeedback());
				appointment.setLocation(appointmentEntity.getLocation());
				appointment.setNotificationId(appointmentEntity.getNotificationId());
				appointment.setPharmaRepId(appointmentEntity.getPharmaRepId());
				if(appointmentEntity.getPharmaRepId() != null)
				{
					PharmaRepEntity repEntity = pharmaRepDAO.findById(PharmaRepEntity.class, appointmentEntity.getPharmaRepId());
					if(repEntity.getUser() != null)
					{
						String name = Util.getTitle(repEntity.getUser().getTitle())+repEntity.getUser().getFirstName() + " " + repEntity.getUser().getLastName();
						appointment.setPharmaRepName(name);
					}
				}
				if(appointmentEntity.getNotificationId() != null)
				{
					NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, appointmentEntity.getNotificationId());
					if(notificationEntity != null)
					{
						if(notificationEntity.getCompanyId() != null)
						{
							CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, notificationEntity.getCompanyId());
							if(companyEntity != null)
							{
								appointment.setCompanyId(companyEntity.getCompanyId());
								appointment.setCompanyname(companyEntity.getCompanyName());
							}
						}
						if(notificationEntity.getTherapeuticId() != null)
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, notificationEntity.getTherapeuticId());
							if(therapeuticAreaEntity != null)
							{
								appointment.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
								appointment.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
					}
				}
				appointment.setStartDate(DateConvertor.convertDateToString(appointmentEntity.getStartDate(), DateConvertor.YYYYMMDDHHMISS));
				appointment.setStatus(appointmentEntity.getStatus());
				appointment.setTitle(appointmentEntity.getTitle());
				appointments.add(appointment);
			}

			sort(appointments,Sorting.DESC);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return appointments;
	}

}

interface Sorting{
	int ASC=0;
	int DESC=1;
}