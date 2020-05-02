package com.medrep.app.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medrep.app.entity.NotificationTypeEntity;
import com.medrep.app.model.AppResponse;
import com.medrep.app.model.Company;
import com.medrep.app.model.NotificationType;
import com.medrep.app.model.PharmaRep;
import com.medrep.app.model.Role;
import com.medrep.app.model.TherapeuticArea;
import com.medrep.app.service.DoctorService;
import com.medrep.app.service.MasterDataService;
import com.medrep.app.service.MedRepService;
import com.mysql.fabric.xmlrpc.base.Array;

@Controller
@RequestMapping("/preapi/masterdata")
public class MasterDataController {
	public static final String ROLES = "/getRoles";
	public static final String NOTIFICATION_TYPE = "/getNotificationTypes";
	public static final String THERAPEUTIC_AREA = "/getTherapeuticAreaDetails";
	public static final String COMPANY_DETAIL = "/getCompanyDetails";
	public static final String STATESLIST_DETAIL = "/getAllStates";
	public static final String DISTRICTLIST_DETAIL = "/getAllDistrictByState";
	public static final String SUBDISTRICTLIST_DETAIL = "/getAllSubDistrictByDistrict";
	public static final String MANAGER_DETAIL = "/getManagerDetails/{companyId}";
	
	private static final Logger logger = LoggerFactory.getLogger(MasterDataController.class);
	
	@Autowired
	private MasterDataService masterDataService;
	
	@Autowired
	private MedRepService medRepService;
	
	
	@RequestMapping(value = MasterDataController.ROLES, method = RequestMethod.GET)
    public @ResponseBody List<Role> getRoles() {
        logger.info("Request received for Get My Profile");        
        return masterDataService.getAllRoles();
    }
	
	@RequestMapping(value = MasterDataController.NOTIFICATION_TYPE, method = RequestMethod.GET)
    public @ResponseBody List<NotificationType> getNotificationTypes() {
        logger.info("Request received for Get My Profile");              
        return masterDataService.getAllNotificationType();
    }
	
	
	@RequestMapping(value = MasterDataController.THERAPEUTIC_AREA, method = RequestMethod.GET)
    public @ResponseBody List<TherapeuticArea> getTherapeuticAreaDetails() {
        logger.info("Request received for TherapeuticArea");
        return masterDataService.getAllTherapeuticArea();
    }
	
	@RequestMapping(value = MasterDataController.COMPANY_DETAIL, method = RequestMethod.GET)
    public @ResponseBody List<Company> getCompanyDetails() {
        logger.info("Request received for Company Details");               
        return masterDataService.getAllCompanyDetails();
    }
	
	@RequestMapping(value = MasterDataController.STATESLIST_DETAIL, method = RequestMethod.GET)
    public @ResponseBody List<String> getAllStates() {
        logger.info("Request received for Company Details");               
        return masterDataService.getAllState();
    }
	@RequestMapping(value =MasterDataController.DISTRICTLIST_DETAIL, method = RequestMethod.GET)
	public @ResponseBody List<String> getAllDistrictByState(@RequestParam String stateId) {
		return masterDataService.getAllDistrictByState(stateId);		 
	}
	
	@RequestMapping(value = MasterDataController.SUBDISTRICTLIST_DETAIL, method = RequestMethod.GET)
	public @ResponseBody List<String> getAllSubDistrictByDistrict(@RequestParam String districtId,@RequestParam String stateId) {
		return masterDataService.getAllSubDistrictByDistrict(stateId,districtId);	
	}
	
	@RequestMapping(value = MasterDataController.MANAGER_DETAIL, method = RequestMethod.GET)
    public @ResponseBody List<PharmaRep> getManagerDetails(@PathVariable("companyId") String companyId) {
        logger.info("Request received for manager details Details");    
        List<PharmaRep> managers = new ArrayList<PharmaRep>();
        try
        {
	        Integer _companyId = Integer.parseInt(companyId);
	        managers = medRepService.getPharmaRepManager(_companyId);         
        }
        catch(Exception e)
        {
        	logger.error("Error occurred while fethcing managers " + e.getMessage());
        }
        return managers;
    }
    
}
