package com.medrep.app.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.model.AppResponse;
import com.medrep.app.model.DoctorRegisteredCampaign;
import com.medrep.app.model.PatientAbout;
import com.medrep.app.model.PatientRegisteredCampaign;
import com.medrep.app.model.PharmaCampStats;
import com.medrep.app.model.PharmaCampaignModel;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.PharmaCampaignService;
import com.medrep.app.util.MedRepProperty;

@RestController
@RequestMapping("/api/campaign")
public class PharmaCampaignController {

	
	private static final Logger logger = LoggerFactory.getLogger(PharmaCampaignController.class);


	@Autowired
	PharmaCampaignService  pharmaCampaignService;
	

	
	public static final String GET_CAMPAIGNS_COUNT = "/getListCount";

	public static final String GET_DOCTOR_CAMPAIGNS = "/getDoctorCampaigns";
	public static final String GET_PATIENT_CAMPAIGNS = "/getPatientCampaigns";
	public static final String GET_COMPANY_CAMPAIGNS = "/getCompanyCampaigns";
	
	public static final String GET_LIST_REGISTERED_DOCTORS = "/getListRegisteredDoctor";

	public static final String GET_PATIENT_REGISTERED_CAMPAIGNS = "/getPatientRegisteredCampaigns";
	public static final String GET_DOCTOR_REGISTERED_CAMPAIGNS = "/getDoctorRegisteredCampaigns";

	public static final String GET_SEND_EMAIL_SAMPLE = "/sendEmailSample";

	public static final String GET_DOCTOR_REG_CAMPAIGNSID = "/getDocRegisterByCampId";

	public static final String GET_PATIENT_REG_CAMPID_DOCID = "/getPatRegisterByCampIdDocId";

	public static final String POST_DOCTOR_REGISTER = "/postDoctorRegisterCampaign";
	public static final String POST_PATIENT_REGISTER = "/postPatientRegisterCampaign";
	
	public static final String LIST_OF_UNTREATED_PATIENTS = "/listofUntreatedPatient";

	public static final String SEND_PUSH_NOTIFICATION_UN_PATIENTS = "/sendPushNotificationPatients";

	


	@RequestMapping(value = PharmaCampaignController.GET_DOCTOR_REG_CAMPAIGNSID, method = RequestMethod.GET)
    public @ResponseBody List<DoctorRegisteredCampaign> getDoctorRegByCampId(@RequestParam Integer campaignId) {
        logger.info("Request received for update Notfication");
        List<DoctorRegisteredCampaign> response = new ArrayList<DoctorRegisteredCampaign>();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			
             			response=pharmaCampaignService.getDoctorRegisterByCampId(campaignId);
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();

    		}
            logger.info("response completed");
            return response;
        
        }

	
	@RequestMapping(value = PharmaCampaignController.GET_PATIENT_REG_CAMPID_DOCID, method = RequestMethod.GET)
    public @ResponseBody List<PatientRegisteredCampaign> getPatRegByCampDocId(@RequestParam Integer campaignId,@RequestParam Integer doctorId) {
        logger.info("Request received for update Notfication");
        List<PatientRegisteredCampaign> response = new ArrayList<PatientRegisteredCampaign>();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			
             			response=pharmaCampaignService.getPatRegByCampAndDocId(doctorId,campaignId);
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();

    		}
            logger.info("response completed");
            return response;
        
        }
	
	
	@RequestMapping(value = PharmaCampaignController.LIST_OF_UNTREATED_PATIENTS, method = RequestMethod.GET)
    public @ResponseBody List<PatientAbout> listofUntreatedPatient(@RequestParam Integer campaignId) {
        logger.info("Request received for update Notfication");
        List<PatientAbout> response = new ArrayList<PatientAbout>();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			
             			response=pharmaCampaignService.getListofUntreatedPatient(campaignId);
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();

    		}
            logger.info("response completed");
            return response;
        
        }
	
	@RequestMapping(value = PharmaCampaignController.GET_DOCTOR_CAMPAIGNS, method = RequestMethod.GET)
    public @ResponseBody List<PharmaCampStats> getDoctorCampaigns() {
        logger.info("Request received for update Notfication");
        List<PharmaCampStats> response = new ArrayList<PharmaCampStats>();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			response=pharmaCampaignService.getDoctorCampaigns(loginId);
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();

    		}
            logger.info("response completed");
            return response;
        
        }
	
	@RequestMapping(value = PharmaCampaignController.GET_COMPANY_CAMPAIGNS, method = RequestMethod.GET)
    public @ResponseBody List<PharmaCampaignModel> getCompanyCampaigns() {
        logger.info("Request received for Company Campaigns");
        List<PharmaCampaignModel> response = new ArrayList<PharmaCampaignModel>();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			response=pharmaCampaignService.getCompanyCampaigns(loginId);
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();

    		}
            logger.info("response completed");
            return response;
        
        }
	
	@RequestMapping(value = PharmaCampaignController.GET_CAMPAIGNS_COUNT, method = RequestMethod.GET)
    public @ResponseBody AppResponse getCountForNear(@RequestParam Integer campaignId) {
        logger.info("Request received for update Notfication");
        AppResponse response = new AppResponse();
        try
        {
        	 UserSecurityContext context = null;
        	 if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             	if(context != null)
             	{
		        	response.setResult(pharmaCampaignService.getCampaignCount(campaignId));
		            response.setStatus("OK");
             	}
             }
        	 else
        	 {
        		 response.setMessage("Invalid token, can not get user profile.");
                 response.setStatus("Error");
        	 }
        }
        catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("Error while updating Notifcation");
            response.setStatus("Error");

		}

        return response;
    }
	
	
	@RequestMapping(value = PharmaCampaignController.POST_DOCTOR_REGISTER, method = RequestMethod.POST)
    public @ResponseBody AppResponse registerDoctorCampaigns(@RequestBody DoctorRegisteredCampaign doctorRegisteredCampaign, Model model) {
        logger.info("Request received forcampaign Register doctor");
        AppResponse response = new AppResponse();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			model.addAttribute("doctorRegisteredCampaign", doctorRegisteredCampaign);
             			model.addAttribute("loginId", loginId);
             			pharmaCampaignService.doctorRegisterCampaign(model);
             			response.setMessage("Successfully Registered.");
    		            response.setStatus("Success");
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();
    			response.setMessage("Error occurred while campaing Register");
	            response.setStatus("Error");
    		}
            logger.info("response completed");
            return response;
        
        }
	
	
	
	@RequestMapping(value = PharmaCampaignController.POST_PATIENT_REGISTER, method = RequestMethod.POST)
    public @ResponseBody AppResponse registerPatientCampaigns(@RequestBody PatientRegisteredCampaign patientRegisteredCampaign, Model model) {
        logger.info("Request received for campaign Register patient");
        AppResponse response = new AppResponse();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			model.addAttribute("patientRegisteredCampaign", patientRegisteredCampaign);
             			model.addAttribute("loginId", loginId);
             			pharmaCampaignService.patientRegisterCampaign(model);
             			response.setMessage("Successfully Registered.");
    		            response.setStatus("Success");
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();
    			response.setMessage("Error occurred while campaing Register");
	            response.setStatus("Error");
    		}
            logger.info("response completed");
            return response;
        
        }
	
	@RequestMapping(value = PharmaCampaignController.SEND_PUSH_NOTIFICATION_UN_PATIENTS, method = RequestMethod.POST)
    public @ResponseBody AppResponse sendPushNotfUnTreatedPatient(@RequestBody PharmaCampStats pharmaCampStats) {
        logger.info("Request received for campaign Register patient");
        AppResponse response = new AppResponse();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			
             			List<DeviceTokenEntity> devices=pharmaCampaignService.sendPushNotfUnTreatedPatient(pharmaCampStats);
             			String message=pharmaCampaignService.getCampaignName(pharmaCampStats.getCampaignID());
             			if(devices!=null){
             				pharmaCampaignService.pushMessage(message, devices);
            			}
             			response.setMessage("Successfully Sent Notifications.");
    		            response.setStatus("Success");
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();
    			response.setMessage("Error occurred while campaing Register");
	            response.setStatus("Error");
    		}
            logger.info("response completed");
            return response;
        
        }
	
	
	
	@RequestMapping(value = PharmaCampaignController.GET_LIST_REGISTERED_DOCTORS, method = RequestMethod.GET)
    public @ResponseBody List<DoctorRegisteredCampaign> getRegisteredDoctors(@RequestParam Integer campaignId) {
        logger.info("Request received for update Notfication");
        List<DoctorRegisteredCampaign> response = new ArrayList<DoctorRegisteredCampaign>();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			response=pharmaCampaignService.getRegisteredDoctors(campaignId);
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();
    			

    		}
            logger.info("response completed");
            return response;
        
        }
	
	
	@RequestMapping(value = PharmaCampaignController.GET_PATIENT_CAMPAIGNS, method = RequestMethod.GET)
    public @ResponseBody List<PharmaCampStats> getPatientCampaigns() {
        logger.info("Request received for update Notfication");
        List<PharmaCampStats> response = new ArrayList<PharmaCampStats>();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			response=pharmaCampaignService.getPatientCampaigns(loginId);
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();
    			

    		}
            logger.info("response completed");
            return response;
        
        }
	
	@RequestMapping(value = PharmaCampaignController.GET_PATIENT_REGISTERED_CAMPAIGNS, method = RequestMethod.GET)
    public @ResponseBody AppResponse getPatientRegisteredCampaigns(Model model) {
        logger.info("Request received for registered Campaign");
		AppResponse response = new AppResponse();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			String  loginId = context.getUsername();
             			model.addAttribute("username", context.getUsername());
             			pharmaCampaignService.getPatientRegisteredCampaigns(model);
    					response.setStatus("success");
    					response.setResult(model.asMap().get("patientRegistedInfo"));	
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();
    			

    		}
            logger.info("response completed");
            return response;
        
        }
	
	@RequestMapping(value = PharmaCampaignController.GET_DOCTOR_REGISTERED_CAMPAIGNS, method = RequestMethod.GET)
    public @ResponseBody AppResponse getDoctorRegisteredCampaigns(Model model) {
        logger.info("Request received for registered Campaign");
		AppResponse response = new AppResponse();

        try{
       	 	UserSecurityContext context = null;
        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             		context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             		if(context != null)
             		{
             			
             			model.addAttribute("username", context.getUsername());
             			pharmaCampaignService.getDoctorRegisteredCampaigns(model);
    					response.setStatus("success");
    					response.setResult(model.asMap().get("doctorRegistedInfo"));	
             		}
             	
             	}
        	}
        	catch(Exception e)
    		{
    			e.printStackTrace();
    			

    		}
            logger.info("response completed");
            return response;
        
        }
	
	@RequestMapping(value = PharmaCampaignController.GET_SEND_EMAIL_SAMPLE, method = RequestMethod.GET)
	public @ResponseBody AppResponse sendEmailSample(@RequestParam Integer campaignId) {
		        logger.info("Request received for Get Survey Pending Dcotors");
		        AppResponse response=new AppResponse();
		        UserSecurityContext context = null;
		        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
		        {
		        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		        	if(context != null)
		        	{
		        		try
		        		{
		        			

		            			String to=MedRepProperty.getInstance().getProperties("surveys.reports.admin");
		            			String email="";
		            			String  loginId = context.getUsername();
		            			pharmaCampaignService.sendSampleMail(campaignId,loginId);
		            			/*if(doctorId!=null && doctorId!=0){
		            				Doctor d=doctorService.findDoctorById(doctorId+"");
		            				name=d.getFirstName() +" "+d.getLastName();
		            				email=d.getEmailId();
		            			}else if(repId!=null && repId!=0){
		            				PharmaRep pharma=medrepService.getPharamaRep(repId+"");
		            				name=pharma.getFirstName()+" "+pharma.getLastName();
		            				email=pharma.getEmailId();
		            			}
		            			Mail mail = new Mail();
								mail.setMailTo(user.getEmailId());
								mail.setTemplateName(EmailService.DOCTOD_ACCOUNT_ACTIVATION);
								mail.setValueMap(valueMap);
								emailService.sendMail(mail);

		            			SurveyEntity survey=surveyService.getSurvey(surveyId);
		            			if(name!=null&& name.length()>0){
		            				String msg="<br/><b>SurveyId :</b> <a target='_blank' href='"+survey.getSurveyUrl()+"'>"+survey.getSurveyId()+"</a><br/><b>Survey Title :</b> "+survey.getSurveyTitle()+"<br/><b>Survey Description :</b> "+survey.getSurveyDescription()+"<br/><b>Requested Doctor :</b> "+email;
		            				emailService.sendMail(to,null,null,name+" requested a report for the survey","Hi Admin,<br/>"+name+" requested a report for the following survey. "+msg+"<br/><br/>Regards,<br/>MedRep Team.");
		            			}*/

		        			

		        			response.setResult("Your request has been submitted. You will receive the sample in shortly.");
		        			response.setStatus("success");
		        		}
		        		catch(Exception e)
		        		{
		        			e.printStackTrace();
		        			response.setStatus("error");
		        			response.setMessage("[API Error]"+e.getMessage());
		        		}
		        	}
		        }
		        return response;
		    }
}
