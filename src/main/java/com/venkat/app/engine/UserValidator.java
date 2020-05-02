package com.venkat.app.engine;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.venkat.app.dao.UserDAO;

import com.venkat.app.entity.UserEntity;
import com.venkat.app.entity.UserSecurityEntity;

@Transactional
@Component
@EnableScheduling
public class UserValidator {
	
	@Autowired
	private UserDAO userDAO;
	
	/*@Autowired
	private OTPDAO otpDAO;*/
	
	private static final Log log = LogFactory.getLog(UserValidator.class);
	
/*	@Scheduled(cron="0/10 * * * * ?")
	public void validateUser()
	{
		log.debug("Starting  user validation :" + new Date());
		List<UserEntity> userEntities = userDAO.findByStatus("Inactive");

		for(UserEntity userEntity : userEntities)
		{
		    UserSecurityEntity securityEntity =	userEntity.getSecurity();
		    log.debug("validating  OTP for user :" + userEntity.getEmailId() + " and security Id " + userEntity.getSecurity().getUserSecId());
		    boolean flag = false;
		    if(securityEntity != null)
		    {
		    	Integer securityId = securityEntity.getUserSecId();
		    	
		    	List<OTPEntity> otps = otpDAO.findBySecurityId(securityId);
		    	for(OTPEntity otp : otps)
		    	{
		    		if("VERIFIED".equalsIgnoreCase(otp.getStatus()))
		    		{
		    			flag = true;
		    		}
		    	}
		    }
		    if(flag)
		    {
		    	userEntity.setStatus("Active");
		    	if(userEntity.getSecurity() != null)
		    	{
		    		userEntity.getSecurity().setStatus("Verified");
		    	}
		    	userDAO.merge(userEntity);
		    }
			
		}
	}*/

}
