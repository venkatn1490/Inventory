package com.medrep.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.medrep.app.dao.AboutDao;
import com.medrep.app.dao.AwardsDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.EducationalQualificationDAO;
import com.medrep.app.dao.InterestedAreasDAO;
import com.medrep.app.dao.LocationDAO;
import com.medrep.app.dao.PublicationDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.dao.WebinarsDAO;
import com.medrep.app.dao.WorkExperiencesDAO;
import com.medrep.app.entity.AboutEntity;
import com.medrep.app.entity.AwardsEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.EducationalQualificationEntity;
import com.medrep.app.entity.InterestedAreasEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.PublicationEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.entity.WebinarsEntity;
import com.medrep.app.entity.WorkExperienceEntity;
import com.medrep.app.model.About;
import com.medrep.app.model.Awards;
import com.medrep.app.model.EducationalQualification;
import com.medrep.app.model.InterestedAreas;
import com.medrep.app.model.Location;
import com.medrep.app.model.Publication;
import com.medrep.app.model.TherapeuticArea;
import com.medrep.app.model.User;
import com.medrep.app.model.Webinars;
import com.medrep.app.model.WorkExperience;
import com.medrep.app.util.Util;

@Service("doctorInfoService")
@Transactional
public class DoctorInfoService {

	@Autowired
	AboutDao aboutDao;
	@Autowired
	UserDAO userDAO;
	@Autowired
	DoctorDAO doctorDAO;
	@Autowired
	WorkExperiencesDAO workExperiencesDAO;
	@Autowired
	InterestedAreasDAO interestedAreasDAO;
	@Autowired
	EducationalQualificationDAO educationalQualificationDAO;
	@Autowired
	PublicationDAO publicationDAO;
	@Autowired
	WebinarsDAO webinarsDAO;
	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;
	@Autowired
	LocationDAO locationDAO;
	
	@Autowired
	AwardsDAO awardsDAO;

	public void addAbout(Model model) {
		About about = (About) model.asMap().get("about");
		AboutEntity ae = new AboutEntity();
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		List<DoctorEntity> doctor = userDAO.getDoctorId((Integer) user.get(0)[0]);
		ae.setDoctorEntity(doctor.get(0));
		BeanUtils.copyProperties(about, ae);
		aboutDao.persist(ae);
		model.addAttribute("id",ae.getId());
	}

	@Deprecated
	public void getAbout(Model model) {
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		AboutEntity ae = aboutDao.findByDoctorId(de.getDoctorId());
		About about = new About();
		if(null!=ae){
		BeanUtils.copyProperties(ae, about);
		about.setDoctorId(ae.getDoctorEntity().getDoctorId());
		}
		model.addAttribute("about", about);
	}

	public void deleteAbout(Model model) {
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		AboutEntity ae = aboutDao.findByDoctorId(de.getDoctorId());
		aboutDao.remove(ae);
//		model.addAttribute("id",ae.getId());
	}

	public void updateAbout(Model model) {
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		AboutEntity ae = aboutDao.findByDoctorId(de.getDoctorId());
		About about = (About) model.asMap().get("about");
		ae.setDesignation(about.getDesignation());
		ae.setLocation(about.getLocation());
		ae.setName(about.getName());		
		aboutDao.persist(ae);
		
//		model.addAttribute("id",ae.getId());
	}
	
	public void updateDoctorExp(Model model) {
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		About about = (About) model.asMap().get("about");
		de.setWorkExpMonths(about.getWorkExpMonth());
		de.setWorkExpYears(about.getWorkExpYear());
		doctorDAO.persist(de);
		
//		model.addAttribute("id",ae.getId());
	}
	
	

	public void addAwards(Model model) {
		List<Awards> awards = (List<Awards>) model.asMap().get("awards");

		List<AwardsEntity> entities=new ArrayList<AwardsEntity>(awards.size());
		for(Awards pub:awards){
			AwardsEntity awardsEntity=new AwardsEntity();
			BeanUtils.copyProperties(pub, awardsEntity);
			entities.add(awardsEntity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		awardsDAO.saveAwardsForDoctor(entities, de);
		List<Integer> ids=new ArrayList<Integer>();
		for(AwardsEntity entity:entities){
			ids.add(entity.getId());
		}
		model.addAttribute("id",ids);

	}
	
	
	public void updateAwards(Model model) throws Exception {
		List<Awards> awards=(List<Awards>) model.asMap().get("awards");

		List<AwardsEntity> entities=new ArrayList<AwardsEntity>(awards.size());
		for(Awards pub:awards){
			AwardsEntity	entity=new AwardsEntity();
			BeanUtils.copyProperties(pub,entity);
			entities.add(entity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		awardsDAO.saveOrUpdate(entities,de);
	}
	public void addWebinars(Model model) {
		List<Webinars> webinars = (List<Webinars>) model.asMap().get("webinars");

		List<WebinarsEntity> entities=new ArrayList<WebinarsEntity>(webinars.size());
		for(Webinars pub:webinars){
			WebinarsEntity webinarsEntity=new WebinarsEntity();
			BeanUtils.copyProperties(pub, webinarsEntity);
			entities.add(webinarsEntity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		webinarsDAO.savePublicationsForDoctor(entities, de);
		List<Integer> ids=new ArrayList<Integer>();
		for(WebinarsEntity entity:entities){
			ids.add(entity.getId());
		}
		model.addAttribute("id",ids);

	}
	public void updateWebinars(Model model) throws Exception {
		List<Webinars> webinars=(List<Webinars>) model.asMap().get("webinars");

		List<WebinarsEntity> entities=new ArrayList<WebinarsEntity>(webinars.size());
		for(Webinars pub:webinars){
			WebinarsEntity entity=new WebinarsEntity();
			BeanUtils.copyProperties(pub,entity);
			entities.add(entity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		webinarsDAO.saveOrUpdate(entities,de);
	}

	
	public void addWorkExperience(Model model) {
		List<WorkExperience> workExperiencesList = (List<WorkExperience>) model.asMap()
				.get("workExperiencesList");

		List<WorkExperienceEntity> entities=new ArrayList<WorkExperienceEntity>(workExperiencesList.size());
		for(WorkExperience w:workExperiencesList){
			WorkExperienceEntity entity=new WorkExperienceEntity();
			BeanUtils.copyProperties(w, entity);
			entities.add(entity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		workExperiencesDAO.saveWorkExperienceForDoctor(entities, de);
		List<Integer> ids=new ArrayList<Integer>();
		for(WorkExperienceEntity entity:entities){
			ids.add(entity.getId());
		}
		model.addAttribute("id",ids);
	}

	public void updateWorkExperience(Model model) throws Exception {
		List<WorkExperience> workExperiencesList=(List<WorkExperience>) model.asMap().get("workExperiencesList");
		List<WorkExperienceEntity> entities=new ArrayList<WorkExperienceEntity>(workExperiencesList.size());

		for(WorkExperience w:workExperiencesList){
			WorkExperienceEntity entity=new WorkExperienceEntity();
			BeanUtils.copyProperties(w, entity);
			entities.add(entity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		workExperiencesDAO.saveOrUpdate(entities,de);
		/*List<Integer> ids=new ArrayList<Integer>();
		for(WorkExperienceEntity entity:entities){
			ids.add(entity.getId());
		}
		model.addAttribute("id",ids);

*/	}

	public void getWorkExperience(Model model) {
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		List<WorkExperienceEntity> workExperienceEntities = workExperiencesDAO.findByDoctorId(de.getDoctorId());
		List<WorkExperience> dto=new ArrayList<WorkExperience>(workExperienceEntities.size());
		if(workExperienceEntities!=null){
		for(WorkExperienceEntity entity:workExperienceEntities){
			WorkExperience workExperience=new WorkExperience();
			BeanUtils.copyProperties(entity, workExperience);
			dto.add(workExperience);
		}
		BeanUtils.copyProperties(workExperienceEntities, dto);
		}

		model.addAttribute("workExperiences", dto);
	}

	public void deleteWorkExperience(Model model) throws Exception{
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		Integer workExperienceId = (Integer) model.asMap().get("workExperienceId");
		WorkExperienceEntity we = workExperiencesDAO.findById(WorkExperienceEntity.class, workExperienceId);
		if(we==null)
			throw new Exception("Work Experience details doesn't exist");
		if(!username.equals(we.getDoctorEntity().getUser().getEmailId()))
			throw new Exception("Invalied Access");
		workExperiencesDAO.remove(we);

//		model.addAttribute("id",we.getId());

	}

	public void deleteWebinars(Model model) throws Exception{
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		Integer webinarsId = (Integer) model.asMap().get("webinarsId");
		WebinarsEntity we = webinarsDAO.findById(WebinarsEntity.class, webinarsId);
		if(we==null)
			throw new Exception("Webinars details doesn't exist");
		if(!username.equals(we.getDoctorEntity().getUser().getEmailId()))
			throw new Exception("Invalied Access");
		webinarsDAO.remove(we);

//		model.addAttribute("id",we.getId());

	}
	public void deleteAwards(Model model) throws Exception{
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		Integer awardsId = (Integer) model.asMap().get("awardsId");
		AwardsEntity ae = awardsDAO.findById(AwardsEntity.class, awardsId);
		if(ae==null)
			throw new Exception("Awards details doesn't exist");
		if(!username.equals(ae.getDoctorEntity().getUser().getEmailId()))
			throw new Exception("Invalied Access");
		awardsDAO.remove(ae);

//		model.addAttribute("id",we.getId());

	}
	public void deleteInterest(Model model) throws Exception{
		String loginId=(String) model.asMap().get("username");
		DoctorEntity d=doctorDAO.findByLoginId(loginId);
		Integer interestId = (Integer) model.asMap().get("interestId");
		InterestedAreasEntity iae = interestedAreasDAO.findById(InterestedAreasEntity.class, interestId);
		if(iae==null){
			getInterest(model);
			List interestedAreas=(List) model.asMap().get("interests");
			if(Util.isEmpty(interestedAreas))
				throw new Exception("There should be atleast one interest");
			TherapeuticAreaEntity t=therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, interestId);
			if(t!=null&& d.getTherapeuticId().equals(t.getTherapeuticId()+"")){

				InterestedAreasEntity i=interestedAreasDAO.findMaxInterest(d.getDoctorId());
				interestedAreasDAO.remove(i);
				TherapeuticAreaEntity newTheraPeutic=new TherapeuticAreaEntity();
				newTheraPeutic.setTherapeuticName(i.getName());
				therapeuticAreaDAO.persist(newTheraPeutic);
				d.setTherapeuticId(newTheraPeutic.getTherapeuticId()+"");
				doctorDAO.merge(d);
			}else
				throw new Exception("Unable to delete TherapeutiArea");
		}else{
			getInterest(model);
			List interestedAreas=(List) model.asMap().get("interests");


		if(!loginId.equals(iae.getDoctorEntity().getUser().getEmailId()))
			throw new Exception("Invalied Access");
		interestedAreasDAO.remove(iae);
		}
//		model.addAttribute("id",iae.getId());
	}

	public void addInterest(Model model) {
		List<InterestedAreas> interestedAreas = (List<InterestedAreas>) model.asMap().get("interests");

		List<InterestedAreasEntity> entities=new ArrayList<InterestedAreasEntity>(interestedAreas.size());
		for(InterestedAreas  interestedArea:interestedAreas){
			InterestedAreasEntity entity=new InterestedAreasEntity();
			BeanUtils.copyProperties(interestedArea,entity);
			entities.add(entity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		interestedAreasDAO.saveInterestsForDoctor(entities, de);
		List<Integer> ids=new ArrayList<Integer>();
		for(InterestedAreasEntity entity:entities){
			ids.add(entity.getId());
		}
		model.addAttribute("id",ids);


	}

	public void updateInterest(Model model) throws Exception {
		List<InterestedAreas> interestedAreas=(List<InterestedAreas>) model.asMap().get("interests");

		List<InterestedAreasEntity> entities=new ArrayList<InterestedAreasEntity>(interestedAreas.size());

		for(InterestedAreas i:interestedAreas){
			InterestedAreasEntity entity=new InterestedAreasEntity();
			BeanUtils.copyProperties(i, entity);
			entities.add(entity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		interestedAreasDAO.saveOrUpdate(entities,de);
		/*List<Integer> ids=new ArrayList<Integer>();
		for(InterestedAreasEntity entity:entities){
			ids.add(entity.getId());
		}
		model.addAttribute("id",ids);*/

	}

	public void getInterest(Model model)  throws Exception{
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		List<InterestedAreasEntity> interests = interestedAreasDAO.findByDoctorId(de.getDoctorId());

		List<InterestedAreas> dto=new ArrayList<InterestedAreas>(interests.size());
		for(InterestedAreasEntity entity:interests){
			InterestedAreas interest=new InterestedAreas();
			BeanUtils.copyProperties(entity, interest);
			dto.add(interest);
		}

		model.addAttribute("interests", dto);
	}

	public void deletePublication(Model model) throws Exception{
		String username=(String) model.asMap().get("username");
		Integer publicationId = (Integer) model.asMap().get("publicationId");
		PublicationEntity pub = publicationDAO.findById(PublicationEntity.class, publicationId);
		if(pub==null)
			throw new Exception("Publcation doesn't exist");
		if(!username.endsWith(pub.getDoctorEntity().getUser().getEmailId()))
			throw new Exception("Invalied Access");
		publicationDAO.remove(pub);
//		model.addAttribute("id",pub.getId());
	}

	public void getPublication(Model model) {
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		List<PublicationEntity> publications = publicationDAO.findByDoctorId(de.getDoctorId());

		List<Publication> dto=new ArrayList<Publication>(publications.size());
		for(PublicationEntity entity:publications){
			Publication pub=new Publication();
			BeanUtils.copyProperties(entity,pub);
			dto.add(pub);
		}

		model.addAttribute("publications", dto);
	}

	public void updatePublication(Model model) throws Exception {
		List<Publication> publications=(List<Publication>) model.asMap().get("publications");

		List<PublicationEntity> entities=new ArrayList<PublicationEntity>(publications.size());
		for(Publication pub:publications){
			PublicationEntity entity=new PublicationEntity();
			BeanUtils.copyProperties(pub,entity);
			entities.add(entity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		publicationDAO.saveOrUpdate(entities,de);
		/*List<Integer> ids=new ArrayList<Integer>();
		for(PublicationEntity entity:entities){
			ids.add(entity.getId());
		}
		model.addAttribute("id",ids);*/

	}

	public void addPublication(Model model) {
		List<Publication> publications = (List<Publication>) model.asMap().get("publications");

		List<PublicationEntity> entities=new ArrayList<PublicationEntity>(publications.size());
		for(Publication pub:publications){
			PublicationEntity publicationEntity=new PublicationEntity();
			BeanUtils.copyProperties(pub, publicationEntity);
			entities.add(publicationEntity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		publicationDAO.savePublicationsForDoctor(entities, de);
		List<Integer> ids=new ArrayList<Integer>();
		for(PublicationEntity entity:entities){
			ids.add(entity.getId());
		}
		model.addAttribute("id",ids);

	}

	public void getEducation(Model model) {
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		List<EducationalQualificationEntity> educationsDetails = educationalQualificationDAO
				.findByDoctorId(de.getDoctorId());


		List<EducationalQualification> ed=new ArrayList<EducationalQualification>(educationsDetails.size());
		for(EducationalQualificationEntity entity:educationsDetails){
			EducationalQualification eq=new EducationalQualification();
			BeanUtils.copyProperties(entity,eq);
			ed.add(eq);
		}

		model.addAttribute("educationsDetails", ed);
	}

	/**
	 *
	 * @param model
	 */
	public void deleteEducation(Model model) throws Exception{
		Integer educationId = (Integer) model.asMap().get("educationId");
		EducationalQualificationEntity education = educationalQualificationDAO
				.findById(EducationalQualificationEntity.class, educationId);
		String username=(String) model.asMap().get("username");
		if(education==null)
			throw new Exception("Education details doesn't exist");
		if(!username.equals(education.getDoctorEntity().getUser().getEmailId()))
			throw new Exception("Invalied Access");
		educationalQualificationDAO.remove(education);
//		model.addAttribute("id",education.getId());
	}

	/**
	 *
	 * @param model
	 * @throws Exception
	 */
	public void updateEducation(Model model) throws Exception {
		List<EducationalQualification> educationalQualifications=(List<EducationalQualification>) model.asMap().get("educationDetails");

		List<EducationalQualificationEntity> entities=new ArrayList<EducationalQualificationEntity>(educationalQualifications.size());

		for(EducationalQualification eq:educationalQualifications){
			EducationalQualificationEntity entity=new EducationalQualificationEntity();
			BeanUtils.copyProperties(eq	,entity);
			entities.add(entity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		educationalQualificationDAO.saveOrUpdate(entities,de);
		/*List<Integer> ids=new ArrayList<Integer>();
		for(EducationalQualificationEntity entity:entities){
			ids.add(entity.getId());
		}
		model.addAttribute("id",ids);*/

	}

	/**
	 *
	 * @param model
	 */
	public void addEducation(Model model) {
		List<EducationalQualification> eduEntities = (List<EducationalQualification>) model.asMap().get("educationDetails");
		List<EducationalQualificationEntity> entities=new ArrayList<EducationalQualificationEntity>(eduEntities.size());
		for(EducationalQualification  qualification:eduEntities){
			EducationalQualificationEntity entity=new EducationalQualificationEntity();
			BeanUtils.copyProperties(qualification,entity);
			entities.add(entity);
		}

		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
		educationalQualificationDAO.saveEducationDetailsForDoctor(entities, de);
		List<Integer> ids=new ArrayList<Integer>();
		for(EducationalQualificationEntity entity:entities){
			ids.add(entity.getId());
		}
		model.addAttribute("id",ids);

	}

	public void fetchDoctorInfo(Model model) throws Exception {
		String username = (String) model.asMap().get("username");
		DoctorEntity de = doctorDAO.findByLoginId(username);

		if(de!=null && de.getDoctorId()!=null){
			model.addAttribute("doctorId",de.getDoctorId());
			fetchDoctorInfoByDoctorId(model);
		}else
			throw new Exception("Doctor Profile doesn't exist");
	}

	public void fetchTheraPeuticArea(Model model) {
		String username = (String) model.asMap().get("username");
		List<Object[]> user = userDAO.getUserId(username);
		if (user != null && user.size() == 1) {
			DoctorEntity de = doctorDAO.findByUserId((Integer) user.get(0)[0]);
			if (de != null && de.getTherapeuticId() != null) {
				model.addAttribute("therapeuticarea", therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
						Integer.parseInt(de.getTherapeuticId())));
			}
		}
	}

	public void updateTheraPeutcArea(Model model) throws Exception {
		TherapeuticArea therapeuticArea = (TherapeuticArea) model.asMap().get("therapeuticArea");
		TherapeuticAreaEntity tentity =new TherapeuticAreaEntity();
		String loginId=(String) model.asMap().get("username");
		DoctorEntity doctor=doctorDAO.findByLoginId(loginId);

		if(doctor.getTherapeuticId()!=null)
			tentity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
				Integer.parseInt(doctor.getTherapeuticId()));
		tentity.setTherapeuticDesc(therapeuticArea.getTherapeuticDesc());
		tentity.setTherapeuticName(therapeuticArea.getTherapeuticName());
		tentity=therapeuticAreaDAO.merge(tentity);
		doctor.setTherapeuticId(String.valueOf(tentity.getTherapeuticId()));
	}

	public void changeAddress(Model model) {
		List<Location> addresses=(List<Location>) model.asMap().get("addresses");
		String username=(String) model.asMap().get("username");
		UserEntity userEntity=userDAO.findByEmailId(username);
		List<LocationEntity> locations=new ArrayList<LocationEntity>();
		if(userEntity!=null){
			for(Location location:addresses){
				LocationEntity locationEntity=new LocationEntity();
				BeanUtils.copyProperties(location, locationEntity);
				locations.add(locationEntity);
			}
		locationDAO.changeAddress(locations,userEntity);
		}

	}

	public void getAddress(Model model) {
			List<Location> addresses=new ArrayList<Location>();
			String username=(String) model.asMap().get("username");
			UserEntity userEntity=userDAO.findByEmailId(username);
			List<LocationEntity> l=locationDAO.findLocationsByUserId(userEntity.getUserId());
			List<Location> loc=new ArrayList<Location>();

			if(l!=null&& !l.isEmpty()){
				for(LocationEntity le:l){
					Location tmp=new Location();
					BeanUtils.copyProperties(le, tmp);
					loc.add(tmp);
				}
			}
			model.addAttribute("loc", loc);
	}

	public void updateContactInfo(Model model) {
		String username = (String) model.asMap().get("username");
		User user = (User) model.asMap().get("user");
		System.out.println(user.getMobileNo()+"******");
		UserEntity userEntity = userDAO.findByEmailId(username);
		if (user.getMobileNo() != null && user.getMobileNo().trim().length() != 0)
			userEntity.setMobileNo(user.getMobileNo());
		if (user.getPhoneNo() != null && user.getPhoneNo().trim().length() != 0)
			userEntity.setPhoneNo(user.getPhoneNo());
		if (user.getAlternateEmailId() != null && user.getAlternateEmailId().trim().length() != 0)
			userEntity.setAlternateEmailId(user.getAlternateEmailId());
		if (user.getEmailId() != null && user.getEmailId().trim().length() != 0)
			userEntity.setEmailId(user.getEmailId());

		if (!Util.isEmpty(user.getFirstName())) {
			if(user.getFirstName().indexOf("Dr.")!=-1)
				userEntity.setFirstName(user.getFirstName().substring(user.getFirstName().lastIndexOf("Dr.")+3).trim());
			else
				userEntity.setFirstName(user.getFirstName());
		}

//		if (user.getFirstName() != null && user.getFirstName().trim().length() != 0)
//			userEntity.setFirstName(user.getFirstName());
		if (user.getLastName() != null && user.getLastName().trim().length() != 0)
			userEntity.setLastName(user.getLastName());
//		if (user.getMiddleName() != null && user.getMiddleName().trim().length() != 0)
//			userEntity.setMiddleName(user.getMiddleName());
		userDAO.merge(userEntity);
	}

	public void deleteTheraPeutcArea(Model model) {
		TherapeuticArea therapeuticArea=(TherapeuticArea) model.asMap().get("therapeuticArea");
		for(Integer therapeuticId:therapeuticArea.getTherapeuticIds()){
			TherapeuticAreaEntity entity=therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, therapeuticId);
			if(entity!=null)
			therapeuticAreaDAO.remove(entity);
		}

	}

	public void fetchDoctorInfoByDoctorId(Model model) throws Exception {

		Map<String,Object> doctorInfo=new HashMap<String,Object>();

		Integer doctorId = (Integer) model.asMap().get("doctorId");

		DoctorEntity de=doctorDAO.findByDoctorId(doctorId);
		if(de==null ||(de!=null &&de.getDoctorId()==null))
			throw new Exception("Doctor Profile  not found");
		if(de!=null){
//		AboutEntity ae = aboutDao.findByDoctorId(de.getDoctorId());

		About about = new About();
		about.setName(Util.getTitle(de.getUser().getTitle())+de.getUser().getFirstName()+" "+de.getUser().getLastName());
		

		
//		about.setLocation(de.getUser().get);
//		if(null!=ae){
//		BeanUtils.copyProperties(ae, about);
//		about.setDoctorId(ae.getDoctorEntity().getDoctorId());
//		}
		doctorInfo.put("about", about);
		if (de.getWorkExpYears() !=null)
			about.setWorkExpYear(de.getWorkExpYears());
		else
			about.setWorkExpYear(0);
		
		if (de.getWorkExpMonths() !=null)
			about.setWorkExpMonth(de.getWorkExpMonths());
		else
			about.setWorkExpMonth(0);
		if(de.getUser()!=null &&  de.getUser().getDisplayPicture()!=null)
			doctorInfo.put("dPicture",de.getUser().getDisplayPicture().getImageUrl());

		List<WorkExperienceEntity> workExperienceEntities = workExperiencesDAO.findByDoctorId(de.getDoctorId());
		List<WorkExperience> workexperiences=new ArrayList<WorkExperience>();
		if(workExperienceEntities!=null)
		for(WorkExperienceEntity  entity:workExperienceEntities){
			WorkExperience dto=new WorkExperience();
			BeanUtils.copyProperties(entity,dto);
			workexperiences.add(dto);
		}
		doctorInfo.put("workexperiences", workexperiences);

		List<InterestedAreasEntity> interestAreas = interestedAreasDAO.findByDoctorId(de.getDoctorId());


		List<InterestedAreas> interests=new ArrayList<InterestedAreas>();

		if(de.getTherapeuticId()!=null){
			TherapeuticAreaEntity t=therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
					Integer.parseInt(de.getTherapeuticId()));

			if(t!=null){
				 InterestedAreas thereapeuticArea=new InterestedAreas();
				 thereapeuticArea.setId(t.getTherapeuticId());
				 thereapeuticArea.setName(t.getTherapeuticName());
				 interests.add(thereapeuticArea);
				 about.setDesignation(t.getTherapeuticName());
			}
		}



		if(interestAreas!=null)
		for(InterestedAreasEntity  entity:interestAreas){
			InterestedAreas dto=new InterestedAreas();
			BeanUtils.copyProperties(entity,dto);
			interests.add(dto);
		}

		doctorInfo.put("interests", interests);

		List<PublicationEntity> publications = publicationDAO.findByDoctorId(de.getDoctorId());
		List<Publication> pubs=new ArrayList<Publication>();
		if(publications!=null){
		for(PublicationEntity  entity:publications){
			Publication dto=new Publication();
			BeanUtils.copyProperties(entity,dto);
			pubs.add(dto);
		}
		}
		doctorInfo.put("publications", pubs);
		
		
		List<AwardsEntity> awardsEntity = awardsDAO.findByDoctorId(de.getDoctorId());
		List<Awards> awards=new ArrayList<Awards>();
		if(awardsEntity!=null){
		for(AwardsEntity  award:awardsEntity){
			Awards dto=new Awards();
			BeanUtils.copyProperties(award,dto);
			awards.add(dto);
		}
		}
		doctorInfo.put("awards", awards);
		
		List<WebinarsEntity> webinarsEntity = webinarsDAO.findByDoctorId(de.getDoctorId());
		List<Webinars> webs=new ArrayList<Webinars>();
		if(webinarsEntity!=null){
		for(WebinarsEntity  entity:webinarsEntity){
			Webinars dto=new Webinars();
			BeanUtils.copyProperties(entity,dto);
			webs.add(dto);
		}
		}
		doctorInfo.put("webinars", webs);

		List<EducationalQualificationEntity> educationsDetails = educationalQualificationDAO.findByDoctorId(de.getDoctorId());
		List<EducationalQualification> ed=new ArrayList<EducationalQualification>();
		if(educationsDetails!=null)
		for(EducationalQualificationEntity  entity:educationsDetails){
			EducationalQualification dto=new EducationalQualification();
			BeanUtils.copyProperties(entity,dto);
			ed.add(dto);
		}
		doctorInfo.put("educationdetails", ed);
		
	


		List<LocationEntity> lentities=locationDAO.findLocationsByUserId(de.getUser().getUserId());
		List<Location> locations=new ArrayList<Location>();
		if(lentities!=null && lentities.size()>0){
			for(LocationEntity locationEntity:lentities){
				Location location=new Location();

				location.setAddress1(locationEntity.getAddress1());
				location.setAddress2(locationEntity.getAddress2());
				location.setLocatity(locationEntity.getLocatity());
				location.setCity(locationEntity.getCity());
				location.setCountry(locationEntity.getCountry());
				location.setLocationId(locationEntity.getLocationId());
				location.setState(locationEntity.getState());
				location.setType(Util.getLocationType(locationEntity.getType()));
				location.setZipcode(locationEntity.getZipcode());
				if(!Util.isEmpty(location.getCity()) && Util.isEmpty(about.getLocation()))
					about.setLocation(location.getCity());

				locations.add(location);
			}
		}
		doctorInfo.put("address", locations);

		Map<String,Object> contactInfo=new HashMap<String,Object>();
		if(de.getUser()!=null){
			contactInfo.put("mobileNo", de.getUser().getMobileNo());
			contactInfo.put("phoneNo", de.getUser().getPhoneNo());
			contactInfo.put("alternateEmail", de.getUser().getAlternateEmailId());
			contactInfo.put("email", de.getUser().getEmailId());
			Map<String,String> name=new HashMap<String, String>();
			name.put("firstName",Util.getTitle(de.getUser().getTitle())+ de.getUser().getFirstName());
			name.put("lastName", de.getUser().getLastName());
//			name.put("middleNam", de.getUser().getMiddleName());
			contactInfo.put("name", name);
		}



		doctorInfo.put("contactInfo", contactInfo);


		model.addAttribute("doctorinfo",doctorInfo);

		}else
		throw new Exception("Doctor Profile doesn't exist");

		/*DoctorEntity de=doctorDAO.findByDoctorId(doctorId);
		AboutEntity ae = aboutDao.findByDoctorId(de.getDoctorId());
		About about = new About();
		if(null!=ae){
		BeanUtils.copyProperties(ae, about);
		about.setDoctorId(ae.getDoctorEntity().getDoctorId());
		}
		doctorInfo.put("about", about);

		List<WorkExperienceEntity> workExperienceEntities = workExperiencesDAO.findByDoctorId(de.getDoctorId());
		List<WorkExperience> workexperiences=new ArrayList<WorkExperience>(workExperienceEntities.size());
		for(WorkExperienceEntity  entity:workExperienceEntities){
			WorkExperience dto=new WorkExperience();
			BeanUtils.copyProperties(entity,dto);
			workexperiences.add(dto);
		}
		doctorInfo.put("workexperiences", workexperiences);

		List<InterestedAreasEntity> interestAreas = interestedAreasDAO.findByDoctorId(de.getDoctorId());

		List<InterestedAreas> interests=new ArrayList<InterestedAreas>(interestAreas.size()+1);
		TherapeuticAreaEntity t=therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,
				Integer.parseInt(de.getTherapeuticId()));
		if(t!=null){
			 InterestedAreas thereapeuticArea=new InterestedAreas();
			 thereapeuticArea.setId(t.getTherapeuticId());
			 thereapeuticArea.setName(t.getTherapeuticName());
			 interests.add(thereapeuticArea);
		}
		for(InterestedAreasEntity  entity:interestAreas){
			InterestedAreas dto=new InterestedAreas();
			BeanUtils.copyProperties(entity,dto);
			interests.add(dto);
		}

		doctorInfo.put("interests", interests);

		List<PublicationEntity> publications = publicationDAO.findByDoctorId(de.getDoctorId());
		List<Publication> pubs=new ArrayList<Publication>(publications.size());
		for(PublicationEntity  entity:publications){
			Publication dto=new Publication();
			BeanUtils.copyProperties(entity,dto);
			pubs.add(dto);
		}
		doctorInfo.put("publications", pubs);

		List<EducationalQualificationEntity> educationsDetails = educationalQualificationDAO.findByDoctorId(de.getDoctorId());
		List<EducationalQualification> ed=new ArrayList<EducationalQualification>(educationsDetails.size());
		for(EducationalQualificationEntity  entity:educationsDetails){
			EducationalQualification dto=new EducationalQualification();
			BeanUtils.copyProperties(entity,dto);
			ed.add(dto);
		}
		doctorInfo.put("educationdetails", ed);

		List<LocationEntity> lentities=locationDAO.findLocationsByUserId(de.getUser().getUserId());
		List<Location> locations=new ArrayList<Location>();
		if(lentities!=null && lentities.size()>0){
			for(LocationEntity locationEntity:lentities){

				Location location=new Location();

				location.setAddress1(locationEntity.getAddress1());
				location.setAddress2(locationEntity.getAddress2());
				location.setCity(locationEntity.getCity());
				location.setCountry(locationEntity.getCountry());
				location.setLocationId(locationEntity.getLocationId());
				location.setState(locationEntity.getState());
				location.setType(Util.getLocationType(locationEntity.getType()));
				location.setZipcode(locationEntity.getZipcode());

				locations.add(location);
			}
		}
		doctorInfo.put("address", locations);

		Map<String,Object> contactInfo=new HashMap<String,Object>();
		contactInfo.put("mobileNo", de.getUser().getMobileNo());
		contactInfo.put("phoneNo", de.getUser().getPhoneNo());
		contactInfo.put("alternateEmail", de.getUser().getAlternateEmailId());
		contactInfo.put("email", de.getUser().getEmailId());
		Map<String,String> name=new HashMap<String, String>();
		name.put("firstName", de.getUser().getFirstName());
		name.put("lastName", de.getUser().getLastName());
		name.put("middleNam", de.getUser().getMiddleName());
		contactInfo.put("name", name);

		doctorInfo.put("contactInfo", contactInfo);


		model.addAttribute("doctorinfo",doctorInfo);*/
	}

	@SuppressWarnings("unchecked")
	public void deleteAddress(Model model) throws Exception {
		String username = (String) model.asMap().get("username");

		Set<Integer> locations = (Set<Integer>) model.asMap().get("locationids");
		if (locations != null && locations.size() > 0) {
			for (Integer locationId : locations) {
				UserEntity userEntity = userDAO.findByEmailId(username);
				List<LocationEntity> locs = locationDAO.findLocationsByUserId(userEntity.getUserId());
				if (locs.size() > 1) {
					LocationEntity le = locationDAO.findById(LocationEntity.class, locationId);
					if (le == null)
						throw new Exception("Address details doesn't exist");
					if (!username.equals(le.getUser().getEmailId()))
						throw new Exception("Invalied Access");
					locationDAO.remove(le);
				} else
					throw new Exception("Address cannot be deleted.There should be a minimum of 1 address.");
			}
		}
	}
}