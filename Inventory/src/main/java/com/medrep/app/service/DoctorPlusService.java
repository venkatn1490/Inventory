package com.medrep.app.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.medrep.app.constants.Status;
import com.medrep.app.dao.AboutDao;
import com.medrep.app.dao.CommentDAO;
import com.medrep.app.dao.ConnectionDAO;
import com.medrep.app.dao.DeviceTokenDAO;
import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorNotificationDAO;
import com.medrep.app.dao.DoctorPlusDAO;
import com.medrep.app.dao.GroupDAO;
import com.medrep.app.dao.LikesDAO;
import com.medrep.app.dao.LocationDAO;
import com.medrep.app.dao.MemberDAO;
import com.medrep.app.dao.MessageDAO;
import com.medrep.app.dao.NewsDAO;
import com.medrep.app.dao.PatientDAO;
import com.medrep.app.dao.PendingCountDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.PharmaRepNotificationDAO;
import com.medrep.app.dao.PharmaRepSurveyDAO;
import com.medrep.app.dao.PostTypeDao;
import com.medrep.app.dao.ShareDetailsDAO;
import com.medrep.app.dao.SurveyDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.TransfromDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.dao.UserSecurityDAO;
import com.medrep.app.entity.CommentEntity;
import com.medrep.app.entity.ConnectionEntity;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorInvitationEntity;
import com.medrep.app.entity.GroupEntity;
import com.medrep.app.entity.LikeEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.MemberEntity;
import com.medrep.app.entity.MessageEntity;
import com.medrep.app.entity.NewsEntity;
import com.medrep.app.entity.PatientEntity;
import com.medrep.app.entity.PendingCountsEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.PostTypeEntity;
import com.medrep.app.entity.ShareDetailsEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.TransformEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.Comments;
import com.medrep.app.model.ConnectionDetails;
import com.medrep.app.model.Contacts;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorInvitation;
import com.medrep.app.model.Group;
import com.medrep.app.model.InviteContacts;
import com.medrep.app.model.Likes;
import com.medrep.app.model.Location;
import com.medrep.app.model.Member;
import com.medrep.app.model.News;
import com.medrep.app.model.PendingCount;
import com.medrep.app.model.PostMessage;
import com.medrep.app.model.RegisterDeviceToken;
import com.medrep.app.model.SMS;
import com.medrep.app.model.ShareDetails;
import com.medrep.app.model.Transform;
import com.medrep.app.model.TransformModel;
import com.medrep.app.model.UserDetails;
import com.medrep.app.util.AndroidPushNotification;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.Util;


@Service("doctorPlusService")
@Transactional
public class DoctorPlusService {
	@Autowired
	DoctorService doctorService;

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	PatientDAO patientDAO;
	@Autowired
	DoctorPlusDAO doctorPlusDAO;

	@Autowired
	UserSecurityDAO securityDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	@Autowired
	ConnectionDAO connectionDAO;

	@Autowired
	GroupDAO groupDAO;

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	UserDAO userDAO;

	@Autowired
	MessageDAO messageDAO;

	@Autowired
	CommentDAO commentDAO;

	@Autowired
	SMSService smsService;

	@Autowired
	DeviceTokenDAO deviceTokenDAO;

	@Autowired
	LikesDAO likeDAO;

	@Autowired
	LocationDAO locationDAO;

	@Autowired
	NewsDAO newsDAO;

	@Autowired
	TransfromDAO transformDAO;

	@Autowired
	ShareDetailsDAO shareDetailsDAO;
	@Autowired
	DisplayPictureDAO displayPictureDAO;
	@Autowired
	PostTypeDao postTypeDao;
	@Autowired
	NotificationService notificationService;
	@Autowired
	AboutDao aboutDao;
	@Autowired
	SurveyDAO surveyDAO;
	@Autowired
	PharmaRepDAO pharmaRepDAO;
	@Autowired
	PharmaRepSurveyDAO pharmaRepSurveyDAO;
	@Autowired
	DoctorNotificationDAO doctorNotificationDAO;
	@Autowired
	PharmaRepNotificationDAO pharmaRepNotificationDAO;
	@Autowired
	PendingCountDAO pendingCountDAO;

	private static final Log log = LogFactory.getLog(DoctorPlusService.class);
	private static final String PENDING = "PENDING";
	private static final String ACTIVE = "ACTIVE";

	public List<DoctorInvitation> getConnectionsForDoctor(String loginId, String status) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);

		List<DoctorInvitationEntity> connectionEnityies = null;
		List<DoctorInvitation> connections = new ArrayList<DoctorInvitation>();
		try {
			System.out.println("DoctorId=" + doctorEntity.getDoctorId());
			connectionEnityies = doctorPlusDAO.findDoctorConnections(doctorEntity.getDoctorId(), status);

		} catch (Exception e) {
			System.out.println("Entry not found");
		}

		DoctorEntity doctorEntity1 = null;
		for (DoctorInvitationEntity doctorInvitationEntity : connectionEnityies) {
			DoctorInvitation doctorInvitation = new DoctorInvitation();
			if (doctorEntity.getDoctorId() != doctorInvitationEntity.getInvitedDoctorId()) {
				// doctorEntity1=doctorInvitationEntity.getInvitedDoctor();
				doctorEntity1 = doctorDAO.findById(DoctorEntity.class, doctorInvitationEntity.getInvitedDoctorId());
				doctorInvitation.setInvitationId(doctorInvitationEntity.getInvitationId());
				doctorInvitation.setInviteStatus(doctorInvitationEntity.getInviteStatus());
				doctorInvitation.setDoctorId(doctorInvitationEntity.getInvitedDoctorId());
				doctorInvitation.setDoctorName(Util.getTitle(doctorEntity1.getUser().getTitle())+
						doctorEntity1.getUser().getFirstName() + " " + doctorEntity1.getUser().getLastName());
				if (doctorEntity1.getTherapeuticId() != null) {
					TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
							.findById(TherapeuticAreaEntity.class, Integer.parseInt(doctorEntity1.getTherapeuticId()));
					if (therapeuticAreaEntity != null) {
						doctorInvitation.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
						doctorInvitation.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
					}
					connections.add(doctorInvitation);
				}
			} else if (doctorEntity.getDoctorId() != doctorInvitationEntity.getDoctorId()) {
				// doctorEntity1=doctorInvitationEntity.getDoctor();
				doctorEntity1 = doctorDAO.findById(DoctorEntity.class, doctorInvitationEntity.getDoctorId());
				doctorInvitation.setInvitationId(doctorInvitationEntity.getInvitationId());
				doctorInvitation.setInviteStatus(doctorInvitationEntity.getInviteStatus());
				doctorInvitation.setDoctorId(doctorInvitationEntity.getDoctorId());
				doctorInvitation.setDoctorName(Util.getTitle(doctorEntity1.getUser().getTitle())+
						doctorEntity1.getUser().getFirstName() + " " + doctorEntity1.getUser().getLastName());
				if (doctorEntity1.getTherapeuticId() != null) {
					TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
							.findById(TherapeuticAreaEntity.class, Integer.parseInt(doctorEntity1.getTherapeuticId()));
					if (therapeuticAreaEntity != null) {
						doctorInvitation.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
						doctorInvitation.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
					}
					connections.add(doctorInvitation);
				}
			}
		}
		return connections;
	}

	public void updateDoctorInvitation(DoctorInvitation doctorInvitation, String loginId) {

		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
			DoctorInvitationEntity doctorInvitationEntity = doctorPlusDAO
					.findByDoctorInvitation(doctorInvitation.getDoctorId(), doctorEntity.getDoctorId());

			if (doctorInvitationEntity != null && doctorInvitationEntity.getInvitationId() != null) {

				if (doctorInvitation.getAcceptedOn() != null && doctorInvitation.getAcceptedOn().length() == 14) {
					doctorInvitationEntity.setAcceptedOn(DateConvertor
							.convertStringToDate(doctorInvitation.getAcceptedOn(), DateConvertor.YYYYMMDDHHMISS));
				}

				if (doctorInvitation.getInviteStatus() != null) {
					doctorInvitationEntity.setInviteStatus(doctorInvitation.getInviteStatus());
				}

			}
			doctorPlusDAO.merge(doctorInvitationEntity);
		}
	}

	public void createDoctorInvitation(DoctorInvitation doctorInvitation, String loginId) {

		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
			DoctorInvitationEntity doctorInvitationEntity = doctorPlusDAO
					.findByDoctorInvitation(doctorEntity.getDoctorId(), doctorInvitation.getDoctorId());

			if (doctorInvitationEntity == null || doctorInvitationEntity.getInvitationId() == null) {
				doctorInvitationEntity = BeanUtils.instantiateClass(DoctorInvitationEntity.class);
				doctorInvitationEntity.setDoctorId(doctorEntity.getDoctorId());
				doctorInvitationEntity.setInvitedDoctorId(doctorInvitation.getDoctorId());
				if (doctorInvitation.getInvitedOn() != null && doctorInvitation.getInvitedOn().length() == 14) {
					doctorInvitationEntity.setInvitedOn(DateConvertor
							.convertStringToDate(doctorInvitation.getInvitedOn(), DateConvertor.YYYYMMDDHHMISS));
				}
				doctorInvitationEntity.setInviteStatus("PENDING");
				doctorPlusDAO.persist(doctorInvitationEntity);
			}
		}
	}

	public List<Doctor> findAllActiveDoctors(String loginId) {
		List<Doctor> doctors = new ArrayList<Doctor>();
		DoctorEntity doctorEntity1 = doctorDAO.findByLoginId(loginId);
		if (doctorEntity1 != null && doctorEntity1.getDoctorId() != null) {

			List<DoctorEntity> doctorEntities = doctorDAO
					.findAllActiveDoctorsExceptHimself(doctorEntity1.getDoctorId());
			for (DoctorEntity doctorEntity : doctorEntities) {
				List<DoctorInvitationEntity> instances = doctorPlusDAO
						.findDoctorAlreadyRegistered(doctorEntity1.getDoctorId(), doctorEntity.getDoctorId());
				if (instances != null && instances.size() > 0) {
					// Doctor already Connected
				} else {
					Doctor doctor = new Doctor();
					UserEntity user = doctorEntity.getUser();

					if (user != null) {
						doctor.setEmailId(user.getEmailId());
						doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//						doctor.setMiddleName(user.getMiddleName());
						doctor.setLastName(user.getLastName());
						doctor.setPhoneNo(user.getPhoneNo());
						doctor.setAlias(user.getAlias());
						doctor.setTitle(user.getTitle());
						doctor.setAlternateEmailId(user.getAlternateEmailId());
						doctor.setMobileNo(user.getMobileNo());
						doctor.setDoctorId(doctorEntity.getDoctorId());

						if (user!=null && user.getDisplayPicture() != null) {
							doctor.setdPicture(user.getDisplayPicture().getImageUrl());
						}
					}
					doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
					if (doctorEntity.getTherapeuticId() != null) {
						try {
							if (doctorEntity.getTherapeuticId() != null) {
								TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(
										TherapeuticAreaEntity.class, Integer.parseInt(doctorEntity.getTherapeuticId()));
								if (therapeuticAreaEntity != null) {
									doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
								}
							}
						} catch (Exception e) {
							// Ignore
						}
					}
					doctors.add(doctor);
				}
			}
		}

		return doctors;
	}

	/**
	 * Method for get all the contact list
	 *
	 * @param loginId
	 * @return
	 */
	public List<Doctor> findAllDoctorsContacts(String loginId) {
		List<Doctor> doctors = new ArrayList<Doctor>();
		DoctorEntity doctorEntity1 = doctorDAO.findByLoginId(loginId);
		List<DoctorEntity> doctorEntity = doctorDAO.findAllDoctorsExceptHimself(doctorEntity1.getDoctorId());
		if (doctorEntity != null && doctorEntity.size() > 0) {
			for (DoctorEntity docEn : doctorEntity) {
				Doctor doctor = new Doctor();
				UserEntity user = docEn.getUser();
				if (user != null) {
					doctor.setEmailId(user.getEmailId());
					doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//					doctor.setMiddleName(user.getMiddleName());
					doctor.setLastName(user.getLastName());
					doctor.setPhoneNo(user.getPhoneNo());
					doctor.setAlias(user.getAlias());
					doctor.setTitle(user.getTitle());
					doctor.setAlternateEmailId(user.getAlternateEmailId());
					doctor.setMobileNo(user.getMobileNo());
					doctor.setDoctorId(docEn.getDoctorId());

					if (user.getDisplayPicture() != null) {
						doctor.setdPicture(user.getDisplayPicture().getImageUrl());
					}
				}
				doctor.setTherapeuticId(docEn.getTherapeuticId());
				if (docEn.getTherapeuticId() != null) {
					try {
						if (docEn.getTherapeuticId() != null) {
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
									.findById(TherapeuticAreaEntity.class, Integer.parseInt(docEn.getTherapeuticId()));
							if (therapeuticAreaEntity != null) {
								doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
					} catch (Exception e) {
						// Ignore
					}
				}
				doctors.add(doctor);
			}
			List<ConnectionEntity> connInstances = connectionDAO.findByDocID(doctorEntity1.getDoctorId());
			if (connInstances.size() > 0) {
				for (ConnectionEntity connectionEntity : connInstances) {
					for (Doctor doc : doctors) {
						if (doc.getDoctorId().equals(connectionEntity.getDocID())) {
							doctors.remove(doc);
						}
					}
				}
			}
		}

		return doctors;
	}

	/**
	 * Method for create the group
	 *
	 * @param group
	 * @param loginId
	 */
	public int createGroup(Model model) throws Exception {

		Group group=(Group) model.asMap().get("group");
		String loginId=(String) model.asMap().get("loginId");

		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		int result = 0;
		try {
			if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
				GroupEntity groupEntity = new GroupEntity();
				groupEntity.setAdmin_id(doctorEntity.getDoctorId());


				if(group.getImgData()!=null){
					String _groupImage = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/groups/";
					_groupImage += FileUtil.copyBinaryData(group.getImgData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "groups",group.getFileName());
					groupEntity.setImageUrl(_groupImage);
				}
				/*MultipartFile groupImage=(MultipartFile) model.asMap().get("groupImage");
				  if (groupImage != null) {
					String _groupImage = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/groups/";
					_groupImage += FileUtil.copyImage(groupImage,
							MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "groups");
					groupEntity.setImageUrl(_groupImage);
				}*/
				groupEntity.setGroup_long_desc(group.getGroup_long_desc());
				groupEntity.setGroup_name(group.getGroup_name());
				groupEntity.setGroup_short_desc(group.getGroup_short_desc());

				groupDAO.persist(groupEntity);
				result = groupEntity.getGroup_id();
			}
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public void createMemberInGroup(Member memberObj, String memberID, int admin) throws Exception {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(memberID);
			MemberEntity member = memberDAO.findByDocAndConnId(memberObj.getGroup_id(), admin==1?doctorEntity.getDoctorId():memberObj.getMember_id());
			if(member!=null && member.getId()!=0 && Status.ACTIVE.equalsIgnoreCase(member.getStatus()))
				throw new Exception("You are already a member of this group.");
			member = new MemberEntity();
			List<GroupEntity> groups=groupDAO.findByGroupID(memberObj.getGroup_id());
			if(!Util.isEmpty(groups)){
				GroupEntity groupEntity=groups.get(0);
				if(groupEntity.getAdmin_id()==doctorEntity.getDoctorId()){
					memberObj.setStatus(Status.ACTIVE);
				}
			}
			if (admin == 1) {
				member.setGroup_id(memberObj.getGroup_id());
				member.setMember_id(doctorEntity.getDoctorId());
				member.setStatus(memberObj.getStatus());
				member.setIs_admin(memberObj.is_admin);
				memberDAO.persist(member);
			} else if (admin == 0) {
				member.setGroup_id(memberObj.getGroup_id());
				member.setMember_id(memberObj.getMember_id());
				member.setStatus(memberObj.getStatus());
				member.setIs_admin(memberObj.is_admin);
				int i = memberDAO.findByDocAndConnId(memberObj.getGroup_id(), memberObj.getMember_id()).getId();
				member.setId(i);
				memberDAO.merge(member);
			}
	}

	public Map  getAllGroups(String loginId) {
		Map instance = new HashMap();
		try {
			DoctorEntity doctorEntity1 = doctorDAO.findByLoginId(loginId);
			List<MemberEntity> memberEntities = memberDAO.findByDocID(doctorEntity1.getDoctorId());
			Map m=new HashMap();
			instance.put("pendingGroups", memberDAO.findPendingGroupMembers(doctorEntity1.getDoctorId()).size());
			List groups=new ArrayList();
			for (MemberEntity memEntity : memberEntities) {
				List<GroupEntity> groupList = groupDAO.findByGroupID(memEntity.getGroup_id());
				if (groupList.size() > 0) {
					for (GroupEntity entity : groupList) {
						Group group = new Group();
						group.setGroup_id(entity.getGroup_id());
						group.setAdmin_id(entity.getAdmin_id());
						group.setImageUrl(entity.getImageUrl());
						group.setGroup_long_desc(entity.getGroup_long_desc());
						group.setGroup_name(entity.getGroup_name());
						group.setGroup_short_desc(entity.getGroup_short_desc());
						List<MemberEntity> members=memberDAO.getMembersForGroup(entity.getGroup_id(),PENDING);
						group.setPendingMembers(members!=null?members.size():0);
						if(memEntity.is_admin){
							group.setIs_Admin("true");
						} else {
							group.setIs_Admin("false");
						}

						groups.add(group);
					}
				}
			}
			instance.put("groups", groups);

		} catch (Exception e) {
			instance =new HashMap();
			e.printStackTrace();
		}
		return instance;
	}


	/**
	 * Fetch All groups with members using doctor ID
	 *
	 * @param loginId
	 * @return
	 */
	public String updateGroupsMemberStatus(String loginId, Member member) {
		String response = "";
		MemberEntity memberObj = memberDAO.getID(member.getMember_id(), member.getGroup_id());
		try {
			MemberEntity memberEntity = new MemberEntity();
			memberEntity.setGroup_id(member.getGroup_id());
			memberEntity.setMember_id(member.getMember_id());
			memberEntity.setStatus(member.getStatus());

			memberEntity.setId(memberObj.getId());

			memberDAO.merge(memberEntity);
			updateDoctorPlusCount(loginId,member.getStatus());
			response = "SUCCESS";
		} catch (Exception e) {
			response = "ERROR";
			e.printStackTrace();
		}
		return response;
	}

	private void updateDoctorPlusCount(String loginId, String status) {
		PendingCountsEntity pendingCountsEntity=pendingCountDAO.findByLoginId(loginId);
		UserEntity userEntity=userDAO.findByEmailId(loginId);

		if(pendingCountsEntity==null){
			pendingCountsEntity=new PendingCountsEntity();
			pendingCountsEntity.setUserId(userEntity.getUserId());
			pendingCountDAO.persist(pendingCountsEntity);
		}

		int pendingCount=pendingCountsEntity.getDoctorPlusCount()!=null?pendingCountsEntity.getDoctorPlusCount():0;
			if(status!=null){
			if("PENDING".equals(status))
					pendingCount++;
			else if("ACTIVE".equals(status)||"REJECT".equals(status))
				pendingCount=pendingCount>0?pendingCount--:0;
			}

			pendingCountsEntity.setDoctorPlusCount(pendingCount);

//			pendingCountDAO.merge(pendingCountsEntity);
	}

	/**
	 * Service for fetch all the members of the group
	 *
	 * @param loginId
	 * @return
	 */
	public List<Group> fetchMembersOfGroup(String loginId) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		List<Group> response = new ArrayList<Group>();
		List<GroupEntity> groupList = groupDAO.findByDocID(doctorEntity.getDoctorId());
		if (groupList.size() > 0) {
			for (GroupEntity groupEntity : groupList) {
				Group groupObj = new Group();
				groupObj.setGroup_id(groupEntity.getGroup_id());
				groupObj.setGroup_long_desc(groupEntity.getGroup_long_desc());
				groupObj.setGroup_short_desc(groupEntity.getGroup_short_desc());
				groupObj.setGroup_name(groupEntity.getGroup_name());

				List<MemberEntity> membersList = memberDAO.findByGroupID(groupEntity.getGroup_id());
				List<Member> memberDataList = new ArrayList<Member>();
				for (MemberEntity memberObj : membersList) {
					Member member = new Member();
					DoctorEntity doctorObj = doctorDAO.findByDoctorId(memberObj.getMember_id());
					UserEntity userEntity = doctorObj.getUser();

					if (doctorObj.getTherapeuticId() != null) {
						TherapeuticAreaEntity areaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
								Integer.valueOf(doctorObj.getTherapeuticId()));
						if(!Util.isEmpty(areaEntity)){
						member.setTherapeuticName(areaEntity.getTherapeuticName());
						member.setTherapeuticDesc(areaEntity.getTherapeuticDesc());
						}
					}

					member.setGroup_id(groupEntity.getGroup_id());
					member.setMember_id(memberObj.getMember_id());
					member.setStateMedCouncil(doctorObj.getStateMedCouncil());
					if (userEntity != null) {
						member.setAlias(userEntity.getAlias());
						member.setFirstName(Util.getTitle(userEntity.getTitle())+userEntity.getFirstName());
						member.setLastName(userEntity.getLastName());
						member.setStatus(userEntity.getStatus());
						member.setRoleName(userEntity.getRole().getRoleName());
						member.setRoleId(userEntity.getRole().getRoleId());
						member.setImageUrl(userEntity.getDisplayPicture().getImageUrl());
					}
					memberDataList.add(member);
				}
				groupObj.setMember(memberDataList);
				response.add(groupObj);
			}
		}
		return response;
	}

	@SuppressWarnings("null")
	public List<UserDetails> getUserDetails(String loginId) {
		List<DoctorEntity> docEntity = new ArrayList<DoctorEntity>();
		List<Object[]> locDetails = new ArrayList<Object[]>();
		List<UserEntity> userEntity = new ArrayList<UserEntity>();
		List<UserDetails> userDetails = new ArrayList<UserDetails>();

		UserDetails user = new UserDetails();
		log.info("LoginId :" + loginId);
		Integer id = 0;
		List<Object[]> userId = userDAO.getUserId(loginId);
		for (Object[] idDetails : userId) {
			id = (Integer) idDetails[0];
		}

		locDetails = userDAO.getLocationDetails(id);
		String city = null;
		for (Object[] data : locDetails) {
			city = (String) data[3];
		}

		docEntity = userDAO.getDoctorId(id);
		Integer doctorId = 0; // THERAPEUTIC_id
		String therapeuticId = null;
		if (docEntity != null && !docEntity.isEmpty()) {
			for (DoctorEntity docEn : docEntity) {
				doctorId = docEn.getDoctorId();
				therapeuticId = docEn.getTherapeuticId();
				user.setDoctorId(doctorId);
				Integer theId = Integer.parseInt(therapeuticId);
				String therapeuticName = null;
				List<Object[]> therapeuticDetails = userDAO.therapeuticDetails(theId);
				for (Object[] theDetails : therapeuticDetails) {
					therapeuticName = (String) theDetails[1];
					user.setTherapeuticArea(therapeuticName);
				}
			}
		}
				userEntity = userDAO.getUserDetails(loginId);
				if (userEntity != null && userEntity.size() > 0) {
					for (UserEntity userEn : userEntity) {
						Integer userObj = userEn.getUserId();
						if (userObj != null || userObj != 0) {
							user.setUserId(userEn.getUserId());
							user.setFirstName(Util.getTitle(userEn.getTitle())+userEn.getFirstName());
							user.setLastName(userEn.getLastName());
							user.setStatus(userEn.getStatus());
							user.setRoleId(userEn.getRole().getRoleId());
							user.setdPicture(userEn.getDisplayPicture().getImageUrl());
							user.setCity(city);
						}
						userDetails.add(user);
					}
				}
		return userDetails;
	}

	public List<UserDetails> getUserDetailsByCity(String loginId,int groupId) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		List<Integer> existingList = new ArrayList<Integer>();

		if(groupId!=0){
			List<MemberEntity> members=memberDAO.findByGroupID(groupId);
			if(!Util.isEmpty(members))
			for(MemberEntity mem:members)
				existingList.add(mem.getMember_id());
		}else{
			List<ConnectionEntity> connectionEntities = connectionDAO.findActiveAndPendingByDocID(doctorEntity.getDoctorId());
			if(!Util.isEmpty(connectionEntities))
			for (ConnectionEntity connectionEntity : connectionEntities)
				existingList.add(connectionEntity.getConnID());

		}

		existingList.add(doctorEntity.getDoctorId());

		if (doctorEntity != null && doctorEntity.getDoctorId() != null && doctorEntity.getUser() != null) {
			List<LocationEntity> locs = locationDAO.findLocationsByUserId(doctorEntity.getUser().getUserId());
			if (locs != null && !locs.isEmpty()) {
				List<String> cities=new ArrayList<String>();
				for(LocationEntity loc:locs){
					cities.add(loc.getCity());
				}
				List<LocationEntity> locationList = locationDAO.findByCities(cities);

				Set<Integer> addedDoctors=new HashSet<Integer>();
				for (LocationEntity locationEntityObj : locationList) {
					UserDetails details = new UserDetails();
					if (locationEntityObj.getUser() != null) {
						DoctorEntity doctorObj = doctorDAO.findByUserId(locationEntityObj.getUser().getUserId());

						if (doctorObj != null && doctorObj.getDoctorId() != null) {
							UserEntity userEntity = locationEntityObj.getUser();
							details.setUserId(userEntity.getUserId());
							details.setFirstName(Util.getTitle(userEntity.getTitle())+userEntity.getFirstName());
							details.setLastName(userEntity.getLastName());
							details.setStatus(userEntity.getStatus());
							details.setDoctorId(doctorObj.getDoctorId());
							if (doctorObj.getTherapeuticId() != null) {
							TherapeuticAreaEntity areaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
									Integer.valueOf(doctorObj.getTherapeuticId()));
							if(!Util.isEmpty(areaEntity))
								details.setTherapeuticName(areaEntity.getTherapeuticName());
							}
							if (userEntity.getDisplayPicture() != null) {
								details.setdPicture(userEntity.getDisplayPicture().getImageUrl());
							}
							if (!existingList.contains(doctorObj.getDoctorId()) && !addedDoctors.contains(doctorObj.getDoctorId())) {
								userDetails.add(details);
							}

							addedDoctors.add(doctorObj.getDoctorId());
						}
					}
				}
			}
		}

		return userDetails;
	}

	@SuppressWarnings("null")
	public List<UserDetails> getAllMyContacts(String loginId) {
		log.info("User Id" + loginId);

		List<Object[]> locDetails = new ArrayList<Object[]>();
		List<Object[]> userInfo = new ArrayList<Object[]>();
		List<DoctorEntity> docEntity = new ArrayList<DoctorEntity>();
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		List<Object[]> docDetails = new ArrayList<Object[]>();
		List<Object[]> nameDetails = new ArrayList<Object[]>();
		Integer userId = 0;
		Integer uid = 0;
		String firstName = null;
		String lastName = null;
		userInfo = userDAO.getUserId(loginId);
		for (Object[] data : userInfo) {
			userId = (Integer) data[0];
		}
		locDetails = userDAO.getLocationDetails(userId);
		String city = null;
		for (Object[] data : locDetails) {
			city = (String) data[3];
		}
		docDetails = userDAO.getDocDetailsByCity(city);
		for (Object[] data : docDetails) {
			UserDetails user = new UserDetails();
			uid = (Integer) data[0];
			nameDetails = userDAO.getNames(uid);
			for (Object[] names : nameDetails) {
				firstName = (String) names[0];
				lastName = (String) names[1];
			}
			if (userId == uid) {
				continue;
			} else {
				docEntity = userDAO.getDoctorId(uid);
				Integer doctorId = 0;
				String therapeuticId = null;
				if (docEntity != null) {
					for (DoctorEntity docEn : docEntity) {
						doctorId = docEn.getDoctorId();
						therapeuticId = docEn.getTherapeuticId();
					}
				}
				Integer theId = Integer.parseInt(therapeuticId);
				String therapeuticName = null;
				List<Object[]> therapeuticDetails = userDAO.therapeuticDetails(theId);
				for (Object[] theDetails : therapeuticDetails) {
					therapeuticName = (String) theDetails[1];
					user.setTherapeuticArea(therapeuticName);
				}
				docEntity = userDAO.getAllMyContacts(uid);
				if (docEntity != null && docEntity.size() > 0) {
					for (DoctorEntity docEn : docEntity) {
						Integer docObj = docEn.getUser().getUserId();
						if (docObj != null || docObj != 0) {
							user.setDoctorId(docEn.getDoctorId());
							user.setUserId(docEn.getUser().getUserId());
							user.setdPicture(docEn.getUser().getDisplayPicture().getImageUrl());
							user.setCity(city);
							user.setFirstName(firstName);
							user.setLastName(lastName);
						}
						userDetails.add(user);
					}
				}
			}
		}
		return userDetails;
	}

	/*@SuppressWarnings("null")
	public List<UserDetails> getMyContactList(String loginId) {
		log.info("User Id" + loginId);
		List<Object[]> locDetails = new ArrayList<Object[]>();
		List<DoctorEntity> docEntity = new ArrayList<DoctorEntity>();
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		List<Object[]> activeDetails = new ArrayList<Object[]>();
		List<ConnectionDetails> contactDetails = new ArrayList<ConnectionDetails>();
		List<Object[]> docId = new ArrayList<Object[]>();
		Integer userId = 0;
		Integer doctorId = 0;
		Integer connectionId = 0;
		String status = null;
		String firstName = null;
		String lastName = null;
		Integer userId1 = 0;
		activeDetails = userDAO.getActiveConnectionDetails();
		if (activeDetails != null & activeDetails.size() > 0) {
			for (Object[] value : activeDetails) {
				ConnectionDetails contacts = new ConnectionDetails();
				UserDetails user = new UserDetails();
				connectionId = (Integer) value[0];
				docId = userDAO.getDocID(connectionId);
				for (Object[] data : docId) {
					userId1 = (Integer) data[1];
					locDetails = userDAO.getUserNames(userId1);
					for (Object[] name : locDetails) {
						firstName = (String) name[3];
						lastName = (String) name[5];
					}
				}
				doctorId = (Integer) value[1];
				status = (String) value[2];
				contacts.setConnectionId(connectionId);
				contacts.setDoctorId(doctorId);
				contacts.setStatus(status);
				contactDetails.add(contacts);

				locDetails = userDAO.getUserId(loginId);
				for (Object[] data : locDetails) {
					userId = (Integer) data[0];
				}
				locDetails = userDAO.getLocationDetails(userId);
				String city = null;
				for (Object[] data : locDetails) {
					city = (String) data[3];
				}

				docEntity = userDAO.getDoctorId(userId);
				String therapeuticId = null;
				if (docEntity != null) {
					for (DoctorEntity docEn : docEntity) {
						doctorId = docEn.getDoctorId();
						therapeuticId = docEn.getTherapeuticId();
					}
				}
				Integer theId = Integer.parseInt(therapeuticId);
				String therapeuticName = null;
				List<Object[]> therapeuticDetails = userDAO.therapeuticDetails(theId);
				for (Object[] theDetails : therapeuticDetails) {
					therapeuticName = (String) theDetails[1];
					user.setTherapeuticArea(therapeuticName);
				}
				docEntity = userDAO.pendinContacts(userId);
				if (docEntity != null && docEntity.size() > 0) {
					for (DoctorEntity docEn : docEntity) {
						Integer docObj = docEn.getUser().getUserId();
						if (docObj != null || docObj != 0) {
							user.setDoctorId(docEn.getDoctorId());
							user.setUserId(docEn.getUser().getUserId());
							user.setdPicture(docEn.getUser().getDisplayPicture().getData());
							user.setCity(city);
							user.setStatus(contacts.getStatus());
							user.setContactId(contacts.getConnectionId());
							user.setFirstName(firstName);
							user.setLastName(lastName);
						}
						userDetails.add(user);
					}
				}
			}
		}
		return userDetails;
	}*/

	public Map<String,Object> getMyContactList(String loginId) {
		Map<String,Object> result=new HashMap<String,Object>();
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
			result.put("pendingConnections", connectionDAO.findPendingConnectionsCount(doctorEntity.getDoctorId()));
			List<ConnectionEntity> connectionEntities = connectionDAO.findByConnectionStatus(doctorEntity.getDoctorId(), Status.ACTIVE);
			if (connectionEntities.size() > 0) {
				for (ConnectionEntity connectionEntity : connectionEntities) {
					UserDetails details = new UserDetails();
					DoctorEntity doctorObj = doctorDAO.findByDoctorId(connectionEntity.getConnID());
					if (doctorObj.getUser() != null) {
						UserEntity userEntity = doctorObj.getUser();
						List<LocationEntity> locs = locationDAO.findLocationsByUserId(doctorObj.getUser().getUserId());


						if(locs!=null && !locs.isEmpty()){
							for(LocationEntity l:locs){
								if(l.getType()!=null &&l.getType().equals("1")){
									details.setCity(l.getCity());
								}
							}
						}
//						if (locationEntity != null && locationEntity.getCity() != null) {
//							details.setCity(locationEntity.getCity());
//						}
						details.setUserId(userEntity.getUserId());
						details.setFirstName(Util.getTitle(userEntity.getTitle())+userEntity.getFirstName());
						details.setLastName(userEntity.getLastName());
						details.setStatus(userEntity.getStatus());
						details.setDoctorId(doctorObj.getDoctorId());
						if (doctorObj.getTherapeuticId() != null) {
							TherapeuticAreaEntity areaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
									Integer.valueOf(doctorObj.getTherapeuticId()));
							if(!Util.isEmpty(areaEntity)){
							details.setTherapeuticName(areaEntity.getTherapeuticName());
							details.setTherapeuticArea(areaEntity.getTherapeuticDesc());
							}
						}
						if (userEntity.getDisplayPicture() != null) {
							details.setdPicture(userEntity.getDisplayPicture().getImageUrl());
						}
						details.setContactId(connectionEntity.getId());
						details.setConnStatus(connectionEntity.getStatus());
						userDetails.add(details);
					}
					result.put("myContacts", userDetails);
				}
			}
		}

		return result;
	}

	public UserDetails getDoctorDetails(Integer doctorId,Integer loggedInDoctorId){

		DoctorEntity doctorEntity=doctorDAO.findByDoctorId(doctorId);
		DoctorEntity loggedInDoctor=doctorDAO.findByDoctorId(loggedInDoctorId);
		ConnectionEntity connectionEntity=connectionDAO.findByConnectionAndDocId(loggedInDoctorId, doctorId);
		UserDetails details=new UserDetails();
		if(!Util.isEmpty(doctorEntity)){
				details.setPendingConnections(connectionDAO.findPendingConnectionsCount(doctorEntity.getDoctorId()));
			if(doctorEntity.getUser()!=null){

				UserEntity userEntity = doctorEntity.getUser();
				List<LocationEntity> locs = locationDAO.findLocationsByUserId(doctorEntity.getUser().getUserId());


				if(locs!=null && !locs.isEmpty()){
					for(LocationEntity l:locs){
						if(l.getType()!=null &&l.getType().equals("1")){
							details.setCity(l.getCity());
						}
					}
				}
//				if (locationEntity != null && locationEntity.getCity() != null) {
//					details.setCity(locationEntity.getCity());
//				}
				details.setUserId(userEntity.getUserId());
				details.setFirstName(Util.getTitle(userEntity.getTitle())+userEntity.getFirstName());
				details.setLastName(userEntity.getLastName());
				details.setStatus(userEntity.getStatus());
				details.setDoctorId(doctorEntity.getDoctorId());

				if (doctorEntity.getTherapeuticId() != null) {
					TherapeuticAreaEntity areaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
							Integer.valueOf(doctorEntity.getTherapeuticId()));
					if(!Util.isEmpty(areaEntity)){
					details.setTherapeuticName(areaEntity.getTherapeuticName());
					details.setTherapeuticArea(areaEntity.getTherapeuticDesc());
					}
				}

				if (userEntity.getDisplayPicture() != null) {
					details.setdPicture(userEntity.getDisplayPicture().getImageUrl());
				}

				if(connectionEntity!=null){
				details.setContactId(connectionEntity.getId());
				details.setConnStatus(connectionEntity.getStatus());
				}
			}
		}
		return details;
	}
	public List<UserDetails> getSuggestedContacts(String loginId) {
		log.info("User Id" + loginId);
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		List<Integer> regId = new ArrayList<Integer>();
		regId.add(doctorEntity.getDoctorId());
		List<ConnectionEntity> connectionEntities = connectionDAO.findActiveAndPendingByDocID(doctorEntity.getDoctorId());
		for (ConnectionEntity connectionEntity : connectionEntities) {
			regId.add(connectionEntity.getConnID());
		}
		List<Object[]> locDetails = new ArrayList<Object[]>();
		List<DoctorEntity> docEntity = new ArrayList<DoctorEntity>();
		List<UserDetails> userDetails = new ArrayList<UserDetails>();

		List<Object[]> conValues = new ArrayList<Object[]>();
		List<Object[]> nameDetails = new ArrayList<Object[]>();
		UserDetails user = new UserDetails();
		Integer userId = 0;
		Integer doctorId = 0;
		String theraId = null;
		String status = null;
		Integer connectionId = 0;
		String firstName = null;
		String lastName = null;

		locDetails = userDAO.getUserId(loginId);
		for (Object[] data : locDetails) {
			userId = (Integer) data[0];
		}
		locDetails = userDAO.getLocationDetails(userId);
		String city = null;
		for (Object[] data : locDetails) {
			city = (String) data[3];
		}
		docEntity = userDAO.getDoctorId(userId);
		String therapeuticId = null;
		if (docEntity != null) {
			for (DoctorEntity docEn : docEntity) {
				doctorId = docEn.getDoctorId();
				therapeuticId = docEn.getTherapeuticId();

				Integer theId = Integer.parseInt(therapeuticId);
				String therapeuticName = null;
				List<Object[]> therapeuticDetails = userDAO.therapeuticDetails(theId);
				for (Object[] theDetails : therapeuticDetails) {
					therapeuticName = (String) theDetails[1];
					user.setTherapeuticArea(therapeuticName);
				}
				List<Object[]> docWithArea = new ArrayList<Object[]>();
				docWithArea = userDAO.docInfoWithTherapeuticArea(theId);
				if (docWithArea != null & docWithArea.size() > 0) {
					for (Object[] data : docWithArea) {
						UserDetails user1 = new UserDetails();
						ConnectionDetails contacts = new ConnectionDetails();
						if (docEn.getDoctorId() == data[0]) {
							continue;
						} else {
							doctorId = (Integer) data[0];
							userId = (Integer) data[1];
							nameDetails = userDAO.getNames(userId);
							for (Object[] names : nameDetails) {
								firstName = (String) names[0];
								lastName = (String) names[1];
							}
							theraId = (String) data[5];
							status = (String) data[7];
							UserEntity u=userDAO.findByUserId(userId);
							conValues = userDAO.getConIdByDocId(doctorId);
							for (Object[] conDetails : conValues) {
								connectionId = (Integer) conDetails[0];
								contacts.setConnectionId(connectionId);
							}
							user1.setDoctorId(doctorId);
							user1.setUserId(userId);
							user1.setTherapeuticName(theraId);
							user1.setStatus(status);
							if(u.getDisplayPicture()!=null)
							user1.setdPicture(u.getDisplayPicture().getImageUrl());
							user1.setTherapeuticName(therapeuticName);
							user1.setCity(city);
							user1.setContactId(contacts.getConnectionId());
							user1.setFirstName(firstName);
							user1.setLastName(lastName);
							if (!regId.contains(doctorId)) {
								userDetails.add(user1);
							}
						}
					}
				}
			}
		}

		return userDetails;
	}

	/*@SuppressWarnings("null")
	public List<UserDetails> getPendingContactsInfo(String loginId) {
		log.info("User Id" + loginId);
		List<Object[]> locDetails = new ArrayList<Object[]>();
		List<Object[]> penDetails = new ArrayList<Object[]>();
		List<DoctorEntity> docEntity = new ArrayList<DoctorEntity>();
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		List<ConnectionDetails> contactDetails = new ArrayList<ConnectionDetails>();
		List<Object[]> docId = new ArrayList<Object[]>();
		Integer userId1 = 0;
		Integer userId = 0;
		Integer doctorId = 0;
		Integer connectionId = 0;
		String status = null;
		String firstName = null;
		String lastName = null;

		penDetails = userDAO.getConnectionDetails();
		if (penDetails != null & penDetails.size() > 0) {
			for (Object[] value : penDetails) {
				ConnectionDetails contacts = new ConnectionDetails();
				UserDetails user = new UserDetails();
				connectionId = (Integer) value[0];
				docId = userDAO.getDocID(connectionId);
				for (Object[] data : docId) {
					userId1 = (Integer) data[1];
					locDetails = userDAO.getUserNames(userId1);
					for (Object[] name : locDetails) {
						firstName = (String) name[3];
						lastName = (String) name[5];
					}
				}
				doctorId = (Integer) value[1];
				status = (String) value[2];
				contacts.setConnectionId(connectionId);
				contacts.setDoctorId(doctorId);
				contacts.setStatus(status);
				contactDetails.add(contacts);

				locDetails = userDAO.getUserId(loginId);
				for (Object[] data : locDetails) {
					userId = (Integer) data[0];
					// firstName=(String) data[3];
					// lastName=(String) data[5];
				}
				locDetails = userDAO.getLocationDetails(userId);
				String city = null;
				for (Object[] data : locDetails) {
					city = (String) data[3];
				}

				docEntity = userDAO.getDoctorId(userId);
				String therapeuticId = null;
				if (docEntity != null) {
					for (DoctorEntity docEn : docEntity) {
						doctorId = docEn.getDoctorId();
						therapeuticId = docEn.getTherapeuticId();
					}
				}

				Integer theId = Integer.parseInt(therapeuticId);
				String therapeuticName = null;
				List<Object[]> therapeuticDetails = userDAO.therapeuticDetails(theId);
				for (Object[] theDetails : therapeuticDetails) {
					therapeuticName = (String) theDetails[1];
					user.setTherapeuticArea(therapeuticName);
				}
				docEntity = userDAO.pendinContacts(userId);
				if (docEntity != null && docEntity.size() > 0) {
					for (DoctorEntity docEn : docEntity) {
						Integer docObj = docEn.getUser().getUserId();
						if (docObj != null || docObj != 0) {
							user.setDoctorId(docEn.getDoctorId());
							user.setUserId(docEn.getUser().getUserId());
							user.setdPicture(docEn.getUser().getDisplayPicture().getData());
							user.setCity(city);
							user.setStatus(contacts.getStatus());
							user.setContactId(contacts.getConnectionId());
							user.setFirstName(firstName);
							user.setLastName(lastName);
						}
						userDetails.add(user);
					}
				}
			}
		}
		return userDetails;
	}*/

	public List<UserDetails> getPendingContacts(String loginId) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
			List<ConnectionEntity> connectionEntities = connectionDAO.findByPendingConnectionStatus(doctorEntity.getDoctorId(), PENDING);
			if (connectionEntities.size() > 0) {
				for (ConnectionEntity connectionEntity : connectionEntities) {
					UserDetails details = new UserDetails();
					DoctorEntity doctorObj = doctorDAO.findByDoctorId(connectionEntity.getDocID());
					if (doctorObj.getUser() != null) {
						UserEntity userEntity = doctorObj.getUser();
						details.setUserId(userEntity.getUserId());
						details.setFirstName(Util.getTitle(userEntity.getTitle())+userEntity.getFirstName());
						details.setLastName(userEntity.getLastName());
						details.setStatus(userEntity.getStatus());
						details.setDoctorId(doctorObj.getDoctorId());
						if (doctorObj.getTherapeuticId() != null) {
							TherapeuticAreaEntity areaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
									Integer.valueOf(doctorObj.getTherapeuticId()));
							if(areaEntity!=null)
							details.setTherapeuticName(areaEntity.getTherapeuticName());
						}
						if (userEntity.getDisplayPicture() != null) {
							details.setdPicture(userEntity.getDisplayPicture().getImageUrl());
						}
						details.setContactId(connectionEntity.getId());
						details.setConnStatus(connectionEntity.getStatus());
						userDetails.add(details);
					}
				}
			}
		}

		return userDetails;
	}

	public int addContacts(Contacts contacts, String loginId) throws Exception {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		int result = 0;
			if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
				ConnectionEntity conEntity= connectionDAO.findByConnectionAndDocId(doctorEntity.getDoctorId(),contacts.getConnID());
				ConnectionEntity tmp=connectionDAO.findByConnectionAndDocId(contacts.getConnID(), doctorEntity.getDoctorId());
				if(tmp!=null && tmp.getId()!=0){
					DoctorEntity con=doctorDAO.findByDoctorId(conEntity.getConnID());
					if(Status.PENDING.equals(tmp.getStatus()))
						throw new Exception(Util.getTitle(con.getUser().getTitle())+con.getUser().getFirstName()+" has already requested for you.");
					else if(Status.ACTIVE.equals(conEntity.getStatus()))
						throw new Exception(Util.getTitle(con.getUser().getTitle())+con.getUser().getFirstName()+" already exists in your contact list");
				}
				if(conEntity!=null && conEntity.getId()!=0){
					DoctorEntity con=doctorDAO.findByDoctorId(conEntity.getConnID());
					if(Status.PENDING.equals(conEntity.getStatus()))
						throw new Exception(Util.getTitle(con.getUser().getTitle())+con.getUser().getFirstName()+" yet to accept your request");
					else if(Status.ACTIVE.equals(conEntity.getStatus()))
						throw new Exception(Util.getTitle(con.getUser().getTitle())+con.getUser().getFirstName()+" already exists in your contact list");
					else if(Status.EXIT.equals(conEntity.getStatus())){
						conEntity.setStatus(Status.ACTIVE);
						connectionDAO.merge(conEntity);
					}

				}else{
				conEntity = new ConnectionEntity();
				conEntity.setConnID(contacts.getConnID());
				conEntity.setDocID(doctorEntity.getDoctorId());
				conEntity.setStatus(contacts.getStatus());
				connectionDAO.persist(conEntity);
				DoctorEntity d=doctorDAO.findByDoctorId(contacts.getConnID());
				if(d!=null || (d.getDoctorId()!=null &&d.getDoctorId()!=0)){
					if(d.getUser()!=null && d.getUser().getSecurity()!=null)
					updateDoctorPlusCount(d.getUser().getSecurity().getLoginId(), "PENDING");
				}

				}
				result = conEntity.getConnID();

			}
		return result;
	}

	public List<Doctor> findDoctorContactBySearch(String loginId, String name) {
		return findDoctorContactBySearch(loginId, name, 0);
	}

	/**
	 * Delete Group using group ID
	 *
	 * @param loginId
	 * @return
	 */
	public String deleteGroup(String loginId, Group group) {
		String response = "";
		try {
			memberDAO.removeGroupAllMembers(group.getGroup_id());
			List<GroupEntity> groupEntities = groupDAO.findByGroupID(group.getGroup_id());

			if (groupEntities != null && !groupEntities.isEmpty()) {
				String fileName = groupEntities.get(0).getImageUrl()
						.substring(groupEntities.get(0).getImageUrl().lastIndexOf("/"));
				fileName = MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "groups"
						+ File.separator + fileName;
				FileUtil.delete(fileName);
			}
			int i = groupDAO.deleteGroupUsingGroupID(group.getGroup_id());

			System.out.println(i);
			response = "SUCCESS";
		} catch (Exception e) {
			response = "ERROR";
			e.printStackTrace();
		}
		return response;
	}

	public void removeMembersFromGroup(Member member, String loginId) {
		String response = "";
		try {
			if (member.getMemberList() != null && member.getMemberList().size() > 0) {
				for (Integer i : member.getMemberList()) {
					memberDAO.removeMember(member.getGroup_id(), i);
				}
			}
			response = "SUCCESS";
		} catch (Exception e) {
			response = "ERROR";
			e.printStackTrace();
		}
	}

	public String updateGroupData(Model model) {
		String loginId = (String) model.asMap().get("loginId");
		Group group = (Group) model.asMap().get("group");
		String response = "";
		DoctorEntity doctorEntity1 = doctorDAO.findByLoginId(loginId);
		try {
			List<GroupEntity> ges = groupDAO.findByGroupID(group.getGroup_id());
			if (ges != null && !ges.isEmpty()) {
				GroupEntity groupEntity = ges.get(0);
				groupEntity.setGroup_id(group.getGroup_id());
				groupEntity.setAdmin_id(doctorEntity1.getDoctorId());
				groupEntity.setGroup_long_desc(group.getGroup_long_desc());
				groupEntity.setGroup_short_desc(group.getGroup_short_desc());
				groupEntity.setGroup_name(group.getGroup_name());

				if (group.getImgData() != null) {
					String _groupImage = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/groups/";
					_groupImage += FileUtil.copyBinaryData(group.getImgData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "groups",group.getFileName());
					groupEntity.setImageUrl(_groupImage);
				}

				/*MultipartFile file=(MultipartFile) model.asMap().get("image");
				if (file!= null) {
					String groupImage = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/groups/";
					groupImage += FileUtil.copyImage(file,
							MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "groups");
					groupEntity.setImageUrl(groupImage);
				}*/
				groupDAO.merge(groupEntity);
			}

			response = "SUCCESS";
		} catch (Exception e) {
			response = "ERROR";
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Fetch Members of the group using Doctor ID and Group ID
	 *
	 * @param loginId
	 * @param group
	 * @return
	 */
	public List<Group> fetchMembersOfGroup(String loginId, int groupId) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		List<Group> response = new ArrayList<Group>();
		List<GroupEntity> groupList = groupDAO.findByGroupID(groupId);
		if (groupList.size() > 0) {
			for (GroupEntity groupEntity : groupList) {
				Group groupObj = new Group();
				groupObj.setGroup_id(groupEntity.getGroup_id());
				groupObj.setImageUrl(groupEntity.getImageUrl());
				groupObj.setGroup_long_desc(groupEntity.getGroup_long_desc());
				groupObj.setGroup_short_desc(groupEntity.getGroup_short_desc());
				groupObj.setGroup_name(groupEntity.getGroup_name());
				groupObj.setAdmin_id(groupEntity.getAdmin_id());

				List<MemberEntity> membersList = memberDAO.findActiveGroupMembers(groupEntity.getGroup_id());
				List<Member> memberDataList = new ArrayList<Member>();
				for (MemberEntity memberObj : membersList) {
					Member member = new Member();
					DoctorEntity doctorObj = doctorDAO.findByDoctorId(memberObj.getMember_id());
					UserEntity userEntity = doctorObj.getUser();
					if (doctorObj.getTherapeuticId() != null) {
						TherapeuticAreaEntity areaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
								Integer.parseInt(doctorObj.getTherapeuticId()));
						if(!Util.isEmpty(areaEntity)){
						member.setTherapeuticName(areaEntity.getTherapeuticName());
						member.setTherapeuticDesc(areaEntity.getTherapeuticDesc());
						}
					}

					member.setGroup_id(groupEntity.getGroup_id());
					member.setMember_id(memberObj.getMember_id());
					member.setStateMedCouncil(doctorObj.getStateMedCouncil());
					if (userEntity != null) {
						member.setAlias(userEntity.getAlias());
						member.setFirstName(Util.getTitle(userEntity.getTitle())+userEntity.getFirstName());
						member.setLastName(userEntity.getLastName());
						member.setRoleName(userEntity.getRole().getRoleName());
						member.setRoleId(userEntity.getRole().getRoleId());
						if(userEntity.getDisplayPicture()!=null)
						member.setImageUrl(userEntity.getDisplayPicture().getImageUrl());
					}
					member.setStatus(memberObj.getStatus());
					memberDataList.add(member);
				}
				groupObj.setMember(memberDataList);
				response.add(groupObj);
			}
		}
		return response;
	}

	public List<Group> fetchPendingGroups(String loginId) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		List<Group> groupList = new ArrayList<Group>();
		List<MemberEntity> members = memberDAO.findPendingGroupMembers(doctorEntity.getDoctorId());
		if (members.size() > 0) {

			for (MemberEntity entity : members) {
				GroupEntity groupEntity = groupDAO.findById(GroupEntity.class, entity.getGroup_id());
				if(groupEntity!=null){
				Group group = new Group();
				group.setGroup_id(groupEntity.getGroup_id());
				group.setGroup_name(groupEntity.getGroup_name());
				group.setGroup_long_desc(groupEntity.getGroup_long_desc());
				group.setGroup_short_desc(groupEntity.getGroup_short_desc());
				group.setImageUrl(groupEntity.getImageUrl());
				group.setAdmin_id(groupEntity.getAdmin_id());
				groupList.add(group);
				}
			}
		}
		return groupList;
	}

	public List<Member> fetchPendingMembers(String loginId, int groupId) {
		List<Member> memberList = new ArrayList<Member>();
		List<MemberEntity> members = memberDAO.findPendingMembersOfGroup(groupId);

		if (members.size() > 0) {
			for (MemberEntity memberObj : members) {
				Member member = new Member();
				DoctorEntity doctorObj = doctorDAO.findByDoctorId(memberObj.getMember_id());
				UserEntity userEntity = doctorObj.getUser();
				if (doctorObj.getTherapeuticId() != null) {
					TherapeuticAreaEntity areaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
							Integer.valueOf(doctorObj.getTherapeuticId()));
					if(!Util.isEmpty(areaEntity)){
					member.setTherapeuticName(areaEntity.getTherapeuticName());
					member.setTherapeuticDesc(areaEntity.getTherapeuticDesc());
					}
				}
				member.setGroup_id(memberObj.getGroup_id());
				member.setMember_id(memberObj.getMember_id());
				member.setStateMedCouncil(doctorObj.getStateMedCouncil());
				if (userEntity != null) {
					member.setAlias(userEntity.getAlias());
					member.setFirstName(Util.getTitle(userEntity.getTitle())+userEntity.getFirstName());
					member.setLastName(userEntity.getLastName());
					member.setStatus(userEntity.getStatus());
					member.setRoleName(userEntity.getRole().getRoleName());
					member.setRoleId(userEntity.getRole().getRoleId());
					member.setImageUrl(userEntity.getDisplayPicture().getImageUrl());
				}
				memberList.add(member);
			}
		}

		return memberList;
	}

	public String inviteContacts(String loginId, InviteContacts inviteContacts) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		String response = "";
		if (inviteContacts.getToMobileContactList().size() > 0) {
			for (String toContact : inviteContacts.getToMobileContactList()) {
				SMS sms = new SMS();
				try {
					sms.setPhoneNumber(toContact);
					sms.setTemplate(SMSService.INVITE);
					Map<String, String> valueMap = new HashMap<String, String>();
					valueMap.put("NOTIFICATION", inviteContacts.getMessage());
					valueMap.put("URL", inviteContacts.getUrl());
					valueMap.put("firstName",Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
					valueMap.put("lastName",doctorEntity.getUser().getLastName());
					sms.setValueMap(valueMap);

					smsService.sendSMS(sms);
				} catch (Exception e) {
					response = "ERROR";
				}
				response = "SUCCESS";
			}
		}
		return response;
	}

	public List<Group> fetchSuggestedGroups(String loginId) {

		DoctorEntity d=doctorDAO.findByLoginId(loginId);
		List<Group> instance = new ArrayList<Group>();
		try {
			List<GroupEntity> groupList = groupDAO.findSuggestedGroups(d.getDoctorId());
			if (groupList.size() > 0) {
				for (GroupEntity entity : groupList) {
					Group group = new Group();
					group.setGroup_id(entity.getGroup_id());
					group.setAdmin_id(entity.getAdmin_id());
					group.setGroup_long_desc(entity.getGroup_long_desc());
					group.setGroup_name(entity.getGroup_name());
					group.setGroup_short_desc(entity.getGroup_short_desc());
					group.setImageUrl(entity.getImageUrl());
					instance.add(group);
				}
			}
		} catch (Exception e) {
			instance = new ArrayList<Group>();
			e.printStackTrace();
		}
		return instance;
	}

	public String updateConnectionStatus(ConnectionDetails connectionDetails, String loginId) {
		String response = "";
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		try {
			ConnectionEntity connectionEntity =  connectionDAO.findByConnectionAndDocId(doctorEntity.getDoctorId(), connectionDetails.getConnectionId());

			if(connectionEntity==null ||connectionEntity.getId()==0){
				connectionEntity=new ConnectionEntity();
				connectionEntity.setConnID(connectionDetails.getConnectionId());
				connectionEntity.setDocID(doctorEntity.getDoctorId());
				connectionDAO.persist(connectionEntity);
			}

			connectionEntity.setStatus(connectionDetails.getStatus());
//			int id = connectionDAO
//					.findByConnectionAndDocId(doctorEntity.getDoctorId(), connectionDetails.getConnectionId()).getId();
//			connectionEntity.setId(id);
			/*if(!connectionDetails.getStatus().equalsIgnoreCase("ACTIVE") && !connectionDetails.getStatus().equalsIgnoreCase("PENDING")){
				connectionDAO.remove(connectionEntity);
			}else{
			connectionDAO.merge(connectionEntity);
			}*/
			if(Status.REJECT.equals(connectionDetails.getStatus())){
				connectionDAO.remove(connectionEntity);
				ConnectionEntity connectionEntity1 =  connectionDAO.findByConnectionAndDocId(connectionDetails.getConnectionId(),doctorEntity.getDoctorId());
					if(connectionEntity1!=null)
						connectionDAO.remove(connectionEntity1);
			}/*else
			connectionDAO.merge(connectionEntity);*/
			updateDoctorPlusCount(loginId, connectionDetails.getStatus());
			response = "SUCCESS";
		} catch (Exception e) {
			response = "ERROR";
			e.printStackTrace();
		}
		return response;
	}

	public String updatePendingConnectionStatus(ConnectionDetails connectionDetails, String loginId) {
		String response = "";
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		try {

			ConnectionEntity connectionEntity =  connectionDAO.findByConnectionAndDocId(connectionDetails.getDoctorId(),doctorEntity.getDoctorId());

			if(connectionEntity==null || connectionEntity.getId()==0){
				connectionEntity=new ConnectionEntity();
				connectionEntity.setConnID(doctorEntity.getDoctorId());
				connectionEntity.setDocID(connectionDetails.getDoctorId());
				connectionDAO.persist(connectionEntity);
			}

			connectionEntity.setStatus(connectionDetails.getStatus());

			response = "SUCCESS";
		} catch (Exception e) {
			response = "ERROR";
			e.printStackTrace();
		}
		return response;
	}

	public int createMessages(Model model) throws Exception {
		PostMessage postMessage = (PostMessage) model.asMap().get("postMessage");
		String loginId = (String) model.asMap().get("loginId");

		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		int result = 0;
		try {
			if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setMember_id(doctorEntity.getDoctorId());
				messageEntity.setGroup_id(postMessage.getGroup_id());
				messageEntity.setMessage(postMessage.getMessage());
				messageEntity.setMessage_type(postMessage.getMessage_type());
				messageEntity.setPost_date(new Date());
				messageEntity.setReceiver_id(postMessage.getReceiver_id());
				messageEntity.setTopic_id(postMessage.getTopic_id());

				if (postMessage.getFileData() != null) {
					String _file = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/messages/";
					_file+= FileUtil.copyBinaryData(postMessage.getFileData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "messages",postMessage.getFileName());
					messageEntity.setFileUrl(_file);
				}

				/*MultipartFile file=(MultipartFile) model.asMap().get("file");
				if (file!= null) {
					String postFile = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/messages/";
					postFile += FileUtil.copyImage(file,
							MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "messages");
					messageEntity.setFileUrl(postFile);
				}*/
				messageDAO.persist(messageEntity);

				DoctorEntity receiver=doctorDAO.findByDoctorId(postMessage.getReceiver_id());
				String message=Util.getTitle(doctorEntity.getUser().getTitle())+(Util.isEmpty(doctorEntity.getUser().getFirstName())?"":doctorEntity.getUser().getFirstName())+
						(Util.isEmpty(doctorEntity.getUser().getLastName())?"":" "+doctorEntity.getUser().getLastName())+" has sent you a message";
				notificationService.push(message, postMessage.getReceiver_id());

				result = messageEntity.getMessage_id();
			}
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public List<PostMessage> fetchPostedMessage(String loginId) {
		List<Object[]> postMessages = new ArrayList<Object[]>();
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		postMessages = messageDAO.fetchPostedMessage(doctorEntity.getDoctorId());
		List<PostMessage> message = new ArrayList<PostMessage>();
		if (postMessages != null & postMessages.size() > 0) {
			for (Object[] data : postMessages) {
				PostMessage postMessage = new PostMessage();
				postMessage.setMessage_id((Integer) data[0]);
				postMessage.setMember_id((Integer) data[1]);
				postMessage.setGroup_id((Integer) data[2]);
				postMessage.setMessage((String) data[3]);
				postMessage.setMessage_type((String) data[4]);
				if(data[5]!=null)
				postMessage.setPost_date(((Date) data[5]));
				postMessage.setFileUrl((String) data[7]);
				message.add(postMessage);
			}
		}
		return message;
	}

	public int reCommentMessages(Comments comment, String loginId) throws Exception {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		int result = 0;
		try {
			if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
				CommentEntity commentEntity = new CommentEntity();
				commentEntity.setMember_id(doctorEntity.getDoctorId());
				commentEntity.setGroup_id(comment.getGroup_id());
				commentEntity.setMessage(comment.getMessage());
				commentEntity.setMessage_type(comment.getMessage_type());
				commentEntity.setPost_date(new Date());
				commentEntity.setMessage_id(comment.getMessage_id());
				commentDAO.persist(commentEntity);
				result = commentEntity.getMessage_id();
			}
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	public List<Group> getMoreGroups(String loginId) {

		DoctorEntity d=doctorDAO.findByLoginId(loginId);
		List<Group> instance = new ArrayList<Group>();
		try {
			List<GroupEntity> groupList = groupDAO.findMoreGroups(d.getDoctorId());
			if (groupList.size() > 0) {
				for (GroupEntity entity : groupList) {
					Group group = new Group();
					group.setGroup_id(entity.getGroup_id());
					group.setAdmin_id(entity.getAdmin_id());
					group.setGroup_long_desc(entity.getGroup_long_desc());
					group.setGroup_name(entity.getGroup_name());
					group.setGroup_short_desc(entity.getGroup_short_desc());
					group.setImageUrl(entity.getImageUrl());
					instance.add(group);
				}
			}
		} catch (Exception e) {
			instance = new ArrayList<Group>();
			e.printStackTrace();
		}
		return instance;
	}

	public void exitMemberfromGroup(Member memberObj, String loginId) {
			MemberEntity member=memberDAO.getID(memberObj.getMember_id(), memberObj.getGroup_id());
			if(member!=null &&member.getId()!=0){
				member.setStatus(memberObj.getStatus());
			}
//			MemberEntity member = new MemberEntity();
//			member.setGroup_id(memberObj.getGroup_id());
//			member.setMember_id(memberObj.getMember_id());
//			member.setStatus(memberObj.getStatus());
//			memberDAO.updateMemberStatus(member);

	}

	public List<Transform> fetchTransDetails() {
		List<Object[]> transMessages = new ArrayList<Object[]>();
		List<Transform> message = new ArrayList<Transform>();
		if (transMessages != null & transMessages.size() > 0) {
			for (Object[] data : transMessages) {
				Transform transform = new Transform();
				transform.setTransform_id((Integer) data[0]);
				transform.setContent((String) data[1]);
				transform.setContent_type((String) data[2]);
				transform.setPosted_date((Date) data[3]);
				transform.setSource((String) data[4]);
				transform.setCategory((String) data[5]);
				transform.setSource_url((String) data[6]);
				transform.setThumbnail((String) data[7]);
				transform.setActual((String) data[8]);
				message.add(transform);
			}
		}
		return message;
	}

	public void deleteContact(String loginId, Contacts contacts) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		int result = 0;
		try {
			if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
				//ConnectionEntity conEntity = new ConnectionEntity();
				//conEntity.setConnID(contacts.getConnID());
				//conEntity.setDocID(doctorEntity.getDoctorId());
				//conEntity.setStatus(contacts.getStatus());
				ConnectionEntity conEntity1 = connectionDAO.findByConnectionAndDocId(contacts.getConnID(),doctorEntity.getDoctorId());
				ConnectionEntity conEntity2 = connectionDAO.findByConnectionAndDocId(doctorEntity.getDoctorId(), contacts.getConnID());
				//conEntity.setId(id1);
				connectionDAO.remove(conEntity1);
				connectionDAO.remove(conEntity2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerDeviceToken(String loginId, RegisterDeviceToken deviceToken) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		PharmaRepEntity repEntity = pharmaRepDAO.findByLoginId(loginId);
		PatientEntity patientEntity=patientDAO.findByLoginId(loginId);
		UserEntity userEntity=userDAO.findByEmailId(loginId);
		try {
			List<DeviceTokenEntity> availableDevices=deviceTokenDAO.findAll();
			if(!Util.isEmpty(availableDevices)){
				for(DeviceTokenEntity d:availableDevices){
					if("ANDROID".equals(deviceToken.getPlatform())){
						String canonicalId=AndroidPushNotification.getCanonicalId(d.getDeviceToken());
						if(!Util.isEmpty(canonicalId) && canonicalId.equals(deviceToken.getRegDeviceToken())){
							deviceTokenDAO.remove(d);
						}
						}
				}
			}


			if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
				
				DeviceTokenEntity conEntity = new DeviceTokenEntity();
					conEntity.setDeviceToken(deviceToken.getRegDeviceToken());
					conEntity.setDocId(doctorEntity.getDoctorId());
					conEntity.setPlatform(deviceToken.getPlatform());
					conEntity.setPlatform(deviceToken.getPlatform());
					Date d1=new Date();
					conEntity.setCdate(d1);
					deviceTokenDAO.merge(conEntity);

			}else if(patientEntity != null && patientEntity.getPatientId() != null){
				DeviceTokenEntity conEntity = new DeviceTokenEntity();
				conEntity.setDeviceToken(deviceToken.getRegDeviceToken());
				conEntity.setPatId(patientEntity.getPatientId());		
				conEntity.setPlatform(deviceToken.getPlatform());
				conEntity.setPlatform(deviceToken.getPlatform());
				Date d1=new Date();
				conEntity.setCdate(d1);
				deviceTokenDAO.merge(conEntity);
			
		}else if(!Util.isEmpty(userEntity) && !Util.isEmpty(userEntity.getRole()) && !Util.isZeroOrNull(userEntity.getRole().getRoleId())){
				DeviceTokenEntity deviceTokenEntity=new DeviceTokenEntity();
						deviceTokenEntity.setDeviceToken(deviceToken.getRegDeviceToken());
						deviceTokenEntity.setPlatform(deviceToken.getPlatform());
					if(!Util.isZeroOrNull(repEntity.getRepId())){
						Date d1=new Date();
						deviceTokenEntity.setCdate(d1);
						if(userEntity.getRole().getRoleId()==3)
							deviceTokenEntity.setRepId(repEntity.getRepId());
						else
							deviceTokenEntity.setRepMgrId(repEntity.getRepId());

					}

				deviceTokenDAO.merge(deviceTokenEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int postLIke(Likes like, String loginId) throws Exception {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		int result = 0;
		try {
			if (doctorEntity != null && doctorEntity.getDoctorId() != null) {
				LikeEntity likeEntity = new LikeEntity();
				likeEntity.setLike_status(like.getLike_status());
				likeEntity.setLike_Time(new Date());
				likeEntity.setMessage_id(like.getMessage_id());
				likeEntity.setUser_id(doctorEntity.getDoctorId());
				likeDAO.persist(likeEntity);
				result = likeEntity.getMessage_id();
			}
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

	@SuppressWarnings("null")
	public List<Likes> getLikeDetails(String loginId, String likeStatus) {
		List<Object[]> likeValues = new ArrayList<Object[]>();
		List<Likes> likeDetails = new ArrayList<Likes>();
		log.info("likeStatus :" + likeStatus);
		Integer id = 0;
		Integer user_id = 0;
		String status = null;
		Integer message_id = 0;
		Date like_time;

		likeValues = likeDAO.getPostedLikes(likeStatus);
		if (likeValues != null) {
			for (Object[] like : likeValues) {
				Likes values = new Likes();
				user_id = (Integer) like[1];
				status = (String) like[2];
				message_id = (Integer) like[3];
				like_time = (Date) like[4];

				values.setUser_id(user_id);
				values.setMessage_id(message_id);
				values.setLike_status(status);
				values.setLike_time(like_time);
				likeDetails.add(values);
			}

		}

		return likeDetails;
	}

	public List<PostMessage> fetchShareDetails() {
		List<Object[]> postMessages = new ArrayList<Object[]>();
		postMessages = messageDAO.fetchShare();
		List<PostMessage> message = new ArrayList<PostMessage>();
		if (postMessages != null & postMessages.size() > 0) {
			for (Object[] data : postMessages) {
				PostMessage postMessage = new PostMessage();
				postMessage.setMessage_id((Integer) data[0]);
				postMessage.setMember_id((Integer) data[1]);
				postMessage.setGroup_id((Integer) data[2]);
				postMessage.setMessage((String) data[3]);
				postMessage.setMessage_type((String) data[4]);
				if(data[5]!=null)
				postMessage.setPost_date(((Date) data[5]));
				message.add(postMessage);
			}
		}
		return message;
	}

	public List<PostMessage> fetchShareByTopic(Integer topicId,String loginId) {
		List<MessageEntity> postMessages = new ArrayList<MessageEntity>();
		postMessages = messageDAO.fetchShareByTopic(topicId);
		DoctorEntity loggedInUser = doctorDAO.findByLoginId(loginId);

		List<PostMessage> message = new ArrayList<PostMessage>();
		if (postMessages != null & postMessages.size() > 0) {
			for (MessageEntity data : postMessages) {
				PostMessage postMessage = new PostMessage();
				BeanUtils.copyProperties(data, postMessage);
				DoctorEntity doctorEntity = doctorDAO.findByDoctorId(postMessage.getMember_id());
				if (doctorEntity != null && doctorEntity.getUser() != null) {
//					if(Util.isEmpty(postMessage.getMessage()) && Util.isEmpty(postMessage.getFileUrl())){
						String _message="";
						if(data.getMember_id()!=0){
							if(data.getMember_id()==loggedInUser.getDoctorId()){
								_message="You";
							}else{
								if(!Util.isEmpty(doctorEntity) && !Util.isEmpty(doctorEntity.getUser()))
								_message=Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName();
							}
						}
						_message+=" shared the post ";

						if(data.getReceiver_id()!=0){
							DoctorEntity receiver = doctorDAO.findByDoctorId(postMessage.getReceiver_id());

							if(data.getReceiver_id()==loggedInUser.getDoctorId()){
								_message+="with you.";
							}else{
								if(!Util.isEmpty(receiver) && !Util.isEmpty(receiver.getUser()))
								_message+="with "+Util.getTitle(receiver.getUser().getTitle())+receiver.getUser().getFirstName()+".";
							}
						}else if(data.getGroup_id()!=0){
							List<GroupEntity> groups=groupDAO.findByGroupID(data.getGroup_id());
							if(!Util.isEmpty(groups)){
							GroupEntity group=groups.get(0);
							_message+=" with the group "+group.getGroup_name();
							}
						}else
							_message=postMessage.getMessage();
							postMessage.setMessage(_message);
//					}


					postMessage.setDoctor_Name(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
					if (doctorEntity.getUser().getDisplayPicture() != null)
						postMessage.setDisplayPicture(doctorEntity.getUser().getDisplayPicture().getImageUrl());
				}
				message.add(postMessage);
			}
		}
		return message;
	}

	public Map getContactsAndGroups(String loginId) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		Map groupsAndContactsList = new HashMap();
		List<Doctor> doctorList = new ArrayList<Doctor>();

		try {
			List<ConnectionEntity> connectionEntities = connectionDAO.findActiveByDocID(doctorEntity.getDoctorId());
			if (connectionEntities.size() > 0) {
				for (ConnectionEntity connectionEntity : connectionEntities) {
					DoctorEntity doctorEntityObj = doctorDAO.findByDoctorId(connectionEntity.getConnID());
					Doctor doctor = new Doctor();

					doctor.setRegistrationNumber(doctorEntityObj.getRegistrationNumber());
					doctor.setRegistrationYear(doctorEntityObj.getRegistrationYear());
					doctor.setTherapeuticId(doctorEntityObj.getTherapeuticId());
					doctor.setStateMedCouncil(doctorEntityObj.getStateMedCouncil());
					doctor.setQualification(doctorEntityObj.getQualification());
					doctor.setStatus(doctorEntityObj.getStatus());
					doctor.setDoctorId(doctorEntityObj.getDoctorId());

					if (doctorEntityObj.getUser() != null) {
						UserEntity userEntity = doctorEntityObj.getUser();
						doctor.setFirstName(Util.getTitle(userEntity.getTitle())+userEntity.getFirstName());
						doctor.setLastName(userEntity.getLastName());
						doctor.setUserId(userEntity.getUserId());
						if (!Util.isZeroOrNull(doctorEntityObj.getTherapeuticId())) {
							TherapeuticAreaEntity areaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
									Integer.valueOf(doctorEntityObj.getTherapeuticId()));
							if(!Util.isEmpty(areaEntity))
							doctor.setTherapeuticName(areaEntity.getTherapeuticName());
						}
						if (userEntity.getDisplayPicture() != null) {
							doctor.setdPicture(userEntity.getDisplayPicture().getImageUrl());
						}
					}
					doctorList.add(doctor);
				}
			}
			groupsAndContactsList.put("pendingConnections",connectionDAO.findPendingConnectionsCount(doctorEntity.getDoctorId()));
			groupsAndContactsList.put("contacts",doctorList);

			groupsAndContactsList.putAll((Map)getAllGroups(loginId));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("grpsNCntcts",e);
			groupsAndContactsList = new HashMap();
		}
		return groupsAndContactsList;
	}


	public Object medicineAlternatives(String id, int limit) {
		String url =MedRepProperty.getInstance().getProperties("drugsearch.url")+"/medicine_alternatives"+"?key="+MedRepProperty.getInstance().getProperties("drugsearch.key")+ "&id="+id+ "&limit="+limit;
		RestTemplate restTemplate=new RestTemplate();
		return restTemplate.getForObject(url, Object.class);
	}

	public Object fetchDrugDetails(String callback, String id, int limit) throws IOException {
		String url =MedRepProperty.getInstance().getProperties("drugsearch.url")+"/typeahead.json"+"?key="+MedRepProperty.getInstance().getProperties("drugsearch.key")+ "&callback="+callback+ "&id="+id+ "&limit="+limit;

		RestTemplate restTemplate=new RestTemplate();
		return restTemplate.getForObject(url, Object.class);

		/*HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;*/
	}

	public Object fetchMedicineDetails(String id, int limit) throws IOException {
//		final String USER_AGENT = "Mozilla/5.0";
		String url = MedRepProperty.getInstance().getProperties("drugsearch.url")+"/medicine_details"+"?key="+MedRepProperty.getInstance().getProperties("drugsearch.key")+"&id=" + id + "&limit=" + limit;
		RestTemplate restTemplate=new RestTemplate();
		List<String> response=new ArrayList<String>();
		response=restTemplate.getForObject(url, response.getClass());
		return response;

		/*URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;*/
	}

	public List<String> fetchMedicineSuggestions(String id, int limit) throws IOException {
		String url = MedRepProperty.getInstance().getProperties("drugsearch.url")+"/medicine_suggestions"+"?key="+MedRepProperty.getInstance().getProperties("drugsearch.key")+"&id=" + id + "&limit=" + limit;

		RestTemplate restTemplate=new RestTemplate();
		Map response=restTemplate.getForObject(url, HashMap.class);
		List<String> suggestionsList=new ArrayList<String>();
		if(response.get("status").equals("ok")){
			Map _response=(Map) response.get("response");
			List<Map> suggestions=(List<Map>) _response.get("suggestions");
			for(Map suggestion:suggestions){
				suggestionsList.add((String) suggestion.get(("suggestion")));
			}
			}
		return suggestionsList;
		/*URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response);

		List<String> responseList = new ArrayList<String>();
		JsonElement jelement = new JsonParser().parse(response.toString());
		JsonObject jobject = jelement.getAsJsonObject();
		jobject = jobject.getAsJsonObject("response");
		JsonArray jarray = jobject.getAsJsonArray("suggestions");
		for (int i = 0; i < jarray.size(); i++) {
			jobject = jarray.get(i).getAsJsonObject();
			String result = jobject.get("suggestion").toString();
			responseList.add(result.replace("\"", "").trim());
		}

		return responseList;*/
	}

	public Object getMedicineDetails(String id, int limit) throws IOException {
		String url = MedRepProperty.getInstance().getProperties("drugsearch.url")+"/medicine_details"+"?key="+MedRepProperty.getInstance().getProperties("drugsearch.key")+"&id=" + id + "&limit=" + limit;
		RestTemplate restTemplate=new RestTemplate();
		return restTemplate.getForObject(url,HashMap.class);

		/*URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response);
		return response;*/
	}

	public List<PostMessage> fetchShareDetailsByDate(String loginId, Date post_date) throws Exception {
		List<PostMessage> response = new ArrayList<PostMessage>();
		if (post_date == null) {
			int noOfDays = -7; // i.e two weeks
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
			post_date = calendar.getTime();
		}

		if (post_date != null) {
			List<MessageEntity> messageEntities = messageDAO.findByDate(post_date);
			for (MessageEntity messageEntity : messageEntities) {
				PostMessage postMessage = BeanUtils.instantiateClass(PostMessage.class);
				BeanUtils.copyProperties(messageEntity, postMessage);
				postMessage.setShare_date(messageEntity.getPost_date().toString());
				DoctorEntity doctorEntity = doctorDAO.findByDoctorId(postMessage.getMember_id());
				if (doctorEntity != null && doctorEntity.getUser() != null) {
					postMessage.setDoctor_Name(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
					if (doctorEntity.getUser().getDisplayPicture() != null)
						postMessage.setDisplayPicture(doctorEntity.getUser().getDisplayPicture().getImageUrl());
				}
				response.add(postMessage);
			}
		}

		return response;
	}

	public void pushNews(News news) {
		if (news != null) {
			NewsEntity newsEntity = newsDAO.findBytitle(news.getTitle());
			if(Util.isEmpty(newsEntity)){
				newsEntity=new NewsEntity();
				BeanUtils.copyProperties(news, newsEntity);
				newsEntity.setCreatedOn(new Date());
				newsDAO.persist(newsEntity);
			}
		}
	}

	public void pushTransform(TransformModel news) {
		if (news != null) {
			TransformEntity transformEntity=transformDAO.findByTitle(news.getTitle());
			if(Util.isEmpty(transformEntity)){
			transformEntity = new TransformEntity();
			BeanUtils.copyProperties(news, transformEntity);
			transformEntity.setCreatedOn(new Date());
			transformDAO.persist(transformEntity);
			}
		}
	}

	public Map<String, String> updateGroup(Model model) {
		Map result = new HashMap<String, String>();
		try {
			Integer groupID = (Integer) model.asMap().get("groupId");
			List<GroupEntity> ges = groupDAO.findByGroupID(groupID);

			if (ges != null && !ges.isEmpty()) {
				GroupEntity ge = ges.get(0);

				// save the entity to DB
				Group group = (Group) model.asMap().get("group");
				group.setGroup_id((Integer) model.asMap().get("groupId"));

				if (group.getImgData() != null) {
					String _groupImage = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/groups/";
					_groupImage += FileUtil.copyBinaryData(group.getImgData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "groups",group.getFileName());
					ge.setImageUrl(_groupImage);
				}

				if (group.getGroup_name() != null)
					ge.setGroup_name(group.getGroup_name());

				if (group.getGroup_long_desc() != null && group.getGroup_long_desc().trim().length() != 0)
					ge.setGroup_long_desc(group.getGroup_long_desc());

				groupDAO.persist(ge);
				result.put("status", "success");
				result.put("message", "Succesfully updated group");
				result.put("group", ge);
			} else {
				result.put("status", "failure");
				result.put("message", "GroupId doesn't exist");
			}
		} catch (Exception e) {
			result.put("status", "error");
			result.put("message", "An Internal Exception Occured.Please try after sometime");
			e.printStackTrace();
		}
		return result;
	}

	public Group getGroup(Integer groupId) {
		GroupEntity ge = groupDAO.findById(GroupEntity.class, groupId);
		Group group = new Group();
		BeanUtils.copyProperties(ge, group);
		return group;
	}

	public int createPost(ShareDetails shareDetails, String loginId) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		try {
			if (doctorEntity != null) {
				ShareDetailsEntity shareDetailsEntity = new ShareDetailsEntity();

				shareDetailsEntity.setDoctor_id(doctorEntity.getDoctorId());
				shareDetailsEntity.setLikes_count(shareDetails.getLikes_count());
				shareDetailsEntity.setShare_count(shareDetails.getShare_count());
				shareDetailsEntity.setComment_count(shareDetails.getComment_count());
				shareDetailsEntity.setContent_type(shareDetails.getContent_type());
				shareDetailsEntity.setDetail_desc(shareDetails.getDetail_desc());
				shareDetailsEntity.setShort_desc(shareDetails.getShort_desc());
				shareDetailsEntity.setTitle_desc(shareDetails.getTitle_desc());
				shareDetailsEntity.setTransform_post_id(shareDetails.getTransform_post_id());
				shareDetailsEntity.setUrl(shareDetails.getUrl());
				shareDetailsDAO.persist(shareDetailsEntity);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	public int updateLikesCommentsShareOfTopic(ShareDetails shareDetails, String loginId) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		try {
			if (doctorEntity != null) {
				ShareDetailsEntity shareDetailsEntity = shareDetailsDAO.findById(ShareDetailsEntity.class, shareDetails.getId());
				if (shareDetails.getLikes_count() > 0) {
					shareDetailsEntity.setLikes_count(shareDetails.getLikes_count());
				}
				if (shareDetails.getComment_count() > 0) {
					shareDetailsEntity.setComment_count(shareDetails.getComment_count());
				}
				if (shareDetails.getShare_count() > 0) {
					shareDetailsEntity.setShare_count(shareDetails.getShare_count());
				}
				shareDetailsDAO.merge(shareDetailsEntity);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

/*	public List<ShareDetails> fetchTopicDetails(String loginId) {
		List<ShareDetails> shareDetails = new ArrayList<ShareDetails>();
		try {
			DoctorEntity doctor=doctorDAO.findByLoginId(loginId);

			List<ShareDetailsEntity> shareDetailsEntity = shareDetailsDAO.fetchAllShareTopicData();
			for (ShareDetailsEntity shareDetailsEntity2 : shareDetailsEntity) {
				ShareDetails details = new ShareDetails();
				DoctorEntity doctorEntity = doctorDAO.findByDoctorId(shareDetailsEntity2.getDoctor_id());
				details.setId(shareDetailsEntity2.getId());
				details.setDoctor_id(shareDetailsEntity2.getDoctor_id());
				details.setLikes_count(shareDetailsEntity2.getLikes_count());
				details.setComment_count(shareDetailsEntity2.getComment_count());
				details.setShare_count(shareDetailsEntity2.getShare_count());
				details.setPosted_on(shareDetailsEntity2.getPosted_on());

				details.setTitle_desc(shareDetailsEntity2.getTitle_desc());
				details.setShort_desc(shareDetailsEntity2.getShort_desc());
				details.setDetail_desc(shareDetailsEntity2.getDetail_desc());
				details.setContent_type(shareDetailsEntity2.getContent_type());

			if(doctor!=null){
				LikeEntity like=likeDAO.findByTopicIdAndDoctorId(shareDetailsEntity2.getId(), doctor.getDoctorId());
				if(like!=null){
				details.setLike(Status.TRUE.equalsIgnoreCase(like.getLike_status()));
				}
			}

				if(shareDetailsEntity2.getTransform_post_id()!=null){
					if(shareDetailsEntity2.getSource().getId()==0){
						TransformEntity transformEntity=transformDAO.findById(TransformEntity.class, shareDetailsEntity2.getTransform_post_id());

						if(transformEntity!=null){
							if(details.getTitle_desc()==null)
						details.setTitle_desc(transformEntity.getTitle());
							if(details.getShort_desc()==null)
						details.setShort_desc(transformEntity.getTagDesc());
							if(details.getDetail_desc()==null)
						details.setDetail_desc(transformEntity.getTransformDesc());

							if(details.getContent_type()==null)
						if(transformEntity.getVideoUrl()!=null)
							details.setContent_type("video");
						else if(transformEntity.getInnerImgUrl()!=null && transformEntity.getCoverImgUrl()!=null)
							details.setContent_type("image");
						else
							details.setContent_type("text");
						}


					}else if(shareDetailsEntity2.getSource().getId()==5)
					{
						NewsEntity newsEntity=newsDAO.findById(NewsEntity.class,shareDetailsEntity2.getTransform_post_id());

						if(newsEntity!=null){
							if(details.getTitle_desc()==null)
							details.setTitle_desc(newsEntity.getTitle());
							if(details.getShort_desc()==null)
							details.setShort_desc(newsEntity.getTagDesc());
							if(details.getDetail_desc()==null)
							details.setDetail_desc(newsEntity.getNewsDesc());
							if(details.getContent_type()==null)
							if(newsEntity.getVideoUrl()!=null)
								details.setContent_type("video");
							else if(newsEntity.getInnerImgUrl()!=null && newsEntity.getCoverImgUrl()!=null)
								details.setContent_type("image");
							else
								details.setContent_type("text");
						}
						}
					}


				details.setUrl(shareDetailsEntity2.getUrl());
				details.setTransform_post_id(shareDetailsEntity2.getTransform_post_id());
				details.setTopic_id(shareDetailsEntity2.getId());

				if(doctorEntity != null && doctorEntity.getUser() != null){
					details.setDoctor_Name(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
					if(doctorEntity.getUser().getDisplayPicture()!=null)
					details.setDisplayPicture(doctorEntity.getUser().getDisplayPicture().getImageUrl());
				}
				shareDetails.add(details);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shareDetails;
	}
*/
	
	public List<ShareDetails> fetchTopicDetails(String loginId) {
		List<ShareDetails> shareDetails = new ArrayList<ShareDetails>();
		try {
			DoctorEntity doctor=doctorDAO.findByLoginId(loginId);

			List<ShareDetailsEntity> shareDetailsEntity = shareDetailsDAO.fetchAllShareTopicData();
			for (ShareDetailsEntity shareDetailsEntity2 : shareDetailsEntity) {
				ShareDetails details = new ShareDetails();
				DoctorEntity doctorEntity = doctorDAO.findByDoctorId(shareDetailsEntity2.getDoctor_id());
				details.setId(shareDetailsEntity2.getId());
				details.setDoctor_id(shareDetailsEntity2.getDoctor_id());
				details.setLikes_count(shareDetailsEntity2.getLikes_count());
				details.setComment_count(shareDetailsEntity2.getComment_count());
				details.setShare_count(shareDetailsEntity2.getShare_count());
				details.setPosted_on(shareDetailsEntity2.getPosted_on());
				System.out.println(Integer.parseInt(doctorEntity.getTherapeuticId()));
				TherapeuticAreaEntity therapeuticarea=therapeuticAreaDAO.findById(Integer.parseInt(doctorEntity.getTherapeuticId()));
				details.setTherapeutic_area(therapeuticarea.getTherapeuticName());
				ConnectionEntity connectionentity= connectionDAO.findByConnectionAndDocId(doctor.getDoctorId(),doctorEntity.getDoctorId() );
				if (connectionentity.getStatus() == null){
					connectionentity= connectionDAO.findByConnectionAndDocId(doctorEntity.getDoctorId(),doctor.getDoctorId() );
					if("PENDING".equals(connectionentity.getStatus()))
						details.setShare_doctor_connection("ACCEPTORREJECT");
					else
						details.setShare_doctor_connection(connectionentity.getStatus());
				}else{
					details.setShare_doctor_connection(connectionentity.getStatus());
				}
				if(shareDetailsEntity2.getComment_count()>0){
					List<MessageEntity> messageEntity=messageDAO.fetchShareById(shareDetailsEntity2.getId());
					DoctorEntity doctorEntity_post = doctorDAO.findByDoctorId(messageEntity.get(0).getMember_id());
					TherapeuticAreaEntity therapeuticarea_post=therapeuticAreaDAO.findById(Integer.parseInt(doctorEntity_post.getTherapeuticId()));
					details.setShare_comment_doctor_therapeutic_area(therapeuticarea_post.getTherapeuticName());
					details.setShare_comment_doctorname(Util.getTitle(doctorEntity_post.getUser().getTitle())+doctorEntity_post.getUser().getFirstName());
					details.setShare_comment_last(messageEntity.get(0).getMessage());					
				}
				
			
				details.setTitle_desc(shareDetailsEntity2.getTitle_desc());
				details.setShort_desc(shareDetailsEntity2.getShort_desc());
				details.setDetail_desc(shareDetailsEntity2.getDetail_desc());
				details.setContent_type(shareDetailsEntity2.getContent_type());

			if(doctor!=null){
				
				LikeEntity like=likeDAO.findByTopicIdAndDoctorId(shareDetailsEntity2.getId(), doctor.getDoctorId());
				if(like!=null){
				details.setLike(Status.TRUE.equalsIgnoreCase(like.getLike_status()));
				}
			}

				if(shareDetailsEntity2.getTransform_post_id()!=null){
					if(shareDetailsEntity2.getSource().getId()==0){
						TransformEntity transformEntity=transformDAO.findById(TransformEntity.class, shareDetailsEntity2.getTransform_post_id());

						if(transformEntity!=null){
							if(details.getTitle_desc()==null)
						details.setTitle_desc(transformEntity.getTitle());
							if(details.getShort_desc()==null)
						details.setShort_desc(transformEntity.getTagDesc());
							if(details.getDetail_desc()==null)
						details.setDetail_desc(transformEntity.getTransformDesc());

							if(details.getContent_type()==null)
						if(transformEntity.getVideoUrl()!=null)
							details.setContent_type("video");
						else if(transformEntity.getInnerImgUrl()!=null && transformEntity.getCoverImgUrl()!=null)
							details.setContent_type("image");
						else
							details.setContent_type("text");
						}


					}else if(shareDetailsEntity2.getSource().getId()==5)
					{
						NewsEntity newsEntity=newsDAO.findById(NewsEntity.class,shareDetailsEntity2.getTransform_post_id());

						if(newsEntity!=null){
							if(details.getTitle_desc()==null)
							details.setTitle_desc(newsEntity.getTitle());
							if(details.getShort_desc()==null)
							details.setShort_desc(newsEntity.getTagDesc());
							if(details.getDetail_desc()==null)
							details.setDetail_desc(newsEntity.getNewsDesc());
							if(details.getContent_type()==null)
							if(newsEntity.getVideoUrl()!=null)
								details.setContent_type("video");
							else if(newsEntity.getInnerImgUrl()!=null && newsEntity.getCoverImgUrl()!=null)
								details.setContent_type("image");
							else
								details.setContent_type("text");
						}
						}
					}


				details.setUrl(shareDetailsEntity2.getUrl());
				details.setTransform_post_id(shareDetailsEntity2.getTransform_post_id());
				details.setTopic_id(shareDetailsEntity2.getId());

				if(doctorEntity != null && doctorEntity.getUser() != null){
					details.setDoctor_Name(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
					if(doctorEntity.getUser().getDisplayPicture()!=null)
					details.setDisplayPicture(doctorEntity.getUser().getDisplayPicture().getImageUrl());
				}
				shareDetails.add(details);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shareDetails;
	}

	static final int TRANSFORM = 0;
	static final int SHARE = 1;
	static final int MESSAGE = 2;// insert
	static final int LIKE = 3;
	static final int COMMENT = 4;
	static final int NEWS_UPDATES = 5;
	static final int GLOBAL_SHARE=6;
	// just inserting post _t

	// insert post_topic message
	public boolean postShare(ShareDetails shareDetails, String loginId) throws Exception {
		boolean result = false;
		DoctorEntity doctor = doctorDAO.findByLoginId(loginId);

		log.info("Enum::"+shareDetails.getPostMessage().getPostType());
		log.info("groupId::"+shareDetails.getGroup_id()+"::"+shareDetails.getPostMessage().getGroupId()+"::"+shareDetails.getPostMessage().getGroup_id());
		switch (shareDetails.getPostMessage().getPostType()) {
		case TRANSFORM:
			ShareDetailsEntity shareDetailsEntity =shareDetailsDAO.fetchShare(doctor.getDoctorId(),shareDetails.getTopic_id());
			PostTypeEntity postTypeEntity =null;
			if(shareDetailsEntity!=null){
				shareDetailsEntity.setShare_count(shareDetailsEntity.getShare_count()!=null?shareDetailsEntity.getShare_count()+1:1);
				shareDetailsDAO.merge(shareDetailsEntity);
			}else{
				shareDetailsEntity=new ShareDetailsEntity();
			postTypeEntity = postTypeDao.findById(TRANSFORM);
			shareDetailsEntity.setSource(postTypeEntity);
			shareDetailsEntity.setDoctor_id(doctor.getDoctorId());
			shareDetailsEntity.setTransform_post_id(shareDetails.getTopic_id());
			shareDetailsEntity.setComment_count(0);
			shareDetailsEntity.setShare_count(0);
			shareDetailsEntity.setLikes_count(0);
			shareDetailsEntity.setDetail_desc(shareDetails.getDetail_desc());

			String titleDesc=shareDetails.getTitle_desc();
			if(Util.isEmpty(titleDesc)){
				if(shareDetails.getDetail_desc()!=null){
					titleDesc=Util.subString(shareDetails.getDetail_desc(), 100);
				}
			}
			shareDetailsEntity.setTitle_desc(titleDesc);

			 String shortDesc=shareDetails.getShort_desc();
			if(Util.isEmpty(shortDesc)){
				if(shareDetails.getDetail_desc()!=null){
					shortDesc=Util.subString(shareDetails.getDetail_desc(), 100);
				}
			}
			shareDetailsEntity.setShort_desc(shortDesc);


			TransformEntity transformEntity=transformDAO.findById(TransformEntity.class, shareDetails.getTransform_post_id());
			if(transformEntity!=null){
				System.out.println("Found transform Entity.."+transformEntity.getTransformId());
				if(transformEntity.getVideoUrl()!=null){
					shareDetailsEntity.setContent_type("video");
					shareDetailsEntity.setUrl(transformEntity.getVideoUrl());
				}
				else if(transformEntity.getInnerImgUrl()!=null && transformEntity.getCoverImgUrl()!=null){
					shareDetailsEntity.setContent_type("image");
					if(transformEntity.getInnerImgUrl()!=null ){
						shareDetailsEntity.setUrl(transformEntity.getInnerImgUrl());
					}else{
						shareDetailsEntity.setUrl(transformEntity.getCoverImgUrl());
					}
				}
				else
					shareDetailsEntity.setContent_type("text");

				if(Util.isEmpty(shareDetailsEntity.getTitle_desc()))
					shareDetailsEntity.setTitle_desc(transformEntity.getTitle());
				if(Util.isEmpty(shareDetailsEntity.getShort_desc()))
					shareDetailsEntity.setShort_desc(transformEntity.getTitle());
				if(Util.isEmpty(shareDetailsEntity.getDetail_desc())){
					shareDetailsEntity.setDetail_desc(transformEntity.getTransformDesc());
				}


			}


//			if(shareDetails.getPostMessage()!=null)
//			shareDetailsEntity.setContent_type(shareDetails.getPostMessage().getMessage_type());
			shareDetailsDAO.persist(shareDetailsEntity);
			}

			result = true;
			break;
		case SHARE:

			String fileUrl = "";
			if (shareDetails.getPostMessage().getFileData() != null) {
				fileUrl = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/messages/";
				fileUrl += FileUtil.copyBinaryData(shareDetails.getPostMessage().getFileData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "messages",shareDetails.getPostMessage().getFileName());
			}
			if (shareDetails.getPostMessage().getGroupId() != null)
				for (Integer groupId : shareDetails.getPostMessage().getGroupId()) {
					MessageEntity messageEntity = new MessageEntity();
					messageEntity.setTopic_id(shareDetails.getTopic_id());
					messageEntity.setMember_id(doctor.getDoctorId());
					messageEntity.setMessage(shareDetails.getPostMessage().getMessage());
					messageEntity.setMessage_type(shareDetails.getPostMessage().getMessage_type());
					messageEntity.setPost_date(new Date());
					messageEntity.setGroup_id(groupId);
					if (fileUrl.length() != 0)
						messageEntity.setFileUrl(fileUrl);

					ShareDetailsEntity shareDetailsEntity1=shareDetailsDAO.findById(ShareDetailsEntity.class,shareDetails.getTopic_id());
					if(!Util.isEmpty(shareDetailsEntity1)){
						messageEntity.setMessage(shareDetailsEntity1.getDetail_desc());
						messageEntity.setFileUrl(shareDetailsEntity1.getUrl());
					}

					messageDAO.persist(messageEntity);
				}

			if (shareDetails.getPostMessage().getReceiverId() != null)
				for (Integer receiverId : shareDetails.getPostMessage().getReceiverId()) {
					MessageEntity messageEntity = new MessageEntity();
					messageEntity.setTopic_id(shareDetails.getTopic_id());
					messageEntity.setMember_id(doctor.getDoctorId());
					messageEntity.setMessage(shareDetails.getPostMessage().getMessage());
					messageEntity.setMessage_type(shareDetails.getPostMessage().getMessage_type());
					messageEntity.setPost_date(new Date());
					messageEntity.setReceiver_id(receiverId);
					if (fileUrl.length() != 0)
						messageEntity.setFileUrl(fileUrl);
					messageDAO.persist(messageEntity);

				}
			shareDetailsEntity=shareDetailsDAO.findById(ShareDetailsEntity.class, shareDetails.getTopic_id());
			if(shareDetailsEntity!=null)
			shareDetailsEntity.setShare_count(shareDetailsEntity.getShare_count()!=null?shareDetailsEntity.getShare_count()+1:1);

			result = true;
			break;
		case MESSAGE:
			shareDetailsEntity = new ShareDetailsEntity();
			shareDetailsEntity.setDoctor_id(doctor.getDoctorId());
			shareDetailsEntity.setComment_count(0);
			shareDetailsEntity.setLikes_count(0);
			shareDetailsEntity.setShare_count(0);
			shareDetailsEntity.setContent_type(shareDetails.getPostMessage().getMessage_type());
			shareDetailsEntity.setDetail_desc(shareDetails.getDetail_desc());
			shareDetailsEntity.setPosted_on(new Date());
			shareDetailsEntity.setShort_desc(shareDetails.getShort_desc());
			postTypeEntity = postTypeDao.findById(MESSAGE);
			shareDetailsEntity.setSource(postTypeEntity);

			String titleDesc=shareDetails.getTitle_desc();
			if(Util.isEmpty(titleDesc)){
				if(shareDetails.getDetail_desc()!=null){
					titleDesc=Util.subString(shareDetails.getDetail_desc(), 100);
				}
			}
			shareDetailsEntity.setTitle_desc(titleDesc);

			 String shortDesc=shareDetails.getShort_desc();
			if(Util.isEmpty(shortDesc)){
				if(shareDetails.getDetail_desc()!=null){
					shortDesc=Util.subString(shareDetails.getDetail_desc(), 100);
				}
			}
			shareDetailsEntity.setShort_desc(shortDesc);


			if (shareDetails.getPostMessage().getFileData() != null) {
				String _file = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/messages/";
				_file += FileUtil.copyBinaryData(shareDetails.getPostMessage().getFileData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "messages",shareDetails.getPostMessage().getFileName());
				shareDetailsEntity.setUrl(_file);
			}

			if(shareDetails.getPostMessage().getGroupId()!=null && shareDetails.getPostMessage().getGroupId().length>0){
				shareDetailsEntity.setGroupId(shareDetails.getPostMessage().getGroupId()[0]);
				List<MemberEntity> members=memberDAO.findActiveGroupMembers(shareDetailsEntity.getGroupId());
				if(!Util.isEmpty(members)){
					for(MemberEntity member:members){
						if(member.getMember_id()!=doctor.getDoctorId()){
							notificationService.push(Util.getTitle(doctor.getUser().getTitle())+doctor.getUser().getFirstName()+"posted a new message: "+Util.subString(titleDesc,25), member.getMember_id());
						}
					}
				}

			}else if(shareDetails.getPostMessage().getReceiverId()!=null && shareDetails.getPostMessage().getReceiverId().length>0){
				String message=Util.getTitle(doctor.getUser().getTitle())+(Util.isEmpty(doctor.getUser().getFirstName())?"":doctor.getUser().getFirstName())+
						(Util.isEmpty(doctor.getUser().getLastName())?"":" "+doctor.getUser().getLastName())+" has sent you a message";
				shareDetailsEntity.setReceiverId(shareDetails.getPostMessage().getReceiverId()[0]);
				notificationService.push(message, shareDetails.getPostMessage().getReceiverId()[0]);
				DoctorEntity d=doctorDAO.findByDoctorIdWithUserEntity(shareDetails.getPostMessage().getReceiverId()[0]);
				PendingCountsEntity pe=pendingCountDAO.findByUserId(d.getUser().getUserId());
				pe.setDoctorPlusCount(Util.isZeroOrNull(pe.getDoctorPlusCount())?1:pe.getDoctorPlusCount()+1);
//				pendingCountDAO.merge(pe);

			}

			shareDetailsDAO.persist(shareDetailsEntity);
			result = true;
			break;
		case LIKE:
			shareDetailsEntity = shareDetailsDAO.findById(ShareDetailsEntity.class, shareDetails.getTopic_id());

			if (doctor != null && doctor.getDoctorId() != null) {
				LikeEntity like = likeDAO.findByTopicIdAndDoctorId(shareDetailsEntity.getId(), doctor.getDoctorId());
				if (like == null) {
					LikeEntity likeEntity = new LikeEntity();
					likeEntity.setLike_status( shareDetails.isLike()?Status.TRUE:Status.FALSE);
					likeEntity.setLike_Time(new Date());
					likeEntity.setMessage_id(shareDetailsEntity.getId());
					likeEntity.setUser_id(doctor.getDoctorId());
					likeDAO.persist(likeEntity);
					shareDetailsEntity.setLikes_count(shareDetailsEntity.getLikes_count()!=null?shareDetailsEntity.getLikes_count()+1:1);
				} else {
						like.setLike_status(shareDetails.isLike()?Status.TRUE:Status.FALSE);
						if(shareDetails.isLike())
							shareDetailsEntity.setLikes_count(shareDetailsEntity.getLikes_count()!=null?shareDetailsEntity.getLikes_count()+1:1);
						else
							shareDetailsEntity.setLikes_count(shareDetailsEntity.getLikes_count()!=null && shareDetailsEntity.getLikes_count()>0 ?shareDetailsEntity.getLikes_count()-1:0);
						//likeDAO.merge(like);
				}
			}else
				throw new Exception("Doctor Profile not found");
			//			shareDetailsEntity.setComment_count(shareDetailsEntity.getComment_count()!=null?shareDetailsEntity.getComment_count()+1:1);
//			shareDetailsEntity.setShare_count(shareDetailsEntity.getShare_count()!=null?shareDetailsEntity.getShare_count()+1:1);
			shareDetailsDAO.merge(shareDetailsEntity);
			result = true;
			break;
		case COMMENT:
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setTopic_id(shareDetails.getTopic_id());
			messageEntity.setMember_id(doctor.getDoctorId());
			messageEntity.setMessage(shareDetails.getPostMessage().getMessage());
			messageEntity.setMessage_type(shareDetails.getPostMessage().getMessage_type());
			messageEntity.setPost_date(new Date());
			if (shareDetails.getPostMessage().getFileData() != null) {
				String _file = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/messages/";
				_file += FileUtil.copyBinaryData(shareDetails.getPostMessage().getFileData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "messages",shareDetails.getPostMessage().getFileName());
				messageEntity.setFileUrl(_file);
			}
			messageEntity.setReceiver_id(0);
			messageEntity.setGroup_id(0);
			shareDetailsEntity=shareDetailsDAO.findById(ShareDetailsEntity.class, shareDetails.getTopic_id());
			if(shareDetailsEntity!=null){
			shareDetailsEntity.setComment_count(shareDetailsEntity.getComment_count()!=null?shareDetailsEntity.getComment_count()+1:1);
			titleDesc=shareDetails.getTitle_desc();
			if(Util.isEmpty(titleDesc)){
				if(shareDetails.getDetail_desc()!=null){
					titleDesc=Util.subStringWithEllipsis(shareDetails.getDetail_desc(), 100);
				}
			}
			if(!Util.isEmpty(titleDesc))
			shareDetailsEntity.setTitle_desc(titleDesc);

			 shortDesc=shareDetails.getShort_desc();
			if(Util.isEmpty(shortDesc)){
				if(shareDetails.getDetail_desc()!=null){
					shortDesc=Util.subStringWithEllipsis(shareDetails.getDetail_desc(), 100);
				}
			}
			if(!Util.isEmpty(shortDesc))
			shareDetailsEntity.setShort_desc(shortDesc);
			}

			messageDAO.persist(messageEntity);
			result = true;
			break;
		case NEWS_UPDATES:
			shareDetailsEntity = shareDetailsDAO.fetchShare(doctor.getDoctorId(), shareDetails.getTopic_id());
			if(shareDetailsEntity!=null){
				shareDetailsEntity.setShare_count(shareDetailsEntity.getShare_count()!=null?shareDetailsEntity.getShare_count()+1:1);
				shareDetailsDAO.merge(shareDetailsEntity);
			}else{
				shareDetailsEntity=new ShareDetailsEntity();
			shareDetailsEntity.setDoctor_id(doctor.getDoctorId());
			postTypeEntity = postTypeDao.findById(NEWS_UPDATES);
			shareDetailsEntity.setSource(postTypeEntity);
			shareDetailsEntity.setTransform_post_id(shareDetails.getTopic_id());

			shareDetailsEntity.setComment_count(0);
			shareDetailsEntity.setShare_count(0);
			shareDetailsEntity.setLikes_count(0);

			NewsEntity newsEntity=newsDAO.findById(NewsEntity.class, shareDetailsEntity.getTransform_post_id());

			titleDesc=shareDetails.getTitle_desc();
			if(Util.isEmpty(titleDesc)){
				if(shareDetails.getDetail_desc()!=null){
					titleDesc=shareDetails.getDetail_desc().length()>=10?shareDetails.getDetail_desc().substring(0, 10):shareDetails.getDetail_desc();
				}
			}

			shareDetailsEntity.setTitle_desc(titleDesc);

			 shortDesc=shareDetails.getShort_desc();
			if(Util.isEmpty(shortDesc)){
				if(shareDetails.getDetail_desc()!=null){
					shortDesc=shareDetails.getDetail_desc().length()>=10?shareDetails.getDetail_desc().substring(0, 10):shareDetails.getDetail_desc();
				}
			}


			shareDetailsEntity.setShort_desc(shortDesc);


			if(!Util.isEmpty(shareDetails.getDetail_desc()))
			shareDetailsEntity.setDetail_desc(shareDetails.getDetail_desc());

			if(!Util.isEmpty(newsEntity)){

				if(Util.isEmpty(shareDetailsEntity.getTitle_desc()))
					shareDetailsEntity.setTitle_desc(newsEntity.getTitle());

				if(Util.isEmpty(shareDetailsEntity.getShort_desc()))
					shareDetailsEntity.setShort_desc(newsEntity.getTagDesc());

				if(Util.isEmpty(shareDetails.getDetail_desc()))
					shareDetailsEntity.setDetail_desc(newsEntity.getNewsDesc());

			}



			if(newsEntity!=null){
			if(newsEntity.getVideoUrl()!=null){
				shareDetailsEntity.setContent_type("video");
				shareDetailsEntity.setUrl(newsEntity.getVideoUrl());
			}
			else if(newsEntity.getInnerImgUrl()!=null && newsEntity.getCoverImgUrl()!=null){
				shareDetailsEntity.setContent_type("image");

				if(newsEntity.getInnerImgUrl()!=null ){
					shareDetailsEntity.setUrl(newsEntity.getInnerImgUrl());
				}else{
					shareDetailsEntity.setUrl(newsEntity.getCoverImgUrl());
				}
			}
			else
				shareDetailsEntity.setContent_type("text");

				if(Util.isEmpty(shareDetailsEntity.getTitle_desc()))
					shareDetailsEntity.setTitle_desc(newsEntity.getTitle());
				if(Util.isEmpty(shareDetailsEntity.getShort_desc()))
					shareDetailsEntity.setShort_desc(newsEntity.getTitle());

				if(Util.isEmpty(shareDetailsEntity.getDetail_desc()))
					shareDetailsEntity.setDetail_desc(newsEntity.getNewsDesc());

			}

//			if(shareDetails.getPostMessage()!=null)
//				shareDetailsEntity.setContent_type(shareDetails.getPostMessage().getMessage_type());
			shareDetailsDAO.persist(shareDetailsEntity);
			}
			result = true;
			break;
		case GLOBAL_SHARE:

			shareDetailsEntity=new ShareDetailsEntity();
			shareDetailsEntity.setDetail_desc(shareDetails.getDetail_desc());
			shareDetailsEntity.setContent_type(shareDetails.getContent_type());
			shareDetailsEntity.setDoctor_id(doctor.getDoctorId());
			shareDetailsEntity.setPosted_on(new Date());
			shareDetailsEntity.setSource(postTypeDao.findById(GLOBAL_SHARE));
			shareDetailsEntity.setShare_count(0);
			shareDetailsEntity.setComment_count(0);
			shareDetailsEntity.setShare_count(0);
			shareDetailsEntity.setLikes_count(0);

			 titleDesc=shareDetails.getTitle_desc();
			if(Util.isEmpty(titleDesc)){
				if(shareDetails.getDetail_desc()!=null){
					titleDesc=Util.subString(shareDetails.getDetail_desc(), 100);
				}
			}
			shareDetailsEntity.setTitle_desc(titleDesc);

			 shortDesc=shareDetails.getShort_desc();
			if(Util.isEmpty(shortDesc)){
				if(shareDetails.getDetail_desc()!=null){
					shortDesc=Util.subString(shareDetails.getDetail_desc(), 100);
				}
			}
			shareDetailsEntity.setShort_desc(shortDesc);

			if (shareDetails.getPostMessage().getFileData() != null) {
				String _file = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/messages/";
				_file += FileUtil.copyBinaryData(shareDetails.getPostMessage().getFileData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "messages",shareDetails.getPostMessage().getFileName());
				shareDetailsEntity.setUrl(_file);
			}


			shareDetailsDAO.persist(shareDetailsEntity);
			result=true;
			break;
		}

		return result;
	}

	public List<ShareDetails> findByMemberId(String loginId,Integer receiverId) {
			DoctorEntity doctor=doctorDAO.findByLoginId(loginId);
			List<ShareDetails> shareDetailsList=new ArrayList<ShareDetails>();
			if(doctor!=null){
				List<ShareDetailsEntity> shareDetailsEntities = shareDetailsDAO.showPostsByDoctorToReceiver(doctor.getDoctorId(),receiverId);

				if (shareDetailsEntities != null && !Util.isEmpty(shareDetailsEntities)) {
					for (ShareDetailsEntity entity : shareDetailsEntities) {
						ShareDetails shareDetails = new ShareDetails();
						shareDetails.setContact_id(receiverId);
						BeanUtils.copyProperties(entity, shareDetails);
						if(entity.getReceiverId()!=null && entity.getReceiverId()!=0){
							DoctorEntity doctorEntity = doctorDAO.findByDoctorId(entity.getDoctor_id());
							if(doctorEntity != null && doctorEntity.getUser() != null){
								String shareMesssage="";
								if(doctor.getDoctorId()==doctorEntity.getDoctorId()){
									shareMesssage="You ";
								}else{
									DoctorEntity doctorrecevicedEntity = doctorDAO.findByDoctorId(entity.getDoctor_id());
									if(!Util.isEmpty(doctorrecevicedEntity) && !Util.isEmpty(doctorrecevicedEntity.getUser()))
									shareMesssage=Util.getTitle(doctorrecevicedEntity.getUser().getTitle())+doctorrecevicedEntity.getUser().getFirstName();
								}
								shareMesssage+=" shared the post ";
								if(doctor.getDoctorId()==entity.getReceiverId()){
									shareMesssage="with you ";
								}else{
									DoctorEntity doctorrecevicedEntity = doctorDAO.findByDoctorId(entity.getReceiverId());
									if(!Util.isEmpty(doctorEntity) && !Util.isEmpty(doctorEntity.getUser()))
									shareMesssage="with "+Util.getTitle(doctorrecevicedEntity.getUser().getTitle())+doctorrecevicedEntity.getUser().getFirstName();
								}

								shareDetails.setShareMesssage(shareMesssage);
								shareDetails.setDoctor_Name(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
								if(doctorEntity.getUser().getDisplayPicture()!=null)
								shareDetails.setDisplayPicture(doctorEntity.getUser().getDisplayPicture().getImageUrl());
						}
							shareDetailsList.add(shareDetails);
					}else{

						List<MessageEntity> messages= messageDAO.fetchShareByTopicToDoctor(receiverId,doctor.getDoctorId(),entity.getId());
						if(!Util.isEmpty(messages))
						for(MessageEntity m:messages){
							ShareDetails shareDetails2=new ShareDetails();
							BeanUtils.copyProperties(shareDetails, shareDetails2);
							shareDetails.setPosted_on(m.getPost_date());
							DoctorEntity doctorEntity=doctorDAO.findByDoctorId(m.getMember_id());
							if(doctorEntity != null && doctorEntity.getUser() != null){
								/*String shareMesssage="";
								if(doctor.getDoctorId()==doctorEntity.getDoctorId()){
									shareMesssage="You shared the post this post." ;
								}else{
									if(!Util.isEmpty(doctorEntity) && !Util.isEmpty(doctorEntity.getUser()))
									shareMesssage=Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName()+" shared the post with you.";
								}*/
								String shareMesssage="";
								System.out.println(doctor.getDoctorId() +"------"+entity.getDoctor_id());
								if(doctor.getDoctorId().equals(entity.getDoctor_id())){
									shareMesssage+="You ";
								}else{
									DoctorEntity doctorpostEntity = doctorDAO.findByDoctorId(entity.getDoctor_id());
									
									if(!Util.isEmpty(doctorpostEntity) && !Util.isEmpty(doctorpostEntity.getUser()))
									shareMesssage+=Util.getTitle(doctorpostEntity.getUser().getTitle())+doctorpostEntity.getUser().getFirstName();
								}
								shareMesssage+=" shared the post ";
								if(entity.getReceiverId()!=null){
									if(doctor.getDoctorId().equals(entity.getReceiverId())){
										shareMesssage+="with you ";
									}else{
										DoctorEntity doctorrecevicedEntity = doctorDAO.findByDoctorId(entity.getReceiverId());
										if(!Util.isEmpty(doctorEntity) && !Util.isEmpty(doctorEntity.getUser()))
											shareMesssage+="with "+Util.getTitle(doctorrecevicedEntity.getUser().getTitle())+doctorrecevicedEntity.getUser().getFirstName();
									}
								}
								shareDetails2.setShareMesssage(shareMesssage);
								shareDetails2.setDoctor_Name(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
								if(doctorEntity.getUser().getDisplayPicture()!=null)
									shareDetails2.setDisplayPicture(doctorEntity.getUser().getDisplayPicture().getImageUrl());
							shareDetailsList.add(shareDetails2);
						}


						}

					}

					}
				}
				}
		return shareDetailsList;
	}

	public List<ShareDetails> findByGroupId(Integer groupId,String loginId) {
		DoctorEntity doctor;
			 doctor=doctorDAO.findByLoginId(loginId);

		List<ShareDetailsEntity> shareDetailsEntities = shareDetailsDAO.findByGroupId(groupId);
		List<ShareDetails> shareDetailsList=new ArrayList<ShareDetails>();
		if (shareDetailsEntities != null) {
			for (ShareDetailsEntity entity : shareDetailsEntities) {
				ShareDetails shareDetails = new ShareDetails();
				BeanUtils.copyProperties(entity, shareDetails);
				shareDetails.setGroup_id(groupId);
				DoctorEntity doctorEntity = doctorDAO.findByDoctorId(entity.getDoctor_id());

				if(entity.getGroupId()==null || entity.getGroupId()==0){
					List<MessageEntity> messages= messageDAO.fetchShareByTopicMembers(groupId,entity.getId());
					if(!Util.isEmpty(messages))
					for(MessageEntity m:messages){
						ShareDetails shareDetails2=new ShareDetails();
						BeanUtils.copyProperties(shareDetails, shareDetails2);
						shareDetails.setPosted_on(m.getPost_date());
						doctorEntity=doctorDAO.findByDoctorId(m.getMember_id());
						String shareMessage=" ";

						if(doctorEntity != null && doctorEntity.getUser() != null){
							if(doctor != null){
								List<GroupEntity> groups=groupDAO.findByGroupID(groupId);
								if(!Util.isEmpty(groups)){
									GroupEntity group=groups.get(0);
									if(doctor.getDoctorId()==doctorEntity.getDoctorId()){
										shareMessage="You shared the post with the group " +group.getGroup_name();
									}else{
										shareMessage=Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName()+"shared the post with group "+ group.getGroup_name();
									}
								}
								shareDetails2.setShareMesssage(shareMessage);;
							}
							shareDetails2.setDoctor_Name(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
							if(doctorEntity.getUser().getDisplayPicture()!=null)
								shareDetails2.setDisplayPicture(doctorEntity.getUser().getDisplayPicture().getImageUrl());
						shareDetailsList.add(shareDetails2);
					}


					}
				}else {
				if(doctorEntity != null && doctorEntity.getUser() != null){
					shareDetails.setDoctor_Name(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
					if(doctorEntity.getUser().getDisplayPicture()!=null)
					shareDetails.setDisplayPicture(doctorEntity.getUser().getDisplayPicture().getImageUrl());
				shareDetailsList.add(shareDetails);
			}
				}
			}
		}

		Collections.sort(shareDetailsList, new Comparator<ShareDetails>(){
	           public int compare (ShareDetails m1, ShareDetails m2){
	               return m1.getPosted_on().compareTo(m2.getPosted_on());
	           }
	       });


		return shareDetailsList;
	}

	public void getShareBytopicId(Model model) throws Exception {
		Integer topicId=(Integer) model.asMap().get("topicId");
		ShareDetailsEntity entity=shareDetailsDAO.fetchShareByTopic(topicId);
		ShareDetails shareDetails=new ShareDetails();
		Map shareDetailsMap=new HashMap();
		if(entity!=null){
			BeanUtils.copyProperties(entity, shareDetails);
			DoctorEntity doctor=doctorDAO.findByDoctorId(entity.getDoctor_id());
			if(doctor!=null && doctor.getUser()!=null &&doctor.getUser().getDisplayPicture()!=null)
				shareDetails.setDisplayPicture(doctor.getUser().getDisplayPicture().getImageUrl());

			shareDetailsMap.put("shareDetails", shareDetails);

			if(entity.getTransform_post_id()!=null && entity.getSource()!=null){
				switch(entity.getSource().getId()){
				case TRANSFORM:
					TransformEntity transformEntity=transformDAO.findById(TransformEntity.class, entity.getTransform_post_id());
					shareDetailsMap.put("postMessage", transformEntity);
					break;
				case NEWS_UPDATES:
					NewsEntity newsEntity=newsDAO.findById(NewsEntity.class, entity.getTransform_post_id());
					shareDetailsMap.put("postMessage", newsEntity);
					break;
				}
			}
		}else
			throw new Exception("Invalied topicId ");
		model.addAttribute("shareDetails",shareDetailsMap);
	}

	public Doctor getDoctor(String loginId){
		Doctor doctor=new Doctor();
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		if(doctorEntity!=null){
			doctor.setDoctorId(doctorEntity.getDoctorId());
			doctor.setFirstName(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
			doctor.setStatus(doctorEntity.getStatus());
		}
		return doctor;
	}

	public void myWall(Model model) throws IllegalAccessException, InvocationTargetException {
		String loginId=(String) model.asMap().get("loginId");
		Date timeStamp=(Date) model.asMap().get("timeStamp");
		List shareDetailsList=new ArrayList();
		List<ShareDetailsEntity> shareDetailsEntities=new ArrayList<ShareDetailsEntity>();
		DoctorEntity loggedInDoctor=doctorDAO.findByLoginId(loginId);

		if(!Util.isEmpty(loggedInDoctor)){

			List<ShareDetailsEntity> tmp=shareDetailsDAO.showSharedPostsToDoctor(loggedInDoctor.getDoctorId(),timeStamp);

			if(!Util.isEmpty(tmp))
				shareDetailsEntities.addAll(tmp);

			//show posts to the groups
			tmp=shareDetailsDAO.showSharedPostsToDoctorInGroups(loggedInDoctor.getDoctorId(),timeStamp);
			if(!Util.isEmpty(tmp))
				shareDetailsEntities.addAll(tmp);

			Collections.sort(shareDetailsEntities, new Comparator<ShareDetailsEntity>(){
		           public int compare (ShareDetailsEntity m1, ShareDetailsEntity m2){
		               return m1.getPosted_on().compareTo(m2.getPosted_on());
		           }
		       });



			if (!Util.isEmpty(shareDetailsEntities)) {
				for (ShareDetailsEntity entity : shareDetailsEntities) {

					ShareDetails shareDetails = new ShareDetails();
					BeanUtils.copyProperties(entity, shareDetails);
					shareDetails.setContact_id(shareDetails.getDoctor_id());
					if(entity.getGroupId()!=null&& entity.getGroupId()!=0)
						shareDetails.setGroup_id(entity.getGroupId());

					if(entity.getReceiverId()!=null && entity.getReceiverId()!=0){
						UserDetails userDetails=	getDoctorDetails(entity.getDoctor_id(),loggedInDoctor.getDoctorId());
						shareDetails.setUserDetails(userDetails);
					}

					//get Messages
					List _messages=new ArrayList();
					List<MessageEntity> messageEntities=messageDAO.fetchShareByTopic(entity.getId());
					if(!Util.isEmpty(messageEntities))
					for(MessageEntity messageEntity:messageEntities){

						System.out.println("messages are not empty");
						PostMessage postMessage=new PostMessage();
						BeanUtils.copyProperties(messageEntity, postMessage);

						DoctorEntity doctorEntity = doctorDAO.findByDoctorId(messageEntity.getMember_id());
						if(doctorEntity != null && doctorEntity.getUser() != null){
							postMessage.setDoctor_Name(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
							if(doctorEntity.getUser().getDisplayPicture()!=null)
							postMessage.setDisplayPicture(doctorEntity.getUser().getDisplayPicture().getImageUrl());
						}

						if((entity.getReceiverId()==null ||entity.getReceiverId()==0) && (entity.getGroupId()==null ||entity.getGroupId()==0)){
							if(messageEntity.getReceiver_id()!=0){
								if(shareDetails.getContact_id()==null ||shareDetails.getContact_id()==0)
								shareDetails.setContact_id(messageEntity.getReceiver_id());
							}else if(messageEntity.getGroup_id()!=0){
								shareDetails.setGroup_id(messageEntity.getGroup_id());
							}
						}

						//

						String _message1="";
						if(messageEntity.getMember_id()!=0){
							if(messageEntity.getMember_id()==loggedInDoctor.getDoctorId()){
								_message1="You";
							}else{
								if(!Util.isEmpty(doctorEntity) && !Util.isEmpty(doctorEntity.getUser()))
								_message1=Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName();
							}
						}
						_message1+=" shared the post ";

						if(messageEntity.getReceiver_id()!=0){
							DoctorEntity receiver = doctorDAO.findByDoctorId(postMessage.getReceiver_id());

							if(messageEntity.getReceiver_id()==loggedInDoctor.getDoctorId()){
								_message1+="with you.";
							}else{
								if(!Util.isEmpty(receiver) && !Util.isEmpty(receiver.getUser()))
								_message1+="with "+Util.getTitle(receiver.getUser().getTitle())+receiver.getUser().getFirstName()+".";
							}
						}else if(messageEntity.getGroup_id()!=0){
							List<GroupEntity> groups=groupDAO.findByGroupID(messageEntity.getGroup_id());
							if(!Util.isEmpty(groups)){
							GroupEntity group=groups.get(0);
							_message1+=" with the group "+group.getGroup_name();
							}
						}else
							_message1=postMessage.getMessage();

						postMessage.setMessage(_message1);


						//
						Map _message=new HashMap();
						List<PostMessage> comments=new ArrayList<PostMessage>();

						List<CommentEntity> commentEntities=commentDAO.findByMessageId(messageEntity.getMessage_id(),loggedInDoctor.getDoctorId());
						if(!Util.isEmpty(commentEntities)){
							for(CommentEntity commentEntity:commentEntities){
								System.out.println("Comments are not empty");
								PostMessage comment=new PostMessage();
								BeanUtils.copyProperties(commentEntity, comment);

								DoctorEntity commentDoctorEntity = doctorDAO.findByDoctorId(commentEntity.getMember_id());
								if(commentDoctorEntity != null && commentDoctorEntity.getUser() != null){
									comment.setDoctor_Name(Util.getTitle(commentDoctorEntity.getUser().getTitle())+commentDoctorEntity.getUser().getFirstName());
									if(commentDoctorEntity.getUser().getDisplayPicture()!=null)
									comment.setDisplayPicture(commentDoctorEntity.getUser().getDisplayPicture().getImageUrl());
								}
								comments.add(comment);
							}
						}
						_message.put("message", postMessage);
						_message.put("comments", comments);
						_messages.add(_message);
					}


					shareDetails.setMessages(_messages);

					DoctorEntity doctorEntity = doctorDAO.findByDoctorId(entity.getDoctor_id());
						if(doctorEntity != null && doctorEntity.getUser() != null){
							shareDetails.setDoctor_Name(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName());
							if(doctorEntity.getUser().getDisplayPicture()!=null)
							shareDetails.setDisplayPicture(doctorEntity.getUser().getDisplayPicture().getImageUrl());
					}

					shareDetailsList.add(shareDetails);
				}
			}


		}
		sort(shareDetailsList,Sorting.DESC);
		model.addAttribute("shareDetails", shareDetailsList);
	}

	private void sort(List shareDetailsList, int desc) {
		if(!Util.isEmpty(shareDetailsList)){
			Collections.sort(shareDetailsList,new Comparator<ShareDetails>() {

				@Override
				public int compare(ShareDetails share1, ShareDetails share2) {

						Date maxDate2=share2.getPosted_on();
						if(!Util.isEmpty(share2.getMessages()))
						for(Map m1:(List<Map>)share2.getMessages()){
							if(!Util.isEmpty(m1.get("message"))){
								PostMessage pm=(PostMessage)m1.get("message");
									if(Util.isEmpty(maxDate2))
										maxDate2=pm.getPost_date();
									else if(maxDate2.compareTo(pm.getPost_date())>0)
										maxDate2=pm.getPost_date();
							}

							if(!Util.isEmpty(m1.get("comments"))){
								for(PostMessage pm:(List<PostMessage>)m1.get("comments")){
									if(Util.isEmpty(maxDate2))
										maxDate2=pm.getPost_date();
									else if(maxDate2.compareTo(pm.getPost_date())>0)
										maxDate2=pm.getPost_date();
								}
							}
						}


						Date maxDate1=share1.getPosted_on();
						if(!Util.isEmpty(share1.getMessages()))
						for(Map m1:(List<Map>)share1.getMessages()){
							if(!Util.isEmpty(m1.get("message"))){
								PostMessage pm=(PostMessage)m1.get("message");
									if(Util.isEmpty(maxDate1))
										maxDate1=pm.getPost_date();
									else if(maxDate1.compareTo(pm.getPost_date())>0)
										maxDate1=pm.getPost_date();

							}

							if(!Util.isEmpty(m1.get("comments"))){
								for(PostMessage pm:(List<PostMessage>)m1.get("comments")){
									if(Util.isEmpty(maxDate1))
										maxDate1=pm.getPost_date();
									else if(maxDate1.compareTo(pm.getPost_date())>0)
										maxDate1=pm.getPost_date();
								}
							}
						}


					return maxDate1.compareTo(maxDate2);
				}
			});
		}

	}

	private void getDoctorInfo(ShareDetails shareDetails, DoctorEntity doctorEntity) {
			if(doctorEntity != null)
			{
				Doctor doctor=new Doctor();
				UserEntity user = doctorEntity.getUser();

				if(user != null)
				{
					doctor = new Doctor();
					doctor.setEmailId(user.getEmailId());
					doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
					doctor.setLastName(user.getLastName());
					doctor.setPhoneNo(user.getPhoneNo());
					doctor.setAlias(user.getAlias());
					doctor.setTitle(user.getTitle());
					doctor.setAlternateEmailId(user.getAlternateEmailId());
					doctor.setMobileNo(user.getMobileNo());
					doctor.setDoctorId(doctorEntity.getDoctorId());
					doctor.setUsername(user.getSecurity()!=null ? user.getSecurity().getLoginId():"");
//					RoleEntity role = user.getRole();
//					if(role != null)
//					{
//						doctor.setRoleName(role.getRoleName());
//						doctor.setRoleId(role.getRoleId());
//					}
					List<Location> locations = new ArrayList<Location>();
					for(LocationEntity locationEntity : user.getLocations())
					{
						Location location = new Location();
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setState(locationEntity.getState());
						location.setType(Util.getLocationType(locationEntity.getType()));
						location.setZipcode(locationEntity.getZipcode());
						locations.add(location);

					}
					doctor.setLocations(locations);

					if(user.getDisplayPicture()!=null)
					{
						doctor.setdPicture(user.getDisplayPicture().getImageUrl());
					}
				}
				doctor.setQualification(doctorEntity.getQualification());
				doctor.setRegistrationNumber(doctorEntity.getRegistrationNumber());
				doctor.setRegistrationYear(doctorEntity.getRegistrationYear());
				doctor.setStateMedCouncil(doctorEntity.getStateMedCouncil());
				doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
				if(doctorEntity.getTherapeuticId() != null)
				{
					try
					{
						if(doctorEntity.getTherapeuticId()!=null)
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(doctorEntity.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
					}
					catch(Exception e)
					{
						//Ignore
					}
				}
			}
	}

	public void getContact(Model model) {
		String username=(String) model.asMap().get("username");
		Integer connectionId=(Integer) model.asMap().get("connectionId");
		DoctorEntity d=doctorDAO.findByLoginId(username);

		DoctorEntity de = doctorDAO.findByDoctorId(connectionId);
		if(!Util.isEmpty(de)){
//			About about = new About();
//			about.setName(de.getUser().getFirstName()+" "+de.getUser().getLastName());

//			if(de.getTherapeuticId()!=null){
//				TherapeuticAreaEntity t=therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
//						Integer.parseInt(de.getTherapeuticId()));
//
//				if(t!=null)
//					 about.setDesignation(t.getTherapeuticName());
//
//				List<LocationEntity> lentities=locationDAO.findLocationsByUserId(de.getUser().getUserId());
//				if(lentities!=null && lentities.size()>0){
//					for(LocationEntity locationEntity:lentities){
//						if(!Util.isEmpty(locationEntity.getCity()) && Util.isEmpty(about.getLocation())){
//							about.setLocation(locationEntity.getCity());
//							break;
//						}
//					}
//				}

				Map result=new HashMap();
				result.put("userDetails", getDoctorDetails(de.getDoctorId(),d.getDoctorId()));
				result.put("posts", findByMemberId(username, connectionId));
				model.addAttribute("result", result);
			}

		}


	public void getGroup(Model model) {
		Integer groupId=(Integer) model.asMap().get("groupId");
		Map result=new HashMap();
		result.put("group", getGroup(groupId));
		result.put("posts", findByGroupId(groupId,"null"));
		model.addAttribute("result", result);
	}

	public void getPendingItems(Model model) {
		PendingCount pendingCount=(PendingCount) model.asMap().get("pendingCount");
		UserEntity user=userDAO.findByEmailId((String)model.asMap().get("username"));
		PendingCountsEntity pendingCountsEntity=pendingCountDAO.findByUserId(user.getUserId());

		if(pendingCountsEntity==null){
			List surveys=null;
			List notifications=null;
			int doctorPlusCount=0;

			DoctorEntity d=doctorDAO.findByLoginId((String)model.asMap().get("username"));
			boolean isDoctor=!Util.isEmpty(d)&& !Util.isEmpty(d.getDoctorId());
			PharmaRepEntity pharma=pharmaRepDAO.findByLoginId((String)model.asMap().get("username"));
			boolean isPharmarep=!Util.isEmpty(pharma)&& !Util.isEmpty(pharma.getRepId());

			if(isDoctor){
				surveys=surveyDAO.findPendingSurveys(d.getDoctorId());
				notifications=doctorNotificationDAO.findByStatus(d.getDoctorId(),"Pending");
				doctorPlusCount = connectionDAO.findPendingConnectionsCount(d.getDoctorId());
				 List pendingGroups=memberDAO.findPendingGroupMembers(d.getDoctorId());
				 if(pendingGroups!=null && !pendingGroups.isEmpty())
					 doctorPlusCount+=pendingGroups.size();
			}else if(isPharmarep){
					surveys = pharmaRepSurveyDAO.findPendingSurveys(pharma.getRepId());
					notifications=pharmaRepNotificationDAO.findByStatus(pharma.getRepId(),"Pending");
			}

			pendingCountsEntity=new PendingCountsEntity();
			pendingCountsEntity.setUserId(user.getUserId());
			pendingCountsEntity.setSurveysCount(Util.isEmpty(surveys)?0:surveys.size());
			pendingCountsEntity.setNotificationsCount(Util.isEmpty(notifications)?0:notifications.size());
			pendingCountsEntity.setDoctorPlusCount(doctorPlusCount);
		}

		if(Boolean.valueOf(pendingCount.getResetSurveyCount()+"")){
			pendingCountsEntity.setSurveysCount(0);
			pendingCount.setSurveyCount(0);
		}

		if(Boolean.valueOf(pendingCount.getResetNotificationCount()+"")){
			pendingCountsEntity.setNotificationsCount(0);
			pendingCount.setNotificationsCount(0);
		}

		if(Boolean.valueOf(pendingCount.getResetDoctorPlusCount()+"")){
			pendingCountsEntity.setDoctorPlusCount(0);
			pendingCount.setDoctorPlusCount(0);
		}


		pendingCount.setSurveyCount(pendingCountsEntity.getSurveysCount());
		pendingCount.setNotificationsCount(pendingCountsEntity.getNotificationsCount());
		pendingCount.setDoctorPlusCount(pendingCountsEntity.getDoctorPlusCount());

		pendingCountDAO.merge(pendingCountsEntity);

		model.addAttribute("result",pendingCount);
	}

	public List<Doctor> findDoctorContactBySearch(String loginId, String name, int groupId) {
		List<Doctor> doctors = new ArrayList<Doctor>();
		DoctorEntity doctorEntity1 = doctorDAO.findByLoginId(loginId);
		List<DoctorEntity> doctorEntity = doctorDAO.findDoctorsbySearch(doctorEntity1.getDoctorId(), name);
		if (doctorEntity != null && doctorEntity.size() > 0) {
			for (DoctorEntity docEn : doctorEntity) {
				Doctor doctor = new Doctor();
				UserEntity user = docEn.getUser();
				if (user != null) {
					doctor.setEmailId(user.getEmailId());
					doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//					doctor.setMiddleName(user.getMiddleName());
					doctor.setLastName(user.getLastName());
					doctor.setPhoneNo(user.getPhoneNo());
					doctor.setAlias(user.getAlias());
					doctor.setTitle(user.getTitle());
					doctor.setAlternateEmailId(user.getAlternateEmailId());
					doctor.setMobileNo(user.getMobileNo());
					doctor.setDoctorId(docEn.getDoctorId());

					if (user.getDisplayPicture() != null) {
						doctor.setdPicture(user.getDisplayPicture().getImageUrl());
					}
				}
				doctor.setTherapeuticId(docEn.getTherapeuticId());
				if (docEn.getTherapeuticId() != null) {
					try {
						if (docEn.getTherapeuticId() != null) {
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
									.findById(TherapeuticAreaEntity.class, Integer.parseInt(docEn.getTherapeuticId()));
							if (therapeuticAreaEntity != null) {
								doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
					} catch (Exception e) {
						// Ignore
					}
				}
				doctors.add(doctor);
			}
			if(groupId==0){
				List<ConnectionEntity> connInstances = connectionDAO.findByDocID(doctorEntity1.getDoctorId());
				if (connInstances.size() > 0) {


					Iterator<ConnectionEntity> connections=connInstances.iterator();
					while(connections.hasNext()){
						ConnectionEntity conn=connections.next();
						Iterator<Doctor> it = doctors.iterator();
						while(it.hasNext()){
							Doctor doc=it.next();
							if(doc.getDoctorId().equals(conn.getConnID()))
								it.remove();
						}

					}
				}
			}else{
				List<MemberEntity> memberEntities=memberDAO.findByGroupID(groupId);
				if(!Util.isEmpty(memberEntities)){
					Iterator<MemberEntity> mem=memberEntities.iterator();
					while(mem.hasNext()){
						MemberEntity member=mem.next();
						Iterator<Doctor> it = doctors.iterator();
						while(it.hasNext()){
							Doctor doc=it.next();
							if(doc.getDoctorId().equals(member.getMember_id()))
								it.remove();
						}

					}
				}
			}

		}

		return doctors;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object medicineSearch(String searchTerm) {
		String getMedicine=MedRepProperty.getInstance().getProperties("drugsearch.api.url")+"/autocomplete/medicines/brands/{searchTerm}?access_token={accessToken}";
		RestTemplate restTemplate=new RestTemplate();

		HashMap<String,String> requestParams=new HashMap<String,String>();
		requestParams.put("searchTerm", searchTerm);
		requestParams.put("accessToken", getDrugSearchApiToken());
		Object obj= restTemplate.exchange(getMedicine, HttpMethod.GET, getRequestEntity(null), Object.class,requestParams);
		 return ((org.springframework.http.ResponseEntity)obj).getBody();
	}

	public Object medicineAlternatives(String searchTerm, int limit,int page) {
		String getMedicine=MedRepProperty.getInstance().getProperties("drugsearch.api.url")+"/medicines/brands/{searchTerm}/alternatives?page={page}&size={size}&access_token={accessToken}";
		RestTemplate restTemplate=new RestTemplate();
		HashMap<String,String> requestParams=new HashMap<String,String>();
		requestParams.put("searchTerm", searchTerm);
		requestParams.put("page", page+"");
		requestParams.put("size", limit+"");
		requestParams.put("accessToken", getDrugSearchApiToken());
		Object obj= restTemplate.exchange(getMedicine, HttpMethod.GET, getRequestEntity(null), Object.class,requestParams);
		return ((org.springframework.http.ResponseEntity)obj).getBody();
	}
	private String getDrugSearchApiToken(){
		String authUrl=MedRepProperty.getInstance().getProperties("drugsearch.api.url")+"/oauth/token.json";

		HashMap<String,String> request=new HashMap<String,String>();
		request.put("grant_type", MedRepProperty.getInstance().getProperties("drugsearch.api.grant_type"));
		request.put("client_id", MedRepProperty.getInstance().getProperties("drugsearch.api.client_id"));
		request.put("client_secret",MedRepProperty.getInstance().getProperties("drugsearch.api.client_secret"));
		request.put("scope","public");// MedRepProperty.getInstance().getProperties("drugsearch.api.scope"));


		RestTemplate restTemplate=new RestTemplate();

		Object obj =restTemplate.exchange(authUrl,HttpMethod.POST, getRequestEntity(request), Object.class);
		return (String)((java.util.LinkedHashMap)((org.springframework.http.ResponseEntity)obj).getBody()).get("access_token");

//		try {
//			sendPost();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";

	}

	private HttpEntity<Map> getRequestEntity(HashMap<String,String> requestBody) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("User-Agent", USER_AGENT);
		HttpEntity<Map> _request=null;
		if(requestBody==null)
			_request=new HttpEntity<Map>(headers);
		else
		_request = new HttpEntity<Map>(requestBody,headers);
		return _request;
	}

	private final String USER_AGENT = "Mozilla/5.0";
	private void sendPost() throws Exception {

		String authUrl=MedRepProperty.getInstance().getProperties("drugsearch.api.url")+"/oauth/token.json";
		URL obj = new URL(authUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Content-Type", "application/json");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		String urlParameters="{\"grant_type\":\""+ MedRepProperty.getInstance().getProperties("drugsearch.api.grant_type")+"\","+
		"\"client_id\":\""+ MedRepProperty.getInstance().getProperties("drugsearch.api.client_id")+"\","+
		"\"client_secret\":\""+MedRepProperty.getInstance().getProperties("drugsearch.api.client_secret")+"\","+
		"\"scope\":\"public\"}";
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		StringBuffer response = new StringBuffer();
		int responseCode = con.getResponseCode();

		if(responseCode==200){
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();
		}
		System.out.println(response.toString());

	}

}