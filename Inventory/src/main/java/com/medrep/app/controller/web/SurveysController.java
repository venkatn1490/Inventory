package com.medrep.app.controller.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.Notification;
import com.medrep.app.model.Survey;
import com.medrep.app.service.DoctorService;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.SurveyService;
import com.medrep.app.service.TherapeuticAreaService;
import com.medrep.app.util.MedrepException;

@Controller
@RequestMapping("/web/admin")
@SessionAttributes({ "companyMap" })
public class SurveysController {

	protected static Logger logger = Logger.getLogger(SurveysController.class);

	private static final String VIEW = "/web/admin/surveys";
	@Autowired
	SurveyService surveyService;
	@Autowired
	TherapeuticAreaService therapeuticAreaService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	DoctorService doctorService;

	/**
	 * Handles and retrieves the admin JSP page that only admins can see
	 *
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/surveys.do", method = RequestMethod.GET)
	public String getAdminSurveyPage(Model model) {
		logger.debug("Received request to show admin page");
		try {
			model.addAttribute("surveyList",surveyService.getAdminSurveysList());
			model.addAttribute("companyMap",notificationService.getAllCompanys());
			model.addAttribute("therapeuticsAreaList",therapeuticAreaService.getAll());
			model.addAttribute("surveyFormObj", new Survey());
		} catch (MedrepException me) {
			model.addAttribute("notificationmsg", me.getMessage());
		}
		return VIEW;
	}

	@RequestMapping(value = "/createSurvey.do", method = RequestMethod.POST)
	public String createAdminSurvey(Model model,@ModelAttribute("surveyFormObj") Survey survey) {
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateToStr = format.format(curDate);
		survey.setCreatedOn(dateToStr);
		try {
			surveyService.createAdminSurvey(survey);
			model.addAttribute("surveyList",surveyService.getAdminSurveysList());
		} catch (MedrepException me) {
			model.addAttribute("notificationmsg", me.getMessage());
		}

		Thread thread = null;
		try {
			Runnable runSurvey = new Runnable() {
				@Override
				public void run() {
					surveyService.pushSurvey();
				}
			};
			thread = new Thread(runSurvey);
			thread.start();
		} catch (Exception e) {
			if(thread != null){
				thread.stop();
			}
			e.printStackTrace();
		}
		return VIEW;

	}

	@RequestMapping(value = "/updateSurvey.do", method = RequestMethod.POST)
	public String updateAdminSurvey(Model model,@ModelAttribute("surveyFormObj") Survey survey) {
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateToStr = format.format(curDate);
		survey.setCreatedOn(dateToStr);
		try {
			surveyService.updateAdminSurvey(survey);
			model.addAttribute("surveyFormObj", new Survey());
			model.addAttribute("surveyList",surveyService.getAdminSurveysList());
			model.addAttribute("notificationmsg", "Survey Updated Successfully");
		} catch (MedrepException me) {
			model.addAttribute("notificationmsg", me.getMessage());
		}

		Thread thread = null;
		try {
			Runnable runSurvey = new Runnable() {
				@Override
				public void run() {
					surveyService.pushSurvey();
				}
			};
			thread = new Thread(runSurvey);
			thread.start();
		} catch (Exception e) {
			if(thread != null){
				thread.stop();
			}
			e.printStackTrace();
		}
		return VIEW;
	}

	@RequestMapping(value = "/getSurveyModelData.do", method = RequestMethod.GET)
	public @ResponseBody Survey getAdminSurveyById(@RequestParam String surveyId) {
		Survey survey = new Survey();
		try {
			survey = surveyService.getAdminSurveyById(surveyId);
		} catch (MedrepException me) {
			me.printStackTrace();
		}
		return survey;
	}

	@RequestMapping(value = "/removeSurvey.do", method = RequestMethod.POST)
	public String deleteAdminSurvey(Model model,@ModelAttribute("surveyFormObj") Survey survey,@RequestParam String surveyDelId) {
		try{
			surveyService.removeAdminSurvey(surveyDelId);
			model.addAttribute("surveyList", surveyService.getAdminSurveysList());
			model.addAttribute("notificationmsg", "Survey Removed Successfully");
		}catch(MedrepException me){
			model.addAttribute("notificationmsg", me.getMessage());
		}
		return VIEW;
	}


	 void setModelObjects(Model model)throws MedrepException{
		model.addAttribute("surveyList", surveyService.getAdminSurveysList());
		model.addAttribute("therapeuticsAreaList",therapeuticAreaService.getAll());
	}

	@RequestMapping(value = "/publishSurvey.do", method = RequestMethod.POST)
	public String publishSurvey(Model model,@ModelAttribute("surveyFormObj") Survey survey) {
		try{
			ArrayList<DeviceTokenEntity> devices=surveyService.publish(survey);
			setModelObjects(model);
			if(devices!=null && !devices.isEmpty())
				notificationService.pushMessage("A New Survey is available.Please click here to know more.", devices);
				model.addAttribute("notificationmsg", "Survey has been published");
		}catch(Exception me){
			me.printStackTrace();
			logger.error("survey error");
			logger.error(me);
			model.addAttribute("notificationmsg", me.getMessage());
		}
		return VIEW;
	}


	@RequestMapping(value = "/getDoctorsByTAreas.do", method = {RequestMethod.POST,RequestMethod.GET})
	public String getDocotorsByTAreas(Model model,@RequestParam(required=false) String tareaIds) {
    	try{
    		List<Doctor> list = new ArrayList<Doctor>();
    		if(tareaIds!=null && tareaIds.contains(",")){
    			for(String tarea : tareaIds.split(",")) {
    				List<Doctor> docList = doctorService.findByTAreaId(tarea);
    				list.addAll(docList);
    			}
    		}else if(tareaIds!=null) {
    			list = doctorService.findByTAreaId(tareaIds);
    		}
    		model.addAttribute("docsList",list);
    		setModelObjects(model);
    		model.addAttribute("publish", "true");
    	}catch(Exception me){
    		model.addAttribute("notificationmsg", me.getMessage());
    	}
    	return "/web/admin/doctorsList";
	}

}
