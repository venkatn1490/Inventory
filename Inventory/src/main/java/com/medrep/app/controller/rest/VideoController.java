package com.medrep.app.controller.rest;

import java.util.ArrayList;
import java.util.List;

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
import com.medrep.app.model.ChatMessageModel;
import com.medrep.app.model.ChatMessages;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.model.VideoModel;
import com.medrep.app.service.VideoService;

@Controller
@RequestMapping("/api/video")
public class VideoController {

	private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

	public static final String CREATE_VIDEO_ID = "/createVideoId/{appoinmentID}/{status}";
	public static final String GET_VIDEO_CONSULTINGS = "/getVideoConsultings/{videoId}";
	public static final String UPDATE_STATUS = "/updateStatus/{videoId}/{status}";
	public static final String VIDEO_PUSH_NOTIFICATIONS = "/videoPushNotification/{videoId}";

	
	@Autowired
	VideoService videoService;
	
	
	@RequestMapping(value = VideoController.CREATE_VIDEO_ID, method = RequestMethod.GET)
    public @ResponseBody VideoModel createVideoId(@PathVariable("appoinmentID") Integer appoinmentId,@PathVariable("status") Integer status) {
        logger.info("Request creating or checking Video Id ");
        VideoModel videoModel = null;
        try
        {
        	UserSecurityContext context = null;
        
        	if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
        		context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        		if (context != null) {
        			String  loginId = context.getUsername();
        			videoModel = videoService.createOrCheckVideoId(appoinmentId,status);
        		}
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return videoModel;
    }
   
	@RequestMapping(value = VideoController.GET_VIDEO_CONSULTINGS, method = RequestMethod.GET)
    public @ResponseBody  VideoModel getVideoConsulting(@PathVariable("videoId") Integer videoId) {
        logger.info("Request getting Video Id ");
       VideoModel videoModel = null;
        try
        {
        	UserSecurityContext context = null;
        
        	if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
        		context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        		if (context != null) {
        			String  loginId = context.getUsername();
        			videoModel = videoService.getVideoConsultings(videoId);
        		}
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return videoModel;
    }
	
/*	
	@RequestMapping(value = VideoController.UPDATE_STATUS, method = RequestMethod.GET)
    public @ResponseBody AppResponse updateStatusVideo(@PathVariable("videoId") Integer videoId,@PathVariable("status") Integer status) {
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
        			videoService.updateVideoConsultings(loginId,videoId,status);
		        	response.setResult("Successfully Updated");
		            response.setStatus("OK");
             	}
             }
        	
        }
        catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("Error while updating Status");
            response.setStatus("Error");

		}

        return response;
    }*/
	
	
	@RequestMapping(value = VideoController.VIDEO_PUSH_NOTIFICATIONS, method = RequestMethod.GET)
    public @ResponseBody AppResponse videoPushNotification(@PathVariable("videoId") Integer videoId) {
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
        			videoService.videopushNotificationDoctor(videoId);
		        	response.setResult("Successfully Sent");
		            response.setStatus("OK");
             	}
             }
        	
        }
        catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("Error while updating Status");
            response.setStatus("Error");

		}

        return response;
    }
	
	/**/
}
