package com.medrep.app.controller.web;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.ForgotPassword;
import com.medrep.app.model.Mail;
import com.medrep.app.model.OTP;
import com.medrep.app.model.Role;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.DisplayPictureService;
import com.medrep.app.service.EmailService;
import com.medrep.app.service.MasterDataService;
import com.medrep.app.service.OTPService;
import com.medrep.app.service.RegistrationService;
import com.medrep.app.service.UserService;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.PasswordProtector;
import com.medrep.app.util.Util;

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

	@Autowired
	DisplayPictureService displayPictureService;

	@Autowired
	RegistrationService registrationService;

	@Autowired
	OTPService otpService;


	@Autowired
	EmailService emailService;

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

    @RequestMapping(value = "/common.do", method = RequestMethod.GET)
    public String getCommonPage(HttpServletRequest request) {
    	System.out.println("Received request to show common page");
    	UserSecurityContext context = null;
    	String page = "";
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	boolean isAdmin = false;
        	boolean isWebUser = false;
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		for(Role role : context.getAuthorities())
        		{
        			if("ROLE_ADMIN".equalsIgnoreCase(role.getName()))
					{
        				isAdmin = true;
					}else if("ROLE_WEB_USER".equalsIgnoreCase(role.getName()))
					{
        				isWebUser = true;
					}
        		}
        	}

        	request.getSession().setAttribute("UserSecuritId", context.getUserSecurityId());
        	request.getSession().setAttribute("UserName", userService.fetchUserNameByUserSecurityId(context.getUserSecurityId()));
        	DisplayPicture dp = displayPictureService.getUserDisplayPicture(context.getUserSecurityId());
        	if(dp != null)
        	{
        		request.getSession().setAttribute("profiePicture",dp);
        	}

        	if(isAdmin)
        	{
        		page = "redirect:/web/admin/dashboard.do";
        	}
        	else if(isWebUser)
        	{
        		page = "redirect:/web/main/coming-soon.do";
        	}


        }

    	return page;
	}


    @RequestMapping(value = "/sendFPLinkToEmail.do", method = RequestMethod.GET)
	public @ResponseBody String verifyEmailId(@RequestParam String email) {
    	int secId = 0;
    	try{
    		secId = registrationService.getUserSecurityId(email);
    		OTP emailOTP = new OTP();
    		emailOTP.setSecurityId(secId);
    		emailOTP.setVerificationId(email);
    		emailOTP.setType("EMAIL");
    		emailOTP = otpService.reCreateOTP(emailOTP);
    		sendEmailForVerification(emailOTP);
    	}catch(Exception e){
    		secId = 0;
    	}
    	return secId > 0 ? "true" : "false";
	}

    @RequestMapping(value = "/fpForm.do", method = RequestMethod.GET)
   	public ModelAndView getForgotPasswordForm(Model model, @RequestParam String token) { //TODO: after clicking on the link ,this method executes
    	Map<String,String> map =verifyToken(token);
    	ModelAndView mnv = new ModelAndView();
    	mnv.addObject("statusMsg", map.get("statusMsg"));
    	mnv.addObject("email", map.get("email"));
    	mnv.addObject("url", "./support.do");
		mnv.addObject("url_name","Support");
	   	mnv.setViewName("/web/auth/forgotpassword");
       	return mnv;
   	}

    @RequestMapping(value = "/updatePassword.do", method = RequestMethod.POST)
   	public String setUpdatePassword(@RequestParam("email") String email,@RequestParam("newPassword") String np,@RequestParam("confirmPassword") String cnp) {
    	try{
    	ForgotPassword fp =new ForgotPassword();
    	fp.setUserName(email);
    	fp.setNewPassword(np);
    	registrationService.updatePassword(fp);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "redirect:/web/auth/login.do";
   	}

    private void sendEmailForVerification(OTP emailOTP)
	{
      try{
		Mail mail = new Mail();
		mail.setTemplateName(EmailService.RESET_PASSWORD);
		mail.setMailTo(emailOTP.getVerificationId());
		String url = MedRepProperty.getInstance().getProperties("medrep.home") + "web/auth/fpForm.do?token=";
		String token = emailOTP.getVerificationId() + "MEDREP" + PasswordProtector.decrypt(emailOTP.getOtp());
		String encryteptoken = PasswordProtector.encrypt(token) ;
		encryteptoken = encryteptoken.replace('/', '.').replace('+','-');
		url = url + encryteptoken;
		String name = "";
		com.medrep.app.model.User user =userService.findUserByEmailId(emailOTP.getVerificationId());
		if(user!=null) name = Util.getTitle(user.getTitle())+user.getFirstName() + " " +user.getLastName();
		Map<String,String> map =new HashMap<String,String>();
		map.put("URL", url);
		map.put("NAME", name);
		mail.setValueMap(map);
		emailService.sendMail(mail);
      }catch(Exception e){
    	  e.printStackTrace();
      }
	}

    private Map<String,String> verifyToken(String token){
    	  Map<String,String> map = new HashMap<String,String>();
    	  token = token.replace('.', '/').replace('-', '+');
    	  String decryptedPassword = PasswordProtector.decrypt(token);
          StringTokenizer tokenizer = new StringTokenizer(decryptedPassword,"MEDREP");
          String emailId = tokenizer.nextToken();
          String decToken = tokenizer.nextToken();
          OTP otp = new OTP();
          otp.setOtp(decToken);
          otp.setVerificationId(emailId);
          otp.setType("EMAIL");
          otp =  otpService.verifyOTP(otp);
      	 if("VERIFIED".equals(otp.getStatus()))
      	{
      		map.put("statusMsg", "success");
      	}
      	else if("EXPIRED".equals(otp.getStatus()))
      	{
      		map.put("statusMsg", "fail");
      	}
      	else
      	{
      		map.put("statusMsg", "fail");
      	}
      	map.put("email", emailId);
      	return map;
    }

}
