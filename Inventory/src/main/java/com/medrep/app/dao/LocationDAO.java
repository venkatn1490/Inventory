package com.medrep.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.UserEntity;

/**
 * Home object for domain model class TLocation.
 * @see com.medrep.app.dao.LocationEntity
 * @author Hibernate Tools
 */
@Repository
public class LocationDAO extends MedRepDAO<LocationEntity>{

	private static final Log log = LogFactory.getLog(LocationDAO.class);

	public void removeByLocationId(Integer locationId) {
		log.info("removing TLocation instance"+locationId);
		try {
			  entityManager.createQuery("delete l from LocationEntity l where l.locationId = :locationId", LocationEntity.class).setParameter("locationId",locationId).executeUpdate();
			  log.info("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
		}
	}

	public List<LocationEntity> findByCity(String city) {
		log.info("TLocation instance"+city);
		List<LocationEntity> locationEntity = new ArrayList<LocationEntity>();
		try {
			locationEntity = entityManager.createQuery("select d from LocationEntity d where d.city = :city", LocationEntity.class)
					.setParameter("city", city)
					.getResultList();
			  log.info("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
		}

		return locationEntity;
	}

	public LocationEntity findByUserId(Integer id) {
		LocationEntity locationEntity = new LocationEntity();
		try {
			locationEntity = entityManager.createQuery("select d from LocationEntity d where d.user.userId = :id", LocationEntity.class)
					.setParameter("id", id)
					.getSingleResult();
			  log.info("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
		}

		return locationEntity;
	}

	public List<LocationEntity> findLocationsByUserId(Integer id) {
		List<LocationEntity> locationEntity = null;
		try {
			locationEntity = entityManager.createQuery("select d from LocationEntity d where d.user.userId = :id", LocationEntity.class)
					.setParameter("id", id).getResultList();
		} catch (Exception re) {
		re.printStackTrace();
		}

		return locationEntity;
	}

	public void changeAddress(List<LocationEntity> locations, UserEntity userEntity) {
		for (LocationEntity locationEntity : locations) {
			if (locationEntity.getLocationId() != null
					&& findById(LocationEntity.class, locationEntity.getLocationId()) != null) {
				locationEntity.setUser(userEntity);
				LocationEntity tmp = merge(locationEntity);
				locationEntity.setUser(userEntity);
				tmp.setLocationId(locationEntity.getLocationId());
				if (locationEntity.getAddress1() != null)
					tmp.setAddress1(locationEntity.getAddress1());
				if (locationEntity.getAddress2() != null)
					tmp.setAddress2(locationEntity.getAddress2());
				if (locationEntity.getCity() != null)
					tmp.setCity(locationEntity.getCity());
				if (locationEntity.getCountry() != null)
					tmp.setCountry(locationEntity.getCountry());
				if (locationEntity.getState() != null)
					tmp.setState(locationEntity.getState());
				if (locationEntity.getZipcode() != null)
					tmp.setZipcode(locationEntity.getZipcode());
			}
			else {
					locationEntity.setUser(userEntity);
					entityManager.persist(locationEntity);
				}
			}
		}

	public List<LocationEntity> findByCities(List<String> cities) {
		List<LocationEntity> locationEntity = new ArrayList<LocationEntity>();
		try {
			locationEntity = entityManager.createQuery("select d from LocationEntity d where d.city in :cities", LocationEntity.class)
					.setParameter("cities", cities)
					.getResultList();
			  System.out.println("remove successful");
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("remove failed", re);
		}

		return locationEntity;
	}
}