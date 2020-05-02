package com.medrep.app.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.model.AppResponse;
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.ForgotPassword;
import com.medrep.app.model.Location;
import com.medrep.app.model.Mail;
import com.medrep.app.model.OTP;
import com.medrep.app.model.Patient;
import com.medrep.app.model.PharmaRep;
import com.medrep.app.model.SMS;
import com.medrep.app.model.User;
import com.medrep.app.service.DisplayPictureService;
import com.medrep.app.service.DoctorService;
import com.medrep.app.service.EmailService;
import com.medrep.app.service.OTPService;
import com.medrep.app.service.RegistrationService;
import com.medrep.app.service.SMSService;
import com.medrep.app.service.UserService;
import com.medrep.app.util.PasswordProtector;

@Controller
@RequestMapping("/preapi/registration")
public class RegistrationController {

	public static final String SIGN_UP_DOCTOR = "/signupDoctor";
	public static final String SIGN_UP_PATIENT = "/signupPatient";
	public static final String SIGN_UP_REP = "/signupRep";
	public static final String PHONE_VERIFICATION = "/verifyMobileNo";
	public static final String UPLOAD_DP = "/uploadDP";
	public static final String GET_OTP = "/getOTP/{token}";
	public static final String GET_PATIENT_MOBILE_VERIFICATION = "/getPatientVerifyNumber/{token}";
	public static final String NEW_SMSOTP = "/getNewSMSOTP/{token}";
	public static final String NEW_EMAILOTP = "/getNewEMAILOTP/{token}";
	public static final String FORGOT_PASSWORD = "/forgotPassword";
	public static final String SEND_SMS = "/sendSMS/{token}";
	public static final String SEND_EMAIL = "/sendEmail/{token}";

	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private OTPService otpService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private DisplayPictureService dpService;

	@Autowired
	UserService userService;

	@Autowired
	EmailService emailService;

	@Autowired
	SMSService smsService;


	/**
	 * Method will be called in initial page load at GET /employee-module
	 * 
	 * */
	@RequestMapping(value = RegistrationController.SIGN_UP_DOCTOR, method = RequestMethod.POST)
	public @ResponseBody AppResponse signupDoctor(@RequestBody Doctor doctor,Model model) {
		logger.info("Request received for login");
		model.addAttribute("doctor", doctor);
		AppResponse response = new AppResponse(); 
		try
		{
			 if(doctor.getEmailId()==null || doctor.getMobileNo()==null || doctor.getFirstName()==null || doctor.getLastName()==null				 
					 ||  doctor.getRegistrationYear()==null || doctor.getRegistrationNumber()==null || doctor.getStateMedCouncil()==null || doctor.getTherapeuticId()==null)
			{
				response.setMessage("Please fill all the Mandatory Fields");
				response.setStatus("Fail");
			}
			 else if(!registrationService.verifyEmail(doctor.getEmailId()))
			{
				response.setMessage("The Email Id you provided is already exiting in our system. Please use other email id.");
				response.setStatus("Fail");
			}
			 else if(doctor.getMobileNo().length()>12)
			 {
				 response.setMessage("The Mobile Number length is too long.");
					response.setStatus("Fail");
			 }
			else if(!registrationService.verifyMobileNO(doctor.getMobileNo()))
			{
				response.setMessage("The Mobile Number you provided is already exiting in our system. Please use other Mobile No.");
				response.setStatus("Fail");
			}
			 else
			 {
			for (Location location:doctor.getLocations())
			{
				if(location.getAddress1()==null || location.getAddress2()==null || location.getCity()==null || location.getState()==null  )	//|| location.getCountry()==null
				{
					response.setMessage("Please fill all the Mandatory Fields");
					response.setStatus("Fail");
				}
			}
			 }
			if(response.getStatus()!="Fail")
			{        	
				registrationService.createDoctor(model);
				response.setMessage("Success");
				response.setStatus("OK");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("Error occuured while registing doctor");
			response.setStatus("Error");
		}

		return response;
	}
	

	
	@RequestMapping(value = RegistrationController.SIGN_UP_PATIENT, method = RequestMethod.POST)
	public @ResponseBody AppResponse signupPatient(@RequestBody Patient patient,Model model) {
		logger.info("Request received for login");
		model.addAttribute("patient", patient);
		AppResponse response = new AppResponse(); 
		try
		{
			if(patient.getMobileNo()==null || patient.getFirstName()==null || patient.getEmailId()==null )
			{
				response.setMessage("Please fill all the Mandatory Fields");
				response.setStatus("Fail");
			}
			 else if(!registrationService.verifyEmail(patient.getEmailId()))
			{
				response.setMessage("The Email Id you provided is already exiting in our system. Please use other email id.");
				response.setStatus("Fail");
			}
			else if(!registrationService.verifyMobileNO(patient.getMobileNo()))
			{
				response.setMessage("The Mobile Number you provided is already exiting in our system. Please use other Mobile No.");
				response.setStatus("Fail");
			}
			 else
			 {
				if(patient.getDateofBirth()==null )
				{
					response.setMessage("Please fill all the Date of Birth Fields");
					response.setStatus("Fail");
				}
			}
			 
			if(response.getStatus()!="Fail")
			{        	
				if (registrationService.createPatient(model))
					{
					response.setMessage("Success");
					response.setStatus("OK");
					}else{
						response.setMessage("Fail");
						response.setStatus("OK");
					}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("Error occuured while registing doctor");
			response.setStatus("Error");
		}

		return response;
	}
	@RequestMapping(value = RegistrationController.SIGN_UP_REP, method = RequestMethod.POST)
	public @ResponseBody AppResponse signupRep(@RequestBody PharmaRep medrep,Model model) {
		logger.info("Request received for signin Rep");
		model.addAttribute("pharmaRep",medrep);
		AppResponse response = new AppResponse();
		try 
		{
			if(medrep.getEmailId()==null || medrep.getFirstName()==null || medrep.getLastName()==null || medrep.getMobileNo()==null
				|| medrep.getManagerEmail()==null || medrep.getCompanyId()==null || medrep.getTherapeuticId()==null
				|| medrep.getCoveredArea()==null || medrep.getCoveredZone()==null )
			{
				response.setMessage("Please fill all the Mandatory Fields");
				response.setStatus("Fail");	
			}
			else if(!registrationService.verifyEmail(medrep.getEmailId()))
			{
				response.setMessage("The Email Id you provided is already exiting in our system. Please use other email id.");
				response.setStatus("Fail");
			}
			else if(!registrationService.verifyMobileNO(medrep.getMobileNo()))
			{
				response.setMessage("The Mobile Number you provided is already exiting in our system. Please use other Mobile No.");
				response.setStatus("Fail");
			}
			else
			 {
			for (Location location:medrep.getLocations())
			{
				if(location.getAddress1()==null  || location.getCity()==null || location.getState()==null || location.getCountry()==null || location.getZipcode()==null)
				{
					response.setMessage("Please fill all the Mandatory Fields");
					response.setStatus("Fail");
				}
			}
			 }
			if(response.getStatus()!="Fail")
			{     
				registrationService.createMedRep(model);        	
				response.setMessage("Success");
				response.setStatus("OK");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			response = new AppResponse();
			response.setMessage("Error occuured while registing Medrep");
			response.setStatus("Error");
		} 
		return response;
	}


	@RequestMapping(value = RegistrationController.UPLOAD_DP, method = RequestMethod.POST)
	public @ResponseBody AppResponse uploadDP(Model model,@RequestBody DisplayPicture dp) 
	{
		logger.info("Request received for login");
		AppResponse response = new AppResponse();
		model.addAttribute("displayPicture", dp);
		try
		{
			if(dpService.uploadDisplayPicture(model))
			{
				response.setMessage("Success");
				response.setStatus("OK");
				response.setResult(model.asMap().get("url"));
			}
			else
			{
				response.setMessage("Invalid Email Id");
				response.setStatus("Error");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("Error occuured while registing doctor");
			response.setStatus("Error");
		}

		return response;
	}


	@RequestMapping(value = RegistrationController.GET_OTP, method = RequestMethod.GET)
	public @ResponseBody OTP getOTP(@PathVariable("token") String token) {
		logger.info("Request received for Get My OTP " + token);

		OTP otp = new OTP();
		otp.setVerificationId(token);
		otp = otpService.getOTP(otp);
		return otp;
	}
	
	@RequestMapping(value = RegistrationController.GET_PATIENT_MOBILE_VERIFICATION, method = RequestMethod.GET)
	public @ResponseBody AppResponse getMobileVerification(@PathVariable("token") String phonenumber) {
		logger.info("Request received for Get My OTP " + phonenumber);
		AppResponse response= new AppResponse();
		if(phonenumber.length()>12)
		 {
				response.setMessage("The Mobile Number length is too long.");
				response.setStatus("False");
		 }else{
			AppResponse response1= registrationService.verifyPatientMobileNO(phonenumber);
			if ( response1.getStatus()=="True")
			{
				if(response1.getMessage()=="Registered"){
					String otpvalue=registrationService.otpSendPatient(phonenumber);
					SMS sms = new SMS();
					sms.setPhoneNumber(phonenumber);
					sms.setTemplate(SMSService.VERIFY_NO);
					Map<String, String> valueMap = new HashMap<String, String>();
					valueMap.put("OTP", (otpvalue));
					sms.setValueMap(valueMap);
					smsService.sendSMS(sms);
					response=response1;
				}else{
					OTP otp = new OTP();
					otp.setVerificationId(phonenumber);
					otp.setType("MOBILE");
					otp = otpService.reCreateOTP(otp);
					SMS sms = new SMS();
					sms.setPhoneNumber(phonenumber);
					sms.setTemplate(SMSService.VERIFY_NO);
					Map<String, String> valueMap = new HashMap<String, String>();
					valueMap.put("OTP", PasswordProtector.decrypt(otp.getOtp()));
					sms.setValueMap(valueMap);
					smsService.sendSMS(sms);
					response=response1;

				}
		 }else{
				response=response1;
		 }			
		 }
		return response;
	}


	@RequestMapping(value = RegistrationController.PHONE_VERIFICATION, method = RequestMethod.GET)
	public @ResponseBody AppResponse verifyMobileNo(@RequestParam String token, @RequestParam String number) 
	{
		logger.info("Request received for Get My Profile");
		OTP otp = new OTP();
		otp.setOtp(token);
		otp.setVerificationId(number);
		otp.setType("MOBILE");
		AppResponse response = new AppResponse();
		try
		{
			otp =  otpService.verifyOTP(otp);
			if("VERIFIED".equals(otp.getStatus()))
			{
				System.out.println("OTP Verified sending Email Token="+number);
				Doctor doctor=doctorService.getDoctorBasicDetailsByMobile(number);
				System.out.println("Sendin gEmail to=="+doctor.getEmailId()+" & Name="+doctor.getFirstName());
				if(doctor!=null && doctor.getEmailId()!=null){
					doctorService.sendEmailAboutAccountActivation(doctor);
				}
				response.setMessage("Success");
				response.setStatus("OK");
			}
			else if("EXPIRED".equals(otp.getStatus()))
			{
				response.setMessage("OTP Expired. Please generate another OTP");
				response.setStatus("Fail");
			}
			else
			{
				response.setMessage("Invalid OTP");
				response.setStatus("Fail");
			}
		}
		catch(Exception e)
		{
			logger.equals(e.getStackTrace());
			response.setMessage("System error occured while verifying OTP");
			response.setStatus("Fail");
		}        

		return response;
	}

	@RequestMapping(value = RegistrationController.NEW_SMSOTP, method = RequestMethod.GET)
	public @ResponseBody AppResponse getNewSMSOTP(@PathVariable("token") String token) {
		logger.info("Request received for Get new SMS OTP");
		AppResponse response = new AppResponse();
		try
		{

			User user =  userService.findUserByEmailId(token);
			if(user != null)
			{
				OTP otp = new OTP();
				otp.setVerificationId(user.getMobileNo());
				otp.setType("MOBILE");
				otp = otpService.reCreateOTP(otp);
				SMS sms = new SMS();
				sms.setPhoneNumber(user.getMobileNo());
				sms.setTemplate(SMSService.VERIFY_NO);
				Map<String, String> valueMap = new HashMap<String, String>();
				valueMap.put("OTP", PasswordProtector.decrypt(otp.getOtp()));
				sms.setValueMap(valueMap);
				smsService.sendSMS(sms);
				Mail mail = new Mail();
				mail.setTemplateName(EmailService.EMAIL_OTP);
				mail.setMailTo(user.getEmailId());
				mail.setValueMap(valueMap);
				emailService.sendMail(mail);
				response.setMessage("Success");
				response.setStatus("OK");
			}
			else
			{
				response.setMessage("Invalid user Id");
				response.setStatus("Fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("Error occuured while creating OTP");
			response.setStatus("Error");
		}
		return response;
	}

	@RequestMapping(value = RegistrationController.NEW_EMAILOTP, method = RequestMethod.GET)
	public @ResponseBody AppResponse getNewEMAILOTP(@PathVariable("token") String token) {
		logger.info("Request received for Get My Profile");

		AppResponse response = new AppResponse();
		try
		{

			User user =  userService.findUserByEmailId(token);
			if(user != null)
			{
				OTP otp = new OTP();
				otp.setVerificationId(user.getEmailId());
				otp.setType("EMAIL");
				otpService.reCreateOTP(otp);
				response.setMessage("Success");
				response.setStatus("OK");
			}
			else
			{
				response.setMessage("Invalid user Id");
				response.setStatus("Fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("Error occuured while creating OTP");
			response.setStatus("Error");
		}
		return response;
	}


	@RequestMapping(value = RegistrationController.FORGOT_PASSWORD, method = RequestMethod.POST)
	public @ResponseBody AppResponse forgotPassword(@RequestBody ForgotPassword forgotPassword) 
	{
		logger.info("Request received for login");
		AppResponse response = new AppResponse();

		try
		{
			OTP otp = new OTP();
			otp.setOtp(forgotPassword.getOtp());
			User user =  userService.findUserByEmailId(forgotPassword.getUserName());
			if(forgotPassword.getNewPassword() != null && forgotPassword.getConfirmPassword() != null && forgotPassword.getNewPassword().equals(forgotPassword.getConfirmPassword()))
			{
				if(user != null)
				{
					otp.setVerificationId(user.getMobileNo());
					otp.setType("MOBILE");

					otpService.verifyOTP(otp);
					if("VERIFIED".equals(otp.getStatus()))
					{
						registrationService.updatePassword(forgotPassword);
						response.setMessage("Success");
						response.setStatus("OK");
					}
					else if("EXPIRED".equals(otp.getStatus()))
					{
						response.setMessage("OTP Expired. Please generate another OTP");
						response.setStatus("Fail");
					}
					else
					{
						response.setMessage("Invalid OTP");
						response.setStatus("Fail");
					}
				}
				else
				{
					response.setMessage("Invalid Login Id");
					response.setStatus("Fail");
				}
			}
			else
			{
				response.setMessage("Password and confirm  password does not match");
				response.setStatus("Fail");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("Error occuured while updating password");
			response.setStatus("Error");
		}

		return response;
	}

	@RequestMapping(value = RegistrationController.SEND_SMS, method = RequestMethod.GET)
	public @ResponseBody AppResponse sendSMS(@PathVariable("token") String token) {
		logger.info("Request received for Get My OTP " + token);
		SMS sms = new SMS();
		sms.setPhoneNumber(token);
		Map<String,String> valueMap = new HashMap<String, String>();
		valueMap.put("OTP","123456");
		sms.setValueMap(valueMap);
		sms.setTemplate(SMSService.VERIFY_NO);
		smsService.sendSMS(sms);
		System.out.println("Message sent successfylly");
		AppResponse response = new AppResponse();
		response.setStatus("OK");
		response.setMessage("Success");

		return response;
	}

	@RequestMapping(value = RegistrationController.SEND_EMAIL, method = RequestMethod.GET)
	public @ResponseBody AppResponse sendEmail(@PathVariable("token") String token) {
		logger.info("Request received for Get My OTP " + token);
		Mail mail = new Mail();
		mail.setMailTo(token);
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("NAME", "Umar Ashraf");
		valueMap.put("URL", "http://localhost:8090/MedrepApplication");
		mail.setValueMap(valueMap);

		AppResponse response = new AppResponse();
		response.setStatus("OK");
		response.setMessage("Success");

		return response;
	}


}
