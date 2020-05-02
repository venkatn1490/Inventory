package com.medrep.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.InterestedAreasEntity;
import com.medrep.app.entity.PublicationEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;

@Repository
public class InterestedAreasDAO extends MedRepDAO<InterestedAreasEntity> {
	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;
	public List<InterestedAreasEntity> findByDoctorId(Integer doctorId) {
		List<InterestedAreasEntity> result = entityManager
				.createQuery("select ia from InterestedAreasEntity ia where ia.doctorEntity.doctorId= :doctorId",
						InterestedAreasEntity.class)
				.setParameter("doctorId", doctorId).getResultList();
		return result;
	}

	public void saveInterestsForDoctor(List<InterestedAreasEntity> interests, DoctorEntity de) {
		for(InterestedAreasEntity interest:interests){
			interest.setDoctorEntity(de);
			persist(interest);
		}
	}

	public void saveOrUpdate(List<InterestedAreasEntity> interests, DoctorEntity de) throws Exception {
		for(InterestedAreasEntity w:interests){
			if(w.getId()==null){
				w.setDoctorEntity(de);
				persist(w);
			}else{
				InterestedAreasEntity tmp=findById(InterestedAreasEntity.class, w.getId());

				if(tmp!=null && tmp.getDoctorEntity().getDoctorId().equals(de.getDoctorId())){
				tmp.setName(w.getName());
				merge(tmp);
				}else{
//					TherapeuticAreaEntity th=therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, w.getId());
//					if(th==null)
//						throw new Exception("Therapeutic Area/Interests("+w.getId()+") not found with the id for the doctor."+w.getId());
					TherapeuticAreaEntity th=therapeuticAreaDAO.findByName(w.getName());
					de.setTherapeuticId(th.getTherapeuticId()+"");
//					th.setTherapeuticName(w.getName());
//					therapeuticAreaDAO.merge(th);

				}

			}
		}

	}

	public InterestedAreasEntity findMaxInterest(Integer doctorId) {
		InterestedAreasEntity result = entityManager
				.createQuery("select ia from InterestedAreasEntity ia where ia.doctorEntity.doctorId= :doctorId and ia.id=(select max(i.id) from InterestedAreasEntity i where i.doctorEntity.doctorId= :doctorId)",
						InterestedAreasEntity.class)
				.setParameter("doctorId", doctorId).getSingleResult();
		return result;
	}
}
