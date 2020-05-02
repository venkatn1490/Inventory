package com.medrep.app.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.medrep.app.model.HealthTipCategory;
import com.medrep.app.model.HealthTipModel;
import com.medrep.app.model.HealthTipSource;
import com.medrep.app.model.TransformCategory;
import com.medrep.app.model.TransformModel;
import com.medrep.app.model.TransformSource;
import com.medrep.app.service.CachingService;
import com.medrep.app.service.HealthTipService;
import com.medrep.app.service.TherapeuticAreaService;
import com.medrep.app.service.TransformService;
import com.medrep.app.util.MedrepException;


@Controller
@RequestMapping("/web/patient")
@SessionAttributes( {"tareaList"})
public class HealthTipController {

	private static final String VIEW ="/web/patient/healthtips";
	protected static Logger logger = Logger.getLogger("controller");

	@Autowired
	HealthTipService healthtipService;
	@Autowired
	CachingService cachingService;

	@Autowired
	TherapeuticAreaService therapeuticAreaService;
	/**
     * Handles and retrieves the admin JSP page that only admins can see
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/healthtips.do", method = RequestMethod.GET)
    public String getHealthTipList(Model model) {
    	logger.debug("Received request to show admin page");
    try{
    	 setModelObjects(model, null, null, null);
    	 if(!model.containsAttribute("tareaList")) {
    	     model.addAttribute("tareaList", therapeuticAreaService.getAll());
    	 }
    }catch(MedrepException e){
    	model.addAttribute("healthtipmsg", e.getMessage());
    }
		return VIEW;
	}

    @RequestMapping(value = "/uploadHealthTip.do", method = RequestMethod.POST)
    public String uploadHealthtip(Model model,@ModelAttribute("healthtipFormObj") HealthTipModel healthtip,HttpServletRequest request) {
    	logger.debug("Received request to show admin page");
    try{
    	 List<HealthTipCategory> list = healthtipService.getAllHealthTipCategories();
    	 if(list == null || list.isEmpty()) {
    		 setModelObjects(model, null, null,null);
    		 model.addAttribute("healthtipmsg", "Please create at least one Category");
    		 return VIEW;
    	 }
    	 List<HealthTipSource> list1 = healthtipService.getAllHealthTipSources();
    	 if(list1 == null || list1.isEmpty()) {
    		 setModelObjects(model, null, null,null);
    		 model.addAttribute("healthtipmsg", "Please create at least one Source");
    		 return VIEW;
    	 }
    	 healthtip.setUserId((Integer)request.getSession().getAttribute("UserSecuritId"));
    	 healthtipService.uploadHealthTip(healthtip);
    	 setModelObjects(model, null, null,null);
    	 model.addAttribute("healthtipmsg", "Upload HealthTip Successfully");
    	 cachingService.clear();
    }catch(Exception e){
    	model.addAttribute("healthtipmsg", e.getMessage());
    }
		return VIEW;
	}

    @RequestMapping(value = "/uploadHealthTipCategory.do", method = RequestMethod.POST)
    public String uploadHealthTipCategory(Model model,@ModelAttribute("healthtipCategoryFormObj") HealthTipCategory category,HttpServletRequest request) {
    	logger.debug("Received request to create category page");
    try{
    	 category.setUserId((Integer)request.getSession().getAttribute("UserSecuritId"));
    	 healthtipService.createHealthTipCategory(category);
    	 setModelObjects(model, null, null,null);
    	 model.addAttribute("healthtipmsg", "Create HealthTip Category Successfully");
    	 cachingService.clear();
    }catch(MedrepException e){
    	model.addAttribute("healthtipmsg", e.getMessage());
    }
		return VIEW;
	}

    @RequestMapping(value = "/getHealthTipData.do", method = RequestMethod.GET)
	public @ResponseBody HealthTipModel getHealthTipById(@RequestParam String healthTipId) {
    	return healthtipService.getHealthTipById(Integer.valueOf(healthTipId));
	}


    @RequestMapping(value = "/deleteHealthTip.do", method = RequestMethod.POST)
   	public String deleteHealthTip(Model model,@RequestParam String healthtipId) {
       	try{
       		healthtipService.deleteHealthTip(healthtipId);
       		setModelObjects(model,null,null,null);
       		model.addAttribute("healthtipmsg", "HealthTip Removed Successfully");
       		cachingService.clear();
       	}catch(MedrepException me){
       		model.addAttribute("healthtipmsg", me.getMessage());
       	}
       	return VIEW;
   	}

    private void setModelObjects(Model model, HealthTipModel healthtip ,HealthTipCategory category, HealthTipSource source)throws MedrepException{
		model.addAttribute("healthTipList", healthtipService.getAllHealthTips());
	
		if(healthtip == null) model.addAttribute("healthtipFormObj", new HealthTipModel());
		else model.addAttribute("healthtipFormObj",healthtip);
		if(category == null) model.addAttribute("healthtipCategoryFormObj", new HealthTipCategory());
		else model.addAttribute("healthtipCategoryFormObj",category);
		if(source == null) model.addAttribute("healthtipSourceFormObj", new HealthTipSource());
		else model.addAttribute("healthtipSourceFormObj",source);

		//Get all Sources
		model.addAttribute("healthtipSourceList", healthtipService.getAllHealthTipSources());

		model.addAttribute("healthtipCategoryList", healthtipService.getAllHealthTipCategories());
	}

    @RequestMapping(value = "/uploadHealthTipSource.do", method = RequestMethod.POST)
    public String uploadHealthtipSource(Model model,@ModelAttribute("healthtipCategoryFormObj") HealthTipSource source,HttpServletRequest request) {
    	logger.debug("Received request to create category page");
    try{
    	source.setUserId((Integer)request.getSession().getAttribute("UserSecuritId"));
    	healthtipService.createHealthTipSource(source);
    	 setModelObjects(model, null, null,null);
    	 model.addAttribute("healthtipmsg", "Create HealthTip Source Successfully");
    	 cachingService.clear();
    }catch(MedrepException e){
    	model.addAttribute("healthtipmsg", e.getMessage());
    }
		return VIEW;
	}
    
    @RequestMapping(value = "/removeHealthtip.do", method = RequestMethod.POST)
	public String deleteAdminNotification(Model model,@RequestParam String healthTipId) {
    	try{
    		healthtipService.deleteHealthTip(healthTipId);
    		setModelObjects(model,null, null,null);
    		model.addAttribute("healthtipmsg", "Healthtip Removed Successfully");
    	}catch(MedrepException me){
    		model.addAttribute("notificationmsg", me.getMessage());
    	}
    	return VIEW;
	}


}
