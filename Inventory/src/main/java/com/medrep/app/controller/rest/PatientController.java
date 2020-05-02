package com.medrep.app.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.medrep.app.model.AppResponse;
import com.medrep.app.model.HealthTipCategory;
import com.medrep.app.model.Location;
import com.medrep.app.model.PatDocAppointModel;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.CachingService;
import com.medrep.app.service.FindNearByService;
import com.medrep.app.service.HealthTipService;
import com.medrep.app.service.OTPService;
import com.medrep.app.service.PatientAppointmentService;
import com.medrep.app.service.PatientService;
import com.medrep.app.util.Util;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
	
	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	private OTPService otpService;
	@Autowired
	CachingService cachingService;
	@Autowired
	HealthTipService healthtipService;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	PatientAppointmentService patientAppointmentService;

	@Autowired
	FindNearByService findNearByService;
	public static final String GET_HEALTH = "/getHealthtip/{categoryId}";
	public static final String GET_HEALTH_CATEGORY = "/getHealthtipCategory";
	public static final String GET_PATIENT_APPOINTMENT = "/getPatientAppoinments";
	public static final String POST_APPOINTMENT = "/postAppointment";

	/**
	 * API for fetch News
	 *
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = PatientController.GET_HEALTH, method = RequestMethod.GET)
	public @ResponseBody Object getHealthtip(@PathVariable("categoryId")  Integer categoryId,@RequestParam(required=false) Long timestamp,Model model) {
		logger.info("Request received for getContactsAndGroups");
		List response = new ArrayList();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					if(categoryId!=null ){
									String categoryName = healthtipService.getNameByHealthTipCategory((categoryId));
									Map result=(Map) cachingService.get(categoryName);
									if(Util.isEmpty(result)){
										result=new HashMap();
										result.put("healthtip",healthtipService.getAllHealthTipById((categoryId), categoryName,timestamp));
										cachingService.put(categoryName, result);
									}

								return result;
					}
		}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	@RequestMapping(value =PatientController.GET_HEALTH_CATEGORY, method = RequestMethod.GET)
    public @ResponseBody  List<HealthTipCategory> getCompanyDetails() {
        logger.info("Request received for getContactsAndGroups");
        List<HealthTipCategory> listofCatgory=null;
        try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					
					listofCatgory=healthtipService.getAllHealthTipCategories();
				}
			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

				
		return listofCatgory;

    }
	
	@RequestMapping(value="/address/update",method=RequestMethod.POST)
	public AppResponse changeAddress(@RequestBody List<Location>  addresses,Authentication activeUser,Model model) {
		AppResponse response = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			model.addAttribute("addresses", addresses);
			patientService.changeAddress(model);
			response.setStatus("success");
		} catch (Exception e) {
			response.setStatus("error");
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value="/address/delete",method=RequestMethod.POST)
	public AppResponse deleteAddress(@RequestBody Location  address,Authentication activeUser,Model model) {
		AppResponse response = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			Set<Integer> locationIds= new HashSet<Integer>();

			if(address.getLocationId()!=null)
			locationIds.add(address.getLocationId());
			if(address.getLocationIds()!=null && !address.getLocationIds().isEmpty())
			locationIds.addAll(address.getLocationIds());
			model.addAttribute("locationids",locationIds);
			patientService.deleteAddress(model);
			response.setStatus("success");
		} catch (Exception e) {
			response.setStatus("error");
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value =PatientController.GET_PATIENT_APPOINTMENT, method = RequestMethod.GET)
    public @ResponseBody AppResponse getMyDoctorAppoinment(@RequestParam String type,Model model) {
        logger.info("Request received for get my appoinments");
		AppResponse response = new AppResponse();
        try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
					model.addAttribute("communicationMode", type);
					patientAppointmentService.getDoctorAppointmentForPatient(model);
					response.setStatus("success");
					response.setResult(model.asMap().get("patientAppointmentInfo"));			
				}
			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

				
		return response;

    }
	@RequestMapping(value =  PatientController.POST_APPOINTMENT, method = RequestMethod.POST)
	public @ResponseBody AppResponse postAppoinment(@RequestBody PatDocAppointModel patDocAppointModel, Model model) {
		logger.info("Request received for update Notfication");
		AppResponse response = new AppResponse();
		model.addAttribute("patDocAppointModel", patDocAppointModel);
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
					
					findNearByService.postAppointmentPatDoc(model);
					response.setStatus("success");

					if(model.asMap().get("id") != null) {
						response.setId(model.asMap().get("id"));
						response.setStatus("Success");
						response.setMessage("Your Appointment has been completed successfully.");
					}else {
						response.setStatus("Error");
						response.setMessage("You Already Registered for this Slot.");	
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while retrieving About ");
			response.setStatus("Error");

		}

		return response;
	}
}
