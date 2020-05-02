package com.medrep.app.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.OTPDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.dao.UserSecurityDAO;
import com.medrep.app.entity.OTPEntity;
import com.medrep.app.entity.RoleEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.entity.UserSecurityEntity;
import com.medrep.app.model.Role;
import com.medrep.app.model.User;
import com.medrep.app.model.UserSecurityContext;
import com.medrep.app.util.Util;

@Service("userService")
@Transactional
public class UserService implements UserDetailsService{

	@Autowired
	UserSecurityDAO dao;

	@Autowired
	UserDAO userDAO;

	@Autowired
	OTPDAO otpdao;

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
			if("ADMIN".equals(securityEntity.getType()))
			{
				Role appRole = new Role();
				appRole.setName("ROLE_ADMIN");
				appRole.setDescription("Application Admin User");
				context.getAuthorities().add(appRole);
			}
			else
			{
				Role appRole = new Role();
				appRole.setName("ROLE_APP_USER");
				appRole.setDescription("Mobile Application User");
				context.getAuthorities().add(appRole);
				appRole = new Role();
				appRole.setName("ROLE_WEB_USER");
				appRole.setDescription("Web Application User");
				context.getAuthorities().add(appRole);
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


	public User findUserByEmailId(String emailId) {
		User user = null;
		UserEntity userEntiry = userDAO.findByEmailId(emailId);
		if(userEntiry != null && userEntiry.getUserId() != null)
		{
			user = new User();
			user.setAlias(userEntiry.getAlias());
			user.setAlternateEmailId(userEntiry.getAlternateEmailId());
			user.setEmailId(userEntiry.getEmailId());
			user.setFirstName(Util.getTitle(userEntiry.getTitle())+userEntiry.getFirstName());
//			user.setMiddleName(userEntiry.getMiddleName());
			user.setLastName(userEntiry.getLastName());
			user.setMobileNo(userEntiry.getMobileNo());
			user.setTitle(userEntiry.getTitle());
			user.setAlternateEmailId(userEntiry.getAlternateEmailId());
			user.setUserId(userEntiry.getUserId());
			user.setUsername(userEntiry.getSecurity().getLoginId());
			if(userEntiry.getDisplayPicture()!=null)
			user.setdPicture(userEntiry.getDisplayPicture().getImageUrl());


			RoleEntity roleEntity = userEntiry.getRole();

			if(roleEntity != null)
			{
				user.setRoleId(roleEntity.getRoleId());
				user.setRoleName(roleEntity.getRoleName());
			}
			if(userEntiry.getStatus() != null && "Active".equalsIgnoreCase(userEntiry.getStatus()))
			{
					user.setStatus("Verified");
			}
			else
			{
					OTPEntity otpentity =otpdao.findByVerificationId(userEntiry.getMobileNo());
					if("VERIFIED".equalsIgnoreCase(otpentity.getStatus())) 
						user.setStatus("Verified");
					else
						user.setStatus("Not Verified");

			}
		}
		return user;
	}

	public User findUserByUserSecurityId(Integer id){
		User user = null;
		UserEntity userEntity = userDAO.findBySecurityId(id);
		if(userEntity != null)
		{
			user = new User();
			user.setAlias(userEntity.getAlias());
			user.setAlternateEmailId(userEntity.getAlternateEmailId());
			user.setEmailId(userEntity.getEmailId());
			user.setFirstName(Util.getTitle(userEntity.getTitle())+userEntity.getFirstName());
//			user.setMiddleName(userEntity.getMiddleName());
			user.setLastName(userEntity.getLastName());
			user.setMobileNo(userEntity.getMobileNo());
			user.setTitle(userEntity.getTitle());
			user.setAlternateEmailId(userEntity.getAlternateEmailId());
			user.setPhoneNo(userEntity.getPhoneNo());
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
//			if(userEntity.getMiddleName()!=null)
//			{
//				userName += " "+userEntity.getMiddleName();
//			}
			userName += " "+userEntity.getLastName();
		}
		return userName;
	}

	public void updateRegistrationToken(String regToken, String username)
	{
		userDAO.updateRegistrationToken(regToken, username);
	}

}
