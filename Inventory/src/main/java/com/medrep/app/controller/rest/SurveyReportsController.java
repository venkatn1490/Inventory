package com.medrep.app.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medrep.app.entity.SurveyEntity;
import com.medrep.app.model.AppResponse;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.PharmaRep;
import com.medrep.app.model.Survey;
import com.medrep.app.model.SurveyStatistics;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.DoctorService;
import com.medrep.app.service.EmailService;
import com.medrep.app.service.MedRepService;
import com.medrep.app.service.SurveyService;
import com.medrep.app.util.MedRepProperty;

@Controller
@RequestMapping("/api/survey")
public class SurveyReportsController {
	public static final String SUVERYS = "/getCompanySurveys";
	public static final String SUVERY_STATISTICS = "/getSurveyStatistics/{surveyId}";
	public static final String SURVEY_PENDING_DOCTORS = "/getSurveyPendingDoctors/{surveyId}";
	public static final String SURVEY_REPORTS_FOR_DOCTOR = "/getReportsForDoctor";
	public static final String SURVEY_REPORTS_FOR_PHARMA = "/getReportsForPharma";
	public static final String GET_REPORTS = "/getReport";

	private static final Logger logger = LoggerFactory.getLogger(SurveyReportsController.class);

	@Autowired
	private SurveyService surveyService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private MedRepService medrepService;

	@RequestMapping(value = SurveyReportsController.SUVERYS, method = RequestMethod.GET)
    public @ResponseBody List<Survey> getMyCompanySurveys() {
        logger.info("Request received for Get My Company Surveys");
        List<Survey> surveys = new ArrayList<Survey>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{


        			String  loginId = context.getUsername();
        			surveys = surveyService.getSurveysByRep(loginId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return surveys;
    }

	@RequestMapping(value = SurveyReportsController.SUVERY_STATISTICS, method = RequestMethod.GET)
    public @ResponseBody SurveyStatistics getSurveyStatistics(@PathVariable("surveyId") Integer surveyId) {
        logger.info("Request received for Get Survey Statistics");
        SurveyStatistics surveyStatistics = new SurveyStatistics();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String  loginId = context.getUsername();
        			surveyStatistics = surveyService.getSurveyStatisticsBySurveyId(surveyId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return surveyStatistics;
    }

	@RequestMapping(value = SurveyReportsController.SURVEY_PENDING_DOCTORS, method = RequestMethod.GET)
    public @ResponseBody  List<Survey> getSurveyPendingDoctors(@PathVariable("surveyId") Integer surveyId) {
        logger.info("Request received for Get Survey Pending Dcotors");
        List<Survey> surveys = new ArrayList<Survey>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String  loginId = context.getUsername();
        			surveys = surveyService.getPendingDoctorSurvey(surveyId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return surveys;
    }

	@RequestMapping(value = SurveyReportsController.SURVEY_REPORTS_FOR_DOCTOR, method = RequestMethod.GET)
    public @ResponseBody  AppResponse getSurveyReportsForDoctor(@RequestParam(required=false) Integer doctorId) {
        logger.info("Request received for Get Survey Pending Dcotors");
        AppResponse response=new AppResponse();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String  loginId = context.getUsername();
        			response.setResult( surveyService.getSurveyReport(doctorId,loginId));
        			response.setStatus("success");
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        			response.setStatus("error");
        			response.setMessage(e.getMessage());
        		}
        	}
        }
        return response;
    }

	@RequestMapping(value = SurveyReportsController.SURVEY_REPORTS_FOR_PHARMA, method = RequestMethod.GET)
    public @ResponseBody  AppResponse getSurveyReportForPharma(@RequestParam(required=false) Integer repId) {
        logger.info("Request received for Get Survey Pending Dcotors");
        AppResponse response=new AppResponse();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String  loginId = context.getUsername();
        			response.setResult( surveyService.getSurveyReportForPharma(repId,loginId));
        			response.setStatus("success");
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        			response.setStatus("error");
        			response.setMessage(e.getMessage());
        		}
        	}
        }
        return response;
    }

	@RequestMapping(value = SurveyReportsController.GET_REPORTS, method = RequestMethod.GET)
    public @ResponseBody  AppResponse getReport(@RequestParam(required=false) String format,@RequestParam(required=false) Integer repId,@RequestParam(required=false) Integer doctorId,@RequestParam Integer surveyId) {
        logger.info("Request received for Get Survey Pending Dcotors");
        AppResponse response=new AppResponse();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			if("pdf".equalsIgnoreCase(format)){

        			}else{

            			String to=MedRepProperty.getInstance().getProperties("surveys.reports.admin");
            			String email="";
            			String name= (String)SecurityContextHolder.getContext().getAuthentication().getName();
            			if(doctorId!=null && doctorId!=0){
            				Doctor d=doctorService.findDoctorById(doctorId+"");
            				name=d.getFirstName() +" "+d.getLastName();
            				email=d.getEmailId();
            			}else if(repId!=null && repId!=0){
            				PharmaRep pharma=medrepService.getPharamaRep(repId+"");
            				name=pharma.getFirstName()+" "+pharma.getLastName();
            				email=pharma.getEmailId();
            			}

            			SurveyEntity survey=surveyService.getSurvey(surveyId);
            			if(name!=null&& name.length()>0){
            				String msg="<br/><b>SurveyId :</b> <a target='_blank' href='"+survey.getSurveyUrl()+"'>"+survey.getSurveyId()+"</a><br/><b>Survey Title :</b> "+survey.getSurveyTitle()+"<br/><b>Survey Description :</b> "+survey.getSurveyDescription()+"<br/><b>Requested Doctor :</b> "+email;
            				emailService.sendMail(to,null,null,name+" requested a report for the survey","Hi Admin,<br/>"+name+" requested a report for the following survey. "+msg+"<br/><br/>Regards,<br/>MedRep Team.");
            			}

        			}

        			response.setResult("Your request has been submitted. You will receive the report as soon as the Survey has been completed.");
        			response.setStatus("success");
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        			response.setStatus("error");
        			response.setMessage("[API Error]"+e.getMessage());
        		}
        	}
        }
        return response;
    }
}
