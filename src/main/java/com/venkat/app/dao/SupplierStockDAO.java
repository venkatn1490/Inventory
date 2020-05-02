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

import com.venkat.app.entity.RoleEntity;
import com.venkat.app.entity.SalesUserEntity;
import com.venkat.app.entity.SupplierOrderItemsEnity;
import com.venkat.app.entity.SupplierOrdersEnity;
import com.venkat.app.entity.SupplierStockEnity;
import com.venkat.app.entity.UserEntity;
import com.venkat.app.entity.UserSecurityEntity;
import com.venkat.app.model.User;

/**
 * Home object for domain model class TUser.
 * @see com.venkat.app.dao.UserEntity
 * @author Hibernate Tools
 */
@Repository
public class SupplierStockDAO extends VenkatDAO<SupplierStockEnity>{

	private static final Log log = LogFactory.getLog(SupplierStockDAO.class);

	
	public SupplierStockEnity findByitemId(Integer itemId){
		SupplierStockEnity entites = null;
		try {
			entites = entityManager.createQuery("select s from SupplierStockEnity s Where s.itemId = :itemId",SupplierStockEnity.class).setParameter("itemId", itemId).getSingleResult();

		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return entites;
		}
}
