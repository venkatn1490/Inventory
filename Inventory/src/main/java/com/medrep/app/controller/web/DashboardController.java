package com.medrep.app.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.service.CompanyService;

/**
 * Handles and retrieves the common or admin page depending on the URI template.
 * A user must be log-in first he can access these pages.  Only the admin can see
 * the adminpage, however.
 */
@Controller
@RequestMapping("/web/admin")
public class DashboardController {
	
	@Autowired
	DisplayPictureDAO dispictureDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	CompanyService companyService;
	
	protected static Logger logger = Logger.getLogger("controller");
	
	  
    /**
     * Handles and retrieves the admin JSP page that only admins can see
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/dashboard.do", method = RequestMethod.GET)
    public ModelAndView getAdminDashboardPage(HttpServletRequest request) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	String userSecuritId = request.getSession().getAttribute("UserSecuritId").toString();
    	System.out.println(userSecuritId);
      	 mnv.addObject("FirstName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getFirstName());
      	 mnv.addObject("MiddleName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getMiddleName());
      	 mnv.addObject("LastName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getLastName());
      	 mnv.addObject("Companies",companyService.fetchAllPharmaCompaniesWithDeleted());
      	 /*mnv.addObject("UserSecuritId", userSecuritId);*/
      	 mnv.setViewName("/web/admin/dashboard");    	 
      	 return mnv;
	}
    
    @RequestMapping(value = "doctor/dashboard.do", method = RequestMethod.GET)
    public String getDoctorPage() {
    	logger.debug("Received request to show admin page");
    
    	// Do your work here. Whatever you like
    	// i.e call a custom service to do your business
    	// Prepare a model to be used by the JSP page
    	
    	// This will resolve to /WEB-INF/jsp/adminpage.jsp
    	return "admin/dashboard.do";
	}
    
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String getDefaultPage() {
    	logger.debug("Received request to show admin page");
    
    	// Do your work here. Whatever you like
    	// i.e call a custom service to do your business
    	// Prepare a model to be used by the JSP page
    	
    	// This will resolve to /WEB-INF/jsp/adminpage.jsp
    	return "default";
	}
    
}
