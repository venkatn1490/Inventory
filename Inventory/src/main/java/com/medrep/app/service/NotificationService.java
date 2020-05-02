package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DeviceTokenDAO;
import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.DoctorAppointmentDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorNotificationDAO;
import com.medrep.app.dao.NotificationDAO;
import com.medrep.app.dao.NotificationDetailsDAO;
import com.medrep.app.dao.PendingCountDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.PharmaRepNotificationDAO;
import com.medrep.app.dao.PharmaTherapeuticsDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.engine.NotificationPublisherManual;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.entity.DisplayPictureEntity;
import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorNotificationEntity;
import com.medrep.app.entity.NotificationDetailsEntity;
import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.PendingCountsEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.PharmaRepNotificationEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorNotification;
import com.medrep.app.model.DoctorNotificationStat;
import com.medrep.app.model.Notification;
import com.medrep.app.model.NotificationDetails;
import com.medrep.app.model.NotificationStat;
import com.medrep.app.model.PharmaRepNotification;
import com.medrep.app.util.AndroidPushNotification;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.IosPushNotification;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.Util;

@Service("notificationService")
@Transactional
public class NotificationService {

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	NotificationDAO notificationDAO;
	@Autowired
	DisplayPictureDAO displayPictureDao;

	@Autowired
	DoctorNotificationDAO doctorNotificationDAO;

	@Autowired
	NotificationDetailsDAO notificationDetailsDAO;

	@Autowired
	CompanyDAO companyDAO;

	@Autowired
	PharmaTherapeuticsDAO pharmaTherapeuticsDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	@Autowired
	PharmaRepDAO pharmaRepDAO;

	@Autowired
	PharmaRepNotificationDAO pharmaRepNotificationDAO;

	@Autowired
	NotificationPublisherManual notificationPublisherManual;

	@Autowired
	DeviceTokenDAO deviceTokenDAO;

	@Autowired
	PendingCountDAO pendingCountDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	DoctorAppointmentDAO doctorAppointmentDAO;

	private static final Log log = LogFactory.getLog(NotificationService.class);

	public List<Notification> getDoctorNotifications(String loginId, Date findByDate) {

		List<Notification> notificationList = new ArrayList<Notification>();
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);

		if (doctorEntity != null) {
//			if(doctorEntity.getUser()!=null && doctorEntity.getUser().getStatus()!=null && !doctorEntity.getStatus().equalsIgnoreCase("Active") && doctorEntity.getUser().getStatus().equalsIgnoreCase("ACTIVE")){
			if(!"Active".equalsIgnoreCase(doctorEntity.getStatus())){
			return notificationList;
			}
			int doctorId = doctorEntity.getDoctorId();
			List<DoctorNotificationEntity> doctorNotificationEntities = doctorNotificationDAO.findByDoctorDate(doctorId,
					findByDate);

			// Search for notifications

			for (DoctorNotificationEntity doctorNotificationEntity : doctorNotificationEntities) {

				NotificationEntity notificationEntity = doctorNotificationEntity.getNotificationEntity();
				if (notificationEntity != null) {
					// : TODO Populate elemenets
					Notification notification = new Notification();
					if (notificationEntity.getCompanyId() != null) {
						notification.setCompanyId(notificationEntity.getCompanyId());
						Integer companyId = notificationEntity.getCompanyId();
						CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, companyId);
						if (companyEntity != null) {
							notification.setCompanyName(companyEntity.getCompanyName());
							if(companyEntity.getDpId()!=null && companyEntity.getDpId()!=0){
							DisplayPictureEntity dp=displayPictureDao.findById(DisplayPictureEntity.class, companyEntity.getDpId());
							if(dp!=null)
								notification.setdPicture(dp.getImageUrl());
							}
						}
					}

					if (notificationEntity.getTherapeuticId() != null) {
						notification.setTherapeuticId(notificationEntity.getTherapeuticId());
						Integer tid = notificationEntity.getTherapeuticId();
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
								.findById(TherapeuticAreaEntity.class, tid);
						if (therapeuticAreaEntity != null) {
							notification.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}

					}

					if(doctorNotificationEntity.getFavourite()!=null)
					notification.setFavourite(doctorNotificationEntity.getFavourite());
					notification.setRemindMe(doctorNotificationEntity.getRemindMe());
					notification.setViewStatus(doctorNotificationEntity.getViewStatus());

					notification.setCreatedBy(notificationEntity.getCreatedBy());
					notification.setCreatedOn(DateConvertor.convertDateToString(notificationEntity.getCreatedOn(),
							DateConvertor.YYYYMMDDHHMISS));
					notification.setExternalRef(notificationEntity.getExternalRef());
					notification.setNotificationDesc(notificationEntity.getNotificationDesc());
					notification.setNotificationId(notificationEntity.getNotificationId());
					notification.setStatus(notificationEntity.getStatus());
					notification.setNotificationName(notificationEntity.getNotificationName());
					notification.setTypeId(notificationEntity.getTypeId());
					notification.setUpdatedBy(notificationEntity.getUpdatedBy());
					notification.setUpdatedOn(DateConvertor.convertDateToString(notificationEntity.getUpdatedOn(),
							DateConvertor.YYYYMMDDHHMISS));
					notification.setValidUpto(DateConvertor.convertDateToString(notificationEntity.getValidUpto(),
							DateConvertor.YYYYMMDDHHMISS));

					List<NotificationDetails> notificationDetails = new ArrayList<NotificationDetails>();

					for (NotificationDetailsEntity notifcationDetailsEntity : notificationEntity
							.getNotificationDetailEntities()) {

						if (notifcationDetailsEntity != null) {

							NotificationDetails notificationDetail = new NotificationDetails();

						    notificationDetail.setContentLocation(notifcationDetailsEntity.getContentLocation());
							notificationDetail.setContentName(notifcationDetailsEntity.getContentName());
							notificationDetail.setContentSeq(notifcationDetailsEntity.getContentSeq());
							notificationDetail.setContentType(notifcationDetailsEntity.getContentType());
							notificationDetail.setDetailId(notifcationDetailsEntity.getDetailId());
							notificationDetail.setNotificationId(notificationEntity.getNotificationId());
							notificationDetail.setDetailTitle(notifcationDetailsEntity.getDetailTitle());
							notificationDetail.setDetailDesc(notifcationDetailsEntity.getDetailDesc());
							notificationDetails.add(notificationDetail);

						}
					}

					notification.setNotificationDetails(notificationDetails);

					notificationList.add(notification);

				}

			}

		}

		return notificationList;

	}

	public NotificationDetails getNotificationDetails(Integer notificationDetailId,String loginId) {
		NotificationDetailsEntity notificationDetailsEntity = notificationDetailsDAO.findById(NotificationDetailsEntity.class, notificationDetailId);
		NotificationDetails notificationDetails = null;
		if (notificationDetailsEntity != null && notificationDetailsEntity.getDetailId() != null) {
			NotificationEntity notificationEntity=notificationDetailsEntity.getNotificationEntity();


			if(!Util.isEmpty(loginId)){
				DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
				if(!Util.isEmpty(doctorEntity) && !Util.isZeroOrNull(doctorEntity.getDoctorId())){
					DoctorNotificationEntity dNotificationEntity=doctorNotificationDAO.findByDoctorNotification(doctorEntity.getDoctorId(), notificationDetailsEntity.getNotificationEntity().getNotificationId());

					if(!Util.isEmpty(dNotificationEntity) && !Util.isZeroOrNull(dNotificationEntity.getUserNotificationId())){
						dNotificationEntity.setDoctorId(doctorEntity.getDoctorId());
						dNotificationEntity.setNotificationEntity(notificationEntity);
					}

					if(Util.isZeroOrNull(dNotificationEntity.getViewCount()))
						dNotificationEntity.setViewCount(0);
					dNotificationEntity.setViewCount(dNotificationEntity.getViewCount()+1);

					doctorNotificationDAO.merge(dNotificationEntity);
				}

			}


			notificationDetails = new NotificationDetails();
			notificationDetails.setNotificationId(notificationDetailsEntity.getDetailId());
			notificationDetails.setContentLocation(notificationDetailsEntity.getContentLocation());
			notificationDetails.setContentName(notificationDetailsEntity.getContentName());
			notificationDetails.setContentType(notificationDetailsEntity.getContentType());
			notificationDetails.setContentSeq(notificationDetailsEntity.getContentSeq());
		}

		return notificationDetails;

	}

	public NotificationStat getNotificationStats(Integer notificationId) {

		NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, notificationId);
		NotificationStat notificationStat = new NotificationStat();
		if (notificationEntity != null && notificationEntity.getNotificationId() != null) {
			notificationStat.setNotificationId(notificationEntity.getNotificationId());
			notificationStat.setNotificationName(notificationEntity.getNotificationName());

			List<Doctor> viewedDoctors=new ArrayList<Doctor>();
			List<Doctor> appointmentsMade=new ArrayList<Doctor>();
			List<Doctor> pendingDoctors=new ArrayList<Doctor>();
			
			int  pendingCount=0;
			int viewedCount=0;
			int favoritesCount=0;
			int remaindersCount=0;
			
			List<DoctorAppointmentEntity> appointments=doctorAppointmentDAO.findAppointmentsByNotificationId(notificationId);

			//appointments made
			Set<Integer> _appointments=new HashSet<Integer>();
			if(appointments!=null)
			for(DoctorAppointmentEntity dAppointmentEntity:appointments){
				if(_appointments.add(dAppointmentEntity.getDoctorId()))
				appointmentsMade.add(getDoctorDetailsByDoctorId(dAppointmentEntity.getDoctorId()));
			}
			Set<Integer> _pendingDoctors=new HashSet<Integer>();
			Set<Integer> _viewedDoctors=new HashSet<Integer>();

			//notifications
			List<DoctorNotificationEntity> doctorNotificationEntities=doctorNotificationDAO.findByNotificationId(notificationId);
			for(DoctorNotificationEntity dne:doctorNotificationEntities){
				Doctor doctor=getDoctorDetailsByDoctorId(dne.getDoctorId());
				if(dne.getFavourite()!=null)
				{
				doctor.setFavouriteNotification(Boolean.valueOf(dne.getFavourite()));
				favoritesCount++;
				}
				if(dne.getRemindMe()!=null)
				{
				doctor.setRemindNotification(dne.getRemindMe());
				remaindersCount++;
				}
				if("PENDING".equalsIgnoreCase(dne.getViewStatus())){
					pendingCount++;
					if(_pendingDoctors.add(dne.getDoctorId()))
					pendingDoctors.add(doctor);
				}else{
					viewedCount++;
					if(!_viewedDoctors.add(dne.getDoctorId()))
					viewedDoctors.add(doctor);
				}
			}

			notificationStat.setTotalConvertedToAppointment(appointments!=null?appointments.size():0);
			notificationStat.setTotalPendingNotifcation(pendingCount);
			notificationStat.setTotalViewedNotifcation(viewedCount);
			notificationStat.setTotalSentNotification(pendingCount+viewedCount);
			
			notificationStat.setPending(pendingDoctors);
			notificationStat.setViewed(viewedDoctors);
			notificationStat.setNotificationsSent(pendingDoctors);
			notificationStat.setAppointments(appointmentsMade);
			notificationStat.setTotalremainders(remaindersCount);
			notificationStat.setTotalfavorites(favoritesCount);


//			Integer totalSentNotification = 0;
//			Integer totalPendingNotifcation = 0;
//			Integer totalViewedNotifcation = 0;
//			Integer totalConvertedToAppointment = 0;
//			for (Object[] data : notificationDAO.findNotificationStats(notificationId)) {
//
//				Integer count = 0;
//				if (data[0] != null) {
//					count = ((BigInteger) data[0]).intValue();
//				}
//				String status = (String) data[1];
//				String type = (String) data[2];
//				if ("APPOINTMENT".equalsIgnoreCase(type)) {
//					totalConvertedToAppointment = count;
//				}
//				if ("NOTIFICATION".equalsIgnoreCase(type)) {
//					totalSentNotification = totalSentNotification + count;
//					if ("PENDING".equalsIgnoreCase(status)) {
//						totalPendingNotifcation = count;
//					}
//
//				}
//
//			}
//			totalViewedNotifcation = totalSentNotification - totalPendingNotifcation;
//			notificationStat.setTotalConvertedToAppointment(totalConvertedToAppointment);
//			notificationStat.setTotalPendingNotifcation(totalPendingNotifcation);
//			notificationStat.setTotalSentNotification(totalSentNotification);
//			notificationStat.setTotalViewedNotifcation(totalViewedNotifcation);
		}

		return notificationStat;

	}

	private Doctor getDoctorDetailsByDoctorId(Integer doctorId) {
		DoctorEntity d=doctorDAO.findByDoctorId(doctorId);
		Doctor doctor=new Doctor();
		if(d!=null){
			doctor=new Doctor();
			doctor.setDoctorId(d.getDoctorId());
			if(d.getUser()!=null){
				doctor.setFirstName("Dr."+d.getUser().getFirstName());
				doctor.setLastName(d.getUser().getLastName());
				if (d.getUser().getDisplayPicture() != null)
				doctor.setdPicture(d.getUser().getDisplayPicture().getImageUrl());
			}
		}
		return doctor;
	}

	public void updateDoctorNotification(DoctorNotification doctorNotification, String loginId) {

		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
			DoctorNotificationEntity doctorNotificationEntity = doctorNotificationDAO
					.findByDoctorNotification(doctorEntity.getDoctorId(), doctorNotification.getNotificationId());

			if (doctorNotificationEntity != null && doctorNotificationEntity.getUserNotificationId() != null) {

				if (doctorNotification.getFavourite() != null) {
					doctorNotificationEntity.setFavourite(doctorNotification.getFavourite());
				}

				if (doctorNotification.getRating() != null && doctorNotification.getRating() != "") {
					doctorNotificationEntity.setRating(doctorNotification.getRating());
				}
				if (doctorNotification.getRecomend() != null && doctorNotification.getRecomend() != "") {
					doctorNotificationEntity.setRecomend(doctorNotification.getRecomend());
				}
				if (doctorNotification.getPrescribe() != null && doctorNotification.getPrescribe() != "") {
					doctorNotificationEntity.setPrescribe(doctorNotification.getPrescribe());
				}

				if (doctorNotification.getViewedOn() != null && doctorNotification.getViewedOn().length() == 14) {
					doctorNotificationEntity.setViewedOn(DateConvertor
							.convertStringToDate(doctorNotification.getViewedOn(), DateConvertor.YYYYMMDDHHMISS));
				}

				if (doctorNotification.getViewStatus() != null) {
					doctorNotificationEntity.setViewStatus(doctorNotification.getViewStatus());
				}

				if(doctorNotification.getRemindMe()!=null && doctorNotification.getRemindMe().trim().length()!=0)
					doctorNotificationEntity.setRemindMe(doctorNotification.getRemindMe());

				if(!"".equals(doctorNotificationEntity.getRemindMe())){
					long t=new Date().getTime();
					long t1=t;
					if("1h".equals(doctorNotificationEntity.getRemindMe()))
						t1=t+60*60*1000;
					else if("1d".equals(doctorNotificationEntity.getRemindMe()))
						t1=t+24*60*60*1000;
					else if("1w".equals(doctorNotificationEntity.getRemindMe()))
						t1=t+7*24*60*60*1000;
					else if("1m".equals(doctorNotificationEntity.getRemindMe()))
						t1=t+30*24*60*60*1000;
					if(t1!=t)
					doctorNotificationEntity.setReminderTime(new Date(t1));
				}

			}
			//doctorNotificationDAO.merge(doctorNotificationEntity);
			updateNotificationCount(loginId, doctorNotificationEntity.getViewStatus());
		}


	}

	public boolean isFeedbackProvided(DoctorNotification doctorNotification, String loginId) {
		boolean flag = false;
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
			DoctorNotificationEntity doctorNotificationEntity = doctorNotificationDAO
					.findByDoctorNotification(doctorEntity.getDoctorId(), doctorNotification.getNotificationId());

			if (doctorNotificationEntity != null && doctorNotificationEntity.getUserNotificationId() != null) {

				if (doctorNotification.getRating() != null && doctorNotification.getRating() != "") {
					doctorNotificationEntity.setRating(doctorNotification.getRating());
					flag = true;
				}
				if (doctorNotification.getRecomend() != null && doctorNotification.getRecomend() != "") {
					doctorNotificationEntity.setRecomend(doctorNotification.getRecomend());
					flag = true;
				}
				if (doctorNotification.getPrescribe() != null && doctorNotification.getPrescribe() != "") {
					doctorNotificationEntity.setPrescribe(doctorNotification.getPrescribe());
					flag = true;
				}

			}

		}

		return flag;
	}

	public List<Notification> getAdminNotificationsList() throws MedrepException {
		List<Notification> valObjs = new ArrayList<Notification>();
		try {
			List<NotificationEntity> list = notificationDAO.getAdminNotificationsList();
			Map<String, String> cmap = getAllCompanys();

			for (NotificationEntity entity : list) {
				Notification notification = BeanUtils.instantiateClass(Notification.class);
				BeanUtils.copyProperties(entity, notification);
				Date d = entity.getCreatedOn();
				if (d != null)
					notification.setCreatedOn(d.toString());
				Integer cid = notification.getCompanyId();
				if (cid != null && !cid.equals("0"))
					notification.setCompanyName(cmap.get(String.valueOf(cid)));
				else
					notification.setCompanyName("");
				Integer tid = notification.getTherapeuticId();
				if (tid != null) {
					TherapeuticAreaEntity tentity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, tid);
					if (tentity != null) {
						notification.setTherapeuticName(tentity.getTherapeuticName());
					}
				}
				valObjs.add(notification);
			}
		} catch (Exception e) {
			throw new MedrepException("Error while getting Notification List " + e.getMessage());
		}
		return valObjs;
	}

	public void createNotification(Notification notification) throws MedrepException {
		try {
			List<NotificationDetailsEntity> detailEntityList = new ArrayList<NotificationDetailsEntity>();
			NotificationEntity entity = BeanUtils.instantiateClass(NotificationEntity.class);
			BeanUtils.copyProperties(notification, entity);
			entity.setPushNotification("PUSH");
			entity.setCreatedOn(new Date());
			entity.setUpdatedOn(new Date());
			List<NotificationDetails> list = notification.getNotificationDetails();
			notificationDAO.persist(entity);
			int seq = 1;
			for (NotificationDetails detailBean : list) {
				NotificationDetailsEntity detailEntity = BeanUtils.instantiateClass(NotificationDetailsEntity.class);
				BeanUtils.copyProperties(detailBean, detailEntity);
				detailEntity.setContentSeq(seq++);
				detailEntity.setNotificationEntity(entity);
				detailEntityList.add(detailEntity);

			}
			entity.setNotificationDetailEntities(detailEntityList);
			notificationDAO.merge(entity);
			seq = 0;
			detailEntityList = new ArrayList<NotificationDetailsEntity>();
			for (NotificationDetailsEntity dentity : entity.getNotificationDetailEntities()) {
				seq = dentity.getContentSeq();
				MultipartFile file = notification.getFileList().get(--seq);
				if (file != null)
					createFileUrl(file, dentity, entity.getNotificationId(), dentity.getDetailId());
				detailEntityList.add(dentity);
			}
			entity.setNotificationDetailEntities(detailEntityList);
			notificationDAO.merge(entity);
		} catch (Exception e) {
			throw new MedrepException("Error while creating Notification " + e.getMessage());
		}
	}

	public void updateAdminNotificaion(Notification notification) throws MedrepException {
		try {
			NotificationEntity entity = notificationDAO.findById(NotificationEntity.class,
					notification.getNotificationId());
			if (entity != null && entity.getNotificationId() != null) {
				entity.setNotificationName(notification.getNotificationName());
				entity.setNotificationDesc(notification.getNotificationDesc());
				entity.setCompanyId(notification.getCompanyId());
				entity.setTherapeuticId(notification.getTherapeuticId());
				entity.setStatus(notification.getStatus());
				entity.setUpdatedOn(new Date());
				entity.setPushNotification("PUSH");
				notificationDAO.merge(entity);
			}
		} catch (Exception e) {
			throw new MedrepException("Error while update Notification " + e.getMessage());
		}
	}

	public void deleteAdminNotification(String notificationId) throws MedrepException {
		try {
			NotificationEntity entity = notificationDAO.findById(NotificationEntity.class,
					Integer.parseInt(notificationId));
			notificationDAO.remove(entity);
		} catch (Exception e) {
			throw new MedrepException("Error while remove Notification " + e.getMessage());
		}
	}

	public void createFileUrl(MultipartFile mfile, NotificationDetailsEntity entity, int nid, int ndid) throws IOException {
		entity.setContentType(mfile.getOriginalFilename().split("\\.")[1].toUpperCase());
		entity.setContentName(mfile.getOriginalFilename().replace("\\s",""));
		if(mfile!=null){
//			String contentLocationUrl = MedRepProperty.getInstance().getProperties("medrep.home") + "static/notifications/"+nid+"/"+ndid+"/";
			String contentLocationUrl = MedRepProperty.getInstance().getProperties("static.resources.url") + "static/notifications/"+nid+"/"+ndid+"/";
			contentLocationUrl += FileUtil.copyBytesToFile(mfile.getBytes(),MedRepProperty.getInstance().getProperties("medrep.notification.basepath")+File.separator+nid+File.separator+ndid,mfile.getOriginalFilename());
			entity.setContentLocation(contentLocationUrl);
		}
	}

	public Notification getAdminNotificationById(String notificationId) throws MedrepException {
		Notification notification = BeanUtils.instantiateClass(Notification.class);
		try {
			Map<String, String> cmap = getAllCompanys();
			NotificationEntity entity = notificationDAO.findById(NotificationEntity.class,
					Integer.parseInt(notificationId));
			BeanUtils.copyProperties(entity, notification);
			Date d = entity.getCreatedOn();
			if (d != null)
				notification.setCreatedOn(d.toString());
			Integer cid = notification.getCompanyId();
			if (cid != null && !cid.equals("0"))
				notification.setCompanyName(cmap.get(String.valueOf(cid)));
			else
				notification.setCompanyName("");
			Integer tid = notification.getTherapeuticId();
			if (tid != null) {
				TherapeuticAreaEntity tentity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, tid);
				notification.setTherapeuticName(tentity.getTherapeuticName());
			}

			List<NotificationDetailsEntity> elist = entity.getNotificationDetailEntities();
			List<NotificationDetails> list = new ArrayList<NotificationDetails>();
			for (NotificationDetailsEntity dentity : elist) {
				NotificationDetails ndetails = BeanUtils.instantiateClass(NotificationDetails.class);
				BeanUtils.copyProperties(dentity, ndetails);
				list.add(ndetails);

			}
			notification.setNotificationDetails(list);

			// Getting Notification statitics
			NotificationStat stat = this.getNotificationStats(Integer.parseInt(notificationId));
			notification.setTotalSentNotification(stat.getTotalSentNotification());
			notification.setTotalViewedNotifcation(stat.getTotalViewedNotifcation());
			notification.setTotalPendingNotifcation(stat.getTotalPendingNotifcation());
			notification.setTotalConvertedToAppointment(stat.getTotalConvertedToAppointment());
			notification.setTotalnotificationRemainders(stat.getTotalremainders());
			notification.setTotalCntFavouriteNotification(stat.getTotalfavorites());
	

		} catch (Exception e) {
			throw new MedrepException("Error while getting Notification " + e.getMessage());
		}
		return notification;
	}

	public Map<String, String> getAllCompanys() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("0", "----------   Select One  --------");
		List<CompanyEntity> companyEntities = companyDAO.findAll();
		for (CompanyEntity companyEntity : companyEntities) {
			map.put(String.valueOf(companyEntity.getCompanyId()), companyEntity.getCompanyName());
		}
		return map;
	}

	public Map<String, String> getAllTherapeuticsByCompanyId(String id) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Set<TherapeuticAreaEntity> therapeuticAreaEntities = pharmaTherapeuticsDAO
				.findTherapeuticAreasByCompanyId(Integer.parseInt(id));
		for (TherapeuticAreaEntity entity : therapeuticAreaEntities) {
			map.put(String.valueOf(entity.getTherapeuticId()), entity.getTherapeuticName());
		}

		return map;
	}

	public List<Notification> getPharmaRepNotifications(String loginId, Date findByDate) {

		List<Notification> notificationList = new ArrayList<Notification>();

		PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);

		if(Util.isEmpty(pharmaRepEntity)||(!Util.isEmpty(pharmaRepEntity)&& !"Active".equalsIgnoreCase(pharmaRepEntity.getStatus()))){
			return notificationList;
		}

		if (pharmaRepEntity != null && pharmaRepEntity.getRepId() != null) {
			int pharmaRepId = pharmaRepEntity.getRepId();

			List<PharmaRepNotificationEntity> pharmaRepNotificationEntities = pharmaRepNotificationDAO
					.findByPharmaRepDate(pharmaRepId, findByDate);

			for (PharmaRepNotificationEntity pharmaRepNotificationEntity : pharmaRepNotificationEntities) {

				NotificationEntity notificationEntity = pharmaRepNotificationEntity.getNotificationEntity();
				if (notificationEntity != null) {
					Notification notification = new Notification();
					if (notificationEntity.getCompanyId() != null) {
						notification.setCompanyId(notificationEntity.getCompanyId());
						int compaId = notificationEntity.getCompanyId();
						CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, compaId);
						if (companyEntity != null) {
							notification.setCompanyName(companyEntity.getCompanyName());
						}
					}
					if (notificationEntity.getTherapeuticId() != null) {
						notification.setTherapeuticId(notificationEntity.getTherapeuticId());
						int tid = notificationEntity.getTherapeuticId();
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
								.findById(TherapeuticAreaEntity.class, tid);
						if (therapeuticAreaEntity != null) {
							notification.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}

					}

					if(pharmaRepNotificationEntity.getFavourite()!=null)
					notification.setFavourite(pharmaRepNotificationEntity.getFavourite());

					notification.setViewStatus(pharmaRepNotificationEntity.getViewStatus());
					notification.setCreatedBy(notificationEntity.getCreatedBy());
					notification.setCreatedOn(DateConvertor.convertDateToString(notificationEntity.getCreatedOn(),
							DateConvertor.YYYYMMDDHHMISS));
					notification.setExternalRef(notificationEntity.getExternalRef());
					notification.setNotificationDesc(notificationEntity.getNotificationDesc());
					notification.setNotificationId(notificationEntity.getNotificationId());
					notification.setStatus(notificationEntity.getStatus());
					notification.setTypeId(notificationEntity.getTypeId());
					notification.setUpdatedBy(notificationEntity.getUpdatedBy());
					notification.setUpdatedOn(DateConvertor.convertDateToString(notificationEntity.getUpdatedOn(),
							DateConvertor.YYYYMMDDHHMISS));
					notification.setValidUpto(DateConvertor.convertDateToString(notificationEntity.getValidUpto(),
							DateConvertor.YYYYMMDDHHMISS));
					notification.setNotificationDesc(notificationEntity.getNotificationDesc());
					notification.setNotificationName(notificationEntity.getNotificationName());
					List<NotificationDetails> notificationDetails = new ArrayList<NotificationDetails>();

					for (NotificationDetailsEntity notifcationDetailsEntity : notificationEntity
							.getNotificationDetailEntities()) {

						if (notifcationDetailsEntity != null) {

							NotificationDetails notificationDetail = new NotificationDetails();

							notificationDetail.setContentLocation(notifcationDetailsEntity.getContentLocation());
							notificationDetail.setContentName(notifcationDetailsEntity.getContentLocation());
							notificationDetail.setContentSeq(notifcationDetailsEntity.getContentSeq());
							notificationDetail.setContentType(notifcationDetailsEntity.getContentType());
							notificationDetail.setDetailId(notifcationDetailsEntity.getDetailId());
							notificationDetail.setNotificationId(notificationEntity.getNotificationId());
							notificationDetail.setDetailTitle(notifcationDetailsEntity.getDetailTitle());
							notificationDetail.setDetailDesc(notifcationDetailsEntity.getDetailDesc());
							notificationDetails.add(notificationDetail);

						}
					}

					notification.setNotificationDetails(notificationDetails);
					notificationList.add(notification);
				}
			}
		}

		return notificationList;
	}

	public Notification getPharmaRepNotification(String notificationId) {

		Notification notification = null;
		Integer _notificationId = Integer.parseInt(notificationId);
		NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, _notificationId);
		if (notificationEntity != null) {
			notification = new Notification();
			if (notificationEntity.getCompanyId() != null) {
				notification.setCompanyId(notificationEntity.getCompanyId());
				int compaId = notificationEntity.getCompanyId();
				CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, compaId);
				if (companyEntity != null) {
					notification.setCompanyName(companyEntity.getCompanyName());
				}
			}
			if (notificationEntity.getTherapeuticId() != null) {
				notification.setTherapeuticId(notificationEntity.getTherapeuticId());
				int tid = notificationEntity.getTherapeuticId();
				TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
						tid);
				if (therapeuticAreaEntity != null) {
					notification.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
				}

			}
			notification.setCreatedBy(notificationEntity.getCreatedBy());
			notification.setCreatedOn(
					DateConvertor.convertDateToString(notificationEntity.getCreatedOn(), DateConvertor.YYYYMMDDHHMISS));
			notification.setExternalRef(notificationEntity.getExternalRef());
			notification.setNotificationDesc(notificationEntity.getNotificationDesc());
			notification.setNotificationId(notificationEntity.getNotificationId());
			notification.setStatus(notificationEntity.getStatus());
			notification.setTypeId(notificationEntity.getTypeId());
			notification.setUpdatedBy(notificationEntity.getUpdatedBy());
			notification.setUpdatedOn(
					DateConvertor.convertDateToString(notificationEntity.getUpdatedOn(), DateConvertor.YYYYMMDDHHMISS));
			notification.setValidUpto(
					DateConvertor.convertDateToString(notificationEntity.getValidUpto(), DateConvertor.YYYYMMDDHHMISS));
			notification.setNotificationDesc(notificationEntity.getNotificationDesc());
			notification.setNotificationName(notificationEntity.getNotificationName());
			List<NotificationDetails> notificationDetails = new ArrayList<NotificationDetails>();
			for (NotificationDetailsEntity notifcationDetailsEntity : notificationEntity
					.getNotificationDetailEntities()) {

				if (notifcationDetailsEntity != null) {

					NotificationDetails notificationDetail = new NotificationDetails();

					notificationDetail.setContentLocation(notifcationDetailsEntity.getContentLocation());
					notificationDetail.setContentName(notifcationDetailsEntity.getContentLocation());
					notificationDetail.setContentSeq(notifcationDetailsEntity.getContentSeq());
					notificationDetail.setContentType(notifcationDetailsEntity.getContentType());
					notificationDetail.setDetailId(notifcationDetailsEntity.getDetailId());
					notificationDetail.setNotificationId(notificationEntity.getNotificationId());
					notificationDetail.setDetailTitle(notifcationDetailsEntity.getDetailTitle());
					notificationDetail.setDetailDesc(notifcationDetailsEntity.getDetailDesc());
					notificationDetails.add(notificationDetail);

				}
			}

			notification.setNotificationDetails(notificationDetails);

		}

		return notification;
	}

	public void updatePharmaRepNotification(PharmaRepNotification pharmaRepNotification, String loginId) {

		PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);
		if (pharmaRepEntity != null && pharmaRepEntity.getRepId() != null) {
			PharmaRepNotificationEntity pharmaRepNotificationEntity = pharmaRepNotificationDAO
					.findByPharmaRepNotification(pharmaRepEntity.getRepId(), pharmaRepNotification.getNotificationId());
			if (pharmaRepNotificationEntity != null) {

				if (pharmaRepNotification.getFavourite() != null) {
					pharmaRepNotificationEntity.setFavourite(pharmaRepNotification.getFavourite());
				}

				if (pharmaRepNotification.getFeedback() != null && pharmaRepNotification.getFeedback() != "") {
					pharmaRepNotificationEntity.setFeedback(pharmaRepNotification.getFeedback());
				}

				if (pharmaRepNotification.getViewedOn() != null && pharmaRepNotification.getViewedOn().length() == 14) {
					pharmaRepNotificationEntity.setViewedOn(DateConvertor
							.convertStringToDate(pharmaRepNotification.getViewedOn(), DateConvertor.YYYYMMDDHHMISS));
				}

				if (pharmaRepNotification.getViewStatus() != null) {
					pharmaRepNotificationEntity.setViewStatus(pharmaRepNotification.getViewStatus());
				}

				updateNotificationCount(loginId, pharmaRepNotificationEntity.getViewStatus());

			}
			pharmaRepNotificationDAO.merge(pharmaRepNotificationEntity);
		}
	}

	private void updateNotificationCount(String loginId, String status) {

		PendingCountsEntity pendingCountEntity=pendingCountDAO.findByLoginId(loginId);

		UserEntity userEntity=userDAO.findByEmailId(loginId);

		if(pendingCountEntity==null){
			pendingCountEntity=new PendingCountsEntity();
			pendingCountEntity.setNotificationsCount(0);
			pendingCountEntity.setUserId(userEntity.getUserId());
			pendingCountDAO.persist(pendingCountEntity);
		}


		if("Viewed".equalsIgnoreCase(status))
				pendingCountEntity.setNotificationsCount((pendingCountEntity.getNotificationsCount()!=null && pendingCountEntity.getNotificationsCount()>0)?pendingCountEntity.getNotificationsCount()-1:0);
		else if("Pending".equalsIgnoreCase(status))
				pendingCountEntity.setNotificationsCount(pendingCountEntity.getNotificationsCount()!=null?pendingCountEntity.getNotificationsCount()+1:1);

		//pendingCountDAO.merge(pendingCountEntity);
	}

	public DoctorNotificationStat getDoctorNotificationStats(Integer notificationId) {

		NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class, notificationId);
		DoctorNotificationStat doctorNotificationStat = new DoctorNotificationStat();
		if (notificationEntity != null && notificationEntity.getNotificationId() != null) {
			doctorNotificationStat.setNotificationId(notificationEntity.getNotificationId());
			doctorNotificationStat.setNotificationName(notificationEntity.getNotificationName());
			Integer totalCount = 0;
			for (Object[] data : doctorNotificationDAO.findDoctorNotificationStats(notificationId)) {

				String type = "";

				if (data[2] != null) {
					type = (String) data[2];
				}

				if ("RATING".equalsIgnoreCase(type)) {

					if (data[1] != null)
						doctorNotificationStat.setTotalCount(data[1] + "");
					else
						doctorNotificationStat.setTotalCount("0");

					if (data[0] != null)
						doctorNotificationStat.setRatingAverage(data[0] + "");
					else
						doctorNotificationStat.setRatingAverage("0");
				} else if ("FAVOURITE".equalsIgnoreCase(type)) {
					String value = "";
					value = data[1] + "";
					if ("Y".equalsIgnoreCase(value)) {
						doctorNotificationStat.setFavoriteYes(data[0] + "");
					} else {
						doctorNotificationStat.setFavoriteNo(data[0] + "");
					}
				} else if ("PRESCRIBE".equalsIgnoreCase(type)) {
					String value = "";
					value = data[1] + "";
					if ("Y".equalsIgnoreCase(value)) {
						doctorNotificationStat.setPrescribeYes(data[0] + "");
					} else {
						doctorNotificationStat.setPrescribeNo(data[0] + "");
					}
				} else if ("RECOMEND".equalsIgnoreCase(type)) {
					String value = "";
					value = data[1] + "";
					if ("Y".equalsIgnoreCase(value)) {
						doctorNotificationStat.setRecomendYes(data[0] + "");
					} else {
						doctorNotificationStat.setRecomendNo(data[0] + "");
					}
				}
			}
		}

		return doctorNotificationStat;

	}

	private Doctor copy(DoctorEntity doctorEntity) {
		Doctor doctor = new Doctor();
		if (doctorEntity != null) {
			UserEntity user = doctorEntity.getUser();
			if (user != null) {
				doctor.setEmailId(user.getEmailId());
				doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//				doctor.setMiddleName(user.getMiddleName());
				doctor.setLastName(user.getLastName());
				doctor.setPhoneNo(user.getPhoneNo());
				doctor.setAlias(user.getAlias());
				doctor.setTitle(user.getTitle());
				doctor.setAlternateEmailId(user.getAlternateEmailId());
				doctor.setMobileNo(user.getMobileNo());
				doctor.setDoctorId(doctorEntity.getDoctorId());
			}
		}
		return doctor;
	}

	public List<Doctor> getDocsList(List<DoctorEntity> active, List<Integer> viewPendingList) {
		List<Doctor> docsList = new ArrayList<Doctor>();
		for (DoctorEntity entity : active) {
			if (!viewPendingList.contains(entity.getDoctorId())) {
				Doctor d = this.copy(entity);
				docsList.add(d);
			}
		}
		return docsList;
	}


	public ArrayList<DeviceTokenEntity> publishNotifications(Notification notification) {
		String notificationsIds = notification.getPublishNotificationId();
		NotificationEntity notificationEntity = notificationDAO.findById(NotificationEntity.class,
				Integer.parseInt(notificationsIds));
		if(!Util.isEmpty(notificationEntity))
		notification.setCompanyId(notificationEntity.getCompanyId());
		List<NotificationEntity> notificationList = new ArrayList();
		notificationList.add(notificationEntity);
		List<DoctorEntity> docList = new ArrayList();
		System.out.println("pd.........."+notification.getPublishDocsIds());
		for (String docId : notification.getPublishDocsIds()) {
			DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, Integer.parseInt(docId));
			docList.add(doctorEntity);
		}


		return publishNotifications(notificationList, docList);

	}

	private static final String NEW_PRODUCT=" have a new product. Please click to know more.";
	public int pushNotification() {
		try {
				List<NotificationEntity> notificationEntities = new ArrayList<NotificationEntity>();
				notificationEntities.addAll(notificationDAO.findByPushStatus("Publish"));
//				notificationEntities.addAll(notificationDAO.findByPushStatus("New"));

				List<DeviceTokenEntity> registerDeviceTokens = deviceTokenDAO.findActiveDoctors();

				if (notificationEntities.size() > 0) {
					for (NotificationEntity notificationEntity : notificationEntities) {
						CompanyEntity companyEntity = companyDAO.findByCompanyId(notificationEntity.getCompanyId());
//						message = createPushMessage(notificationEntity, companyEntity);
						String message=companyEntity!=null?(companyEntity.getCompanyName() + NEW_PRODUCT):NEW_PRODUCT;
						if(registerDeviceTokens.size() > 0){
							pushMessage(message, registerDeviceTokens);
							notificationEntity.setPushNotification("PUSHED");
							notificationDAO.merge(notificationEntity);
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	private String createPushMessage(NotificationEntity notificationEntity, CompanyEntity companyEntity) {
		String gcmJSON = "";
		StringBuilder build = new StringBuilder();
		try {
//			.append("\"notificationId : \"").append(notificationEntity.getNotificationId()).append("\",");
			build.append("Notification: ");//append(notificationEntity.getNotificationName()).append("\",");
			build.append(notificationEntity.getNotificationDesc());
//			build.append("\"companyName\" : \"").append(companyEntity.getCompanyName()).append("\",");
//			build.append("\"companyId\" : \"").append(companyEntity.getCompanyId()).append("\",");
//			build.append("\"companyDesc\" : \"").append(companyEntity.getCompanyDesc()).append("\",");
//			build.append("\"companyUrl\" : \"").append(companyEntity.getCompanyUrl()).append("\"");

			gcmJSON = build.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return gcmJSON;
	}

	@Async
	public void push(String message,Integer doctorId) {
		try {
			if(doctorId!=null){
				List<DeviceTokenEntity> registeredDevices = deviceTokenDAO.findByDoctorId(doctorId);
				pushMessage(message, registeredDevices);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<DeviceTokenEntity> publishNotifications(List<NotificationEntity> notificationEntities,List<DoctorEntity> doctorEntities){
		ArrayList<DeviceTokenEntity> devicesList = new ArrayList<DeviceTokenEntity>();
		try
		{
			//Find all unpublished notifications
			boolean flag = false;

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
						doctorNotificationEntity.setViewCount(0);
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

						updateNotificationCount(user.getSecurity().getLoginId(), doctorNotificationEntity.getViewStatus());

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
								updateNotificationCount(user.getSecurity().getLoginId(), repNotificationEntity.getViewStatus());
//								List<DeviceTokenEntity> devices=deviceTokenDAO.findByDoctorId(pharmaRep.getRepId());
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
//				if(!Util.isEmpty(companyEntity))
//				 message+= companyEntity.getCompanyName();
//				message+=" have a new product. Please click to know more.";


//			if(devicesList!=null && devicesList.size()>0){
//				push(message, devicesList);
//				//Notify.sendNotification("New Notification", devicesList);
//			}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return devicesList;

	}

	@Async
	public   void push(String message, ArrayList<DeviceTokenEntity> devicesList) {
		try{
		if(!Util.isEmpty((Collection)devicesList))
			pushMessage(message, devicesList);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
		}
	}

	/**
	 * Core Utility method for sending push notifications
	 * @param message
	 * @param devicesList
	 * @param company
	 */
	@Async
	public void pushMessage(String message, List<DeviceTokenEntity> devicesList,CompanyEntity company) {
		if (!Util.isEmpty(devicesList))
			for (DeviceTokenEntity d : devicesList) {
				try {
					if ("IOS".equalsIgnoreCase(d.getPlatform())) {
						IosPushNotification.pushMessage(d.getDeviceToken(), message,company);
					} else {
						String canonicalId=AndroidPushNotification.pushMessage(d.getDeviceToken(), message,company);
						if(!Util.isEmpty(canonicalId)){
							log.info(">>Device/CanonicalID::"+d.getDeviceToken()+"::"+canonicalId);
							deviceTokenDAO.deleteById(d.getDeviceToken());
						}
//						}
					}
				} catch (Exception e) {
					log.error("Unable to send push notification to::" + d.getDeviceToken(), e);
				}
			}

	}
	@Async
	public void pushMessage(String message, List<DeviceTokenEntity> devicesList) {
		pushMessage(message, devicesList,null);
	}

	public NotificationDetails getNotificationDetails(Integer detailNotificationId) {

		return getNotificationDetails(detailNotificationId,null);
	}

	public Map<String,List<DoctorNotification>> getReport(Integer notificationId) {
		log.info(" To get all doctors view count against notification " +notificationId);
		Map<String,List<DoctorNotification>> reportDataMap = new HashMap<String,List<DoctorNotification>>();
		List<DoctorNotification> docNList = new ArrayList<DoctorNotification>();
		try {
			NotificationEntity entity = notificationDAO.findById(NotificationEntity.class,
					notificationId);
			List<DoctorNotificationEntity> doctorNotificationEntities=doctorNotificationDAO.getAllViewByNotificationId(notificationId);
			for(DoctorNotificationEntity dne:doctorNotificationEntities){
				Doctor doctor=getDoctorDetailsByDoctorId(dne.getDoctorId());
				DoctorNotification docNtion = new DoctorNotification();
				docNtion.setDoctorName(doctor.getDisplayName());
				docNtion.setViewCount(dne.getViewCount());
				docNtion.setFavourite(dne.getFavourite());
				docNtion.setRemindMe(dne.getRemindMe() !=null ? "Y" : "N");
				docNList.add(docNtion);
			}
			log.info("Total doctors viewed count " +docNList.size());
			reportDataMap.put(notificationId + "-" + entity.getNotificationName(), docNList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return reportDataMap;

	}


}