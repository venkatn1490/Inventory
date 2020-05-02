package com.medrep.app.engine;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.medrep.app.util.Util;

@Transactional
@Component
@EnableScheduling
public class SurveyPublisher {

	private static final Log log = LogFactory.getLog(SurveyPublisher.class);


	@Autowired
	SurveyDAO surveyDAO;

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	DoctorSurveyDAO doctorSurveyDAO;
	@PersistenceContext
	EntityManager entityManager;


	@Scheduled(fixedDelay=24*60*60*1000)
	public void resetReminder()
	{
		List<DoctorSurveyEntity> doctorSurveys=doctorSurveyDAO.findByStatus("Pending");
		if(!Util.isEmpty(doctorSurveys)){
			for(DoctorSurveyEntity d:doctorSurveys){
				if(!Util.isEmpty(d.getReminderSentOn())){
					Date currentDate=new Date();
					if(Util.daysBetween(currentDate, d.getReminderSentOn())>7){
						d.setReminderSent("N");
					}
				}
			}
		}
	}

	@Scheduled(cron="0 0/1 * * * ?")
	public void publishSurveys()
	{
		try
		{
			//Get All Active Doctors for now. Will Apply rules later.
			List<DoctorEntity> doctors = doctorDAO.findByStatus("Active");

			//Get All valid Surveys to be published.
			List<SurveyEntity> surveys = surveyDAO.findSurveysByStatus("Active");
			for(SurveyEntity survey : surveys)
			{
				//Create entry for every doctor.
				for(DoctorEntity doctor : doctors)
				{

					// Also validate if the doctor survey may also be published. In that case, just update the status.
					DoctorSurveyEntity doctorSurveyEntity = doctorSurveyDAO.findByDoctorSurveyId(doctor.getDoctorId(), survey.getSurveyId());
					if(doctorSurveyEntity != null && doctorSurveyEntity.getDoctorSurveyId() > 0 )
					{

						doctorSurveyEntity.setStatus("Pending");
						doctorSurveyEntity.setSurvey(survey);
						doctorSurveyEntity.setDoctorId(doctor.getDoctorId());
						doctorSurveyEntity.setCreatedOn(new Date());
						doctorSurveyEntity.setUpdatedOn(new Date());
						doctorSurveyEntity.setCreatedBy("Scheduler");
						doctorSurveyEntity.setCreatedBy("Scheduler");
						doctorSurveyEntity.setRefId(survey.getRefId());
						doctorSurveyDAO.merge(doctorSurveyEntity);

					}
					else
					{
						doctorSurveyEntity = new DoctorSurveyEntity();
						doctorSurveyEntity.setStatus("Pending");
						doctorSurveyEntity.setSurvey(survey);
						doctorSurveyEntity.setDoctorId(doctor.getDoctorId());
						doctorSurveyEntity.setCreatedOn(new Date());
						doctorSurveyEntity.setUpdatedOn(new Date());
						doctorSurveyEntity.setCreatedBy("Scheduler");
						doctorSurveyEntity.setCreatedBy("Scheduler");
						doctorSurveyDAO.persist(doctorSurveyEntity);
						doctorSurveyEntity.setRefId(survey.getRefId());

					}

				}
				//Once published to doctors, update the status to publish.
				survey.setStatus("Publish");
				surveyDAO.merge(survey);
			}
		}
		catch(Exception e)
		{
			log.error("Publish Survey failed.", e);
		}
	}
}
