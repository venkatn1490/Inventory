package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.medrep.app.dao.ChatDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.PatDocAppointDAO;
import com.medrep.app.dao.PatientDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.VideoDAO;
import com.medrep.app.entity.PatientDocAppoiEntity;
import com.medrep.app.entity.PatientEntity;
import com.medrep.app.entity.VideoEntity;
import com.medrep.app.model.PatDocAppointModel;
import com.medrep.app.model.VideoModel;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;

@Service("patientAppointmentService")
@Transactional
public class PatientAppointmentService {
	
	@Autowired
	PatDocAppointDAO patDocAppointDAO;

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	PatientDAO patientDAO;
	@Autowired 
	TherapeuticAreaDAO therapeuticAreaDAO;
	
	@Autowired 
	VideoDAO videoDAO;
	@Autowired 
	ChatDAO chatDAO;

	private static final Log log = LogFactory.getLog(PatientAppointmentService.class);

	
	public void getDoctorAppointmentForPatient(Model model) {
		String username = (String) model.asMap().get("username");
		String communicationMode=(String) model.asMap().get("communicationMode");
		try
		{
		if("CHAT".equals(communicationMode)) {
			PatientEntity patientEntity = patientDAO.findByLoginId(username);
			List<PatientDocAppoiEntity> appointmentEntities = null;
			
				Map<String,Object> patientAppointmentInfo=new HashMap<String,Object>();
				appointmentEntities = patDocAppointDAO.findByPatientId(patientEntity.getPatientId(),communicationMode);
				List<PatDocAppointModel> previousAppoinment=new ArrayList<PatDocAppointModel>();
				List<PatDocAppointModel> upcomingAppoinment=new ArrayList<PatDocAppointModel>();
				List<PatDocAppointModel> ongoingAppoinment=new ArrayList<PatDocAppointModel>();

				if(appointmentEntities != null) {
					for(PatientDocAppoiEntity we: appointmentEntities) {
						
						Calendar endDate=Calendar.getInstance();
						Calendar startDate=Calendar.getInstance();
						startDate.setTime(we.getAppointmentDate());
						endDate.setTime(we.getAppointmentDate());
						endDate.add(Calendar.DATE,2);
						if((new Date()).compareTo(endDate.getTime())> 0){
							PatDocAppointModel patDocAppointModel=new PatDocAppointModel();
							BeanUtils.copyProperties(we, patDocAppointModel);
							if(we.getDoctorEntity().getTherapeuticId() !=null) {
								
								patDocAppointModel.setTherapeuticName(therapeuticAreaDAO.findById(Integer.parseInt(we.getDoctorEntity().getTherapeuticId() )).getTherapeuticName());

							}
							patDocAppointModel.setDocImageUrl(we.getDoctorEntity().getUser().getDisplayPicture().getImageUrl());
							DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
							patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
							DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
							patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
							patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());
							patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
							patDocAppointModel.setDoctorName((we.getDoctorEntity().getUser().getFirstName() == null ? "" : we.getDoctorEntity().getUser().getFirstName())+" "+(we.getDoctorEntity().getUser().getLastName() == null ? "" : we.getDoctorEntity().getUser().getLastName()));
							upcomingAppoinment.add(patDocAppointModel);
						}else if((new Date()).compareTo(startDate.getTime()) <= 0  ){
							PatDocAppointModel patDocAppointModel=new PatDocAppointModel();							
							BeanUtils.copyProperties(we, patDocAppointModel);
							if(we.getDoctorEntity().getTherapeuticId() !=null) {				
								patDocAppointModel.setTherapeuticName(therapeuticAreaDAO.findById(Integer.parseInt(we.getDoctorEntity().getTherapeuticId() )).getTherapeuticName());
							}
							patDocAppointModel.setDocImageUrl(we.getDoctorEntity().getUser().getDisplayPicture().getImageUrl());
							
							
							DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
							patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
							DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
							patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
							patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());
							patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
							patDocAppointModel.setDoctorName((we.getDoctorEntity().getUser().getFirstName() == null ? "" : we.getDoctorEntity().getUser().getFirstName())+" "+(we.getDoctorEntity().getUser().getLastName() == null ? "" : we.getDoctorEntity().getUser().getLastName()));
							ongoingAppoinment.add(patDocAppointModel);
						}else {
							PatDocAppointModel patDocAppointModel=new PatDocAppointModel();							
							BeanUtils.copyProperties(we, patDocAppointModel);
							if(we.getDoctorEntity().getTherapeuticId() !=null) {						
								patDocAppointModel.setTherapeuticName(therapeuticAreaDAO.findById(Integer.parseInt(we.getDoctorEntity().getTherapeuticId() )).getTherapeuticName());

							}
							patDocAppointModel.setDocImageUrl(we.getDoctorEntity().getUser().getDisplayPicture().getImageUrl());
							DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
							patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
							DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
							patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
							patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());
							patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
							patDocAppointModel.setDoctorName((we.getDoctorEntity().getUser().getFirstName() == null ? "" : we.getDoctorEntity().getUser().getFirstName())+" "+(we.getDoctorEntity().getUser().getLastName() == null ? "" : we.getDoctorEntity().getUser().getLastName()));
							previousAppoinment.add(patDocAppointModel);
						}
					}
					patientAppointmentInfo.put("previous", previousAppoinment);
					patientAppointmentInfo.put("upcoming", upcomingAppoinment);	
					patientAppointmentInfo.put("ongoing", ongoingAppoinment);	
					model.addAttribute("patientAppointmentInfo",patientAppointmentInfo);
				}
		
			}else if("VIDEO".equals(communicationMode)) {
				PatientEntity patientEntity = patientDAO.findByLoginId(username);
				List<PatientDocAppoiEntity> appointmentEntities = null;
				
					Map<String,Object> patientAppointmentInfo=new HashMap<String,Object>();
					appointmentEntities = patDocAppointDAO.findByPatientId(patientEntity.getPatientId(),communicationMode);
					List<PatDocAppointModel> previousAppoinment=new ArrayList<PatDocAppointModel>();
					List<PatDocAppointModel> upcomingAppoinment=new ArrayList<PatDocAppointModel>();
					if(appointmentEntities != null) {
						for(PatientDocAppoiEntity we: appointmentEntities) {
							//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Date date = new Date();
							if(we.getAppointmentTime().compareTo(date) >= 0) {
								PatDocAppointModel patDocAppointModel=new PatDocAppointModel();
								BeanUtils.copyProperties(we, patDocAppointModel);
								
								VideoEntity videoEntity=videoDAO.findById(VideoEntity.class,we.getVideoId());
								if(videoEntity !=null) {
									VideoModel videoModel=new VideoModel();
									BeanUtils.copyProperties(videoEntity,videoModel);
									patDocAppointModel.setVideoModel(videoModel);
								}
								
								if(we.getDoctorEntity().getTherapeuticId() !=null) {				
									patDocAppointModel.setTherapeuticName(therapeuticAreaDAO.findById(Integer.parseInt(we.getDoctorEntity().getTherapeuticId() )).getTherapeuticName());
								}
								patDocAppointModel.setDocImageUrl(we.getDoctorEntity().getUser().getDisplayPicture().getImageUrl());

								DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
								patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
								DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
								patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
								patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
								patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());
								patDocAppointModel.setDoctorName((we.getDoctorEntity().getUser().getFirstName() == null ? "" : we.getDoctorEntity().getUser().getFirstName())+" "+(we.getDoctorEntity().getUser().getLastName() == null ? "" : we.getDoctorEntity().getUser().getLastName()));
								upcomingAppoinment.add(patDocAppointModel);
							}else {
								PatDocAppointModel patDocAppointModel=new PatDocAppointModel();	
								BeanUtils.copyProperties(we, patDocAppointModel);
								VideoEntity videoEntity=videoDAO.findById(VideoEntity.class,we.getVideoId());
								if(videoEntity !=null) {
									VideoModel videoModel=new VideoModel();
									BeanUtils.copyProperties(videoEntity,videoModel);
									patDocAppointModel.setVideoModel(videoModel);
								}
								if(we.getDoctorEntity().getTherapeuticId() !=null) {
									patDocAppointModel.setTherapeuticName(therapeuticAreaDAO.findById(Integer.parseInt(we.getDoctorEntity().getTherapeuticId() )).getTherapeuticName());
									patDocAppointModel.setDocImageUrl(we.getDoctorEntity().getUser().getDisplayPicture().getImageUrl());
								}
								DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
								patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
								DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
								patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
								patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
								patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());
								patDocAppointModel.setDoctorName((we.getDoctorEntity().getUser().getFirstName() == null ? "" : we.getDoctorEntity().getUser().getFirstName())+" "+(we.getDoctorEntity().getUser().getLastName() == null ? "" : we.getDoctorEntity().getUser().getLastName()));
								previousAppoinment.add(patDocAppointModel);
							}
						}
						patientAppointmentInfo.put("previous", previousAppoinment);
						patientAppointmentInfo.put("upcoming", upcomingAppoinment);	
						model.addAttribute("patientAppointmentInfo",patientAppointmentInfo);
					}
			}else{
				
				PatientEntity patientEntity = patientDAO.findByLoginId(username);
				List<PatientDocAppoiEntity> appointmentEntities = null;
				
					Map<String,Object> patientAppointmentInfo=new HashMap<String,Object>();
					appointmentEntities = patDocAppointDAO.findByPatientId(patientEntity.getPatientId(),communicationMode);
					List<PatDocAppointModel> previousAppoinment=new ArrayList<PatDocAppointModel>();
					List<PatDocAppointModel> upcomingAppoinment=new ArrayList<PatDocAppointModel>();
					if(appointmentEntities != null) {
						for(PatientDocAppoiEntity we: appointmentEntities) {
						//	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Date date = new Date();
							if(we.getAppointmentTime().compareTo(date) >=0) {
								PatDocAppointModel patDocAppointModel=new PatDocAppointModel();
								BeanUtils.copyProperties(we, patDocAppointModel);
								if(we.getDoctorEntity().getTherapeuticId() !=null) {				
									patDocAppointModel.setTherapeuticName(therapeuticAreaDAO.findById(Integer.parseInt(we.getDoctorEntity().getTherapeuticId() )).getTherapeuticName());
								}
								patDocAppointModel.setDocImageUrl(we.getDoctorEntity().getUser().getDisplayPicture().getImageUrl());

								DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
								patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
								DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
								patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
								patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
								patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());
								patDocAppointModel.setDoctorName((we.getDoctorEntity().getUser().getFirstName() == null ? "" : we.getDoctorEntity().getUser().getFirstName())+" "+(we.getDoctorEntity().getUser().getLastName() == null ? "" : we.getDoctorEntity().getUser().getLastName()));
								upcomingAppoinment.add(patDocAppointModel);
							}else {
								PatDocAppointModel patDocAppointModel=new PatDocAppointModel();	
								BeanUtils.copyProperties(we, patDocAppointModel);
								if(we.getDoctorEntity().getTherapeuticId() !=null) {
									
									patDocAppointModel.setTherapeuticName(therapeuticAreaDAO.findById(Integer.parseInt(we.getDoctorEntity().getTherapeuticId() )).getTherapeuticName());
									patDocAppointModel.setDocImageUrl(we.getDoctorEntity().getUser().getDisplayPicture().getImageUrl());

								}

								DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
								patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
								DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
								patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
								patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
								patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());
								patDocAppointModel.setDoctorName((we.getDoctorEntity().getUser().getFirstName() == null ? "" : we.getDoctorEntity().getUser().getFirstName())+" "+(we.getDoctorEntity().getUser().getLastName() == null ? "" : we.getDoctorEntity().getUser().getLastName()));
								previousAppoinment.add(patDocAppointModel);
							}
						}
						patientAppointmentInfo.put("previous", previousAppoinment);
						patientAppointmentInfo.put("upcoming", upcomingAppoinment);	
						model.addAttribute("patientAppointmentInfo",patientAppointmentInfo);
			}
		}
		} catch (Exception e)
		{
			System.out.println("Entry not found");
		}
	}
	
	public void postPrescriptionImage(PatDocAppointModel model) throws IOException{
		String img1=null,img2=null;
		if(model.getAppointmentId() != null) {
			PatientDocAppoiEntity patientDocAppoiEntity =patDocAppointDAO.findById(PatientDocAppoiEntity.class, model.getAppointmentId());
			if(model.getImgPrescriptionUrl1() != null) {
				img1 = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/videoPrescription/";
				img1 += FileUtil.copyBinaryData(model.getImgPrescriptionUrl1().getBytes(),MedRepProperty.getInstance().getProperties("images.loc")+File.separator+"videoPrescription","img1_"+(model.getAppointmentId())+".png");
				patientDocAppoiEntity.setImgPrescriptionUrl1(img1);
			}
			
			if(model.getImgPrescriptionUrl2() != null) {
				img2 = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/videoPrescription/";
				img2 += FileUtil.copyBinaryData(model.getImgPrescriptionUrl2().getBytes(),MedRepProperty.getInstance().getProperties("images.loc")+File.separator+"videoPrescription","img2_"+(model.getAppointmentId())+".png");
				patientDocAppoiEntity.setImgPrescriptionUrl2(img2);
			}
			patDocAppointDAO.merge(patientDocAppoiEntity);
			
		}
		
	}
}
