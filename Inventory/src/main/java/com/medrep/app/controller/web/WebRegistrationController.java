package com.medrep.app.controller.web;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.model.AppResponse;
import com.medrep.app.model.OTP;
import com.medrep.app.service.OTPService;
import com.medrep.app.service.RegistrationService;
import com.medrep.app.util.PasswordProtector;

@Controller
@RequestMapping("/web/registration")
public class WebRegistrationController {
	
	@Autowired
	OTPService otpService;
	
	public static final String EMAIL_VERIFICATION = "/verifyEmail/{token}";
	private static final Logger logger = LoggerFactory.getLogger(WebRegistrationController.class);
	
	@RequestMapping(value = WebRegistrationController.EMAIL_VERIFICATION, method = RequestMethod.GET)
    public ModelAndView verifyEmail(@PathVariable("token") String token) {
	{
        logger.info("Request received for token verification");
        ModelAndView mnv = new ModelAndView();  
        token = token.replace('.', '/').replace('-', '+');
        String decryptedPassword = PasswordProtector.decrypt(token);
        StringTokenizer tokenizer = new StringTokenizer(decryptedPassword,"MEDREP");
        String emailId = tokenizer.nextToken();
        String decToken = tokenizer.nextToken();      
        String message = "";
        OTP otp = new OTP();
        otp.setOtp(decToken);
        otp.setVerificationId(emailId);
        otp.setType("EMAIL");
        try
        {
        	otp =  otpService.verifyOTP(otp);
        	if("VERIFIED".equals(otp.getStatus()))
        	{
        		 message = "Successfully Registered";
        	}
        	else if("EXPIRED".equals(otp.getStatus()))
        	{
        		 message = "OTP Expired. Please generate another OTP";
        	}
        	else
        	{
        		 message = "Invalid OTP";
        	}
        }
        catch(Exception e)
        {
        	 logger.error("get failed "+e);
        	 message = "System error occured while verifying OTP";
        }        
        mnv.addObject("message", message);
        mnv.addObject("url", "./login.do");
   	 	mnv.addObject("url_name","Login");
      	mnv.setViewName("/web/auth/email-validation"); 
        return mnv;
    }

}

}