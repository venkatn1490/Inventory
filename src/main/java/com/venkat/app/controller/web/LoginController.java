package com.venkat.app.controller.web;



import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.venkat.app.model.Role;
import com.venkat.app.model.User;
import com.venkat.app.model.UserSecurityContext;
import com.venkat.app.service.MasterDataService;
import com.venkat.app.service.UserService;


/**
 * Handles and retrieves the common or admin page depending on the URI template.
 * A user must be log-in first he can access these pages.  Only the admin can see
 * the adminpage, however.
 */
@Controller
@RequestMapping("/web/auth")
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	MasterDataService masterDataService;


	protected static Logger logger = Logger.getLogger("controller");

	/**
	 * Handles and retrieves the common JSP page that everyone can see
	 *
	 * @return the name of the JSP page
	 */

	/**
	 * Handles and retrieves the login JSP page
	 *
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public ModelAndView getLoginPage(@RequestParam(value="error", required=false) boolean error, ModelMap model) {
		logger.debug("Received request to show login page");
		ModelAndView mnv = new ModelAndView();
        System.out.println("Received request to show login page");
		if (error == true) {
			// Assign an error message
			mnv.addObject("error", "You have entered an invalid username or password!");
		}
		else
		{
			mnv.addObject("error", "");
		}
		// This will resolve to /WEB-INF/view/loginpage.jsp
		mnv.addObject("url", "./support.do");
		mnv.addObject("url_name","Support");
	   	mnv.setViewName("/web/auth/loginpage");
		return mnv;
	}
	@RequestMapping(value = "/register.do", method = RequestMethod.GET)
	public ModelAndView registerUser(ModelMap model) {
		logger.debug("Received request to show login page");
		ModelAndView mnv = new ModelAndView();
        System.out.println("Received request to show login page");
		
	   	mnv.setViewName("/web/auth/register");
		return mnv;
	}
    @RequestMapping(value = "/common.do", method = RequestMethod.GET)
    public String getCommonPage(HttpServletRequest request) {
    	System.out.println("Received request to show common page");
    	UserSecurityContext context = null;
    	String page = "";
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	boolean isAdmin = false;
        	
        	boolean isPurchaser = false;
        	
        	boolean isSupplier = false;
        	
        	boolean isSaleman = false;
        	
        	boolean isCustomer = false;

        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		for(Role role : context.getAuthorities())
        		{
        			if("ROLE_ADMIN".equalsIgnoreCase(role.getName())){
        				isAdmin = true;
					}
        			else if("ROLE_PURCHASER".equalsIgnoreCase(role.getName())){
        				isPurchaser = true;
					}
        			else if("ROLE_SUPPLIER".equalsIgnoreCase(role.getName())) {
        				isSupplier = true;
        			}
        			else if("ROLE_SALESMAN".equalsIgnoreCase(role.getName())) {
        				isSaleman = true;
        			}
        			else if("ROLE_CUSTOMER".equalsIgnoreCase(role.getName())) {
        				isCustomer = true;
        			}
        		}
        	}

        	request.getSession().setAttribute("UserSecuritId", context.getUserSecurityId());
        	request.getSession().setAttribute("UserName", userService.fetchUserNameByUserSecurityId(context.getUserSecurityId()));
        
        	if(isAdmin)
        	{
        		page = "redirect:/web/admin/dashboard.do";
        	}
        	
        	else if(isPurchaser)
        	{
        		page = "redirect:/web/purchaser/dashboard.do";
        	}
        	else if(isSupplier)
        	{
        		page = "redirect:/web/supplier/dashboard.do";
        	}
        	else if(isSaleman)
        	{
        		page = "redirect:/web/salesman/dashboard.do";
        	}
        	else if(isCustomer)
        	{
        		page = "redirect:/web/customer/dashboard.do";
        	}

        }

    	return page;
	}
    @RequestMapping(value="/createUser.do",method=RequestMethod.POST)
    public ModelAndView createAsstManager( 
    		@RequestParam("firstName") String first_name,
			 @RequestParam("lastName") String lastName,
			 @RequestParam("email") String email, 								
			 @RequestParam("mobile") String mobile,
			 @RequestParam("title") String title,
			 @RequestParam("gender") String gender,
			 @RequestParam("dob") String dob,
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
    	 	usermodel.setRoleId(3);
    	 	    	 	
    	 	if(buttonName.equals("Create")){
    	 		model.addAttribute("user",usermodel);
    	 		userService.createUser(model);
    	 	}
    	 	logger.debug("Received request to show admin page");        
    	 	logger.debug("Received request to show admin page");
    	 	}catch(Exception e){
    	 		e.printStackTrace();
    	 		
    	 	}
     		
    	    mnv.setViewName("redirect:/web/auth/login.do"); 
    	return mnv;
    }

}
