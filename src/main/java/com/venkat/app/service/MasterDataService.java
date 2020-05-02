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


import com.venkat.app.dao.RoleDAO;
import com.venkat.app.dao.UserDAO;

import com.venkat.app.model.Role;

@Service("masterDataService")
@Transactional
public class MasterDataService {

	@Autowired
	RoleDAO roleDAO;


	/*@Autowired
	LocationDAO locationDAO;*/

	@Autowired
	UserDAO userDAO;
	
	

	private static final Log log = LogFactory.getLog(MasterDataService.class);
	
	
	public List<Role> getAllRoles()
	{
		List<Role> roles = new ArrayList<Role>();
		try
		{
			for(com.venkat.app.entity.RoleEntity roleEntity : roleDAO.findAll())
			{
				Role role = new Role();
				role.setRoleId(roleEntity.getRoleId());
				role.setName(roleEntity.getRoleName());
				role.setDescription(roleEntity.getRoleDesc());
				roles.add(role);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return roles;

	}



}
