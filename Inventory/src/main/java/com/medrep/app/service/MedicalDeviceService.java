package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.LocationDAO;
import com.medrep.app.dao.MedicalDeviceDao;
import com.medrep.app.dao.OrderMedicalDao;
import com.medrep.app.dao.PharmaTherapeuticsDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DisplayPictureEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.MedicalDeviceEntity;
import com.medrep.app.entity.OrderMedicalDeviceEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.Company;
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.Location;
import com.medrep.app.model.MedicalDevicesModel;
import com.medrep.app.model.OrderMedicalDevice;
import com.medrep.app.model.OrderMedicalDevice_res;
import com.medrep.app.model.TherapeuticArea;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;


@Service("medicaldeviceservice")
@Transactional
public class MedicalDeviceService {
	
	@Autowired
	DoctorDAO doctorDAO;
	@Autowired
	DisplayPictureDAO displayPictureDAO;
	
	@Autowired 
	CompanyDAO companyDAO;

	@Autowired
	PharmaTherapeuticsDAO pharmaTherapeuticsDAO;
	
	@Autowired
	MedicalDeviceDao medicalDeviceDao;
	
	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;
	
	@Autowired
	OrderMedicalDao orderMedicalDAO;
	
	@Autowired
	
	LocationDAO locationDAO;
	
	
	
	
	public void alterDeviceStatusByDeviceId(Integer companyId, String status)
	{
		
		MedicalDeviceEntity medicaldeviceentity  = medicalDeviceDao.findById(MedicalDeviceEntity.class,companyId);
		medicaldeviceentity.setStatus(status);
		medicalDeviceDao.merge(medicaldeviceentity);
	}
	
	
	
	
	public List<MedicalDevicesModel> getAllDevices()
	{
	List<MedicalDeviceEntity> list = medicalDeviceDao.getDeviceList();
		List<MedicalDevicesModel> modelList = new ArrayList<MedicalDevicesModel>();
		for(MedicalDeviceEntity entity : list) {
			MedicalDevicesModel model = BeanUtils.instantiateClass(MedicalDevicesModel.class);
			BeanUtils.copyProperties(entity, model, "createdOn");
			if(entity.getCreatedOn()!= null)
					model.setCreatedOn(entity.getCreatedOn().toString());
			if(entity.getCompanyId()!=null)
			{
				CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,Integer.parseInt(entity.getCompanyId()));
				
				if(null!=companyEntity)
				{
					model.setCompanyName(companyEntity.getCompanyName());
					model.setCompanyUrl(companyEntity.getCompanyUrl());
				}
				
			}
			TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(entity.getTherapeuticId()));
			model.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");
			
			//setNames(model);
			modelList.add(model);
		}
		return modelList;
		
	}
	
	private void setNames(MedicalDevicesModel model) {
		try {
			TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,model.getTherapeuticId());
			model.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");
			
			CompanyEntity companyEntity=companyDAO.findById(CompanyEntity.class, model.getCompanyId());
			model.setCompanyName(companyEntity!=null?companyEntity.getCompanyName():"");
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public MedicalDevicesModel fetchMedicalDeviceById(String Id)
	{
		Set<TherapeuticAreaEntity> therapeuticAreaEntities = new HashSet<TherapeuticAreaEntity>();
		MedicalDeviceEntity medicalDeviceEntity = medicalDeviceDao.findById(MedicalDeviceEntity.class, Integer.parseInt(Id));
		MedicalDevicesModel medicalDevicesModel = null;
		CompanyEntity companyEntity=null;
		if(medicalDeviceEntity != null)
		{
			 medicalDevicesModel = new MedicalDevicesModel();
			/*if(medicalDeviceEntity.getDpId() != null)
			{
				DisplayPictureEntity displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,medicalDeviceEntity.getDpId());
				if(displayPictureEntity != null)
				{
					DisplayPicture displayPicture = new DisplayPicture();
					displayPicture.setDpId(displayPictureEntity.getDpId());
					displayPicture.setdPicture(displayPictureEntity.getImageUrl());
					medicalDevicesModel.setDisplayPicture(displayPicture);
				}
			}*/
			if(medicalDeviceEntity.getCompanyId()!=null)
			{
				companyEntity=companyDAO.findById(CompanyEntity.class,Integer.parseInt(medicalDeviceEntity.getCompanyId()));
				
				if(null!=companyEntity)
				{
				medicalDevicesModel.setCompanyName(companyEntity.getCompanyName());
				medicalDevicesModel.setCompanyUrl(companyEntity.getCompanyUrl());
				}
				
			}
			Set<TherapeuticArea> therapeuticAreas = new HashSet<TherapeuticArea>();
			
			medicalDevicesModel.setCompanyId(medicalDeviceEntity.getCompanyId());
			medicalDevicesModel.setDevice_id(medicalDeviceEntity.getDevice_id());
			medicalDevicesModel.setDevice_name(medicalDeviceEntity.getDevice_name());
			medicalDevicesModel.setDevice_desc(medicalDeviceEntity.getDevice_desc());
			medicalDevicesModel.setDevice_price(medicalDeviceEntity.getDevice_price());
			medicalDevicesModel.setDevice_unit(medicalDeviceEntity.getDevice_unit());
			medicalDevicesModel.setFeatures(medicalDeviceEntity.getFeatures());
			medicalDevicesModel.setDeviceUrl(medicalDeviceEntity.getDeviceUrl());
			medicalDevicesModel.setTherapeuticId(medicalDeviceEntity.getTherapeuticId());
			
			TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(medicalDevicesModel.getTherapeuticId()));
			medicalDevicesModel.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");
			
			
			/*therapeuticAreaEntities=pharmaTherapeuticsDAO.findTherapeuticAreasByCompanyId(companyEntity.getCompanyId());
			if(therapeuticAreaEntities!=null)
			{
				for(TherapeuticAreaEntity  therapeuticAreaEntity : therapeuticAreaEntities)
				{
					TherapeuticArea therapeuticArea = new TherapeuticArea();
					therapeuticArea.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
					therapeuticArea.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
					therapeuticArea.setTherapeuticDesc(therapeuticAreaEntity.getTherapeuticDesc());
					therapeuticAreas.add(therapeuticArea);
				}
				medicalDevicesModel.setTherapeuticAreas(therapeuticAreas);
			}*/
		}
		return medicalDevicesModel;
	}
	
	public List<Company> fetchallCompanies(String loginid)
	{
		List<Company> companies=new ArrayList<Company>();
		List<CompanyEntity> companyEntity=new ArrayList<CompanyEntity>();
		companyEntity=companyDAO.findAll();
		for(CompanyEntity companyEntities:companyEntity)
		{
			Company company=new Company();
			if(companyEntities.getDpId() != null)
			{
				DisplayPictureEntity displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,companyEntities.getDpId());
				if(displayPictureEntity != null)
				{
					DisplayPicture displayPicture = new DisplayPicture();
					displayPicture.setDpId(displayPictureEntity.getDpId());
					displayPicture.setdPicture(displayPictureEntity.getImageUrl());
					company.setCompanylogourl(displayPictureEntity.getImageUrl());
				}

			}
			List<Object[]> therapeutic_IDs= medicalDeviceDao.findByCompanyIdwithTheraputicid(companyEntities.getCompanyId());
			List<TherapeuticArea> therapeuticAreaies= new ArrayList<TherapeuticArea>();
			for(Object therapeutic_id : therapeutic_IDs)
			{
				TherapeuticArea   therapeuticArea=new TherapeuticArea(); 
				TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.valueOf((String) therapeutic_id));
				therapeuticArea.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
				therapeuticArea.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
				therapeuticAreaies.add(therapeuticArea);
				
			}
			company.setTherapeuticAresIU(therapeuticAreaies);
			company.setCompanyId(companyEntities.getCompanyId());
			company.setCompanyName(companyEntities.getCompanyName());
			company.setCompanyDesc(companyEntities.getCompanyDesc());
			company.setCompanyUrl(companyEntities.getCompanyUrl());
			company.setContactName(companyEntities.getContactName());
			company.setContactNo(companyEntities.getContactNo());
			companies.add(company);
		}
		return companies;
		
	}
	
	public List<MedicalDevicesModel> fetchallMedicalDevicesWithCompany(String loginid)
	{
		List<MedicalDevicesModel> medicalDevicesModels=new ArrayList<MedicalDevicesModel>();
		List<MedicalDeviceEntity> medicalDeviceEntity=new ArrayList<MedicalDeviceEntity>();
		medicalDeviceEntity= medicalDeviceDao.getDeviceList();
		CompanyEntity companyEntity=null;
		for(MedicalDeviceEntity medicalDeviceEntities:medicalDeviceEntity)
		{
			MedicalDevicesModel medicalDevicesModel=new MedicalDevicesModel();
			
			medicalDevicesModel.setDevice_id(medicalDeviceEntities.getDevice_id());
			medicalDevicesModel.setDevice_name(medicalDeviceEntities.getDevice_name());
			medicalDevicesModel.setDevice_desc(medicalDeviceEntities.getDevice_desc());
			medicalDevicesModel.setDevice_price(medicalDeviceEntities.getDevice_price());
			medicalDevicesModel.setDevice_unit(medicalDeviceEntities.getDevice_unit());
			medicalDevicesModel.setDeviceUrl(medicalDeviceEntities.getDeviceUrl());
			medicalDevicesModel.setFeatures(medicalDeviceEntities.getFeatures());
			medicalDevicesModel.setTherapeuticId(medicalDeviceEntities.getTherapeuticId());
			medicalDevicesModel.setCompanyId(medicalDeviceEntities.getCompanyId());
			if(medicalDeviceEntities.getCompanyId()!=null)
			{
				companyEntity=companyDAO.findById(CompanyEntity.class, medicalDeviceEntities.getCompanyId());
				
				if(null!=companyEntity)
				{
				medicalDevicesModel.setCompanyName(companyEntity.getCompanyName());
				medicalDevicesModel.setCompanyUrl(companyEntity.getCompanyUrl());
				}
				
			}
			TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(medicalDeviceEntities.getTherapeuticId()));
			medicalDevicesModel.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");
			
			medicalDevicesModels.add(medicalDevicesModel);
			
		}
		return medicalDevicesModels;
		
	}

	public List<MedicalDevicesModel> fetchMedicalDevicesByCompanyId(String companyId,String therapeutic_id)
	{
		List<MedicalDevicesModel> medicalDevicesModels=new ArrayList<MedicalDevicesModel>();
		List<MedicalDeviceEntity> medicalDeviceEntity=new ArrayList<MedicalDeviceEntity>();
		medicalDeviceEntity= medicalDeviceDao.findByCompanyId(companyId,therapeutic_id);
		CompanyEntity companyEntity=null;
		for(MedicalDeviceEntity medicalDeviceEntities:medicalDeviceEntity)
		{
			MedicalDevicesModel medicalDevicesModel=new MedicalDevicesModel();
			
			medicalDevicesModel.setDevice_id(medicalDeviceEntities.getDevice_id());
			medicalDevicesModel.setDevice_name(medicalDeviceEntities.getDevice_name());
			medicalDevicesModel.setDevice_desc(medicalDeviceEntities.getDevice_desc());
			medicalDevicesModel.setDevice_price(medicalDeviceEntities.getDevice_price());
			medicalDevicesModel.setDevice_unit(medicalDeviceEntities.getDevice_unit());
			medicalDevicesModel.setDeviceUrl(medicalDeviceEntities.getDeviceUrl());
			medicalDevicesModel.setFeatures(medicalDeviceEntities.getFeatures());
			medicalDevicesModel.setTherapeuticId(medicalDeviceEntities.getTherapeuticId());
			medicalDevicesModel.setCompanyId(medicalDeviceEntities.getCompanyId());
			if(medicalDeviceEntities.getCompanyId()!=null)
			{
				companyEntity=companyDAO.findById(CompanyEntity.class, Integer.parseInt(medicalDeviceEntities.getCompanyId()));
				
				if(null!=companyEntity)
				{
				medicalDevicesModel.setCompanyName(companyEntity.getCompanyName());
				medicalDevicesModel.setCompanyUrl(companyEntity.getCompanyUrl());
				}
				
			}
			TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(medicalDeviceEntities.getTherapeuticId()));
			medicalDevicesModel.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");
			
			medicalDevicesModels.add(medicalDevicesModel);
		}		return medicalDevicesModels;
		
	}
	public List<OrderMedicalDevice_res> orderMedicalDevicesByDoctorId(String loginId)
	{
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		int i=0;Date now;
		List<OrderMedicalDevice_res> ordermedicalDevice_res=new ArrayList<OrderMedicalDevice_res>();
		List<MedicalDevicesModel> medicalDevicesModels=new ArrayList<MedicalDevicesModel>();
		/*List<OrderMedicalDeviceEntity> ordermedicalEntity=new ArrayList<OrderMedicalDeviceEntity>();*/
		List<OrderMedicalDeviceEntity>	ordermedicalEntity= orderMedicalDAO.getOrderListByDoctor(Integer.toString(doctorEntity.getDoctorId()));
		if(ordermedicalEntity != null){
			   now=ordermedicalEntity.get(0).getOrderOn();
		
		for(OrderMedicalDeviceEntity ordermedicalentites : ordermedicalEntity){
			i++;
			OrderMedicalDevice_res ordermedicaldevice=new OrderMedicalDevice_res();
	
				MedicalDeviceEntity medicalDeviceEntity=new MedicalDeviceEntity();
				medicalDeviceEntity= medicalDeviceDao.findById(MedicalDeviceEntity.class,Integer.parseInt(ordermedicalentites.getDevice_id()));
					CompanyEntity companyEntity=null;
					if(null!=medicalDeviceEntity)
					{
					MedicalDevicesModel medicalDevicesModel=new MedicalDevicesModel();
					medicalDevicesModel.setCreatedOn((ordermedicalentites.getOrderOn()).toString());
					medicalDevicesModel.setDevice_id(medicalDeviceEntity.getDevice_id());
					medicalDevicesModel.setDevice_name(medicalDeviceEntity.getDevice_name());
					medicalDevicesModel.setDevice_desc(medicalDeviceEntity.getDevice_desc());
					medicalDevicesModel.setDeviceUrl(medicalDeviceEntity.getDeviceUrl());
					medicalDevicesModel.setFeatures(medicalDeviceEntity.getFeatures());
					medicalDevicesModel.setTherapeuticId(medicalDeviceEntity.getTherapeuticId());
					medicalDevicesModel.setCompanyId(medicalDeviceEntity.getCompanyId());
					medicalDevicesModel.setCompanyUrl(medicalDeviceEntity.getCompanyUrl());
					if(medicalDeviceEntity.getCompanyId()!=null)
					{
						companyEntity=companyDAO.findById(CompanyEntity.class, Integer.parseInt(medicalDeviceEntity.getCompanyId()));
						
						if(null!=companyEntity)
						{
						medicalDevicesModel.setCompanyName(companyEntity.getCompanyName());
						}
						
					}
					TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(medicalDeviceEntity.getTherapeuticId()));
					medicalDevicesModel.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");
			
					
					
					
					if (DateUtils.isSameDay(ordermedicalentites.getOrderOn(),now )) {
						now=ordermedicalentites.getOrderOn();						
						medicalDevicesModels.add(medicalDevicesModel);
					}else{	
						ordermedicaldevice.setOrderOn(now.toString());
						now=ordermedicalentites.getOrderOn();
						ordermedicaldevice.setMedicalDevicesModel(medicalDevicesModels);
						ordermedicalDevice_res.add(ordermedicaldevice);
						medicalDevicesModels=new ArrayList<MedicalDevicesModel>();
						medicalDevicesModels.add(medicalDevicesModel);
					}
					
				if(i==ordermedicalEntity.size()){
					ordermedicaldevice.setOrderOn(ordermedicalentites.getOrderOn().toString());
					ordermedicaldevice.setMedicalDevicesModel(medicalDevicesModels);
					ordermedicalDevice_res.add(ordermedicaldevice);
				}
					
				}
						
		}	
		}		return ordermedicalDevice_res;
		
	}	
	/*public List<MedicalDevicesModel> orderMedicalDevicesByDoctorId(String loginId)
	{
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		List<OrderMedicalDevice> ordermedicalDevice_res=new ArrayList<OrderMedicalDevice>();
		List<MedicalDevicesModel> medicalDevicesModels=new ArrayList<MedicalDevicesModel>();
		
		List<OrderMedicalDeviceEntity> ordermedicalEntity=new ArrayList<OrderMedicalDeviceEntity>();
		ordermedicalEntity= orderMedicalDAO.getOrderListByDoctor(Integer.toString(doctorEntity.getDoctorId()));
		Date now=ordermedicalEntity.get(0).getOrderOn();
		for(OrderMedicalDeviceEntity ordermedicalentites : ordermedicalEntity){
				
				MedicalDeviceEntity medicalDeviceEntity=new MedicalDeviceEntity();
				medicalDeviceEntity= medicalDeviceDao.findById(MedicalDeviceEntity.class,Integer.parseInt(ordermedicalentites.getDevice_id()));
					CompanyEntity companyEntity=null;
					if(null!=medicalDeviceEntity)
					{
					MedicalDevicesModel medicalDevicesModel=new MedicalDevicesModel();
					medicalDevicesModel.setCreatedOn((ordermedicalentites.getOrderOn()).toString());
					medicalDevicesModel.setDevice_id(medicalDeviceEntity.getDevice_id());
					medicalDevicesModel.setDevice_name(medicalDeviceEntity.getDevice_name());
					medicalDevicesModel.setDevice_desc(medicalDeviceEntity.getDevice_desc());
					medicalDevicesModel.setDevice_price(medicalDeviceEntity.getDevice_price());
					medicalDevicesModel.setDevice_unit(medicalDeviceEntity.getDevice_unit());
					medicalDevicesModel.setDeviceUrl(medicalDeviceEntity.getDeviceUrl());
					medicalDevicesModel.setFeatures(medicalDeviceEntity.getFeatures());
					medicalDevicesModel.setTherapeuticId(medicalDeviceEntity.getTherapeuticId());
					medicalDevicesModel.setCompanyId(medicalDeviceEntity.getCompanyId());
					if(medicalDeviceEntity.getCompanyId()!=null)
					{
						companyEntity=companyDAO.findById(CompanyEntity.class, medicalDeviceEntity.getCompanyId());
						
						if(null!=companyEntity)
						{
						medicalDevicesModel.setCompanyName(companyEntity.getCompanyName());
						medicalDevicesModel.setCompanyUrl(companyEntity.getCompanyUrl());
						}
						
					}
					TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(medicalDeviceEntity.getTherapeuticId()));
					medicalDevicesModel.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");
					
					if (DateUtils.isSameDay(ordermedicalentites.getOrderOn(),now )) {
						now=ordermedicalentites.getOrderOn();
						medicalDevicesModels.add(medicalDevicesModel);
					}else{
						now=ordermedicalentites.getOrderOn();
						ordermedicalDevice_res.add(medicalDevicesModels);
						medicalDevicesModels=null;
						medicalDevicesModels.add(medicalDevicesModel);
					}
					
				}
						
		}	
				return medicalDevicesModels;
		
	}*/
	public List<OrderMedicalDevice> getAllorders()
	{
		
		
		List<OrderMedicalDevice> ordermedicaldeevicesmodel=new ArrayList<OrderMedicalDevice>();
		List<OrderMedicalDeviceEntity> ordermedicalEntity=new ArrayList<OrderMedicalDeviceEntity>();
		ordermedicalEntity= orderMedicalDAO.getAllOrdersList();
		
		for(OrderMedicalDeviceEntity ordermedicalentites : ordermedicalEntity){
				DoctorEntity doctorEntity = doctorDAO.findByDoctorIdWithUserEntity(Integer.parseInt(ordermedicalentites.getOrderBy()));	
				UserEntity userentity=doctorEntity.getUser();
				OrderMedicalDevice ordermedicalDevice=new OrderMedicalDevice();
				
				MedicalDeviceEntity medicalDeviceEntity= medicalDeviceDao.findById(MedicalDeviceEntity.class,Integer.parseInt(ordermedicalentites.getDevice_id()));
					CompanyEntity companyEntity=null;
					if(null!=medicalDeviceEntity)
					{
						ordermedicalDevice.setOrderid(ordermedicalentites.getOrder_id());
						ordermedicalDevice.setDeviceName(medicalDeviceEntity.getDevice_name());
						ordermedicalDevice.setOrderOn(ordermedicalentites.getOrderOn().toString());
						ordermedicalDevice.setDoctorName((userentity.getFirstName() == null ? "" : userentity.getFirstName())+" "+(userentity.getLastName() == null ? "" : userentity.getLastName()));
		
					if(medicalDeviceEntity.getCompanyId()!=null)
					{
						companyEntity=companyDAO.findById(CompanyEntity.class, Integer.parseInt(medicalDeviceEntity.getCompanyId()));
						
						if(null!=companyEntity)
						{
							
							ordermedicalDevice.setCompanyName(companyEntity.getCompanyName());
							ordermedicalDevice.setCompanyURL(companyEntity.getCompanyUrl());
						}
						
					}
					TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(medicalDeviceEntity.getTherapeuticId()));
					ordermedicalDevice.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");
					
					ordermedicaldeevicesmodel.add(ordermedicalDevice);
				}
						
		}	
				return ordermedicaldeevicesmodel;
		
	}
	public void createMedicalDevice(Model model) throws IOException
	{
			MedicalDevicesModel medicalDevicesModel=(MedicalDevicesModel) model.asMap().get("medicaldevice");
		    MedicalDeviceEntity medicalDeviceEntity = new MedicalDeviceEntity();
			/*LocationEntity locationEntity = new LocationEntity();
			DisplayPictureEntity displayPictureEntity = new DisplayPictureEntity();
			Location location = company.getLocation();
			DisplayPicture displayPicture = company.getDisplayPicture();*/

		    medicalDeviceEntity.setStatus("Active");
		    medicalDeviceEntity.setDevice_name(medicalDevicesModel.getDevice_name());
		    medicalDeviceEntity.setDevice_desc(medicalDevicesModel.getDevice_desc());
		    medicalDeviceEntity.setFeatures(medicalDevicesModel.getFeatures());
		    medicalDeviceEntity.setDevice_price(medicalDevicesModel.getDevice_price());
		    medicalDeviceEntity.setDevice_unit(medicalDevicesModel.getDevice_unit());
		    medicalDeviceEntity.setCompanyId(medicalDevicesModel.getCompanyId());
		    medicalDeviceEntity.setCompanyUrl(medicalDevicesModel.getCompanyUrl());
		    MultipartFile displayPic=(MultipartFile) model.asMap().get("companyLogo");
		    MultipartFile displayPiDevice=(MultipartFile) model.asMap().get("deviceLogo");
		    medicalDeviceEntity.setCompanyId(medicalDevicesModel.getCompanyId());
		    medicalDeviceEntity.setTherapeuticId(medicalDevicesModel.getTherapeuticId());
		    medicalDeviceEntity.setCreatedOn(Calendar.getInstance().getTime());
			//MultipartFile displayPic=(MultipartFile) model.asMap().get("companyLogo");
			if (displayPic != null) {
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/medicaldevices/companies/";
				_displayPic += FileUtil.copyImage(displayPic,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "medicaldevices"+File.separator+"companies");
				medicalDeviceEntity.setCompanyUrl(_displayPic);
			}
			if (displayPiDevice != null) {
				String _displayPicDevice = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/medicaldevices/devices/";
				_displayPicDevice += FileUtil.copyImage(displayPiDevice,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "medicaldevices"+File.separator+"devices");
				medicalDeviceEntity.setDeviceUrl(_displayPicDevice);
			}

			/*
			displayPictureDAO.persist(displayPictureEntity);
			companyEntity.setDpId(displayPictureEntity.getDpId());*/
			medicalDeviceDao.persist(medicalDeviceEntity);
			

	}
	
	public void updateMedicalDevice(Model model) throws IOException
	{
		MedicalDevicesModel medicalDevicesModel=(MedicalDevicesModel) model.asMap().get("medicaldevice");
	  
		
		if(String.valueOf(medicalDevicesModel.getDevice_id())!=null)
		{
			
		    MedicalDeviceEntity medicalDeviceEntity =medicalDeviceDao.findById(MedicalDeviceEntity.class, medicalDevicesModel.getDevice_id());
		    medicalDeviceEntity.setDevice_name(medicalDevicesModel.getDevice_name());
		    medicalDeviceEntity.setDevice_desc(medicalDevicesModel.getDevice_desc());
		    medicalDeviceEntity.setFeatures(medicalDevicesModel.getFeatures());
		    medicalDeviceEntity.setDevice_price(medicalDevicesModel.getDevice_price());
		    medicalDeviceEntity.setDevice_unit(medicalDevicesModel.getDevice_unit());
		    medicalDeviceEntity.setCompanyId(medicalDevicesModel.getCompanyId());
		    medicalDeviceEntity.setCompanyUrl(medicalDevicesModel.getCompanyUrl());
		    MultipartFile displayPic=(MultipartFile) model.asMap().get("companyLogo");
		    MultipartFile displayPiDevice=(MultipartFile) model.asMap().get("deviceLogo");
		    medicalDeviceEntity.setCompanyId(medicalDevicesModel.getCompanyId());
		    medicalDeviceEntity.setTherapeuticId(medicalDevicesModel.getTherapeuticId());
		    medicalDeviceEntity.setCreatedOn(Calendar.getInstance().getTime());
		    medicalDeviceDao.persist(medicalDeviceEntity);
		}
	}
	
	public void createOrder(Model model) throws IOException
	{
		String loginId=(String) model.asMap().get("loginId");
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		UserEntity userEntity = doctorEntity.getUser();
		OrderMedicalDevice ordermedicaldevice=(OrderMedicalDevice) model.asMap().get("ordermedicaldevice");
		OrderMedicalDeviceEntity ordermedicalEntity=new OrderMedicalDeviceEntity();
		ordermedicalEntity.setCompanyId(ordermedicaldevice.getCompanyID());
		ordermedicalEntity.setDevice_id(ordermedicaldevice.getDeviceId());
		ordermedicalEntity.setOrderBy(Integer.toString(doctorEntity.getDoctorId()));
		ordermedicalEntity.setTherapeuticId(ordermedicaldevice.getTherapeuticId());
		ordermedicalEntity.setCOMMENTS(ordermedicaldevice.getComments());
		ordermedicalEntity.setEMAILID(ordermedicaldevice.getEmailId());
		ordermedicalEntity.setTherapeuticId(ordermedicaldevice.getTherapeuticId());
		ordermedicalEntity.setMOBILENO(ordermedicaldevice.getMobileNumber());
		ordermedicalEntity.setOrderOn(Calendar.getInstance().getTime());
		if (ordermedicaldevice.getAddressflag().equals("Y"))
		{

			if(ordermedicaldevice.getLocations() != null && ordermedicaldevice.getLocations().size() > 0)
			{			
				Location last = null;
				for(Location location : ordermedicaldevice.getLocations())
				{
					last = location;
				}
				com.medrep.app.entity.LocationEntity locationEntity = new com.medrep.app.entity.LocationEntity();
				locationEntity.setAddress1(last.getAddress1());
				locationEntity.setAddress2(last.getAddress2());
				locationEntity.setCity(last.getCity());
				locationEntity.setState(last.getState());
				locationEntity.setZipcode(last.getZipcode());
				locationEntity.setCountry(last.getCountry());
				locationEntity.setType(last.getType());
				locationEntity.setUser(userEntity);
				locationEntity.setLocationType(last.getLocationType());
				locationEntity.setDoctorId(doctorEntity.getDoctorId());
				locationDAO.persist(locationEntity);				
			}
			
		}
		orderMedicalDAO.persist(ordermedicalEntity);
	}

}
