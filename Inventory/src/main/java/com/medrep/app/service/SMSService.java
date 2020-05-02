package com.medrep.app.service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.medrep.app.model.SMS;
import com.medrep.app.util.MedRepProperty;

@Service("smsService")
public class SMSService {
	
	public static final String VERIFY_NO = "VERIFY_NO";
	public static final String RESET_PASSWORD = "RESET_PASSWORD";
	public static final String ACCOUNT_ACTIVATION = "ACCOOUNT_ACTIVATION";
	public static final String NEW_APPOINTMENT = "NEW_APPOINTMENT";
	public static final String CONFIRM_APPOINTMENT = "CONFIRM_APPOINTMENT";
	public static final String FEEDBACK = "FEEDBACK";
	public static final String NOTIFICATION = "NOTIFICATION";
	public static final String INVITE = "INVITE";
	
	private Map<String,String> templateMap = new HashMap<String, String>();
	
	private static final Log log = LogFactory.getLog(SMSService.class);
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	public SMSService()
	{
		templateMap.put(SMSService.VERIFY_NO + "_TEMPLATE", "Mobile_Verification.vm");
		templateMap.put(SMSService.RESET_PASSWORD + "_TEMPLATE", "Mobile_PasswordReset.vm");
		templateMap.put(SMSService.ACCOUNT_ACTIVATION + "_TEMPLATE", "Mobile_Activation.vm");
		templateMap.put(SMSService.NEW_APPOINTMENT + "_TEMPLATE", "Mobile_NewAppointment.vm");
		templateMap.put(SMSService.CONFIRM_APPOINTMENT + "_TEMPLATE", "Mobile_ConfirmAppointment.vm");
		templateMap.put(SMSService.FEEDBACK + "_TEMPLATE", "Mobile_Feedback.vm");
		templateMap.put(SMSService.NOTIFICATION + "_TEMPLATE", "Mobile_Notification.vm");
		templateMap.put(SMSService.INVITE + "_TEMPLATE", "Mobile_Invite.vm");
		
	}
	
	public void sendSMS(SMS sms)
	{
		try
		{
		 RestTemplate restTemplate = new RestTemplate();
		 Template template = velocityEngine.getTemplate(templateMap.get(sms.getTemplate() + "_TEMPLATE"));
		 VelocityContext velocityContext = new VelocityContext();
		 Map<String,String> valueMap = sms.getValueMap();
		 if(valueMap != null)
		 {
			  for(String key :valueMap.keySet())
			  {
				  velocityContext.put(key, valueMap.get(key));
			  }
		 }		 
		 StringWriter stringWriter = new StringWriter();
		 template.merge(velocityContext, stringWriter);
		 String message = stringWriter.toString();
		 String baseURL = MedRepProperty.getInstance().getProperties("sms.url");
		 baseURL = baseURL + "&mobilenumber=" + sms.getPhoneNumber();
		 baseURL = baseURL + "&message=" + message;
		 log.info("Sending text message to " + sms.getPhoneNumber());
		 ResponseEntity<String> response = restTemplate.getForEntity(baseURL,String.class);
		 log.info("Response received from SMS country " + response.getBody());
		}
		catch(Exception e)
		{
			log.error("Send SMS failed", e);
		}
	}

}
