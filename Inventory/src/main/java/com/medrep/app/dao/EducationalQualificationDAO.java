package com.medrep.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.EducationalQualificationEntity;
import com.medrep.app.entity.WorkExperienceEntity;

@Repository
public class EducationalQualificationDAO extends MedRepDAO<EducationalQualificationEntity>{

	public List<EducationalQualificationEntity> findByDoctorId(Integer doctorId) {
		List<EducationalQualificationEntity> result = entityManager.createQuery(
				"select edu from EducationalQualificationEntity edu where edu.doctorEntity.doctorId= :doctorId",
				EducationalQualificationEntity.class).setParameter("doctorId", doctorId).getResultList();
		return result;
	}

	public void saveEducationDetailsForDoctor(List<EducationalQualificationEntity> eduEntities, DoctorEntity de) {
		for(EducationalQualificationEntity e:eduEntities){
			e.setDoctorEntity(de);
			persist(e);
		}
	}

	public void saveOrUpdate(List<EducationalQualificationEntity> eucationDetails,DoctorEntity de) throws Exception {
		for(EducationalQualificationEntity w:eucationDetails){
			if(w.getId()==null){
				w.setDoctorEntity(de);
				persist(w);
			}else{
				EducationalQualificationEntity tmp=findById(EducationalQualificationEntity.class, w.getId());

				if(tmp==null || !tmp.getDoctorEntity().getDoctorId().equals(de.getDoctorId()))
					throw new Exception("Unable to find Educational Qualification("+w.getId()+") for the doctor("+de.getDoctorId()+").");

				tmp.setAggregate(w.getAggregate());
				tmp.setCollegeName(w.getCollegeName());
				tmp.setCourse(w.getCourse());
				tmp.setYearOfPassout(w.getYearOfPassout());
				tmp.setDegree(w.getDegree());
				tmp.setSpecialization(w.getSpecialization());
				merge(tmp);
			}
		}

	}

}
