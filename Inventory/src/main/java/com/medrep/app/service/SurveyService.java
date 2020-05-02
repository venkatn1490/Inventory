package com.medrep.app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DeviceTokenDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorDetailsSurveyDAO;
import com.medrep.app.dao.DoctorSurveyDAO;
import com.medrep.app.dao.PendingCountDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.PharmaRepSurveyDAO;
import com.medrep.app.dao.SurveyDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DeviceTokenEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorSurveyEntity;
import com.medrep.app.entity.PendingCountsEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.PharmaSurveyEntity;
import com.medrep.app.entity.SurveyEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.CompanySurvey;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.NotificationActivity;
import com.medrep.app.model.NotificationStat;
import com.medrep.app.model.Survey;
import com.medrep.app.model.SurveyStatistics;
import com.medrep.app.util.AndroidPushNotification;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.IosPushNotification;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.Util;

@Service("surveyService")
@Transactional
public class SurveyService {

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	DoctorSurveyDAO doctorSurveyDAO;

	@Autowired
	DoctorDetailsSurveyDAO doctorDetailsSurveyDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	@Autowired
	CompanyDAO companyDAO;

	@Autowired
	SurveyDAO surveyDAO;

	@Autowired
	NotificationService notificationService;

	@Autowired
	PharmaRepDAO pharmaRepDAO;

	@Autowired
	DeviceTokenDAO deviceTokenDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	PendingCountDAO pendingCountDAO;
	@Autowired
	PharmaRepSurveyDAO pharmaRepSurveyDAO;

	private static final Log log = LogFactory.getLog(SurveyService.class);

	/**
	 * List all pending survey for given doctor Id based on the status to 'NEW'
	 * @param loginId
	 * @return
	 */
	public List<Survey> getDoctorSurvey(String loginId)
	{
		List<Survey> surveys = new ArrayList<Survey>();
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		int doctorId = doctorEntity.getDoctorId();
		List<DoctorSurveyEntity> doctorSurveys = doctorSurveyDAO.findByStatusAndDoctorId(doctorId,"Pending");
		for(DoctorSurveyEntity doctorSurveyEntity :doctorSurveys)
		{

			SurveyEntity surveyEntity = doctorSurveyEntity.getSurvey();
			Survey survey = new Survey();

			survey.setDoctorId(doctorSurveyEntity.getDoctorId());
			survey.setDoctorSurveyId(doctorSurveyEntity.getDoctorSurveyId());
			survey.setStatus(doctorSurveyEntity.getStatus());
			if(surveyEntity != null)
			{
				survey.setScheduledStart(surveyEntity.getScheduledStart());
				survey.setScheduledFinish(surveyEntity.getScheduledFinish());
				survey.setSurveyId(surveyEntity.getSurveyId());
				survey.setSurveyTitle(surveyEntity.getSurveyTitle());
				survey.setSurveyDescription(Util.stripHtml(surveyEntity.getSurveyDescription()));
				//Customizing URL for Every doctor to track the doctor Id.
				survey.setSurveyUrl(surveyEntity.getSurveyUrl() + "&userId="+doctorSurveyEntity.getDoctorId());
				survey.setCompanyId(surveyEntity.getCompanyId());
				survey.setTherapeuticId(surveyEntity.getTherapeuticId());

				if(surveyEntity.getCompanyId() != null)
				{
					CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,surveyEntity.getCompanyId());
					if(companyEntity != null)
					{
						survey.setCompanyName(companyEntity.getCompanyName());
					}
				}

				if(surveyEntity.getTherapeuticId() != null)
				{
					TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,surveyEntity.getTherapeuticId());
					if(therapeuticAreaEntity != null)
					{
						survey.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
					}
				}
			}
			surveys.add(survey);

		}
		return surveys;
	}

	/**
	 * Update the doctor Survey status to 'COMPLETE'
	 * @param survey
	 */
	public void updateDoctorSurvey(Survey survey,String loginId)
	{
		DoctorSurveyEntity doctorSurveyEntity = doctorSurveyDAO.findById(DoctorSurveyEntity.class,survey.getDoctorSurveyId());
		doctorSurveyEntity.setStatus(survey.getStatus());
		doctorSurveyDAO.merge(doctorSurveyEntity);
		updateSurveyCountByLoginId(doctorSurveyEntity.getStatus(), loginId);
	}


	/**
	 * Get Survey based on Survey Id
	 * @param loginId
	 * @return
	 */
	public Survey getDoctorSurvey(Survey _survey)
	{
		Survey survey = null;
		try
		{

			DoctorSurveyEntity doctorSurveyEntity = doctorSurveyDAO.findById(DoctorSurveyEntity.class,_survey.getDoctorSurveyId());
			if(doctorSurveyEntity != null)
			{
				survey = new Survey();
				SurveyEntity surveyEntity = doctorSurveyEntity.getSurvey();
				survey.setDoctorId(doctorSurveyEntity.getDoctorId());
				survey.setDoctorSurveyId(doctorSurveyEntity.getDoctorSurveyId());
				survey.setStatus(doctorSurveyEntity.getStatus());
				if(surveyEntity != null)
				{
					survey.setScheduledStart(surveyEntity.getScheduledStart());
					survey.setScheduledFinish(surveyEntity.getScheduledFinish());
					survey.setSurveyId(surveyEntity.getSurveyId());
					survey.setSurveyTitle(surveyEntity.getSurveyTitle());
					survey.setSurveyDescription(Util.stripHtml(surveyEntity.getSurveyDescription()));
					survey.setSurveyUrl(surveyEntity.getSurveyUrl());

					survey.setCompanyId(surveyEntity.getCompanyId());
					survey.setTherapeuticId(surveyEntity.getTherapeuticId());

					if(surveyEntity.getCompanyId() != null)
					{
						CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,surveyEntity.getCompanyId());
						if(companyEntity != null)
						{
							survey.setCompanyName(companyEntity.getCompanyName());
						}
					}

					if(surveyEntity.getTherapeuticId() != null)
					{
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,surveyEntity.getTherapeuticId());
						if(therapeuticAreaEntity != null)
						{
							survey.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}
					}
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return survey;
	}



	/**
	 * Get Survey based on Survey Id
	 * @param loginId
	 * @return
	 */
	public Survey getRepSurvey(Survey _survey,String loginId)
	{
		Survey survey = null;
		try
		{

			PharmaSurveyEntity pharmaSurveyEntity = pharmaRepSurveyDAO.findBySurveyIdForPharmaRep(loginId,_survey.getSurveyId());
			if(pharmaSurveyEntity != null)
			{
				survey = new Survey();
				SurveyEntity surveyEntity = pharmaSurveyEntity.getSurvey();
				survey.setRepId(pharmaSurveyEntity.getRepId());
				survey.setRepSurveyId(pharmaSurveyEntity.getRepSurveyId());
				survey.setStatus(pharmaSurveyEntity.getStatus());
				if(surveyEntity != null)
				{
					survey.setScheduledStart(surveyEntity.getScheduledStart());
					survey.setScheduledFinish(surveyEntity.getScheduledFinish());
					survey.setSurveyId(surveyEntity.getSurveyId());
					survey.setSurveyTitle(surveyEntity.getSurveyTitle());
					survey.setSurveyDescription(Util.stripHtml(surveyEntity.getSurveyDescription()));
					survey.setSurveyUrl(surveyEntity.getSurveyUrl());

					survey.setCompanyId(surveyEntity.getCompanyId());
					survey.setTherapeuticId(surveyEntity.getTherapeuticId());

					if(surveyEntity.getCompanyId() != null)
					{
						CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,surveyEntity.getCompanyId());
						if(companyEntity != null)
						{
							survey.setCompanyName(companyEntity.getCompanyName());
						}
					}

					if(surveyEntity.getTherapeuticId() != null)
					{
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,surveyEntity.getTherapeuticId());
						if(therapeuticAreaEntity != null)
						{
							survey.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}
					}
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return survey;
	}



	public void createAdminSurvey(Survey survey)throws MedrepException{
		SurveyEntity entity = BeanUtils.instantiateClass(SurveyEntity.class);
		entity.setPushNotification("PUSH");
		BeanUtils.copyProperties(survey, entity);
		surveyDAO.persist(entity);
	}

	public void updateAdminSurvey(Survey survey)throws MedrepException{
		SurveyEntity entity=surveyDAO.findById(SurveyEntity.class,Integer.valueOf(survey.getSurveyId()));
		entity.setCompanyId(survey.getCompanyId());
		entity.setTherapeuticId(survey.getTherapeuticId());
		entity.setSurveyDescription(survey.getSurveyDescription());
		entity.setStatus(survey.getStatus());
		entity.setPushNotification("PUSH");
		surveyDAO.merge(entity);
	}


	public SurveyEntity getSurvey(Integer surveyId)throws MedrepException{
		SurveyEntity entity=surveyDAO.findById(SurveyEntity.class,surveyId);
		return entity;
	}

	public SurveyEntity updateSurvey(Integer surveyId,Integer doctorId)throws MedrepException{
		SurveyEntity entity=surveyDAO.findById(SurveyEntity.class,surveyId);
		DoctorSurveyEntity dse=doctorSurveyDAO.findByDoctorSurveyId(doctorId, surveyId);
		if(dse!=null){
			dse.setReminderSent("1");
			dse.setReminderSentOn(new Date());
		}
		return entity;
	}



	public List<Survey> getAdminSurveysList()throws MedrepException{
		Map<String,String> map =notificationService.getAllCompanys();
		List<SurveyEntity> list = surveyDAO.getAdminSurveysList();
		List<Survey> valObjs = new ArrayList<Survey>();
		for(SurveyEntity entity: list)
		{
			Survey survey = BeanUtils.instantiateClass(Survey.class);
			BeanUtils.copyProperties(entity, survey);
			Integer cid = entity.getCompanyId();
			if(cid!=null && cid>0) survey.setCompanyName(map.get(String.valueOf(cid)));
			else survey.setCompanyName("");
			if(entity.getTherapeuticId() != null )
			{
				TherapeuticAreaEntity tentity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, entity.getTherapeuticId());
				if(tentity!=null) survey.setTherapeuticName(tentity.getTherapeuticName());
				else survey.setTherapeuticName("");
			}
			valObjs.add(survey);
		}
		return valObjs;
	}

	public Survey getAdminSurveyById(String id)throws MedrepException{
		SurveyEntity entity=surveyDAO.findById(SurveyEntity.class,Integer.valueOf(id));
		Survey survey = BeanUtils.instantiateClass(Survey.class);
		BeanUtils.copyProperties(entity, survey);
		String url = survey.getSurveyUrl()!=null ? survey.getSurveyUrl()+"&userId=0" : "";
		survey.setSurveyUrl(url);
		Map<String,String> map =notificationService.getAllCompanys();
		Integer cid = survey.getCompanyId();
		if(cid!=null && !cid.equals("0")) survey.setCompanyName(map.get(String.valueOf(cid)));
		else survey.setCompanyName("");
		if(survey.getTherapeuticId() != null )
		{
			TherapeuticAreaEntity tentity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, entity.getTherapeuticId());
			if(tentity!=null) survey.setTherapeuticName(tentity.getTherapeuticName());
			else survey.setTherapeuticName("");
		}
		StringBuilder buffer= new StringBuilder();
		buffer.append("<option value=0> ----------   Select One  -------- </option>");
		if(cid!=null && !cid.equals("0")){
			Map<String,String> tmap = notificationService.getAllTherapeuticsByCompanyId(String.valueOf(cid));
			for(String key : tmap.keySet()){
				buffer.append("<option value="+key +">" +tmap.get(key) +"</option>");
			}
		}
		survey.setTherapeuticDropDownValues(buffer.toString());
	//	Getting survey stats
		SurveyStatistics stat = this.getSurveyStatisticsBySurveyId(Integer.parseInt(id));
		survey.setTotalSent(stat.getTotalSent());
		survey.setTotalPending(stat.getTotalPending());
		survey.setTotalCompleted(stat.getTotalCompleted());
		return survey;
	}

	public void removeAdminSurvey(String id)throws MedrepException{
		try{
			SurveyEntity entity=surveyDAO.findById(SurveyEntity.class,Integer.valueOf(id));
			surveyDAO.remove(entity);
		}catch(Exception e){
			throw new MedrepException(e.getMessage());
		}
	}

	public List<CompanySurvey> getCompanySurvey(String repLoginId,Integer surveyId) throws MedrepException
	{
		List<CompanySurvey> compnaySurveys = new ArrayList<CompanySurvey>();
		List<DoctorSurveyEntity> doctorSurveyEntities = null;
		try
		{
			//System.out.println("Trying to get for login id : " + repLoginId);
			PharmaRepEntity repEntity = pharmaRepDAO.findByLoginId(repLoginId);

			//System.out.println("Found rep id : " + repEntity.getRepId());
			SurveyEntity surveyEntity = surveyDAO.findById(SurveyEntity.class, surveyId);

			//System.out.println("Found relevant surveys : " + surveyEntity.getStatus());

			if(repEntity!=null)
			{
				doctorSurveyEntities = doctorSurveyDAO.findBySurveyId(surveyEntity.getSurveyId());

				//System.out.println("Got doctor surveys : " + doctorSurveyEntities.size());

				if(doctorSurveyEntities!=null)
				{
					CompanySurvey companySurvey = new CompanySurvey();
					companySurvey.setCompanyId(surveyEntity.getCompanyId());
					companySurvey.setSurveyId(surveyId);

					//companySurvey.setTherapeuticName(surveyEntity.getT);
					CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,surveyEntity.getCompanyId());
					if(companyEntity!=null)
					{
						companySurvey.setCompanyName(companyEntity.getCompanyName());
					}

					companySurvey.setCreatedOn(surveyEntity.getCreatedOn());
					companySurvey.setSurveyDescription(Util.stripHtml(surveyEntity.getSurveyDescription()));
					companySurvey.setSurveyTitle(surveyEntity.getSurveyTitle());
					companySurvey.setSurveyUrl(surveyEntity.getSurveyUrl());
					companySurvey.setNotifiedDoctorsCount(doctorSurveyEntities.size());
					int counter = 0;
					for(DoctorSurveyEntity doctorSurveyEntity : doctorSurveyEntities)
					{
						if(doctorSurveyEntity.getStatus().equalsIgnoreCase("COMPLETE"))
						{
							counter++;

						}

					}
					TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,surveyEntity.getTherapeuticId());
					if(therapeuticAreaEntity != null)
					{
						companySurvey.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
					}
					companySurvey.setDoctorResponseCount(counter);
					companySurvey.setTherapeuticId(surveyEntity.getTherapeuticId());
					companySurvey.setScheduledStart(surveyEntity.getScheduledStart());
					companySurvey.setScheduledFinish(surveyEntity.getScheduledFinish());
					compnaySurveys.add(companySurvey);

				}


			}

		}
		catch(Exception e)
		{
			throw new MedrepException(e.getMessage());
		}

		return compnaySurveys;

	}


	public List<Survey> getSurveysByRep(String repLoginId) throws MedrepException
	{
		List<SurveyEntity> surveysList = new ArrayList<SurveyEntity>();
		List<Survey> surveys = new ArrayList<Survey>();
		try
		{
			//System.out.println("Trying to get for login id : " + repLoginId);
			PharmaRepEntity repEntity = pharmaRepDAO.findByLoginId(repLoginId);
			if(repEntity!=null)
			{
				surveysList = surveyDAO.findSurveysByCompanyId( repEntity.getCompanyId());

				for(SurveyEntity surveyEntity :surveysList)
				{

					Survey survey = new Survey();
					List<Object[]> activities = surveyDAO.findStatsBySurveyId(surveyEntity.getSurveyId());
					if(activities!=null && activities.size()>0)
						survey.setReportsStatus(true);
					else
						survey.setReportsStatus(false);
					survey.setScheduledStart(surveyEntity.getScheduledStart());
					survey.setScheduledFinish(surveyEntity.getScheduledFinish());
					survey.setSurveyId(surveyEntity.getSurveyId());
					survey.setSurveyTitle(surveyEntity.getSurveyTitle());
					survey.setSurveyDescription(Util.stripHtml(surveyEntity.getSurveyDescription()));
					//Customizing URL for Every doctor to track the doctor Id.
					survey.setCompanyId(surveyEntity.getCompanyId());
					survey.setTherapeuticId(surveyEntity.getTherapeuticId());

					if(surveyEntity.getCompanyId() != null)
					{
						CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,surveyEntity.getCompanyId());
						if(companyEntity != null)
						{
							survey.setCompanyName(companyEntity.getCompanyName());
						}
					}

					if(surveyEntity.getTherapeuticId() != null)
					{
						TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,surveyEntity.getTherapeuticId());
						if(therapeuticAreaEntity != null)
						{
							survey.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						}
					}

					String path=MedRepProperty.getInstance().getProperties("medrep.survey.reports.path")+File.separator+surveyEntity.getSurveyId()+".pdf";
					if(FileUtil.isFileExists(path)){
						survey.setReportUrl(MedRepProperty.getInstance().getProperties("medrep.home")+"survey/reports/"+surveyEntity.getSurveyId()+".pdf");
						survey.setReportsAvailable(true);
					}
					surveys.add(survey);

				}

			}

		}
		catch(Exception e)
		{
			throw new MedrepException(e.getMessage());
		}

		return surveys;

	}

	public SurveyStatistics getSurveyStatisticsBySurveyId(Integer surveyId)
	{
		List<DoctorSurveyEntity> doctorSurveyEntities = doctorSurveyDAO.findStatsBySurveyId(surveyId);
		Integer totalSent=0;
		Integer totalPending=0;
		Integer totalCompleted=0;
		SurveyStatistics surveyStatistics=new SurveyStatistics();
		surveyStatistics.setSurveyId(surveyId);

		List<Doctor> completed=new ArrayList<Doctor>();
		List<Doctor> pending=new ArrayList<Doctor>();
		Set<Integer> _completed=new HashSet<Integer>();
		Set<Integer> _pending=new HashSet<Integer>();
		List<Doctor> sent=new ArrayList<Doctor>();
		Set<Integer> _sent=new HashSet<Integer>();
		for(DoctorSurveyEntity dse:doctorSurveyEntities){
			Doctor doctor=getDoctorDetailsByDoctorId(dse.getDoctorId());
			if(_sent.add(dse.getDoctorId()))
				sent.add(doctor);

			if("Filled".equals(dse.getStatus()) || "Completed".equalsIgnoreCase(dse.getStatus())){
				if(_completed.add(dse.getDoctorId())){
				completed.add(doctor);
				totalCompleted++;
				}
			}else{
				if(_pending.add(dse.getDoctorId())){
					doctor.setRemindNotification("1".equals(dse.getReminderSent())+"");
					pending.add(doctor);
					totalPending++;
				}
			}
		}

		totalSent=totalCompleted+totalPending;
		surveyStatistics.setSent(sent);
		surveyStatistics.setTotalCompleted(totalCompleted);
		surveyStatistics.setTotalPending(totalPending);
		surveyStatistics.setTotalSent(totalSent);
		surveyStatistics.setCompleted(completed);
		surveyStatistics.setPending(pending);
		return surveyStatistics;

	}

	private Doctor getDoctorDetailsByDoctorId(Integer doctorId) {
		DoctorEntity d=doctorDAO.findByDoctorId(doctorId);
		Doctor doctor=new Doctor();
		if(d!=null){
			doctor=new Doctor();
			doctor.setDoctorId(d.getDoctorId());
			if(d.getUser()!=null){
				doctor.setFirstName("Dr."+d.getUser().getFirstName());
				doctor.setLastName(d.getUser().getLastName());
				if (d.getUser().getDisplayPicture() != null)
				doctor.setdPicture(d.getUser().getDisplayPicture().getImageUrl());
			}
		}
		return doctor;
	}

	public List<Survey> getPendingDoctorSurvey(Integer surveyId)
	{
		List<Survey> surveys = new ArrayList<Survey>();
		System.out.println("surveyId="+surveyId);
		//List<DoctorDetailsSurveyEntity> doctorSurveys = doctorDetailsSurveyDAO.findBySurveyId(surveyId);
		List<DoctorSurveyEntity> doctorSurveys = doctorSurveyDAO.findBySurveyIdStatus(surveyId,"Pending");
		System.out.println("doctorSurveys="+doctorSurveys);
		if(doctorSurveys!=null){
			for(DoctorSurveyEntity doctorDetailsSurveyEntity :doctorSurveys)
			{
				DoctorEntity doctorEntity  = doctorDAO.findById(DoctorEntity.class, doctorDetailsSurveyEntity.getDoctorId());
				Survey survey = new Survey();

				survey.setDoctorId(doctorEntity.getDoctorId());
				survey.setDoctorSurveyId(doctorDetailsSurveyEntity.getDoctorSurveyId());
				survey.setStatus(doctorDetailsSurveyEntity.getStatus());
				survey.setSurveyId(doctorDetailsSurveyEntity.getSurvey().getSurveyId());
				survey.setDoctorName(Util.getTitle(doctorEntity.getUser().getTitle())+doctorEntity.getUser().getFirstName()+" "+doctorEntity.getUser().getLastName());
				survey.setReminder_sent("1".equals(doctorDetailsSurveyEntity.getReminderSent()));
				surveys.add(survey);

			}
		}
		return surveys;
	}


	public int pushSurvey() {
		String message = " Click here to fill the survey";
		try {
				List<SurveyEntity> surveyEntities = surveyDAO.findSurveysByPushStatus("Active");
				List<DeviceTokenEntity> registerDeviceTokens = deviceTokenDAO.findActiveDoctors();

				if (surveyEntities.size() > 0) {
					for (SurveyEntity surveyEntity : surveyEntities) {
						CompanyEntity companyEntity = companyDAO.findByCompanyId(surveyEntity.getCompanyId());
						message = createPushMessage(surveyEntity, companyEntity);
						if(registerDeviceTokens.size() > 0){
							for (DeviceTokenEntity deviceTokenEntity : registerDeviceTokens) {
								if(deviceTokenEntity.getPlatform().equalsIgnoreCase("IOS")){
									IosPushNotification.pushMessage(deviceTokenEntity.getDeviceToken(), message, companyEntity);
								}else{
									AndroidPushNotification.pushMessage(deviceTokenEntity.getDeviceToken(), message, companyEntity);
								}
							}
							surveyEntity.setPushNotification("PUSHED");
							surveyDAO.merge(surveyEntity);
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	private String createPushMessage(SurveyEntity surveyEntity, CompanyEntity companyEntity) {
		String gcmJSON = "";
		StringBuilder build = new StringBuilder();
		try {
			build.append("{").append("\"surveyId : \"").append(surveyEntity.getSurveyId()).append("\",");
			build.append("\"surveyTitle\" : \"").append(surveyEntity.getSurveyTitle()).append("\",");
			build.append("\"surveyDescription\" : \"").append(surveyEntity.getSurveyDescription()).append("\",");
			build.append("\"surveyUrl\" : \"").append(surveyEntity.getSurveyUrl()).append("\",");
			build.append("\"companyName\" : \"").append(companyEntity.getCompanyName()).append("\",");
			build.append("\"companyId\" : \"").append(companyEntity.getCompanyId()).append("\",");
			build.append("\"companyDesc\" : \"").append(companyEntity.getCompanyDesc()).append("\",");
			build.append("\"companyUrl\" : \"").append(companyEntity.getCompanyUrl()).append("\"");
			build.append("}");

			gcmJSON = build.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return gcmJSON;
	}

	public Map getSurveyReport(Integer doctorId,String username) {
		if(Util.isEmpty(doctorId))
			doctorId=doctorDAO.findByLoginId(username).getDoctorId();

		List<Object[]> _report= surveyDAO.findSurveysByDoctorId(doctorId);
		Map report=new HashMap();

		for(Object[] data:_report){
			report.put(data[0], data[1]);
		}
		return report;
	}

	public Object getSurveyReportForPharma(Integer repId,String username) {
		if(Util.isEmpty(repId))
			repId=pharmaRepDAO.findByLoginId(username).getRepId();


		List<Object[]> _report= surveyDAO.findSurveysByRepId(repId);
		Map report=new HashMap();

		for(Object[] data:_report){
			report.put(data[0], data[1]);
		}
		return report;
	}

	public ArrayList<DeviceTokenEntity> publish(Survey survey) {
		String surveyId = survey.getPublishSurveyId();
		SurveyEntity surveyEntity = surveyDAO.findById(SurveyEntity.class,Integer.parseInt(surveyId));
		List<SurveyEntity> surveysList = new ArrayList();
		surveysList.add(surveyEntity);
		List<DoctorEntity> docList = new ArrayList();
		Set<Integer> docIds=new HashSet();
		for (String docId : survey.getPublishDocsIds()) {
			DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, Integer.parseInt(docId));
			if(docIds.add(doctorEntity.getDoctorId()))
			docList.add(doctorEntity);
		}
		return publish(surveysList, docList);
	}


	public ArrayList<DeviceTokenEntity> publish(List<SurveyEntity> surveyEntities,List<DoctorEntity> doctorEntities){
		try
		{
			//Find all unpublished notifications
			boolean flag = false;
			ArrayList<DeviceTokenEntity> devicesList = new ArrayList<DeviceTokenEntity>();
			for(SurveyEntity surveyEntity : surveyEntities)
			{
				for (DoctorEntity doctorEntity : doctorEntities)
				{
					if(!"Active".equalsIgnoreCase(doctorEntity.getStatus()))
							continue;
					boolean isSurveySentToDoctor = false;
					DoctorSurveyEntity doctorSurveyEntity = doctorSurveyDAO.findByDoctorSurveyId(doctorEntity.getDoctorId(), surveyEntity.getSurveyId());
					if( doctorSurveyEntity != null && doctorSurveyEntity.getDoctorSurveyId() != 0)
					{

						System.out.println("Publishing DoctorSurvey Entity block for Doctor: " + doctorEntity.getDoctorId() + " Notfiication : " + surveyEntity.getSurveyId());
						doctorSurveyEntity.setStatus("Pending");
						if(Util.isEmpty(doctorSurveyEntity.getRefId()))
						doctorSurveyEntity.setRefId(surveyEntity.getRefId());
						doctorSurveyDAO.merge(doctorSurveyEntity);
						flag = true;
						isSurveySentToDoctor = true;
					}
					else
					{
						doctorSurveyEntity = new DoctorSurveyEntity();
						doctorSurveyEntity.setCreatedOn(new Date());
						doctorSurveyEntity.setDoctorId(doctorEntity.getDoctorId());
						doctorSurveyEntity.setSurvey(surveyEntity);
						doctorSurveyEntity.setStatus("Pending");
						doctorSurveyEntity.setRefId(surveyEntity.getRefId());
						doctorSurveyDAO.persist(doctorSurveyEntity);
						flag = true;
						isSurveySentToDoctor = true;
					}

					if(isSurveySentToDoctor)
					{

						UserEntity user = doctorEntity.getUser();

						updateSurveyCountByLoginId(doctorSurveyEntity.getStatus(), user.getSecurity().getLoginId());

						List<DeviceTokenEntity> devices=deviceTokenDAO.findByDoctorId(doctorEntity.getDoctorId());
						if(!Util.isEmpty((Collection)devices))
							devicesList.addAll(devices);
					}

				}

				if(surveyEntity.getCompanyId() != null)
				{
					List<PharmaRepEntity> pharmaReps = pharmaRepDAO.findByCompanyId(surveyEntity.getCompanyId());

					for(PharmaRepEntity pharmaRep : pharmaReps)
					{
						boolean isNotificationSentToRep = false;
						PharmaSurveyEntity repSurveyEntity = pharmaRepSurveyDAO.findByPharmaRepSurvey(pharmaRep.getRepId(), surveyEntity.getSurveyId());
						if(repSurveyEntity != null && repSurveyEntity.getRepSurveyId() != null)
						{

							repSurveyEntity.setStatus("Pending");
							pharmaRepSurveyDAO.merge(repSurveyEntity);
							flag = true;
							isNotificationSentToRep = true;
						}
						else
						{
							repSurveyEntity = new PharmaSurveyEntity();
							repSurveyEntity.setCreatedOn(DateConvertor.convertDateToString(new Date(),DateConvertor.YYYYMMDDHHMISS));
							repSurveyEntity.setRepId(pharmaRep.getRepId());
//							repSurveyEntity.setTherapeuticId(surveyEntity.getTherapeuticId());
							repSurveyEntity.setStatus("Pending");
							repSurveyEntity.setSurvey(surveyEntity);
							pharmaRepSurveyDAO.persist(repSurveyEntity);
							flag = true;
							isNotificationSentToRep = true;
						}
						if(isNotificationSentToRep)
						{
							UserEntity user = pharmaRep.getUser();
							if(user != null)
							{
								updateSurveyCountByLoginId(repSurveyEntity.getStatus(),user.getSecurity().getLoginId());
								List<DeviceTokenEntity> devices=deviceTokenDAO.findByRepId(pharmaRep.getRepId());
								if(!Util.isEmpty(devices))
									devicesList.addAll(devices);
							}
						}

					}

				}


				if(flag)
				{
					surveyEntity.setStatus("Published");
					surveyDAO.merge(surveyEntity);
				}


			}
			return devicesList;

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;

	}

	public void updateRepSurvey(Survey survey, String loginId) {
		PharmaSurveyEntity pharmaSurveyEntity = pharmaRepSurveyDAO.findById(PharmaSurveyEntity.class,survey.getRepSurveyId());
		pharmaSurveyEntity.setStatus(survey.getStatus());
		pharmaRepSurveyDAO.merge(pharmaSurveyEntity);
		updateSurveyCountByLoginId(pharmaSurveyEntity.getStatus(), loginId);
	}

	private void updateSurveyCountByLoginId(String surveyStatus, String loginId) {
		UserEntity userEntity=userDAO.findByEmailId(loginId);
		PendingCountsEntity pendingCountsEntity=pendingCountDAO.findByLoginId(loginId);
		if(pendingCountsEntity==null){
			pendingCountsEntity=new PendingCountsEntity();
			pendingCountsEntity.setUserId(userEntity.getUserId());
			pendingCountsEntity.setSurveysCount(0);
			pendingCountDAO.persist(pendingCountsEntity);
		}

		if("Completed".equalsIgnoreCase(surveyStatus))
				pendingCountsEntity.setSurveysCount(pendingCountsEntity.getSurveysCount()!=null &&pendingCountsEntity.getSurveysCount()>0?pendingCountsEntity.getSurveysCount()-1:0);
			else if("Pending".equalsIgnoreCase(surveyStatus) || "publish".equalsIgnoreCase(surveyStatus))
				pendingCountsEntity.setSurveysCount(pendingCountsEntity.getSurveysCount()!=null?pendingCountsEntity.getSurveysCount()+1:1);
		//		pendingCountDAO.merge(pendingCountsEntity);
	}

	public NotificationActivity getSurveyStats(String surveyId) {
		// TODO Auto-generated method stub
		return null;
	}
}
