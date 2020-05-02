package com.venkat.app.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.venkat.app.dao.RoleDAO;
import com.venkat.app.dao.SalesUserDAO;
import com.venkat.app.dao.UserDAO;
import com.venkat.app.dao.UserSecurityDAO;
import com.venkat.app.entity.RoleEntity;
import com.venkat.app.entity.SalesUserEntity;
import com.venkat.app.entity.UserEntity;
import com.venkat.app.entity.UserSecurityEntity;
import com.venkat.app.model.Role;
import com.venkat.app.model.SalesUser;
import com.venkat.app.model.User;
import com.venkat.app.model.UserSecurityContext;
import com.venkat.app.util.PasswordProtector;
import com.venkat.app.util.Util;

@Service("userService")
@Transactional
public class UserService implements UserDetailsService{

	@Autowired
	UserSecurityDAO dao;

	@Autowired
	UserDAO userDAO;

	@Autowired
	SalesUserDAO salesUserDAO;
	
	@Autowired
	RoleDAO roleDAO;

	private static final Log log = LogFactory.getLog(UserDetailsService.class);

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserSecurityEntity securityEntity = dao.findByLoginId(userName);
		UserSecurityContext context = null;
		if(securityEntity != null && securityEntity.getLoginId() != null)
		{
			context = new UserSecurityContext();
			context.setPassword(securityEntity.getPassword());
			context.setUsername(securityEntity.getLoginId());
			context.setUserSecurityId(securityEntity.getUserSecId());
			context.setLoginTime(new Date());
			if("ADMIN".equals(securityEntity.getType())){
				if (securityEntity.getStatus().equals("Active")) {
					Role appRole = new Role();
					appRole.setName("ROLE_ADMIN");
					appRole.setDescription("Application Admin User");
					context.getAuthorities().add(appRole);
				}
				else{
					throw new UsernameNotFoundException("Invalid loginId");
				}
			}
			else if("PURCHASER".equals(securityEntity.getType())){
				if (securityEntity.getStatus().equals("Active")) {
				Role appRole = new Role();
				appRole.setName("ROLE_PURCHASER");
				appRole.setDescription("Application Purchaser");
				context.getAuthorities().add(appRole);
				}
				else{
					throw new UsernameNotFoundException("Invalid loginId");
				}
			}
			else if("SUPPLIER".equals(securityEntity.getType())){
				if (securityEntity.getStatus().equals("Active")) {
				Role appRole = new Role();
				appRole.setName("ROLE_SUPPLIER");
				appRole.setDescription("Application Purchaser");
				context.getAuthorities().add(appRole);
				}
				else{
					throw new UsernameNotFoundException("Invalid loginId");
				}
			}
			else if("SALESMAN".equals(securityEntity.getType())){
				if (securityEntity.getStatus().equals("Active")) {
				Role appRole = new Role();
				appRole.setName("ROLE_SALESMAN");
				appRole.setDescription("Application Purchaser");
				context.getAuthorities().add(appRole);
				}
				else{
					throw new UsernameNotFoundException("Invalid loginId");
				}
			}
			else if("CUSTOMER".equals(securityEntity.getType())){
				if (securityEntity.getStatus().equals("Active")) {
				Role appRole = new Role();
				appRole.setName("ROLE_CUSTOMER");
				appRole.setDescription("Application Purchaser");
				context.getAuthorities().add(appRole);
				}
				else{
					throw new UsernameNotFoundException("Invalid loginId");
				}
			}
			securityEntity.setLastLoginDate(new Date());
		    dao.merge(securityEntity);
		}
		else
		{
			throw new UsernameNotFoundException("Invalid loginId");
		}
		return context;
	}


	public User findUserByUserSecurityId(Integer id){
		User user = null;
		UserEntity userEntity = userDAO.findBySecurityId(id);
		if(userEntity != null)
		{
			user = new User();
			user.setEmailId(userEntity.getEmailId());
			user.setFirstName(Util.getTitle(userEntity.getTitle())+userEntity.getFirstName());
			user.setLastName(userEntity.getLastName());
			user.setMobileNo(userEntity.getMobileNo());
			user.setTitle(userEntity.getTitle());
			user.setUserId(userEntity.getUserId());
		}
		return user;
	}

	public String fetchUserNameByUserSecurityId(Integer id)
	{
		String userName="";
		UserEntity userEntity = userDAO.findBySecurityId(id);
		if(userEntity != null)
		{
			userName =Util.getTitle(userEntity.getTitle())+ userEntity.getFirstName();
			userName += " "+userEntity.getLastName();
		}
		return userName;
	}

	public List<User> getAllUsers() {
		List<User> list = new ArrayList<User>();
		try {
			List<UserEntity> entites = userDAO.findAllUsers();
			if(entites!=null) {
				
				for(UserEntity e : entites) {
					User model = new User();
					BeanUtils.copyProperties(e, model);
					model.setRoleId(e.getRole());
					model.setFirstName(e.getFirstName()+" "+e.getLastName());
					model.setUserId(e.getUserId());
						list.add(model);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<User> getUsersByRole(Integer roleId) {
		List<User> list = new ArrayList<User>();
		try {
			List<UserEntity> entites = userDAO.findUsersByRole(roleId);
			if(entites!=null) {
				
				for(UserEntity e : entites) {
					User model = new User();
					BeanUtils.copyProperties(e, model);
					model.setRoleId(e.getRole());
					model.setFirstName(e.getFirstName()+" "+e.getLastName());
					model.setUserId(e.getUserId());
						list.add(model);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<SalesUser> getSalesMan(Integer roleId) {
		List<SalesUser> list = new ArrayList<SalesUser>();
		try {
			List<SalesUserEntity> entites = salesUserDAO.findAllUsers();
			if(entites!=null) {
				
				for(SalesUserEntity e : entites) {
					SalesUser model = new SalesUser();
					BeanUtils.copyProperties(e, model);
					UserEntity user = userDAO.findByUserId(e.getUser().getUserId());
					model.setRoleId(user.getRole());
					model.setFirstName(user.getFirstName()+" "+user.getLastName());
					model.setUserId(e.getUser().getUserId());
					model.setMobileNo(e.getUser().getMobileNo());
					model.setEmailId(e.getUser().getEmailId());
					model.setGender(e.getUser().getGender());
					model.setStatus(e.getUser().getStatus());
					UserEntity purchaser = userDAO.findByUserId(e.getPurchaserId());
					model.setPurchaserName(purchaser.getFirstName() + " " + purchaser.getLastName());
						list.add(model);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public void createSalesUser(Model model) throws IOException, ParseException{
		SalesUser userModel=(SalesUser) model.asMap().get("user");
		try {
		UserSecurityEntity securityEntity = new UserSecurityEntity();
			securityEntity.setStatus("Active");
			securityEntity.setLoginId(userModel.getUsername());
			securityEntity.setPassword(PasswordProtector.encrypt(userModel.getPassword()));
			RoleEntity roleEntity = roleDAO.findByRoleId(userModel.getRoleId());
			securityEntity.setType(roleEntity.getRoleName());
			securityEntity.setCreatedDate(Calendar.getInstance().getTime());		
		    UserEntity userEntity = new UserEntity();				
		    userEntity.setSecurity(securityEntity);		   
		    userEntity.setFirstName(userModel.getFirstName());
		    userEntity.setLastName(userModel.getLastName());
		    userEntity.setEmailId(userModel.getEmailId());
			userEntity.setMobileNo(userModel.getMobileNo());
			userEntity.setTitle(userModel.getTitle());
			userEntity.setGender(userModel.getGender());
		    userEntity.setDob((new SimpleDateFormat("yyyy-MM-dd").parse(userModel.getDob())));
		    userEntity.setStatus("Active");	
		    userEntity.setRole(userModel.getRoleId());
		    userEntity.setCreatedDate(Calendar.getInstance().getTime());
		    userDAO.persist(userEntity);
		    
		    SalesUserEntity salesUserEntity = new SalesUserEntity();
		    salesUserEntity.setPurchaserId(userModel.getPurchaserUserId());
		    salesUserEntity.setUser(userEntity);
		    salesUserDAO.persist(salesUserEntity);
		    
			}
		   catch(Exception e) {
			 e.printStackTrace();

		   }

		}
	
	public void createUser(Model model) throws IOException, ParseException{
		User userModel=(User) model.asMap().get("user");
		try {
		UserSecurityEntity securityEntity = new UserSecurityEntity();
			securityEntity.setStatus("Active");
			securityEntity.setLoginId(userModel.getUsername());
			securityEntity.setPassword(PasswordProtector.encrypt(userModel.getPassword()));
			RoleEntity roleEntity = roleDAO.findByRoleId(userModel.getRoleId());
			securityEntity.setType(roleEntity.getRoleName());
			securityEntity.setCreatedDate(Calendar.getInstance().getTime());		
		    UserEntity userEntity = new UserEntity();				
		    userEntity.setSecurity(securityEntity);		   
		    userEntity.setFirstName(userModel.getFirstName());
		    userEntity.setLastName(userModel.getLastName());
		    userEntity.setEmailId(userModel.getEmailId());
			userEntity.setMobileNo(userModel.getMobileNo());
			userEntity.setTitle(userModel.getTitle());
			userEntity.setGender(userModel.getGender());
		    userEntity.setDob((new SimpleDateFormat("yyyy-MM-dd").parse(userModel.getDob())));
		    userEntity.setStatus("Active");	
		    userEntity.setRole(userModel.getRoleId());
		    userEntity.setCreatedDate(Calendar.getInstance().getTime());

		    userDAO.persist(userEntity);
			}
		   catch(Exception e) {
			 e.printStackTrace();

		   }

		}
	
}
