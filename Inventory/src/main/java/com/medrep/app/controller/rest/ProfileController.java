package com.medrep.app.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.Patient;
import com.medrep.app.model.PharmaRep;
import com.medrep.app.model.User;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.ActivityService;
import com.medrep.app.service.DisplayPictureService;
import com.medrep.app.service.DoctorService;
import com.medrep.app.service.MedRepService;
import com.medrep.app.service.PatientService;
import com.medrep.app.service.UserService;

@Controller
@RequestMapping("/api/profile")
public class ProfileController
{
	public static final String DOCTOR_PROFILE = "/getDoctorProfile";
	public static final String PATIENT_PROFILE = "/getPatientProfile";
	public static final String DOCTOR_DEFAULT_PROFILE = "/getDoctorDefaultProfile";
	public static final String GET_DISPLAY_PICTURE = "/getDisplayPicture";
	public static final String GET_DOCTOR_PROFILE = "/getDoctorProfile/{token}";
	public static final String UPDATE_DOCTOR_PROFILE = "/updateDoctorProfile";
	public static final String UPDATE_PATIENT_PROFILE = "/updatePatientProfile";
	public static final String SEARCH_DOCTOR_BY_NAME = "/searchDoctorByName/{name}";
	public static final String SEARCH_DOCTOR_BY_MOBILE = "/searchDoctorByMobile/{mobileNo}";

	public static final String REP_PROFILE = "/getPharmaRepProfile/{pharmaRepId}";
	public static final String REP_DEFAULT_PROFILE = "/getPharmaRepDefaultProfile";
	public static final String GET_REP_PROFILE = "/getPharmaRepProfile";
	public static final String UPDATE_REP_PROFILE = "/updatePharmaRepProfile";
	public static final String SEARCH_REP_BY_NAME = "/searchPharmaRepByName/{name}";
	public static final String SEARCH_REP_BY_MOBILE = "/searchPharmaRepMobile/{mobileNo}";

	public static final String MY_ROLE = "/getMyRole";
	public static final String GET_MY_TEAM = "/getMyTeam";
	public static final String GET_MY_COMPANY_DOCTORS = "/getMyCompanyDoctors";

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private MedRepService medRepService;

	@Autowired
	private UserService userService;

	@Autowired
	private DisplayPictureService displayPictureService;

	@Autowired
	private ActivityService activityService;

	/**
	 * Method will be called in initial page load at GET /employee-module
	 *
	 *
	 * */

	@RequestMapping(value = ProfileController.UPDATE_DOCTOR_PROFILE, method = RequestMethod.POST)
    public @ResponseBody AppResponse updateDoctorProfile(Doctor doctor,Model model) {
        logger.info("Request received for login");

        AppResponse response = new AppResponse();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String loginId = context.getUsername();
        			model.addAttribute("loginId", loginId);
        			model.addAttribute("doctor",doctor);
        			doctorService.updateDoctor(model);
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

	
	@RequestMapping(value = ProfileController.UPDATE_PATIENT_PROFILE, method = RequestMethod.POST)
    public @ResponseBody AppResponse updatePatientProfile(@RequestBody Patient patient,Model model) {
        logger.info("Request received for login");

        AppResponse response = new AppResponse();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String loginId = context.getUsername();
        			model.addAttribute("loginId", loginId);
        			model.addAttribute("patient",patient);
        			patientService.updatePatient(model);
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
	@RequestMapping(value = ProfileController.DOCTOR_PROFILE, method = RequestMethod.GET)
    public @ResponseBody Doctor getDoctorProfile() {
        logger.info("Request received for Get My Profile");
        Doctor doctor = new Doctor();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			doctor = doctorService.getDoctor(context.getUserSecurityId());

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return doctor;
    }
	
	@RequestMapping(value = ProfileController.PATIENT_PROFILE, method = RequestMethod.GET)
    public @ResponseBody Patient getPatientProfile() {
        logger.info("Request received for Get My Profile");
        Patient patient = new Patient();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			patient = patientService.getPatient(context.getUserSecurityId());

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return patient;
    }

	@RequestMapping(value = ProfileController.DOCTOR_DEFAULT_PROFILE, method = RequestMethod.GET)
    public @ResponseBody Doctor getDoctorDefaultProfile() {
        logger.info("Request received for Get My Profile");
        Doctor doctor = new Doctor();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			doctor = doctorService.getDoctorDefaultProfile(context.getUserSecurityId());

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return doctor;
    }


	@RequestMapping(value = ProfileController.GET_DOCTOR_PROFILE, method = RequestMethod.GET)
    public @ResponseBody Doctor getDoctorProfile(@PathVariable("token") String token) {
        logger.info("Request received for Get Doctor Profile based on Id");
        Doctor doctor = null;
        try
		{

			doctor = doctorService.findDoctorById(token);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
        return doctor;
    }

    @RequestMapping(value = ProfileController.SEARCH_DOCTOR_BY_NAME, method = RequestMethod.GET)
    public @ResponseBody List<Doctor> searchDoctorByName(@PathVariable("name") String name) {
        logger.info("Request received for Get My Profile");
        List<Doctor> doctors = null;
        try
        {

        	doctors = doctorService.searchDoctorByName(name);
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}


        return doctors;
    }

	@RequestMapping(value = ProfileController.SEARCH_DOCTOR_BY_MOBILE, method = RequestMethod.GET)
    public @ResponseBody Doctor searchDoctorByMobile(@PathVariable("mobileNo") String mobileNo) {
        logger.info("Request received for Get My Profile");
        Doctor doctor = null;
        try
        {

        	doctor = doctorService.getDoctorByMobile(mobileNo);
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}


        return doctor;
    }



	/**
	 * Method will be called in initial page load at GET /employee-module
	 *
	 *
	 * */

	@RequestMapping(value = ProfileController.UPDATE_REP_PROFILE, method = RequestMethod.POST)
    public @ResponseBody AppResponse updatePharmaRepProfile(@RequestBody PharmaRep pharmaRep,Model model) {
        logger.info("Request received for login");

        AppResponse response = new AppResponse();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			String loginId = context.getUsername();
        			model.addAttribute("loginId",loginId);
        			model.addAttribute("pharmaRep", pharmaRep);
		        	medRepService.updatePharmaRep(model);
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



	@RequestMapping(value = ProfileController.REP_PROFILE, method = RequestMethod.GET)
    public @ResponseBody PharmaRep getPharaRepProfile(@PathVariable String pharmaRepId)
	{
        logger.info("Request received for Get My Profile");
        PharmaRep rep = new PharmaRep();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			rep = medRepService.getPharamaRep(pharmaRepId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return rep;
    }

	@RequestMapping(value = ProfileController.REP_DEFAULT_PROFILE, method = RequestMethod.GET)
    public @ResponseBody PharmaRep getPharaRepDefaultProfile()
	{
        logger.info("Request received for Get My Profile");
        PharmaRep rep = new PharmaRep();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			rep = medRepService.getPharamaRepDefaultProfile(context.getUserSecurityId());

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return rep;
    }


	@RequestMapping(value = ProfileController.GET_REP_PROFILE, method = RequestMethod.GET)
    public @ResponseBody PharmaRep getPharmaRepProfile() {
        PharmaRep rep = new PharmaRep();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			System.out.println("contextsecid"+context.getUserSecurityId());
        			rep = medRepService.getPharamaRepDefaultProfile(context.getUserSecurityId());

        		}
        		catch(Exception e)
        		{
        			logger.error("exception occured in pharmarepdefaultptofile");
        			e.printStackTrace();
        		}
        	}


        }
        return rep;
    }

	@RequestMapping(value = ProfileController.SEARCH_REP_BY_NAME, method = RequestMethod.GET)
    public @ResponseBody List<PharmaRep> searchPharmaRepByName(@PathVariable("name") String name) {
        logger.info("Request received for Get My Profile");
        List<PharmaRep> reps = null;
        try
        {

        	reps = medRepService.searchPharamaRepByName(name);
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}


        return reps;
    }

	@RequestMapping(value = ProfileController.SEARCH_REP_BY_MOBILE, method = RequestMethod.GET)
    public @ResponseBody PharmaRep searchPharmaRepByMobile(@PathVariable("mobileNo") String mobileNo) {
        logger.info("Request received for Get My Profile");
        PharmaRep rep = null;
        try
        {

        	rep = medRepService.getPharmaRepByMobile(mobileNo);
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}


        return rep;
    }

	@RequestMapping(value = ProfileController.MY_ROLE, method = RequestMethod.GET)
    public @ResponseBody User getMyRole() {
        logger.info("Request received for Get My Profile");
        User user = new User();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{
        			user = userService.findUserByEmailId(context.getUsername());
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return user;
    }

	@RequestMapping(value = ProfileController.GET_MY_TEAM, method = RequestMethod.GET)
    public @ResponseBody List<PharmaRep> getMyTeam() {
        logger.info("Request received for manager details Details");
        List<PharmaRep> reps = new ArrayList<PharmaRep>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			reps = medRepService.getPharmaRepByManagerEmail(context.getUsername());
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return reps;
    }

	@RequestMapping(value = ProfileController.GET_DISPLAY_PICTURE, method = RequestMethod.GET)
    public @ResponseBody DisplayPicture getDisplayPicture() {
        logger.info("Request received for Get My Profile");
        DisplayPicture displayPicture = null;
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			displayPicture = displayPictureService.getUserDisplayPicture(context.getUserSecurityId());

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return displayPicture;
    }

	@RequestMapping(value = ProfileController.GET_MY_COMPANY_DOCTORS, method = RequestMethod.GET)
    public @ResponseBody List<Doctor> getMyCompanyDoctors()
	{
        logger.info("Request received for providing doctors");
        List<Doctor> doctors = new ArrayList<Doctor>();
        UserSecurityContext context = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext)
        {
        	context = (UserSecurityContext)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	if(context != null)
        	{
        		try
        		{

        			String  loginId = context.getUsername();
        			doctors = activityService.getCompnayDoctors(loginId);

        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
        	}


        }
        return doctors;
    }
}
