package com.medrep.app.controller.rest;

import java.util.HashSet;
import java.util.List;
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

import com.medrep.app.model.About;
import com.medrep.app.model.AppResponse;
import com.medrep.app.model.Awards;
import com.medrep.app.model.EducationalQualification;
import com.medrep.app.model.InterestedAreas;
import com.medrep.app.model.Location;
import com.medrep.app.model.Publication;
import com.medrep.app.model.TherapeuticArea;
import com.medrep.app.model.User;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.model.Webinars;
import com.medrep.app.model.WorkExperience;
import com.medrep.app.service.DoctorInfoService;

@RestController
@RequestMapping("/api/doctor/info")
public class DoctorInfoController {
	private static final Logger logger = LoggerFactory.getLogger(DoctorInfoController.class);

	@Autowired
	DoctorInfoService doctorInfoService;
	
	

	@RequestMapping(value = "/fetch", method = RequestMethod.GET)
	public @ResponseBody AppResponse fetchDoctorInfo(Model model) {
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
					doctorInfoService.fetchDoctorInfo(model);
					response.setStatus("success");
					response.setResult(model.asMap().get("doctorinfo"));
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setStatus("Error");

		}

		return response;
	}

	
	@RequestMapping(value = "/fetchByDoctorId", method = RequestMethod.GET)
	public @ResponseBody AppResponse fetchDoctorInfoByDoctorId(@RequestParam Integer doctorId,Model model) {
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
					model.addAttribute("doctorId", doctorId);
					doctorInfoService.fetchDoctorInfoByDoctorId(model);
					response.setStatus("success");
					response.setResult(model.asMap().get("doctorinfo"));
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setStatus("Error");

		}

		return response;
	}

	/*@RequestMapping(value = "/career/update", method = RequestMethod.POST)
	public @ResponseBody AppResponse updateCareer(@RequestBody DoctorCareerModel about, Model model) {
		logger.info("Request received for update Notfication");
		AppResponse response = new AppResponse();
		model.addAttribute("about", about);
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
				
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while retrieving About ");
			response.setStatus("Error");

		}
		return response;

	}*/
	@RequestMapping(value = "/about/add", method = RequestMethod.POST)
	public @ResponseBody AppResponse addAbout(@RequestBody About about, Model model) {
		logger.info("Request received for update Notfication");
		AppResponse response = new AppResponse();
		model.addAttribute("about", about);
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
					doctorInfoService.addAbout(model);
					response.setStatus("success");
					response.setMessage("Succesfully inserted  about info");
					response.setId(model.asMap().get("id"));
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

	/**
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/about/get", method = RequestMethod.GET)
	public @ResponseBody AppResponse getAbout(Model model) {
		logger.info("Request received for update Notfication");
		AppResponse response = new AppResponse();

		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
					doctorInfoService.getAbout(model);
					response.setStatus("success");
					response.setResult(model.asMap().get("about"));
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

	@RequestMapping(value = "/about/update", method = RequestMethod.POST)
	public @ResponseBody AppResponse updateAbout(@RequestBody About about, Model model) {
		logger.info("Request received for update Notfication");
		AppResponse response = new AppResponse();

		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
					model.addAttribute("about", about);
					doctorInfoService.updateAbout(model);
					response.setStatus("success");
					response.setMessage("succesfully updated");
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

	@RequestMapping(value = "/about/updateDoctorExp", method = RequestMethod.POST)
	public @ResponseBody AppResponse doctorexpupdate(@RequestBody About about, Model model) {
		logger.info("Request received for update Notfication");
		AppResponse response = new AppResponse();

		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
					model.addAttribute("about", about);
					doctorInfoService.updateDoctorExp(model);
					response.setStatus("success");
					response.setMessage("succesfully updated");
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
	/**
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/about/delete", method = RequestMethod.GET)
	public @ResponseBody AppResponse deleteAbout(Model model) {
		AppResponse response = new AppResponse();

		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username", context.getUsername());
					doctorInfoService.deleteAbout(model);
					response.setMessage("successfully deleted about info");
					response.setStatus("success");
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

	/**
	 *
	 * @param workExperencesList
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/webinars/add", method = RequestMethod.POST)
	public @ResponseBody AppResponse addWebinars(@RequestBody List<Webinars> webinars, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("webinars", webinars);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.addWebinars(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully added webinars for the user");
			appResponse.setId(model.asMap().get("id"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage("Unable to add webinars for the user.");
		}
		return appResponse;
	}

	/**
	 * Updates the publications for the Active User
	 *
	 * @param publications
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/webinars/update", method = RequestMethod.POST)
	public @ResponseBody AppResponse updateWebinars(@RequestBody List<Webinars> webinars, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("webinars", webinars);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.updateWebinars(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully updated webinars");
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}
	

	@RequestMapping(value = "/awards/add", method = RequestMethod.POST)
	public @ResponseBody AppResponse addAwards(@RequestBody List<Awards> awards, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("awards", awards);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.addAwards(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully added awards for the user");
			appResponse.setId(model.asMap().get("id"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage("Unable to add awards for the user.");
		}
		return appResponse;
	}

	/**
	 * Updates the publications for the Active User
	 *
	 * @param publications
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/awards/update", method = RequestMethod.POST)
	public @ResponseBody AppResponse updateAwards(@RequestBody List<Awards> awards, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("awards", awards);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.updateAwards(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully updated awards");
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}
	/**
	 *
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/workexperience/get", method = RequestMethod.GET)
	public @ResponseBody AppResponse getWorkexperience(Model model, Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.getWorkExperience(model);
			appResponse.setStatus("success");
			appResponse.setResult(model.asMap().get("workExperiences"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage("Unable to get work experience.");
			;

		}
		return appResponse;
	}

	/**
	 *
	 * @param workExperencesList
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/workexperience/add", method = RequestMethod.POST)
	public @ResponseBody AppResponse addWorkexperience(@RequestBody List<WorkExperience> workExperencesList,
			Model model, Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			model.addAttribute("workExperiencesList", workExperencesList);
			doctorInfoService.addWorkExperience(model);
			appResponse.setStatus("success");
			appResponse.setMessage("succesfully added workexperience");
			appResponse.setResult(model.asMap().get("id"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}
	@RequestMapping(value = "/workexperience/update", method = RequestMethod.POST)
	public @ResponseBody AppResponse updateWorkexperience(@RequestBody List<WorkExperience> workExperencesList,
			Model model, Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			model.addAttribute("workExperiencesList", workExperencesList);
			doctorInfoService.updateWorkExperience(model);
			appResponse.setStatus("success");
			appResponse.setMessage("succesfully updated workexperience");
			appResponse.setResult(model.asMap().get("id"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 *
	 * @param workExperienceId
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/workexperience/delete/{workExperienceId}", method = RequestMethod.GET)
	public @ResponseBody AppResponse deleteWorkexperience(@PathVariable Integer workExperienceId, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("workExperienceId", workExperienceId);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.deleteWorkExperience(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully deleted work experience");
			appResponse.setResult(model.asMap().get("id"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	@RequestMapping(value = "/awards/delete/{awardsId}", method = RequestMethod.GET)
	public @ResponseBody AppResponse deleteAwards(@PathVariable Integer awardsId, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("awardsId", awardsId);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.deleteAwards(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully deleted awards");
			appResponse.setResult(model.asMap().get("id"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}
	@RequestMapping(value = "/webinars/delete/{webinarsId}", method = RequestMethod.GET)
	public @ResponseBody AppResponse deleteWebinars(@PathVariable Integer webinarsId, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("webinarsId", webinarsId);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.deleteWebinars(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully deleted webinars");
			appResponse.setResult(model.asMap().get("id"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}
	/**
	 *
	 * @param iae
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/interests/add", method = RequestMethod.POST)
	public @ResponseBody AppResponse addInterest(@RequestBody List<InterestedAreas> interests, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("interests", interests);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.addInterest(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully added interests");
			appResponse.setId(model.asMap().get("id"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 *
	 * @param iae
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/interests/update", method = RequestMethod.POST)
	public @ResponseBody AppResponse updateInterest(@RequestBody List<InterestedAreas> interests, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("interests", interests);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.updateInterest(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully updated interests.");
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 *
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/interests/get", method = RequestMethod.GET)
	public @ResponseBody AppResponse getInterest(Model model, Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.getInterest(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully fetched interest area");
			appResponse.setResult(model.asMap().get("interests"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 *
	 * @param interestId
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/interests/delete/{interestId}", method = RequestMethod.GET)
	public @ResponseBody AppResponse deleteInterest(@PathVariable Integer interestId, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("interestId", interestId);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.deleteInterest(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully deleted interest area");
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 *
	 * @param iae
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/education/add", method = RequestMethod.POST)
	public @ResponseBody AppResponse addEducation(@RequestBody List<EducationalQualification> educationDetails,
			Model model, Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("educationDetails", educationDetails);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.addEducation(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully added education details");
			appResponse.setId(model.asMap().get("id"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 *
	 * @param eduQualificationEntity
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/education/update", method = RequestMethod.POST)
	public @ResponseBody AppResponse updateEducation(@RequestBody List<EducationalQualification> educationDetails,
			Model model, Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("educationDetails", educationDetails);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.updateEducation(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully updated education details");
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 *
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/education/get", method = RequestMethod.GET)
	public @ResponseBody AppResponse getEducation(Model model, Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.getEducation(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully fetched education details.");
			appResponse.setResult(model.asMap().get("educationsDetails"));
			;
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 * Deletes the EducationalQualifications for the Active User
	 *
	 * @param eduId
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/education/delete/{educationId}", method = RequestMethod.GET)
	public @ResponseBody AppResponse deleteEducation(@PathVariable Integer educationId, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("educationId", educationId);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.deleteEducation(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully deleted education details");
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 * Adds publications to the Active User
	 *
	 * @param publications
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/publications/add", method = RequestMethod.POST)
	public @ResponseBody AppResponse addPublication(@RequestBody List<Publication> publications, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("publications", publications);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.addPublication(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully added publications for the user");
			appResponse.setId(model.asMap().get("id"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage("Unable to add publications for the user.");
		}
		return appResponse;
	}

	/**
	 * Updates the publications for the Active User
	 *
	 * @param publications
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/publications/update", method = RequestMethod.POST)
	public @ResponseBody AppResponse updatePublication(@RequestBody List<Publication> publications, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("publications", publications);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.updatePublication(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully updated publications");
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 * Fetches publications for the Active User
	 *
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/publications/get", method = RequestMethod.GET)
	public @ResponseBody AppResponse getPublications(Model model, Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.getPublication(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully fetched publication");
			appResponse.setResult((List<Publication>) model.asMap().get("publications"));
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}

	/**
	 * Deletes the publication sepecified by publicationId for the active user
	 *
	 * @param publicationId
	 * @param model
	 * @param activeUser
	 * @return
	 */
	@RequestMapping(value = "/publications/delete/{publicationId}", method = RequestMethod.GET)
	public @ResponseBody AppResponse deletePublication(@PathVariable Integer publicationId, Model model,
			Authentication activeUser) {
		AppResponse appResponse = new AppResponse();
		try {
			model.addAttribute("publicationId", publicationId);
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.deletePublication(model);
			appResponse.setStatus("success");
			appResponse.setMessage("Successfully deleted publication");
		} catch (Exception e) {
			appResponse.setStatus("failure");
			appResponse.setMessage(e.getMessage());
		}
		return appResponse;
	}


	@RequestMapping("/therapeutic-area/get")
	public AppResponse fetchTherapeuticArea(Model model,Authentication activeUser) {
		AppResponse response=new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.fetchTheraPeuticArea(model);
			response.setStatus("success");
			if(model.asMap().get("therapeuticarea")==null){
				response.setResult(model.asMap().get("therapeuticarea"));
			}else
				response.setMessage("TheraPeutic Area is not available.");
		} catch (Exception e) {
			response.setStatus("error");
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value="/therapeutic-area/update",method=RequestMethod.POST)
	public AppResponse updateTheraPeuticArea(@RequestBody TherapeuticArea therapeuticArea,Authentication activeUser,Model model) {
		AppResponse response = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			model.addAttribute("therapeuticArea", therapeuticArea);
			doctorInfoService.updateTheraPeutcArea(model);
			response.setStatus("success");
			response.setId(therapeuticArea.getTherapeuticId());
		} catch (Exception e) {
			response.setStatus("error");
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}


	@RequestMapping(value="/therapeutic-area/delete",method=RequestMethod.POST)
	public AppResponse deleteTheraPeuticArea(@RequestBody TherapeuticArea therapeuticArea,Authentication activeUser,Model model) {
		AppResponse response = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			model.addAttribute("therapeuticArea", therapeuticArea);
			doctorInfoService.deleteTheraPeutcArea(model);
			response.setStatus("success");
			response.setId(therapeuticArea.getTherapeuticId());
		} catch (Exception e) {
			response.setStatus("error");
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value="/address/get",method=RequestMethod.GET)
	public AppResponse getAddress(Authentication activeUser,Model model) {
		AppResponse response = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			doctorInfoService.getAddress(model);
			response.setResult(model.asMap().get("loc"));
			response.setStatus("success");
		} catch (Exception e) {
			response.setStatus("error");
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	@RequestMapping(value="/address/update",method=RequestMethod.POST)
	public AppResponse changeAddress(@RequestBody List<Location>  addresses,Authentication activeUser,Model model) {
		AppResponse response = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			model.addAttribute("addresses", addresses);
			doctorInfoService.changeAddress(model);
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
			doctorInfoService.deleteAddress(model);
			response.setStatus("success");
		} catch (Exception e) {
			response.setStatus("error");
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value="/contactInfo/update",method=RequestMethod.POST)
	public AppResponse updateContactInfo(@RequestBody User user,Authentication activeUser,Model model) {
		AppResponse response = new AppResponse();
		try {
			model.addAttribute("username", ((UserSecurityContext) activeUser.getPrincipal()).getUsername());
			model.addAttribute("user",user);
			doctorInfoService.updateContactInfo(model);
			response.setStatus("success");
		} catch (Exception e) {
			response.setStatus("error");
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
}