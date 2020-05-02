package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import com.medrep.app.dao.NotificationDetailsDAO;
import com.medrep.app.dao.PendingCountDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.PharmaRepNotificationDAO;
import com.medrep.app.dao.PharmaTherapeuticsDAO;
import com.medrep.app.dao.PharmaWebinarDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.engine.NotificationPublisherManual;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.entity.DisplayPictureEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorNotificationEntity;
import com.medrep.app.entity.NotificationDetailsEntity;
import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.PendingCountsEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.PharmaRepNotificationEntity;
import com.medrep.app.entity.PharmaWebinarEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorNotification;
import com.medrep.app.model.DoctorNotificationStat;
import com.medrep.app.model.Notification;
import com.medrep.app.model.NotificationDetails;
import com.medrep.app.model.NotificationStat;
import com.medrep.app.model.PharmaRepNotification;
import com.medrep.app.model.PharmaWebinar;
import com.medrep.app.util.AndroidPushNotification;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.IosPushNotification;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.Util;

@Service("pharmaWebinarService")
@Transactional
public class PharmaWebinarService {

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	PharmaWebinarDAO pharmaWebinarDAO;
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

	private static final Log log = LogFactory.getLog(PharmaWebinarService.class);
	

	public List<PharmaWebinar> getAdminWebinarsList() throws MedrepException {
		List<PharmaWebinar> valObjs = new ArrayList<PharmaWebinar>();
		try {
			List<PharmaWebinarEntity> list = pharmaWebinarDAO.getAdminWebinarsList();
			Map<String, String> cmap = getAllCompanys();

			for (PharmaWebinarEntity entity : list) {
				PharmaWebinar notification = BeanUtils.instantiateClass(PharmaWebinar.class);
				BeanUtils.copyProperties(entity, notification);
				Date d = entity.getcDate();
				if (d != null)
					notification.setcDate(d.toString());
				Date vd = entity.getVdate();
				if (vd != null)
					notification.setVdate(vd.toString());
				Integer cid = notification.getCompanyId();
				if (cid != null && !cid.equals("0"))
					notification.setCompanyName(cmap.get(String.valueOf(cid)));
				else
					notification.setCompanyName("");
				valObjs.add(notification);
			}
		} catch (Exception e) {
			throw new MedrepException("Error while getting Notification List " + e.getMessage());
		}
		return valObjs;
	}
	
	public List<PharmaWebinar> getWebinarsList(String status,Integer companyId) throws MedrepException {
		List<PharmaWebinar> valObjs = new ArrayList<PharmaWebinar>();
		try {
			List<PharmaWebinarEntity> list = pharmaWebinarDAO.getWebinarsList(status,companyId);
			Map<String, String> cmap = getAllCompanys();

			for (PharmaWebinarEntity entity : list) {
				PharmaWebinar pWebinars = BeanUtils.instantiateClass(PharmaWebinar.class);
				BeanUtils.copyProperties(entity, pWebinars);
				Date d = entity.getcDate();
				if (d != null)
					pWebinars.setcDate(d.toString());
				Date vd = entity.getVdate();
				if (vd != null)
					pWebinars.setVdate(vd.toString());
				Integer cid = pWebinars.getCompanyId();
				if (cid != null && !cid.equals("0"))
					pWebinars.setCompanyName(cmap.get(String.valueOf(cid)));
				else
					pWebinars.setCompanyName("");
				valObjs.add(pWebinars);
			}
		} catch (Exception e) {
			throw new MedrepException("Error while getting Notification List " + e.getMessage());
		}
		return valObjs;
	}

	public void createWebinars(PharmaWebinar pharmaWebinar) throws MedrepException {
		try {
			PharmaWebinarEntity entity = BeanUtils.instantiateClass(PharmaWebinarEntity.class);
			BeanUtils.copyProperties(pharmaWebinar, entity);
			MultipartFile file = pharmaWebinar.getThumbImage();
			if (file != null) {
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/pharmawebinars/"+(new Date()).getTime();
				_displayPic += FileUtil.copyImage(file,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "pharmawebinars"+File.separator+(new Date()).getTime());
				entity.setThumbImgUrl(_displayPic);
			}
			if ( pharmaWebinar.getVdate() !=null) {
				Date vd=	DateConvertor.convertStringToDate(pharmaWebinar.getVdate(),DateConvertor.YYYYMMDD2);
				entity.setVdate(vd);
			} 
			entity.setcDate(new Date());
			pharmaWebinarDAO.persist(entity);
			
		} catch (Exception e) {
			throw new MedrepException("Error while creating Notification " + e.getMessage());
		}
	}
	
	public void updateWebinarStatus(Integer webinarId,String Status) {
		
		PharmaWebinarEntity pharmaWebinarEntity=pharmaWebinarDAO.findById(PharmaWebinarEntity.class, webinarId);
			pharmaWebinarEntity.setStatus(Status);
			pharmaWebinarDAO.merge(pharmaWebinarEntity);
		
		
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
	
	/*public void createFileUrl(MultipartFile mfile, NotificationDetailsEntity entity, int nid, int ndid) throws IOException {
		entity.setContentType(mfile.getOriginalFilename().split("\\.")[1].toUpperCase());
		entity.setContentName(mfile.getOriginalFilename().replace("\\s",""));
		if(mfile!=null){
//			String contentLocationUrl = MedRepProperty.getInstance().getProperties("medrep.home") + "static/notifications/"+nid+"/"+ndid+"/";
			String contentLocationUrl = MedRepProperty.getInstance().getProperties("static.resources.url") + "static/notifications/"+nid+"/"+ndid+"/";
			contentLocationUrl += FileUtil.copyBytesToFile(mfile.getBytes(),MedRepProperty.getInstance().getProperties("medrep.notification.basepath")+File.separator+nid+File.separator+ndid,mfile.getOriginalFilename());
			entity.setContentLocation(contentLocationUrl);
		}
	}
*/
	


}