package com.medrep.app.controller.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.medrep.app.model.PharmaRep;
import com.medrep.app.service.MedRepService;
import com.medrep.app.service.NotificationService;
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
@SessionAttributes( { "companyMap"})
public class PharmaRepsController {
	
	@Autowired
	MedRepService medRepService;
	
	@Autowired
	NotificationService notificationService;

	protected static Logger logger = Logger.getLogger("controller");
	
	  
    /**
     * Handles and retrieves the admin JSP page that only admins can see
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/pharma-reps.do", method = RequestMethod.GET)
    public ModelAndView getAdminPage() {
    	logger.debug("Received request to show admin page");
    
    	logger.debug("Received request to show admin page");
   	 ModelAndView mnv = new ModelAndView();
   	 mnv.addObject("newRepList",getRepList("New"));
  	 mnv.addObject("activeRepList", getRepList("Active"));
   	 mnv.addObject("disabledRepList", getRepList("Disabled"));
   	 mnv.addObject("inProgressRepList", getRepList("Inprogress"));
   	 mnv.addObject("allDoctorList", medRepService.findallRepsExceptDeleted());
   	 mnv.addObject("companyMap", notificationService.getAllCompanys());
   	 mnv.setViewName("/web/admin/pharma-reps");    	 
   	 return mnv;
	}
    
    public List<PharmaRep> getRepList(String status){
    	List<PharmaRep> new_users = null;
    	try {
			new_users = medRepService.getPharamaRepByStatus(status);
		} catch (MedrepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new_users;        
    }
    
    @RequestMapping(value = "/pharma-reps.do", method = RequestMethod.POST)
    public ModelAndView updateRepsStatus(@RequestParam("statusDropDown") String status,@RequestParam("repId") String repId){
    	medRepService.alterRepStatusByRepId(repId, status);
    	logger.debug("Received request to show admin page");
        
    	logger.debug("Received request to show admin page");
   	 ModelAndView mnv = new ModelAndView();
   	 mnv.addObject("newRepList",getRepList("New"));
  	 mnv.addObject("activeRepList", getRepList("Active"));
   	 mnv.addObject("disabledRepList", getRepList("Disabled"));
   	 mnv.addObject("inProgressRepList", getRepList("Inprogress"));
   	 mnv.addObject("allDoctorList", medRepService.findallRepsExceptDeleted());
   	 mnv.setViewName("/web/admin/pharma-reps");    	 
   	 return mnv;
    	
    	
    }
    
    @RequestMapping(value = "/removeReps.do", method = RequestMethod.POST)
    public ModelAndView removeReps(@RequestParam("removeReps") String[] repIds){
    	for(int i=0; i <repIds.length ; i++)
    	{
    		System.out.println(repIds[i]);
    		medRepService.alterRepStatusByRepId(repIds[i], "Deleted");
    	}
    	logger.debug("Received request to show admin page");
        
    	logger.debug("Received request to show admin page");
   	 ModelAndView mnv = new ModelAndView("redirect:/web/admin/pharma-reps.do");
   	 mnv.addObject("newRepList",getRepList("New"));
  	 mnv.addObject("activeRepList", getRepList("Active"));
   	 mnv.addObject("disabledRepList", getRepList("Disabled"));
   	 mnv.addObject("inProgressRepList", getRepList("Inprogress"));
   	 mnv.addObject("allDoctorList", medRepService.findallRepsExceptDeleted());
   	 return mnv;
    	
    	
    }
    
    @RequestMapping(value = "/getPharmaRep.do", method = RequestMethod.GET)
   	public @ResponseBody PharmaRep getPharmaRepById(@RequestParam String repId) {
    	PharmaRep pharmaRep = null;
       	if(repId!=null)
       		pharmaRep = medRepService.findRepById(repId);
       	return pharmaRep;
       }	
}
