package com.medrep.app.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.medrep.app.service.UserService;
import com.medrep.app.util.PasswordProtector;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	UserService userService;

	public Authentication authenticate(org.springframework.security.core.Authentication authentication) throws org.springframework.security.core.AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		password = PasswordProtector.encrypt(password);
		String regToken=null;
		if(RequestContextHolder.getRequestAttributes()!=null){
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			regToken=request.getParameter("regToken");
		}
		UserDetails user = userService.loadUserByUsername(username);

		if (user == null) 
		{
			throw new UsernameNotFoundException("Username not found.");
		}
		if (!password.equals(user.getPassword())) 
		{
			throw new UsernameNotFoundException("Wrong password.");
		}
		if(regToken!=null && !"".equalsIgnoreCase(regToken)){
			userService.updateRegistrationToken(regToken, username);
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}


}
