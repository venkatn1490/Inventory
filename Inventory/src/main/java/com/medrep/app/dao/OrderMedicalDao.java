package com.medrep.app.dao;
//Generated Aug 2, 2015 5:41:10 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.MedicalDeviceEntity;
import com.medrep.app.entity.OrderMedicalDeviceEntity;

/**
* DOA object for domain model class TDoctor.
* @see com.medrep.app.dao.DoctorEntity
* @author Hibernate Tools
*/
@Repository("orderMedicalDAO")
public class OrderMedicalDao  extends MedRepDAO<OrderMedicalDeviceEntity>{

	private static final Log LOG = LogFactory.getLog(OrderMedicalDao.class);

	public List<MedicalDeviceEntity> getDeviceList(){
		List<MedicalDeviceEntity> deviceList =new ArrayList<MedicalDeviceEntity>();
		LOG.info("getting Device list");
		try
		{
			deviceList = entityManager.createQuery("SELECT p FROM OrderMedicalDeviceEntity p where  p.status = :status  order by p.createdOn desc  ", MedicalDeviceEntity.class).setParameter("status", "Active")
					.setFirstResult(0).setMaxResults(30).getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return deviceList;
	}
	public List<OrderMedicalDeviceEntity> getOrderListByDoctor(String loginId){
		List<OrderMedicalDeviceEntity> orderList =new ArrayList<OrderMedicalDeviceEntity>();
		LOG.info("getting Device list");
		try
		{
			orderList = entityManager.createQuery("SELECT p FROM OrderMedicalDeviceEntity p where  p.orderBy = :orderBy  order by p.orderOn desc  ", OrderMedicalDeviceEntity.class).setParameter("orderBy", loginId)
					.getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return orderList;
	}

	public List<OrderMedicalDeviceEntity> getAllOrdersList(){
		List<OrderMedicalDeviceEntity> orderList =new ArrayList<OrderMedicalDeviceEntity>();
		LOG.info("getting Device list");
		try
		{
			orderList = entityManager.createQuery("SELECT p FROM OrderMedicalDeviceEntity p   order by p.orderOn desc  ", OrderMedicalDeviceEntity.class)
					.getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return orderList;
	}
}
