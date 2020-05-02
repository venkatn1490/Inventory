/**
 *
 */
package com.medrep.app.service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.model.Mail;
import com.medrep.app.util.MedRepProperty;

/**
 * @author Umar Ashraf
 *
 */
@Service("emailService")
@EnableAsync
public class EmailService
{
	@Autowired
    private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	public static final String VERIFY_EMAIL = "VERIFY_EMAIL";
	public static final String RESET_PASSWORD = "RESET_PASSWORD";
	public static final String DOCTOD_ACCOUNT_ACTIVATION = "DOCTOR_ACCOOUNT_ACTIVATION";
	public static final String REP_ACCOUNT_ACTIVATION = "REP_ACCOOUNT_ACTIVATION";
	public static final String FEEDBACK_ACK = "FEEDBACK_ACK";
	public static final String FEEDBACK_ADMIN_ACK = "FEEDBACK_ADMIN_ACK";
	public static final String EMAIL_OTP = "EMAIL_OTP";
	public static final String DOCTOR_ACCOUNT_ACTIVATION_INTIMATE = "DOCTOR_ACCOUNT_ACTIVATION_INTIMATE";
	public static final String SURVEY_REPORT="SURVEY_REPORT";
	public static final String SAMPLE_REQUEST = "SAMPLE_REQUEST";

	private Map<String,String> templateMap = new HashMap<String, String>();
	private static final Log log = LogFactory.getLog(EmailService.class);

	public EmailService()
	{
		templateMap.put(EmailService.VERIFY_EMAIL + "_SUBJECT", "Please verify your email address");
		templateMap.put(EmailService.VERIFY_EMAIL + "_TEMPLATE", "Email_Verification.vm");
		templateMap.put(EmailService.RESET_PASSWORD + "_SUBJECT", "Reset your password on Medrep app");
		templateMap.put(EmailService.RESET_PASSWORD + "_TEMPLATE", "Email_PasswordReset.vm");
		templateMap.put(EmailService.DOCTOD_ACCOUNT_ACTIVATION + "_SUBJECT", "Your MedRep account is activated now");
		templateMap.put(EmailService.DOCTOD_ACCOUNT_ACTIVATION + "_TEMPLATE", "Email_D_Activation.vm");
		templateMap.put(EmailService.REP_ACCOUNT_ACTIVATION + "_SUBJECT", "Your MedRep account is activated now");
		templateMap.put(EmailService.REP_ACCOUNT_ACTIVATION + "_TEMPLATE", "Email_P_Activation.vm");
		templateMap.put(EmailService.FEEDBACK_ACK + "_SUBJECT", "Thanks for writing to us");
		templateMap.put(EmailService.FEEDBACK_ACK + "_TEMPLATE", "Email_Feedback.vm");
		templateMap.put(EmailService.FEEDBACK_ADMIN_ACK + "_SUBJECT", "Contact request received.");
		templateMap.put(EmailService.FEEDBACK_ADMIN_ACK + "_TEMPLATE", "Email_Admin_Feedback.vm");
		templateMap.put(EmailService.EMAIL_OTP + "_SUBJECT", "One time password");
		templateMap.put(EmailService.EMAIL_OTP + "_TEMPLATE", "Email_OTP.vm");
		templateMap.put(EmailService.DOCTOR_ACCOUNT_ACTIVATION_INTIMATE + "_SUBJECT", "Verification Process Initiated");
		templateMap.put(EmailService.DOCTOR_ACCOUNT_ACTIVATION_INTIMATE + "_TEMPLATE", "Email_D_Accont_Activation_Intimate.vm");
		templateMap.put(EmailService.SAMPLE_REQUEST + "_SUBJECT", " Sample Requested by Doctor [Campaign: ");
		templateMap.put(EmailService.SAMPLE_REQUEST + "_TEMPLATE", "Sample_Request.vm");


		templateMap.put(EmailService.SURVEY_REPORT + "_TEMPLATE", "SurveyReport.vm");
		templateMap.put(EmailService.SURVEY_REPORT + "_SUBJECT", "Requested Survey");

		templateMap.put("EMAIL_FROM", "info@medrep.in");
	}

	@Async
	public void sendMail(Mail mail)
	{
		try
		{

		  MimeMessage mimeMessage = mailSender.createMimeMessage();
		  MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
		  helper.setFrom(templateMap.get("EMAIL_FROM"));
		  if(mail.getMailTo() != null && mail.getMailTo().length() > 0)
		  {
			  helper.setTo(mail.getMailTo());
			  if(mail.getMailBcc() != null && mail.getMailBcc().length() > 0)
			  {
				  helper.setBcc(mail.getMailBcc());
			  }
			  if(mail.getMailCc() != null && mail.getMailCc().length() > 0)
			  {
				  helper.setCc(mail.getMailCc());
			  }
			  helper.setSubject(mail.getTemplateName() );


			  Template template = velocityEngine.getTemplate(templateMap.get(mail.getTemplateName() + "_TEMPLATE"));
			  VelocityContext velocityContext = new VelocityContext();
			  Map<String,String> valueMap = mail.getValueMap();
			  valueMap.put("INFOMAILID", MedRepProperty.getInstance().getProperties("medrep.email.info.id"));
			  if(valueMap != null)
			  {
				  for(String key :valueMap.keySet())
				  {
					  velocityContext.put(key, valueMap.get(key));
				  }
			  }
			  velocityContext.put("TITLE",templateMap.get(mail.getTemplateName() + "_SUBJECT"));
			  StringWriter stringWriter = new StringWriter();
			  template.merge(velocityContext, stringWriter);
			  mimeMessage.setContent(stringWriter.toString(), "text/html");
			  mailSender.send(mimeMessage);
		  }


		}
		catch(Exception e)
		{
			log.error("Send Email Error ", e);
		}

	}

	
	@Async
	public void sendMultipleMail(Mail mail)
	{
		try
		{

		  MimeMessage mimeMessage = mailSender.createMimeMessage();
		  MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
		  helper.setFrom(templateMap.get("EMAIL_FROM"));
		  if(mail.getMultipleMailTo() != null )
		  {
			  helper.setTo(mail.getMultipleMailTo());
			  if(mail.getMailBcc() != null && mail.getMailBcc().length() > 0)
			  {
				  helper.setBcc(mail.getMailBcc());
			  }
			  if(mail.getMailCc() != null && mail.getMailCc().length() > 0)
			  {
				  helper.setCc(mail.getMailCc());
			  }
			  helper.setSubject(templateMap.get(mail.getTemplateName() + "_SUBJECT"));


			  Template template = velocityEngine.getTemplate(templateMap.get(mail.getTemplateName() + "_TEMPLATE"));
			  VelocityContext velocityContext = new VelocityContext();
			  Map<String,String> valueMap = mail.getValueMap();
		//	  valueMap.put("INFOMAILID", MedRepProperty.getInstance().getProperties("medrep.email.info.id"));
			  if(valueMap != null)
			  {
				  for(String key :valueMap.keySet())
				  {
					  velocityContext.put(key, valueMap.get(key));
				  }
			  }
			  velocityContext.put("TITLE",templateMap.get(mail.getTemplateName() + "_SUBJECT"));
			  StringWriter stringWriter = new StringWriter();
			  template.merge(velocityContext, stringWriter);
			  mimeMessage.setContent(stringWriter.toString(), "text/html");
			  mailSender.send(mimeMessage);
		  }


		}
		catch(Exception e)
		{
			log.error("Send Email Error ", e);
		}

	}

	@Async
	public void sendMail(String to,String bcc,String cc,String subject,String message)
	{
		try
		{

		  MimeMessage mimeMessage = mailSender.createMimeMessage();
		  MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
		  helper.setFrom(templateMap.get("EMAIL_FROM"));
		  if(to != null && to.length() > 0)
		  {
			  helper.setTo(to);
			  if(bcc!=null && bcc.length() > 0)
			  {
				  helper.setBcc(bcc);
			  }
			  if(cc!= null && cc.length() > 0)
			  {
				  helper.setCc(cc);
			  }
			  helper.setSubject(subject);
			  mimeMessage.setContent(message, "text/html");
			  mailSender.send(mimeMessage);
		  }


		}
		catch(Exception e)
		{
			log.error("Send Email Error ", e);
		}

	}

}
