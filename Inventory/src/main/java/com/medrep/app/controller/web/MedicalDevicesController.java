package com.medrep.app.controller.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.model.Company;
import com.medrep.app.model.MedicalDevicesModel;
import com.medrep.app.model.TransformCategory;
import com.medrep.app.model.TransformModel;
import com.medrep.app.model.TransformSource;
import com.medrep.app.service.CompanyService;
import com.medrep.app.service.MedicalDeviceService;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.TherapeuticAreaService;
import com.medrep.app.util.MedrepException;

@Controller
@RequestMapping("/web/admin")
@SessionAttributes({"tareaList","companyMap"})

public class MedicalDevicesController {

	
	private static String view="/web/admin/medicalDevice";
	protected static Logger logger=Logger.getLogger("controller");

	@Autowired
	MedicalDeviceService  medicalDeviceService;
	
	@Autowired
	TherapeuticAreaService therapeuticAreaService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	NotificationService notificationService;

	
	
	@RequestMapping(value="/devices.do", method=RequestMethod.GET)
	public String getMedicalDevices(Model model) 
	{
		logger.debug(getClass().getName()+" Medical Devices");
		try{
			setModelObjects(model,null);
		if(!model.containsAttribute("tareaList")) 
    	     model.addAttribute("tareaList", therapeuticAreaService.getAll());
    	 		
		if(!model.containsAttribute("companyMap")) 
   	     model.addAttribute("companyMap", notificationService.getAllCompanys());
   	 		}
		catch(MedrepException e)
		{
			model.addAttribute("deviceMsg", e.getMessage());
		}
		
		return view;
		
	}

	private void setModelObjects(Model model, MedicalDevicesModel devices) throws MedrepException{
		// TODO Auto-generated method stub
		model.addAttribute("deviceformlist",medicalDeviceService.getAllDevices());
		model.addAttribute("orderformlist",medicalDeviceService.getAllorders());
		if(devices==null) 
			model.addAttribute("deviceFormObj" , new MedicalDevicesModel());
		else 
			model.addAttribute("deviceFormObj",devices);
			
		
	}
	
	
	 public MedicalDevicesModel getMedicalDevice(String Id){
		 MedicalDevicesModel medicalDevice= new MedicalDevicesModel();
	    	if(Id!=null)
	    		medicalDevice = medicalDeviceService.fetchMedicalDeviceById(Id);
	        return medicalDevice;        
	    }
	    
	    @RequestMapping(value = "/getDevices.do", method = RequestMethod.GET)
		public @ResponseBody MedicalDevicesModel getDeviceById(@RequestParam String deviceId) {
	    	return getMedicalDevice(deviceId);
	    }
	    

@RequestMapping(value = "/medicalDeviceUpdate.do", method = RequestMethod.POST)
public ModelAndView medicalDeviceUpdate( @RequestParam("device_name") String device_name,
								 @RequestPart("deviceLogo") MultipartFile deviceLogo,
								 @RequestParam("device_desc") String device_desc,
								
								 @RequestParam("device_unit") String device_unit,
								 @RequestParam("device_price") String device_price,
								 @RequestParam("features") String features,
								 @RequestParam("companyLogo") MultipartFile companyLogo,
								 @RequestParam("therapeuticId") String therapeuticId,
								 @RequestParam("companyId") String companyId,
								/* @RequestParam("device_id") String device_id,*/
								 @RequestParam("buttonName") String buttonName,Model model){
	 ModelAndView mnv = new ModelAndView();
	try{
	
	model.addAttribute("companyLogo", companyLogo);
	model.addAttribute("deviceLogo", deviceLogo);
	MedicalDevicesModel medicalDevicesModel = new MedicalDevicesModel();
	/*Location location = new Location();
	DisplayPicture displayPicture = new DisplayPicture();
	Set<TherapeuticArea> therapeuticAreas = new HashSet<TherapeuticArea>();*/
	medicalDevicesModel.setDevice_name(device_name);
	medicalDevicesModel.setDevice_desc(device_desc);
	medicalDevicesModel.setDevice_price(Integer.parseInt(device_price));
	medicalDevicesModel.setDevice_unit(Integer.parseInt(device_unit));
	medicalDevicesModel.setDeviceLogo(deviceLogo);
	medicalDevicesModel.setCompanyId(companyId);
	medicalDevicesModel.setTherapeuticId(therapeuticId);
	medicalDevicesModel.setCompanyLogo(companyLogo);
	medicalDevicesModel.setFeatures(features);
	medicalDevicesModel.setStatus("Active");

	if(buttonName.equals("Create"))
	{
		model.addAttribute("medicaldevice",medicalDevicesModel);
		medicalDeviceService.createMedicalDevice(model);
	}
	else if(buttonName.equals("Update"))
	{
	/*	medicalDevicesModel.setDevice_id(Integer.parseInt(device_id));*/
		model.addAttribute("company",medicalDevicesModel);
		medicalDeviceService.updateMedicalDevice(model);
	}
	   
	    logger.debug("Received request to show admin page");        
	logger.debug("Received request to show admin page");
	}catch(Exception e){
		System.out.println(e);
	}
   	 mnv.addObject("deviceformlist",getMedicalDeviceList()); 
   	  	 mnv.setViewName("redirect:/web/admin/devices.do");    	 
	 return mnv;
}

public List<MedicalDevicesModel> getMedicalDeviceList(){
	List<MedicalDevicesModel> medicaldevices;
	medicaldevices = medicalDeviceService.getAllDevices();
    return medicaldevices;        
}


@RequestMapping(value = "/devices.do", method = RequestMethod.POST)
public ModelAndView pharmaRemove(@RequestParam("srado") String deviceId,@RequestParam("buttonName") String buttonName){
	System.out.println("Company id fetched"+deviceId);
	    ModelAndView mnv = new ModelAndView();
	    String view = null;
	if(buttonName.equals("Remove"))
	{
		
		medicalDeviceService.alterDeviceStatusByDeviceId(Integer.parseInt(deviceId), "Deleted");
		view = "/web/admin/medicalDevice";
	}
	else if(buttonName.equals("UpdateToTab"))
	{
		mnv.addObject("devicesObj",getMedicalDevice(deviceId));
		
		mnv.addObject("deviceId", deviceId);
		view = "/web/admin/medicalDevice";
	}
	logger.debug("Received request to show admin page");        
	logger.debug("Received request to show admin page");
	//model.addAttribute("deviceformlist",medicalDeviceService.getAllDevices());
   	 mnv.addObject("deviceformlist",getMedicalDeviceList()); 
   //	 mnv.addObject("therapeuticAreaList",getTherapeuticAreaList());
  	 mnv.setViewName(view);    	 
	 return mnv;
}
}
