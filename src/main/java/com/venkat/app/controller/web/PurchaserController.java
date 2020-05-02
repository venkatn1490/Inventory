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
import com.venkat.app.model.CustomerOrderModel;
import com.venkat.app.model.SalesUser;
import com.venkat.app.model.SupplierOrderItemModel;
import com.venkat.app.model.SupplierOrderModel;
import com.venkat.app.model.User;
import com.venkat.app.service.ItemsService;
import com.venkat.app.service.OrdersService;
import com.venkat.app.service.UserService;
import com.venkat.app.util.PasswordProtector;
import com.venkat.app.util.VenkatException;

@Controller
@RequestMapping("/web/purchaser")
public class PurchaserController {
	
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
    public ModelAndView getPurchaserDashboardPage(HttpServletRequest request) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	String userSecuritId = request.getSession().getAttribute("UserSecuritId").toString();
    	System.out.println(userSecuritId);
    	
      	 mnv.addObject("FirstName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getFirstName());
      	 mnv.addObject("LastName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getLastName());
      	 mnv.setViewName("/web/purchaser/dashboard");    	 
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
			 @RequestParam("dob") String dob,
			 @RequestParam("role") Integer role,
			@RequestParam("buttonName") String buttonName,
			 Model model,HttpServletRequest request) {
    	ModelAndView mnv=new ModelAndView();
    	if (request.getSession().getAttribute("UserSecuritId") != null) {
    		String userSecuritId = request.getSession().getAttribute("UserSecuritId").toString();
        	System.out.println(userSecuritId);
          	 mnv.addObject("FirstName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getFirstName());
          	 mnv.addObject("LastName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getLastName());
          	 User user = userService.findUserByUserSecurityId(Integer.parseInt(userSecuritId));
          	 if (user != null) {
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
            	 	usermodel.setPurchaserUserId(user.getUserId());
            	 	
            	 	if(buttonName.equals("Create"))
            	 	{
            	 		model.addAttribute("user",usermodel);
            	 		userService.createSalesUser(model);				
            	 	}
            	 	
            	 	}catch(Exception e){
            	 		e.printStackTrace();
            	 	}
          	 }
 	   	    mnv.setViewName("redirect:/web/purchaser/salesman.do"); 
    	}
    	else {
    		mnv.addObject("url", "./support.do");
    		mnv.addObject("url_name","Support");
    	   	mnv.setViewName("/web/auth/loginpage"); 
    	   	}
     	
    	return mnv;
    }
    @RequestMapping(value = "/myorders.do", method = RequestMethod.GET)
    public ModelAndView getPurchaserOrders(Model model, HttpServletRequest request) {
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
	          	    		model.addAttribute("orderList",orderService.getAllPurchaserOrdersById(user.getUserId()));
	          	    		model.addAttribute("itemList",itemService.getAllMasterItems());
	          	    		model.addAttribute("supplierList",userService.getUsersByRole(2));

		      		   }catch(Exception e) {
		      			   model.addAttribute("orderList", e.getMessage());
		      		   
			          		}
          	 }
          	 mnv.setViewName("/web/purchaser/myorders"); 
    	}
    	else {
    		mnv.addObject("url", "./support.do");
    		mnv.addObject("url_name","Support");
    	   	mnv.setViewName("/web/auth/loginpage"); 
    	   	}
	   return mnv;
	}
  
    @RequestMapping(value = "/orderItems.do", method = RequestMethod.GET)
    public ModelAndView getPurchaserOrderItems(Model model, HttpServletRequest request, @RequestParam Integer orderId) {
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
	          	    		model.addAttribute("itemList",orderService.getAllSupplierOrdersItemsById(orderId));
		      		   }catch(Exception e) {
		      			   model.addAttribute("itemList", e.getMessage());
		      		   
			          		}
          	 }
          	 mnv.setViewName("/web/purchaser/orderItems"); 
    	}
    	else {
    		mnv.addObject("url", "./support.do");
    		mnv.addObject("url_name","Support");
    	   	mnv.setViewName("/web/auth/loginpage"); 
    	   	}
	   return mnv;
	}
  
    @RequestMapping(value="/createOrder.do",method=RequestMethod.POST)
    public ModelAndView createNewSupplierOrder( 
    		@RequestParam("supplierId") Integer supplierId,
			 @RequestParam("itemId") Integer itemId,
			 @RequestParam("stock") Integer stock, 								
			@RequestParam("buttonName") String buttonName,
			 Model model,HttpServletRequest request) {
    	ModelAndView mnv=new ModelAndView();
    	if (request.getSession().getAttribute("UserSecuritId") != null) {
    		String userSecuritId = request.getSession().getAttribute("UserSecuritId").toString();
        	System.out.println(userSecuritId);
          	 mnv.addObject("FirstName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getFirstName());
          	 mnv.addObject("LastName",userDAO.findBySecurityId(Integer.parseInt(userSecuritId)).getLastName());
          	 User user = userService.findUserByUserSecurityId(Integer.parseInt(userSecuritId));
          	 if (user != null) {
          		try{
            	 	SupplierOrderModel orderModel = new SupplierOrderModel();
            	 	orderModel.setPurchaserId(user.getUserId());
            	 	orderModel.setSupplierId(supplierId);
            	 	SupplierOrderItemModel itemModel = new SupplierOrderItemModel();
            	 	itemModel.setItemId(itemId);
            	 	itemModel.setStock(stock);
            	 	if(buttonName.equals("Create")){
            	 		model.addAttribute("order",orderModel);
            	 		model.addAttribute("item",itemModel);
            	 		orderService.createNewSupplierOrder(model);				
            	 	}
            	 	}catch(Exception e){
            	 		e.printStackTrace();
            	 	}		
          	 }
 	   	    mnv.setViewName("redirect:/web/purchaser/myorders.do"); 
    	}
    	else {
    		mnv.addObject("url", "./support.do");
    		mnv.addObject("url_name","Support");
    	   	mnv.setViewName("/web/auth/loginpage"); 
    	   	}
     	
    	return mnv;
    }
    @RequestMapping(value = "/salesman.do", method = RequestMethod.GET)
    public ModelAndView getMySalesman(Model model) {
    	logger.debug("Received request to show admin page");
    	ModelAndView mnv = new ModelAndView();
    	try{
    		model.addAttribute("userList",userService.getSalesMan(4));
    		model.addAttribute("purchaserList",userService.getUsersByRole(3));
    	}
    	catch(Exception e){
    		e.printStackTrace();
 	    	}
      	 mnv.setViewName("/web/purchaser/salesman"); 
    	
      	 return mnv;
	}
}
