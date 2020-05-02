
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.ChatDAO;
import com.medrep.app.dao.DeviceTokenDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.PatDocAppointDAO;
import com.medrep.app.dao.PatientDAO;
import com.medrep.app.entity.ChatEntity;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.PatientEntity;
import com.medrep.app.model.ChatMessageModel;
import com.medrep.app.model.ChatMessages;
import com.medrep.app.util.AndroidPushNotification;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.IosPushNotification;
import com.medrep.app.util.IosPushNotificationPatient;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.Util;


@Service("chatMessageService")
@Transactional
public class ChatMessageService {
	@Autowired
	PatDocAppointDAO patDocAppointDAO;
	@Autowired
	ChatDAO chatDAO;
	@Autowired
	PatientDAO patientDAO;
	@Autowired
	DoctorDAO doctorDAO;
	@Autowired
	DeviceTokenDAO deviceTokenDAO;
	
	private static final Log log = LogFactory.getLog(ChatMessageService.class);
	
	public List<ChatMessageModel> getChatConsultings(String loginId,Integer status){
	
		List<ChatMessageModel> chatModels=new ArrayList<ChatMessageModel>();
		
		if (status ==1) {
			DoctorEntity doctorEntity=doctorDAO.findByLoginId(loginId);
			List<ChatEntity> chatEntites=chatDAO.getDocChatConsultings(doctorEntity.getDoctorId());
			for (ChatEntity ce: chatEntites) {
				ChatMessageModel model = BeanUtils.instantiateClass(ChatMessageModel.class);
				BeanUtils.copyProperties(ce,model );
				chatModels.add(model);
			}
		}else {
			PatientEntity patientEntity=patientDAO.findByLoginId(loginId);
			List<ChatEntity> chatEntites=chatDAO.getPatChatConsultings(patientEntity.getPatientId());
			for (ChatEntity ce: chatEntites) {
				ChatMessageModel model = BeanUtils.instantiateClass(ChatMessageModel.class);
				BeanUtils.copyProperties(ce,model );
				chatModels.add(model);
			}
		}
		
		return chatModels;
		
	}
	
	/*public void updateVideoConsultings(String loginId,Integer videoId,Integer status){
		
		
		if (status ==1) {
			DoctorEntity doctorEntity=doctorDAO.findByLoginId(loginId);
			ChatEntity chatEntity=chatDAO.findById(ChatEntity.class,videoId );
			if(chatEntity !=null) {
				chatEntity.setPatStatus("Y");
				chatDAO.merge(chatEntity);
			}
		}else {
			ChatEntity chatEntity=chatDAO.findById(ChatEntity.class,videoId );
			if(chatEntity !=null) {
				chatEntity.setDocStatus("Y");
				chatDAO.merge(chatEntity);
			}
			


		}
				
	}*/
	@Async
	public void publishChatMessage(ChatMessageModel model) throws IOException {
		String chatMsgString;
		if(model.getChatMessages().getUserType() == 1) {
			DoctorEntity doctorEntity=doctorDAO.findByDoctorId(model.getDoctorId());			

			List<DeviceTokenEntity> availableDevices=deviceTokenDAO.findBySinglePatientId(Integer.valueOf(model.getPatientId()));	
			if(availableDevices != null && availableDevices.size() > 0) {
				String message="Message From "+ (doctorEntity.getUser().getFirstName()) +" "+ (doctorEntity.getUser().getLastName()); 

				if("IMAGE".equals(model.getChatMessages().getMsgType())){	
						String orgPic=null,thumPic=null;
						if(model.getChatMessages().getOrgImg() != null) {
							 orgPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/chatImages/";
							 orgPic += FileUtil.copyBinaryData(model.getChatMessages().getOrgImg().getBytes(),MedRepProperty.getInstance().getProperties("images.loc")+File.separator+"chatImages",model.getChatMessages().getImgName());
						}
						if(model.getChatMessages().getThumbImg() != null) {
							 thumPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/chatImages/";
							 thumPic += FileUtil.copyBinaryData(model.getChatMessages().getOrgImg().getBytes(),MedRepProperty.getInstance().getProperties("images.loc")+File.separator+"chatThumbImages",model.getChatMessages().getImgName());
						}											
						if ("IOS".equalsIgnoreCase(availableDevices.get(0).getPlatform())) {
							IosPushNotificationPatient.pushChatMessage(availableDevices.get(0).getDeviceToken(),message ,"Received Attachment");
						} else {
							String canonicalId=AndroidPushNotification.pushChatMessage(availableDevices.get(0).getDeviceToken(), message,"Received Attachment");
							if(!Util.isEmpty(canonicalId)){
								log.info(">>Device/CanonicalID::"+availableDevices.get(0).getDeviceToken()+"::"+canonicalId);
								deviceTokenDAO.deleteById(availableDevices.get(0).getDeviceToken());
							}
					
						}
						Date d1=new Date();
						ChatEntity chatEntites = BeanUtils.instantiateClass(ChatEntity.class);
						BeanUtils.copyProperties(model,chatEntites ,"chatMessages");
						ChatMessages chatMessage=new ChatMessages();
						chatMessage.setMsgType("IMAGE");
						chatMessage.setThumbImg(thumPic);
						chatMessage.setOrgImg(orgPic);
						chatMessage.setUserType(1);
						chatMessage.setsDate((DateConvertor.convertDateToString(d1,DateConvertor.YYYYMMDD2))+" "+(DateConvertor.convertDateToString(d1,DateConvertor.HHmmss)));
						chatEntites.setChatMessages(chatMessage);
						chatDAO.persist(chatEntites);
				}else {
						if ("IOS".equalsIgnoreCase(availableDevices.get(0).getPlatform())) {
							IosPushNotificationPatient.pushChatMessage(availableDevices.get(0).getDeviceToken(),message ,model.getChatMessages().getText());
						} else {
							String canonicalId=AndroidPushNotification.pushChatMessage(availableDevices.get(0).getDeviceToken(), message,model.getChatMessages().getText());
							if(!Util.isEmpty(canonicalId)){
								log.info(">>Device/CanonicalID::"+availableDevices.get(0).getDeviceToken()+"::"+canonicalId);
								deviceTokenDAO.deleteById(availableDevices.get(0).getDeviceToken());
							}
					
						}
						ChatEntity chatEntites = BeanUtils.instantiateClass(ChatEntity.class);
						BeanUtils.copyProperties(model,chatEntites);
						Date d1=new Date();
						ChatMessages chatMessage=new ChatMessages();
						chatMessage.setMsgType("TEXT");
						chatMessage.setText(model.getChatMessages().getText());
						chatMessage.setUserType(1);
						chatMessage.setsDate((DateConvertor.convertDateToString(d1,DateConvertor.YYYYMMDD2))+" "+(DateConvertor.convertDateToString(d1,DateConvertor.HHmmss)));
						chatEntites.setChatMessages(chatMessage);
						chatDAO.persist(chatEntites);
				}							
			}
			
		}else {		
			PatientEntity patientEntity=patientDAO.findByPatientId(model.getPatientId());
			List<DeviceTokenEntity> availableDevices=deviceTokenDAO.findBySingleDoctorId(Integer.valueOf(model.getDoctorId()));	
			if(availableDevices != null && availableDevices.size() > 0) {
					String message="Message From "+ (patientEntity.getUser().getFirstName()) +" "+ (patientEntity.getUser().getLastName()); 

					if("IMAGE".equals(model.getChatMessages().getMsgType())){		
							String orgPic=null,thumPic=null;
							if(model.getChatMessages().getOrgImg() != null) {
								 orgPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/chatImages/";
								orgPic += FileUtil.copyBinaryData(model.getChatMessages().getOrgImg().getBytes(),MedRepProperty.getInstance().getProperties("images.loc")+File.separator+"chatImages",model.getChatMessages().getImgName());
							}
							if(model.getChatMessages().getThumbImg() != null) {
								 thumPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/chatImages/";
								 thumPic += FileUtil.copyBinaryData(model.getChatMessages().getOrgImg().getBytes(),MedRepProperty.getInstance().getProperties("images.loc")+File.separator+"chatThumbImages",model.getChatMessages().getImgName());
							}						
						
							if ("IOS".equalsIgnoreCase(availableDevices.get(0).getPlatform())) {
								IosPushNotification.pushChatMessage(availableDevices.get(0).getDeviceToken(),message ,"Received Attachment");
							} else {
								String canonicalId=AndroidPushNotification.pushChatMessage(availableDevices.get(0).getDeviceToken(), message,"Received Attachment");
								if(!Util.isEmpty(canonicalId)){
									log.info(">>Device/CanonicalID::"+availableDevices.get(0).getDeviceToken()+"::"+canonicalId);
									deviceTokenDAO.deleteById(availableDevices.get(0).getDeviceToken());
								}
						
							}
							ChatEntity chatEntites = BeanUtils.instantiateClass(ChatEntity.class);
							BeanUtils.copyProperties(model,chatEntites ,"chatMessages");
							ChatMessages chatMessage=new ChatMessages();
							chatMessage.setMsgType("IMAGE");
							chatMessage.setThumbImg(thumPic);
							chatMessage.setOrgImg(orgPic);
							chatMessage.setUserType(2);
							Date d1=new Date();
							chatMessage.setsDate((DateConvertor.convertDateToString(d1,DateConvertor.YYYYMMDD2))+" "+(DateConvertor.convertDateToString(d1,DateConvertor.HHmmss)));
							chatEntites.setChatMessages(chatMessage);
							chatDAO.persist(chatEntites);
						}else {
								if ("IOS".equalsIgnoreCase(availableDevices.get(0).getPlatform())) {
									IosPushNotification.pushChatMessage(availableDevices.get(0).getDeviceToken(),message ,model.getChatMessages().getText());
								} else {
									String canonicalId=AndroidPushNotification.pushChatMessage(availableDevices.get(0).getDeviceToken(), message,model.getChatMessages().getText());
									if(!Util.isEmpty(canonicalId)){
										log.info(">>Device/CanonicalID::"+availableDevices.get(0).getDeviceToken()+"::"+canonicalId);
										deviceTokenDAO.deleteById(availableDevices.get(0).getDeviceToken());
									}
							
								}
								ChatEntity chatEntites = BeanUtils.instantiateClass(ChatEntity.class);
								BeanUtils.copyProperties(model,chatEntites);
								Date d1=new Date();
								ChatMessages chatMessage=new ChatMessages();
								chatMessage.setMsgType("TEXT");
								chatMessage.setText(model.getChatMessages().getText());
								chatMessage.setUserType(2);
								chatMessage.setsDate((DateConvertor.convertDateToString(d1,DateConvertor.YYYYMMDD2))+" "+(DateConvertor.convertDateToString(d1,DateConvertor.HHmmss)));
								chatEntites.setChatMessages(chatMessage);
								chatDAO.persist(chatEntites);
				}							
			}
			
		}
		
		
	}
	public List<ChatMessages> getMyMessages(Integer appoinId){
	
		List<ChatMessages> chatModels=new ArrayList<ChatMessages>();

		List<ChatEntity> chatEntites =chatDAO.findByAppoinmentId(appoinId);

		for (ChatEntity ce: chatEntites) {
			ChatMessages model = BeanUtils.instantiateClass(ChatMessages.class);
			if(ce.getChatMessages() != null ) {
				BeanUtils.copyProperties(ce.getChatMessages(),model );
				chatModels.add(model);
			}
				
		}
		
		return chatModels;
	
	}
	
}