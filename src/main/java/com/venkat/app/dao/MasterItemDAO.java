package com.venkat.app.dao;
// Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.venkat.app.entity.MasterItemsEntity;
import com.venkat.app.entity.RoleEntity;
import com.venkat.app.entity.SalesUserEntity;
import com.venkat.app.entity.UserEntity;
import com.venkat.app.entity.UserSecurityEntity;
import com.venkat.app.model.User;

/**
 * Home object for domain model class TUser.
 * @see com.venkat.app.dao.UserEntity
 * @author Hibernate Tools
 */
@Repository
public class MasterItemDAO extends VenkatDAO<MasterItemsEntity>{

	private static final Log log = LogFactory.getLog(MasterItemDAO.class);

	
	public List<MasterItemsEntity> findAllItems(){
		List<MasterItemsEntity> entites = null;
		try {
			entites = entityManager.createQuery("select r from MasterItemsEntity r",MasterItemsEntity.class).getResultList();
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return entites;
		}

	public MasterItemsEntity findByitemId(Integer id) {
		MasterItemsEntity itemEntity =  new MasterItemsEntity();
		try
		{
			itemEntity = entityManager.createQuery("select s from MasterItemsEntity s Where s.id = :id",MasterItemsEntity.class).setParameter("id", id).getSingleResult();
			log.info("get successfull");
		}
		catch(Exception e)
		{
			log.error("Failed : "+e);
		}
		return itemEntity;
	}

}
