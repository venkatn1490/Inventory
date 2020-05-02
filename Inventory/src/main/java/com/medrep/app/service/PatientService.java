package com.medrep.app.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.DoctorAppointmentDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorNotificationDAO;
import com.medrep.app.dao.DoctorSurveyDAO;
import com.medrep.app.dao.LocationDAO;
import com.medrep.app.dao.OTPDAO;
import com.medrep.app.dao.PatientContactDAO;
import com.medrep.app.dao.PatientDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.dao.UserSecurityDAO;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.PatientContactEntity;
import com.medrep.app.entity.PatientEntity;
import com.medrep.app.entity.RoleEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.entity.UserSecurityEntity;
import com.medrep.app.model.Location;
import com.medrep.app.model.Patient;
import com.medrep.app.model.PatientContactModel;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.PasswordProtector;
import com.medrep.app.util.Util;

@Service("patientService")
@Transactional
public class PatientService {

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	UserSecurityDAO securityDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	@Autowired
	SMSService smsService;

	@Autowired
	EmailService emailService;

	@Autowired
	LocationDAO locationDAO;

	@Autowired
	DisplayPictureDAO displayPictureDAO;
	
	@Autowired 
	UserDAO userDAO;
	@Autowired
	PatientDAO patientDAO;
	
	@Autowired
	PatientContactDAO patientContactDAO;
	
	@Autowired
	DoctorNotificationDAO doctorNotificationDAO;
	@Autowired
	DoctorAppointmentDAO doctorAppointmentDAO;
	
	@Autowired
	DoctorSurveyDAO doctorSurveyDAO;

	@Autowired
	OTPDAO otpdao;

	private static final Log log = LogFactory.getLog(DoctorService.class);

	public void updatePatient(Model model) throws MedrepException
	{
		Patient patient=(Patient) model.asMap().get("patient");
		String loginId=(String) model.asMap().get("loginId");
		try
		{
			if(loginId!=null )
			{

				PatientEntity patientEntity = null;
					patientEntity = patientDAO.findByLoginId(loginId);
		
				if(patientEntity != null && patientEntity.getPatientId() != null)
				{
					

					UserEntity userEntity = patientEntity.getUser();

					if(userEntity != null)
					{
						if(patient.getFirstName() != null)
						{
								userEntity.setFirstName(patient.getFirstName());
						}
						if(patient.getMiddleName() != null)
						{
							userEntity.setMiddleName(patient.getMiddleName());
						}
						if(patient.getLastName() != null)
						{
							userEntity.setLastName(patient.getLastName());
						}
						if(patient.getAlias() != null)
						{
							userEntity.setAlias(patient.getAlias());
						}
						if(patient.getAlternateEmailId() != null)
						{
							userEntity.setAlternateEmailId(patient.getAlternateEmailId());
						}
						if(patient.getSex() != null)
						{
							patientEntity.setSex(patient.getSex());
						}
						if(patient.getHeight() != null)
						{
							patientEntity.setHeight(patient.getHeight());
						}
						if(patient.getWeight() != null)
						{
							patientEntity.setWeight(patient.getWeight());
						}
						if(patient.getBloodGroup() != null)
						{
							patientEntity.setBloodGroup(patient.getBloodGroup());
						}
						if(patient.getAadharCardNumber() != null)
						{
							patientEntity.setAadharCardNumber(patient.getAadharCardNumber());
						}
						if(patient.getDateofBirth() != null)
						{
							SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
							Date date = dt.parse(patient.getDateofBirth());
							patientEntity.setDateofBirth(date);						}
						}
						
						if(patient.getEmailId() != null)
						{

							if(!userEntity.getEmailId().equalsIgnoreCase(patient.getEmailId()))
							{
								userEntity.setEmailId(patient.getEmailId());
								//TODO: Detect change in Mobile No. Invoke OTP.
							}
						}
						

						/*if(patient.getLocations() != null && patient.getLocations().size() > 0)
						{
							for( LocationEntity locationEntity : userEntity.getLocations())
							{
								locationDAO.remove(locationEntity);
							}
						}
						ArrayList<com.medrep.app.entity.LocationEntity> locations = new ArrayList<com.medrep.app.entity.LocationEntity>();
						for(Location location : patient.getLocations())
						{
							com.medrep.app.entity.LocationEntity locationEntity = new com.medrep.app.entity.LocationEntity();
							locationEntity.setAddress1(location.getAddress1());
							locationEntity.setAddress2(location.getAddress2());
							locationEntity.setLocatity(location.getLocatity());
							locationEntity.setCity(location.getCity());
							locationEntity.setState(location.getState());
							locationEntity.setZipcode(location.getZipcode());
							locationEntity.setCountry(location.getCountry());
							locationEntity.setType(location.getType());
							locationEntity.setLocationType(location.getLocationType());
							locationEntity.setUser(userEntity);
							locations.add(locationEntity);
						}

						userEntity.setLocations(locations);

						    if(patient.getProfilePicture()!=null && patient.getProfilePicture().getImgData()!=null){
						    	DisplayPictureEntity displayPictureEntity=new DisplayPictureEntity();
									String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/patient/";
									_displayPic+= FileUtil.copyBinaryData(patient.getProfilePicture().getImgData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"patient",patient.getProfilePicture().getFileName());
									if(patient.getProfilePicture().getDpId()!=null){
										 displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,patient.getProfilePicture().getDpId());
										 String fileName = displayPictureEntity.getImageUrl()
													.substring(displayPictureEntity.getImageUrl().lastIndexOf("/"));
											fileName = MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"patient"+ File.separator + fileName;
											FileUtil.delete(fileName);
									}
									 displayPictureEntity.setImageUrl(_displayPic);
									 userEntity.setDisplayPicture(displayPictureEntity);
						    }*/

						    if(patient.getPatientContactModel().getContactNumber()!=null  && patient.getPatientContactModel().getContacType()!=null){
						    		PatientContactEntity patientContactEntity =new PatientContactEntity();
						    	if(patient.getPatientContactModel().getContactId()!= null){
						    		patientContactEntity = patientContactDAO.findById(PatientContactEntity.class,patient.getPatientContactModel().getContactId());
						    	}
						    	patientContactEntity.setRelattionShipToPatient(patient.getPatientContactModel().getRelattionShipToPatient());
					    		patientContactEntity.setCity(patient.getPatientContactModel().getCity());
					    		patientContactEntity.setContactNumber(patient.getPatientContactModel().getContactNumber());
					    		patientContactEntity.setContactPersonName(patient.getPatientContactModel().getContactPersonName());
					    		patientContactEntity.setContacType(patient.getPatientContactModel().getContacType());
					    		patientContactEntity.setPatientId(patientEntity.getPatientId());
					    		patientEntity.setPatientContactEntity(patientContactEntity);
						    }

						UserSecurityEntity securityEntity = userEntity.getSecurity();
						if(securityEntity != null)
						{
							if(patient.getPassword() != null)
							{
								securityEntity.setPassword(PasswordProtector.encrypt(patient.getPassword()));
							}
						}
						patientDAO.merge(patientEntity);

					}


				}
			

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MedrepException();
		}

	}

	public Patient getPatient(Integer userSecurityId) throws MedrepException
	{
		Patient patient = null;
		try
		{
			if(userSecurityId!=null)
			{
				PatientEntity patientEntity  = patientDAO.findBySecurityId(userSecurityId);
				if(patientEntity != null)
				{
					patient = new Patient();


					UserEntity user = patientEntity.getUser();
					

					if(user != null)
					{
						patient.setPatientId(patientEntity.getPatientId());
						patient.setEmailId(user.getEmailId());
						patient.setFirstName(user.getFirstName());
//						doctor.setMiddleName(user.getMiddleName());
						patient.setLastName(user.getLastName());
						patient.setPhoneNo(user.getPhoneNo());
						patient.setAlternateEmailId(user.getAlternateEmailId());
						patient.setMobileNo(user.getMobileNo());
						RoleEntity role = user.getRole();
						if(role != null)
						{
							patient.setRoleName(role.getRoleName());
							patient.setRoleId(role.getRoleId());
						}
						
						List<Location> locations = new ArrayList<Location>();
						if(user.getLocations()!=null)
							for(LocationEntity locationEntity : user.getLocations())
							{
								Location location = new Location();
								location.setLocationId(locationEntity.getLocationId());
								location.setAddress1(locationEntity.getAddress1());
								location.setAddress2(locationEntity.getAddress2());
								location.setLocatity(locationEntity.getLocatity());
								location.setCity(locationEntity.getCity());
								location.setCountry(locationEntity.getCountry());
								location.setState(locationEntity.getState());
								location.setType(Util.getLocationType(locationEntity.getType()));
								location.setZipcode(locationEntity.getZipcode());
								locations.add(location);

							}
						patient.setLocations(locations);
						if(user.getDisplayPicture()!=null)
						{
							patient.setdPicture(user.getDisplayPicture().getImageUrl());
						}

						UserSecurityEntity securityEntity = user.getSecurity();
						if(securityEntity != null)
						{
							patient.setUsername(securityEntity.getLoginId());
							patient.setUserSecurityId(securityEntity.getUserSecId());
						}
					}
						if(patientEntity.getPatientContactEntity()!=null){
							PatientContactModel patientContactModel=new PatientContactModel();
							patientContactModel.setContactId(patientEntity.getPatientContactEntity().getContactId());
							patientContactModel.setContactPersonName(patientEntity.getPatientContactEntity().getContactPersonName());				
							patientContactModel.setContacType(patientEntity.getPatientContactEntity().getContacType());
							patientContactModel.setContactNumber(patientEntity.getPatientContactEntity().getContactNumber());
							patientContactModel.setRelattionShipToPatient(patientEntity.getPatientContactEntity().getRelattionShipToPatient());
							patient.setPatientContactModel(patientContactModel);
						}
						
						patient.setAddress1(patientEntity.getAddress1());
						patient.setAddress2(patientEntity.getAddress2());
						patient.setCity(patientEntity.getCity());
						patient.setState(patientEntity.getState());
						patient.setZip(patientEntity.getZip());
						patient.setHeight(patientEntity.getHeight());
						patient.setWeight(patientEntity.getWeight());
						patient.setSex(patientEntity.getSex());
						patient.setMarriedstatus(patientEntity.getMarriedstatus());
						patient.setAadharCardNumber(patientEntity.getAadharCardNumber());
						patient.setBloodGroup(patientEntity.getBloodGroup());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						patient.setDateofBirth(dateFormat.format(patientEntity.getDateofBirth()).toString());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MedrepException();
		}
		return patient;
	}

	public void changeAddress(Model model) {
		List<Location> addresses=(List<Location>) model.asMap().get("addresses");
		String username=(String) model.asMap().get("username");
		PatientEntity patientEntity = patientDAO.findByLoginId(username);

		UserEntity userEntity=userDAO.findByMobileNo(username);
		List<LocationEntity> locations=new ArrayList<LocationEntity>();
		
		if(userEntity!=null){
			if(patientEntity !=null){
			for(Location location:addresses){
					LocationEntity locationEntity=new LocationEntity();
					BeanUtils.copyProperties(location, locationEntity);
					locationEntity.setPatientId(patientEntity.getPatientId());
					locations.add(locationEntity);
				}
			locationDAO.changeAddress(locations,userEntity);
			}
		}

	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void deleteAddress(Model model) throws Exception {
		String username = (String) model.asMap().get("username");

		Set<Integer> locations = (Set<Integer>) model.asMap().get("locationids");
		if (locations != null && locations.size() > 0) {
			for (Integer locationId : locations) {
				UserEntity userEntity = userDAO.findByEmailId(username);
				List<LocationEntity> locs = locationDAO.findLocationsByUserId(userEntity.getUserId());
				if (locs.size() > 1) {
					LocationEntity le = locationDAO.findById(LocationEntity.class, locationId);
					if (le == null)
						throw new Exception("Address details doesn't exist");
					if (!username.equals(le.getUser().getMobileNo()))
						throw new Exception("Invalied Access");
					locationDAO.remove(le);
				} else
					throw new Exception("Address cannot be deleted.There should be a minimum of 1 address.");
			}
		}
	}	
	
	

}
