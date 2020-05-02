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
@RequestMapping("/web/supplier")
public class SupplierController {
	
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
    public ModelAndView getSupplierDashboardPage(HttpServletRequest request) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	String userSecuritId = request.getSession().getAttribute("UserSecuritId").toString();
    	System.out.println(userSecuritId);
    	
      	 mnv.addObject("FirstName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getFirstName());
      	 mnv.addObject("LastName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getLastName());
      	 mnv.setViewName("/web/supplier/dashboard");    	 
      	 return mnv;
	}
    @RequestMapping(value = "/orders.do", method = RequestMethod.GET)
    public ModelAndView getAllOrders(Model model, HttpServletRequest request) {
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
	          	    		model.addAttribute("orderList",orderService.getAllSupplierOrdersById(user.getUserId()));
		      		   }catch(Exception e) {
		      			   model.addAttribute("orderList", e.getMessage());
		      		   
			          		}
          	 }
          	 mnv.setViewName("/web/supplier/orders"); 
    	}
    	else {
    		mnv.addObject("url", "./support.do");
    		mnv.addObject("url_name","Support");
    	   	mnv.setViewName("/web/auth/loginpage"); 
    	   	}
	   return mnv;
	}
  
    @RequestMapping(value = "/orderItems.do", method = RequestMethod.GET)
    public ModelAndView getAllOrderItems(Model model, HttpServletRequest request, @RequestParam String orderId) {
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
	          	    		model.addAttribute("itemList",orderService.getAllSupplierOrdersById(user.getUserId()));
		      		   }catch(Exception e) {
		      			   model.addAttribute("itemList", e.getMessage());
		      		   
			          		}
          	 }
          	 mnv.setViewName("/web/supplier/orderItems"); 
    	}
    	else {
    		mnv.addObject("url", "./support.do");
    		mnv.addObject("url_name","Support");
    	   	mnv.setViewName("/web/auth/loginpage"); 
    	   	}
	   return mnv;
	}
  
    
}
