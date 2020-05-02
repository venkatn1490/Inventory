

package com.medrep.app.controller.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.model.AppResponse;
import com.medrep.app.model.Comments;
import com.medrep.app.model.ConnectionDetails;
import com.medrep.app.model.Contacts;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorInvitation;
import com.medrep.app.model.Group;
import com.medrep.app.model.InviteContacts;
import com.medrep.app.model.Likes;
import com.medrep.app.model.Member;
import com.medrep.app.model.News;
import com.medrep.app.model.NewsSource;
import com.medrep.app.model.PendingCount;
import com.medrep.app.model.PostMessage;
import com.medrep.app.model.RegisterDeviceToken;
import com.medrep.app.model.ShareDetails;
import com.medrep.app.model.Transform;
import com.medrep.app.model.TransformCategory;
import com.medrep.app.model.TransformModel;
import com.medrep.app.model.UserDetails;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.service.CachingService;
import com.medrep.app.service.DoctorPlusService;
import com.medrep.app.service.NewsService;
import com.medrep.app.service.NotificationService;
import com.medrep.app.service.SurveyService;
import com.medrep.app.util.RssFeedReader;
import com.medrep.app.util.Util;

@Controller
@RequestMapping("/api/doctorPlus")
public class DoctorPlusController {

	@Autowired
	DoctorPlusService doctorPlusService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	SurveyService surveyService;

	@Autowired
	NewsService newsService;
	@Autowired
	CachingService cachingService;

	private static final Logger logger = LoggerFactory.getLogger(DoctorPlusController.class);
	public static final String GET_MY_CONNECTIONS = "/getMyConnections";
	public static final String UPDATE_INVITATION = "/updateInvitation";
	public static final String CREATE_INVITATION = "/createInvitation";
	public static final String GET_MEDREP_DOCTORS = "/getMedRepDoctors";

	// New Services
	public static final String GET_MEDREP_CONTACTS = "/getContacts";
	public static final String SEARCH_CONTACTS = "/searchContact";
	public static final String CREATE_GROUP = "/createGroup";
	public static final String GET_GROUP = "/getGroups";
	public static final String UPDATE_MEMBER_STATUS = "/updateMemberStatus";
	public static final String FETCH_GROUP_MEMBERS = "/fetchAllGroupMembers";
	public static final String ADD_MEMBER = "/addMember";
	public static final String DELETE_GROUP = "/deleteGroup";
	public static final String UPDATE_GROUP = "/updateGroup";
	public static final String GET_GROUP_MEMBERS = "/getGroupMembers/{groupId}";
	public static final String GET_PENDING_GROUPS = "/getPendingGroups";
	public static final String GET_PENDING_MEMBES_GROUP = "/getPendingGroupMembers/{groupId}";
	public static final String INVITE_CONTACTS = "/inviteContacts";
	public static final String GET_SUGGESTED_GROUP = "/getSuggestedGroups";
	public static final String UPDATE_CONNECTION_STATUS = "/updateContactStatus";
	public static final String MORE_GROUP = "/moreGroups";
	public static final String JOIN_GROUP = "/joinGroups";
	public static final String EXIT_GROUP = "/exitGroup";
	public static final String DELETE_CONTACT = "/deleteContact";
	public static final String REGISTER_DEVICE_TOKEN = "/registerDeviceToken";
	public static final String PUSH_NOTIFICATION = "/pushNotification";

	public static final String GET_USER_PREFERENCES = "/getUserPreferences";
	public static final String GET_ALL_CONTACTS_BY_CITY = "/getAllContactsByCity";
	public static final String GET_MY_CONTACT_LIST = "/getMyContactList";
	public static final String GET_SUGGESTED_CONTACTS = "/getSuggestedContacts";
	public static final String GET_PENDING_CONNECTIONS = "/fetchPendingConnections";
	public static final String ADD_CONTACTS = "/addContacts";
	public static final String REMOVE_MEMBER = "/removeMember";
	public static final String POST_MESSAGES = "/postMessages";
	public static final String GET_POST_MESSAGES = "/getPostMessagesById";
	public static final String RE_POST_MESSAGES = "/rePostMessages";
	public static final String GET_TRANSFORM = "/getTransform";
	public static final String POST_LIKE = "/postLike";
	public static final String GET_LIKE = "/getLike/{likeStatus}";
	public static final String GET_SHARE = "/getShare";
	public static final String GET_SHARE_BY_DATE = "/getShareByDate/{post_date}";

	public static final String GET_CONTACT_GROUPS = "/getContactsAndGroups";
	public static final String DRUG_SEARCH ="/getDrugSearchInfo";
	public static final String MEDICINE_ALTERNATIVES ="/medicineAlternatives";

	public static final String GET_NEWS_UPDATES = "/getNewsAndUpdates";
	public static final String GET_TRANSFORM_BY_CATEGORY = "/getNewsAndUpdates";
	public static final String DRUG_MEDICINE_SEARCH = "/medicineSearch";
	public static final String DRUG_MEDICINE_SUGGESTIONS = "/medicineSuggestions";
	public static final String DRUG_MEDICINE_DETAILS = "/medicineDetails";

	public static final String CREATE_TOPIC = "/createTopic";
	public static final String UPDATE_TOPIC_DETAILS = "/updateTopicDetails";
	public static final String GET_SHARE_BY_TOPIC = "/getShareByTopic/{topicId}";
	public static final String POST_SHARE="/postShare";
	public static final String GET_MESSAGE_BY_ID="/getMessagesById";
	public static final String GET_PENDING_ITEMS="/getPendingItems";

	@RequestMapping(value = DoctorPlusController.GET_MY_CONNECTIONS, method = RequestMethod.GET)
	public @ResponseBody List<DoctorInvitation> getMyConnections() {
		logger.info("Request received for login");
		List<DoctorInvitation> connections = new ArrayList<DoctorInvitation>();
		UserSecurityContext context = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
			context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (context != null) {
				try {

					String loginId = context.getUsername();
					connections = doctorPlusService.getConnectionsForDoctor(loginId, "Accepted");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return connections;
	}

	@RequestMapping(value = DoctorPlusController.UPDATE_INVITATION, method = RequestMethod.POST)
	public @ResponseBody AppResponse updateInvitation(@RequestBody DoctorInvitation doctorInvitation) {
		logger.info("Request received for update Notfication");
		AppResponse response = new AppResponse();

		try {

			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					doctorPlusService.updateDoctorInvitation(doctorInvitation, loginId);
					response.setMessage("Success");
					response.setStatus("OK");

				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while updating Invitaion");
			response.setStatus("Error");

		}

		return response;
	}

	@RequestMapping(value = DoctorPlusController.CREATE_INVITATION, method = RequestMethod.POST)
	public @ResponseBody AppResponse createInvitation(@RequestBody DoctorInvitation doctorInvitation) {
		logger.info("Request received for create Invitation");
		AppResponse response = new AppResponse();

		try {

			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					doctorPlusService.createDoctorInvitation(doctorInvitation, loginId);
					response.setMessage("Success");
					response.setStatus("OK");

				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Creating Invitaion");
			response.setStatus("Error");

		}

		return response;
	}

	@RequestMapping(value = DoctorPlusController.GET_MEDREP_DOCTORS, method = RequestMethod.GET)
	public @ResponseBody List<Doctor> getMedRepDoctors() {
		logger.info("Request received for login");
		List<Doctor> doctors = new ArrayList<Doctor>();
		UserSecurityContext context = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
			context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (context != null) {
				try {

					String loginId = context.getUsername();
					doctors = doctorPlusService.findAllActiveDoctors(loginId);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return doctors;
	}

	/**
	 * API for get all contacts
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_MEDREP_CONTACTS, method = RequestMethod.GET)
	public @ResponseBody List<Doctor> getAllContacts() {
		logger.info("Request received for Contacts");
		List<Doctor> doctors = new ArrayList<Doctor>();
		UserSecurityContext context = null;

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
			context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (context != null) {
				try {
					String loginId = context.getUsername();
					doctors = doctorPlusService.findAllDoctorsContacts(loginId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return doctors;
	}

	/**
	 * API for get all contacts by search
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.SEARCH_CONTACTS, method = RequestMethod.GET)
	public @ResponseBody List<Doctor> getDocContactBySearch(@RequestParam("name") String name,@RequestParam(required=false,defaultValue="0")int groupId) {
		logger.info("Request received for Contacts");
		List<Doctor> doctors = new ArrayList<Doctor>();
		UserSecurityContext context = null;

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
			context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (context != null) {
				try {
					String loginId = context.getUsername();
					doctors = doctorPlusService.findDoctorContactBySearch(loginId, name,groupId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return doctors;
	}

	/**
	 * API for create the Group
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.CREATE_GROUP, method = RequestMethod.POST)
	public @ResponseBody AppResponse createGroup(@RequestBody Group group,Model model) {

		logger.info("Request received for create Invitation");
		model.addAttribute("group", group);
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					model.addAttribute("loginId",loginId);

					int result = doctorPlusService.createGroup(model);
					if (result > 0) {
						Member member = new Member();
						member.setGroup_id(result);
						member.setIs_admin(true);
						member.setStatus("ACTIVE");
						doctorPlusService.createMemberInGroup(member, loginId, 1);
						response.setMessage("Success");
						response.setStatus("OK");
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Creating Group");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for fetch all the Groups of doctor
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_GROUP, method = RequestMethod.GET)
	public @ResponseBody Object getAllGroupByDoc() {
		logger.info("Request received for get group");
		Object result =null;
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					result = doctorPlusService.getAllGroups(loginId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			 result = new AppResponse();
			((AppResponse)result).setStatus("error");
			((AppResponse)result).setMessage(e.getMessage());
			logger.info("Error while Getting Groups");
		}
		return result;
	}

	/**
	 * API for update the Member Status
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.UPDATE_MEMBER_STATUS, method = RequestMethod.POST)
	public @ResponseBody AppResponse updateGroupmemberStatus(@RequestBody Member member) {
		logger.info("Request received for Update Member Status");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					String result = doctorPlusService.updateGroupsMemberStatus(loginId, member);
					if (result.equalsIgnoreCase("SUCCESS")) {
						response.setMessage("Success");
						response.setStatus("OK");
						String message="";
						if(member.getStatus()!=null && (member.getStatus().equals("ACTIVE")||member.getStatus().equals("EXIT")))
							notificationService.push(member.getStatus().equals("ACTIVE")?"ACCEPTED":"REJECTED", member.getMember_id());
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Creating Group");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for fetch all the Member Details of Group
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.FETCH_GROUP_MEMBERS, method = RequestMethod.GET)
	public @ResponseBody List<Group> fetchAllGroupMembers() {
		logger.info("Request received for Update Member Status");
		List<Group> response = new ArrayList<Group>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();

					response = doctorPlusService.fetchMembersOfGroup(loginId);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping(value = DoctorPlusController.GET_USER_PREFERENCES, method = RequestMethod.GET)
	public @ResponseBody List<UserDetails> getUserDetails() {
		logger.info("Request received for Contacts");
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		UserSecurityContext context = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
			context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (context != null) {
				try {
					String loginId = context.getUsername();
					userDetails = doctorPlusService.getUserDetails(loginId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return userDetails;
	}

	@RequestMapping(value = DoctorPlusController.GET_ALL_CONTACTS_BY_CITY, method = RequestMethod.GET)
	public @ResponseBody List<UserDetails> getAllMyContacts(@RequestParam(required=false,defaultValue="0") int groupId) {
		logger.info("Request received for Contacts");
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		UserSecurityContext context = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
			context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (context != null) {
				try {
					String loginId = context.getUsername();
					userDetails = doctorPlusService.getUserDetailsByCity(loginId,groupId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return userDetails;
	}

	@RequestMapping(value = DoctorPlusController.GET_MY_CONTACT_LIST, method = RequestMethod.GET)
	public @ResponseBody AppResponse getMyContactList() {
		AppResponse response=new AppResponse();
		logger.info("Request received for Contacts");
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		UserSecurityContext context = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
			context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (context != null) {
				try {
					String loginId = context.getUsername();
					response.setStatus("success");
					response.setResult(doctorPlusService.getMyContactList(loginId));
				} catch (Exception e) {
					e.printStackTrace();
					response.setStatus("error");
					response.setMessage(e.getMessage());
				}
			}
		}
		return response;
	}

	@RequestMapping(value = DoctorPlusController.GET_SUGGESTED_CONTACTS, method = RequestMethod.GET)
	public @ResponseBody List<UserDetails> getSuggestedContacts() {
		logger.info("Request received for Contacts");
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		UserSecurityContext context = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
			context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (context != null) {
				try {
					String loginId = context.getUsername();
					userDetails = doctorPlusService.getSuggestedContacts(loginId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return userDetails;
	}

	@RequestMapping(value = DoctorPlusController.GET_PENDING_CONNECTIONS, method = RequestMethod.GET)
	public @ResponseBody List<UserDetails> getPendingContacts() {
		logger.info("Request received for Contacts");
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		UserSecurityContext context = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
			context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (context != null) {
				try {
					String loginId = context.getUsername();
					userDetails = doctorPlusService.getPendingContacts(loginId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return userDetails;
	}

	/**
	 * API for AddContacts
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.ADD_CONTACTS, method = RequestMethod.POST)
	public @ResponseBody AppResponse addContacts(@RequestBody Contacts contacts) {

		logger.info("Request received for create Invitation");
		AppResponse response = new AppResponse();
		int result = 0;
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					for (Integer connId : contacts.getConnIdList()) {
						contacts.setConnID(connId);
						contacts.setStatus("PENDING");
						result = doctorPlusService.addContacts(contacts, loginId);
						result = 1;
					}
					if (result > 0) {
						response.setMessage("Success");
						response.setStatus("OK");
						for (Integer connId : contacts.getConnIdList()) {
							Doctor doctor=doctorPlusService.getDoctor(loginId);
							notificationService.push(doctor.getFirstName()+" has requested you to add in his contacts.Please Accept or Reject", connId);
						}
					}
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

	/**
	 * Method for add member in group
	 *
	 * @param member
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.ADD_MEMBER, method = RequestMethod.POST)
	public @ResponseBody AppResponse addMemberInGroup(@RequestBody Member member) {

		logger.info("Request received for addMemberInGroup");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					for (Integer memId : member.getMemberList()) {
						member.setMember_id(memId);
						doctorPlusService.createMemberInGroup(member, loginId, 0);
					}
					response.setMessage("Success");
					response.setStatus("OK");
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

	/**
	 * API for delete the group using Group ID
	 *
	 * @param group
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.DELETE_GROUP, method = RequestMethod.POST)
	public @ResponseBody AppResponse deleteGroup(@RequestBody Group group) {

		logger.info("Request received for deleteGroup");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					doctorPlusService.deleteGroup(loginId, group);
					response.setMessage("Success");
					response.setStatus("OK");
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Deleting Group");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for delete members from the group
	 *
	 * @param member
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.REMOVE_MEMBER, method = RequestMethod.POST)
	public @ResponseBody AppResponse removeMembersInGroup(@RequestBody Member member) {

		logger.info("Request received for removeMembersInGroup");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();

					doctorPlusService.removeMembersFromGroup(member, loginId);
					response.setMessage("Success");
					response.setStatus("OK");
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Removings Member");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for update group
	 *
	 * @param member
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.UPDATE_GROUP, method = RequestMethod.POST)
	public @ResponseBody AppResponse updateGroupData(@RequestBody Group group,Model model) {
		logger.info("Request received for Update Member Status");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					model.addAttribute("loginId", loginId);
					model.addAttribute("group", group);
					String result = doctorPlusService.updateGroupData(model);
					if (result.equalsIgnoreCase("SUCCESS")) {
						response.setMessage("Success");
						response.setStatus("OK");
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Creating Group");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for GetGroupMembers
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_GROUP_MEMBERS, method = RequestMethod.GET)
	public @ResponseBody List<Group> getGroupMembers(@PathVariable("groupId") int groupId) {
		logger.info("Request received for Update Member Status");
		List<Group> response = new ArrayList<Group>();

		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					response = doctorPlusService.fetchMembersOfGroup(loginId, groupId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * API for fetch Pending groups of doctor
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_PENDING_GROUPS, method = RequestMethod.GET)
	public @ResponseBody List<Group> getPendingGroupOfMember() {
		logger.info("Request received for Update Member Status");
		List<Group> response = new ArrayList<Group>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					response = doctorPlusService.fetchPendingGroups(loginId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * API for fetch Pending members of the group
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_PENDING_MEMBES_GROUP, method = RequestMethod.GET)
	public @ResponseBody List<Member> getPendingMembersGroup(@PathVariable("groupId") int groupId) {
		logger.info("Request received for Update Member Status");
		List<Member> response = new ArrayList<Member>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					response = doctorPlusService.fetchPendingMembers(loginId, groupId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * API for Invite Contacts
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.INVITE_CONTACTS, method = RequestMethod.POST)
	public @ResponseBody AppResponse inviteContacts(@RequestBody InviteContacts inviteContacts) {
		logger.info("Request received for Update Member Status");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					String result = doctorPlusService.inviteContacts(loginId, inviteContacts);
					if (result.equalsIgnoreCase("SUCCESS")) {
						response.setMessage("Success");
						response.setStatus("OK");
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}
		} catch (Exception e) {
			response.setMessage("Error while Inviting Contact");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for fetch Pending members of the group
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_SUGGESTED_GROUP, method = RequestMethod.GET)
	public @ResponseBody List<Group> getSuggestedGroup() {
		logger.info("Request received for Fetch Suggested Groups");
		List<Group> response = new ArrayList<Group>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					response = doctorPlusService.fetchSuggestedGroups(loginId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 *
	 * API for set Connection Status
	 *
	 * @param member
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.UPDATE_CONNECTION_STATUS, method = RequestMethod.POST)
	public @ResponseBody AppResponse updateContactStatus(@RequestBody ConnectionDetails connectionDetails) {

		logger.info("Request received for addMemberInGroup");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					for (Integer connId : connectionDetails.getConnList()) {
						connectionDetails.setConnectionId(connId);
						doctorPlusService.updateConnectionStatus(connectionDetails, loginId);
					}
					if(connectionDetails.getStatus().equalsIgnoreCase("ACTIVE")){
						for (Integer connId : connectionDetails.getConnList()) {
							connectionDetails.setDoctorId(connId);
							doctorPlusService.updatePendingConnectionStatus(connectionDetails, loginId);
						}
					}
					response.setMessage("Success");
					response.setStatus("OK");
					if(connectionDetails.getStatus()!=null && (connectionDetails.getStatus().equals("ACTIVE")|| connectionDetails.getStatus().equals("EXIT"))){

						String message="ACTIVE".equals(connectionDetails.getStatus())?" has accepted your request.":(("EXIT".equals(connectionDetails.getStatus())|| "REJECT".equals(connectionDetails.getStatus()))?" has rejected your request.":"");
					for(Integer connId:connectionDetails.getConnList()){
						Doctor doctor=doctorPlusService.getDoctor(loginId);
						notificationService.push(connectionDetails.getStatus().equals("ACTIVE")?doctor.getFirstName()+" has accepted your request.":doctor.getFirstName()+"has rejected your request.", connId);
					}
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while updating Contact");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for postMessages
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.POST_MESSAGES, method = RequestMethod.POST)
	public @ResponseBody AppResponse postMessages(@RequestBody PostMessage postMessage,Model model) {

		logger.info("Request received for post messages");
		AppResponse response = new AppResponse();
		model.addAttribute("postMessage", postMessage);
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					model.addAttribute("loginId", loginId);
					int result = doctorPlusService.createMessages(model);
					if (result > 0) {
						response.setMessage("Success");
						response.setStatus("OK");
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Creating Group");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for fetch post messages
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_POST_MESSAGES, method = RequestMethod.GET)
	public @ResponseBody List<PostMessage> getPostMessages() {
		logger.info("Request received for fetch post messages");
		List<PostMessage> response = new ArrayList<PostMessage>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					response = doctorPlusService.fetchPostedMessage(loginId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * API for repost messages
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.RE_POST_MESSAGES, method = RequestMethod.POST)
	public @ResponseBody AppResponse rePostMessages(@RequestBody Comments comment) {

		logger.info("Request received for repost messages");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();

					int result = doctorPlusService.reCommentMessages(comment, loginId);
					if (result > 0) {
						response.setMessage("Success");
						response.setStatus("OK");
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Creating Group");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for fetch all the Groups
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.MORE_GROUP, method = RequestMethod.GET)
	public @ResponseBody List<Group> getMoreGroups() {
		logger.info("Request received for get more groups");
		List<Group> result = new ArrayList<Group>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					result = doctorPlusService.getMoreGroups(loginId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ArrayList<Group>();
			logger.info("Error while Getting Groups");
		}
		return result;
	}

	/**
	 * Method for add member in group
	 *
	 * @param member
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.JOIN_GROUP, method = RequestMethod.POST)
	public @ResponseBody AppResponse joinGroup(@RequestBody Member member) {

		logger.info("Request received for addMemberInGroup");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					for (Integer grpId : member.getGroupList()) {
						member.setGroup_id(grpId);
						doctorPlusService.createMemberInGroup(member, loginId, 0);
					}
					response.setMessage("Success");
					response.setStatus("OK");
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Adding Member");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * Method for exit member from group
	 *
	 * @param member
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.EXIT_GROUP, method = RequestMethod.POST)
	public @ResponseBody AppResponse exitGroup(@RequestBody Member member) {

		logger.info("Request received for addMemberInGroup");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					doctorPlusService.exitMemberfromGroup(member, loginId);
					response.setMessage("Success");
					response.setStatus("OK");
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Adding Member");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for delete the contact
	 *
	 * @param group
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.DELETE_CONTACT, method = RequestMethod.POST)
	public @ResponseBody AppResponse deleteContact(@RequestBody Contacts contacts) {

		logger.info("Request received for deleteGroup");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					doctorPlusService.deleteContact(loginId, contacts);
					response.setMessage("Success");
					response.setStatus("OK");
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Deleting Group");
			response.setStatus("Error");
		}

		return response;
	}


	/**
	 * API for register the token
	 *
	 * @param group
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.REGISTER_DEVICE_TOKEN, method = RequestMethod.POST)
	public @ResponseBody AppResponse registerDeviceToken(@RequestBody RegisterDeviceToken regDeviceToken) {

		logger.info("Request received for registerToken");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					doctorPlusService.registerDeviceToken(loginId, regDeviceToken);
					response.setMessage("Success");
					response.setStatus("OK");
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while Deleting Group");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for getTransform details
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_TRANSFORM, method = RequestMethod.GET)
	public @ResponseBody List<Transform> getTransformDetails() {
		logger.info("Request received for fetch transform details");
		List<Transform> response = new ArrayList<Transform>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					response = doctorPlusService.fetchTransDetails();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * API for repost messages
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.POST_LIKE, method = RequestMethod.POST)
	public @ResponseBody AppResponse postLike(@RequestBody Likes likes) {

		logger.info("Request received for post like message");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					int result = doctorPlusService.postLIke(likes, loginId);
					if (result > 0) {
						response.setMessage("Success");
						response.setStatus("OK");
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while post like");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for get likes
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_LIKE, method = RequestMethod.GET)
	public @ResponseBody List<Likes> getLike(@PathVariable("likeStatus") String likeStatus) {
		logger.info("Request received for get more groups");
		List<Likes> result = new ArrayList<Likes>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					result = doctorPlusService.getLikeDetails(loginId,likeStatus);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ArrayList<Likes>();
			logger.info("Error while get like");
		}
		return result;
	}

	/**
	 * API for push notification
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.PUSH_NOTIFICATION, method = RequestMethod.POST)
	public @ResponseBody AppResponse pushNotification() {
		logger.info("Request received for post like message");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					final String loginId = context.getUsername();
					Runnable runnableNotification = new Runnable() {
						@Override
						public void run() {
							notificationService.pushNotification();
						}
					};

					Runnable runnableSurvey = new Runnable() {
						@Override
						public void run() {
							surveyService.pushSurvey();
						}
					};
					Thread thread = new Thread(runnableNotification);
					Thread threadSurvey = new Thread(runnableSurvey);
					thread.start();
					threadSurvey.start();

					response.setMessage("Success");
					response.setStatus("OK");
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while post like");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for fetch share details
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_SHARE, method = RequestMethod.GET)
	public @ResponseBody List<ShareDetails> getShareInfo() {
		logger.info("Request received for get share");
		List<ShareDetails> response = new ArrayList<ShareDetails>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					response = doctorPlusService.fetchTopicDetails(loginId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * API for fetch share details By Date yyyy-mm-dd
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_SHARE_BY_DATE, method = RequestMethod.GET)
//	public @ResponseBody List<PostMessage> getShareInfoByDate(@PathVariable("post_date") @DateTimeFormat(iso=ISO.DATE) Date post_date) {
	public @ResponseBody List<PostMessage> getShareInfoByDate(@PathVariable("post_date") String post_date) {
		logger.info("Request received for get share");
		List<PostMessage> response = new ArrayList<PostMessage>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					if(post_date != null){
						SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				        Date date = dt.parse(post_date);
				        String loginId = context.getUsername();
						response = doctorPlusService.fetchShareDetailsByDate(loginId,date);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * API for fetch contacts and groups
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_CONTACT_GROUPS, method = RequestMethod.GET)
	public @ResponseBody Map getContactsAndGroups() {
		logger.info("Request received for getContactsAndGroups");
		Map response = new HashMap();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					response = doctorPlusService.getContactsAndGroups(loginId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * API for fetch News
	 *
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = DoctorPlusController.GET_NEWS_UPDATES, method = RequestMethod.GET)
	public @ResponseBody Object getNewsAndUpdates(@RequestParam(required=false) String category,@RequestParam(required=false) Long timestamp,Model model) {
		logger.info("Request received for getContactsAndGroups");
		List response = new ArrayList();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					if(category!=null &&category.trim().length()!=0){
									Map result=(Map) cachingService.get(category);
									if(Util.isEmpty(result)){
										result=new HashMap();
										List<Integer> id = newsService.getTransformCategoryId(category);
										result.put("transforms",newsService.getAllTransformsById(id, category,timestamp));
										result.put("subCategories", newsService.getAllSubCategories(category));
										cachingService.put(category, result);
									}

								return result;
					}else{

						response=(List) cachingService.get("newsList");
						if(Util.isEmpty(response)){
							 response=newsService.getAllNews(timestamp);
//							response=new ArrayList();
//							for (News news : newsList) {
//								News newObj = newsService.getnewsSourceById(news);
//								response.add(newObj);
//							}
							cachingService.put("newsList", response);
						}

					}
		}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Deprecated
	@RequestMapping(value = DoctorPlusController.DRUG_SEARCH, method = RequestMethod.GET)
	public @ResponseBody Object getDrugSearch(@RequestParam(required=false) String callback, @RequestParam String id, @RequestParam int limit) {
		Object response=null;

		try {
					response = doctorPlusService.fetchDrugDetails(callback, id, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Deprecated
	//@RequestMapping(value = DoctorPlusController.MEDICINE_ALTERNATIVES, method = RequestMethod.GET)
	public @ResponseBody Object medicinealternativesold(@RequestParam String id, @RequestParam int limit) {
		Object response=null;

		try {
					response = doctorPlusService.medicineAlternatives(id, limit);
		} catch (Exception e) {
			response=new AppResponse();
			((AppResponse)response).setStatus("error");
			((AppResponse)response).setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@Deprecated
//	@RequestMapping(value = DoctorPlusController.DRUG_MEDICINE_SEARCH, method = RequestMethod.GET)
	public @ResponseBody Object medicineSearchOld(@RequestParam String id, @RequestParam int limit) {
		Object response=null;

		try {
			response = doctorPlusService.fetchMedicineDetails(id, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping(value = DoctorPlusController.DRUG_MEDICINE_SEARCH, method = RequestMethod.GET)
	public @ResponseBody Object medicineSearch(@RequestParam String searchTerm) {
		Object response=null;

		try {
			response = doctorPlusService.medicineSearch(searchTerm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping(value = DoctorPlusController.MEDICINE_ALTERNATIVES, method = RequestMethod.GET)
	public @ResponseBody Object medicineAlternatives(@RequestParam String medicine, @RequestParam(defaultValue="10",required=false) int limit,@RequestParam(defaultValue="1",required=false) int page) {
		Object response=null;

		try {
					response = doctorPlusService.medicineAlternatives(medicine, limit,page);
		} catch (Exception e) {
			response=new AppResponse();
			((AppResponse)response).setStatus("error");
			((AppResponse)response).setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping(value = DoctorPlusController.DRUG_MEDICINE_SUGGESTIONS, method = RequestMethod.GET)
	public @ResponseBody Object medicineSuggestions(@RequestParam String id, @RequestParam int limit) {
		List<String> response=new ArrayList<String>();
		try {
			response = doctorPlusService.fetchMedicineSuggestions(id, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping(value = DoctorPlusController.DRUG_MEDICINE_DETAILS, method = RequestMethod.GET)
	public @ResponseBody Object medicineDetails(@RequestParam String id, @RequestParam int limit) {
		Object response=null;
		try {
			response = doctorPlusService.getMedicineDetails(id, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}


	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.POST, value = "/images/upload")
	public Map<String,String> upload(@RequestParam("file") MultipartFile file , @RequestParam("groupId") Integer groupId,Model model)
			throws IOException {

		Map result=null;
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("file",file);
					model.addAttribute("groupId",groupId);
					result=doctorPlusService.updateGroup(model);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/getGroupImage/{groupId}", method = RequestMethod.GET)
	@ResponseBody
	public Map viewImage(@PathVariable("groupId") Integer groupId, HttpServletResponse response) throws IOException {

		Map result=new HashMap<String,Object>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					Group group = doctorPlusService.getGroup(groupId);

					if (group != null) {
						result.put("status", "success");
						result.put("group", group);
					}else{
						result.put("status", "failure");
						result.put("message", "Group Id Not Found");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Scheduled(fixedDelay=24*60*60*1000)
	@RequestMapping("/cache/clear")
	public void clear(){
		cachingService.clear();
	}

	@Scheduled(cron="0 0 10 * * ?")
	@RequestMapping("/loadNews")
	@ResponseBody
	public void pushRssNews() {
		logger.info("Request received for medicine details");
		try {
			List<String> rssFeedLinkList = new ArrayList<String>();
			rssFeedLinkList.add("https://www.medtechintelligence.com/category/combination-products/combination-products/feed/");
			rssFeedLinkList.add("https://www.medtechintelligence.com/category/market-access/market-access/feed/");
			rssFeedLinkList.add("https://www.medtechintelligence.com/feed/");
			rssFeedLinkList.add("https://www.medtechintelligence.com/category/operations/operations/feed/");
			rssFeedLinkList.add("https://www.medtechintelligence.com/category/business-analysis/business-analysis/feed/");

			Integer result = newsService.deleteAllNews();
			if(result > 0){
				List<NewsSource> list = newsService.getAllNewsSources();
				if(list != null && list.size() > 0){
					for(NewsSource link : list){
						List<News> newsList = RssFeedReader.readNewsXml(link);

						for(int i=0;newsList!=null &&i<50&& i<newsList.size();i++){
							doctorPlusService.pushNews(newsList.get(i));
						}
//						if(newsList.size() > 0){
//							for (News news : newsList) {
//								doctorPlusService.pushNews(news);
//							}
//						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Scheduled(cron="0 0 11 * * ?")
	@RequestMapping("/loadTransforms")
	@ResponseBody
	public void pushRssTransforms() {
		logger.info("Request received for medicine details");
		try {

			Integer result = newsService.deleteAllTransform();
			if(result > 0){
				List<TransformCategory> list = newsService.getAllTransformSources();
				if(list != null && list.size() > 0){
					for(TransformCategory link : list){
						List<TransformModel> newsList = RssFeedReader.readTransformXml(link);
						for(int i=0;newsList!=null&&i<newsList.size()&&i<50;i++){
							doctorPlusService.pushTransform(newsList.get(i));
						}
//						if(newsList.size() > 0){
//							for (TransformModel news : newsList) {
//								doctorPlusService.pushTransform(news);
//							}
//						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = DoctorPlusController.CREATE_TOPIC, method = RequestMethod.POST)
	public @ResponseBody AppResponse createTopic(@RequestBody ShareDetails shareDetails) {

		logger.info("Request received for post like message");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					int result = doctorPlusService.createPost(shareDetails, loginId);
					if (result > 0) {
						response.setMessage("Success");
						response.setStatus("OK");
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while post like");
			response.setStatus("Error");
		}

		return response;
	}

	@RequestMapping(value = DoctorPlusController.UPDATE_TOPIC_DETAILS, method = RequestMethod.POST)
	public @ResponseBody AppResponse updateTopic(@RequestBody ShareDetails shareDetails,Model model) {

		logger.info("Request received for post like message");
		AppResponse response = new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					int result = 0;
					if(shareDetails.getLikes_count() > 0){
						Likes likes = new Likes();
						likes.setLike_status(shareDetails.getLikes().getLike_status());
						likes.setMessage_id(shareDetails.getId());
						result = doctorPlusService.postLIke(likes, loginId);
					}
					if(shareDetails.getComment_count() > 0){
						Comments comments = new Comments();
						comments.setGroup_id(shareDetails.getComments().getGroup_id());
						comments.setMessage(shareDetails.getComments().getMessage());
						comments.setMessage_type(shareDetails.getComments().getMessage_type());
						comments.setMessage_id(shareDetails.getId());
						result = doctorPlusService.reCommentMessages(comments, loginId);
					}
					if(shareDetails.getShare_count() > 0){
						PostMessage messagesShare = new PostMessage();
						messagesShare.setGroup_id(shareDetails.getPostMessage().getGroup_id());
						messagesShare.setMessage(shareDetails.getPostMessage().getMessage());
						messagesShare.setMessage_type(shareDetails.getPostMessage().getMessage_type());
						messagesShare.setReceiver_id(shareDetails.getPostMessage().getReceiver_id());
						messagesShare.setTopic_id(shareDetails.getId());
						model.addAttribute("loginId",loginId);
						model.addAttribute("postMessage",messagesShare);
						result = doctorPlusService.createMessages(model);
					}

					if(result > 0){
						result = doctorPlusService.updateLikesCommentsShareOfTopic(shareDetails, loginId);
						if (result > 0) {
							response.setMessage("Success");
							response.setStatus("OK");
						}
					}
				}
			} else {
				response.setMessage("Invalid token, can not get user profile.");
				response.setStatus("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Error while post like");
			response.setStatus("Error");
		}

		return response;
	}

	/**
	 * API for fetch share details
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_SHARE_BY_TOPIC, method = RequestMethod.GET)
	public @ResponseBody List<PostMessage> getShareByTopic(@PathVariable("topicId") Integer topicId) {
		logger.info("Request received for get share");
		List<PostMessage> response = new ArrayList<PostMessage>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					response = doctorPlusService.fetchShareByTopic(topicId,loginId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}


	/**
	 * API for post share details
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.POST_SHARE, method = RequestMethod.POST)
	public @ResponseBody AppResponse postShare(@RequestBody  ShareDetails shareDetails,Authentication activeUser) {
		AppResponse response=new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
					 if(doctorPlusService.postShare(shareDetails,loginId))
						 response.setStatus("success");
					 else
						 response.setStatus("error");

				}
			}
		} catch (Exception e) {
			response.setStatus("error");
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * API for fetch post messages
	 *
	 * @return
	 */
	@RequestMapping(value = DoctorPlusController.GET_MESSAGE_BY_ID, method = RequestMethod.GET)
	public @ResponseBody List<ShareDetails> getPostMessagesByMemberGroup(@RequestParam Integer memberId, @RequestParam Integer groupId) {
		logger.info("Request received for fetch post messages");
		List<ShareDetails> response = new ArrayList<ShareDetails>();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					String loginId = context.getUsername();
//					response = doctorPlusService.fetchPostedMessage(loginId);
					if(memberId > 0){
						response = doctorPlusService.findByMemberId(loginId,memberId);
					}
					if(groupId > 0){
						response = doctorPlusService.findByGroupId(groupId,loginId);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Not Using
	 * API for fetch post messages
	 *
	 * @return
	 */
	@RequestMapping(value = "/GetShareByTopicID", method = RequestMethod.GET)
	public @ResponseBody AppResponse getShareByTopicId(@RequestParam Integer topicId,Model model) {
		logger.info("Request received for fetch post messages");
		AppResponse response=new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("topicId",topicId);
					doctorPlusService.getShareBytopicId(model);
					response.setStatus("success");
					response.setResult(model.asMap().get("shareDetails"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResult("error");
			response.setMessage(e.getMessage());
		}

		return response;
	}


	/**
	 * API for fetch post messages
	 *
	 * @return
	 */
	@RequestMapping(value = "/myWall", method = RequestMethod.GET)
	public @ResponseBody AppResponse myWall(Model model,@RequestParam(required=false) Long timeStamp) {
		AppResponse response=new AppResponse();
		try {
			UserSecurityContext context = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("loginId",context.getUsername());
					model.addAttribute("timeStamp",new Date(timeStamp));
					doctorPlusService.myWall(model);
					response.setStatus("success");
					response.setResult(model.asMap().get("shareDetails"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResult("error");
			response.setMessage(e.getMessage());
		}

		return response;
	}

	/**
	 *
	 * @param model
	 * @param connectionId
	 * @return
	 */
	@RequestMapping(value = "/getContact/{doctorId}", method = RequestMethod.GET)
	public @ResponseBody AppResponse getContact(Model model,@PathVariable Integer doctorId) {
		AppResponse response=new AppResponse();
		try {
			UserSecurityContext context = null;

			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username",context.getUsername());
					model.addAttribute("connectionId",doctorId);
					doctorPlusService.getContact(model);
					response.setStatus("success");
					response.setResult(model.asMap().get("result"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResult("error");
			response.setMessage(e.getMessage());
		}

		return response;
	}

	/**
	 *
	 * @param model
	 * @param connectionId
	 * @return
	 */
	@RequestMapping(value = "/getGroup/{groupId}", method = RequestMethod.GET)
	public @ResponseBody AppResponse getGroup(Model model,@PathVariable Integer groupId) {
		AppResponse response=new AppResponse();
		try {
			UserSecurityContext context = null;

			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username",context.getUsername());
					model.addAttribute("groupId",groupId);
					doctorPlusService.getGroup(model);
					response.setStatus("success");
					response.setResult(model.asMap().get("result"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResult("error");
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = DoctorPlusController.GET_PENDING_ITEMS, method = RequestMethod.POST)
	public @ResponseBody AppResponse getPendingItems(Model model,@RequestBody(required=false) PendingCount pendingCount) {
		AppResponse response=new AppResponse();
		try {
			UserSecurityContext context = null;

			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserSecurityContext) {
				context = (UserSecurityContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (context != null) {
					model.addAttribute("username",context.getUsername());
					model.addAttribute("pendingCount",pendingCount);

					doctorPlusService.getPendingItems(model);

					response.setStatus("success");
					response.setResult( model.asMap().get("result"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResult("error");
			response.setMessage(e.getMessage());
		}

		return response;
	}
}