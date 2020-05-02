package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.PharmaTherapeuticsEntity;
import com.medrep.app.entity.PharmaTherapeuticsIdEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;

/**
 * Home object for domain model class TPharmaTherapeutics.
 * @see com.medrep.app.dao.PharmaTherapeuticsEntity
 * @author Hibernate Tools
 */
@Repository

public class PharmaTherapeuticsDAO extends MedRepDAO<PharmaTherapeuticsEntity>{
	
	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	private static final Log log = LogFactory.getLog(PharmaTherapeuticsDAO.class);

	public void removeByCompanyId(Integer companyId) {
		log.info("removing TPharmaTherapeutics instance : Company Id ="+companyId);
		try {
			  List<PharmaTherapeuticsEntity> pharmaTherapeuticsEntities = entityManager.createQuery("select p from PharmaTherapeuticsEntity p where p.id.companyId = :companyId", PharmaTherapeuticsEntity.class).setParameter("companyId",companyId).getResultList();
			  for(PharmaTherapeuticsEntity pharmaTherapeuticsEntity : pharmaTherapeuticsEntities)
			  {
				  entityManager.remove(pharmaTherapeuticsEntity);
			  }
			  //entityManager.createQuery("delete from PharmaTherapeuticsIdEntity  where companyId = :companyId", PharmaTherapeuticsIdEntity.class).setParameter("companyId",companyId).executeUpdate();
			log.info("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
		}
	}
	
	public Set<TherapeuticAreaEntity> findTherapeuticAreasByCompanyId(Integer companyId){
		
		  Set<TherapeuticAreaEntity> therapeuticAreaEntities = new HashSet<TherapeuticAreaEntity>();
		  try{
			  List<PharmaTherapeuticsEntity> pharmaTherapeuticsEntities = entityManager.createQuery("select p from PharmaTherapeuticsEntity p where p.id.companyId = :companyId", PharmaTherapeuticsEntity.class).setParameter("companyId",companyId).getResultList();
			  for(PharmaTherapeuticsEntity pharmaTherapeuticsEntity : pharmaTherapeuticsEntities)
			  {
				  TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,pharmaTherapeuticsEntity.getId().getTherapeuticId());
				  therapeuticAreaEntities.add(therapeuticAreaEntity);
			  }
		  }catch (RuntimeException re) {
				log.error("remove failed", re);
			}
	
		return therapeuticAreaEntities;
	}
}
