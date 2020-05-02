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

import com.medrep.app.model.TransformCategory;
import com.medrep.app.model.TransformModel;
import com.medrep.app.model.TransformSource;
import com.medrep.app.service.CachingService;
import com.medrep.app.service.TherapeuticAreaService;
import com.medrep.app.service.TransformService;
import com.medrep.app.util.MedrepException;


@Controller
@RequestMapping("/web/admin")
@SessionAttributes( {"tareaList"})
public class TransformController {

	private static final String VIEW ="/web/admin/transform";
	protected static Logger logger = Logger.getLogger("controller");

	@Autowired
	TransformService transformService;
	@Autowired
	CachingService cachingService;

	@Autowired
	TherapeuticAreaService therapeuticAreaService;
	/**
     * Handles and retrieves the admin JSP page that only admins can see
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/transform.do", method = RequestMethod.GET)
    public String getTransformList(Model model) {
    	logger.debug("Received request to show admin page");
    try{
    	 setModelObjects(model, null, null, null);
    	 if(!model.containsAttribute("tareaList")) {
    	     model.addAttribute("tareaList", therapeuticAreaService.getAll());
    	 }
    }catch(MedrepException e){
    	model.addAttribute("transformmsg", e.getMessage());
    }
		return VIEW;
	}

    @RequestMapping(value = "/uploadTransform.do", method = RequestMethod.POST)
    public String uploadTransform(Model model,@ModelAttribute("transformFormObj") TransformModel transform,HttpServletRequest request) {
    	logger.debug("Received request to show admin page");
    try{
    	 List<TransformCategory> list = transformService.getAllTransformCategories();
    	 if(list == null || list.isEmpty()) {
    		 setModelObjects(model, null, null,null);
    		 model.addAttribute("transformmsg", "Please create at least one Category");
    		 return VIEW;
    	 }
    	 List<TransformSource> list1 = transformService.getAllTransformSources();
    	 if(list1 == null || list1.isEmpty()) {
    		 setModelObjects(model, null, null,null);
    		 model.addAttribute("transformmsg", "Please create at least one Source");
    		 return VIEW;
    	 }
    	 transform.setUserId((Integer)request.getSession().getAttribute("UserSecuritId"));
    	 transformService.uploadTransform(transform);
    	 setModelObjects(model, null, null,null);
    	 model.addAttribute("transformmsg", "Upload Transform Successfully");
    	 cachingService.clear();
    }catch(Exception e){
    	model.addAttribute("transformmsg", e.getMessage());
    }
		return VIEW;
	}

    @RequestMapping(value = "/uploadTransformCategory.do", method = RequestMethod.POST)
    public String uploadTransformCategory(Model model,@ModelAttribute("transformCategoryFormObj") TransformCategory category,HttpServletRequest request) {
    	logger.debug("Received request to create category page");
    try{
    	 category.setUserId((Integer)request.getSession().getAttribute("UserSecuritId"));
    	 transformService.createTransformCategory(category);
    	 setModelObjects(model, null, null,null);
    	 model.addAttribute("transformmsg", "Create Transform Category Successfully");
    	 cachingService.clear();
    }catch(MedrepException e){
    	model.addAttribute("transformmsg", e.getMessage());
    }
		return VIEW;
	}

    @RequestMapping(value = "/getTransformModelData.do", method = RequestMethod.GET)
	public @ResponseBody TransformModel getTransformById(@RequestParam String transformId) {
    	return transformService.getTransformById(Integer.valueOf(transformId));
	}


    @RequestMapping(value = "/deleteTransform.do", method = RequestMethod.POST)
   	public String deleteTransform(Model model,@RequestParam String transformId) {
       	try{
       		transformService.deleteTransform(transformId);
       		setModelObjects(model,null,null,null);
       		model.addAttribute("transformmsg", "Transform Removed Successfully");
       		cachingService.clear();
       	}catch(MedrepException me){
       		model.addAttribute("transformmsg", me.getMessage());
       	}
       	return VIEW;
   	}

    private void setModelObjects(Model model, TransformModel transform ,TransformCategory category, TransformSource source)throws MedrepException{
		model.addAttribute("transformList", transformService.getAllTransforms());

		if(transform == null) model.addAttribute("transformFormObj", new TransformModel());
		else model.addAttribute("transformFormObj",transform);
		if(category == null) model.addAttribute("transformCategoryFormObj", new TransformCategory());
		else model.addAttribute("transformCategoryFormObj",category);
		if(source == null) model.addAttribute("transformSourceFormObj", new TransformSource());
		else model.addAttribute("transformSourceFormObj",source);

		//Get all Sources
		model.addAttribute("transformSourcesList", transformService.getAllTransformSources());

		model.addAttribute("transformCategoryList", transformService.getAllTransformCategories());
	}

    @RequestMapping(value = "/uploadTransformSource.do", method = RequestMethod.POST)
    public String uploadTransformSource(Model model,@ModelAttribute("transformCategoryFormObj") TransformSource source,HttpServletRequest request) {
    	logger.debug("Received request to create category page");
    try{
    	source.setUserId((Integer)request.getSession().getAttribute("UserSecuritId"));
    	 transformService.createTransformSource(source);
    	 setModelObjects(model, null, null,null);
    	 model.addAttribute("transformmsg", "Create Transform Source Successfully");
    	 cachingService.clear();
    }catch(MedrepException e){
    	model.addAttribute("transformmsg", e.getMessage());
    }
		return VIEW;
	}


}
