package com.medrep.app.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medrep.app.engine.AppointmentManager;
import com.medrep.app.engine.NotificationPublisher;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.model.Activity;
import com.medrep.app.model.AppResponse;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorAppointment;
import com.medrep.app.model.DoctorConsultingsModel;
import com.medrep.app.model.DoctorNotification;
import com.medrep.app.model.Notification;
import com.medrep.app.model.NotificationDetails;
import com.medrep.app.model.PatDocAppointModel;
import com.medrep.app.model.PharmaRep;
import com.medrep.app.model.Survey;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.ActivityService;
import com.medrep.app.service.AppointmentService;
import com.medrep.app.service.ConsultingsService;
import com.medrep.app.service.DoctorPlusService;
import com.medrep.app.service.DoctorRepService;
import com.medrep.app.service.DoctorService;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.PatientAppointmentService;
import com.medrep.app.service.PharmaWebinarService;
import com.medrep.app.service.SurveyService;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.Util;

@Controller
@RequestMapping("/api/doctor")
public class DoctorController {



	public static final String GET_MY_NOTIFICATION = "/getMyNotifications/{startDate}";
	public static final String GET_MY_NOTIFICATION_CONTENT = "/getMyNotificationContent/{detailNotificationId}";
	public static final String UPDATE_MY_NOTIFICATION = "/updateMyNotification";
	public static final String GET_MY_APPOINTMENT = "/getMyAppointment/{startDate}";
	public static final String GET_MY_ALL_APPOINTMENT = "/getMyAppointment";
	public static final String CREATE_APPOINTMENT = "/createAppointment";
	public static final String UPDATE_APPOINTMENT = "/updateAppointment";
	public static final String GET_MY_ALL_CONSULTINGS = "/getMyConsultings";
	public static final String CREATE_CONSULTING = "/createConsulting"; 
	public static final String REMOVE_CONSULTING = "/removeConsulting/{consultingId}";
	public static final String GET_MY_SURVEYS = "/getMyPendingSurveys";
	public static final String GET_MY_REPS = "/getMyReps";
	public static final String UPDATE_MY_SURVEY_STATUS = "/updateMySurveyStatus";
	public static final String GET_DOCTOR_PATIENT_APPOINTMENT = "/getDocPatAppoinments";
	public static final String GET_PHARMA_WEBINARS = "/getPharmaWebinars";
	public static final String VIDEO_PRESCRIPTION  = "/videoPrescription";

	private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);


	@Autowired
	ConsultingsService consultingService;
	
	@Autowired
	SurveyService surveyService;

	@Autowired
	AppointmentService appointmentService;

	@Autowired
	DoctorService doctorService;

	@Autowired
	DoctorRepService doctorRepService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	ActivityService activityService;
	@Autowired
	DoctorPlusService doctorPlusService;
	@Autowired
	AppointmentManager appointmentManager;
	@Autowired
	NotificationPublisher notificationPublisher;
	@Autowired
	PharmaWebinarService pharmaWebinarService;
	@Autowired
	PatientAppointmentService patientAppointmentService;
	/**
	 * Method will be called in initial page load at GET /employee-module
	 *
	 * */
	@RequestMapping(value = DoctorController.UPDATE_MY_NOTIFICATION, method = RequestMethod.POST)
    public @ResponseBody AppResponse updateMyNotification(@RequestBody DoctorNotification doctorNotification) {
        logger.info("Request received for update Notfication");
        AppResponse response = new AppResponse();

        try{

        	 UserSecurityContext context = null;
        	 if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             	if(context != null)
             	{
             			String  loginId = context.getUsername();
             			notificationService.updateDoctorNotification(doctorNotification, loginId);
	                	response.setMessage("Success");
	                    response.setStatus("OK");
	                    if(doctorNotification.getRating() != null && doctorNotification.getPrescribe() != null && doctorNotification.getRecomend() != null)
	                    {
	                    	Activity activity = new Activity();
	             			activity.setActivityStatus("U");
	             			activity.setActivityType("DNF");
	             			activity.setActivityTypeId(doctorNotification.getNotificationId());
	             			activity.setUserType("DOCTOR");
	             			activity.setLastUpdateDateTime(new Date());
	             			activityService.createOrUpdateActivity(activity, loginId);
	                    }
	                    if(doctorNotification.getViewStatus() != null)
	                    {
		                   	Activity activity = new Activity();
		         			activity.setActivityStatus("U");
		         			activity.setActivityType("DN");
		         			activity.setActivityTypeId(doctorNotification.getNotificationId());
		         			activity.setUserType("DOCTOR");
		         			activity.setLastUpdateDateTime(new Date());
		         			activityService.createOrUpdateActivity(activity, loginId);
	                    }


	                    notificationReminder();
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
			response.setMessage(e.getMessage());
            response.setStatus("Error");

		}
        logger.info("response completed");
        return response;
    }

	@Async
	private void notificationReminder() {
		logger.info("Notifying reminder");
		Object doctorNotifcations=notificationPublisher.getDoctorNotifications();
		synchronized(doctorNotifcations){
			doctorNotifcations.notify();
		}
		logger.info("Notified reminder");
	}

	@RequestMapping(value = DoctorController.GET_MY_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getMyAppointments(@PathVariable("startDate") String startDate) {
        logger.info("Request received for login");
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
        			appointments = appointmentService.getAppointmentForDoctor(loginId, DateConvertor.convertStringToDate(startDate, DateConvertor.YYYYMMDD));

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return appointments;
    }

	@RequestMapping(value = DoctorController.GET_MY_ALL_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getMyAppointments() {
        logger.info("Request received for login");
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
        			appointments = appointmentService.getAppointmentForDoctor(loginId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return appointments;
    }


	@RequestMapping(value = DoctorController.GET_MY_NOTIFICATION, method = RequestMethod.GET)
    public @ResponseBody List <Notification> getMyNotifications(@PathVariable("startDate") String startDate) {
        logger.info("Request received for Get My Profile");
        List <Notification> notificationList = new ArrayList<Notification>();


        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String  loginId = context.getUsername();
        			notificationList = notificationService.getDoctorNotifications(loginId, DateConvertor.convertStringToDate(startDate,DateConvertor.YYYYMMDD));
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }

        return notificationList;
    }


	@ResponseBody
	@RequestMapping(value = DoctorController.GET_MY_NOTIFICATION_CONTENT, method = RequestMethod.GET)
	public Object getMyNotificationContent(@PathVariable("detailNotificationId") Integer detailNotificationId,Authentication activeUser){
		Map respEntity = new HashMap();
		try{

			Doctor doctor=doctorPlusService.getDoctor(((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			if(Util.isEmpty(doctor) || (!Util.isEmpty(doctor)&& !"Active".equalsIgnoreCase(doctor.getStatus()))){
				respEntity.put("status", "error");
				respEntity.put("message", "Your Account needs to be Active to view notifications.");
			}

			UserSecurityContext context = null;
			String loginId=null;
	        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
	        {
	        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        	if(context != null)
			         loginId = context.getUsername();
	        }
			 NotificationDetails notificationDetails = notificationService.getNotificationDetails(detailNotificationId,loginId);
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
		} catch (Exception e)
		{
			e.printStackTrace();
			respEntity=new HashMap();
		 	respEntity.put("status", "error");
			respEntity.put("message", "An Internal Exception occured.Please try after sometime.");
		}
		return respEntity;
	}



	@RequestMapping(value = DoctorController.CREATE_APPOINTMENT, method = RequestMethod.POST)
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
		        	ArrayList<DeviceTokenEntity> devicesList=appointmentService.createDoctorAppointment(appointment,"doctor",loginId);
		        	response.setMessage("Success");
		            response.setStatus("OK");
		            logger.info("creating appointment..1 ");
		            Activity activity = new Activity();
         			activity.setActivityStatus("U");
         			activity.setActivityType("DA");
         			activity.setActivityTypeId(appointment.getNotificationId());
         			activity.setUserType("DOCTOR");
         			activity.setLastUpdateDateTime(new Date());
         			activityService.createOrUpdateActivity(activity, loginId);
         			logger.info("creating appointment..2");
         			if(appointment.getDoctorId()!=null&& appointment.getDoctorId()!=0){
         			 Doctor d= doctorService.findDoctorById(appointment.getDoctorId()+"");

         			 String message=d.getFirstName()+" " +d.getLastName()+" has ";
         			 if(appointment.isRescheduled())
         				 message+="rescheduled the appointment to ";
         			 else
         				 message+="scheduled an appointment at ";

         			 message+=(DateConvertor.convertStringToDate(appointment.getStartDate(),DateConvertor.YYYYMMDDHHMISS,DateConvertor.TIME_IN_AM_PM));

  		           notificationService.pushMessage(message , devicesList);
         			}
  		           logger.info("creating appointment..3");
		        }
		        catch(Exception e)
		        {
		        	logger.error("exception");
		        	response.setMessage(e.getMessage());
		            response.setStatus("Error");
		            e.printStackTrace();
		        }
        	}
        }
        logger.info("finished processing");
        return response;
    }
	
	
	@RequestMapping(value = DoctorController.CREATE_CONSULTING, method = RequestMethod.POST)
    public @ResponseBody AppResponse createConsulting(@RequestBody DoctorConsultingsModel consultingModel) {
        logger.info("Request received for creating appointment");
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
		        	consultingService.createDoctorConsulting(consultingModel,loginId);
		        	response.setMessage("Success");
		            response.setStatus("OK");
		            logger.info("creating consultings..3");
		        }
		        catch(Exception e)
		        {
		        	logger.error("exception");
		        	response.setMessage(e.getMessage());
		            response.setStatus("Error");
		            e.printStackTrace();
		        }
        	}
        }
        logger.info("finished processing");
        return response;
    }
	
	@RequestMapping(value = DoctorController.GET_MY_ALL_CONSULTINGS, method = RequestMethod.GET)
    public @ResponseBody Object getMyConsultings() {
        logger.info("Request received for login");
        Map<String, List<?>> result=null;
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        				String  loginId = context.getUsername();
        				result=new HashMap<String, List<?>>();;
						result.put("VIDEO",consultingService.getMyConsultings(loginId,"VIDEO"));
						result.put("F2F",consultingService.getMyConsultings(loginId,"F2F"));
						result.put("CHAT",consultingService.getMyConsultings(loginId,"CHAT"));
						return result;

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return result;
    }
	
	@RequestMapping(value = DoctorController.REMOVE_CONSULTING, method = RequestMethod.GET)
    public @ResponseBody AppResponse removeConsulting(@PathVariable("consultingId") Integer consultingId  ) {
        logger.info("Request received for creating appointment");
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
		        if(	consultingService.removeDoctorConsulting(consultingId)){
		        	response.setMessage("Success");
		            response.setStatus("OK");
		            logger.info("removing consultings..3");
		        }else{
		        	response.setMessage("Error While removing consulting");
		            response.setStatus("Error");
		            logger.info("removing consultings..3");
		        }
		        	
		        }
		        catch(Exception e)
		        {
		        	logger.error("exception");
		        	response.setMessage(e.getMessage());
		            response.setStatus("Error");
		            e.printStackTrace();
		        }
        	}
        }
        logger.info("finished processing");
        return response;
    }
	

	@RequestMapping(value = DoctorController.UPDATE_APPOINTMENT, method = RequestMethod.POST)
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
		        	ArrayList<DeviceTokenEntity> devicesList=appointmentService.updateDoctorAppointment(appointment,"doctor",loginId);
		        	response.setMessage("Success");
		            response.setStatus("OK");

		            	Activity activity = new Activity();
             			activity.setActivityStatus("U");
             			activity.setActivityType("DA");
             			activity.setActivityTypeId(appointment.getNotificationId());
             			activity.setUserType("DOCTOR");
             			activity.setLastUpdateDateTime(new Date());
             			activityService.createOrUpdateActivity(activity, loginId);

             			 Doctor d= doctorService.findDoctorById(appointment.getDoctorId()+"");
        				notificationService.push("Your Appointment with "+d.getFirstName() +d.getLastName()+" has been changed to "+DateConvertor.convertStringToDate(appointment.getStartDate(),DateConvertor.YYYYMMDDHHMISS,DateConvertor.TIME_IN_AM_PM), devicesList);
//        				Notify.sendNotification("Doctor Appointment Date Changed", devicesList);

             			appointmentReminder();
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
	
	
	

	@Async
	private void appointmentReminder() {
		logger.info("Notifying appointmentReminders");
		Object doctorAppointments=appointmentManager.getDoctorAppointments();
		synchronized(doctorAppointments){
			doctorAppointments.notify();
		}
		logger.info("Notified appointmentReminders");
	}

	@RequestMapping(value = DoctorController.GET_MY_SURVEYS, method = RequestMethod.GET)
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
        			surveys = surveyService.getDoctorSurvey(loginId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return surveys;
    }

	@RequestMapping(value = DoctorController.UPDATE_MY_SURVEY_STATUS, method = RequestMethod.POST)
	public @ResponseBody AppResponse updateMySurveyStatus(@RequestBody Survey survey) {
		logger.info("Request received for updating survey status");
		AppResponse response = new AppResponse();
		Survey _survey = null;
		try {
			if ((_survey = surveyService.getDoctorSurvey(survey)) != null) {

				UserSecurityContext context = null;

				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
					context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if (context != null) {
						String loginId = context.getUsername();
						surveyService.updateDoctorSurvey(survey,loginId);
						response.setMessage("Success");
						response.setStatus("OK");


						Activity activity = new Activity();
						activity.setActivityStatus("U");
						activity.setActivityType("DS");
						activity.setActivityTypeId(_survey.getDoctorSurveyId());
						activity.setUserType("DOCTOR");
						activity.setLastUpdateDateTime(new Date());
						activityService.createOrUpdateActivity(activity, loginId);

					}
				}
			} else {
				response.setMessage("Survey not found for given Id");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error occurred while updating the status.");
			response.setStatus("Error");
		}
		return response;
	}

	@RequestMapping(value = DoctorController.GET_MY_REPS, method = RequestMethod.GET)
    public @ResponseBody List<PharmaRep> getMyReps() {
        logger.info("Request received for login");
        List<PharmaRep> reps = new ArrayList<PharmaRep>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			reps = doctorRepService.getDoctorReps(loginId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return reps;
    }

	@RequestMapping("/notify")
	@ResponseBody
	public String notifiyAppoinments(){
	appointmentReminder();
		return "Notified";
	}
	@RequestMapping(value =DoctorController.GET_DOCTOR_PATIENT_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody AppResponse getDoctorPatientAppoinment(@RequestParam String type,Model model) {
        logger.info("Request received for get my appoinments");
		AppResponse response = new AppResponse();
        try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
					model.addAttribute("communicationMode", type);
					doctorService.getPatientAppointmentForDoctor(model);
					response.setStatus("success");
					response.setResult(model.asMap().get("doctorAppointmentInfo"));			
				}
			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

				
		return response;

    }
	
	@RequestMapping(value =DoctorController.GET_PHARMA_WEBINARS, method = RequestMethod.GET)
    public @ResponseBody Map<String,Object>  getListofWebinars(@RequestParam Integer companyId) {
        logger.info("Request received for get my appoinments");
        Map<String,Object> result=new HashMap<String,Object>();        try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					
					result.put("Live", pharmaWebinarService.getWebinarsList("Live",companyId));
					result.put("Recorded", pharmaWebinarService.getWebinarsList("Recorded",companyId));
				}
			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

				
		return result;

    }
	
	@RequestMapping(value = DoctorController.VIDEO_PRESCRIPTION, method = RequestMethod.POST)
	public @ResponseBody AppResponse updateAbout(@RequestBody PatDocAppointModel patDocAppointModel) {
		logger.info("Request received for update Notfication");
		AppResponse response = new AppResponse();

		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					patientAppointmentService.postPrescriptionImage(patDocAppointModel);
					response.setStatus("success");
					response.setMessage("succesfully sent");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while retrieving About ");
			response.setStatus("Error");

		}

		return response;
	}
}
