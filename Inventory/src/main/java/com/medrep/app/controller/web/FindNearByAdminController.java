package com.medrep.app.controller.web;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.medrep.app.model.AmbulancesModel;
import com.medrep.app.model.DiagnosticsModel;
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.HospitalModel;
import com.medrep.app.model.Location;
import com.medrep.app.model.TherapeuticArea;
import com.medrep.app.service.FindNearByService;
import com.medrep.app.service.MasterDataService;
import com.medrep.app.service.NotificationService;

@Controller
@RequestMapping("/web/patient")
//@SessionAttributes({"tareaList","companyMap"})
public class FindNearByAdminController {
	
	private static String view="/web/patient/";
	protected static Logger logger=Logger.getLogger("controller");
	
	
	@Autowired
	FindNearByService  findnearbyService;
	
	@Autowired 
	NotificationService notificationService;
	@Autowired
	MasterDataService  masterdataService;
	
	@RequestMapping(value = "/hospital.do", method = RequestMethod.GET)
    public ModelAndView getHospitalsLists() {
    	logger.debug("Received request to show admin page");
     ModelAndView mnv = new ModelAndView();
   	 mnv.addObject("hospitallist",getHospitalList());
   	 mnv.addObject("statesall",masterdataService.getAllState());
   	 mnv.setViewName("/web/patient/hospital");    	 
   	 return mnv;
	}
	
	@RequestMapping(value = "/ambulances.do", method = RequestMethod.GET)
    public ModelAndView getAmbulancesLists() {
    	logger.debug("Received request to show admin page");
     ModelAndView mnv = new ModelAndView();
   	 mnv.addObject("ambulanceslist",getAmbulancesList());
   	 mnv.addObject("statesall",masterdataService.getAllState());
   	 mnv.setViewName("/web/patient/ambulances");    	 
   	 return mnv;
	}
	
	@RequestMapping(value = "/diagnostic.do", method = RequestMethod.GET)
    public ModelAndView getDiagnosticsLists() {
    	logger.debug("Received request to show admin page");
     ModelAndView mnv = new ModelAndView();
   	 mnv.addObject("diagnosticlist",getDiagnosticsList());
   	 mnv.addObject("statesall",masterdataService.getAllState());
   	 mnv.setViewName("/web/patient/diagnostic");    	 
   	 return mnv;
	}
	
	private List<HospitalModel> getHospitalList() {
		// TODO Auto-generated method stub
		 return findnearbyService.getAllHospital();		
	}
	private List<AmbulancesModel> getAmbulancesList() {
		// TODO Auto-generated method stub
		 return findnearbyService.getAllAmbulances();		
	}
	private List<DiagnosticsModel> getDiagnosticsList() {
		// TODO Auto-generated method stub
		 return findnearbyService.getAllDiagnostics();		
	}
	
	@RequestMapping(value = "/getAllDistrict.do", method = RequestMethod.GET)
	public @ResponseBody List<String> getAllDistrictByState(@RequestParam String stateId) {
/*		StringBuilder buffer =new StringBuilder();
		buffer.append("<option value=0> ----------   Select One  -------- </option>");
*/		List<String> tmap = masterdataService.getAllDistrictByState(stateId);
		/*for(String key : tmap.keySet()){
			buffer.append("<option value="+key +">" +tmap.get(key) +"</option>");
		}*/
		return tmap;
	}
	
	@RequestMapping(value = "/getAllSubDistrict.do", method = RequestMethod.GET)
	public @ResponseBody List<String> getAllSubDistrictByDistrict(@RequestParam String stateId,@RequestParam String districtId) {
		
		return  masterdataService.getAllSubDistrictByDistrict(stateId,districtId);
	}
	
	@RequestMapping(value = "/createHospital.do", method = RequestMethod.POST)
    public ModelAndView createHospital( @RequestParam("hospital_name") String HospitalName,
    								 @RequestPart("HospitalLogoImage") MultipartFile HospitalLogoImage,
    								 @RequestPart("HospitalImage") MultipartFile HospitalImage,
    								 @RequestParam("hospitalAddress1") String HospitalAddress1,
    								 @RequestParam("hospitalAddress2") String HospitalAddress2,
    								 @RequestParam("subdistrictSelectedId") String HospitalSubDistrict,
    								 @RequestParam("districtSelectedId") String HospitalCity,
    								 @RequestParam("stateSelectedId") String HospitalState,
/*    								 @RequestParam("HospitalZipcode") String HospitalZipcode,
*/    								 @RequestParam("ContactNumber") String HospitalContactNo,
    								 @RequestParam("emailId") String HospitalEmailId,
    								 @RequestParam("zipcode") String zipcode,

    								/* @RequestParam("buttonName") String buttonName,*/Model model){
		 ModelAndView mnv = new ModelAndView();
		try{
		
		model.addAttribute("hospitalLogo", HospitalLogoImage);
		model.addAttribute("HospitalImage", HospitalImage);
		HospitalModel hospital = new HospitalModel();
		Set<TherapeuticArea> therapeuticAreas = new HashSet<TherapeuticArea>();
		hospital.setHospital_name(HospitalName);
		hospital.setAddress1(HospitalAddress1);
		hospital.setAddress2(HospitalAddress2);
		hospital.setLocatity(HospitalSubDistrict);
		hospital.setCity(HospitalCity);
		hospital.setState(HospitalState);
		hospital.setMobileNo(HospitalContactNo);
		hospital.setZipcode(zipcode);


			model.addAttribute("hospital",hospital);
			findnearbyService.createHospital(model);
			logger.debug("Received request to show admin page");
		}catch(Exception e){
			
		}
      	 mnv.setViewName("redirect:/web/patient/hospital.do");    	 
   	 return mnv;
    }
	
	@RequestMapping(value = "/createAmbulances.do", method = RequestMethod.POST)
    public ModelAndView createAmbulances( @RequestParam("AmbulancesName") String AmbulancesName,
    								 @RequestParam("collectionFee") String collectionFee,
    								 @RequestPart("AmbulancesLogo") MultipartFile AmbulancesLogoImage,
    								 @RequestPart("AmbulancesImage") MultipartFile AmbulancesImage,
    								 @RequestParam("AddressLine1") String AmbulancesAddress1,
    								 @RequestParam("AddressLine2") String AmbulancesAddress2,
    								 @RequestParam("subdistrictSelectedId") String AmbulanceSubDistrict,
    								 @RequestParam("districtSelectedId") String AmbulancesCity,
    								 @RequestParam("stateSelectedId") String AmbulancesState,
    								 @RequestParam("ContactNumber") String AmbulancesContactNo,
    								 @RequestParam("zipcode") String zipcode,
/*    								 @RequestParam("emailId") String HospitalEmailId,
*/    								/* @RequestParam("buttonName") String buttonName,*/Model model){
		 ModelAndView mnv = new ModelAndView();
		try{
		
		model.addAttribute("ambulancesLogo", AmbulancesLogoImage);
		model.addAttribute("ambulancesImage", AmbulancesImage);
		AmbulancesModel ambulances = new AmbulancesModel();
		Set<TherapeuticArea> therapeuticAreas = new HashSet<TherapeuticArea>();
		ambulances.setFee(collectionFee);
		ambulances.setAmbulance_name(AmbulancesName);
		ambulances.setAddress1(AmbulancesAddress1);
		ambulances.setAddress2(AmbulancesAddress2);
		ambulances.setLocatity(AmbulanceSubDistrict);
		ambulances.setCity(AmbulancesCity);
		ambulances.setState(AmbulancesState);
		ambulances.setMobileNo(AmbulancesContactNo);
		ambulances.setZipcode(zipcode);

			model.addAttribute("ambulances",ambulances);
			findnearbyService.createAmbulances(model);

   	   
    	logger.debug("Received request to show admin page");
		}catch(Exception e){
			
		}
      
      	 mnv.setViewName("redirect:/web/patient/ambulances.do");    	 
   	 return mnv;
    }
	
	
	@RequestMapping(value = "/createDiagnostic.do", method = RequestMethod.POST)
    public ModelAndView createDiagnostic( @RequestParam("diagnosticName") String diagnosticName,
    								 @RequestPart("diagnosticLogo") MultipartFile diagnosticLogo,
    								 @RequestPart("diagnosticImage") MultipartFile diagnosticImage,
    								 @RequestParam("AddressLine1") String AddressLine1,
    								 @RequestParam("AddressLine2") String AddressLine2,
    								 @RequestParam("subdistrictSelectedId") String subdistrictSelected,
    								 @RequestParam("districtSelectedId") String districtSelectedId,
    								 @RequestParam("stateSelectedId") String stateSelectedId,
/*    								 @RequestParam("AmbulancesZipcode") String AmbulancesZipcode,*/
    								 @RequestParam("ContactNumber") String ContactNumber,
    								 @RequestParam("emailId") String HospitalEmailId,
    								 @RequestParam("zipcode") String zipcode,
    								 @RequestParam(required=false) String homecollection,Model model){
		 ModelAndView mnv = new ModelAndView();
		try{
		
		model.addAttribute("diagnosticLogo", diagnosticLogo);
		model.addAttribute("diagnosticImage", diagnosticImage);
		DiagnosticsModel diagnostics = new DiagnosticsModel();
		Location location = new Location();
		DisplayPicture displayPicture = new DisplayPicture();
		Set<TherapeuticArea> therapeuticAreas = new HashSet<TherapeuticArea>();
		diagnostics.setDiagnostics_name(diagnosticName);
		diagnostics.setAddress1(AddressLine1);
		diagnostics.setAddress2(AddressLine2);
		diagnostics.setLocatity(subdistrictSelected);
		diagnostics.setCity(districtSelectedId);
		diagnostics.setState(stateSelectedId);
		diagnostics.setMobileNo(ContactNumber);
		if (homecollection!= null){
			diagnostics.setHomeCollection("Y");
		}else {
			diagnostics.setHomeCollection("N");
		}
		diagnostics.setZipcode(zipcode);
		model.addAttribute("diagnostics",diagnostics);
		findnearbyService.createDiagnostics(model);
   	   
    	logger.debug("Received request to show admin page");
		}catch(Exception e){
			
		}
      
      	 mnv.setViewName("redirect:/web/patient/diagnostic.do");    	 
   	 return mnv;
    }

}
	


