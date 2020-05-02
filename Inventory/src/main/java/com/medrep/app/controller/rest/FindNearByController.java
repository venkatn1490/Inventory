package com.medrep.app.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medrep.app.model.AppResponse;
import com.medrep.app.model.PatDocAppointModel;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.FindNearByService;


@Controller
@RequestMapping("/api/findnearby")
public class FindNearByController {

	public static final String GET_MY_COUNT = "/getListCount";
	public static final String GET_LISTOF_DETAILS = "/getListofDetails/{type}";
	public static final String GET_CHECK_DATE_AVAILABLE="/getCheckDateAvailable";
	public static final String UPDATE_SHARE_PROFILE="/updateShareProfile";

	public static final String UPDATE_REMINDER_TIME="/updateReminderTime";



	private static final Logger logger = LoggerFactory.getLogger(FindNearByController.class);


	@Autowired
	FindNearByService findNearbyService;


/**
	 * Method will be called in initial page load at GET /employee-module
	 *
	 * */
	@RequestMapping(value = FindNearByController.GET_MY_COUNT, method = RequestMethod.GET)
    public @ResponseBody AppResponse getCountForNear(@RequestParam String locatity,@RequestParam String city) {
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
		        	response.setResult(findNearbyService.getListofServicesCount(locatity,city));
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


	@RequestMapping(value = FindNearByController.GET_LISTOF_DETAILS, method = RequestMethod.GET)
    public @ResponseBody AppResponse getMyConsultings(@PathVariable("type")  Integer type,@RequestParam String locatity,@RequestParam String city) {
        logger.info("Request received for login");
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
        			
        			response.setStatus("success");
					response.setResult(findNearbyService.getMyListDetails(type,locatity,city));
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return response;
    }
	

	@RequestMapping(value = FindNearByController.GET_CHECK_DATE_AVAILABLE, method = RequestMethod.GET)
    public @ResponseBody AppResponse getCheckDateAvailable(@RequestParam String date,@RequestParam Integer consultingId) {
        logger.info("Request received for login");
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
        			
        			response.setStatus("success");
					response.setResult(findNearbyService.getCheckInAvailableDays(date,consultingId,loginId));
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return response;
    }
	
	@RequestMapping(value = FindNearByController.UPDATE_SHARE_PROFILE, method = RequestMethod.GET)
    public @ResponseBody AppResponse updateShareFlag(@RequestParam String shareProfile,@RequestParam Integer appoinmentId) {
        logger.info("Request received for login");
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
        			findNearbyService.updateShareFlag(appoinmentId,shareProfile);
        			response.setStatus("success");
					response.setMessage("Succesfully Share Profile Flag.");
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return response;
    }
	
	@RequestMapping(value = FindNearByController.UPDATE_REMINDER_TIME, method = RequestMethod.GET)
    public @ResponseBody AppResponse updateReminderTime(@RequestParam String remindTime,@RequestParam Integer appoinmentId) {
        logger.info("Request received for login");
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
        			findNearbyService.updateRemindTime(appoinmentId,remindTime);
        			response.setStatus("success");
					response.setMessage("Succesfully remindTime.");
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return response;
    }
	
	
	/*@RequestMapping(value = "/getCheckDateAvaila", method = RequestMethod.GET)
    public @ResponseBody AppResponse getCfasd() {
        logger.info("Request received for login");
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
        			findNearbyService.getSuresh();
        			response.setStatus("success");
					response.setResult("fasfds");
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return response;
    }*/
	
}

