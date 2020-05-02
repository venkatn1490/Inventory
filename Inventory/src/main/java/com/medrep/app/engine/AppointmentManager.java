/**
 *
 */
package com.medrep.app.engine;

import java.util.ArrayList;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.DoctorAppointmentDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.NotificationDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.RepAppointmentDAO;
import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.RepAppointmentEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.SMS;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.SMSService;
import com.medrep.app.util.Util;

/**
 * @author Umar Ashraf
 *
 */

@Transactional
@Component
@EnableScheduling
public class AppointmentManager {

	@Autowired
	private DoctorAppointmentDAO doctorAppointmentDAO;

	@Autowired
	private RepAppointmentDAO repAppointmentDAO;

	@Autowired
	private NotificationDAO notificationDAO;

	@Autowired
	private PharmaRepDAO repDAO;
	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	private SMSService smsService;
	@Autowired
	NotificationService notificationService;
	@PersistenceContext
	EntityManager entityManager;

	private static final Log log = LogFactory.getLog(AppointmentManager.class);

	@Scheduled(cron = "0 0/1 * * * ?")
	public void scheduleAppointment() {
		log.debug("Starting  user validation :" + new Date());
		List<DoctorAppointmentEntity> doctorAppointments = doctorAppointmentDAO.findByStatus("Pending");
		for (DoctorAppointmentEntity doctorAppointment : doctorAppointments) {
			Integer notificationId = doctorAppointment.getNotificationId();
			boolean updatedFlag = false;
			if (notificationId != null) {
				NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class,
						notificationId);
				if (notificationEntity != null && notificationEntity.getNotificationId() != null) {
					Integer companyId = notificationEntity.getCompanyId();
					if (companyId != null) {
						List<PharmaRepEntity> pharmaRepEnties = repDAO.findByCoveredAreaInCompany(doctorAppointment.getLocation(),companyId);
						for (PharmaRepEntity repEntity : pharmaRepEnties) {
							if (repEntity.getManagerEmail() != null) {
								boolean isAppointmentPending = false;
								RepAppointmentEntity repAppointmentEntity = repAppointmentDAO.findByRepAppointmentId(
										repEntity.getRepId(), doctorAppointment.getAppointmentId());
								if (repAppointmentEntity != null) {
									repAppointmentEntity.setAppointmentId(doctorAppointment.getAppointmentId());
									repAppointmentEntity.setCreatedOn(new Date());
									repAppointmentEntity.setDoctorId(doctorAppointment.getDoctorId());
									repAppointmentEntity.setRepId(repEntity.getRepId());
									repAppointmentEntity.setStatus("Pending");
									repAppointmentDAO.merge(repAppointmentEntity);
									updatedFlag = true;
									isAppointmentPending = true;
								} else {
									repAppointmentEntity = new RepAppointmentEntity();
									repAppointmentEntity.setAppointmentId(doctorAppointment.getAppointmentId());
									repAppointmentEntity.setCreatedOn(new Date());
									repAppointmentEntity.setDoctorId(doctorAppointment.getDoctorId());
									repAppointmentEntity.setRepId(repEntity.getRepId());
									repAppointmentEntity.setStatus("Pending");
									repAppointmentDAO.persist(repAppointmentEntity);
									updatedFlag = true;
									isAppointmentPending = true;
								}
								if (isAppointmentPending) {
									UserEntity user = repEntity.getUser();
									if (user != null) {
										SMS sms = new SMS();
										sms.setPhoneNumber(user.getMobileNo());
										sms.setTemplate(SMSService.NEW_APPOINTMENT);
										Map<String, String> valueMap = new HashMap<String, String>();
										valueMap.put("NAME", Util.getTitle(user.getTitle()) + user.getFirstName() + " "
												+ user.getLastName());
										valueMap.put("NOTIFICATION", notificationEntity.getNotificationName());
										sms.setValueMap(valueMap);
										smsService.sendSMS(sms);
									}
								}
							}

						}
					}
				}
			}
			if (updatedFlag) {
				doctorAppointment.setStatus("Published");
				doctorAppointmentDAO.merge(doctorAppointment);
			}
		}

	}

	Thread reminder;
	final List<DoctorAppointmentEntity> doctorAppointments = new ArrayList<DoctorAppointmentEntity>();
	final float GRACE_PERIOD = 60;

	public List<DoctorAppointmentEntity> getDoctorAppointments() {
		return doctorAppointments;
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
						synchronized (doctorAppointments) {

							// Hit the database and get the latest records
							if (isNotified) {
								log.info("[NOTIFIED] fetching the reminders");
								List<DoctorAppointmentEntity> tmpDoctorAppointments = doctorAppointmentDAO.findAppointmentsOn(new Date());
								entityManager.detach(tmpDoctorAppointments);
								if (doctorAppointments != null && !doctorAppointments.isEmpty())
									doctorAppointments.clear();
								if (tmpDoctorAppointments != null && !tmpDoctorAppointments.isEmpty()) {
									doctorAppointments.addAll(tmpDoctorAppointments);
									count = 0;
								}
								isNotified = false;
							}

							//No reminders..wait until anything found
							if (doctorAppointments.isEmpty()) {
								log.info("[WAITING] - No Reminders available to send");
								doctorAppointments.wait();
								isNotified = true;
								continue;
							}

							log.info("[FOUND] Found  Reminders to send today:" + doctorAppointments);

							// get the reminder
							DoctorAppointmentEntity doctorAppointmentEntity = doctorAppointments.get(count);

							long startTimeInMillis = doctorAppointments.get(count).getReminderTime().getTime();
							long currentTimeInMillis = new Date().getTime();

							long elapsedTimeInSeconds = (startTimeInMillis - currentTimeInMillis) / 1000;

							//if elapsedTime is zero or in 2 minutes,send a push message
							if (elapsedTimeInSeconds == 0 || (Math.abs(elapsedTimeInSeconds) < GRACE_PERIOD)) {
								DoctorEntity d = doctorDAO
										.findByDoctorIdWithUserEntity(doctorAppointmentEntity.getDoctorId());
								String doctorName = "";
								if (d != null) {
									doctorName = d.getUser().getFirstName() + " " + d.getUser().getLastName();
								}
								String msg = "";

								if ("1h".equals(doctorAppointmentEntity.getRemindMe()))
									msg = "1 hour";
								else if ("1d".equals(doctorAppointmentEntity.getRemindMe()))
									msg = "1 day";
								else if ("1w".equals(doctorAppointmentEntity.getRemindMe()))
									msg = "1 week";
								else if ("1m".equals(doctorAppointmentEntity.getRemindMe()))
									msg = "1 month";
								count++;
								notificationService.push("Reminder: Your Appointment with Dr." + doctorName + " starts in " + msg + ".",	doctorAppointmentEntity.getDoctorId());
								log.info("[SENT] Reminder sent to " + doctorName);
							} else if (elapsedTimeInSeconds > 0) {
								//elapsedTime is greater than zero sleep for the specified minutes.
								log.info("[SLEEP/WAIT] Need to wait for  (" + elapsedTimeInSeconds + "sec or "+ elapsedTimeInSeconds / 60 + "minutes)");
								doctorAppointments.wait(elapsedTimeInSeconds * 1000);
								continue;
							}

							boolean hasMoreReminders = count < doctorAppointments.size() && count + 1 < doctorAppointments.size();
							if (hasMoreReminders) {
								long nextAppointmentTime = doctorAppointments.get(count + 1).getReminderTime().getTime();
								long sleepTime = nextAppointmentTime - currentTimeInMillis;
								if (sleepTime > 0) {
									log.info("[SLEEP/WAIT] Need to wait for Next Appoinment(" + sleepTime / 1000 + "sec)");
									doctorAppointments.wait(sleepTime);
								}
							} else {
								log.info("[WAITING] No more reminders");
								doctorAppointments.wait();
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