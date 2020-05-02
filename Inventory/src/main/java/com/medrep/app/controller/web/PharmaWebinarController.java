package com.medrep.app.controller.web;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.model.Notification;
import com.medrep.app.model.PharmaWebinar;
import com.medrep.app.service.CompanyService;
import com.medrep.app.service.DoctorService;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.PharmaWebinarService;
import com.medrep.app.service.SurveyService;
import com.medrep.app.service.TherapeuticAreaService;
import com.medrep.app.util.MedrepException;


@Controller
@RequestMapping("/web/admin")
@SessionAttributes( { "companyMap"})
public class PharmaWebinarController {

	private static final String VIEW ="/web/admin/webinars";
	protected static Logger logger = Logger.getLogger("controller");

	@Autowired
	NotificationService notificationService;
	@Autowired
	CompanyService companyService;

	@Autowired
	DoctorService doctorService;
	@Autowired
	SurveyService surveyService;

	@Autowired
	PharmaWebinarService pharmaWebinarService;
	
	@Autowired
	TherapeuticAreaService therapeuticAreaService;
	/**
     * Handles and retrieves the admin JSP page that only admins can see
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/pharmawebinars.do", method = RequestMethod.GET)
    public String getAdminNotificationPage(Model model) {
    	logger.debug("Received request to show admin page");
    try{
    	 setModelObjects(model, null);
       	 model.addAttribute("companyMap", pharmaWebinarService.getAllCompanys());
    }catch(MedrepException e){
    	model.addAttribute("webinarssmsg", e.getMessage());
    }
		return VIEW;
	}

    @RequestMapping(value = "/createWebinars.do", method = RequestMethod.POST)
    public String createNotification(Model model, @ModelAttribute("pharmaWebinarFormObj") PharmaWebinar pharmaWebinar) {
    	logger.debug("Received request to show admin page");
    	try{
    		pharmaWebinarService.createWebinars(pharmaWebinar);
    		setModelObjects(model, pharmaWebinar);
    		model.addAttribute("webinarssmsg", "webianrs Created Successfully");
    	}catch(MedrepException me){
    		model.addAttribute("webinarssmsg", me.getMessage());
    	}


    	return VIEW;
	}	
    
    @RequestMapping(value = "/updateWebinars.do", method = RequestMethod.POST)
    public String createNotification(Model model, @RequestParam("webniarId") Integer webniarId,@RequestParam("statusDropDown") String status) throws MedrepException {
    	logger.debug("Received request to show admin page");

    		pharmaWebinarService.updateWebinarStatus(webniarId,status);
    		setModelObjects(model, null);
    		model.addAttribute("webinarssmsg", "webianrs Created Successfully");   	 
    	   	 return VIEW;
    	
	}	
	
	private void setModelObjects(Model model, PharmaWebinar pharmaWebinar)throws MedrepException{
		model.addAttribute("pharmaWebinarsList", pharmaWebinarService.getAdminWebinarsList());
		if(pharmaWebinar == null) model.addAttribute("pharmaWebinarFormObj", new PharmaWebinar());
		else model.addAttribute("pharmaWebinarFormObj",pharmaWebinar);
	}

	
}
