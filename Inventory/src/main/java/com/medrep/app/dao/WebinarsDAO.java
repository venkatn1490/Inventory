package com.medrep.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.PublicationEntity;
import com.medrep.app.entity.WebinarsEntity;

@Repository
public class WebinarsDAO extends MedRepDAO<WebinarsEntity> {

	public List<WebinarsEntity> findByDoctorId(Integer doctorId) {
		List<WebinarsEntity> result = entityManager
				.createQuery("select pub from WebinarsEntity pub where pub.doctorEntity.doctorId= :doctorId",
						WebinarsEntity.class)
				.setParameter("doctorId", doctorId).getResultList();
		return result;
	}

	
	
	public void savePublicationsForDoctor(List<WebinarsEntity> publications, DoctorEntity de) {
		for(WebinarsEntity pub:publications){
			pub.setDoctorEntity(de);
			persist(pub);
		}
	}

	public void saveOrUpdate(List<WebinarsEntity> publications,DoctorEntity de) throws Exception {
		for(WebinarsEntity w:publications){
			if(w.getId()==null){
				w.setDoctorEntity(de);
				persist(w);
			}else{
				WebinarsEntity tmp=findById(WebinarsEntity.class, w.getId());
				if(tmp==null || !tmp.getDoctorEntity().getDoctorId().equals(de.getDoctorId()))
					throw new Exception("Unable to find publication("+w.getId()+") for the doctor("+de.getDoctorId()+").");

					tmp.setWebinarName(w.getWebinarName());
					tmp.setYear(w.getYear());
					tmp.setWebinarType(w.getWebinarType());
					tmp.setUrl(w.getUrl());
					merge(tmp);
			}
		}

	}
}