package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.LocationDAO;
import com.medrep.app.dao.OTPDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.PharmaRepSurveyDAO;
import com.medrep.app.dao.SurveyDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserSecurityDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DisplayPictureEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.OTPEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.RoleEntity;
import com.medrep.app.entity.SurveyEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.entity.UserSecurityEntity;
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.Location;
import com.medrep.app.model.Mail;
import com.medrep.app.model.OTP;
import com.medrep.app.model.PharmaRep;
import com.medrep.app.model.SMS;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.PasswordProtector;
import com.medrep.app.util.Util;

@Service("medRepService")
@Transactional
public class MedRepService {

	private static final Logger logger = LoggerFactory.getLogger(MedRepService.class);
	@Autowired
	PharmaRepDAO pharmaRepDAO;
	@Autowired
	SurveyDAO surveyDAO;

	@Autowired
	UserSecurityDAO securityDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	@Autowired
	CompanyDAO companyDAO;

	@Autowired
	SMSService smsService;

	@Autowired
	EmailService emailService;

	@Autowired
	LocationDAO locationDAO;

	@Autowired
	OTPDAO otpdao;


	@Autowired
	DisplayPictureDAO displayPictureDAO;
	private static final Log log = LogFactory.getLog(MedRepService.class);

	public void updatePharmaRep(Model model) throws MedrepException, IOException {
		PharmaRep rep=(PharmaRep) model.asMap().get("pharmaRep");
		String loginId=(String) model.asMap().get("loginId");
		log.info("updatePharmaRepInfo"+rep);
			if(!Util.isEmpty(loginId))
			{

				PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);

				if (!Util.isEmpty(pharmaRepEntity) && !Util.isZeroOrNull(pharmaRepEntity.getRepId()))
				{
					if (!Util.isZeroOrNull(rep.getCompanyId())) {
						pharmaRepEntity.setCompanyId(rep.getCompanyId());
					}
					if (!Util.isEmpty(rep.getCoveredArea())) {
						pharmaRepEntity.setCoveredArea(rep.getCoveredArea());
					}
					if (!Util.isEmpty(rep.getCoveredZone())) {
						pharmaRepEntity.setCoveredZone(rep.getCoveredZone());
					}
					if (!Util.isZeroOrNull(rep.getManagerId())) {
						pharmaRepEntity.setManagerId(rep.getManagerId());
					}
					if (!Util.isEmpty(rep.getManagerEmail())) {
						pharmaRepEntity.setManagerEmail(rep.getManagerEmail());
					}

					if (!Util.isZeroOrNull(rep.getTherapeuticId())) {
						pharmaRepEntity.setTherapeuticId(rep.getTherapeuticId());
					}

					UserEntity userEntity = pharmaRepEntity.getUser();

					if (!Util.isEmpty(userEntity) && !Util.isZeroOrNull(userEntity.getUserId())) {
						if (!Util.isEmpty(rep.getEmailId())) {
							userEntity.setEmailId(rep.getEmailId());
						}
						if (!Util.isEmpty(rep.getFirstName())) {
							if(rep.getFirstName().contains("Mr.")){
								userEntity.setFirstName(rep.getFirstName().substring(rep.getFirstName().lastIndexOf("Mr.")+3).trim());
							}else if(rep.getFirstName().contains("Mrs.")){
								userEntity.setFirstName(rep.getFirstName().substring(rep.getFirstName().lastIndexOf("Mrs.")+4).trim());
							}else
								userEntity.setFirstName(rep.getFirstName());

						}
//						if (rep.getMiddleName() != null) {
//							userEntity.setMiddleName(rep.getMiddleName());
//						}
						if (!Util.isEmpty(rep.getLastName())) {
							userEntity.setLastName(rep.getLastName());
						}
						if (!Util.isEmpty(rep.getPhoneNo())) {
							userEntity.setPhoneNo(rep.getPhoneNo());
						}
						if (!Util.isEmpty(rep.getAlias())) {
							userEntity.setAlias(rep.getAlias());
						}
//						if (!Util.isEmpty(rep.getTitle())) {
//							userEntity.setTitle(rep.getTitle());
//						}
						if (!Util.isEmpty(rep.getAlternateEmailId())) {
							userEntity.setAlternateEmailId(rep.getAlternateEmailId());
						}
						if (!Util.isEmpty(rep.getEmailId())) {
							if (!userEntity.getEmailId().equalsIgnoreCase(rep.getEmailId())) {
								userEntity.setEmailId(rep.getEmailId());
								// TODO: Detect change in Mobile No. Invoke OTP.
							}
						}
						if (!Util.isEmpty(rep.getMobileNo())) {
							if (!userEntity.getMobileNo().equalsIgnoreCase(rep.getMobileNo())) {
								userEntity.setMobileNo(rep.getMobileNo());
							}

						}

//						if(!Util.isEmpty(rep.getLocations()))
//						{
//							for( LocationEntity locationEntity : userEntity.getLocations())
//							{
//								locationDAO.remove(locationEntity);
//							}
//						}

						ArrayList<com.medrep.app.entity.LocationEntity> locations = new ArrayList<com.medrep.app.entity.LocationEntity>();

						if(!Util.isEmpty(rep.getLocations()))
						for (Location location : rep.getLocations()) {
							LocationEntity locationEntity =null;
							if(!Util.isEmpty(location.getLocationId()))
								locationEntity=locationDAO.findById(LocationEntity.class, location.getLocationId());

							if(Util.isEmpty(locationEntity))
								locationEntity = new com.medrep.app.entity.LocationEntity();

							locationEntity.setAddress1(location.getAddress1());
							locationEntity.setAddress2(location.getAddress2());
							locationEntity.setCity(location.getCity());
							locationEntity.setState(location.getState());
							locationEntity.setZipcode(location.getZipcode());
							locationEntity.setCountry(location.getCountry());
							locationEntity.setType(location.getType());
							locationEntity.setUser(userEntity);
							locations.add(locationEntity);
						}

						userEntity.setLocations(locations);

						if(!Util.isEmpty(rep.getProfilePicture()) && !Util.isEmpty(rep.getProfilePicture().getImgData())){
					    	DisplayPictureEntity displayPictureEntity=new DisplayPictureEntity();
								String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/pharmarep/";
								_displayPic+= FileUtil.copyBinaryData(rep.getProfilePicture().getImgData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"pharmarep",rep.getProfilePicture().getFileName());
								if(rep.getProfilePicture().getDpId()!=null){
									 displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,rep.getProfilePicture().getDpId());
									 String fileName = displayPictureEntity.getImageUrl()
												.substring(displayPictureEntity.getImageUrl().lastIndexOf("/"));
										fileName = MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"doctors"+ File.separator + fileName;
										FileUtil.delete(fileName);
								}
								 displayPictureEntity.setImageUrl(_displayPic);
								 userEntity.setDisplayPicture(displayPictureEntity);
						}

						UserSecurityEntity securityEntity = userEntity.getSecurity();
						if (!Util.isEmpty(securityEntity)) {
							if (!Util.isEmpty(rep.getPassword())) {
								securityEntity.setPassword(PasswordProtector.encrypt(rep.getPassword()));
							}
						}
					}

					pharmaRepDAO.merge(pharmaRepEntity);

				}
			}


	}

	public PharmaRep getPharamaRep(String pharmaRepId) throws MedrepException {
		PharmaRep rep = null;
		try {
			PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByRepId(pharmaRepId);
			rep=populatePharmaRep(rep, pharmaRepEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MedrepException();
		}
		return rep;
	}

	public PharmaRep getPharamaRepDefaultProfile(Integer userSecurityId) throws MedrepException {
		PharmaRep rep = null;
		try {
			logger.info("userSecurityId::"+userSecurityId);
			System.out.println("userSecurityId::"+userSecurityId);
			PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findBySecurityId(userSecurityId);
			rep = populatePharmaRep(rep, pharmaRepEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MedrepException();
		}
		return rep;
	}

	private PharmaRep populatePharmaRep(PharmaRep rep, PharmaRepEntity pharmaRepEntity) {
		if (pharmaRepEntity != null) {
			rep = new PharmaRep();
			UserEntity user = pharmaRepEntity.getUser();

			if (user != null) {
				rep.setEmailId(user.getEmailId());
				rep.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
				rep.setLastName(user.getLastName());
				rep.setPhoneNo(user.getPhoneNo());
				rep.setAlias(user.getAlias());
				rep.setTitle(user.getTitle());
				rep.setAlternateEmailId(user.getAlternateEmailId());
				rep.setMobileNo(user.getMobileNo());

				RoleEntity role = user.getRole();
				if (role != null) {
					rep.setRoleName(role.getRoleName());
					rep.setRoleId(role.getRoleId());
				}
				List<Location> locations = new ArrayList<Location>();
				if(!Util.isEmpty(user.getLocations()))
				for (LocationEntity locationEntity : user.getLocations()) {
					Location location = new Location();
					location.setLocationId(locationEntity.getLocationId());
					location.setAddress1(locationEntity.getAddress1());
					location.setAddress2(locationEntity.getAddress2());
					location.setCity(locationEntity.getCity());
					location.setCountry(locationEntity.getCountry());
					location.setState(locationEntity.getState());
					location.setType(Util.getLocationType(locationEntity.getType()));
					location.setZipcode(locationEntity.getZipcode());
					locations.add(location);

				}
				if(user.getDisplayPicture()!=null)
				{
					rep.setdPicture(user.getDisplayPicture().getImageUrl());
//					DisplayPicture displayPicture = new DisplayPicture();
//					displayPicture.setDpId(user.getDisplayPicture().getDpId());
//					displayPicture.setdPicture(user.getDisplayPicture().getImageUrl());
//					rep.setProfilePicture(displayPicture);
				}
				rep.setLocations(locations);
			}

			rep.setCompanyId(pharmaRepEntity.getCompanyId());
			rep.setCoveredArea(pharmaRepEntity.getCoveredArea());
			rep.setCoveredZone(pharmaRepEntity.getCoveredZone());
			rep.setManagerId(pharmaRepEntity.getManagerId());
			rep.setManagerEmail(pharmaRepEntity.getManagerEmail());
			rep.setTherapeuticId(pharmaRepEntity.getTherapeuticId());
			if (pharmaRepEntity.getTherapeuticId() != null)
			{
					TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(pharmaRepEntity.getTherapeuticId()));
					if (therapeuticAreaEntity != null)
					{
						rep.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
					}
			}
			if (pharmaRepEntity.getCompanyId() != null)
			{
				CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,pharmaRepEntity.getCompanyId());
				if (companyEntity != null) {
					rep.setCompanyName(companyEntity.getCompanyName());
				}

			}
		}
		return rep;
	}

	public PharmaRep getPharmaRepByMobile(String mobileNo) throws MedrepException {
		PharmaRep rep = null;
		try {
			PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByMobileNumber(mobileNo);
			if (pharmaRepEntity != null) {
				rep = new PharmaRep();

				UserEntity user = pharmaRepEntity.getUser();

				if (user != null) {
					rep.setEmailId(user.getEmailId());
					rep.setFirstName(user.getFirstName());
//					rep.setMiddleName(user.getMiddleName());
					rep.setLastName(user.getLastName());
					rep.setPhoneNo(user.getPhoneNo());
					rep.setAlias(user.getAlias());
					rep.setTitle(user.getTitle());
					rep.setAlternateEmailId(user.getAlternateEmailId());
					rep.setMobileNo(user.getMobileNo());

					RoleEntity role = user.getRole();
					if (role != null) {
						rep.setRoleName(role.getRoleName());
						rep.setRoleId(role.getRoleId());
					}
					List<Location> locations = new ArrayList<Location>();
					for (LocationEntity locationEntity : user.getLocations()) {
						Location location = new Location();
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setState(locationEntity.getState());
						location.setType(Util.getLocationType(locationEntity.getType()));
						location.setZipcode(locationEntity.getZipcode());
						locations.add(location);

					}
					rep.setLocations(locations);
					if(user.getDisplayPicture()!=null)
					{
						rep.setdPicture(user.getDisplayPicture().getImageUrl());
						DisplayPicture displayPicture = new DisplayPicture();
						displayPicture.setDpId(user.getDisplayPicture().getDpId());
						displayPicture.setdPicture(user.getDisplayPicture().getImageUrl());
						rep.setProfilePicture(displayPicture);
					}

				}
				rep.setCompanyId(pharmaRepEntity.getCompanyId());
				rep.setCoveredArea(pharmaRepEntity.getCoveredArea());
				rep.setCoveredZone(pharmaRepEntity.getCoveredZone());
				rep.setManagerId(pharmaRepEntity.getManagerId());
				rep.setTherapeuticId(pharmaRepEntity.getTherapeuticId());
				if (pharmaRepEntity.getTherapeuticId() != null) {
					try {
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
								.findById(TherapeuticAreaEntity.class,Integer.parseInt(pharmaRepEntity.getTherapeuticId()));
						if (therapeuticAreaEntity != null) {
							rep.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}
					} catch (Exception e) {
						// Ignore
					}
				}
				if (pharmaRepEntity.getCompanyId() != null) {
					try {
						CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,pharmaRepEntity.getCompanyId());
						if (companyEntity != null) {
							rep.setCompanyName(companyEntity.getCompanyName());
						}
					} catch (Exception e) {
						// Ignore
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MedrepException();
		}
		return rep;
	}

	public List<PharmaRep> searchPharamaRepByName(String name) {
		List<PharmaRep> reps = new ArrayList<PharmaRep>();

		List<PharmaRepEntity> pharmaRepEntities = pharmaRepDAO.findByName(name);
		for (PharmaRepEntity pharmaRepEntity : pharmaRepEntities) {
			PharmaRep rep = new PharmaRep();

			UserEntity user = pharmaRepEntity.getUser();

			if (user != null) {
				rep.setEmailId(user.getEmailId());
				rep.setFirstName(user.getFirstName());
//				rep.setMiddleName(user.getMiddleName());
				rep.setLastName(user.getLastName());
				rep.setPhoneNo(user.getPhoneNo());
				rep.setAlias(user.getAlias());
				rep.setTitle(user.getTitle());
				rep.setAlternateEmailId(user.getAlternateEmailId());
				rep.setMobileNo(user.getMobileNo());

				RoleEntity role = user.getRole();
				if (role != null) {
					rep.setRoleName(role.getRoleName());
					rep.setRoleId(role.getRoleId());
				}
				List<Location> locations = new ArrayList<Location>();
				for (LocationEntity locationEntity : user.getLocations()) {
					Location location = new Location();
					location.setAddress1(locationEntity.getAddress1());
					location.setAddress2(locationEntity.getAddress2());
					location.setCity(locationEntity.getCity());
					location.setCountry(locationEntity.getCountry());
					location.setState(locationEntity.getState());
					location.setType(Util.getLocationType(locationEntity.getType()));
					location.setZipcode(locationEntity.getZipcode());
					locations.add(location);

				}
				rep.setLocations(locations);
				if(user.getDisplayPicture()!=null)
				{
					rep.setdPicture(user.getDisplayPicture().getImageUrl());
					DisplayPicture displayPicture = new DisplayPicture();
					displayPicture.setDpId(user.getDisplayPicture().getDpId());
					displayPicture.setdPicture(user.getDisplayPicture().getImageUrl());
					rep.setProfilePicture(displayPicture);
				}
			}
			rep.setCompanyId(pharmaRepEntity.getCompanyId());
			rep.setCoveredArea(pharmaRepEntity.getCoveredArea());
			rep.setCoveredZone(pharmaRepEntity.getCoveredZone());
			rep.setManagerId(pharmaRepEntity.getManagerId());
			rep.setTherapeuticId(pharmaRepEntity.getTherapeuticId());
			if (pharmaRepEntity.getTherapeuticId() != null) {
				try {
					TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
							.findById(TherapeuticAreaEntity.class,Integer.parseInt(pharmaRepEntity.getTherapeuticId()));
					if (therapeuticAreaEntity != null) {
						rep.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
					}
				} catch (Exception e) {
					// Ignore
				}
			}
			if (pharmaRepEntity.getCompanyId() != null) {
				try {
					CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,pharmaRepEntity.getCompanyId());
					if (companyEntity != null) {
						rep.setCompanyName(companyEntity.getCompanyName());
					}
				} catch (Exception e) {
					// Ignore
				}
			}

			reps.add(rep);
		}

		return reps;
	}

	public List<PharmaRep> getPharamaRepByStatus(String status) throws MedrepException {
		List<PharmaRep> reps = new ArrayList<PharmaRep>();
		try
		{
			if(status!=null)
			{
				List<PharmaRepEntity> pharmaRepEntities = pharmaRepDAO.findByStatus(status);

				for(PharmaRepEntity pharmaRepEntity : pharmaRepEntities)
				{

					if (pharmaRepEntity != null)
					{
						PharmaRep rep = new PharmaRep();
						UserEntity user = pharmaRepEntity.getUser();
						rep.setRepId(pharmaRepEntity.getRepId());
						if (user != null)
						{
							rep.setEmailId(user.getEmailId());
							rep.setFirstName(user.getFirstName());
//							rep.setMiddleName(user.getMiddleName());
							rep.setLastName(user.getLastName());
							rep.setPhoneNo(user.getPhoneNo());
							rep.setAlias(user.getAlias());
							rep.setTitle(user.getTitle());
							rep.setAlternateEmailId(user.getAlternateEmailId());
							rep.setMobileNo(user.getMobileNo());

							RoleEntity role = user.getRole();
							if (role != null)
							{
								rep.setRoleName(role.getRoleName());
								rep.setRoleId(role.getRoleId());
							}
							List<Location> locations = new ArrayList<Location>();
							for (LocationEntity locationEntity : user.getLocations())
							{
								Location location = new Location();
								location.setAddress1(locationEntity.getAddress1());
								location.setAddress2(locationEntity.getAddress2());
								location.setCity(locationEntity.getCity());
								location.setCountry(locationEntity.getCountry());
								location.setState(locationEntity.getState());
								location.setType(Util.getLocationType(locationEntity.getType()));
								location.setZipcode(locationEntity.getZipcode());
								locations.add(location);

							}
							rep.setLocations(locations);
							if(user.getDisplayPicture()!=null)
							{
								rep.setdPicture(user.getDisplayPicture().getImageUrl());
								DisplayPicture displayPicture = new DisplayPicture();
								displayPicture.setDpId(user.getDisplayPicture().getDpId());
								displayPicture.setdPicture(user.getDisplayPicture().getImageUrl());
								rep.setProfilePicture(displayPicture);
							}
						}
						rep.setCompanyId(pharmaRepEntity.getCompanyId());
						rep.setCoveredArea(pharmaRepEntity.getCoveredArea());
						rep.setCoveredZone(pharmaRepEntity.getCoveredZone());
						rep.setManagerId(pharmaRepEntity.getManagerId());
						rep.setTherapeuticId(pharmaRepEntity.getTherapeuticId());
						if (pharmaRepEntity.getTherapeuticId() != null)
						{
							try
							{
								TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
										.findById(TherapeuticAreaEntity.class,Integer.parseInt(pharmaRepEntity.getTherapeuticId()));
								if (therapeuticAreaEntity != null)
									rep.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							} catch (Exception e) {
								// Ignore
							}
						}
						if (pharmaRepEntity.getCompanyId() != null)
						{
							try
							{
								CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,pharmaRepEntity.getCompanyId());
								if (companyEntity != null)
									rep.setCompanyName(companyEntity.getCompanyName());
							}
							catch (Exception e)
							{
								// Ignore
							}
						}
						reps.add(rep);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MedrepException();
		}
		return reps;
	}

	public void alterRepStatusByRepId(String repId, String status)
	{
		if(repId!=null)
		{
			PharmaRepEntity pharmaRepEntity  = pharmaRepDAO.findById(PharmaRepEntity.class,Integer.parseInt(repId));
			if(pharmaRepEntity!=null)
			{
				pharmaRepEntity.setStatus(status);
				pharmaRepDAO.merge(pharmaRepEntity);
				if(status != null && "Active".equalsIgnoreCase(status))
				{
					UserEntity user = pharmaRepEntity.getUser();
					if(user != null)
					{
						SMS sms = new SMS();
						sms.setPhoneNumber(user.getMobileNo());
						sms.setTemplate(SMSService.ACCOUNT_ACTIVATION);
						Map<String, String> valueMap = new HashMap<String, String>();
						valueMap.put("NAME", user.getFirstName() + " " + user.getLastName());
						smsService.sendSMS(sms);

						Mail mail = new Mail();
						mail.setMailTo(user.getEmailId());
						mail.setTemplateName(EmailService.REP_ACCOUNT_ACTIVATION);
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

	public List<PharmaRep> findallRepsExceptDeleted()
	{
		List<PharmaRep> reps = new ArrayList<PharmaRep>();
		try
		{
				List<PharmaRepEntity> pharmaRepEntities = pharmaRepDAO.findAllExceptDeleted();

				for(PharmaRepEntity pharmaRepEntity : pharmaRepEntities)
				{

					if (pharmaRepEntity != null)
					{
						PharmaRep rep = new PharmaRep();
						UserEntity user = pharmaRepEntity.getUser();
						rep.setRepId(pharmaRepEntity.getRepId());
						if (user != null)
						{
							rep.setEmailId(user.getEmailId());
							rep.setFirstName(user.getFirstName());
//							rep.setMiddleName(user.getMiddleName());
							rep.setLastName(user.getLastName());
							rep.setPhoneNo(user.getPhoneNo());
							rep.setAlias(user.getAlias());
							rep.setTitle(user.getTitle());
							rep.setAlternateEmailId(user.getAlternateEmailId());
							rep.setMobileNo(user.getMobileNo());

							RoleEntity role = user.getRole();
							if (role != null)
							{
								rep.setRoleName(role.getRoleName());
								rep.setRoleId(role.getRoleId());
							}
							List<Location> locations = new ArrayList<Location>();
							for (LocationEntity locationEntity : user.getLocations())
							{
								Location location = new Location();
								location.setAddress1(locationEntity.getAddress1());
								location.setAddress2(locationEntity.getAddress2());
								location.setCity(locationEntity.getCity());
								location.setCountry(locationEntity.getCountry());
								location.setState(locationEntity.getState());
								location.setType(Util.getLocationType(locationEntity.getType()));
								location.setZipcode(locationEntity.getZipcode());
								locations.add(location);

							}
							rep.setLocations(locations);
							if(user.getDisplayPicture()!=null)
							{
								rep.setdPicture(user.getDisplayPicture().getImageUrl());
								DisplayPicture displayPicture = new DisplayPicture();
								displayPicture.setDpId(user.getDisplayPicture().getDpId());
								displayPicture.setdPicture(user.getDisplayPicture().getImageUrl());
								rep.setProfilePicture(displayPicture);
							}
						}
						rep.setCompanyId(pharmaRepEntity.getCompanyId());
						rep.setCoveredArea(pharmaRepEntity.getCoveredArea());
						rep.setCoveredZone(pharmaRepEntity.getCoveredZone());
						rep.setManagerId(pharmaRepEntity.getManagerId());
						rep.setTherapeuticId(pharmaRepEntity.getTherapeuticId());
						if (pharmaRepEntity.getTherapeuticId() != null)
						{
							try
							{
								TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
										.findById(TherapeuticAreaEntity.class,Integer.parseInt(pharmaRepEntity.getTherapeuticId()));
								if (therapeuticAreaEntity != null)
									rep.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							} catch (Exception e) {
								// Ignore
							}
						}
						if (pharmaRepEntity.getCompanyId() != null)
						{
							try
							{
								CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,pharmaRepEntity.getCompanyId());
								if (companyEntity != null)
									rep.setCompanyName(companyEntity.getCompanyName());
							}
							catch (Exception e)
							{
								// Ignore
							}
						}
						reps.add(rep);
					}
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return reps;
	}

	public PharmaRep findRepById(String id)
	{
		PharmaRep rep = null;
		try
		{
				PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findById(PharmaRepEntity.class, Integer.parseInt(id));


					if (pharmaRepEntity != null)
					{
						rep = new PharmaRep();
						UserEntity user = pharmaRepEntity.getUser();
						rep.setRepId(pharmaRepEntity.getRepId());
						if (user != null)
						{
							rep.setEmailId(user.getEmailId());
							rep.setFirstName(user.getFirstName());
//							rep.setMiddleName(user.getMiddleName());
							rep.setLastName(user.getLastName());
							rep.setPhoneNo(user.getPhoneNo());
							rep.setAlias(user.getAlias());
							rep.setTitle(user.getTitle());
							rep.setAlternateEmailId(user.getAlternateEmailId());
							rep.setMobileNo(user.getMobileNo());

							RoleEntity role = user.getRole();
							if (role != null)
							{
								rep.setRoleName(role.getRoleName());
								rep.setRoleId(role.getRoleId());
							}
							List<Location> locations = new ArrayList<Location>();
							for (LocationEntity locationEntity : user.getLocations())
							{
								Location location = new Location();
								String addr1 =  locationEntity.getAddress1()!=null && locationEntity.getAddress1().trim().length()>1 ? locationEntity.getAddress1() : "";
								String addr2 =  locationEntity.getAddress2()!=null && locationEntity.getAddress2().trim().length()>1 ? locationEntity.getAddress2() : "";
								String city =  locationEntity.getCity()!=null && locationEntity.getCity().trim().length()>1 ? locationEntity.getCity() : "";
								String state =  locationEntity.getState()!=null && locationEntity.getState().trim().length()>1 ? locationEntity.getState() : "";
								String contry =  locationEntity.getCountry()!=null && locationEntity.getCountry().trim().length()>1 ? locationEntity.getCountry() : "";
								String zip =  locationEntity.getZipcode()!=null && locationEntity.getZipcode().trim().length()>1 ? locationEntity.getZipcode() : "";
								location.setAddress1(addr1);
								location.setAddress2(addr2);
								location.setCity(city);
								location.setCountry(contry);
								location.setState(state);
								location.setType(Util.getLocationType(locationEntity.getType()));
								location.setZipcode(zip);
								locations.add(location);

							}
							rep.setLocations(locations);
							if(user.getDisplayPicture()!=null)
							{
								rep.setdPicture(user.getDisplayPicture().getImageUrl());
								DisplayPicture displayPicture = new DisplayPicture();
								displayPicture.setDpId(user.getDisplayPicture().getDpId());
								displayPicture.setdPicture(user.getDisplayPicture().getImageUrl());
								rep.setProfilePicture(displayPicture);
							}
						}
						rep.setCompanyId(pharmaRepEntity.getCompanyId());
						rep.setCoveredArea(pharmaRepEntity.getCoveredArea());
						rep.setCoveredZone(pharmaRepEntity.getCoveredZone());
						rep.setManagerId(pharmaRepEntity.getManagerId());
						rep.setTherapeuticId(pharmaRepEntity.getTherapeuticId());
						if (pharmaRepEntity.getTherapeuticId() != null)
						{
							try
							{
								TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
										.findById(TherapeuticAreaEntity.class,Integer.parseInt(pharmaRepEntity.getTherapeuticId()));
								if (therapeuticAreaEntity != null)
									rep.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							} catch (Exception e) {
								// Ignore
							}
						}
						if (pharmaRepEntity.getCompanyId() != null)
						{
							try
							{
								CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,pharmaRepEntity.getCompanyId());
								if (companyEntity != null)
									rep.setCompanyName(companyEntity.getCompanyName());
							}
							catch (Exception e)
							{
								// Ignore
							}
						}
					}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rep;
	}

	public List<PharmaRep> getPharmaRepManager(Integer companyId) {
		List<PharmaRep> managers = new ArrayList<PharmaRep>();
		try {
			List<PharmaRepEntity> pharmaRepEntities = pharmaRepDAO.findByRoleIdCompanyId(companyId, 4);
			for (PharmaRepEntity pharmaRepEntity : pharmaRepEntities)
			{
				PharmaRep rep = new PharmaRep();

				UserEntity user = pharmaRepEntity.getUser();

				if (user != null) {
					rep.setEmailId(user.getEmailId());
					rep.setFirstName(user.getFirstName());
//					rep.setMiddleName(user.getMiddleName());
					rep.setLastName(user.getLastName());
					rep.setPhoneNo(user.getPhoneNo());
					rep.setAlias(user.getAlias());
					rep.setTitle(user.getTitle());
					rep.setAlternateEmailId(user.getAlternateEmailId());
					rep.setMobileNo(user.getMobileNo());

					RoleEntity role = user.getRole();
					if (role != null) {
						rep.setRoleName(role.getRoleName());
						rep.setRoleId(role.getRoleId());
					}
					List<Location> locations = new ArrayList<Location>();
					for (LocationEntity locationEntity : user.getLocations()) {
						Location location = new Location();
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setState(locationEntity.getState());
						location.setType(Util.getLocationType(locationEntity.getType()));
						location.setZipcode(locationEntity.getZipcode());
						locations.add(location);

					}
					rep.setLocations(locations);
					if(user.getDisplayPicture()!=null)
					{
						rep.setdPicture(user.getDisplayPicture().getImageUrl());
						DisplayPicture displayPicture = new DisplayPicture();
						displayPicture.setDpId(user.getDisplayPicture().getDpId());
						displayPicture.setdPicture(user.getDisplayPicture().getImageUrl());
						rep.setProfilePicture(displayPicture);
					}

				}
				rep.setCompanyId(pharmaRepEntity.getCompanyId());
				rep.setCoveredArea(pharmaRepEntity.getCoveredArea());
				rep.setCoveredZone(pharmaRepEntity.getCoveredZone());
				rep.setManagerId(pharmaRepEntity.getManagerId());
				rep.setTherapeuticId(pharmaRepEntity.getTherapeuticId());
				if (pharmaRepEntity.getTherapeuticId() != null) {
					try {
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
								.findById(TherapeuticAreaEntity.class,Integer.parseInt(pharmaRepEntity.getTherapeuticId()));
						if (therapeuticAreaEntity != null) {
							rep.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}
					} catch (Exception e) {
						// Ignore
					}
				}
				if (pharmaRepEntity.getCompanyId() != null) {
					try {
						CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,pharmaRepEntity.getCompanyId());
						if (companyEntity != null) {
							rep.setCompanyName(companyEntity.getCompanyName());
						}
					} catch (Exception e) {
						// Ignore
					}
				}

				managers.add(rep);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return managers;
	}

	public List<PharmaRep> getPharmaRepByManagerEmail(String managerEmail) {
		List<PharmaRep> reps = new ArrayList<PharmaRep>();
		try {
			List<PharmaRepEntity> pharmaRepEntities = pharmaRepDAO.findRepByManagerId(managerEmail);
			for (PharmaRepEntity pharmaRepEntity : pharmaRepEntities)
			{
				PharmaRep rep = new PharmaRep();

				UserEntity user = pharmaRepEntity.getUser();

				if (user != null) {
					rep.setEmailId(user.getEmailId());
					rep.setFirstName(user.getFirstName());
//					rep.setMiddleName(user.getMiddleName());
					rep.setLastName(user.getLastName());
					rep.setPhoneNo(user.getPhoneNo());
					rep.setAlias(user.getAlias());
					rep.setTitle(user.getTitle());
					rep.setAlternateEmailId(user.getAlternateEmailId());
					rep.setMobileNo(user.getMobileNo());
					rep.setRepId(pharmaRepEntity.getRepId());
					RoleEntity role = user.getRole();
					if (role != null)
					{
						rep.setRoleName(role.getRoleName());
						rep.setRoleId(role.getRoleId());
					}
					List<Location> locations = new ArrayList<Location>();
					for (LocationEntity locationEntity : user.getLocations())
					{
						Location location = new Location();
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setState(locationEntity.getState());
						location.setType(Util.getLocationType(locationEntity.getType()));
						location.setZipcode(locationEntity.getZipcode());
						locations.add(location);

					}
					rep.setLocations(locations);

					if(user.getDisplayPicture()!=null)
					{
						rep.setdPicture(user.getDisplayPicture().getImageUrl());
						DisplayPicture displayPicture = new DisplayPicture();
						displayPicture.setDpId(user.getDisplayPicture().getDpId());
						displayPicture.setdPicture(user.getDisplayPicture().getImageUrl());
						rep.setProfilePicture(displayPicture);
					}

				}
				rep.setCompanyId(pharmaRepEntity.getCompanyId());
				rep.setCoveredArea(pharmaRepEntity.getCoveredArea());
				rep.setCoveredZone(pharmaRepEntity.getCoveredZone());
				rep.setManagerId(pharmaRepEntity.getManagerId());
				rep.setManagerEmail(pharmaRepEntity.getManagerEmail());
				rep.setTherapeuticId(pharmaRepEntity.getTherapeuticId());
				if (pharmaRepEntity.getTherapeuticId() != null)
				{
					TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(pharmaRepEntity.getTherapeuticId()));
					if (therapeuticAreaEntity != null)
					{
						rep.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
					}
				}
				if (pharmaRepEntity.getCompanyId() != null)
				{
					CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,pharmaRepEntity.getCompanyId());
					if (companyEntity != null) {
						rep.setCompanyName(companyEntity.getCompanyName());
					}

				}

				reps.add(rep);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return reps;
	}

	public void getRepManagerSurveys(Model model) {
		String loginId=(String) model.asMap().get("loginId");
		PharmaRepEntity pharmaRepEntity=pharmaRepDAO.findByLoginId(loginId);
		if(pharmaRepEntity!=null){
			List<SurveyEntity> surveys=surveyDAO.findSurveysByCompanyId(pharmaRepEntity.getCompanyId());


		}


	}

}
