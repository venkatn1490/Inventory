package com.medrep.app.engine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DeviceTokenDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorNotificationDAO;
import com.medrep.app.dao.NotificationDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.PharmaRepNotificationDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorNotificationEntity;
import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.PharmaRepNotificationEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.Mail;
import com.medrep.app.model.SMS;
import com.medrep.app.service.EmailService;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.SMSService;
import com.medrep.app.util.AndroidPushNotification;
import com.medrep.app.util.IosPushNotification;
import com.medrep.app.util.Notify;
import com.medrep.app.util.Util;
import com.mysql.jdbc.NotUpdatable;

@Transactional
@Component
@EnableScheduling
public class NotificationPublisher {



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
//	SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	NotificationService notificationService;
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	CompanyDAO companyDAO;


	private static final Log log = LogFactory.getLog(NotificationPublisher.class);


	@Scheduled(cron="0 0 0/1 * * ?")
	public void publishNotifications(){
		try
		{
			//Find all unpublished notifications
			List<NotificationEntity> notificationEntities = notificationDAO.findByStatus("New");

			List<DoctorEntity> doctorEntities = doctorDAO.findByStatus("Active");

			boolean flag = false;
			ArrayList<String> devicesList = new ArrayList<String>();
			for(NotificationEntity notificationEntity : notificationEntities)
			{
				for (DoctorEntity doctorEntity : doctorEntities)
				{
					boolean isNotificationSentToDoctor = false;
					DoctorNotificationEntity doctorNotificationEntity = doctorNotificationDAO.findByDoctorNotification(doctorEntity.getDoctorId(), notificationEntity.getNotificationId());
					if( doctorNotificationEntity != null && doctorNotificationEntity.getUserNotificationId() != null)
					{

						System.out.println("Publishing DoctorNotification Entity block for Doctor: " + doctorEntity.getDoctorId() + " Notfiication : " + notificationEntity.getNotificationId() );
						doctorNotificationEntity.setViewStatus("Pending");
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
						if(user != null)
						{
							if(user.getRegToken()==null || "".equalsIgnoreCase(user.getRegToken()))
							{
							}else{
								devicesList.add(user.getRegToken());
							}

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
								if(user.getRegToken()==null || "".equalsIgnoreCase(user.getRegToken()))
								{
								}else{
									devicesList.add(user.getRegToken());
								}
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

			}
			if(devicesList!=null && devicesList.size()>0){
				Notify.sendNotification("New Notification", devicesList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	List<DoctorNotificationEntity> remindLaterNotifications;

	public List<DoctorNotificationEntity> getRemindLaterNotifications() {
		return remindLaterNotifications;
	}

	public void setRemindLaterNotifications(List<DoctorNotificationEntity> remindLaterNotifications) {
		this.remindLaterNotifications = remindLaterNotifications;
	}

	Thread reminder;
	final List<DoctorNotificationEntity> doctorNotifications = new ArrayList<DoctorNotificationEntity>();
	final float GRACE_PERIOD = 60;

	public List<DoctorNotificationEntity> getDoctorNotifications() {
		return doctorNotifications;
	}
	/**
	 * Runs on every day
	 *
	 * @throws Exception
	 */
//	@Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
	@Deprecated
	public synchronized void appointmentReminder() throws Exception {
		// get the reminders sorted by time for today
		log.info("[STARTING] Reminder Scheduler initiating..");

		if (reminder != null && reminder.isAlive()) {
			System.out.println("Thread is interrupted");
			log.info("[INTERRUPTED] interrupting thread");
			reminder.interrupt();
		}

		reminder = new Thread() {

			@Override
			protected void finalize() throws Throwable {
				super.finalize();
				log.info("[DESTROYED] Reminder Thread is destroyed");
			}

			@Override
			public void run() {

				try {
					int count = 0;
					boolean isNotified = true;

					while (!interrupted()) {
						synchronized (doctorNotifications) {

							// Hit the database and get the latest records
							if (isNotified) {
								log.info("[NOTIFIED] fetching the reminders");
								List<DoctorNotificationEntity> tmpDoctorNotifications = doctorNotificationDAO.findRemindersOn(new Date());

								entityManager.detach(tmpDoctorNotifications);
								if (doctorNotifications != null && !doctorNotifications.isEmpty())
									doctorNotifications.clear();
								if (tmpDoctorNotifications != null && !tmpDoctorNotifications.isEmpty()) {
									doctorNotifications.addAll(tmpDoctorNotifications);
									count = 0;
								}
								isNotified = false;
							}

							//No reminders..wait until anything found
							if (doctorNotifications.isEmpty()) {
								log.info("[WAITING] - No Reminders available to send");
								doctorNotifications.wait();
								isNotified = true;
								continue;
							}

							log.info("[FOUND] Found  Reminders to send today:" + doctorNotifications);

							// get the reminder
							DoctorNotificationEntity doctorNotificationEntity = doctorNotifications.get(count);

							long startTimeInMillis = doctorNotifications.get(count).getReminderTime().getTime();
							long currentTimeInMillis = new Date().getTime();

							long elapsedTimeInSeconds = (startTimeInMillis - currentTimeInMillis) / 1000;

							//if elapsedTime is zero or in 2 minutes,send a push message
							if (elapsedTimeInSeconds == 0 || (Math.abs(elapsedTimeInSeconds) < GRACE_PERIOD)) {
								DoctorEntity d = doctorDAO
										.findByDoctorIdWithUserEntity(doctorNotificationEntity.getDoctorId());
								String doctorName = "";
								if (d != null) {
									doctorName = d.getUser().getFirstName() + " " + d.getUser().getLastName();
								}
								String msg = "";

								if ("1h".equals(doctorNotificationEntity.getRemindMe()))
									msg = "1 hour";
								else if ("1d".equals(doctorNotificationEntity.getRemindMe()))
									msg = "1 day";
								else if ("1w".equals(doctorNotificationEntity.getRemindMe()))
									msg = "1 week";
								else if ("1m".equals(doctorNotificationEntity.getRemindMe()))
									msg = "1 month";
								count++;
								String message="Reminder: ";
								CompanyEntity company=companyDAO.findByCompanyId(doctorNotificationEntity.getCompanyId());
									if(company!=null)
										message+=company.getCompanyName();
									message+="have a new product "+doctorNotificationEntity.getNotificationEntity().getNotificationName()+".";

								notificationService.push(message,doctorNotificationEntity.getDoctorId());
								log.info("[SENT] Reminder sent to " + doctorName+"("+message+")");
							} else if (elapsedTimeInSeconds > 0) {
								//elapsedTime is greater than zero sleep for the specified minutes.
								log.info("[WAIT/SLEEP] Need to wait for  (" + elapsedTimeInSeconds + "sec or "+ elapsedTimeInSeconds / 60 + "minutes)");
								doctorNotifications.wait(elapsedTimeInSeconds * 1000);

								continue;
							}

							boolean hasMoreReminders = count < doctorNotifications.size() && count + 1 < doctorNotifications.size();
							if (hasMoreReminders) {
								long nextAppointmentTime = doctorNotifications.get(count + 1).getReminderTime().getTime();
								long sleepTime = nextAppointmentTime - currentTimeInMillis;
								if (sleepTime > 0) {
									log.info("[WAIT/SLEEP] Need to wait for Next Appoinment(" + sleepTime / 1000 + "sec)");
									doctorNotifications.wait(sleepTime);
								}
							} else {
								log.info("[WAITING] No more reminders");
								doctorNotifications.wait();
								isNotified = true;

							}
						}
					}
				} catch (Exception e) {
					log.error("[HALTED] Reminder Thread got exception " + e);
					e.printStackTrace();
				}
			}

		};

		reminder.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread paramThread, Throwable throwable) {
				log.error("[HALTED] UnCaughtException caught",throwable);
			}
		});
		reminder.start();
		log.info("[STARTED] Reminder Scheduler started");
	}

}