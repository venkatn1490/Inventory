package com.medrep.app.controller.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles and retrieves the common or admin page depending on the URI template.
 * A user must be log-in first he can access these pages.  Only the admin can see
 * 
 * 
 * the adminpage, however.
 */

@Controller
@RequestMapping("/web/main")
public class DemoController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	private static final String VIEW = "/web/main/coming-soon";	
	  
    /**
     * Handles and retrieves the admin JSP page that only admins can see
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/coming-soon.do", method = RequestMethod.GET)
    public ModelAndView getAdminPage() {
     logger.debug("Demo page");
   	 ModelAndView mnv = new ModelAndView();   
     mnv.addObject("url", "auth/login.do");
	 mnv.addObject("url_name","Login");
   	 mnv.setViewName(VIEW); 
   	 return mnv;
	}   
 
}
