package com.medrep.app.controller.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.model.Feedback;
import com.medrep.app.model.Mail;
import com.medrep.app.service.EmailService;

/**
 * Handles and retrieves the common or admin page depending on the URI template.
 * A user must be log-in first he can access these pages.  Only the admin can see
 * 
 * 
 * the adminpage, however.
 */

@Controller
@RequestMapping("/web/auth")
public class SupportController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	private static final String VIEW = "/web/auth/support";
	
	@Autowired
	EmailService emailService;
	  
    /**
     * Handles and retrieves the admin JSP page that only admins can see
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/support.do", method = RequestMethod.GET)
    public ModelAndView getAdminPage() {
     logger.debug("Received request to show feedback page");
   	 ModelAndView mnv = new ModelAndView();
   	 mnv.addObject("feedbackObject", new Feedback());
     mnv.addObject("url", "./login.do");
	 mnv.addObject("url_name","Login");
   	 mnv.setViewName("/web/auth/support");   
   	 mnv.addObject("feedbackMessage", "");
   	 return mnv;
	}
    
    
    @RequestMapping(value = "/feedback.do", method = RequestMethod.POST)
	public ModelAndView submitFeedback(Model model,@ModelAttribute("feedbackObject") Feedback feedback) 
    {
		 System.out.println("feedback " + feedback.getEmail());
		 System.out.println("feedback " + feedback.getMobileNumber());
		 System.out.println("feedback " + feedback.getMessage());
		 System.out.println("feedback " + feedback.getName());
		 String msg = "Your request is submitted successfully";
		 
		 try{
			// User Mail
		 	 Mail mail = new Mail();
			 mail.setTemplateName(EmailService.FEEDBACK_ACK);
			 mail.setMailTo(feedback.getEmail());
			 Map<String,String> map =new HashMap<String,String>();
			 map.put("NAME", feedback.getName());
			 mail.setValueMap(map);
			 emailService.sendMail(mail);
		 
		    //Admin Mail
			 mail = new Mail();
			 mail.setTemplateName(EmailService.FEEDBACK_ADMIN_ACK);
			 mail.setMailTo("gowrisuresh910@gmail.com");
			 map =new HashMap<String,String>();
			 map.put("NAME", feedback.getName());
			 map.put("EMAIL", feedback.getEmail());
			 map.put("MOBILE", feedback.getMobileNumber());
			 map.put("MESSAGE", feedback.getMessage());
			 mail.setValueMap(map);
			 emailService.sendMail(mail);
		 }catch(Exception e){
			 msg = e.getMessage();
		 }
		 ModelAndView mnv = new ModelAndView();
         mnv.addObject("url", "./login.do");
    	 mnv.addObject("url_name","Login");
       	 mnv.setViewName("/web/auth/support"); 
       	 mnv.addObject("feedbackMessage", msg);
       	 return mnv;

	}
}
