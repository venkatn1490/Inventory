package com.medrep.app.engine;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorSurveyDAO;
import com.medrep.app.dao.SurveyDAO;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorSurveyEntity;
import com.medrep.app.entity.SurveyEntity;
import com.medrep.app.model.Activity;
import com.medrep.app.service.ActivityService;

@Transactional
@Component
@EnableScheduling
public class DoctorSurveyProcessor {
	
private static final Log log = LogFactory.getLog(DoctorSurveyProcessor.class);

	@Autowired 
	DoctorDAO doctorDAO;
	
	@Autowired
	DoctorSurveyDAO doctorSurveyDAO;
	
	@Autowired
	ActivityService activityService;
	
	@Scheduled(cron="0 0/5 * * * ?")
	public void processDoctorSurveys()
	{
		try
		{
			//Get All Active Doctors for now. Will Apply rules later.
			List<DoctorSurveyEntity> doctorSurveyEntities = doctorSurveyDAO.findByStatus("Filled");
			for(DoctorSurveyEntity doctorSurveyEntity : doctorSurveyEntities)
			{
				if(doctorSurveyEntity.getDoctorId() != null)
				{
					DoctorEntity doctorEntity = doctorDAO.findById(DoctorEntity.class, doctorSurveyEntity.getDoctorId());
					if(doctorEntity != null && doctorEntity.getUser() != null)
					{
						Activity activity = new Activity();
						activity.setActivityStatus("U");
						activity.setActivityType("DS");
						if(doctorSurveyEntity.getSurvey() != null)
						{
							activity.setActivityTypeId(doctorSurveyEntity.getSurvey().getSurveyId());
						}
						activity.setUserType("DOCTOR");
						activity.setLastUpdateDateTime(new Date());
						activityService.createOrUpdateActivity(activity, doctorEntity.getUser().getEmailId());
					}
				}
				doctorSurveyEntity.setStatus("Completed");
				doctorSurveyDAO.merge(doctorSurveyEntity);
				
			}
			
		}
		catch(Exception e)
		{
			log.error("Complete Survey failed.", e);
		}
	}
}
