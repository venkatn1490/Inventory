package com.medrep.app.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DeviceTokenDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorRegisteredCampaignDAO;
import com.medrep.app.dao.LocationDAO;
import com.medrep.app.dao.PatientDAO;
import com.medrep.app.dao.PatientRegisteredCampaignDAO;
import com.medrep.app.dao.PharmaCampStatsDAO;
import com.medrep.app.dao.PharmaCampaginDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorRegisteredCampaignsEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.PatientEntity;
import com.medrep.app.entity.PatientRegisteredCampaignEntity;
import com.medrep.app.entity.PharmaCampaginEntity;
import com.medrep.app.entity.PharmaCampaignStatsEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.model.DoctorRegisteredCampaign;
import com.medrep.app.model.Location;
import com.medrep.app.model.Mail;
import com.medrep.app.model.PatientAbout;
import com.medrep.app.model.PatientRegisteredCampaign;
import com.medrep.app.model.PharmaCampStats;
import com.medrep.app.model.PharmaCampaignModel;
import com.medrep.app.util.AndroidPushNotification;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.IosPushNotificationPatient;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.Util;

@Service("pharmaCampaginService")
@Transactional
public class PharmaCampaignService {
	
	@Autowired
	PatientDAO patientDAO;
	@Autowired
	DoctorDAO doctorDAO;
	
	@Autowired
	LocationDAO locationDAO;
	@Autowired
	PharmaRepDAO pharmaRepDAO;
	@Autowired 
	TherapeuticAreaDAO therapeuticAreaDAO;
	@Autowired
	PharmaCampaginDAO pharmacampaginDAO;
	@Autowired
	DeviceTokenDAO deviceTokenDAO;
	@Autowired
	CompanyDAO companyDAO;
	@Autowired
	EmailService  emailService;
	@Autowired
	PharmaCampStatsDAO pharmaCampStatsDAO;
	
	@Autowired
	DoctorRegisteredCampaignDAO doctorRegisteredCampaignDAO;
	
	@Autowired
	PatientRegisteredCampaignDAO patientRegisteredCampaignDAO;
	
	private static final Log log = LogFactory.getLog(PharmaCampaignService.class);
	
	public List<DoctorRegisteredCampaign>  getDoctorRegisterByCampId(Integer campaignId){
		List<DoctorRegisteredCampaign> me=new ArrayList<DoctorRegisteredCampaign>();
		
		List<DoctorRegisteredCampaignsEntity> doctorRegisteredCampaignsEntites=	doctorRegisteredCampaignDAO.getDoctorRegByCamginId(campaignId);
		
		for(DoctorRegisteredCampaignsEntity doctorRegisteredCampaignsEntity:doctorRegisteredCampaignsEntites ) {
			DoctorRegisteredCampaign doctorRegisteredCampaign=new DoctorRegisteredCampaign();
			BeanUtils.copyProperties(doctorRegisteredCampaignsEntity, doctorRegisteredCampaign,"pharmaCampaginEntity");			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (doctorRegisteredCampaignsEntity.getRegisteredDate() !=null)
				doctorRegisteredCampaign.setRegisteredDate(dateFormat.format(doctorRegisteredCampaignsEntity.getRegisteredDate()).toString());
			DoctorEntity doctorEntity=doctorDAO.findByDoctorId(doctorRegisteredCampaignsEntity.getDoctorId());
			
			if(doctorEntity.getUser()  != null)
			{
				doctorRegisteredCampaign.setDoctorName(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName() + " " + doctorEntity.getUser().getLastName());

			}
			doctorRegisteredCampaign.setDpImageUrl(doctorEntity.getUser().getDisplayPicture().getImageUrl());
			TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, Integer.parseInt(doctorEntity.getTherapeuticId()));
			if(therapeuticAreaEntity != null)
			{
				doctorRegisteredCampaign.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
			}
			doctorRegisteredCampaign.setNoofAppoinments(patientRegisteredCampaignDAO.findRegPatCountByCampAndDoct(campaignId,doctorEntity.getDoctorId()));
			me.add(doctorRegisteredCampaign);
		}

		
		return me;
	}
	public List<PatientRegisteredCampaign>  getPatRegByCampAndDocId(Integer doctorId,Integer campaignId){
		List<PatientRegisteredCampaign> me=new ArrayList<PatientRegisteredCampaign>();
		
		List<PatientRegisteredCampaignEntity> patientRegisteredCampaignEntities=	patientRegisteredCampaignDAO.findRegPatByCampAndDoct(campaignId,doctorId);
		
		for(PatientRegisteredCampaignEntity patientRegisteredCampaignEntity:patientRegisteredCampaignEntities ) {
			PatientRegisteredCampaign patientRegisteredCampaign=new PatientRegisteredCampaign();
			BeanUtils.copyProperties(patientRegisteredCampaignEntity, patientRegisteredCampaign,"pharmaCampaginEntity");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (patientRegisteredCampaign.getRegisteredDate() !=null)
				patientRegisteredCampaign.setRegisteredDate(dateFormat.format(patientRegisteredCampaignEntity.getRegisteredDate()).toString());

			PatientEntity we=patientDAO.findById(PatientEntity.class,patientRegisteredCampaignEntity.getPatientId());
			
			if(we.getUser()  != null)
			{
				patientRegisteredCampaign.setPatientName((we.getUser().getFirstName() == null ? "" : we.getUser().getFirstName())+" "+(we.getUser().getLastName() == null ? "" : we.getUser().getLastName()));
				patientRegisteredCampaign.setDpImageUrl(we.getUser().getDisplayPicture().getImageUrl());
			}
			
			me.add(patientRegisteredCampaign);
		}

		
		return me;
	}
	
	
	
	
	public List<PharmaCampaignModel> getAdminCampaginList() throws MedrepException {
		List<PharmaCampaignModel> valObjs = new ArrayList<PharmaCampaignModel>();
		try {
			List<PharmaCampaginEntity> list = pharmacampaginDAO.getAdminCampaginList();
			Map<String, String> cmap = getAllCompanys();

			for (PharmaCampaginEntity entity : list) {
				PharmaCampaignModel pharmaCampagin = BeanUtils.instantiateClass(PharmaCampaignModel.class);
				BeanUtils.copyProperties(entity, pharmaCampagin);
				Date d1 = entity.getCampaignStartDate();
				if (d1 != null)
					pharmaCampagin.setCampaignStartDate(d1.toString());
				Date d2 = entity.getCampaignEndDate();
				if (d2 != null)
					pharmaCampagin.setCampaignEndDate(d2.toString());
				if(pharmaCampagin.getCompanyId()!=null) {
					if ( pharmaCampagin.getCompanyId() != null && ! pharmaCampagin.getCompanyId().equals("0"))
						pharmaCampagin.setCompanyName(cmap.get(String.valueOf( pharmaCampagin.getCompanyId())));
					else
						pharmaCampagin.setCompanyName("");
				}		
				if(pharmaCampagin.getTherapeuticId()!=null) {
					Integer tid = Integer.parseInt(pharmaCampagin.getTherapeuticId());
					if (tid != null) {
						TherapeuticAreaEntity tentity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, tid);
						if (tentity != null) {
							pharmaCampagin.setTherapeuticName(tentity.getTherapeuticName());
						}
					}
				}
				valObjs.add(pharmaCampagin);
			}
		} catch (Exception e) {
			throw new MedrepException("Error while getting Notification List " + e.getMessage());
		}
		return valObjs;
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
	public void createPharmacampaign(Model model) throws MedrepException {
		try {
			PharmaCampaignModel pharmaCampaignModel=(PharmaCampaignModel) model.asMap().get("pharmaCampaignModel");
			
			PharmaCampaginEntity pharmaCampaginEntity=new PharmaCampaginEntity();
			pharmaCampaginEntity.setCampaignDetails(pharmaCampaignModel.getCampaignDetails());
			pharmaCampaginEntity.setCampaignTitle(pharmaCampaignModel.getCampaignTitle());
			pharmaCampaginEntity.setCompanyId(pharmaCampaignModel.getCompanyId());
			pharmaCampaginEntity.setCampaignStartDate(pharmaCampaignModel.getCampaignStartDateFormat());
			pharmaCampaginEntity.setCampaignEndDate(pharmaCampaignModel.getCampaignEndDateFormat());
			pharmaCampaginEntity.setTherapeuticId(pharmaCampaignModel.getTherapeuticId());
			pharmaCampaginEntity.setTargetState(pharmaCampaignModel.getTargetState());
			pharmaCampaginEntity.setTargetCity(pharmaCampaignModel.getTargetCity());
			pharmaCampaginEntity.setTargetArea(pharmaCampaignModel.getTargetArea());
			pharmaCampaginEntity.setAddress(pharmaCampaignModel.getAddress());
			pharmaCampaginEntity.setCompanyEmail(pharmaCampaignModel.getCompanyEmail());
			pharmaCampaginEntity.setContactPersonName(pharmaCampaignModel.getContactPersonName());
			pharmaCampaginEntity.setContactPersonPhone(pharmaCampaignModel.getContactPersonPhone());
			pharmaCampaginEntity.setContactPersonEmail(pharmaCampaignModel.getContactPersonEmail());
			pharmaCampaginEntity.setPrice(pharmaCampaignModel.getPrice());
			pharmaCampaginEntity.setRequestSample(pharmaCampaignModel.getRequestSample());
			pharmaCampaginEntity.setStatus("NOTPUBLISH");
			pharmaCampaginEntity.setDoctorStatus("NOTPUBLISH");
			pharmaCampaginEntity.setPatientStatus("NOTPUBLISH");
			MultipartFile displayPic=(MultipartFile) model.asMap().get("campgainImage");
			if (displayPic != null) {
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/hospitals/";
				_displayPic += FileUtil.copyImage(displayPic,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"hospitals");
				pharmaCampaginEntity.setCampaignImageUrl(_displayPic);
			}
			MultipartFile displayLogoPic=(MultipartFile) model.asMap().get("campgainLogo");
			if (displayLogoPic != null) {
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/pharmaCampaign/";
				_displayPic += FileUtil.copyImage(displayLogoPic,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"pharmaCampaign");
				pharmaCampaginEntity.setCampaignThumdNailUrl(_displayPic);
			}
			pharmacampaginDAO.persist(pharmaCampaginEntity);
		} catch (Exception e) {
			throw new MedrepException("Error while creating Notification " + e.getMessage());
		}
	}
	
	public Boolean publishCampaign(PharmaCampStats pharmaCampStats) {
		if("patient".equals(pharmaCampStats.getAudienceType())) {
			
			PharmaCampaginEntity pharmaCampaginEntity=pharmacampaginDAO.getCampaignById(Integer.parseInt(pharmaCampStats.getCampaignID()));
			pharmaCampaginEntity.setStatus("Publish");
			pharmaCampaginEntity.setPatientStatus("Publish");
			pharmacampaginDAO.merge(pharmaCampaginEntity);
			List<PatientEntity> patientEntity=patientDAO.findbyLocationAndThreId(pharmaCampStats.getCity(),pharmaCampStats.getThrepeauticId());
			return publishPatientNotifications(pharmaCampaginEntity, patientEntity);
		}else {
			PharmaCampaginEntity pharmaCampaginEntity=pharmacampaginDAO.getCampaignById(Integer.parseInt(pharmaCampStats.getCampaignID()));
			pharmaCampaginEntity.setDoctorStatus("Publish");
			pharmaCampaginEntity.setStatus("Publish");
			pharmacampaginDAO.merge(pharmaCampaginEntity);
			List<DoctorEntity> doctorEntites=doctorDAO.findbyLocationAndThreId(pharmaCampStats.getCity(),pharmaCampStats.getThrepeauticId());
			return publishDoctorNotifications(pharmaCampaginEntity, doctorEntites);			
		}
		
	}

	public Boolean publishDoctorNotifications(PharmaCampaginEntity pharmaCampaginEntity,List<DoctorEntity> doctorEntities){
		ArrayList<DeviceTokenEntity> devicesList = new ArrayList<DeviceTokenEntity>();
		boolean flag = false;
		try
		{
			for (DoctorEntity doctorEntity : doctorEntities)
				{
					if(!"Active".equalsIgnoreCase(doctorEntity.getStatus()))
							continue;
					PharmaCampaignStatsEntity pharmaCampaignStatsEntity = pharmaCampStatsDAO.findByDoctorCampaign(doctorEntity.getDoctorId(), pharmaCampaginEntity.getCampaignId());
					if( pharmaCampaignStatsEntity != null )
					{
						flag = true;
					}
					else{
						pharmaCampaignStatsEntity = new PharmaCampaignStatsEntity();
						pharmaCampaignStatsEntity.setSentDate(new Date());
						pharmaCampaignStatsEntity.setDoctorId(doctorEntity.getDoctorId());
						pharmaCampaignStatsEntity.setPharmaCampaginEntity(pharmaCampaginEntity);
						pharmaCampaignStatsEntity.setStatus("UnRegistered");
						pharmaCampStatsDAO.merge(pharmaCampaignStatsEntity);
						flag = true;
					}					
				}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return flag;

	}
	
	public Boolean publishPatientNotifications(PharmaCampaginEntity pharmaCampaginEntity,List<PatientEntity> patientEntities){
		ArrayList<DeviceTokenEntity> devicesList = new ArrayList<DeviceTokenEntity>();
		boolean flag = false;
		try
		{
			if(patientEntities != null) {
				for (PatientEntity patientEntity: patientEntities)
				{
					/*if(!"Active".equalsIgnoreCase(patientEntity.getStatus()))
							continue;*/
					PharmaCampaignStatsEntity pharmaCampaignStatsEntity = pharmaCampStatsDAO.findByPatientCampaign(patientEntity.getPatientId(), pharmaCampaginEntity.getCampaignId());
					if( pharmaCampaignStatsEntity != null)
					{
						
						flag = true;
					}
					else{
						pharmaCampaignStatsEntity = new PharmaCampaignStatsEntity();
						pharmaCampaignStatsEntity.setSentDate(new Date());
						pharmaCampaignStatsEntity.setPatientId(patientEntity.getPatientId());
						pharmaCampaignStatsEntity.setPharmaCampaginEntity(pharmaCampaginEntity);
						pharmaCampaignStatsEntity.setStatus("UnRegistered");
						pharmaCampStatsDAO.merge(pharmaCampaignStatsEntity);
						flag = true;
					}					
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return flag;

	}
	
	
	public List<PharmaCampStats> getDoctorCampaigns(String loginId){
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		List<PharmaCampaignStatsEntity> pharmaCampaignStatsEntity = pharmaCampStatsDAO.findByDoctorId(doctorEntity.getDoctorId());
		List<PharmaCampStats> pharmaCampStatsModel = new ArrayList<PharmaCampStats>();

		for (PharmaCampaignStatsEntity pharmaCampStatsEntites : pharmaCampaignStatsEntity ) {
			
			PharmaCampStats pharmaCampStats = BeanUtils.instantiateClass(PharmaCampStats.class);
			BeanUtils.copyProperties(pharmaCampStatsEntites, pharmaCampStats);
			if(pharmaCampStatsEntites.getPharmaCampaginEntity() !=null) {
				
				if(pharmaCampStatsEntites.getPharmaCampaginEntity().getCompanyId() !=null) {
					pharmaCampStats.setComapnyName(companyDAO.findByCompanyId((pharmaCampStatsEntites.getPharmaCampaginEntity().getCompanyId())).getCompanyName());
				}
				if(pharmaCampStatsEntites.getPharmaCampaginEntity().getTherapeuticId() !=null) {
					
					pharmaCampStats.setThrepeauticName(therapeuticAreaDAO.findById(Integer.parseInt(pharmaCampStatsEntites.getPharmaCampaginEntity().getTherapeuticId())).getTherapeuticName());

				}
			}
			/*DoctorRegisteredCampaignsEntity doctorRegisteredCampaignsEntity=doctorRegisteredCampaignDAO.findByDocAndCampId(doctorEntity.getDoctorId(),pharmaCampStatsEntites.getPharmaCampaginEntity().getCampaignId());
			if (doctorRegisteredCampaignsEntity !=null)
				pharmaCampStats.setStatus("Registered");
			else
				pharmaCampStats.setStatus("UnRegistered");*/
			
			
			pharmaCampStatsModel.add(pharmaCampStats);		
		}
		
		
		
		return pharmaCampStatsModel;
	}
	public List<PharmaCampaignModel> getCompanyCampaigns(String loginId){
		PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);
		List<PharmaCampaginEntity> pharmaCampaginEntites = pharmacampaginDAO.getCampaignByCompanyId(pharmaRepEntity.getCompanyId());

		List<PharmaCampaignModel> pharmaCampaignModels = new ArrayList<PharmaCampaignModel>();

		for (PharmaCampaginEntity pharmaCampaginEntity : pharmaCampaginEntites ) {
			
			PharmaCampaignModel pharmaCampaignModel = BeanUtils.instantiateClass(PharmaCampaignModel.class);
			BeanUtils.copyProperties(pharmaCampaginEntity, pharmaCampaignModel);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			pharmaCampaignModel.setCampaignStartDate(dateFormat.format(pharmaCampaginEntity.getCampaignStartDate()).toString());
			pharmaCampaignModel.setCampaignEndDate(dateFormat.format(pharmaCampaginEntity.getCampaignEndDate()).toString());

			if(pharmaCampaginEntity.getTherapeuticId() !=null) {
				
				pharmaCampaignModel.setTherapeuticName(therapeuticAreaDAO.findById(Integer.parseInt(pharmaCampaginEntity.getTherapeuticId())).getTherapeuticName());

			}
	
			pharmaCampaignModels.add(pharmaCampaignModel);		
		}
	
		return pharmaCampaignModels;
	}
		
	
	public List<PharmaCampStats> getPatientCampaigns(String loginId){
		PatientEntity patientEntity = patientDAO.findByLoginId(loginId);
		List<PharmaCampaignStatsEntity> PharmaCampaignStatsEntity = pharmaCampStatsDAO.findByPatientId(patientEntity.getPatientId());
		List<PharmaCampStats> pharmaCampStatsModel = new ArrayList<PharmaCampStats>();

		for (PharmaCampaignStatsEntity pharmaCampStatsEntites : PharmaCampaignStatsEntity ) {
			
			PharmaCampStats pharmaCampStats = BeanUtils.instantiateClass(PharmaCampStats.class);
			BeanUtils.copyProperties(pharmaCampStatsEntites, pharmaCampStats);
			if(pharmaCampStatsEntites.getPharmaCampaginEntity() !=null) {
				
				if(pharmaCampStatsEntites.getPharmaCampaginEntity().getCompanyId() !=null) {
					pharmaCampStats.setComapnyName(companyDAO.findByCompanyId((pharmaCampStatsEntites.getPharmaCampaginEntity().getCompanyId())).getCompanyName());
				}
				if(pharmaCampStatsEntites.getPharmaCampaginEntity().getTherapeuticId() !=null) {
					
					pharmaCampStats.setThrepeauticName(therapeuticAreaDAO.findById(Integer.parseInt(pharmaCampStatsEntites.getPharmaCampaginEntity().getTherapeuticId())).getTherapeuticName());

				}
			}
		/*	PatientRegisteredCampaignEntity patientRegisteredCampaignEntity=patientRegisteredCampaignDAO.findByPatientAndCampId(patientEntity.getPatientId(),pharmaCampStatsEntites.getPharmaCampaginEntity().getCampaignId());
			if (patientRegisteredCampaignEntity !=null)
				pharmaCampStats.setStatus("Registered");
			else
				pharmaCampStats.setStatus("UnRegistered");*/
			
			pharmaCampStatsModel.add(pharmaCampStats);		
		}
		
		
		
		return pharmaCampStatsModel;
	}
	public List<DoctorRegisteredCampaign> getRegisteredDoctors(Integer campaignId){
		List<DoctorRegisteredCampaignsEntity> doctorRegisteredCampaignsEntity = doctorRegisteredCampaignDAO.findByCampId(campaignId);
		List<DoctorRegisteredCampaign> doctorRegisteredCampaigns = new ArrayList<DoctorRegisteredCampaign>();

		for (DoctorRegisteredCampaignsEntity doctorRegisteredCampaignsEntites : doctorRegisteredCampaignsEntity ) {
			
			DoctorRegisteredCampaign doctorRegisteredCampaign = BeanUtils.instantiateClass(DoctorRegisteredCampaign.class);
			BeanUtils.copyProperties(doctorRegisteredCampaignsEntites, doctorRegisteredCampaign,"pharmaCampaginEntity");
			DoctorEntity doctorEntity =doctorDAO.findByDoctorId(doctorRegisteredCampaignsEntites.getDoctorId());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			doctorRegisteredCampaign.setRegisteredDate(dateFormat.format(doctorRegisteredCampaignsEntites.getRegisteredDate()).toString());
			doctorRegisteredCampaign.setCampaignId(doctorRegisteredCampaignsEntites.getPharmaCampaginEntity().getCampaignId());
			if(doctorRegisteredCampaignsEntites.getAddressId() != null) {
				LocationEntity localEntity=locationDAO.findById(LocationEntity.class,doctorRegisteredCampaignsEntites.getAddressId());
			
				if(localEntity != null) {
					Location location = BeanUtils.instantiateClass(Location.class);
					BeanUtils.copyProperties(localEntity, location);
					doctorRegisteredCampaign.setLocation(location);
				}
			}
			doctorRegisteredCampaign.setDoctorName((doctorEntity.getUser().getFirstName() == null ? "" : doctorEntity.getUser().getFirstName())+" "+(doctorEntity.getUser().getLastName() == null ? "" : doctorEntity.getUser().getLastName()));
			if(doctorEntity.getTherapeuticId() !=null) {
				
				doctorRegisteredCampaign.setTherapeuticName(therapeuticAreaDAO.findById(Integer.parseInt(doctorEntity.getTherapeuticId() )).getTherapeuticName());

			}
			doctorRegisteredCampaign.setDpImageUrl(doctorEntity.getUser().getDisplayPicture().getImageUrl());
			doctorRegisteredCampaign.setExperienceNo(doctorEntity.getWorkExpYears()+"."+doctorEntity.getWorkExpMonths());
			doctorRegisteredCampaigns.add(doctorRegisteredCampaign);		
		}
		
		
		
		return doctorRegisteredCampaigns;
	}
	
	public void getPatientRegisteredCampaigns(Model model){
		String username = (String) model.asMap().get("username");

		Map<String,Object> patientRegistedInfo=new HashMap<String,Object>();

		PatientEntity patientEntity = patientDAO.findByLoginId(username);
		
		List<PatientRegisteredCampaignEntity> patientRegisteredCampaignEntity=patientRegisteredCampaignDAO.findRegisteredCampaign(patientEntity.getPatientId());
		List<PatientRegisteredCampaign> previousAppoinment=new ArrayList<PatientRegisteredCampaign>();
		List<PatientRegisteredCampaign> upcomingAppoinment=new ArrayList<PatientRegisteredCampaign>();

		Date date = new Date();

		for (PatientRegisteredCampaignEntity prce : patientRegisteredCampaignEntity ) {
			if(prce.getRegisteredDate().compareTo(date) >=0) {
				PatientRegisteredCampaign patientRegisteredCampaign = BeanUtils.instantiateClass(PatientRegisteredCampaign.class);
				BeanUtils.copyProperties(prce, patientRegisteredCampaign);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				patientRegisteredCampaign.setRegisteredDate(dateFormat.format(prce.getRegisteredDate()).toString());
				DoctorEntity de=doctorDAO.findById(DoctorEntity.class,prce.getDoctorId());
				if(de.getUser()  != null)
					{
						patientRegisteredCampaign.setDoctorName((de.getUser().getFirstName() == null ? "" : de.getUser().getFirstName())+" "+(de.getUser().getLastName() == null ? "" : de.getUser().getLastName()));
						patientRegisteredCampaign.setDpImageUrl(de.getUser().getDisplayPicture().getImageUrl());
						if(de.getTherapeuticId() != null) {
							
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(de.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								patientRegisteredCampaign.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
							
						}
					}
				upcomingAppoinment.add(patientRegisteredCampaign);		
			}
			else {
				PatientRegisteredCampaign patientRegisteredCampaign = BeanUtils.instantiateClass(PatientRegisteredCampaign.class);
				BeanUtils.copyProperties(prce, patientRegisteredCampaign);
				//patientRegisteredCampaign.setRegisteredDate(prce.getRegisteredDate().toString());
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				patientRegisteredCampaign.setRegisteredDate(dateFormat.format(prce.getRegisteredDate()).toString());
				
				DoctorEntity de=doctorDAO.findById(DoctorEntity.class,prce.getDoctorId());
				if(de.getUser()  != null)
					{
						patientRegisteredCampaign.setDoctorName((de.getUser().getFirstName() == null ? "" : de.getUser().getFirstName())+" "+(de.getUser().getLastName() == null ? "" : de.getUser().getLastName()));
						patientRegisteredCampaign.setDpImageUrl(de.getUser().getDisplayPicture().getImageUrl());
						if(de.getTherapeuticId() != null) {
							
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(de.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								patientRegisteredCampaign.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
							
						}
						
					}
				previousAppoinment.add(patientRegisteredCampaign);	
			   }
			}
		
		patientRegistedInfo.put("previous", previousAppoinment);
		patientRegistedInfo.put("upcoming", upcomingAppoinment);	
		model.addAttribute("patientRegistedInfo",patientRegistedInfo);
	}
	public void getDoctorRegisteredCampaigns(Model model){
		String username = (String) model.asMap().get("username");

		Map<String,Object> doctorRegistedInfo=new HashMap<String,Object>();
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(username);
		
		List<PatientRegisteredCampaign> previousAppoinment=new ArrayList<PatientRegisteredCampaign>();
		List<PatientRegisteredCampaign> upcomingAppoinment=new ArrayList<PatientRegisteredCampaign>();
		
		List<DoctorRegisteredCampaignsEntity> doctorRegisteredCampaignsEntity=doctorRegisteredCampaignDAO.findRegisteredCampaign(doctorEntity.getDoctorId());

		
		Date date = new Date();

		for (DoctorRegisteredCampaignsEntity drce : doctorRegisteredCampaignsEntity ) {
			
			List<PatientRegisteredCampaignEntity> patientRegisteredCampaignEntites=patientRegisteredCampaignDAO.findRegisteredCampaignByDocIdandCampId(drce.getDoctorId(),drce.getPharmaCampaginEntity().getCampaignId());
			if(patientRegisteredCampaignEntites !=null) {
						for (PatientRegisteredCampaignEntity prce : patientRegisteredCampaignEntites ) {
			
						if(prce.getRegisteredDate().compareTo(date) >=0) {
							PatientRegisteredCampaign patientRegisteredCampaign = BeanUtils.instantiateClass(PatientRegisteredCampaign.class);
							BeanUtils.copyProperties(prce, patientRegisteredCampaign);
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							patientRegisteredCampaign.setRegisteredDate(dateFormat.format(drce.getRegisteredDate()).toString());
							if (prce.getTherapeuticId() != null) {
								TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,prce.getTherapeuticId());
								if(therapeuticAreaEntity != null)
								{
									patientRegisteredCampaign.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
								}
							}
							PatientEntity pe=patientDAO.findById(PatientEntity.class,prce.getPatientId());
							if(pe.getUser()  != null)
								{
									patientRegisteredCampaign.setDoctorName((pe.getUser().getFirstName() == null ? "" : pe.getUser().getFirstName())+" "+(pe.getUser().getLastName() == null ? "" : pe.getUser().getLastName()));
									patientRegisteredCampaign.setDpImageUrl(pe.getUser().getDisplayPicture().getImageUrl());
								}
							upcomingAppoinment.add(patientRegisteredCampaign);	
						}
							else {
								PatientRegisteredCampaign patientRegisteredCampaign = BeanUtils.instantiateClass(PatientRegisteredCampaign.class);
								BeanUtils.copyProperties(prce, patientRegisteredCampaign);
								DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
								patientRegisteredCampaign.setRegisteredDate(dateFormat.format(drce.getRegisteredDate()).toString());
								if (prce.getTherapeuticId() != null) {

									TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,prce.getTherapeuticId());
									if(therapeuticAreaEntity != null)
									{
										patientRegisteredCampaign.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
									}
								}
								PatientEntity pe=patientDAO.findById(PatientEntity.class,prce.getPatientId());
								if(pe.getUser()  != null)
									{
										patientRegisteredCampaign.setPatientName((pe.getUser().getFirstName() == null ? "" : pe.getUser().getFirstName())+" "+(pe.getUser().getLastName() == null ? "" : pe.getUser().getLastName()));
										patientRegisteredCampaign.setDpImageUrl(pe.getUser().getDisplayPicture().getImageUrl());
									}
								previousAppoinment.add(patientRegisteredCampaign);	
							}
						}
					}
					
			}	
					
					doctorRegistedInfo.put("previous", previousAppoinment);
					doctorRegistedInfo.put("upcoming", upcomingAppoinment);	
					model.addAttribute("doctorRegistedInfo",doctorRegistedInfo);
	}
	
	
	
	public void doctorRegisterCampaign(Model model) {
		
		DoctorRegisteredCampaign doctorRegisteredCampaign = (DoctorRegisteredCampaign) model.asMap().get("doctorRegisteredCampaign");
		DoctorRegisteredCampaignsEntity doctorRegisteredCampaignsEntity=new DoctorRegisteredCampaignsEntity();
		String loginId = (String) model.asMap().get("loginId");
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		PharmaCampaginEntity pharmaCampaginEntity=pharmacampaginDAO.getCampaignById(doctorRegisteredCampaign.getCampaignId());
		doctorRegisteredCampaign.setDoctorId(doctorEntity.getDoctorId());
		BeanUtils.copyProperties(doctorRegisteredCampaign, doctorRegisteredCampaignsEntity);
		doctorRegisteredCampaignsEntity.setPharmaCampaginEntity(pharmaCampaginEntity);
		doctorRegisteredCampaignsEntity.setRegisteredDate(DateConvertor.convertStringToDate(doctorRegisteredCampaign.getRegisteredDate(),DateConvertor.YYYYMMDD1));
		doctorRegisteredCampaignDAO.persist(doctorRegisteredCampaignsEntity);
		PharmaCampaignStatsEntity pharmaCampaignStatsEntity=pharmaCampStatsDAO.findByDoctorCampaign(doctorRegisteredCampaignsEntity.getDoctorId(),doctorRegisteredCampaign.getCampaignId() );
		if (pharmaCampaignStatsEntity !=null){
			pharmaCampaignStatsEntity.setStatus("Registered");
			pharmaCampStatsDAO.merge(pharmaCampaignStatsEntity);
		}
		
	}
	public void patientRegisterCampaign(Model model) {
		PatientRegisteredCampaign patientRegisteredCampaign = (PatientRegisteredCampaign) model.asMap().get("patientRegisteredCampaign");
		PatientRegisteredCampaignEntity patientRegisteredCampaignEntity=new PatientRegisteredCampaignEntity();
		String loginId = (String) model.asMap().get("loginId");
		PatientEntity patientEntity = patientDAO.findByLoginId(loginId);
		PharmaCampaginEntity pharmaCampaginEntity=pharmacampaginDAO.getCampaignById(patientRegisteredCampaign.getCampaignId());
		patientRegisteredCampaign.setPatientId(patientEntity.getPatientId());
		BeanUtils.copyProperties(patientRegisteredCampaign, patientRegisteredCampaignEntity);
		patientRegisteredCampaignEntity.setPharmaCampaginEntity(pharmaCampaginEntity);
		patientRegisteredCampaignEntity.setRegisteredDate(DateConvertor.convertStringToDate(patientRegisteredCampaign.getRegisteredDate(),DateConvertor.YYYYMMDD2));
		patientRegisteredCampaignDAO.persist(patientRegisteredCampaignEntity);
		PharmaCampaignStatsEntity pharmaCampaignStatsEntity=pharmaCampStatsDAO.findByPatientCampaign(patientEntity.getPatientId(),patientRegisteredCampaign.getCampaignId() );
		if (pharmaCampaignStatsEntity !=null){
			pharmaCampaignStatsEntity.setStatus("Registered");
			pharmaCampStatsDAO.merge(pharmaCampaignStatsEntity);
		}
		

	}
	
	public  Map<String,Object> getCampaignCount(Integer campaignId)
	{
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("doctorRegisteredCampaignNo", doctorRegisteredCampaignDAO.findRegisteredCountByCampaignId(campaignId).get(0));
		map.put("patientRegisteredCampaignNo", patientRegisteredCampaignDAO.findRegisteredCountByCampaignId(campaignId).get(0));


		map.put("patientSent",pharmaCampStatsDAO.findPatSentPatCountByCampaignId(campaignId).get(0));
        map.put("doctorSent",pharmaCampStatsDAO.findDocSentPatCountByCampaignId(campaignId).get(0));		
       	
		return map;
	}
	
	public void sendSampleMail(Integer campaignId,String loginId) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		String[] mailList=new String[3];
		Map<String, String> valueMap = new HashMap<String, String>();

		
		if(doctorEntity!=null) {
			valueMap.put("doctorName", (doctorEntity.getUser().getFirstName())+"  "+(doctorEntity.getUser().getLastName()));
			mailList[0]=doctorEntity.getUser().getEmailId();
		}
		PharmaCampaginEntity pharmaCampaginEntity=pharmacampaginDAO.findById(PharmaCampaginEntity.class,campaignId);
		if(pharmaCampaginEntity != null) {
			if(pharmaCampaginEntity.getContactPersonEmail() != null)
				mailList[1]=pharmaCampaginEntity.getContactPersonEmail();	
		}
		mailList[2]=MedRepProperty.getInstance().getProperties("medrep.email.info.id");

		CompanyEntity companyEntity=companyDAO.findByCompanyId(pharmaCampaginEntity.getCompanyId());
		if(companyEntity!=null) {
			mailList[3]=doctorEntity.getUser().getEmailId();
		}
		
		Mail mail = new Mail();
		mail.setMultipleMailTo(mailList);
		mail.setTemplateName("Sample Requested by Doctor [Campaign: "+ (pharmaCampaginEntity.getCampaignTitle()));
	//	mail.setMailTo("naga.suresh432@gmail.com");
		valueMap.put("campaginName", pharmaCampaginEntity.getCampaignTitle());
		valueMap.put("ContactName", pharmaCampaginEntity.getContactPersonName());

		
		valueMap.put("MobileNumber", pharmaCampaginEntity.getContactPersonPhone());
		valueMap.put("EmailAddress", pharmaCampaginEntity.getContactPersonEmail());

		mail.setValueMap(valueMap);
		emailService.sendMultipleMail(mail);
	}
	
	
	public List<PatientAbout> getListofUntreatedPatient(Integer campId){
		List<PatientAbout> patientAbout=new ArrayList<PatientAbout>();
		List<Integer> patientId=pharmaCampStatsDAO.getDistinctUntreatedPatients(campId);
		for(Integer id : patientId) {
			PatientEntity patientEntity=patientDAO.findById(PatientEntity.class, id);
			if(patientEntity != null) {
				PatientAbout patient=new PatientAbout();
				if(patientEntity.getUser().getDisplayPicture() != null) {
					patient.setDpUrl(patientEntity.getUser().getDisplayPicture().getImageUrl());
				}	
				patient.setName((patientEntity.getUser().getFirstName())+" "+(patientEntity.getUser().getLastName()));
				patient.setId(patientEntity.getPatientId());
				patient.setMobileNo(patientEntity.getUser().getMobileNo());
				patientAbout.add(patient);
			}	
				
		}
		return patientAbout;
	}
	
	
	
	public List<DeviceTokenEntity>  sendPushNotfUnTreatedPatient(PharmaCampStats pharmaCampStats) {

		List<PatientEntity> patList = new ArrayList();

		List<DeviceTokenEntity> devicesList = new ArrayList<DeviceTokenEntity>();
		 	
		for (Integer patId : pharmaCampStats.getPatientIds()) {

			PharmaCampaignStatsEntity pharmaCampaignStatsEntity =pharmaCampStatsDAO.findByCampaginByPatient( pharmaCampStats.getCampaignID(),patId);
			if(pharmaCampaignStatsEntity != null) {
				Calendar c=Calendar.getInstance();
				c.setTime(pharmaCampaignStatsEntity.getSentDate());
			 	c.add(Calendar.DATE,7);
			 	if(c.getTime().compareTo(new Date())<0){
			 		System.out.println("It's more than 7 days.");
			 		pharmaCampaignStatsEntity.setSentDate(new Date());
			 		pharmaCampStatsDAO.merge(pharmaCampaignStatsEntity);
					List<DeviceTokenEntity> devices=deviceTokenDAO.findBySinglePatientId(patId);
					if(!Util.isEmpty(devices))
						devicesList.add(devices.get(0));
			 		
			 	}
			}
		 	
		
		}
		return devicesList;

	}
	@Async
	public void pushMessage(String message, List<DeviceTokenEntity> devicesList) {
		pushMessage(message, devicesList,null);
	}
	
	@Async
	public void pushMessage(String message, List<DeviceTokenEntity> devicesList,CompanyEntity company) {
		if (!Util.isEmpty(devicesList))
			for (DeviceTokenEntity d : devicesList) {
				try {
					if ("IOS".equalsIgnoreCase(d.getPlatform())) {
						IosPushNotificationPatient.pushMessage(d.getDeviceToken(), message,company);
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
	
	public String getCampaignName(String campId) {
		
		String message="";
		PharmaCampaginEntity pharmaCampaginEntity =pharmacampaginDAO.findById(PharmaCampaginEntity.class, Integer.parseInt(campId));
		CompanyEntity companyEntity=companyDAO.findByCompanyId(pharmaCampaginEntity.getCompanyId());
		if(!Util.isEmpty(companyEntity))
			 message+= companyEntity.getCompanyName();
		message+=" have a new campaign ";
		if(!Util.isEmpty(pharmaCampaginEntity))
			 message+= pharmaCampaginEntity.getCampaignTitle();
		message+=". Please click to know more.";
		
		return message;
	}
}
