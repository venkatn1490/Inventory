package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.dao.AmbulancesDAO;
import com.medrep.app.dao.ChatDAO;
import com.medrep.app.dao.DiagnosticsDAO;
import com.medrep.app.dao.DoctorConsultingDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.HospitalDAO;
import com.medrep.app.dao.PatDocAppointDAO;
import com.medrep.app.dao.PatientDAO;
import com.medrep.app.dao.StateInformationDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.dao.VideoDAO;
import com.medrep.app.entity.AmbulancesEntity;
import com.medrep.app.entity.ChatEntity;
import com.medrep.app.entity.DiagnosticsEntity;
import com.medrep.app.entity.DoctorConsultingsEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.HospitalEntity;
import com.medrep.app.entity.PatientDocAppoiEntity;
import com.medrep.app.entity.PatientEntity;
import com.medrep.app.entity.VideoEntity;
import com.medrep.app.model.AmbulancesModel;
import com.medrep.app.model.ChatMessages;
import com.medrep.app.model.DiagnosticsModel;
import com.medrep.app.model.DoctorConsultingsModel;
import com.medrep.app.model.HospitalModel;
import com.medrep.app.model.PatDocAppointModel;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;
import com.opentok.OpenTok;
import com.opentok.Session;
import com.opentok.TokenOptions;

@Service("findnearbyservice")
@Transactional
public class FindNearByService {
	
	@Autowired
	HospitalDAO hospitalDAO;
	@Autowired
	AmbulancesDAO ambulanceDAO;
	@Autowired
	DiagnosticsDAO diagnosticsDAO;
	@Autowired 
	TherapeuticAreaDAO therapeuticAreaDAO;
	@Autowired
	PatDocAppointDAO patDocAppointDAO;
	
	@Autowired 
	DoctorConsultingDAO doctorConsultingDAO;
	@Autowired 
	StateInformationDAO stateinformationDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	DoctorDAO doctorDAO;
	@Autowired
	ChatDAO chatDAO;
	@Autowired
	VideoDAO videoDAO;	
	@Autowired
	PatientDAO patientDAO;
	

	static final int apiKey = 46052952;
	static final String apiSecret = "9292574d1d94599ad1da0958f721750499e88e95";
	
	static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs

	
	public  Map<String,Integer> getListofServicesCount(String locatity,String city)
	{
		Map<String, Integer> map = new HashMap<String,Integer>();
		map.put("doctorsCount",doctorConsultingDAO.findByDoctorsConnections(locatity,city));
		map.put("videodoctorCount",doctorConsultingDAO.findByOnlineDoctorsConnections(locatity,city,"VIDEO"));
        map.put("chatdoctorCount",doctorConsultingDAO.findByOnlineDoctorsConnections(locatity,city,"CHAT"));		
        map.put("hospitalCount",hospitalDAO.findByHosptialConnections(locatity,city));
        map.put("ambulancesCount",ambulanceDAO.findByAmbulancesConnections(locatity,city));
        map.put("diagnosticsCount",diagnosticsDAO.findByDiagnosticsConnections(locatity,city));		
		return map;
	}

	static final int DOCTORS = 0;
	static final int VIDEO=1;
	static final int CHAT=5;

	static final int HOSPITALS =2;
	static final int AMBULANCES =3;// insert
	static final int DIAGONISTICS =4;

	
	public Map<String,Object> getMyListDetails(Integer type,String locatity,String city){
		
		Map<String,Object> result=new HashMap<String,Object>();
		switch (type) {
		
		case DOCTORS:
			List<DoctorConsultingsEntity> list = doctorConsultingDAO.findByLocationId(locatity,city,"F2F");
			List<DoctorConsultingsModel> modelList = new ArrayList<DoctorConsultingsModel>();
			for(DoctorConsultingsEntity entity : list) {
				DoctorConsultingsModel model = BeanUtils.instantiateClass(DoctorConsultingsModel.class);
				BeanUtils.copyProperties(entity, model);
				DoctorEntity doctorEntity =doctorDAO.findByDoctorId(entity.getDoctorId());
				model.setDoctorName((doctorEntity.getUser().getFirstName() == null ? "" : doctorEntity.getUser().getFirstName())+" "+(doctorEntity.getUser().getLastName() == null ? "" : doctorEntity.getUser().getLastName()));
				model.setDpImageUrl(doctorEntity.getUser().getDisplayPicture().getImageUrl());
				model.setDoctorEmailId(doctorEntity.getUser().getEmailId());
				model.setNoOfYearsExperience(doctorEntity.getWorkExpYears()+"."+doctorEntity.getWorkExpMonths());
				model.setThrepeauticName(therapeuticAreaDAO.findById(Integer.parseInt(doctorEntity.getTherapeuticId())).getTherapeuticName());
				modelList.add(model);
			}
			 result.put("Doctors", modelList);
			break;
		case VIDEO:
			List<DoctorConsultingsEntity> onlineDoctorslist = doctorConsultingDAO.findByLocationId(locatity,city,"VIDEO");
			List<DoctorConsultingsModel> onlineList = new ArrayList<DoctorConsultingsModel>();
			for(DoctorConsultingsEntity entity : onlineDoctorslist) {
				DoctorConsultingsModel model = BeanUtils.instantiateClass(DoctorConsultingsModel.class);
				BeanUtils.copyProperties(entity, model);
				DoctorEntity doctorEntity =doctorDAO.findByDoctorId(entity.getDoctorId());
				model.setDoctorName((doctorEntity.getUser().getFirstName() == null ? "" : doctorEntity.getUser().getFirstName())+" "+(doctorEntity.getUser().getLastName() == null ? "" : doctorEntity.getUser().getLastName()));
				model.setDpImageUrl(doctorEntity.getUser().getDisplayPicture().getImageUrl());
				model.setDoctorEmailId(doctorEntity.getUser().getEmailId());
				model.setNoOfYearsExperience(doctorEntity.getWorkExpYears()+"."+doctorEntity.getWorkExpMonths());
				model.setThrepeauticName(therapeuticAreaDAO.findById(Integer.parseInt(doctorEntity.getTherapeuticId())).getTherapeuticName());
				onlineList.add(model);
			}
			 result.put("OnlineDoctors", onlineList);
			break;
		case CHAT:
			List<DoctorConsultingsEntity> chatDoctorslist = doctorConsultingDAO.findByLocationId(locatity,city,"CHAT");
			List<DoctorConsultingsModel> chatList = new ArrayList<DoctorConsultingsModel>();
			for(DoctorConsultingsEntity entity : chatDoctorslist) {
				DoctorConsultingsModel model = BeanUtils.instantiateClass(DoctorConsultingsModel.class);
				BeanUtils.copyProperties(entity, model);
				DoctorEntity doctorEntity =doctorDAO.findByDoctorId(entity.getDoctorId());
				model.setDoctorName((doctorEntity.getUser().getFirstName() == null ? "" : doctorEntity.getUser().getFirstName())+" "+(doctorEntity.getUser().getLastName() == null ? "" : doctorEntity.getUser().getLastName()));
				model.setDpImageUrl(doctorEntity.getUser().getDisplayPicture().getImageUrl());
				model.setDoctorEmailId(doctorEntity.getUser().getEmailId());
				model.setNoOfYearsExperience(doctorEntity.getWorkExpYears()+"."+doctorEntity.getWorkExpMonths());
				model.setThrepeauticName(therapeuticAreaDAO.findById(Integer.parseInt(doctorEntity.getTherapeuticId())).getTherapeuticName());
				chatList.add(model);
			}
			 result.put("OnlineDoctors", chatList);
			break;
		case HOSPITALS:
			List<HospitalEntity> hospitallist = hospitalDAO.getHospitalBasedOnLocatity(locatity,city);
			List<HospitalModel> hospitalmodel = new ArrayList<HospitalModel>();
			for(HospitalEntity entity : hospitallist) {
				HospitalModel model = BeanUtils.instantiateClass(HospitalModel.class);
				BeanUtils.copyProperties(entity, model);
				hospitalmodel.add(model);
			}
			 result.put("Hospitals", hospitalmodel);
			break;
		case AMBULANCES:
			List<AmbulancesEntity> ambulancesEntity = ambulanceDAO.getAmbulancesBasedOnLocatity(locatity,city);
			List<AmbulancesModel> ambulancesModel = new ArrayList<AmbulancesModel>();
			for(AmbulancesEntity entity : ambulancesEntity) {
				AmbulancesModel model = BeanUtils.instantiateClass(AmbulancesModel.class);
				BeanUtils.copyProperties(entity, model);
				ambulancesModel.add(model);
			}
			 result.put("Ambulances", ambulancesModel);
			break;
		case DIAGONISTICS:
			List<DiagnosticsEntity> diagnosticsEntity = diagnosticsDAO.getDiagnosticsBasedOnLocatity(locatity,city);
			List<DiagnosticsModel> diagnosticsModel = new ArrayList<DiagnosticsModel>();
			for(DiagnosticsEntity entity : diagnosticsEntity) {
				DiagnosticsModel model = BeanUtils.instantiateClass(DiagnosticsModel.class);
				BeanUtils.copyProperties(entity, model);
				diagnosticsModel.add(model);
			}
			 result.put("Diagnostics", diagnosticsModel);
			 break;
		}

		return result;
	}
	public List<HospitalModel> getAllHospital()
	{
	List<HospitalEntity> list = hospitalDAO.getHospitalList();
		List<HospitalModel> modelList = new ArrayList<HospitalModel>();
		for(HospitalEntity entity : list) {
			HospitalModel model = BeanUtils.instantiateClass(HospitalModel.class);
			BeanUtils.copyProperties(entity, model, "createdOn");
			
			modelList.add(model);
		}
		return modelList;
		
	}
//	List<Object[]> doctorIds= activityDAO.findUserByCompany(companyId);

	
	public List<AmbulancesModel> getAllAmbulances()
	{
	List<AmbulancesEntity> list = ambulanceDAO.getAmbulancesList();
		List<AmbulancesModel> modelList = new ArrayList<AmbulancesModel>();
		for(AmbulancesEntity entity : list) {
			AmbulancesModel model = BeanUtils.instantiateClass(AmbulancesModel.class);
			BeanUtils.copyProperties(entity, model, "createdOn");
			
			modelList.add(model);
		}
		return modelList;
		
	}
	
	/*public List<Object[]> fetchAllStateData(){
	List<Object[]> list = stateinformationDAO.fetchAllStateData();
		List<AmbulancesModel> modelList = new ArrayList<AmbulancesModel>();
		for(AmbulancesEntity entity : list) {
			AmbulancesModel model = BeanUtils.instantiateClass(AmbulancesModel.class);
			BeanUtils.copyProperties(entity, model, "createdOn");
			
			modelList.add(model);
		}
		return list;
		
	}*/
	public List<DiagnosticsModel> getAllDiagnostics()
	{
	List<DiagnosticsEntity> list = diagnosticsDAO.getDiagnosticsList();
		List<DiagnosticsModel> modelList = new ArrayList<DiagnosticsModel>();
		for(DiagnosticsEntity entity : list) {
			DiagnosticsModel model = BeanUtils.instantiateClass(DiagnosticsModel.class);
			BeanUtils.copyProperties(entity, model, "createdOn");
			
			modelList.add(model);
		}
		return modelList;
		
	}
	
	public void createHospital(Model model) throws IOException
	{
			HospitalModel hospital=(HospitalModel) model.asMap().get("hospital");
			HospitalEntity hospitalEntity = new HospitalEntity();
			
/*			DisplayPicture displayPicture = company.getDisplayPicture();
*/			hospitalEntity.setHospital_name(hospital.getHospital_name());
			hospitalEntity.setAddress1(hospital.getAddress1());
			hospitalEntity.setAddress2(hospital.getAddress2());
			hospitalEntity.setLocatity(hospital.getLocatity());
			hospitalEntity.setCity(hospital.getCity());
			hospitalEntity.setMobileNo(hospital.getMobileNo());
			hospitalEntity.setState(hospital.getState());
			hospitalEntity.setZipcode(hospital.getZipcode());
			MultipartFile displayPic=(MultipartFile) model.asMap().get("HospitalImage");
			if (displayPic != null) {
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/hospitals/";
				_displayPic += FileUtil.copyImage(displayPic,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"hospitals");
				hospitalEntity.setHospitalsImage(_displayPic);
			}
			MultipartFile displayLogoPic=(MultipartFile) model.asMap().get("hospitalLogo");
			if (displayLogoPic != null) {
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/hospitals/";
				_displayPic += FileUtil.copyImage(displayLogoPic,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"hospitals");
				hospitalEntity.setHospitalsLogo(_displayPic);
			}


			
			hospitalEntity.setCreatedOn(Calendar.getInstance().getTime());
			hospitalDAO.persist(hospitalEntity);
			

	}
	
	public void createAmbulances(Model model) throws IOException
	{
			AmbulancesModel ambulances=(AmbulancesModel) model.asMap().get("ambulances");
			AmbulancesEntity ambulancesEntity = new AmbulancesEntity();
			
/*			DisplayPicture displayPicture = company.getDisplayPicture();
*/			ambulancesEntity.setAmbulance_name(ambulances.getAmbulance_name());
			ambulancesEntity.setFee(ambulances.getFee());
			ambulancesEntity.setAddress1(ambulances.getAddress1());
			ambulancesEntity.setAddress2(ambulances.getAddress2());
			ambulancesEntity.setLocatity(ambulances.getLocatity());
			ambulancesEntity.setCity(ambulances.getCity());
			ambulancesEntity.setMobileNo(ambulances.getMobileNo());
			ambulancesEntity.setState(ambulances.getState());
			ambulancesEntity.setZipcode(ambulances.getZipcode());
			ambulancesEntity.setCreatedOn(Calendar.getInstance().getTime());

			MultipartFile displayPic=(MultipartFile) model.asMap().get("ambulancesImage");
			if (displayPic != null) {
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/ambulances/";
				_displayPic += FileUtil.copyImage(displayPic,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"ambulances");
				ambulancesEntity.setAmbulancesImage(_displayPic);
			}
			MultipartFile displayLogoPic=(MultipartFile) model.asMap().get("ambulancesLogo");
			if (displayLogoPic != null) {
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/ambulances/";
				_displayPic += FileUtil.copyImage(displayLogoPic,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"ambulances");
				ambulancesEntity.setAmbulancesLogo(_displayPic);
			}		
			ambulanceDAO.persist(ambulancesEntity);			
	}

	public void createDiagnostics(Model model) throws IOException
	{
		DiagnosticsModel diagnostics=(DiagnosticsModel) model.asMap().get("diagnostics");
		DiagnosticsEntity diagnosticsEntity = new DiagnosticsEntity();
			
			diagnosticsEntity.setDiagnostics_name(diagnostics.getDiagnostics_name());
			diagnosticsEntity.setAddress1(diagnostics.getAddress1());
			diagnosticsEntity.setAddress2(diagnostics.getAddress2());
			diagnosticsEntity.setLocatity(diagnostics.getLocatity());
			diagnosticsEntity.setCity(diagnostics.getCity());
			diagnosticsEntity.setMobileNo(diagnostics.getMobileNo());
			diagnosticsEntity.setState(diagnostics.getState());
			diagnosticsEntity.setZipcode(diagnostics.getZipcode());
			diagnosticsEntity.setCreatedOn(Calendar.getInstance().getTime());
			diagnosticsEntity.setHomeCollection(diagnostics.getHomeCollection());


			MultipartFile displayPic=(MultipartFile) model.asMap().get("diagnosticImage");
			if (displayPic != null) {
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/diagnostics/";
				_displayPic += FileUtil.copyImage(displayPic,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"diagnostics");
				diagnosticsEntity.setDignosticsImage(_displayPic);
			}
			MultipartFile displayLogoPic=(MultipartFile) model.asMap().get("diagnosticLogo");
			if (displayLogoPic != null) {
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/diagnostics/";
				_displayPic += FileUtil.copyImage(displayLogoPic,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"diagnostics");
				diagnosticsEntity.setDignosticsLogo(_displayPic);
			}
			diagnosticsDAO.persist(diagnosticsEntity);
	}
	
	
	public String  getCheckInAvailableDays (String date,Integer consultingId,String loginId) throws ParseException {
			PatientEntity patientEntity = patientDAO.findByLoginId(loginId);

		  String s1=null;
		  Date d1=DateConvertor.convertStringToDate(date,DateConvertor.YYYYMMDD1);
		  DateFormat format2=new SimpleDateFormat("E"); 
		  String finalDay=format2.format(d1);
		  DoctorConsultingsEntity doctorConsultingEntity=doctorConsultingDAO.findByConsultingDay(consultingId,finalDay);
		  if(doctorConsultingEntity !=null ) {
			  	int i1=patDocAppointDAO.findNoOfAppoiDay(consultingId,d1);
			  	if(i1 <= doctorConsultingEntity.getMaxAppointments()) {
			  			s1="AVAILABLE";
			  			List<PatientDocAppoiEntity> patientDocAppoiEntities= patDocAppointDAO.checkAppoinments(patientEntity.getPatientId(),consultingId,d1);
						if(patientDocAppoiEntities.size() <= 0) {
				  			s1="AVAILABLE";		
						}else {
				  			s1="Already Booked for This Slot";		
						}
			  	}else {
		  			s1="NOTAVAILABLE";
		  			
			  	}
			}else {
			 s1="NOTAVAILABLE";
			}		
		return s1;
	}
	
	
	public void postAppointmentPatDoc(Model model) {
		PatDocAppointModel patDocAppointModel = (PatDocAppointModel) model.asMap().get("patDocAppointModel");
		PatientDocAppoiEntity patientDocAppoiEntity=new PatientDocAppoiEntity();
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		List<PatientEntity> patient = userDAO.getPatientId((Integer) user.get(0)[0]);
		Date d1=DateConvertor.convertStringToDate(patDocAppointModel.getAppointmentDate(),DateConvertor.YYYYMMDD1);
		Date d2=DateConvertor.convertStringToDate(patDocAppointModel.getAppointmentDate()+" "+patDocAppointModel.getAppointmentTime(),DateConvertor.YYYYMMDDHHMISS1);

		List<PatientDocAppoiEntity> patientDocAppoiEntities= patDocAppointDAO.checkAppoinments(patient.get(0).getPatientId(),patDocAppointModel.getConsultingId(),d1);
		if(patientDocAppoiEntities.size() <= 0) {
				patDocAppointModel.setConfirmationNo(Long.toString(System.currentTimeMillis() / 1000L)+patDocAppointModel.getPatientId()+patDocAppointModel.getDoctorId());
				BeanUtils.copyProperties(patDocAppointModel, patientDocAppoiEntity);
				patientDocAppoiEntity.setPatientEntity(patient.get(0));
				DoctorEntity doctorEntity=doctorDAO.findByDoctorId(patDocAppointModel.getDoctorId());
				patientDocAppoiEntity.setDoctorEntity(doctorEntity);
				patientDocAppoiEntity.setAppointmentDate(d1);
				patientDocAppoiEntity.setAppointmentTime(d2);
				if ("VIDEO".equals(patDocAppointModel.getConsultingType())){
					 try {	
						 
					 			int 	s1=(patientDocAppoiEntities.size()*20);
						 		Calendar date = Calendar.getInstance();
						 		date.setTime(d2);
						 		long t= date.getTimeInMillis();
						 		Date afterAddingTenMins=new Date(t + (s1 * ONE_MINUTE_IN_MILLIS));
								patientDocAppoiEntity.setAppointmentTime(afterAddingTenMins);
								patientDocAppoiEntity.setAppointmentTime(d2);
						 		VideoEntity videoEntity=new VideoEntity();	 
								OpenTok opentok = new OpenTok(apiKey, apiSecret);
			
								Session session = opentok.createSession();
								String sessionId = session.getSessionId();
			
								String docToken = session.generateToken(new TokenOptions.Builder()
									  .data("name="+patDocAppointModel.getDoctorId())
									  .expireTime((System.currentTimeMillis() / 1000L) + (30 * 24 * 60 * 60)) // in one week
									  .build());
								String patToken = session.generateToken(new TokenOptions.Builder()
									  .data("name="+patDocAppointModel.getPatientId())
									  .expireTime((System.currentTimeMillis() / 1000L) + (30 * 24 * 60 * 60)) // in one week
									  .build());
			
								videoEntity.setApiKey(apiKey);
								Date dateVideo=new Date();
								videoEntity.setCreatedDate(dateVideo);
								videoEntity.setDocToken(docToken);
								videoEntity.setDoctorId(patDocAppointModel.getDoctorId());
								videoEntity.setPatToken(patToken);
								videoEntity.setPatientId(patDocAppointModel.getPatientId());
								videoEntity.setSessionId(sessionId);
								videoEntity.setPatStatus("N");
								videoEntity.setDocStatus("N");
								videoDAO.persist(videoEntity);
								patientDocAppoiEntity.setVideoId(videoEntity.getVideoId());
								patDocAppointDAO.persist(patientDocAppoiEntity);
								videoEntity.setAppointmentId(patientDocAppoiEntity.getAppointmentId());
								videoDAO.merge(videoEntity);
								model.addAttribute("id",patientDocAppoiEntity.getAppointmentId());
					 	}
							catch(Exception e) {
							e.printStackTrace();
						}
					
				}else {
					patDocAppointDAO.persist(patientDocAppoiEntity);
					model.addAttribute("id",patientDocAppoiEntity.getAppointmentId());
				}
				

		}

	}
	public void updateShareFlag(Integer appoinmentId,String shareProfileFlag) {		
		PatientDocAppoiEntity patientDocAppoiEntity=patDocAppointDAO.findById(PatientDocAppoiEntity.class, appoinmentId);
		if(patientDocAppoiEntity !=null) {
			patientDocAppoiEntity.setFollowUpFlag(shareProfileFlag);
			patDocAppointDAO.merge(patientDocAppoiEntity);
		}
			
	}
	public void updateRemindTime(Integer appoinmentId,String remindTime) {		
		PatientDocAppoiEntity patientDocAppoiEntity=patDocAppointDAO.findById(PatientDocAppoiEntity.class, appoinmentId);
		if(patientDocAppoiEntity !=null) {
			patientDocAppoiEntity.setRemindTime(remindTime);
			patDocAppointDAO.merge(patientDocAppoiEntity);
		}
			
	}
	
	/*public void getSuresh() {
		
		ChatEntity chatEntity=new ChatEntity();
		ChatMessages chatMessages=new ChatMessages();
		chatMessages.setSender("1");
		chatMessages.setText("I Love You");
		ChatMessages chatMessages1=new ChatMessages();
		chatMessages1.setSender("1");
		chatMessages1.setText("I Love You");
		
		List<ChatMessages> chatMessages2=new ArrayList<ChatMessages>();
		chatMessages2.add(chatMessages1);
		chatMessages2.add(chatMessages1);
		chatEntity.setChatType("fasdf");
		Date d1=new Date();
		chatEntity.setCreatedDate(d1);
		chatEntity.setDoctorId(12);
		chatEntity.setPatientId(23);
		chatEntity.setChatMessages(chatMessages2);
		chatDAO.persist(chatEntity);
		
	}*/
}
