package com.medrep.app.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.medrep.app.entity.AboutEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.InterestedAreasEntity;
import com.medrep.app.entity.WorkExperienceEntity;

@Repository
public class WorkExperiencesDAO extends MedRepDAO<WorkExperienceEntity> {

	public void saveWorkExperienceForDoctor(List<WorkExperienceEntity> workExperiencesList, DoctorEntity de) {

		for (WorkExperienceEntity we : workExperiencesList) {
//			we.setDate(new SimpleDateFormat("dd-MM-yyyy").format(we.getFromDate()))
			we.setDoctorEntity(de);
			this.persist(we);
		}
	}

	public List<WorkExperienceEntity> getWorkExperience(Integer doctorId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WorkExperienceEntity> findByDoctorId(Integer doctorId) {
		List<WorkExperienceEntity> result = entityManager
				.createQuery("select we from WorkExperienceEntity we where we.doctorEntity.doctorId= :doctorId",
						WorkExperienceEntity.class)
				.setParameter("doctorId", doctorId).getResultList();
		if (result.isEmpty())
			return null;

		return result;
	}

	public void saveOrUpdate(List<WorkExperienceEntity> workExperienceEntities,DoctorEntity de) throws Exception {
		for(WorkExperienceEntity w:workExperienceEntities){
			if(w.getId()==null){
				w.setDoctorEntity(de);
				persist(w);
			}else{
				WorkExperienceEntity tmp=findById(WorkExperienceEntity.class, w.getId());
				if(tmp==null || !tmp.getDoctorEntity().getDoctorId().equals(de.getDoctorId()))
					throw new Exception("Unable to find workexperience for the doctor");
				tmp.setDesignation(w.getDesignation());
				tmp.setFromDate(w.getFromDate());
				tmp.setToDate(w.getToDate());
				tmp.setLocation(w.getLocation());
				tmp.setHospital(w.getHospital());
				tmp.setCurrentJob(w.getCurrentJob());
				tmp.setSummary(w.getSummary());
				merge(tmp);
			}
		}

	}
}