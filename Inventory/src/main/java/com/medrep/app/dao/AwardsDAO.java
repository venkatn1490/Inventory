package com.medrep.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.medrep.app.entity.AwardsEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.WebinarsEntity;

@Repository
public class AwardsDAO extends MedRepDAO<AwardsEntity> {

	public List<AwardsEntity> findByDoctorId(Integer doctorId) {
		List<AwardsEntity> result = entityManager
				.createQuery("select pub from AwardsEntity pub where pub.doctorEntity.doctorId= :doctorId",
						AwardsEntity.class)
				.setParameter("doctorId", doctorId).getResultList();
		return result;
	}

	
	
	public void saveAwardsForDoctor(List<AwardsEntity> publications, DoctorEntity de) {
		for(AwardsEntity pub:publications){
			pub.setDoctorEntity(de);
			persist(pub);
		}
	}

	public void saveOrUpdate(List<AwardsEntity> awards,DoctorEntity de) throws Exception {
		for(AwardsEntity w:awards){
			if(w.getId()==null){
				w.setDoctorEntity(de);
				persist(w);
			}else{
				AwardsEntity tmp=findById(AwardsEntity.class, w.getId());
				if(tmp==null || !tmp.getDoctorEntity().getDoctorId().equals(de.getDoctorId()))
					throw new Exception("Unable to find publication("+w.getId()+") for the doctor("+de.getDoctorId()+").");

					tmp.setAwardsName(w.getAwardsName());
					tmp.setOrgName(w.getOrgName());
					tmp.setDate(w.getDate());
					merge(tmp);
			}
		}

	}
}