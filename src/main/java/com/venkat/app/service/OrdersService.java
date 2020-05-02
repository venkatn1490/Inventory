package com.venkat.app.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.venkat.app.dao.CustomerOrderDAO;
import com.venkat.app.dao.CustomerOrderItemsDAO;
import com.venkat.app.dao.MasterItemDAO;
import com.venkat.app.dao.PurchaserStockDAO;
import com.venkat.app.dao.RoleDAO;
import com.venkat.app.dao.SalesUserDAO;
import com.venkat.app.dao.SupplierOrderDAO;
import com.venkat.app.dao.SupplierOrderItemsDAO;
import com.venkat.app.dao.SupplierStockDAO;
import com.venkat.app.dao.UserDAO;
import com.venkat.app.entity.CustomerOrderItemsEnity;
import com.venkat.app.entity.CustomerOrdersEnity;
import com.venkat.app.entity.MasterItemsEntity;
import com.venkat.app.entity.PurchaserStockEnity;
import com.venkat.app.entity.RoleEntity;
import com.venkat.app.entity.SalesUserEntity;
import com.venkat.app.entity.SupplierOrderItemsEnity;
import com.venkat.app.entity.SupplierOrdersEnity;
import com.venkat.app.entity.SupplierStockEnity;
import com.venkat.app.entity.UserEntity;
import com.venkat.app.entity.UserSecurityEntity;
import com.venkat.app.model.CustomerOrderModel;
import com.venkat.app.model.Role;
import com.venkat.app.model.SalesUser;
import com.venkat.app.model.SupplierOrderItemModel;
import com.venkat.app.model.SupplierOrderModel;
import com.venkat.app.model.User;
import com.venkat.app.util.PasswordProtector;

@Service("ordersService")
@Transactional
public class OrdersService {

	@Autowired
	SupplierOrderDAO supplierOrderDAO;
	
	@Autowired
	SupplierOrderItemsDAO supplierOrderItemsDAO;
	
	@Autowired
	MasterItemDAO masterItemDAO;

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	CustomerOrderDAO customerOrderDAO;
	
	@Autowired
	CustomerOrderItemsDAO customerOrderItemsDAO;
	
	@Autowired
	SalesUserDAO salesUserDAO;
	
	@Autowired
	SupplierStockDAO supplierStockDAO;
	
	@Autowired
	PurchaserStockDAO purchaserStockDAO;
	
	private static final Log log = LogFactory.getLog(OrdersService.class);
	
	public List<SupplierOrderModel> getAllSupplierOrdersById(Integer userId)
	{
		List<SupplierOrderModel> orders = new ArrayList<SupplierOrderModel>();
		try
		{
			for(SupplierOrdersEnity supplierOrderEntity : supplierOrderDAO.findByUserId(userId))
			{
				SupplierOrderModel order = new SupplierOrderModel();
				order.setOrderId(supplierOrderEntity.getId());
				order.setPurchaserId(supplierOrderEntity.getPurchaserId());
				UserEntity user = userDAO.findByUserId(supplierOrderEntity.getPurchaserId());
				order.setPurchaserMobile(user.getMobileNo());
				order.setPurchaserName(user.getFirstName() + " " + user.getLastName());
				order.setCreatedDate(supplierOrderEntity.getCreatedDate().toString());
				orders.add(order);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orders;

	}
	public List<SupplierOrderModel> getAllPurchaserOrdersById(Integer userId)
	{
		List<SupplierOrderModel> orders = new ArrayList<SupplierOrderModel>();
		try
		{
			for(SupplierOrdersEnity supplierOrderEntity : supplierOrderDAO.findByPurchaserId(userId))
			{
				SupplierOrderModel order = new SupplierOrderModel();
				order.setOrderId(supplierOrderEntity.getId());
				order.setPurchaserId(supplierOrderEntity.getPurchaserId());
				UserEntity user = userDAO.findByUserId(supplierOrderEntity.getPurchaserId());
				order.setPurchaserMobile(user.getMobileNo());
				order.setPurchaserName(user.getFirstName() + " " + user.getLastName());
				order.setCreatedDate(supplierOrderEntity.getCreatedDate().toString());
				orders.add(order);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orders;

	}

	public List<SupplierOrderItemModel> getAllSupplierOrdersItemsById(Integer orderId)
	{
		List<SupplierOrderItemModel> orders = new ArrayList<SupplierOrderItemModel>();
		try
		{
			for(SupplierOrderItemsEnity supplierOrderItemEntity : supplierOrderItemsDAO.findByOrderId(orderId))
			{
				SupplierOrderItemModel order = new SupplierOrderItemModel();
				order.setOrderId(supplierOrderItemEntity.getId());
				order.setItemId(supplierOrderItemEntity.getItemId());
				MasterItemsEntity masterItem = masterItemDAO.findByitemId(supplierOrderItemEntity.getItemId());
				order.setItemName(masterItem.getItemName());
				order.setStock(supplierOrderItemEntity.getStock());
				orders.add(order);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orders;

	}

	public List<CustomerOrderModel> getAllCustomerOrdersById(Integer userId)
	{
		List<CustomerOrderModel> orders = new ArrayList<CustomerOrderModel>();
		try
		{
			for(CustomerOrdersEnity supplierOrderEntity : customerOrderDAO.findByCustomerId(userId))
			{
				CustomerOrderModel order = new CustomerOrderModel();
				order.setOrderId(supplierOrderEntity.getId());
				order.setPurchaserId(supplierOrderEntity.getPurchaserId());
				UserEntity user = userDAO.findByUserId(supplierOrderEntity.getSalesmanId());
				order.setSalesmanMobile(user.getMobileNo());
				order.setSalesmanName(user.getFirstName() + " " + user.getLastName());
				order.setCreatedDate(supplierOrderEntity.getCreatedDate().toString());
				orders.add(order);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orders;

	}
	public List<CustomerOrderModel> getAllSalesmanOrdersById(Integer userId)
	{
		List<CustomerOrderModel> orders = new ArrayList<CustomerOrderModel>();
		try
		{
			for(CustomerOrdersEnity supplierOrderEntity : customerOrderDAO.findBySalesmanId(userId))
			{
				CustomerOrderModel order = new CustomerOrderModel();
				order.setOrderId(supplierOrderEntity.getId());
				order.setPurchaserId(supplierOrderEntity.getPurchaserId());
				UserEntity user = userDAO.findByUserId(supplierOrderEntity.getCustomerId());
				order.setCustomerMobile(user.getMobileNo());
				order.setCustomerName(user.getFirstName() + " " + user.getLastName());
				order.setCreatedDate(supplierOrderEntity.getCreatedDate().toString());
				orders.add(order);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orders;

	}
	public List<CustomerOrderModel> getAllPurchaserWiseOrdersById(Integer userId)
	{
		List<CustomerOrderModel> orders = new ArrayList<CustomerOrderModel>();
		try
		{
			for(CustomerOrdersEnity supplierOrderEntity : customerOrderDAO.findByPurchaserId(userId))
			{
				CustomerOrderModel order = new CustomerOrderModel();
				order.setOrderId(supplierOrderEntity.getId());
				order.setPurchaserId(supplierOrderEntity.getPurchaserId());
				UserEntity user = userDAO.findByUserId(supplierOrderEntity.getSalesmanId());
				order.setSalesmanMobile(user.getMobileNo());
				order.setSalesmanName(user.getFirstName() + " " + user.getLastName());
				order.setCreatedDate(supplierOrderEntity.getCreatedDate().toString());
				orders.add(order);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orders;

	}
	public List<SupplierOrderItemModel> getAllCustomerOrdersItemsById(Integer orderId)
	{
		List<SupplierOrderItemModel> orders = new ArrayList<SupplierOrderItemModel>();
		try
		{
			for(CustomerOrderItemsEnity supplierOrderItemEntity : customerOrderItemsDAO.findByOrderId(orderId))
			{
				SupplierOrderItemModel order = new SupplierOrderItemModel();
				order.setOrderId(supplierOrderItemEntity.getId());
				order.setItemId(supplierOrderItemEntity.getItemId());
				MasterItemsEntity masterItem = masterItemDAO.findByitemId(supplierOrderItemEntity.getItemId());
				order.setItemName(masterItem.getItemName());
				order.setStock(supplierOrderItemEntity.getStock());
				orders.add(order);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orders;

	}
	public void createNewCustomerOrder(Model model) throws IOException, ParseException{
		CustomerOrderModel orderModel=(CustomerOrderModel) model.asMap().get("order");
		SupplierOrderItemModel itemModel=(SupplierOrderItemModel) model.asMap().get("item");
		try {
			CustomerOrdersEnity orderEntity = new CustomerOrdersEnity();
			orderEntity.setSalesmanId(orderModel.getSalesmanId());
			orderEntity.setCustomerId(orderModel.getCustomerId());
			SalesUserEntity salesUser = salesUserDAO.findByUserId(orderModel.getSalesmanId());
			orderEntity.setPurchaserId(salesUser.getPurchaserId());
			orderEntity.setCreatedDate(Calendar.getInstance().getTime());
			customerOrderDAO.persist(orderEntity);
			
			CustomerOrderItemsEnity itemsEnity = new CustomerOrderItemsEnity();
			itemsEnity.setItemId(itemModel.getItemId());
			itemsEnity.setStock(itemModel.getStock());
			itemsEnity.setOrder(orderEntity);
			customerOrderItemsDAO.persist(itemsEnity);
			
			PurchaserStockEnity stock = purchaserStockDAO.findByItemId(itemModel.getItemId());
			stock.setAvailableStock(stock.getTotalStock() - itemModel.getStock());
			purchaserStockDAO.merge(stock);
			
			}
		   catch(Exception e) {
			 e.printStackTrace();

		   }

		}
	
	public void createNewSupplierOrder(Model model) throws IOException, ParseException{
		SupplierOrderModel orderModel=(SupplierOrderModel) model.asMap().get("order");
		SupplierOrderItemModel itemModel=(SupplierOrderItemModel) model.asMap().get("item");
		try {
			SupplierOrdersEnity orderEntity = new SupplierOrdersEnity();
			orderEntity.setCreatedDate(Calendar.getInstance().getTime());
			orderEntity.setPurchaserId(orderModel.getPurchaserId());
			orderEntity.setSupplierId(orderModel.getSupplierId());
			supplierOrderDAO.persist(orderEntity);
			SupplierOrderItemsEnity itemsEnity = new SupplierOrderItemsEnity();
			itemsEnity.setItemId(itemModel.getItemId());
			itemsEnity.setStock(itemModel.getStock());
			itemsEnity.setOrder(orderEntity);
			supplierOrderItemsDAO.persist(itemsEnity);
			
			SupplierStockEnity stock = supplierStockDAO.findByitemId(itemModel.getItemId());
			stock.setAvailableStock(stock.getTotalStock() - itemModel.getStock());
			supplierStockDAO.merge(stock);
			
			PurchaserStockEnity pStock = purchaserStockDAO.findByItemId(itemModel.getItemId());
			pStock.setTotalStock(pStock.getTotalStock() + itemModel.getStock());
			pStock.setAvailableStock(pStock.getAvailableStock() + itemModel.getStock());
			purchaserStockDAO.merge(pStock);
					
			}
		   catch(Exception e) {
			 e.printStackTrace();

		   }

		}
	

}
