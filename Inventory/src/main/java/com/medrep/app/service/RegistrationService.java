package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.LocationDAO;
import com.medrep.app.dao.PatientDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.RoleDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.dao.UserSecurityDAO;
import com.medrep.app.entity.DisplayPictureEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.RoleEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.entity.UserSecurityEntity;
import com.medrep.app.model.AppResponse;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.ForgotPassword;
import com.medrep.app.model.Location;
import com.medrep.app.model.Mail;
import com.medrep.app.model.OTP;
import com.medrep.app.model.Patient;
import com.medrep.app.model.PharmaRep;
import com.medrep.app.model.SMS;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.OTPUtil;
import com.medrep.app.util.PasswordProtector;
import com.medrep.app.util.Util;

@Service("registrationService")
@Transactional
public class RegistrationService {

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	PatientDAO patientDAO;
	
	@Autowired
	LocationDAO locationDAO;
	@Autowired
	UserSecurityDAO securityDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	OTPService otpService;

	@Autowired
	UserDAO userDAO;

	@Autowired
	PharmaRepDAO pharmaRepDAO;

	@Autowired
	EmailService emailService;

	@Autowired
	SMSService smsService;

	@Autowired
	DisplayPictureDAO dpDAO;

	private static final Log log = LogFactory.getLog(RegistrationService.class);

	public void createDoctor(Model model) throws IOException
	{

			Doctor doctor=(Doctor) model.asMap().get("doctor");
			UserSecurityEntity securityEntity = new UserSecurityEntity();
			securityEntity.setPassword(PasswordProtector.encrypt(doctor.getPassword()));
			securityEntity.setLoginId(doctor.getUsername());
			securityEntity.setCreatedBy("App");
			securityEntity.setCreatedDate(new Date());
			securityEntity.setStatus("Not Verified");
			securityEntity.setType("USER");

			RoleEntity role = roleDAO.findById(RoleEntity.class, doctor.getRoleId());
			UserEntity user = new UserEntity();
			user.setAlias(doctor.getAlias());
			user.setAlternateEmailId(doctor.getAlternateEmailId());
			user.setEmailId(doctor.getEmailId());
			user.setFirstName(doctor.getFirstName());
//			user.setMiddleName(doctor.getMiddleName());
			user.setLastName(doctor.getLastName());
			user.setMobileNo(doctor.getMobileNo());
			user.setTitle(Util.isEmpty(doctor.getTitle())?"Dr":doctor.getTitle());
			user.setSecurity(securityEntity);
			user.setStatus("Inactive");
			com.medrep.app.entity.DoctorEntity doctorEntity = new com.medrep.app.entity.DoctorEntity();
			doctorEntity.setQualification(doctor.getQualification());
			doctorEntity.setRegistrationNumber(doctor.getRegistrationNumber());
			doctorEntity.setRegistrationYear(doctor.getRegistrationYear());
			doctorEntity.setStateMedCouncil(doctor.getStateMedCouncil());
			doctorEntity.setTherapeuticId(doctor.getTherapeuticId());
			doctorEntity.setUser(user);
			doctorEntity.setStatus("New");
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
				locationEntity.setType(location.getType());
				locationEntity.setUser(user);
				locationEntity.setLocationType(location.getLocationType());
				locations.add(locationEntity);
			}

			user.setLocations(locations);
			doctorDAO.persist(doctorEntity);
			user.setRole(role);
			DisplayPictureEntity dpEntity = null;

			if(doctor.getProfilePicture()!=null && doctor.getProfilePicture().getImgData()!=null){
					String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/doctors/";
					_displayPic += FileUtil.copyBinaryData(doctor.getProfilePicture().getImgData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc")+File.separator+"displaypictures" + File.separator + "doctors",doctor.getProfilePicture().getFileName());
					dpEntity=new DisplayPictureEntity();
					dpEntity.setImageUrl(_displayPic);
			}else
				dpEntity=dpDAO.findById(DisplayPictureEntity.class, 0);

			if(dpEntity != null)
			{
				user.setDisplayPicture(dpEntity);
			}
			doctorDAO.merge(doctorEntity);
			List<com.medrep.app.entity.LocationEntity> localEntity=locationDAO.findLocationsByUserId(doctorEntity.getUser().getUserId());
			
			for(LocationEntity location : localEntity)
			{
				location.setDoctorId(doctorEntity.getDoctorId());
				locationDAO.merge(location);
			}
			OTP emailOTP = new OTP();
			OTP mobileOTP = new OTP();

			emailOTP.setSecurityId(securityEntity.getUserSecId());
			emailOTP.setVerificationId(user.getEmailId());
			emailOTP.setType("EMAIL");
			emailOTP = otpService.reCreateOTP(emailOTP);
			sendEmailForVerification(emailOTP, Util.getTitle(user.getTitle())+ user.getFirstName() + " " + user.getLastName());

			mobileOTP.setSecurityId(securityEntity.getUserSecId());
			mobileOTP.setVerificationId(user.getMobileNo());
			mobileOTP.setType("MOBILE");
			mobileOTP = otpService.reCreateOTP(mobileOTP);
			sendSMSForVerification(mobileOTP);
			emailOTP.setOtp(mobileOTP.getOtp());
			sendEmailOTP(emailOTP);
	}
	
	
	public Boolean createPatient(Model model) throws IOException, ParseException
	{

			Boolean result;
			Patient patient=(Patient) model.asMap().get("patient");
			OTP otp = new OTP();
			otp.setOtp(patient.getPassword());
			otp.setVerificationId(patient.getMobileNo());
			otp.setType("MOBILE");
			OTP otpentity=otpService.verifyOTP(otp);
			
			if ("INVALID".equalsIgnoreCase(otpentity.getStatus()) || "EXPIRED".equalsIgnoreCase(otpentity.getStatus()) ){
				result=false;
				}else{
					RoleEntity role = roleDAO.findById(RoleEntity.class, patient.getRoleId());
					UserSecurityEntity 	securityEntity=new UserSecurityEntity();
					securityEntity.setPassword(PasswordProtector.encrypt(patient.getPassword()));
					securityEntity.setLoginId(patient.getMobileNo());
					securityEntity.setCreatedBy("App");
					securityEntity.setCreatedDate(new Date());
					securityEntity.setStatus("Verified");
					securityEntity.setType("USER");
					UserEntity user = new UserEntity();
					user.setRole(role);
					user.setSecurity(securityEntity);
					user.setAlias(patient.getAlias());
					user.setAlternateEmailId(patient.getAlternateEmailId());
					user.setEmailId(patient.getEmailId());
					user.setFirstName(patient.getFirstName());
					user.setMiddleName(patient.getMiddleName());
					user.setLastName(patient.getLastName());
					user.setMobileNo(patient.getMobileNo());
					user.setStatus("active");
					com.medrep.app.entity.PatientEntity patientEntity = new com.medrep.app.entity.PatientEntity();
					SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
					Date date = dt.parse(patient.getDateofBirth());
					patientEntity.setDateofBirth(date);
					patientEntity.setAddress1(patient.getAddress1());
					patientEntity.setAddress2(patient.getAddress2());
					patientEntity.setCity(patient.getCity());
					patientEntity.setState(patient.getState());
					patientEntity.setZip(patient.getZip());
					patientEntity.setHeight(patient.getHeight());
					patientEntity.setWeight(patient.getWeight());
					patientEntity.setSex(patient.getSex());
					patientEntity.setMarriedstatus(patient.getMarriedstatus());
					patientEntity.setBloodGroup(patient.getBloodGroup());
					patientEntity.setUser(user);
					patientDAO.persist(patientEntity);
					result=true;
			}
			return result;
	}
	
	public String otpSendPatient(String phoneNumber){
		
		UserSecurityEntity security =  securityDAO.findByLoginId(phoneNumber);
		String otpvalue=(OTPUtil.generateIntToken());
		security.setPassword(PasswordProtector.encrypt(otpvalue));
		securityDAO.merge(security);
		
		return otpvalue;
	}

	public void createMedRep(Model model) throws IOException
	{
		PharmaRep pharmaRep=(PharmaRep) model.asMap().get("pharmaRep");

			UserSecurityEntity securityEntity = new UserSecurityEntity();
			securityEntity.setPassword(PasswordProtector.encrypt(pharmaRep.getPassword()));
			securityEntity.setLoginId(pharmaRep.getUsername());
			securityEntity.setCreatedBy("App");
			securityEntity.setCreatedDate(new Date());
			securityEntity.setStatus("Not Verified");
			securityEntity.setType("USER");
			RoleEntity role = roleDAO.findById(RoleEntity.class, pharmaRep.getRoleId());
			UserEntity user = new UserEntity();
			user.setAlias(pharmaRep.getAlias());
			user.setAlternateEmailId(pharmaRep.getAlternateEmailId());
			user.setEmailId(pharmaRep.getEmailId());
			user.setFirstName(pharmaRep.getFirstName());
//			user.setMiddleName(pharmaRep.getMiddleName());
			user.setLastName(pharmaRep.getLastName());
			user.setMobileNo(pharmaRep.getMobileNo());
			user.setStatus("Inactive");
			user.setTitle(pharmaRep.getTitle());
			user.setSecurity(securityEntity);
			ArrayList<com.medrep.app.entity.LocationEntity> locations = new ArrayList<com.medrep.app.entity.LocationEntity>();

			for(Location location : pharmaRep.getLocations())
			{
				com.medrep.app.entity.LocationEntity locationEntity = new com.medrep.app.entity.LocationEntity();
				locationEntity.setAddress1(location.getAddress1());
				locationEntity.setAddress2(location.getAddress2());
				locationEntity.setCity(location.getCity());
				locationEntity.setState(location.getState());
				locationEntity.setZipcode(location.getZipcode());
				locationEntity.setCountry(location.getCountry());
				locationEntity.setType(location.getType());
				locationEntity.setUser(user);
				locations.add(locationEntity);
			}
			user.setLocations(locations);
			PharmaRepEntity pharmaEntity = new PharmaRepEntity();
			pharmaEntity.setUser(user);
			pharmaEntity.setCompanyId(pharmaRep.getCompanyId());
			pharmaEntity.setCoveredArea(pharmaRep.getCoveredArea());
			pharmaEntity.setCoveredZone(pharmaRep.getCoveredZone());
			pharmaEntity.setManagerId(pharmaRep.getManagerId());
			pharmaEntity.setManagerEmail(pharmaRep.getManagerEmail());
			pharmaEntity.setTherapeuticId(pharmaRep.getTherapeuticId());
			pharmaEntity.setStatus("New");

			user.setRole(role);

			DisplayPictureEntity dpEntity=null;

			if(pharmaRep.getProfilePicture()!=null && pharmaRep.getProfilePicture().getImgData()!=null){
				dpEntity=new DisplayPictureEntity();
				String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/pharmarep/";
					_displayPic += FileUtil.copyBinaryData(pharmaRep.getProfilePicture().getImgData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc")+File.separator+"displaypictures" + File.separator + "pharmarep",pharmaRep.getProfilePicture().getFileName());
					dpEntity=new DisplayPictureEntity();
					dpEntity.setImageUrl(_displayPic);
			}else
				dpEntity = dpDAO.findById(DisplayPictureEntity.class, 0);

			if(dpEntity != null)
			{
				user.setDisplayPicture(dpEntity);
			}
			pharmaRepDAO.persist(pharmaEntity);

			OTP emailOTP = new OTP();
			OTP mobileOTP = new OTP();
			emailOTP.setSecurityId(securityEntity.getUserSecId());
			emailOTP.setVerificationId(user.getEmailId());
			emailOTP.setType("EMAIL");
			otpService.reCreateOTP(emailOTP);

			sendEmailForVerification(emailOTP, Util.getTitle(user.getTitle())+user.getFirstName() + " " + user.getLastName());
			mobileOTP.setSecurityId(securityEntity.getUserSecId());
			mobileOTP.setVerificationId(user.getMobileNo());
			mobileOTP.setType("MOBILE");
			otpService.reCreateOTP(mobileOTP);
			sendSMSForVerification(mobileOTP);
			emailOTP.setOtp(mobileOTP.getOtp());
			sendEmailOTP(emailOTP);

	}

	public boolean verifyEmail(String emailId)
	{
		boolean result = true;
		UserEntity user = userDAO.findByEmailId(emailId);
		if(user != null && user.getEmailId() != null)
		{
			result = false;
		}

		return result;
	}

	public boolean verifyMobileNO(String mobileNo)
	{
		boolean result = true;
		UserEntity user = userDAO.findByMobileNo(mobileNo);
		if(user != null && user.getEmailId() != null)
		{
			result = false;
		}

		return result;
	}
	
	public AppResponse verifyPatientMobileNO(String mobileNo)
	{
		AppResponse	response= new AppResponse();
		UserEntity user = userDAO.findByMobileNo(mobileNo);
		if(user.getMobileNo() != null )
		{
			if(user.getRole().getRoleId()==5){
				response.setStatus("True");
				response.setMessage("Registered");
			} else{
				response.setStatus("False");
				response.setMessage("Already Registered as "+user.getRole().getRoleName());								
			}
			
			
		}else{
			response.setStatus("True");
			response.setMessage("NOTRegistered");			
		}

		return response;
	}

	public int getUserSecurityId(String emailId)
	{
		try{
			UserSecurityEntity security =  securityDAO.findByLoginId(emailId);
			return security!=null ? security.getUserSecId() : 0;
		}catch(Exception e){
			return 0;
		}
	}

	public void updatePassword(ForgotPassword forgotPassword)
	{
		UserSecurityEntity security =  securityDAO.findByLoginId(forgotPassword.getUserName());
		security.setPassword(PasswordProtector.encrypt(forgotPassword.getNewPassword()));
		securityDAO.merge(security);
	}

	public boolean validateEmail(String emailId)
	{
		boolean result = false;
		UserEntity user = userDAO.findByEmailId(emailId);
		if(user == null)
		{
			result = true;
		}

		return result;
	}

	private void sendEmailForVerification(OTP emailOTP, String name)
	{
		Mail mail = new Mail();
		mail.setTemplateName(EmailService.VERIFY_EMAIL);
		mail.setMailTo(emailOTP.getVerificationId());
		Map<String, String> domainValue = new HashMap<String, String>();
		String url = MedRepProperty.getInstance().getProperties("medrep.home") + "web/registration/verifyEmail/";
		String token = emailOTP.getVerificationId() + "MEDREP" + PasswordProtector.decrypt(emailOTP.getOtp());
		String encryteptoken = PasswordProtector.encrypt(token) ;
		encryteptoken = encryteptoken.replace('/', '.').replace('+','-');
		url = url + encryteptoken + "/";
		domainValue.put("URL", url);
		domainValue.put("NAME", name);
		mail.setValueMap(domainValue);
		emailService.sendMail(mail);
	}

	private void sendSMSForVerification(OTP smsOTP)
	{

		SMS sms = new SMS();
		sms.setPhoneNumber(smsOTP.getVerificationId());
		String otp = PasswordProtector.decrypt(smsOTP.getOtp());
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("OTP", otp);
		sms.setTemplate(SMSService.VERIFY_NO);
		sms.setValueMap(valueMap);
		smsService.sendSMS(sms);

	}

	private void sendEmailOTP(OTP emailOTP)
	{

		Mail mail = new Mail();
		mail.setTemplateName(EmailService.EMAIL_OTP);
		mail.setMailTo(emailOTP.getVerificationId());
		Map<String, String> domainValue = new HashMap<String, String>();
		String otp = PasswordProtector.decrypt(emailOTP.getOtp());
		domainValue.put("OTP", otp);
		mail.setValueMap(domainValue);
		emailService.sendMail(mail);

	}


}
