package com.medrep.app.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.model.DoctorActivity;
import com.medrep.app.model.DoctorAppointment;
import com.medrep.app.model.DoctorNotificationStat;
import com.medrep.app.model.NotificationActivity;
import com.medrep.app.model.NotificationStat;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.ActivityService;
import com.medrep.app.service.AppointmentService;
import com.medrep.app.service.NotificationService;

@Controller
@RequestMapping("/api/analytic")
public class AnalyticController {
	
	private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

	
	public static final String GET_NOTIFICATION_STATS = "/getNotificationStats/{notificationId}";
	public static final String GET_APPOINTMENT_BY_NOTIFICATION = "/getAppointmentsByNotificationId/{notificationId}";
	public static final String GET_DOCTOR_NOTIFICATION_STATS = "/getDoctorNotificationStats/{notificationId}";
	public static final String GET_ACTIVITY_BY_DOCTOR_ID = "/getDoctorActivityScore/{doctorId}";
	public static final String GET_DOCTOR_ACTIVITY_STATS = "/getDoctorActivityScore";
	public static final String GET_DOCTOR_ACTIVITY_BY_COMPANY = "/getDoctorActivityByCompany/{doctorId}";
	public static final String GET_ACTIVITY_STATS = "/getNotificationActivityScore/{notificationId}";
	
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
	AppointmentService appointmentService;
	
	@RequestMapping(value = AnalyticController.GET_APPOINTMENT_BY_NOTIFICATION, method = RequestMethod.GET)
    public @ResponseBody List<DoctorAppointment> getAppointmentsByNotificationId(@PathVariable("notificationId") String notificationId) {
        logger.info("Request received for Get Rep Notifications");
        List<DoctorAppointment> appointments = null;
        try
        {
        	Integer _notificationId = Integer.parseInt(notificationId);
        	appointments = appointmentService.getAppointmentByNotificationId(_notificationId);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return appointments;
    }
	
	@RequestMapping(value = AnalyticController.GET_NOTIFICATION_STATS, method = RequestMethod.GET)
    public @ResponseBody NotificationStat getNotificationStats(@PathVariable("notificationId") String notificationId) {
        logger.info("Request received for Get Rep Notifications");
        NotificationStat notificationStat = null;
        try
        {
        	Integer _notificationId = Integer.parseInt(notificationId);
        	notificationStat = notificationService.getNotificationStats(_notificationId);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return notificationStat;
    }
	
	@RequestMapping(value = AnalyticController.GET_DOCTOR_NOTIFICATION_STATS, method = RequestMethod.GET)
    public @ResponseBody DoctorNotificationStat getDoctorNotificationStats(@PathVariable("notificationId") String notificationId) {
        logger.info("Request received for Get Rep Notifications");
        DoctorNotificationStat notificationStat = null;
        try
        {
        	Integer _notificationId = Integer.parseInt(notificationId);
        	notificationStat = notificationService.getDoctorNotificationStats(_notificationId);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return notificationStat;
    }
	
	@RequestMapping(value = AnalyticController.GET_ACTIVITY_BY_DOCTOR_ID, method = RequestMethod.GET)
    public @ResponseBody DoctorActivity getDoctorActivityScore(@PathVariable("doctorId") String doctorId) {
        logger.info("Request received for Get Rep Notifications");
        DoctorActivity doctorActivity = null;
        try
        {
        	Integer _doctorId = Integer.parseInt(doctorId);
        	doctorActivity = activityService.getDocotrActivity(_doctorId);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return doctorActivity;
    }
	
	@RequestMapping(value = AnalyticController.GET_DOCTOR_ACTIVITY_STATS, method = RequestMethod.GET)
    public @ResponseBody List<DoctorActivity> getDoctorActivityScore() {
		 logger.info("Request received for Get Rep Notifications");
		    List<DoctorActivity> doctorActivities = new ArrayList<DoctorActivity>();
	        UserSecurityContext context = null;
	        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
	        {
	        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        	if(context != null)
	        	{
	        		try
	                {
	                	String loginId = context.getUsername();
	                	doctorActivities = activityService.getDocotrActivity(loginId);
	                }
	                catch(Exception e)
	                {
	                	e.printStackTrace();
	                }
	        	}
	        }
	        
	        
	        return doctorActivities;
    }
	
	@RequestMapping(value = AnalyticController.GET_DOCTOR_ACTIVITY_BY_COMPANY, method = RequestMethod.GET)
    public @ResponseBody DoctorActivity getDoctorActivityByCompany(@PathVariable("doctorId") String doctorId) {
        logger.info("Request received for Get Rep Notifications");
        DoctorActivity doctorActivity = null;
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
                {
                	Integer _doctorId = Integer.parseInt(doctorId);
                	String loginId = context.getUsername();
                	doctorActivity = activityService.getDocotrActivityByCompany(_doctorId, loginId);
                }
                catch(Exception e)
                {
                	e.printStackTrace();
                }
        	}
        }
        
        
        return doctorActivity;
    }
	
	@RequestMapping(value = AnalyticController.GET_ACTIVITY_STATS, method = RequestMethod.GET)
    public @ResponseBody NotificationActivity getNotificationActivityScore(@PathVariable("notificationId") String notificationId) {
        logger.info("Request received for Get Rep Notifications");
        NotificationActivity notificationStat = null;
        try
        {
        	Integer _notificationId = Integer.parseInt(notificationId);
        	notificationStat = activityService.getNotificationActivity(_notificationId);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return notificationStat;
    }
}
