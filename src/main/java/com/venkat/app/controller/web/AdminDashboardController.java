package com.venkat.app.controller.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.venkat.app.dao.UserDAO;
import com.venkat.app.model.SalesUser;
import com.venkat.app.model.User;
import com.venkat.app.service.UserService;
import com.venkat.app.util.PasswordProtector;
import com.venkat.app.util.VenkatException;

/**
 * Handles and retrieves the common or admin page depending on the URI template.
 * A user must be log-in first he can access these pages.  Only the admin can see
 * the adminpage, however.
 */
@Controller
@RequestMapping("/web/admin")
public class AdminDashboardController {
	
	@Autowired
	UserDAO userDAO;	
	@Autowired
	UserService  userService;	
	
	
	protected static Logger logger = Logger.getLogger("controller");
	
	  
    @RequestMapping(value = "/dashboard.do", method = RequestMethod.GET)
    public ModelAndView getAdminDashboardPage(HttpServletRequest request) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	String userSecuritId = request.getSession().getAttribute("UserSecuritId").toString();
    	System.out.println(userSecuritId);
    	
      	 mnv.addObject("FirstName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getFirstName());
      	 mnv.addObject("LastName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getLastName());
      	 mnv.setViewName("/web/admin/dashboard");    	 
      	 return mnv;
	}
    
    @RequestMapping(value = "/supplier.do", method = RequestMethod.GET)
    public ModelAndView getSupplier(Model model) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	try{
    		model.addAttribute("userList",userService.getUsersByRole(2));
    	}
    	catch(Exception e){
    		e.printStackTrace();
 	    	}
      	 mnv.setViewName("/web/admin/supplier"); 
    	
      	 return mnv;
	}
    @RequestMapping(value = "/purchaser.do", method = RequestMethod.GET)
    public ModelAndView getPurchaser(Model model) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	try{
    		model.addAttribute("userList",userService.getUsersByRole(3));
    	}
    	catch(Exception e){
    		e.printStackTrace();
 	    	}
      	 mnv.setViewName("/web/admin/purchaser"); 
    	
      	 return mnv;
	}
    @RequestMapping(value = "/salesman.do", method = RequestMethod.GET)
    public ModelAndView getSalesman(Model model) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	try{
    		model.addAttribute("userList",userService.getSalesMan(4));
    		model.addAttribute("purchaserList",userService.getUsersByRole(3));
    	}
    	catch(Exception e){
    		e.printStackTrace();
 	    	}
      	 mnv.setViewName("/web/admin/salesman"); 
    	
      	 return mnv;
	}
    @RequestMapping(value = "/customer.do", method = RequestMethod.GET)
    public ModelAndView getCustomer(Model model) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	try{
    		model.addAttribute("userList",userService.getUsersByRole(5));
    	}
    	catch(Exception e){
    		e.printStackTrace();
 	    	}
      	 mnv.setViewName("/web/admin/customer"); 
    	
      	 return mnv;
	}
    @RequestMapping(value="/createUser.do",method=RequestMethod.POST)
    public ModelAndView createNewUser( 
    		@RequestParam("firstName") String first_name,
			 @RequestParam("lastName") String lastName,
			 @RequestParam("email") String email, 								
			 @RequestParam("mobile") String mobile,
			 @RequestParam("title") String title,
			 @RequestParam("gender") String gender,
			 @RequestParam("dob") String dob,
			 @RequestParam("role") Integer role,
			@RequestParam("buttonName") String buttonName,
			 Model model) {
    	ModelAndView mnv=new ModelAndView();
     	try{
    	
    	 	User usermodel = new User();
    	 	
    	 	usermodel.setFirstName(first_name);
    	 	usermodel.setLastName(lastName);
    	 	usermodel.setEmailId(email);
    	 	usermodel.setMobileNo(mobile);
    	 	usermodel.setTitle(title);
    	 	usermodel.setGender(gender);	 	 	
    	 	usermodel.setDob(dob);	 
    	 	usermodel.setUsername(mobile.toString());
    	 	usermodel.setPassword(lastName);
    	 	usermodel.setStatus("Active");
    	 	usermodel.setRoleId(role);
    	 	
    	 	if(buttonName.equals("Create"))
    	 	{
    	 		model.addAttribute("user",usermodel);
    	 		userService.createUser(model);				
    	 	}
    	 	
    	 	}catch(Exception e){
    	 		e.printStackTrace();
    	 	}
     		if (role == 2) {
   	    	 mnv.setViewName("redirect:/web/admin/supplier.do"); 
			}
     		else if (role == 3) {
	   	    	 mnv.setViewName("redirect:/web/admin/purchaser.do"); 
				}
     		else if (role == 5) {
	   	    	 mnv.setViewName("redirect:/web/admin/customer.do"); 
				}
    	return mnv;
    }
    @RequestMapping(value="/createSalesman.do",method=RequestMethod.POST)
    public ModelAndView createSalesman( 
    		@RequestParam("firstName") String first_name,
			 @RequestParam("lastName") String lastName,
			 @RequestParam("email") String email, 								
			 @RequestParam("mobile") String mobile,
			 @RequestParam("title") String title,
			 @RequestParam("gender") String gender,
			 @RequestParam("purchaser") Integer purchaser,
			 @RequestParam("dob") String dob,
			 @RequestParam("role") Integer role,
			@RequestParam("buttonName") String buttonName,
			 Model model) {
    	ModelAndView mnv=new ModelAndView();
     	try{
    	
    	 	SalesUser usermodel = new SalesUser();
    	 	usermodel.setFirstName(first_name);
    	 	usermodel.setLastName(lastName);
    	 	usermodel.setEmailId(email);
    	 	usermodel.setMobileNo(mobile);
    	 	usermodel.setTitle(title);
    	 	usermodel.setGender(gender);	 	 	
    	 	usermodel.setDob(dob);	 
    	 	usermodel.setUsername(mobile.toString());
    	 	usermodel.setPassword(lastName);
    	 	usermodel.setStatus("Active");
    	 	usermodel.setRoleId(role);
    	 	usermodel.setPurchaserUserId(purchaser);
    	 	
    	 	if(buttonName.equals("Create"))
    	 	{
    	 		model.addAttribute("user",usermodel);
    	 		userService.createSalesUser(model);				
    	 	}
    	 	
    	 	}catch(Exception e){
    	 		e.printStackTrace();
    	 	}
	   	    mnv.setViewName("redirect:/web/admin/salesman.do"); 
    	return mnv;
    }
}
