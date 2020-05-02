package com.medrep.app.controller.web;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorNotification;
import com.medrep.app.model.LocationType;
import com.medrep.app.model.TherapeuticArea;
import com.medrep.app.service.DoctorService;
import com.medrep.app.service.TherapeuticAreaService;
import com.medrep.app.util.MedrepException;

/**
 * Handles and retrieves the common or admin page depending on the URI template.
 * A user must be log-in first he can access these pages.  Only the admin can see
 * 
 * 
 * the adminpage, however.
 */

@Controller
@RequestMapping("/web/admin")
@SessionAttributes( { "tareaList","locationtypelist"})
public class AdminDoctorController {
	
	@Autowired
	DoctorService doctorService;
	
	@Autowired
	TherapeuticAreaService therapeuticAreaService;

	protected static Logger logger = Logger.getLogger("controller");
	
	  
    /**
     * Handles and retrieves the admin JSP page that only admins can see
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/doctors.do", method = RequestMethod.GET)
    public ModelAndView getAdminPage() {
   	logger.debug("Received request to show admin page");
    
   	 ModelAndView mnv = new ModelAndView();
   	 mnv.addObject("newDoctorList",getDoctorList("New"));
  	 mnv.addObject("activeDoctorList", getDoctorList("Active"));
   	 mnv.addObject("disabledDoctorList", getDoctorList("Disabled"));
   	 mnv.addObject("inProgressDoctorList", getDoctorList("Inprogress"));
   	 mnv.addObject("allDoctorList", doctorService.findAllDoctorsExceptDeleted());
   	 List<TherapeuticArea> tares = therapeuticAreaService.getAll();
   	 List<LocationType> locationtypelis=doctorService.findlocationtype();
   	 mnv.addObject("tareaList", tares);
   	 mnv.addObject("locationtypelist",locationtypelis);
   	 mnv.setViewName("/web/admin/doctors");    	 
   	 return mnv;
   	 
	}
    
    public List<Doctor> getDoctorList(String status){
    	List<Doctor> new_users;
    	new_users = doctorService.searchDoctorByStatus(status);
        return new_users;        
    }
    
    @RequestMapping(value = "/doctors.do", method = RequestMethod.POST)
    public ModelAndView updateDoctorStatus(@RequestParam("statusDropDown") String status,@RequestParam("doctorId") String doctorId){
        doctorService.alterDoctorStatusByDoctorId(doctorId, status);
    	logger.debug("Received request to show admin page");
        
   	 ModelAndView mnv = new ModelAndView();
   	 mnv.addObject("newDoctorList",getDoctorList("New"));
  	 mnv.addObject("activeDoctorList", getDoctorList("Active"));
   	 mnv.addObject("disabledDoctorList", getDoctorList("Disabled"));
   	 mnv.addObject("inProgressDoctorList", getDoctorList("Inprogress"));
   	 mnv.addObject("allDoctorList", doctorService.findAllDoctorsExceptDeleted());
   	 mnv.setViewName("/web/admin/doctors");    	 
   	 return mnv;
    	
    	
    }
    
    @RequestMapping(value = "/removeDoctors.do", method = RequestMethod.POST)
    public ModelAndView removeDoctors(@RequestParam("removeDoctors") String[] doctorIds)
    {
		for(int i=0; i <doctorIds.length ; i++)
		{
			System.out.println(doctorIds[i]);
			doctorService.alterDoctorStatusByDoctorId(doctorIds[i], "Deleted");
		}
    	logger.debug("Received request to show admin page");
	   	 ModelAndView mnv = new ModelAndView("redirect:/web/admin/doctors.do");
	   	 mnv.addObject("newDoctorList",getDoctorList("New"));
	  	 mnv.addObject("activeDoctorList", getDoctorList("Active"));
	   	 mnv.addObject("disabledDoctorList", getDoctorList("Disabled"));
	   	 mnv.addObject("inProgressDoctorList", getDoctorList("Inprogress"));
	   	 mnv.addObject("allDoctorList", doctorService.findAllDoctorsExceptDeleted());
	   	 return mnv;    	
    }
    
    @RequestMapping(value = "/getDoctor.do", method = RequestMethod.GET)
	public @ResponseBody Doctor getDoctorById(@RequestParam String doctorId) {
    	Doctor doctor = null;
    	if(doctorId!=null)
    		doctor = doctorService.findDoctorById(doctorId);
    	return doctor;
    }	
    
    @RequestMapping(value = "/updateDoctor.do", method = RequestMethod.POST)
   	public @ResponseBody String getDoctorById( @RequestBody Doctor reqDocParam,Model model) {
    //	model.addAttribute("displayPic", displayPic);
    	
       			model.addAttribute("loginId", reqDocParam.getUsername());
       			model.addAttribute("doctor",reqDocParam);
       			try {
					doctorService.updateDoctor(model);
				} catch (MedrepException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	       			return "fail";
				}
       		
       	return "success";
    }
    
    
    
    @RequestMapping(value = "/getReport", method = RequestMethod.POST)
	public ModelAndView downloadDoctorStatistics(@RequestParam String doctorId)throws Exception{
		Map<String,List<DoctorNotification>> reportDataMap = doctorService.getReport(Integer.parseInt(doctorId));
		return new ModelAndView("NotificationStatistics", "notificationstatistics", reportDataMap);

	}
}
