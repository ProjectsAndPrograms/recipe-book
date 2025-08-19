package com.oranbyte.recipebook.service.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.oranbyte.recipebook.entity.User;
import com.oranbyte.recipebook.security.CustomUserDetails;
import com.oranbyte.recipebook.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
		
	@Override
	public void login(User user) {
		UserDetails updatedUserDetails = new CustomUserDetails(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
		        updatedUserDetails, updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
//		    UserDetails updatedUserDetails = new CustomUserDetails(user);
//			Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUserDetails,
//					updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities());
//
//			SecurityContext context = SecurityContextHolder.createEmptyContext();
//			context.setAuthentication(authentication);
//			SecurityContextHolder.setContext(context);
//
//			// ✅ Important: Save to HTTP session if using session-based security
//			HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
//					.getSession(true);
//			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
	}

}