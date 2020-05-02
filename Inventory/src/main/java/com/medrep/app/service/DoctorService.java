package com.medrep.app.service;

import java.io.File;
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

import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.DoctorAppointmentDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorNotificationDAO;
import com.medrep.app.dao.DoctorSurveyDAO;
import com.medrep.app.dao.LocationDAO;
import com.medrep.app.dao.OTPDAO;
import com.medrep.app.dao.PatDocAppointDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.dao.UserSecurityDAO;
import com.medrep.app.entity.DisplayPictureEntity;
import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorNotificationEntity;
import com.medrep.app.entity.DoctorSurveyEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.LocationTypeEntity;
import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.OTPEntity;
import com.medrep.app.entity.PatientDocAppoiEntity;
import com.medrep.app.entity.PatientEntity;
import com.medrep.app.entity.RoleEntity;
import com.medrep.app.entity.SurveyEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.entity.UserSecurityEntity;
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorNotification;
import com.medrep.app.model.Location;
import com.medrep.app.model.LocationType;
import com.medrep.app.model.Mail;
import com.medrep.app.model.PatDocAppointModel;
import com.medrep.app.model.SMS;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.PasswordProtector;
import com.medrep.app.util.Util;

@Service("doctorService")
@Transactional
public class DoctorService {

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	UserDAO userDAO;
	
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
	DoctorNotificationDAO doctorNotificationDAO;
	@Autowired
	DoctorAppointmentDAO doctorAppointmentDAO;
	
	@Autowired
	DoctorSurveyDAO doctorSurveyDAO;

	@Autowired
	OTPDAO otpdao;
	@Autowired
	PatDocAppointDAO patDocAppointDAO;

	private static final Log log = LogFactory.getLog(DoctorService.class);

	public void updateDoctor(Model model) throws MedrepException
	{
		Doctor doctor=(Doctor) model.asMap().get("doctor");
		String loginId=(String) model.asMap().get("loginId");
		try
		{
			if(loginId!=null )
			{

				DoctorEntity doctorEntity = null;
				if(doctor.getUsername() != null)
				{
					doctorEntity = doctorDAO.findByLoginId(loginId);
				}
				if(doctorEntity != null && doctorEntity.getDoctorId() != null)
				{
					if(doctor.getRegistrationNumber() != null)
					{
						doctorEntity.setRegistrationNumber(doctor.getRegistrationNumber());
					}
					if(doctor.getRegistrationYear() != null)
					{
						doctorEntity.setRegistrationYear(doctor.getRegistrationYear());
					}
					if(doctor.getQualification() != null)
					{
						doctorEntity.setQualification(doctor.getQualification());
					}
					if(doctor.getStateMedCouncil() != null)
					{
						doctorEntity.setStateMedCouncil(doctor.getStateMedCouncil());
					}
					if(doctor.getTherapeuticId() != null)
					{
						doctorEntity.setTherapeuticId(doctor.getTherapeuticId());
					}

					UserEntity userEntity = doctorEntity.getUser();

					if(userEntity != null)
					{
//						if(doctor.getEmailId() != null)
//						{
//							userEntity.setEmailId(doctor.getEmailId());
//						}
						if(doctor.getFirstName() != null)
						{
							if(doctor.getFirstName().contains("Dr.")){
								userEntity.setFirstName(doctor.getFirstName().substring(doctor.getFirstName().lastIndexOf("Dr.")+3).trim());
							}else 
							userEntity.setFirstName(doctor.getFirstName());
						}
//						if(doctor.getMiddleName() != null)
//						{
//							userEntity.setMiddleName(doctor.getMiddleName());
//						}
						if(doctor.getLastName() != null)
						{
							userEntity.setLastName(doctor.getLastName());
						}
						if(doctor.getPhoneNo() != null)
						{
							userEntity.setPhoneNo(doctor.getPhoneNo());
						}
						if(doctor.getAlias() != null)
						{
							userEntity.setAlias(doctor.getAlias());
						}
						if(doctor.getTitle() != null)
						{
							userEntity.setTitle(doctor.getTitle());
						}
						if(doctor.getAlternateEmailId() != null)
						{
							userEntity.setAlternateEmailId(doctor.getAlternateEmailId());
						}
						if(doctor.getEmailId() != null)
						{

							if(!userEntity.getEmailId().equalsIgnoreCase(doctor.getEmailId()))
							{
								userEntity.setEmailId(doctor.getEmailId());
								//TODO: Detect change in Mobile No. Invoke OTP.
							}
						}
						if(doctor.getMobileNo() != null)
						{
							if(!userEntity.getMobileNo().equalsIgnoreCase(doctor.getMobileNo()))
							{
								//TODO: Detect change in Mobile No. Invoke OTP.
								userEntity.setMobileNo(doctor.getMobileNo());
							}

						}

						if(doctor.getLocations() != null && doctor.getLocations().size() > 0)
						{
							for( LocationEntity locationEntity : userEntity.getLocations())
							{
								locationDAO.remove(locationEntity);
							}
						}
						ArrayList<com.medrep.app.entity.LocationEntity> locations = new ArrayList<com.medrep.app.entity.LocationEntity>();
						for(Location location : doctor.getLocations())
						{
							com.medrep.app.entity.LocationEntity locationEntity = new com.medrep.app.entity.LocationEntity();
							locationEntity.setAddress1(location.getAddress1());
							locationEntity.setAddress2(location.getAddress2());
							locationEntity.setLocatity(location.getLocatity());
							locationEntity.setCity(location.getCity());
							locationEntity.setState(location.getState());
							locationEntity.setZipcode(location.getZipcode());
							locationEntity.setCountry(location.getCountry());
							locationEntity.setType(locationEntity.getType());
							locationEntity.setLocationType(location.getLocationType());
							locationEntity.setUser(userEntity);
							locationEntity.setDoctorId(doctorEntity.getDoctorId());
							locations.add(locationEntity);
						}

						userEntity.setLocations(locations);

						    if(doctor.getProfilePicture()!=null && doctor.getProfilePicture().getImgData()!=null){
						    	DisplayPictureEntity displayPictureEntity=new DisplayPictureEntity();
									String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/doctors/";
									_displayPic+= FileUtil.copyBinaryData(doctor.getProfilePicture().getImgData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"doctors",doctor.getProfilePicture().getFileName());
									if(doctor.getProfilePicture().getDpId()!=null){
										 displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,doctor.getProfilePicture().getDpId());
										 String fileName = displayPictureEntity.getImageUrl()
													.substring(displayPictureEntity.getImageUrl().lastIndexOf("/"));
											fileName = MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"doctors"+ File.separator + fileName;
											FileUtil.delete(fileName);
									}
									 displayPictureEntity.setImageUrl(_displayPic);
									 userEntity.setDisplayPicture(displayPictureEntity);
						    }

						UserSecurityEntity securityEntity = userEntity.getSecurity();
						if(securityEntity != null)
						{
							if(doctor.getPassword() != null)
							{
								securityEntity.setPassword(PasswordProtector.encrypt(doctor.getPassword()));
							}
						}
					}

					doctorDAO.merge(doctorEntity);

				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MedrepException();
		}

	}

	public Doctor getDoctor(Integer userSecurityId) throws MedrepException
	{
		Doctor doctor = null;
		try
		{
			if(userSecurityId!=null)
			{

				DoctorEntity doctorEntity  = doctorDAO.findBySecurityId(userSecurityId);
				if(doctorEntity != null)
				{
					doctor = new Doctor();


					UserEntity user = doctorEntity.getUser();

					
					if(user != null)
					{
						doctor.setEmailId(user.getEmailId());
						doctor.setFirstName(user.getFirstName());
//						doctor.setMiddleName(user.getMiddleName());
						doctor.setLastName(user.getLastName());
						doctor.setPhoneNo(user.getPhoneNo());
						doctor.setAlias(user.getAlias());
						doctor.setTitle(user.getTitle());
						doctor.setAlternateEmailId(user.getAlternateEmailId());
						doctor.setMobileNo(user.getMobileNo());

						RoleEntity role = user.getRole();
						if(role != null)
						{
							doctor.setRoleName(role.getRoleName());
							doctor.setRoleId(role.getRoleId());
						}

						UserSecurityEntity securityEntity = user.getSecurity();
						if(securityEntity != null)
						{
							doctor.setUsername(securityEntity.getLoginId());
							doctor.setUserSecurityId(securityEntity.getUserSecId());
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
						doctor.setLocations(locations);
						if(user.getDisplayPicture()!=null)
						{
							doctor.setdPicture(user.getDisplayPicture().getImageUrl());
						}

					}
					doctor.setQualification(doctorEntity.getQualification());
					doctor.setRegistrationNumber(doctorEntity.getRegistrationNumber());
					doctor.setRegistrationYear(doctorEntity.getRegistrationYear());
					doctor.setStateMedCouncil(doctorEntity.getStateMedCouncil());
					doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
					doctor.setStatus(doctorEntity.getStatus());
					doctor.setDoctorId(doctorEntity.getDoctorId());
					if(doctorEntity.getTherapeuticId() != null)
					{
						try
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(doctorEntity.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
						catch(Exception e)
						{
							//Ignore
						}
					}
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MedrepException();
		}
		return doctor;
	}

	public Doctor getDoctorById(Integer doctorId){
		Doctor doctor=null;
		doctorDAO.findByDoctorId(doctorId);
		return doctor;
	}
	public Doctor getDoctorDefaultProfile(Integer userSecurityId) throws MedrepException
	{
		Doctor doctor = null;
		try
		{
			if(userSecurityId!=null)
			{
				DoctorEntity doctorEntity  = doctorDAO.findBySecurityId(userSecurityId);
				if(doctorEntity != null)
				{
					doctor = new Doctor();

					UserEntity user = doctorEntity.getUser();

					if(user != null)
					{
						doctor.setEmailId(user.getEmailId());
						doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//						doctor.setMiddleName(user.getMiddleName());
						doctor.setLastName(user.getLastName());
						doctor.setPhoneNo(user.getPhoneNo());
						doctor.setAlias(user.getAlias());
						doctor.setTitle(user.getTitle());
						doctor.setAlternateEmailId(user.getAlternateEmailId());
						doctor.setMobileNo(user.getMobileNo());

						if(user.getDisplayPicture()!=null)
						{
							doctor.setdPicture(user.getDisplayPicture().getImageUrl());
						}

						RoleEntity role = user.getRole();
						if(role != null)
						{
							doctor.setRoleName(role.getRoleName());
							doctor.setRoleId(role.getRoleId());
						}

						UserSecurityEntity securityEntity = user.getSecurity();
						if(securityEntity != null)
						{
							doctor.setUsername(securityEntity.getLoginId());
							doctor.setUserSecurityId(securityEntity.getUserSecId());
						}

						List<Location> locations = new ArrayList<Location>();
						if(user.getLocations()!=null)
							for(LocationEntity locationEntity : user.getLocations())
							{
								Location location = new Location();
								location.setAddress1(locationEntity.getAddress1());
								location.setAddress2(locationEntity.getAddress2());								location	.setLocatity(locationEntity.getLocatity());
								location.setLocatity(locationEntity.getLocatity());
								location.setCity(locationEntity.getCity());
								location.setCountry(locationEntity.getCountry());
								location.setState(locationEntity.getState());
								location.setType(Util.getLocationType(locationEntity.getType()));
								location.setZipcode(locationEntity.getZipcode());
								locations.add(location);

							}
						doctor.setLocations(locations);
					}
					doctor.setQualification(doctorEntity.getQualification());
					doctor.setRegistrationNumber(doctorEntity.getRegistrationNumber());
					doctor.setRegistrationYear(doctorEntity.getRegistrationYear());
					doctor.setStateMedCouncil(doctorEntity.getStateMedCouncil());
					doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
					doctor.setStatus(doctorEntity.getStatus());
					doctor.setDoctorId(doctorEntity.getDoctorId());
					if(doctorEntity.getTherapeuticId() != null)
					{
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(doctorEntity.getTherapeuticId()));
						if(therapeuticAreaEntity != null)
						{
							doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}

					}
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MedrepException();
		}
		return doctor;
	}

	public Doctor getDoctorByMobile(String mobileNo) throws MedrepException
	{
		Doctor doctor = null;
		try
		{
			if(mobileNo!=null)
			{
				DoctorEntity doctorEntity  = doctorDAO.findByMobileNumber(mobileNo);
				if(doctorEntity != null)
				{
					doctor = new Doctor();


					UserEntity user = doctorEntity.getUser();

					if(user != null)
					{
						doctor.setEmailId(user.getEmailId());
						doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//						doctor.setMiddleName(user.getMiddleName());
						doctor.setLastName(user.getLastName());
						doctor.setPhoneNo(user.getPhoneNo());
						doctor.setAlias(user.getAlias());
						doctor.setTitle(user.getTitle());
						doctor.setAlternateEmailId(user.getAlternateEmailId());
						doctor.setMobileNo(user.getMobileNo());

						RoleEntity role = user.getRole();
						if(role != null)
						{
							doctor.setRoleName(role.getRoleName());
							doctor.setRoleId(role.getRoleId());
						}
						List<Location> locations = new ArrayList<Location>();
						for(LocationEntity locationEntity : user.getLocations())
						{
							Location location = new Location();
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
						doctor.setLocations(locations);

						if(user.getDisplayPicture()!=null)
						{
							doctor.setdPicture(user.getDisplayPicture().getImageUrl());
						}
					}
					doctor.setQualification(doctorEntity.getQualification());
					doctor.setRegistrationNumber(doctorEntity.getRegistrationNumber());
					doctor.setRegistrationYear(doctorEntity.getRegistrationYear());
					doctor.setStateMedCouncil(doctorEntity.getStateMedCouncil());
					doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
					if(doctorEntity.getTherapeuticId() != null)
					{
						try
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(doctorEntity.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
						catch(Exception e)
						{
							//Ignore
						}
					}
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MedrepException();
		}
		return doctor;
	}

	public Doctor getDoctorBasicDetailsByMobile(String mobileNo) throws MedrepException
	{
		Doctor doctor = null;
		try
		{
			if(mobileNo!=null)
			{
				DoctorEntity doctorEntity  = doctorDAO.findByMobileNumber(mobileNo);
				if(doctorEntity != null)
				{
					doctor = new Doctor();


					UserEntity user = doctorEntity.getUser();

					if(user != null)
					{
						doctor.setEmailId(user.getEmailId());
						doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//						doctor.setMiddleName(user.getMiddleName());
						doctor.setLastName(user.getLastName());
						doctor.setMobileNo(user.getMobileNo());
						if(user.getDisplayPicture()!=null)
						doctor.setdPicture(user.getDisplayPicture().getImageUrl());
					}
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MedrepException();
		}
		return doctor;
	}

	public List<Doctor> searchDoctorByName(String name)
	{
		List<Doctor> doctors = new ArrayList<Doctor>();
		if(name!=null)
		{
			List<DoctorEntity> doctorEntities  = doctorDAO.findByName(name);
			for(DoctorEntity  doctorEntity : doctorEntities)
			{
				Doctor doctor = new Doctor();
				UserEntity user = doctorEntity.getUser();

				if(user != null)
				{
					doctor.setEmailId(user.getEmailId());
					doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//					doctor.setMiddleName(user.getMiddleName());
					doctor.setLastName(user.getLastName());
					doctor.setPhoneNo(user.getPhoneNo());
					doctor.setAlias(user.getAlias());
					doctor.setTitle(user.getTitle());
					doctor.setAlternateEmailId(user.getAlternateEmailId());
					doctor.setMobileNo(user.getMobileNo());

					RoleEntity role = user.getRole();
					if(role != null)
					{
						doctor.setRoleName(role.getRoleName());
						doctor.setRoleId(role.getRoleId());
					}
					List<Location> locations = new ArrayList<Location>();
					for(LocationEntity locationEntity : user.getLocations())
					{
						Location location = new Location();
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
					doctor.setLocations(locations);

					if(user.getDisplayPicture()!=null)
					{
						doctor.setdPicture(user.getDisplayPicture().getImageUrl());
					}
				}
				doctor.setQualification(doctorEntity.getQualification());
				doctor.setRegistrationNumber(doctorEntity.getRegistrationNumber());
				doctor.setRegistrationYear(doctorEntity.getRegistrationYear());
				doctor.setStateMedCouncil(doctorEntity.getStateMedCouncil());
				doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
				if(doctorEntity.getTherapeuticId() != null)
				{
					try
					{
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(doctorEntity.getTherapeuticId()));
						if(therapeuticAreaEntity != null)
						{
							doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}
					}
					catch(Exception e)
					{
						//Ignore
					}
				}
				doctors.add(doctor);
			}
		}
		return doctors;
	}

	public List<Doctor> searchDoctorByStatus(String status)
	{
		List<Doctor> doctors = new ArrayList<Doctor>();
		if(status!=null)
		{
			List<DoctorEntity> doctorEntities  = doctorDAO.findByStatus(status);
			for(DoctorEntity  doctorEntity : doctorEntities)
			{
				Doctor doctor = new Doctor();
				UserEntity user = doctorEntity.getUser();

				if(user != null)
				{
					doctor.setEmailId(user.getEmailId());
					doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//					doctor.setMiddleName(user.getMiddleName());
					doctor.setLastName(user.getLastName());
					doctor.setPhoneNo(user.getPhoneNo());
					doctor.setAlias(user.getAlias());
					doctor.setTitle(user.getTitle());
					doctor.setAlternateEmailId(user.getAlternateEmailId());
					doctor.setMobileNo(user.getMobileNo());
					doctor.setDoctorId(doctorEntity.getDoctorId());

					RoleEntity role = user.getRole();
					if(role != null)
					{
						doctor.setRoleName(role.getRoleName());
						doctor.setRoleId(role.getRoleId());
					}
					List<Location> locations = new ArrayList<Location>();
					for(LocationEntity locationEntity : user.getLocations())
					{
						Location location = new Location();
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
					doctor.setLocations(locations);

					if(user.getDisplayPicture()!=null)
					{
						doctor.setdPicture(user.getDisplayPicture().getImageUrl());
					}
				}
				doctor.setQualification(doctorEntity.getQualification());
				doctor.setRegistrationNumber(doctorEntity.getRegistrationNumber());
				doctor.setRegistrationYear(doctorEntity.getRegistrationYear());
				doctor.setStateMedCouncil(doctorEntity.getStateMedCouncil());
				doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
				if(doctorEntity.getTherapeuticId() != null)
				{
					try
					{
						if(doctorEntity.getTherapeuticId()!=null)
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(doctorEntity.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
					}
					catch(Exception e)
					{
						//Ignore
					}
				}
				doctors.add(doctor);
			}

		}
		return doctors;
	}

	public void alterDoctorStatusByDoctorId(String doctorId, String status)
	{
		if(doctorId != null && doctorId.length() > 0)
		{
			Integer id = Integer.parseInt(doctorId);
			DoctorEntity doctorEntity  = doctorDAO.findById(DoctorEntity.class, id);
			if(doctorEntity != null)
			{
				doctorEntity.setStatus(status);
				doctorDAO.merge(doctorEntity);
				if(status != null && "Active".equalsIgnoreCase(status))
				{
					UserEntity user = doctorEntity.getUser();
					if(user != null)
					{
						SMS sms = new SMS();
						sms.setPhoneNumber(user.getMobileNo());
						sms.setTemplate(SMSService.ACCOUNT_ACTIVATION);
						Map<String, String> valueMap = new HashMap<String, String>();
						valueMap.put("NAME", Util.getTitle(user.getTitle())+  user.getFirstName() + " " + user.getLastName());
						smsService.sendSMS(sms);

						Mail mail = new Mail();
						mail.setMailTo(user.getEmailId());
						mail.setTemplateName(EmailService.DOCTOD_ACCOUNT_ACTIVATION);
						mail.setValueMap(valueMap);
						emailService.sendMail(mail);

						OTPEntity mOtpEntity=otpdao.findByVerificationId(user.getMobileNo());
						if(!Util.isEmpty(mOtpEntity) && !Util.isZeroOrNull(mOtpEntity.getOtpId()))
							mOtpEntity.setStatus("VERIFIED");

						OTPEntity emailOTPEntity=otpdao.findByVerificationId(user.getEmailId());
						if(!Util.isEmpty(emailOTPEntity) && !Util.isZeroOrNull(emailOTPEntity.getOtpId()))
							emailOTPEntity.setStatus("VERIFIED");
					}

				}

			}
		}


	}

	public Doctor findDoctorById(String id)
	{
		Doctor doctor = null;
		if(id!=null&&!id.isEmpty())
		{

			DoctorEntity  doctorEntity = doctorDAO.findById(DoctorEntity.class, Integer.parseInt(id));
			if(doctorEntity != null)
			{
				UserEntity user = doctorEntity.getUser();

				if(user != null)
				{
					doctor = new Doctor();
					doctor.setEmailId(user.getEmailId());
					doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//					doctor.setMiddleName(user.getMiddleName());
					doctor.setLastName(user.getLastName());
					doctor.setPhoneNo(user.getPhoneNo());
					doctor.setAlias(user.getAlias());
					doctor.setTitle(user.getTitle());
					doctor.setAlternateEmailId(user.getAlternateEmailId());
					doctor.setMobileNo(user.getMobileNo());
					doctor.setDoctorId(doctorEntity.getDoctorId());
					doctor.setUsername(user.getSecurity()!=null ? user.getSecurity().getLoginId():"");
					RoleEntity role = user.getRole();
					if(role != null)
					{
						doctor.setRoleName(role.getRoleName());
						doctor.setRoleId(role.getRoleId());
					}
					List<Location> locations = new ArrayList<Location>();
					for(LocationEntity locationEntity : user.getLocations())
					{
						Location location = new Location();
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setLocatity(locationEntity.getLocatity());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setState(locationEntity.getState());
						location.setType(Util.getLocationType(locationEntity.getType()));
						location.setZipcode(locationEntity.getZipcode());
						location.setLocationType(locationEntity.getLocationType());
						locations.add(location);

					}
					doctor.setLocations(locations);

					if(user.getDisplayPicture()!=null)
					{
						doctor.setdPicture(user.getDisplayPicture().getImageUrl());
					}
				}
				doctor.setQualification(doctorEntity.getQualification());
				doctor.setRegistrationNumber(doctorEntity.getRegistrationNumber());
				doctor.setRegistrationYear(doctorEntity.getRegistrationYear());
				doctor.setStateMedCouncil(doctorEntity.getStateMedCouncil());
				doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
				if(doctorEntity.getTherapeuticId() != null)
				{
					try
					{
						if(doctorEntity.getTherapeuticId()!=null)
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(doctorEntity.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
					}
					catch(Exception e)
					{
						//Ignore
					}
				}
				
				doctor=this.doctorstas(doctor);
			}
			
		}
		return doctor;
	}
	
	
	public Doctor doctorstas(Doctor doctor)
	{
		List<DoctorNotificationEntity> doctorNotificationEntitys=doctorNotificationDAO.findByDoctorNotification(doctor.getDoctorId());
		int totalnotifications=0;
		int totalFavnot=0;
		int totdocappoint=0;
		int totalnooffeedback=0;
		int totalsurvey=0;
		for(DoctorNotificationEntity dne:doctorNotificationEntitys)
		{
			if(null!=dne.getNotificationEntity())
			{
				NotificationEntity nte=dne.getNotificationEntity();
				if(null!=nte.getNotificationId())
				{
				totalnotifications++;
				}
			}
			if(null!=dne.getFavourite())
			{
				totalFavnot++;
			}
			
		}
		
		List<DoctorAppointmentEntity> doctorAppointmentEntities=doctorAppointmentDAO.findAppointmentsByDoctorId(doctor.getDoctorId());
		for (DoctorAppointmentEntity doa:doctorAppointmentEntities)
		{
			if(null!=doa.getAppointmentId())
			{
				totdocappoint++;
			}
			
			if(null!=doa.getFeedback())
				totalnooffeedback++;
		}
		List<DoctorSurveyEntity> doctorSurveyEntities=doctorSurveyDAO.findByDoctorSurveyId(doctor.getDoctorId());
				for(DoctorSurveyEntity dse:doctorSurveyEntities)
				{
					SurveyEntity surveyEntity=dse.getSurvey();
					
					if(null!=surveyEntity.getSurveyId())
						totalsurvey++;
				}
		doctor.setTotalNoappointents(totdocappoint);
		doctor.setTotalnotificationviewd(totalnotifications);
		doctor.setTotalnooffeedback(totalnooffeedback);
		doctor.setTotalnosurveys(totalsurvey);
		return doctor;
		
	}

	public List<Doctor> findAllDoctorsExceptDeleted()
	{
		List<Doctor> doctors = new ArrayList<Doctor>();
		List<DoctorEntity> doctorEntities = doctorDAO.findAllExceptDeleted();
		for(DoctorEntity  doctorEntity : doctorEntities)
		{
			Doctor doctor = new Doctor();
			UserEntity user = doctorEntity.getUser();

			if(user != null)
			{
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

				RoleEntity role = user.getRole();
				if(role != null)
				{
					doctor.setRoleName(role.getRoleName());
					doctor.setRoleId(role.getRoleId());
				}
				List<Location> locations = new ArrayList<Location>();
				for(LocationEntity locationEntity : user.getLocations())
				{
					Location location = new Location();
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
				doctor.setLocations(locations);

				if(user.getDisplayPicture()!=null)
				{
					DisplayPicture displayPicture = new DisplayPicture();
					displayPicture.setDpId(user.getDisplayPicture().getDpId());
					displayPicture.setdPicture(user.getDisplayPicture().getImageUrl());
					doctor.setProfilePicture(displayPicture);
				}
			}
			doctor.setQualification(doctorEntity.getQualification());
			doctor.setRegistrationNumber(doctorEntity.getRegistrationNumber());
			doctor.setRegistrationYear(doctorEntity.getRegistrationYear());
			doctor.setStateMedCouncil(doctorEntity.getStateMedCouncil());
			doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
			if(doctorEntity.getTherapeuticId() != null)
			{
				try
				{
					if(doctorEntity.getTherapeuticId()!=null)
					{
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(doctorEntity.getTherapeuticId()));
						if(therapeuticAreaEntity != null)
						{
							doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}
					}
				}
				catch(Exception e)
				{
					//Ignore
				}
			}
			doctors.add(doctor);
		}

		return doctors;
	}


	public void sendEmailAboutAccountActivation(Doctor doctor)
	{
			Map<String, String> valueMap = new HashMap<String, String>();
			valueMap.put("NAME", Util.getTitle(doctor.getTitle()) + doctor.getFirstName() + " " + doctor.getLastName());
			Mail mail = new Mail();
			mail.setMailTo(doctor.getEmailId());
			mail.setTemplateName(EmailService.DOCTOR_ACCOUNT_ACTIVATION_INTIMATE);
			mail.setValueMap(valueMap);
			emailService.sendMail(mail);
	}

	public List<Doctor> findByTAreaId(String tareaId){
		List<DoctorEntity> doctorEntities = doctorDAO.findByTAreaId(tareaId);
		List<Doctor> docs = new ArrayList();
		for(DoctorEntity  doctorEntity : doctorEntities)
		{
			Doctor doctor = new Doctor();
			UserEntity user = doctorEntity.getUser();

			if(user != null)
			{
				doctor.setFirstName(user.getFirstName());
//				doctor.setMiddleName(user.getMiddleName());
				doctor.setLastName(user.getLastName());
				doctor.setPhoneNo(user.getPhoneNo());
//				doctor.setAlias(user.getAlias());
				doctor.setTitle(user.getTitle());
				doctor.setAlternateEmailId(user.getAlternateEmailId());
				doctor.setMobileNo(user.getMobileNo());
				doctor.setDoctorId(doctorEntity.getDoctorId());
				if(user.getDisplayPicture()!=null)
				doctor.setdPicture(user.getDisplayPicture().getImageUrl());
			}
			if("Active".equalsIgnoreCase(doctorEntity.getStatus()))
			docs.add(doctor);
		}

		return docs;

	}
	@SuppressWarnings("unchecked")
	public List<LocationType> findlocationtype()
	{
		
		List<LocationType> areaList = new ArrayList<LocationType>();
		
		List<LocationTypeEntity> entities = (List<LocationTypeEntity>) doctorDAO.findlocationType();
		for(LocationTypeEntity locationTypeEntity : entities) {
			LocationType locationType = new LocationType();
			locationType.setLocationtypeId(locationTypeEntity.getLocationtypeId());
			locationType.setLocationType(locationTypeEntity.getLocationType());
			
			areaList.add(locationType);
		}
		
	  return areaList;
		
	}
	
	public Map<String,List<DoctorNotification>> getReport(Integer doctorId) {
		log.info(" To get all doctors view count against notification " +doctorId);
		Map<String,List<DoctorNotification>> reportDataMap = new HashMap<String,List<DoctorNotification>>();
		List<DoctorNotification> docNList = new ArrayList<DoctorNotification>();
		try {
			DoctorEntity entity = doctorDAO.findById(DoctorEntity.class,
					doctorId);
			List<DoctorNotificationEntity> doctorNotificationEntities=doctorNotificationDAO.getAllViewByNotificationId(doctorId);
			for(DoctorNotificationEntity dne:doctorNotificationEntities){
				//Doctor doctor=getDoctorDetailsByDoctorId(dne.getDoctorId());
			
				DoctorNotification docNtion = new DoctorNotification();
				docNtion.setNotificationId(dne.getNotificationEntity().getNotificationId());
				docNtion.setNotificationName(dne.getNotificationEntity().getNotificationName());
				docNtion.setViewCount(dne.getViewCount());
				docNtion.setFavourite(dne.getFavourite());
				docNtion.setRemindMe(dne.getRemindMe() !=null ? "Y" : "N");
				docNList.add(docNtion);
			}
			log.info("Total doctors viewed count " +docNList.size());
			reportDataMap.put(doctorId + "-" + entity.getUser().getFirstName(), docNList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return reportDataMap;

	}

	public void getPatientAppointmentForDoctor(Model model) {
		String username = (String) model.asMap().get("username");
		String communicationMode=(String) model.asMap().get("communicationMode");
		
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(username);
		List<PatientDocAppoiEntity> appointmentEntities = null;
		try
		{
			if("CHAT".equals(communicationMode)) {

			Map<String,Object> doctorAppointmentInfo=new HashMap<String,Object>();
			appointmentEntities = patDocAppointDAO.findByDoctorId(doctorEntity.getDoctorId(),communicationMode);
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
						DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
						patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
						DateFormat dateFormat2 = new SimpleDateFormat(" HH:mm");
						patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
						patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
						patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());
						patDocAppointModel.setPatientName((we.getPatientEntity().getUser().getFirstName() == null ? "" : we.getPatientEntity().getUser().getFirstName())+" "+(we.getPatientEntity().getUser().getFirstName() == null ? "" : we.getPatientEntity().getUser().getLastName()));
						patDocAppointModel.setMobileNo(we.getPatientEntity().getUser().getMobileNo());
						patDocAppointModel.setEmailId(we.getPatientEntity().getUser().getEmailId());
						patDocAppointModel.setDateOfBirth(dateFormat1.format(we.getPatientEntity().getDateofBirth()).toString());
						patDocAppointModel.setDpImageUrl(we.getPatientEntity().getUser().getDisplayPicture().getImageUrl());
						upcomingAppoinment.add(patDocAppointModel);
					}else if((new Date()).compareTo(startDate.getTime()) <= 0  ){
						PatDocAppointModel patDocAppointModel=new PatDocAppointModel();

						BeanUtils.copyProperties(we, patDocAppointModel);
						DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
						patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
						DateFormat dateFormat2 = new SimpleDateFormat(" HH:mm");
						patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
						patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());

						patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
						patDocAppointModel.setPatientName((we.getPatientEntity().getUser().getFirstName() == null ? "" : we.getPatientEntity().getUser().getFirstName())+" "+(we.getPatientEntity().getUser().getFirstName() == null ? "" : we.getPatientEntity().getUser().getLastName()));
						patDocAppointModel.setMobileNo(we.getPatientEntity().getUser().getMobileNo());
						patDocAppointModel.setEmailId(we.getPatientEntity().getUser().getEmailId());
						patDocAppointModel.setDateOfBirth(dateFormat1.format(we.getPatientEntity().getDateofBirth()).toString());
						patDocAppointModel.setDpImageUrl(we.getPatientEntity().getUser().getDisplayPicture().getImageUrl());
						previousAppoinment.add(patDocAppointModel);
					}else {
						PatDocAppointModel patDocAppointModel=new PatDocAppointModel();
						
						BeanUtils.copyProperties(we, patDocAppointModel);
						DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
						patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
						DateFormat dateFormat2 = new SimpleDateFormat(" HH:mm");
						patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
						patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());

						patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
						patDocAppointModel.setPatientName((we.getPatientEntity().getUser().getFirstName() == null ? "" : we.getPatientEntity().getUser().getFirstName())+" "+(we.getPatientEntity().getUser().getFirstName() == null ? "" : we.getPatientEntity().getUser().getLastName()));
						patDocAppointModel.setMobileNo(we.getPatientEntity().getUser().getMobileNo());
						patDocAppointModel.setEmailId(we.getPatientEntity().getUser().getEmailId());
						patDocAppointModel.setDateOfBirth(dateFormat1.format(we.getPatientEntity().getDateofBirth()).toString());
						patDocAppointModel.setDpImageUrl(we.getPatientEntity().getUser().getDisplayPicture().getImageUrl());
						ongoingAppoinment.add(patDocAppointModel);
					}
					} 
				}
				doctorAppointmentInfo.put("previous", previousAppoinment);
				doctorAppointmentInfo.put("upcoming", upcomingAppoinment);
				doctorAppointmentInfo.put("ongoing", ongoingAppoinment);
				model.addAttribute("doctorAppointmentInfo",doctorAppointmentInfo);
			}
			
			else {
				Map<String,Object> doctorAppointmentInfo=new HashMap<String,Object>();
				appointmentEntities = patDocAppointDAO.findByDoctorId(doctorEntity.getDoctorId(),communicationMode);
				List<PatDocAppointModel> previousAppoinment=new ArrayList<PatDocAppointModel>();
				List<PatDocAppointModel> upcomingAppoinment=new ArrayList<PatDocAppointModel>();
				if(appointmentEntities != null) {
					for(PatientDocAppoiEntity we: appointmentEntities) {
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Date date = new Date();
						if(we.getAppointmentTime().compareTo(date) >=0) {
							PatDocAppointModel patDocAppointModel=new PatDocAppointModel();
							BeanUtils.copyProperties(we, patDocAppointModel);
							DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
							patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
							DateFormat dateFormat2 = new SimpleDateFormat(" HH:mm");
							patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
							patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());
							patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
							patDocAppointModel.setPatientName((we.getPatientEntity().getUser().getFirstName() == null ? "" : we.getPatientEntity().getUser().getFirstName())+" "+(we.getPatientEntity().getUser().getFirstName() == null ? "" : we.getPatientEntity().getUser().getLastName()));
							patDocAppointModel.setMobileNo(we.getPatientEntity().getUser().getMobileNo());
							patDocAppointModel.setEmailId(we.getPatientEntity().getUser().getEmailId());
							patDocAppointModel.setDateOfBirth(dateFormat1.format(we.getPatientEntity().getDateofBirth()).toString());
							patDocAppointModel.setDpImageUrl(we.getPatientEntity().getUser().getDisplayPicture().getImageUrl());
							upcomingAppoinment.add(patDocAppointModel);
						}else {
							PatDocAppointModel patDocAppointModel=new PatDocAppointModel();
							
							BeanUtils.copyProperties(we, patDocAppointModel);
							DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
							patDocAppointModel.setAppointmentDate(dateFormat1.format(we.getAppointmentTime()).toString());
							DateFormat dateFormat2 = new SimpleDateFormat(" HH:mm");
							patDocAppointModel.setPatientId(we.getPatientEntity().getPatientId());
							patDocAppointModel.setDoctorId(we.getDoctorEntity().getDoctorId());
							patDocAppointModel.setAppointmentTime(dateFormat2.format(we.getAppointmentTime()).toString());
							patDocAppointModel.setPatientName((we.getPatientEntity().getUser().getFirstName() == null ? "" : we.getPatientEntity().getUser().getFirstName())+" "+(we.getPatientEntity().getUser().getFirstName() == null ? "" : we.getPatientEntity().getUser().getLastName()));
							patDocAppointModel.setMobileNo(we.getPatientEntity().getUser().getMobileNo());
							patDocAppointModel.setEmailId(we.getPatientEntity().getUser().getEmailId());
							patDocAppointModel.setDateOfBirth(dateFormat1.format(we.getPatientEntity().getDateofBirth()).toString());
							patDocAppointModel.setDpImageUrl(we.getPatientEntity().getUser().getDisplayPicture().getImageUrl());
							previousAppoinment.add(patDocAppointModel);
						}
					}
					doctorAppointmentInfo.put("previous", previousAppoinment);
					doctorAppointmentInfo.put("upcoming", upcomingAppoinment);	
					model.addAttribute("doctorAppointmentInfo",doctorAppointmentInfo);
			}
		} 
			}catch (Exception e)
		{
			System.out.println("Entry not found");
		}
	}
	

}
