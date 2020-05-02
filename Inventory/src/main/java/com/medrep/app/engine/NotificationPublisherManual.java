package com.medrep.app.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DeviceTokenDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorNotificationDAO;
import com.medrep.app.dao.NotificationDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.PharmaRepNotificationDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorNotificationEntity;
import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.PharmaRepNotificationEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.SMSService;
import com.medrep.app.util.AndroidPushNotification;
import com.medrep.app.util.IosPushNotification;
import com.medrep.app.util.Util;

//@Transactional
@Service
//@EnableScheduling
public class NotificationPublisherManual {



	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	DoctorNotificationDAO doctorNotificationDAO;

	@Autowired
	NotificationDAO notificationDAO;

	@Autowired
	PharmaRepDAO pharmaRepDAO;

	@Autowired
	PharmaRepNotificationDAO pharmaRepNotificationDAO;
	@Autowired
	SMSService smsService;
	@Autowired
	DeviceTokenDAO deviceTokenDAO;
//	@Autowired
	CompanyDAO companyDAO;


	private static final Log log = LogFactory.getLog(NotificationPublisherManual.class);


	//@Scheduled(cron="0 0/1 * * * ?")
	@Deprecated
	public void publishNotifications(List<NotificationEntity> notificationEntities,List<DoctorEntity> doctorEntities){
		try
		{
			//Find all unpublished notifications
			boolean flag = false;
			ArrayList<DeviceTokenEntity> devicesList = new ArrayList<DeviceTokenEntity>();
			for(NotificationEntity notificationEntity : notificationEntities)
			{
				for (DoctorEntity doctorEntity : doctorEntities)
				{
					if(!"Active".equalsIgnoreCase(doctorEntity.getStatus()))
							continue;
					boolean isNotificationSentToDoctor = false;
					DoctorNotificationEntity doctorNotificationEntity = doctorNotificationDAO.findByDoctorNotification(doctorEntity.getDoctorId(), notificationEntity.getNotificationId());
					if( doctorNotificationEntity != null && doctorNotificationEntity.getUserNotificationId() != null)
					{

						System.out.println("Publishing DoctorNotification Entity block for Doctor: " + doctorEntity.getDoctorId() + " Notfiication : " + notificationEntity.getNotificationId() );
						doctorNotificationEntity.setViewStatus("pending");
						doctorNotificationDAO.merge(doctorNotificationEntity);
						flag = true;
						isNotificationSentToDoctor = true;
					}
					else
					{
						doctorNotificationEntity = new DoctorNotificationEntity();
						doctorNotificationEntity.setCompanyId(notificationEntity.getCompanyId());
						doctorNotificationEntity.setCreatedOn(new Date());
						doctorNotificationEntity.setDoctorId(doctorEntity.getDoctorId());
						doctorNotificationEntity.setNotificationEntity(notificationEntity);
						if(doctorEntity.getTherapeuticId() != null)
						{
							doctorNotificationEntity.setTherapeuticId(Integer.parseInt(doctorEntity.getTherapeuticId()));
						}
						doctorNotificationEntity.setViewStatus("Pending");
						doctorNotificationDAO.persist(doctorNotificationEntity);
						flag = true;
						isNotificationSentToDoctor = true;
					}
					if(isNotificationSentToDoctor)
					{
						UserEntity user = doctorEntity.getUser();
						List<DeviceTokenEntity> devices=deviceTokenDAO.findByDoctorId(doctorEntity.getDoctorId());
						if(!Util.isEmpty(devices))
							devicesList.addAll(devices);
						if(user != null)
						{

//							if(user.getRegToken()==null || "".equalsIgnoreCase(user.getRegToken()))
//							{
//							}else{
//
//							}

							/*SMS sms = new SMS();
							sms.setPhoneNumber(user.getMobileNo());
							sms.setTemplate(SMSService.NOTIFICATION);
							Map<String, String> valueMap = new HashMap<String, String>();
							valueMap.put("NAME", user.getFirstName() + " " + user.getLastName());
							valueMap.put("NOTIFICATION", notificationEntity.getNotificationName());
							smsService.sendSMS(sms);*/
						}
					}

				}
				if(notificationEntity.getCompanyId() != null)
				{
					List<PharmaRepEntity> pharmaReps = pharmaRepDAO.findByCompanyId(notificationEntity.getCompanyId());

					for(PharmaRepEntity pharmaRep : pharmaReps)
					{
						boolean isNotificationSentToRep = false;
						PharmaRepNotificationEntity repNotificationEntity = pharmaRepNotificationDAO.findByPharmaRepNotification(pharmaRep.getRepId(), notificationEntity.getNotificationId());
						if(repNotificationEntity != null && repNotificationEntity.getUserNotificationId() != null)
						{

							repNotificationEntity.setViewStatus("Pending");
							pharmaRepNotificationDAO.merge(repNotificationEntity);
							flag = true;
							isNotificationSentToRep = true;
						}
						else
						{
							repNotificationEntity = new PharmaRepNotificationEntity();
							repNotificationEntity.setCompanyId(notificationEntity.getCompanyId());
							repNotificationEntity.setCreatedOn(new Date());
							repNotificationEntity.setPharmaRepId(pharmaRep.getRepId());
							repNotificationEntity.setTherapeuticId(notificationEntity.getTherapeuticId());
							repNotificationEntity.setViewStatus("Pending");
							repNotificationEntity.setNotificationEntity(notificationEntity);
							pharmaRepNotificationDAO.persist(repNotificationEntity);
							flag = true;
							isNotificationSentToRep = true;
						}
						if(isNotificationSentToRep)
						{
							UserEntity user = pharmaRep.getUser();
							if(user != null)
							{
								List<DeviceTokenEntity> devices=deviceTokenDAO.findByRepId(pharmaRep.getRepId());

								if(!Util.isEmpty(devices))
									devicesList.addAll(devices);
//								if(user.getRegToken()==null || "".equalsIgnoreCase(user.getRegToken()))
//								{
//								}else{
//									devicesList.add(user.getRegToken());
//								}
								/*SMS sms = new SMS();
								sms.setPhoneNumber(user.getMobileNo());
								sms.setTemplate(SMSService.NOTIFICATION);
								Map<String, String> valueMap = new HashMap<String, String>();
								valueMap.put("NAME", user.getFirstName() + " " + user.getLastName());
								valueMap.put("NOTIFICATION", notificationEntity.getNotificationName());
								smsService.sendSMS(sms);*/
							}
						}

					}

				}

				if(flag)
				{
					notificationEntity.setStatus("Published");
					notificationDAO.merge(notificationEntity);
				}
				CompanyEntity companyEntity=companyDAO.findByCompanyId(notificationEntity.getCompanyId());
				String message="";
				if(!Util.isEmpty(companyEntity))
				 message+= companyEntity.getCompanyName();
				message+=" have a new product. Please click to know more.";
			if(devicesList!=null && devicesList.size()>0){
				push(message, devicesList);
				//Notify.sendNotification("New Notification", devicesList);
			}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	@Deprecated
	public void push(String message, ArrayList<DeviceTokenEntity> devicesList) {
		if(!Util.isEmpty(devicesList))
		for(DeviceTokenEntity d:devicesList){
				System.out.println("Sending push notification.."+d.getDocId()+"-->"+d.getDeviceToken());
					if(d.getPlatform().equalsIgnoreCase("IOS")){
						IosPushNotification.push(d.getDeviceToken(), message);
					}else{
						AndroidPushNotification.push(d.getDeviceToken(), message);
					}
				}
	}

}
