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

import com.venkat.app.entity.CustomerOrderItemsEnity;
import com.venkat.app.entity.CustomerOrderItemsEnity;
import com.venkat.app.entity.RoleEntity;
import com.venkat.app.entity.SalesUserEntity;
import com.venkat.app.entity.SupplierOrdersEnity;
import com.venkat.app.entity.UserEntity;
import com.venkat.app.entity.UserSecurityEntity;
import com.venkat.app.model.User;

/**
 * Home object for domain model class TUser.
 * @see com.venkat.app.dao.UserEntity
 * @author Hibernate Tools
 */
@Repository
public class CustomerOrderItemsDAO extends VenkatDAO<CustomerOrderItemsEnity>{

	private static final Log log = LogFactory.getLog(CustomerOrderItemsDAO.class);

	
	public List<CustomerOrderItemsEnity> findByOrderId(Integer orderId){
		List<CustomerOrderItemsEnity> entites = null;
		try {
			entites = entityManager.createQuery("select s from CustomerOrderItemsEnity s Where s.order.id = :orderId",CustomerOrderItemsEnity.class).setParameter("orderId", orderId).getResultList();

		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return entites;
		}
	
}
