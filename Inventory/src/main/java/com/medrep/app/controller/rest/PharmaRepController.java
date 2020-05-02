package com.medrep.app.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medrep.app.entity.SurveyEntity;
import com.medrep.app.model.Activity;
import com.medrep.app.model.AppResponse;
import com.medrep.app.model.Company;
import com.medrep.app.model.CompanySurvey;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorAppointment;
import com.medrep.app.model.Notification;
import com.medrep.app.model.NotificationDetails;
import com.medrep.app.model.PharmaRep;
import com.medrep.app.model.PharmaRepNotification;
import com.medrep.app.model.Survey;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.ActivityService;
import com.medrep.app.service.AppointmentService;
import com.medrep.app.service.CompanyService;
import com.medrep.app.service.DoctorRepService;
import com.medrep.app.service.MedRepService;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.SurveyService;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.FileUtil;


@Controller
@RequestMapping("/api/pharmarep")
public class PharmaRepController {

	public static final String GET_MY_NOTIFICATION = "/getMyNotifications/{startDate}";
	public static final String GET_NOTIFICATION_BY_ID = "/getNotificationById/{notificationId}";
	public static final String GET_MY_NOTIFICATION_CONTENT = "/getMyNotificationContent/{detailNotificationId}";
	public static final String UPDATE_MY_NOTIFICATION = "/updateMyNotification";
	public static final String GET_MY_APPOINTMENT = "/getMyAppointment/{startDate}";
	public static final String GET_MY_ALL_APPOINTMENT = "/getMyAppointment";
	public static final String GET_MY_UPCOMING_APPOINTMENT = "/getMyUpcomingAppointment";
	public static final String GET_MY_COMPLETED_APPOINTMENT = "/getMyCompletedAppointment";
	public static final String GET_APPOINTMENT_BY_REP = "/getAppointmentsByRep/{repId}";
	public static final String GET_MY_TEAM_APPOINTMENT = "/getMyTeamAppointment/{startDate}";
	public static final String GET_MY_PENDING_APPOINTMENT = "/getMyPendingAppointment";
	public static final String GET_MY_TEAM_PENDING_APPOINTMENT  = "/getMyTeamPendingAppointments";
	public static final String ACCEPT_APPOINTMENT = "/acceptAppointment";
	public static final String CREATE_APPOINTMENT = "/createAppointment";
	public static final String UPDATE_APPOINTMENT = "/updateAppointment";
	public static final String GET_MY_SURVEYS = "/getMyPendingSurveys";
	public static final String GET_MY_DOCTORS = "/getMyDoctors";
	public static final String UPDATE_MY_SURVEY_STATUS = "/updateMySurveyStatus";
	public static final String GET_MY_COMPANY_SURVEYS = "/getMyCompanySurveys/{surveyId}";
	public static final String GET_MY_COMPANY = "/getMyCompany";

	private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);


	@Autowired
	SurveyService surveyService;

	@Autowired
	AppointmentService appointmentService;

	@Autowired
	MedRepService medRepService;

	@Autowired
	DoctorRepService doctorRepService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	CompanyService companyService;

	@Autowired
	ActivityService activityService;

	/**
	 * Method will be called in initial page load at GET /employee-module
	 *
	 * */
	@RequestMapping(value = PharmaRepController.UPDATE_MY_NOTIFICATION, method = RequestMethod.POST)
    public @ResponseBody AppResponse updateMyNotification(@RequestBody PharmaRepNotification pharmaRepNotification) {
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
             		String  loginId = context.getUsername();
		        	notificationService.updatePharmaRepNotification(pharmaRepNotification, loginId);
		        	response.setMessage("Success");
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

	@RequestMapping(value = PharmaRepController.GET_MY_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getMyAppointments(@PathVariable("startDate") String startDate) {
        logger.info("Request received for Appointment");
        List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			appointments = appointmentService.getAppointmentForRep(loginId,DateConvertor.convertStringToDate(startDate, DateConvertor.YYYYMMDD));

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return appointments;
    }

	@RequestMapping(value = PharmaRepController.GET_MY_ALL_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getMyAppointments() {
        logger.info("Request received for Appointment");
        List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			appointments = appointmentService.getAppointmentForRep(loginId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return appointments;
    }

	@RequestMapping(value = PharmaRepController.GET_MY_UPCOMING_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getMyUpcomingAppointments() {
        logger.info("Request received for Appointment");
        List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			appointments = appointmentService.getAppointmentForRep(loginId,"Scheduled");

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return appointments;
    }

	@RequestMapping(value = PharmaRepController.GET_MY_COMPLETED_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getMyCompletedAppointments() {
        logger.info("Request received for Appointment");
        List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			appointments = appointmentService.getAppointmentForRep(loginId,"Completed");

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return appointments;
    }

	@RequestMapping(value = PharmaRepController.GET_APPOINTMENT_BY_REP, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getAppointmentsByRep(@PathVariable("repId") String repId) {
        logger.info("Request received for Appointment");
        List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
        try
		{

	        Integer rep = Integer.parseInt(repId);
	        appointments = appointmentService.getAppointmentForRep(rep);

		}
        catch(Exception e)
		{
			e.printStackTrace();
		}
        return appointments;
    }

	@RequestMapping(value = PharmaRepController.GET_MY_TEAM_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getMyTeamAppointment(@PathVariable("startDate") String startDate) {
        logger.info("Request received for Appointment");
        List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			appointments = appointmentService.getAppointmentForMyTeam(loginId,DateConvertor.convertStringToDate(startDate, DateConvertor.YYYYMMDD));

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return appointments;
    }

	@RequestMapping(value = PharmaRepController.GET_MY_PENDING_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getMyPendingAppointments() {
        logger.info("Request received for Appointment");
        List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String  loginId = context.getUsername();
        			appointments = appointmentService.getRepPendingAppointment(loginId);
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}
        }
        return appointments;
    }


	@RequestMapping(value = PharmaRepController.GET_MY_TEAM_PENDING_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getMyTeamPendingAppointments() {
        logger.info("Request received for Appointment");
        List<DoctorAppointment> appointments = new ArrayList<DoctorAppointment>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String  loginId = context.getUsername();
        			appointments = appointmentService.getMyTeamPendingAppointment(loginId);
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}
        }
        return appointments;
    }

	@RequestMapping(value = PharmaRepController.ACCEPT_APPOINTMENT, method = RequestMethod.POST)
    public @ResponseBody AppResponse acceptAppointment(@RequestBody DoctorAppointment appointment) {
        logger.info("Request received for Appointment");
        UserSecurityContext context = null;
        AppResponse response = new AppResponse();
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String  loginId = context.getUsername();
        			String result = appointmentService.acceptAppointmentByRep(appointment, loginId);
        			if(result.contains("Successful"))
        			{
        				response.setStatus("OK");
        				response.setMessage(result);
        			}
        			else
        			{
        				response.setStatus("Fail");
        				response.setMessage(result);
        			}
        		}
        		catch(Exception e)
        		{
        			response.setStatus("error");
        			response.setMessage(e.getMessage());
        			e.printStackTrace();
        		}
        	}
        }
        return response;
    }


	@RequestMapping(value = PharmaRepController.GET_MY_NOTIFICATION, method = RequestMethod.GET)
    public @ResponseBody List <Notification> getMyNotifications(@PathVariable("startDate") String startDate,Authentication activeUser) {
        logger.info("Request received for Get Rep Notifications");
        List <Notification> notificationList = new ArrayList<Notification>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			System.out.println(" Inside Notification ");
        			String  loginId = context.getUsername();

        			PharmaRep pharmaRep=doctorRepService.getPharmaRep(((UserSecurityContext) activeUser.getPrincipal()).getUsername());
     	    	   if(!"Active".equalsIgnoreCase(pharmaRep.getStatus())){
     	    		   return notificationList;
     	    	   }

        			notificationList = notificationService.getPharmaRepNotifications(loginId, DateConvertor.convertStringToDate(startDate,DateConvertor.YYYYMMDD));
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }

        return notificationList;
    }

	@RequestMapping(value = PharmaRepController.GET_NOTIFICATION_BY_ID, method = RequestMethod.GET)
    public @ResponseBody Notification getNotificationById(@PathVariable("notificationId") String notificationId,Authentication activeUser) {
       logger.info("Request received for Get Rep Notifications");
       Notification notification = new Notification();
       try
		{
    	   PharmaRep pharmaRep=doctorRepService.getPharmaRep(((UserSecurityContext) activeUser.getPrincipal()).getUsername());
    	   if(!"Active".equalsIgnoreCase(pharmaRep.getStatus())){
   				return notification;
   			}
			notification = notificationService.getPharmaRepNotification(notificationId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

        return notification;
    }

	@RequestMapping(value = PharmaRepController.GET_MY_NOTIFICATION_CONTENT, method = RequestMethod.GET)
	@ResponseBody
	public Object getMyNotificationContent(@PathVariable("detailNotificationId") Integer detailNotificationId,Authentication activeUser){

		HashMap respEntity=new HashMap();
		try
		{

			PharmaRep pharmaRep=doctorRepService.getPharmaRep(((UserSecurityContext) activeUser.getPrincipal()).getUsername());
	    	   if(!"Active".equalsIgnoreCase(pharmaRep.getStatus())){
					respEntity.put("status", "error");
					respEntity.put("message", "Your Account needs to be Active to view notifications.");
				}

			 NotificationDetails notificationDetails = notificationService.getNotificationDetails(detailNotificationId);
			 if(notificationDetails != null)
			 {
			     Map response=new HashMap();
			     response.put("contentLocation", notificationDetails.getContentLocation());
			     return response;
		     }
			 else
			 {
				 	respEntity.put("status", "error");
					respEntity.put("message", "No content found");
			}



		} catch (Exception e) {
			e.printStackTrace();
			respEntity=new HashMap();
		 	respEntity.put("status", "error");
			respEntity.put("message", "An Internal Exception occured.Please try after sometime.");
		}

		return respEntity;
	}



	@RequestMapping(value = PharmaRepController.CREATE_APPOINTMENT, method = RequestMethod.POST)
    public @ResponseBody AppResponse createAppointment(@RequestBody DoctorAppointment appointment) {
        logger.info("Request received for creating appointment");
        //TODO : Update Code.
        AppResponse response = new AppResponse();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
		        try
		        {
		        	String  loginId = context.getUsername();
		        	appointmentService.createDoctorAppointment(appointment,"pharmarep",loginId);
		        	response.setMessage("Success");
		            response.setStatus("OK");
		        }
		        catch(Exception e)
		        {
		        	response.setMessage("Error occurred while creating appointment.");
		            response.setStatus("Error");
		            e.printStackTrace();
		        }
        	}
        }
        return response;
    }

	@RequestMapping(value = PharmaRepController.UPDATE_APPOINTMENT, method = RequestMethod.POST)
    public @ResponseBody AppResponse updateAppointment(@RequestBody DoctorAppointment appointment) {
        logger.info("Request received for updating appointment");
        AppResponse response = new AppResponse();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
		        try
		        {
		        	String  loginId = context.getUsername();
		        	appointmentService.updateDoctorAppointment(appointment,"pharmarep",loginId);
		        	response.setMessage("Success");
		            response.setStatus("OK");
		            if(appointment.getStatus() != null && "completed".equalsIgnoreCase(appointment.getStatus()))
		            {
		            	Activity activity = new Activity();
             			activity.setActivityStatus("U");
             			activity.setActivityType("DAC");
             			activity.setActivityTypeId(appointment.getNotificationId());
             			activity.setUserType("DOCTOR");
             			activity.setLastUpdateDateTime(new Date());
             			activity.setUserId(appointment.getDoctorId());
             			activityService.createOrUpdateActivity(activity);

		            }
		        }
		        catch(Exception e)
		        {
		        	response.setMessage("Error occurred while updating appointment.");
		            response.setStatus("Error");
		            System.out.println("Error in completing appointment");
		            e.printStackTrace();
		        }
        	}
        }
        return response;
    }

	@RequestMapping(value="/sendReminder/{surveyId}/{doctorId}",method=RequestMethod.GET)
	@ResponseBody
	public AppResponse sendReminder(@PathVariable Integer doctorId,@PathVariable Integer surveyId){
        AppResponse response=new AppResponse();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			SurveyEntity survey=surveyService.updateSurvey(surveyId,doctorId);
        			notificationService.push("Please fill the survey "+survey.getSurveyTitle(), doctorId);
        			response.setStatus("success");
        			response.setMessage("Reminder has been sent to the doctor");
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        			response.setMessage(e.getMessage());
        			response.setStatus("error");
        		}
        	}
        }
        return response;

	}

	@RequestMapping(value = PharmaRepController.GET_MY_SURVEYS, method = RequestMethod.GET)
    public @ResponseBody List<Survey> getMyPendingSurveys() {
        logger.info("Request received for Get My Surveys");
        List<Survey> surveys = new ArrayList<Survey>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			surveys = surveyService.getSurveysByRep(loginId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return surveys;
    }

	@RequestMapping(value = PharmaRepController.UPDATE_MY_SURVEY_STATUS, method = RequestMethod.POST)
    public @ResponseBody AppResponse updateMySurveyStatus(@RequestBody Survey survey)
	{
        logger.info("Request received for updating survey status");
        AppResponse response = new AppResponse();
        try
        {
        	UserSecurityContext context=null;

        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
            {
            	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            	if(context != null)
            	{
            			String  loginId = context.getUsername();

            			if(surveyService.getRepSurvey(survey,loginId) != null)
                    	{
            		        surveyService.updateRepSurvey(survey,loginId);
            		        response.setMessage("Success");
            		        response.setStatus("OK");
                    	}
                    	else
                    	{
                    		response.setMessage("Survey not found for given Id");
             		        response.setStatus("Error");
                    	}
            	}
        }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	response.setMessage("Error occurred while updating the status.");
            response.setStatus("Error");
        }
        return response;
    }

	@RequestMapping(value = PharmaRepController.GET_MY_DOCTORS, method = RequestMethod.GET)
    public @ResponseBody List<Doctor> getMyDoctors()
	{
        logger.info("Request received for providing doctors");
        List<Doctor> doctors = new ArrayList<Doctor>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			doctors = doctorRepService.getRepDoctors(loginId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return doctors;
    }



	@RequestMapping(value = PharmaRepController.GET_MY_COMPANY_SURVEYS, method = RequestMethod.GET)
    public @ResponseBody List<CompanySurvey> getMyCompanySurveys(@PathVariable("surveyId") String surveyId) {
        logger.info("Request received for Get My Surveys");
        List<CompanySurvey> companySurveys = new ArrayList<CompanySurvey>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			Integer surveyIdentifier = Integer.parseInt(surveyId);
        			companySurveys = surveyService.getCompanySurvey(loginId,surveyIdentifier);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return companySurveys;
    }

	@RequestMapping(value = PharmaRepController.GET_MY_COMPANY, method = RequestMethod.GET)
    public @ResponseBody Company getMyCompany()
	{
        logger.info("Request received for providing doctors");
        Company company = new Company();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			company = companyService.getCompanyDetailByRepId(loginId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return company;
    }

}
