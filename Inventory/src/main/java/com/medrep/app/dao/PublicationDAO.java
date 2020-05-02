package com.medrep.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.EducationalQualificationEntity;
import com.medrep.app.entity.InterestedAreasEntity;
import com.medrep.app.entity.PublicationEntity;

@Repository
public class PublicationDAO extends MedRepDAO<PublicationEntity> {

	public List<PublicationEntity> findByDoctorId(Integer doctorId) {
		List<PublicationEntity> result = entityManager
				.createQuery("select pub from PublicationEntity pub where pub.doctorEntity.doctorId= :doctorId",
						PublicationEntity.class)
				.setParameter("doctorId", doctorId).getResultList();
		return result;
	}

	public void savePublicationsForDoctor(List<PublicationEntity> publications, DoctorEntity de) {
			for(PublicationEntity pub:publications){
				pub.setDoctorEntity(de);
				persist(pub);
			}
	}

	public void saveOrUpdate(List<PublicationEntity> publications,DoctorEntity de) throws Exception {
		for(PublicationEntity w:publications){
			if(w.getId()==null){
				w.setDoctorEntity(de);
				persist(w);
			}else{
				PublicationEntity tmp=findById(PublicationEntity.class, w.getId());
				if(tmp==null || !tmp.getDoctorEntity().getDoctorId().equals(de.getDoctorId()))
					throw new Exception("Unable to find publication("+w.getId()+") for the doctor("+de.getDoctorId()+").");

					tmp.setArticleName(w.getArticleName());
					tmp.setYear(w.getYear());
					tmp.setPublication(w.getPublication());
					tmp.setUrl(w.getUrl());
					merge(tmp);
			}
		}

	}
}