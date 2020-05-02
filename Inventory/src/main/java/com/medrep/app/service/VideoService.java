
package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.DeviceTokenDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.PatDocAppointDAO;
import com.medrep.app.dao.PatientDAO;
import com.medrep.app.dao.VideoDAO;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorInvitationEntity;
import com.medrep.app.entity.PatientDocAppoiEntity;
import com.medrep.app.entity.PatientEntity;
import com.medrep.app.entity.VideoEntity;
import com.medrep.app.model.DoctorConsultingsModel;
import com.medrep.app.model.VideoModel;
import com.medrep.app.util.AndroidPushNotification;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.IosPushNotification;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.Util;
import com.opentok.OpenTok;
import com.opentok.Session;
import com.opentok.TokenOptions;


@Service("videoService")
@Transactional
public class VideoService {
	@Autowired
	PatDocAppointDAO patDocAppointDAO;
	@Autowired
	VideoDAO videoDAO;
	@Autowired
	PatientDAO patientDAO;
	@Autowired
	DoctorDAO doctorDAO;
	
	@Autowired
	DeviceTokenDAO deviceTokenDAO;

	
	private static final Log log = LogFactory.getLog(VideoService.class);
	

	static final int apiKey = 46052952;
	static final String apiSecret = "9292574d1d94599ad1da0958f721750499e88e95";

	public VideoModel createOrCheckVideoId(Integer appoinmentId,Integer status) {
		VideoModel videoModel=null;
		
		PatientDocAppoiEntity patientDocAppEntity=patDocAppointDAO.findById(PatientDocAppoiEntity.class, appoinmentId);
		
		if (patientDocAppEntity !=null) {
			VideoEntity videoEntity=null;
			if(patientDocAppEntity.getVideoId() !=null) {
					videoEntity=videoDAO.findById(VideoEntity.class, patientDocAppEntity.getVideoId());
				
					if(videoEntity !=null) {
						videoModel = BeanUtils.instantiateClass(VideoModel.class);

						BeanUtils.copyProperties(videoEntity,videoModel );
					}
				
				}else {
					 videoEntity=new VideoEntity();
					try {	
						OpenTok opentok = new OpenTok(apiKey, apiSecret);

						Session session = opentok.createSession();
						String sessionId = session.getSessionId();

						String docToken = session.generateToken(new TokenOptions.Builder()
							  .data("name="+patientDocAppEntity.getDoctorEntity().getDoctorId())
							  .build());
						String patToken = session.generateToken(new TokenOptions.Builder()
							  .data("name="+patientDocAppEntity.getPatientEntity().getPatientId())
							  .build());

						videoEntity.setApiKey(apiKey);
						videoEntity.setAppointmentId(appoinmentId);
						Date d1=new Date();
						videoEntity.setCreatedDate(d1);
						videoEntity.setDocToken(docToken);
						videoEntity.setDoctorId(patientDocAppEntity.getDoctorEntity().getDoctorId());
						videoEntity.setPatToken(patToken);
						videoEntity.setPatientId(patientDocAppEntity.getPatientEntity().getPatientId());
						videoEntity.setSessionId(sessionId);
						
							videoEntity.setPatStatus("N");
							videoEntity.setDocStatus("N");
						videoDAO.persist(videoEntity);
						patientDocAppEntity.setVideoId(videoEntity.getVideoId());
						patDocAppointDAO.merge(patientDocAppEntity);
						videoModel = BeanUtils.instantiateClass(VideoModel.class);

						BeanUtils.copyProperties(videoEntity,videoModel );
					
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					
			}
		}
		return videoModel;
		
	}
	public VideoModel getVideoConsultings(Integer videoId){
	
		VideoModel videoModel=new VideoModel();		
		VideoEntity videoEntity=videoDAO.findById(VideoEntity.class,videoId );
		if (videoEntity !=null){
			videoModel.setVideoId(videoEntity.getVideoId());
			videoModel.setSessionId(videoEntity.getSessionId());
			videoModel.setApiKey(videoEntity.getApiKey());
			videoModel.setDocToken(videoEntity.getDocToken());

		}		
		return videoModel;
		
	}
	
/*	public void updateVideoConsultings(String loginId,Integer videoId,Integer status){
		
		
		if (status ==1) {
			DoctorEntity doctorEntity=doctorDAO.findByLoginId(loginId);
			VideoEntity videoEntity=videoDAO.findById(VideoEntity.class,videoId );
			if(videoEntity !=null) {
				videoEntity.setPatStatus("Y");
				videoDAO.merge(videoEntity);
			}
		}else {
			PatientEntity patientEntity=patientDAO.findByLoginId(loginId);
			VideoEntity videoEntity=videoDAO.findById(VideoEntity.class,videoId );
			if(videoEntity !=null) {
				videoEntity.setPatStatus("Y");
				videoDAO.merge(videoEntity);
			}
			


		}
				
	}*/
	
	public void videopushNotificationDoctor(Integer videoId){
			VideoEntity videoEntity=videoDAO.findById(VideoEntity.class,videoId );
			if(videoEntity !=null) {
				DoctorEntity doctorEntity=doctorDAO.findByDoctorId(videoEntity.getDoctorId());
				if(doctorEntity.getDoctorId() != null) {
				
					List<DeviceTokenEntity> availableDevices=deviceTokenDAO.findBySingleDoctorId(Integer.valueOf(doctorEntity.getDoctorId()));	
					if(availableDevices != null) {
					
						PatientEntity patientEntity=patientDAO.findByPatientId(videoEntity.getPatientId());
						String message="Video Call From "+ (patientEntity.getUser().getFirstName()) +" "+ (patientEntity.getUser().getLastName()); 
						if ("IOS".equalsIgnoreCase(availableDevices.get(0).getPlatform())) {
							IosPushNotification.pushVideoMessage(availableDevices.get(0).getDeviceToken(),message ,videoEntity);
						} else {
							String canonicalId=AndroidPushNotification.pushVideoMessage(availableDevices.get(0).getDeviceToken(), message,videoEntity);
							if(!Util.isEmpty(canonicalId)){
								log.info(">>Device/CanonicalID::"+availableDevices.get(0).getDeviceToken()+"::"+canonicalId);
								deviceTokenDAO.deleteById(availableDevices.get(0).getDeviceToken());
						}
					
					}	
					
					}
				}				
			}	
	}
	
	/**/

}