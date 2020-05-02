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

import com.venkat.app.model.User;
import com.venkat.app.service.ItemsService;
import com.venkat.app.service.OrdersService;
import com.venkat.app.service.UserService;
import com.venkat.app.util.PasswordProtector;
import com.venkat.app.util.VenkatException;

@Controller
@RequestMapping("/web/salesman")
public class SalesmanController {
	
	@Autowired
	UserDAO userDAO;	
	@Autowired
	UserService  userService;	
	@Autowired
	OrdersService  orderService;
	@Autowired
	ItemsService  itemService;
	
	protected static Logger logger = Logger.getLogger("controller");
	
	  
    @RequestMapping(value = "/dashboard.do", method = RequestMethod.GET)
    public ModelAndView getSalesmanDashboardPage(HttpServletRequest request) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	String userSecuritId = request.getSession().getAttribute("UserSecuritId").toString();
    	System.out.println(userSecuritId);
    	
      	 mnv.addObject("FirstName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getFirstName());
      	 mnv.addObject("LastName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getLastName());
      	 mnv.setViewName("/web/salesman/dashboard");    	 
      	 return mnv;
	}
    
    @RequestMapping(value = "/orders.do", method = RequestMethod.GET)
    public ModelAndView getSalesmanOrders(Model model, HttpServletRequest request) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	if (request.getSession().getAttribute("UserSecuritId") != null) {
    		String userSecuritId = request.getSession().getAttribute("UserSecuritId").toString();
        	System.out.println(userSecuritId);
          	 mnv.addObject("FirstName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getFirstName());
          	 mnv.addObject("LastName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getLastName());
          	 User user = userService.findUserByUserSecurityId(Integer.parseInt(userSecuritId));
          	 if (user != null) {
	          			try {
	          	    		model.addAttribute("orderList",orderService.getAllSalesmanOrdersById(user.getUserId()));
		      		   }catch(Exception e) {
		      			   model.addAttribute("orderList", e.getMessage());
		      		   
			          		}
          	 }
          	 mnv.setViewName("/web/salesman/orders"); 
    	}
    	else {
    		mnv.addObject("url", "./support.do");
    		mnv.addObject("url_name","Support");
    	   	mnv.setViewName("/web/auth/loginpage"); 
    	   	}
	   return mnv;
	}
  
    @RequestMapping(value = "/OrderItems.do", method = RequestMethod.GET)
    public ModelAndView getSalesmanOrderItems(Model model, HttpServletRequest request, @RequestParam Integer orderId) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	if (request.getSession().getAttribute("UserSecuritId") != null) {
    		String userSecuritId = request.getSession().getAttribute("UserSecuritId").toString();
        	System.out.println(userSecuritId);
          	 mnv.addObject("FirstName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getFirstName());
          	 mnv.addObject("LastName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getLastName());
          	 User user = userService.findUserByUserSecurityId(Integer.parseInt(userSecuritId));
          	 if (user != null) {
	          			try {
	          	    		model.addAttribute("itemList",orderService.getAllCustomerOrdersItemsById(orderId));
		      		   }catch(Exception e) {
		      			   model.addAttribute("itemList", e.getMessage());
		      		   
			          		}
          	 }
          	 mnv.setViewName("/web/salesman/orderItems"); 
    	}
    	else {
    		mnv.addObject("url", "./support.do");
    		mnv.addObject("url_name","Support");
    	   	mnv.setViewName("/web/auth/loginpage"); 
    	   	}
	   return mnv;
	}
    @RequestMapping(value="/createUser.do",method=RequestMethod.POST)
    public ModelAndView createNewCustomer( 
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
     		
	   	    	 mnv.setViewName("redirect:/web/salesman/customers.do"); 
				
    	return mnv;
    }
    @RequestMapping(value = "/customers.do", method = RequestMethod.GET)
    public ModelAndView getMyCustomer(Model model) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	try{
    		model.addAttribute("userList",userService.getUsersByRole(5));
    	}
    	catch(Exception e){
    		e.printStackTrace();
 	    	}
      	 mnv.setViewName("/web/salesman/customers"); 
    	
      	 return mnv;
	}
}
