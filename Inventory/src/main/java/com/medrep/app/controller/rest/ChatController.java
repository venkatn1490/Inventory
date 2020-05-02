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
import org.springframework.web.bind.annotation.ResponseBody;

import com.medrep.app.model.AppResponse;
import com.medrep.app.model.ChatMessageModel;
import com.medrep.app.model.ChatMessages;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.ChatMessageService;

@Controller
@RequestMapping("/api/chat")
public class ChatController {

	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

	public static final String GET_CHAT_MESSAGES = "/getChatMessages/{appoinId}";
	public static final String PUBLISH_MESSSAGES = "/publishMsg";

	
	@Autowired
	ChatMessageService chatMessageService;
	
	
	
   
	/*@RequestMapping(value = ChatController.PUBLISH_MESSSAGES, method = RequestMethod.GET)
    public @ResponseBody  List<VideoModel> getVideoConsulting(@PathVariable("status") Integer status) {
        logger.info("Request getting Video Id ");
        List<VideoModel> videoModel = null;
        try
        {
        	UserSecurityContext context = null;
        
        	if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
        		context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        		if (context != null) {
        			String  loginId = context.getUsername();
        			videoModel = chatMessageService.getVideoConsultings(loginId,status);
        		}
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return videoModel;
    }*/
	@RequestMapping(value =  ChatController.PUBLISH_MESSSAGES, method = RequestMethod.POST)
	public @ResponseBody AppResponse updateAbout(@RequestBody ChatMessageModel chatMessageModel, Model model) {
		logger.info("Request received for update Notfication");
		AppResponse response = new AppResponse();

		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					chatMessageService.publishChatMessage(chatMessageModel);
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
	@RequestMapping(value = ChatController.GET_CHAT_MESSAGES, method = RequestMethod.GET)
    public @ResponseBody List<ChatMessages> getChatMessages(@PathVariable("appoinId") Integer appoinId) {
        logger.info("Request received for update Notfication");
    	List<ChatMessages> chatMessage=new ArrayList<ChatMessages>();
        try
        {

        	 UserSecurityContext context = null;
        	 if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
             {
             	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             	if(context != null)
             	{
        			String  loginId = context.getUsername();
        			chatMessage= chatMessageService.getMyMessages(appoinId);
		        	
             	}
             }
        	
        }
        catch(Exception e)
		{
			e.printStackTrace();

		}

        return chatMessage;
    }
	
}
