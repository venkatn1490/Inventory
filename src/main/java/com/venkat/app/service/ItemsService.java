package com.venkat.app.service;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.venkat.app.dao.MasterItemDAO;
import com.venkat.app.dao.RoleDAO;
import com.venkat.app.dao.SupplierOrderDAO;
import com.venkat.app.dao.UserDAO;
import com.venkat.app.entity.MasterItemsEntity;
import com.venkat.app.entity.SupplierOrdersEnity;
import com.venkat.app.entity.UserEntity;
import com.venkat.app.model.MasterItemModel;
import com.venkat.app.model.Role;
import com.venkat.app.model.SupplierOrderModel;
import com.venkat.app.model.User;

@Service("itemsService")
@Transactional
public class ItemsService {

	@Autowired
	MasterItemDAO masterItemDAO;

	@Autowired
	UserDAO userDAO;
	
	private static final Log log = LogFactory.getLog(ItemsService.class);
	
	public List<MasterItemModel> getAllMasterItems()
	{
		List<MasterItemModel> items = new ArrayList<MasterItemModel>();
		try
		{
			for(MasterItemsEntity itemEntity : masterItemDAO.findAllItems())
			{
				MasterItemModel item = new MasterItemModel();
				item.setId(itemEntity.getId());
				item.setItemName(itemEntity.getItemName());
				item.setDescription(itemEntity.getDecsription());
				items.add(item);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return items;

	}
	public List<MasterItemModel> getMasterItemsById(Integer id)
	{
		List<MasterItemModel> items = new ArrayList<MasterItemModel>();
		try
		{
			for(MasterItemsEntity itemEntity : masterItemDAO.findAllItems())
			{
				MasterItemModel item = new MasterItemModel();
				item.setId(itemEntity.getId());
				item.setItemName(itemEntity.getItemName());
				item.setDescription(itemEntity.getDecsription());
				items.add(item);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return items;

	}



}
