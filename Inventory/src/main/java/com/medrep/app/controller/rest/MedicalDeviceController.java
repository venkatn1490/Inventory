package com.medrep.app.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medrep.app.model.AppResponse;
import com.medrep.app.model.Company;
import com.medrep.app.model.MedicalDevicesModel;
import com.medrep.app.model.OrderMedicalDevice;
import com.medrep.app.model.OrderMedicalDevice_res;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.MedicalDeviceService;

@Controller
@RequestMapping("/api/meddevice")
public class MedicalDeviceController {
	
@Autowired
MedicalDeviceService medicalDeviceService;

@RequestMapping(method=RequestMethod.GET,value="/getCompanies")	
public @ResponseBody List<Company> fetchAllCompanies()
{
	List<Company> Companies=new ArrayList<Company>();
	Company company=new Company();
	 UserSecurityContext context = null;
     if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
     {
     	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     	if(context != null)
     	{
     		try
     		{

     			String  loginId = context.getUsername();
     			//appointments = appointmentService.getAppointmentForRep(loginId,DateConvertor.convertStringToDate(startDate, DateConvertor.YYYYMMDD));
     			Companies=medicalDeviceService.fetchallCompanies(loginId);
     		}
     		catch(Exception e)
     		{
     			e.printStackTrace();
     		}
     	}


     }
	
	
	return Companies;
}

@RequestMapping(method=RequestMethod.GET,value="/getAllMedicalDeviceswithCompany")	
public @ResponseBody List<MedicalDevicesModel> fetchallMedicalDevicesWithCompany()
{
	List<MedicalDevicesModel> medicalDevicesModels=new ArrayList<MedicalDevicesModel>();
	//Company company=new Company();
	 UserSecurityContext context = null;
     if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
     {
     	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     	if(context != null)
     	{
     		try
     		{

     			String  loginId = context.getUsername();
     			//appointments = appointmentService.getAppointmentForRep(loginId,DateConvertor.convertStringToDate(startDate, DateConvertor.YYYYMMDD));
     			medicalDevicesModels=medicalDeviceService.fetchallMedicalDevicesWithCompany(loginId);
     		}
     		catch(Exception e)
     		{
     			e.printStackTrace();
     		}
     	}


     }
	
	
	return medicalDevicesModels;
}

@RequestMapping(method=RequestMethod.GET,value="/getMedicalDevicesByCompanyId/{companyId}/{therapeutic_id}")	
public @ResponseBody List<MedicalDevicesModel> fetchMedicalDevicesByCompany(@PathVariable("companyId") String companyId,@PathVariable("therapeutic_id") String therapeutic_id)
{
	List<MedicalDevicesModel> medicalDevicesModels=new ArrayList<MedicalDevicesModel>();
	//Company company=new Company();
	 UserSecurityContext context = null;
     if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
     {
     	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     	if(context != null)
     	{
     		try
     		{

     			String  loginId = context.getUsername();
     			
     			//appointments = appointmentService.getAppointmentForRep(loginId,DateConvertor.convertStringToDate(startDate, DateConvertor.YYYYMMDD));
     			medicalDevicesModels=medicalDeviceService.fetchMedicalDevicesByCompanyId(companyId,therapeutic_id);
     		}
     		catch(Exception e)
     		{
     			e.printStackTrace();
     		}
     	}


     }
	
	
	return medicalDevicesModels;
}

@RequestMapping(method=RequestMethod.GET,value="/getMyOrderMedicalDevices")	
public @ResponseBody List<OrderMedicalDevice_res> getMyOrderMedicalDevices()
{
	List<OrderMedicalDevice_res> ordermedicalDevicesModels=new ArrayList<OrderMedicalDevice_res>();
	//Company company=new Company();
	 UserSecurityContext context = null;
     if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
     {
     	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     	if(context != null)
     	{
     		try
     		{

     			String  loginId = context.getUsername();
     			//appointments = appointmentService.getAppointmentForRep(loginId,DateConvertor.convertStringToDate(startDate, DateConvertor.YYYYMMDD));
     			ordermedicalDevicesModels=medicalDeviceService.orderMedicalDevicesByDoctorId((loginId));
     		}
     		catch(Exception e)
     		{
     			e.printStackTrace();
     		}
     	}


     }
	
	
	return ordermedicalDevicesModels;
}

@RequestMapping(method = RequestMethod.GET, value = "/orderMedicalsDevice/{companyId}/{therapeutic_id}/{device_id}")
public @ResponseBody AppResponse orderMedicalsDevice(@PathVariable("companyId") String companyId,@PathVariable("therapeutic_id") String therapeutic_id,@PathVariable("device_id") String device_id) {
 //   logger.info("Request received for login");
	
    AppResponse response = new AppResponse();
    UserSecurityContext context = null;
    if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
    {
    	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if(context != null)
    	{
    		try
    		{
    			String  loginId = context.getUsername();
    /*			model.addAttribute("loginId", Integer.toString(loginId));
    */			
    			/*model.addAttribute("ordermedicaldevice",ordermedicaldevice);*/
    			
            	response.setMessage("Success");
                response.setStatus("OK");



    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
	        	response.setMessage("Error occurred while updating doctor.");
	            response.setStatus("Error");
    		}
    	}
    }

    return response;
}



@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
public @ResponseBody AppResponse createOrder(@RequestBody OrderMedicalDevice ordermedicaldevice,Model model) {
   // logger.info("Request received for creating appointment");
    AppResponse response = new AppResponse();
    UserSecurityContext context = null;
    model.addAttribute("ordermedicaldevice", ordermedicaldevice);
    if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
    {
    	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if(context != null)
    	{
	        try
	        {
	        	String  loginId = context.getUsername();
	        	model.addAttribute("loginId",loginId);
	        	medicalDeviceService.createOrder(model);
	        	response.setMessage("Your enquiry has been successfully submitted, you will get a response from us shortly.");
	            response.setStatus("True");
	        }
	        catch(Exception e)
	        {
	        //	logger.error("exception");
	        	response.setMessage(e.getMessage());
	            response.setStatus("Error");
	            e.printStackTrace();
	        }
    	}
    }
   // logger.info("finished processing");
    return response;
}

}
