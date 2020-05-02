package com.medrep.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.GEODAO;
import com.medrep.app.dao.LocationDAO;
import com.medrep.app.dao.NotificationTypeDAO;
import com.medrep.app.dao.RoleDAO;
import com.medrep.app.dao.StateInformationDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DisplayPictureEntity;
import com.medrep.app.entity.GEOEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.NotificationTypeEntity;
import com.medrep.app.model.Company;
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.GEOModel;
import com.medrep.app.model.Location;
import com.medrep.app.model.NotificationType;
import com.medrep.app.model.Role;
import com.medrep.app.model.TherapeuticArea;
import com.medrep.app.util.Util;

@Service("masterDataService")
@Transactional
public class MasterDataService {

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	TherapeuticAreaDAO tAreaDAO;

	@Autowired
	NotificationTypeDAO notificationTypeDAO;

	@Autowired
	CompanyDAO companyDAO;

	@Autowired
	LocationDAO locationDAO;

	@Autowired
	DisplayPictureDAO displayPictureDAO;

	@Autowired
	UserDAO userDAO;

	@Autowired
	GEODAO geoDAO;
	@Autowired
	StateInformationDAO stateinformationDAO;
	
	

	private static final Log log = LogFactory.getLog(MasterDataService.class);

	public List<Role> getAllRoles()
	{
		List<Role> roles = new ArrayList<Role>();
		try
		{
			for(com.medrep.app.entity.RoleEntity roleEntity : roleDAO.findAll())
			{
				Role role = new Role();
				role.setRoleId(roleEntity.getRoleId());
				role.setName(roleEntity.getRoleName());
				role.setDescription(roleEntity.getRoleDesc());
				roles.add(role);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return roles;

	}



	public List<TherapeuticArea> getAllTherapeuticArea(){

		List<TherapeuticArea> tAreas = new ArrayList<TherapeuticArea>();
		try
		{
			for (com.medrep.app.entity.TherapeuticAreaEntity tAreaEntiry : tAreaDAO.findAll())
			{
				TherapeuticArea tArea = new TherapeuticArea();
				tArea.setTherapeuticDesc(tAreaEntiry.getTherapeuticDesc());
				tArea.setTherapeuticId(tAreaEntiry.getTherapeuticId());
				tArea.setTherapeuticName(tAreaEntiry.getTherapeuticName());
				tAreas.add(tArea);

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return tAreas;

	}



	public List<NotificationType> getAllNotificationType(){

		List<NotificationType> notificationTypes = new ArrayList<NotificationType>();
		try
		{
			for (NotificationTypeEntity notificationTypeEntity : notificationTypeDAO.findAll())
			{
				 NotificationType notificationType = new NotificationType();
				 notificationType.setTypeId(notificationTypeEntity.getTypeId());
				 notificationType.setTypeName(notificationTypeEntity.getTypeName());
				 notificationType.setTypeDesc(notificationTypeEntity.getTypeDesc());
				 notificationTypes.add(notificationType);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return notificationTypes;

	}



	public List<Company> getAllCompanyDetails(){

		List<Company> companyList = new ArrayList<Company>();
		try
		{
			for (CompanyEntity companyEntity : companyDAO.findAll()){

				if(companyEntity != null){

					Company companyInstance = new Company();
					Location location = new Location();
					LocationEntity locationEntity = locationDAO.findById(LocationEntity.class,companyEntity.getLocationId());
					DisplayPicture displayPicture = new DisplayPicture();
					DisplayPictureEntity displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,companyEntity.getDpId());
					if(locationEntity!=null){
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setLocationId(locationEntity.getLocationId());
						location.setState(locationEntity.getState());
						location.setType(Util.getLocationType(locationEntity.getType()));
						location.setZipcode(locationEntity.getZipcode());
					}

					if(displayPictureEntity!=null){
						displayPicture.setdPicture(displayPictureEntity.getImageUrl());
						displayPicture.setDpId(displayPictureEntity.getDpId());
					}
					companyInstance.setCompanyDesc(companyEntity.getCompanyDesc());
					companyInstance.setCompanyId(companyEntity.getCompanyId());
					companyInstance.setCompanyName(companyEntity.getCompanyName());
					companyInstance.setContactName(companyEntity.getContactName());
					companyInstance.setContactNo(companyEntity.getContactNo());
					companyInstance.setLocation(location);
					companyInstance.setDisplayPicture(displayPicture);
					companyInstance.setStatus(companyEntity.getStatus());
					companyList.add(companyInstance);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return companyList;

	}

	public List<GEOModel> getAllStates() {

		List<GEOModel> states = new ArrayList<GEOModel>();
		try {
			for (GEOEntity geoEntity : geoDAO.getAllStates()) {
				GEOModel model = BeanUtils.instantiateClass(GEOModel.class);
				BeanUtils.copyProperties(geoEntity, model);
				states.add(model);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return states;

	}
	
	
	public List<String> getAllState() {
		
		List<String> statedata=null;
		try {
			statedata	=stateinformationDAO.fetchAllStateData();

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return statedata;

	}	
	
	public List<String> getAllSubDistrictByDistrict(String stateId,String districtid) {
		
		return stateinformationDAO.findSubDistrictByDistrict(stateId,districtid);
	}
	public List<String> getAllDistrictByState(String id) {
		
		return stateinformationDAO.findDistrictByState((id));
	}
	public List<GEOModel> findCitiesByState(String state)
	{
		List<GEOModel> cities = new ArrayList<GEOModel>();
		if(state!=null && state.trim().length()>1){
		try {
			for (GEOEntity geoEntity : geoDAO.findCitiesByState(state.trim())) {
				GEOModel model = BeanUtils.instantiateClass(GEOModel.class);
				BeanUtils.copyProperties(geoEntity, model);
				cities.add(model);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}

		return cities;
	}


}
