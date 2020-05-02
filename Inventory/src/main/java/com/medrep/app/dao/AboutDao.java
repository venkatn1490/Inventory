package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.AboutEntity;

/**
 * 
 * @author wenky
 *
 */
@Repository
public class AboutDao extends MedRepDAO<AboutEntity> {

	private static final Log log = LogFactory.getLog(AboutDao.class);

	public AboutEntity findByDoctorId(Integer doctorId) {
		List<AboutEntity> result = entityManager
				.createQuery("select ae from AboutEntity ae where ae.doctorEntity.doctorId= :doctorId",
						AboutEntity.class)
				.setParameter("doctorId", doctorId).getResultList();
		if (result.isEmpty())
			return null;

		return result.get(0);
	}
}