package com.medrep.app.controller.web;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.model.Doctor;
import com.medrep.app.model.Notification;
import com.medrep.app.model.PharmaCampStats;
import com.medrep.app.model.PharmaCampaignModel;
import com.medrep.app.model.Survey;
import com.medrep.app.service.DoctorService;
import com.medrep.app.service.MasterDataService;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.PharmaCampaignService;
import com.medrep.app.service.TherapeuticAreaService;
import com.medrep.app.util.MedrepException;

@Controller
@RequestMapping("/web/admin")
public class PharmaCampaignWebController {
	
	private static final String VIEW ="/web/admin/pharmacampaigns";
	protected static Logger logger=Logger.getLogger("controller");

	@Autowired
	PharmaCampaignService  pharmaCampaginService;
	@Autowired
	NotificationService  notificationService;
	@Autowired
	TherapeuticAreaService  therapeuticAreaService;
	
	@Autowired
	MasterDataService  masterdataService;
	
	@Autowired
	DoctorService  doctorService;
	
	
	
	@RequestMapping(value = "/pharmaCampagin.do", method = RequestMethod.GET)
    public String getAdminNotificationPage(Model model) {
    	logger.debug("Received request to show admin page");
    try{
    	 setModelObjects(model, null);
       	 model.addAttribute("companyMap", notificationService.getAllCompanys());
    }catch(MedrepException e){
    	model.addAttribute("pharmacampaignmsg", e.getMessage());
    }
		return VIEW;
	}
	
	private void setModelObjects(Model model, PharmaCampaignModel pharmaCampaginModel)throws MedrepException{
		model.addAttribute("pharmaCampaignlists", pharmaCampaginService.getAdminCampaginList());
		/*if(pharmaCampaginModel == null) model.addAttribute("pharmaCampgainFormObj", new Notification());
		else model.addAttribute("pharmaCampgainFormObj",pharmaCampaginModel);*/
		model.addAttribute("tareaList",therapeuticAreaService.getAll());
		model.addAttribute("statesall", masterdataService.getAllState());
		model.addAttribute("publishTareaIds",new String());
		model.addAttribute("publishDocsIds","null");
		/*model.addAttribute("stateSelectedId1",new String());
		model.addAttribute("stateDistrictId1","null");*/
	}
	
	@RequestMapping(value = "/getAllDistrict.do", method = RequestMethod.GET)
	public @ResponseBody List<String> getAllDistrictByState(@RequestParam String stateId) {
		
		return masterdataService.getAllDistrictByState(stateId);
	}
	
	@RequestMapping(value = "/getAllSubDistrict.do", method = RequestMethod.GET)
	public @ResponseBody List<String> getAllSubDistrictByDistrict(@RequestParam String stateId,@RequestParam String districtId) {
		
		return  masterdataService.getAllSubDistrictByDistrict(stateId,districtId);
	}
	
	@RequestMapping(value = "/createPharmaCampaign.do", method = RequestMethod.POST)
    public ModelAndView createNotification(@RequestParam("campaignTitle") String campaignTitle,
			    						@RequestPart("campgainLogo") MultipartFile campgainLogo,
			    						@RequestPart("campgainImage") MultipartFile campgainImage,
										 @RequestParam("companyId") Integer companyId,
										 @RequestParam("startDate") Date startDate,
			    						@RequestParam("therapeuticId") String therapeuticId,
			    						@RequestParam("endDate") Date endDate,
			    						@RequestParam("subdistrictSelectedId") String subdistrictSelectedId,
			    						@RequestParam("districtSelectedId") String districtSelectedId,
			    						@RequestParam("stateSelectedId") String stateSelectedId,
    								 	@RequestParam("contactNumber") String contactNumber,
    								 	@RequestParam("adddressLine") String adddressLine,
			 @RequestParam("companyemailId") String companyemailId, @RequestParam("contactName") String contactName,
			 @RequestParam("campgainDescription") String campgainDescription,@RequestParam("contactEmail") String contactEmail,
			 @RequestParam("price") String price,@RequestParam(required=false)  String requestSample,
			 Model model) throws MedrepException {
    	logger.debug("Received request to show admin page");
		 ModelAndView mnv = new ModelAndView();
    	try{
    		model.addAttribute("campgainLogo", campgainLogo);
    		model.addAttribute("campgainImage", campgainImage);
    		PharmaCampaignModel pharmaCampaignModel=new PharmaCampaignModel();
    		pharmaCampaignModel.setCampaignDetails(campgainDescription);
    		pharmaCampaignModel.setCampaignTitle(campaignTitle);
    		pharmaCampaignModel.setCompanyId(companyId);
    		pharmaCampaignModel.setCampaignStartDateFormat(startDate);
    		pharmaCampaignModel.setCampaignEndDateFormat(endDate);
    		pharmaCampaignModel.setTherapeuticId(therapeuticId);
    		pharmaCampaignModel.setTargetState(stateSelectedId);
    		pharmaCampaignModel.setTargetCity(districtSelectedId);
    		pharmaCampaignModel.setTargetArea(subdistrictSelectedId);
    		pharmaCampaignModel.setAddress(adddressLine);
    		pharmaCampaignModel.setCompanyEmail(companyemailId);
    		pharmaCampaignModel.setContactPersonName(contactName);
    		pharmaCampaignModel.setContactPersonPhone(contactNumber);
    		pharmaCampaignModel.setContactPersonEmail(contactEmail);
    		pharmaCampaignModel.setPrice(price);
    		if (requestSample!= null){
    			pharmaCampaignModel.setRequestSample("Y");
    		}else {
    			pharmaCampaignModel.setRequestSample("N");
    		}
			model.addAttribute("pharmaCampaignModel",pharmaCampaignModel);
    		pharmaCampaginService.createPharmacampaign(model);
    		model.addAttribute("pharmacampaignmsg", "Campaign Created Successfully");
    
    		}catch(MedrepException me){
    		model.addAttribute("pharmacampaignmsg", me.getMessage());
    	}

		/*Thread thread = null;
		try {
			Runnable runNotification = new Runnable() {
				@Override
				public void run() {
					notificationService.pushNotification();
				}
			};
			thread = new Thread(runNotification);
			thread.start();
		} catch (Exception e) {
			if(thread != null){
				thread.stop();
			}
			e.printStackTrace();
		}*/

    	mnv.addObject("pharmaCampaignlists", pharmaCampaginService.getAdminCampaginList());
  	  	 mnv.setViewName("redirect:/web/admin/pharmaCampagin.do");    	 
  	  	 return mnv;
	}
	
	
	@RequestMapping(value = "/publishCampaign.do", method = RequestMethod.POST)
    public ModelAndView publishCampaign(
    									@RequestParam("campaignId") String campaignId,
			    						@RequestParam(required=false) String therapeuticId,
			    						@RequestParam("pdistrictSelectedId") String city,
			    						@RequestParam("stateSelectedId1") String stateSelectedId,
			    						@RequestParam("publishDocsIds") String[] publishDocsIds,			    						
			    						Model model) throws MedrepException {
			    						
			logger.debug("Received request to show admin page");
			ModelAndView mnv = new ModelAndView();
			try{
				PharmaCampStats pharmaCampStats=new PharmaCampStats();
				pharmaCampStats.setCampaignID(campaignId);
				pharmaCampStats.setThrepeauticId(therapeuticId);
				pharmaCampStats.setCity(city);
				
				pharmaCampaginService.publishCampaign(pharmaCampStats);
	    		model.addAttribute("pharmacampaignmsg", "Campaign Published Successfully");

			}catch(Exception me){
				model.addAttribute("pharmacampaignmsg", me.getMessage());
			}
	    	mnv.addObject("pharmaCampaignlists", pharmaCampaginService.getAdminCampaginList());
	    	mnv.setViewName("redirect:/web/admin/pharmaCampagin.do");    	 
	    	return mnv;
	}
	
	@RequestMapping(value = "/getDocsByTAs.do", method = {RequestMethod.POST,RequestMethod.GET})
	public String getDocotorsByTAreas(Model model,@RequestParam String tareaIds) {
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
    		model.addAttribute("publishTareaIds",new String());
    		model.addAttribute("publishDocsIds","null");
    		
/*
    		setModelObjects(model,null);
    		Notification notification=(Notification) model.asMap().get("notificationFormObj");
    		notification.setPublishTareaIds(tareaIds.split(","));
    		model.addAttribute("publish", "true");*/
    	}catch(Exception me){
    		model.addAttribute("pharmacampaignmsg", me.getMessage());
    	}

    	return VIEW;
	}
	
	@RequestMapping(value = "/getDocsByTAass.do", method = RequestMethod.GET)
	public @ResponseBody String getDoctorByTherapeutics(@RequestParam String tareaIds,Model model) {
		StringBuilder buffer =new StringBuilder();
		//buffer.append("<option value=0> ----------   Select One  -------- </option>");
		Map<String,String> tmap = new HashMap();
		List<Doctor> list = new ArrayList<Doctor>();
		if(tareaIds!=null && tareaIds.contains(",")){
			String tarea[]=tareaIds.split(",");
			for(int i=0;i<tarea.length;i++){
		//	for(String tarea : tareaIds.split(",")) {
				List<Doctor> docList = doctorService.findByTAreaId(tarea[i]);
				list.addAll(docList);
				if(null!=docList && !docList.isEmpty())
				{
					for(int j=0;j<docList.size();j++)
				tmap.put(docList.get(j).getDoctorId().toString(), docList.get(j).getDisplayName());
				}
				//list.addAll(docList);
			}
		}else if(tareaIds!=null) {
			list= doctorService.findByTAreaId(tareaIds);
			if(null!=list & list.size() > 0)
				tmap.put(list.get(0).getDoctorId().toString(),list.get(0).getDisplayName());
		}
		model.addAttribute("docsList",list);
		model.addAttribute("publishTareaIds",new String());
		model.addAttribute("publishDocsIds","null");
		for(String key : tmap.keySet()){
			buffer.append("<option value="+key +">" +tmap.get(key) +"</option>");
		}
		return buffer.toString();
	}
	@RequestMapping(value = "/getMulDistrictByStates.do", method = RequestMethod.GET)
	public @ResponseBody String getMulDistrictByStates(@RequestParam String stateId,Model model) {
		StringBuilder buffer =new StringBuilder();
		List<String> list = new ArrayList<String>();
		if(stateId!=null && stateId.contains(",")){
			String states[]=stateId.split(",");
			for(int i=0;i<states.length;i++){
				List<String> distList = masterdataService.getAllDistrictByState(states[i]);
				if(null!=distList && !distList.isEmpty())
				{
					list.addAll(distList);
				}
				//list.addAll(docList);
			}
		}else if(stateId!=null) {
			list= masterdataService.getAllDistrictByState(stateId);
		}
	/*	model.addAttribute("docsList",list);
		model.addAttribute("publishTareaIds",new String());
		model.addAttribute("publishDocsIds","null");*/
		for(String key : list){
			buffer.append("<option value="+key +">" +key +"</option>");
		}
		return buffer.toString();
	}
	
}