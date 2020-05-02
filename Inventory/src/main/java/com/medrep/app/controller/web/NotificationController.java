package com.medrep.app.controller.web;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.model.Company;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorNotification;
import com.medrep.app.model.Notification;
import com.medrep.app.model.NotificationDetails;
import com.medrep.app.model.Survey;
import com.medrep.app.service.CompanyService;
import com.medrep.app.service.DoctorService;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.SurveyService;
import com.medrep.app.service.TherapeuticAreaService;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.Util;


@Controller
@RequestMapping("/web/admin")
@SessionAttributes( { "companyMap"})
public class NotificationController {

	private static final String VIEW ="/web/admin/notifications";
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
	TherapeuticAreaService therapeuticAreaService;
	/**
     * Handles and retrieves the admin JSP page that only admins can see
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/notifications.do", method = RequestMethod.GET)
    public String getAdminNotificationPage(Model model) {
    	logger.debug("Received request to show admin page");
    try{
    	 setModelObjects(model, null);
       	 model.addAttribute("companyMap", notificationService.getAllCompanys());
    }catch(MedrepException e){
    	model.addAttribute("notificationmsg", e.getMessage());
    }
		return VIEW;
	}

    @RequestMapping(value = "/createNotification.do", method = RequestMethod.POST)
    public String createNotification(Model model, @ModelAttribute("notificationFormObj") Notification notification) {
    	logger.debug("Received request to show admin page");
    	try{
    		notificationService.createNotification(notification);
    		setModelObjects(model, notification);
    		model.addAttribute("notificationmsg", "Notification Created Successfully");
    	}catch(MedrepException me){
    		model.addAttribute("notificationmsg", me.getMessage());
    	}

		Thread thread = null;
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
		}


    	return VIEW;
	}

    @RequestMapping(value = "/updateNotification.do", method = RequestMethod.POST)
	public String updateAdminNotification(Model model,@ModelAttribute("notificationFormObj") Notification notification) {
		try
		{
			notificationService.updateAdminNotificaion(notification);
			setModelObjects(model, notification);
			model.addAttribute("notificationmsg", "Notification Updated Successfully");

		}catch(MedrepException me){
    		model.addAttribute("notificationmsg", me.getMessage());
    	}

		Thread thread = null;
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
		}
		return VIEW;
	}

    @RequestMapping(value = "/getNotificationContent.do/{detailNotificationId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public void getNotificationContent(@PathVariable("detailNotificationId") Integer detailNotificationId, HttpServletResponse response){

		try
		{

			 NotificationDetails notificationDetails = notificationService.getNotificationDetails(detailNotificationId);
			 if(notificationDetails != null)
			 {

				 String mimeType = "application/octet-stream";
				 byte[] content = FileUtil.getBinaryData(notificationDetails.getContentLocation());
				 if(content!=null){
				 response.setContentType(mimeType);
				 response.setContentLength(content.length);
				 String headerKey = "Content-Disposition";
		         String headerValue = String.format("attachment; filename=\"%s\"",notificationDetails.getContentName());
		         response.setHeader(headerKey, headerValue);
		         OutputStream outStream = response.getOutputStream();
		         outStream.write(content);
		         outStream.close();
				 }
			 }

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

    @RequestMapping(value = "/removeNotification.do", method = RequestMethod.POST)
	public String deleteAdminNotification(Model model,@RequestParam String notificationDelId) {
    	try{
    		notificationService.deleteAdminNotification(notificationDelId);
    		setModelObjects(model,null);
    		model.addAttribute("notificationmsg", "Notification Removed Successfully");
    	}catch(MedrepException me){
    		model.addAttribute("notificationmsg", me.getMessage());
    	}
    	return VIEW;
	}


	@RequestMapping(value = "/getNotificationModelData.do", method = RequestMethod.GET)
	public @ResponseBody Notification getAdminNotificationById(@RequestParam String notificationId) {
		try{
		Notification notification = notificationService.getAdminNotificationById(notificationId);
		notification.setTherapeuticDropDownValues(this.getTherapeuticsByCompanyId(String.valueOf(notification.getCompanyId())));
		return notification;
		}catch(Exception e){
			e.printStackTrace();
		}
		return new Notification();
	}

	@RequestMapping(value = "/getAllTherapeutics.do", method = RequestMethod.GET)
	public @ResponseBody String getTherapeuticsByCompanyId(@RequestParam String companyId) {
		StringBuilder buffer =new StringBuilder();
		buffer.append("<option value=0> ----------   Select One  -------- </option>");
		Map<String,String> tmap = notificationService.getAllTherapeuticsByCompanyId(companyId);
		for(String key : tmap.keySet()){
			buffer.append("<option value="+key +">" +tmap.get(key) +"</option>");
		}
		return buffer.toString();
	}

	private void setModelObjects(Model model, Notification notification)throws MedrepException{
		model.addAttribute("notificationList", notificationService.getAdminNotificationsList());
		if(notification == null) model.addAttribute("notificationFormObj", new Notification());
		else model.addAttribute("notificationFormObj",notification);
		model.addAttribute("therapeuticsAreaList",therapeuticAreaService.getAll());
	}

	@RequestMapping(value = "/publishNotification.do", method = {RequestMethod.POST})
	public String publishNotifications(Model model,@ModelAttribute("notificationFormObj") Notification notification) {
		try{
			ArrayList<DeviceTokenEntity> devices=notificationService.publishNotifications(notification);
			setModelObjects(model, notification);
			model.addAttribute("notificationmsg","Notification published succesfully");
			Company company=companyService.getCompanyDetail(notification.getCompanyId());
			String message="";
			if(!Util.isEmpty(company))
				 message+= company.getCompanyName();
				message+=" have a new product. Please click to know more.";
				logger.info(">>"+message+"::"+(Util.isEmpty(company)?"":company.getCompanyName()));
			if(devices!=null){
				notificationService.pushMessage(message, devices);
			}
		}catch(Exception e){
			model.addAttribute("notificationmsg",e.getMessage());
			e.printStackTrace();
		}
		return VIEW;
	}
	 void setModelObjects(Model model)throws MedrepException{
		model.addAttribute("surveyList", surveyService.getAdminSurveysList());
		model.addAttribute("therapeuticsAreaList",therapeuticAreaService.getAll());

	}

	@RequestMapping(value = "/getDocsByTAreas.do", method = {RequestMethod.POST,RequestMethod.GET})
		public String getDocotorsByTAreas(Model model,@RequestParam String tareaIds,@RequestParam(required=false) String referrer) {
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

	    		if("surveys".equalsIgnoreCase(referrer)){
	    			Survey survey=new Survey();
	    			if(tareaIds!=null)
	    			survey.setPublishTareaIds(tareaIds.split(","));
	    			model.addAttribute("surveyFormObj", survey);


		    		setModelObjects(model);
		    		model.addAttribute("publish", "true");
		    		return "/web/admin/surveys";
		    	}

	    		setModelObjects(model,null);
	    		Notification notification=(Notification) model.asMap().get("notificationFormObj");
	    		notification.setPublishTareaIds(tareaIds.split(","));
	    		model.addAttribute("publish", "true");
	    	}catch(Exception me){
	    		model.addAttribute("notificationmsg", me.getMessage());
	    	}

	    	return VIEW;
		}
	
	@RequestMapping(value = "/getDocsByTAreass.do", method = RequestMethod.GET)
	public @ResponseBody String getDoctorByTherapeutics(@RequestParam String tareaIds,Model model) {
		StringBuilder buffer =new StringBuilder();
		buffer.append("<option value=0> ----------   Select One  -------- </option>");
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
			if(null!=list)
			tmap.put(list.get(0).getDoctorId().toString(),list.get(0).getDisplayName());
		}
		model.addAttribute("docsList",list);
		//Map<String,String> tmap = notificationService.getAllTherapeuticsByCompanyId(companyId);
		for(String key : tmap.keySet()){
			buffer.append("<option value="+key +">" +tmap.get(key) +"</option>");
		}
		return buffer.toString();
	}

	@RequestMapping(value = "/Report", method = RequestMethod.POST)
	public ModelAndView downloadNotificationStatistics(@RequestParam String notificationStatId)throws Exception{
		Map<String,List<DoctorNotification>> reportDataMap = notificationService.getReport(Integer.parseInt(notificationStatId));
		return new ModelAndView("NotificationStatistics", "notificationstatistics", reportDataMap);

	}
}
