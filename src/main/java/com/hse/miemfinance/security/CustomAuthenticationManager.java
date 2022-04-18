package com.hse.miemfinance.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

	private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationManager.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String pw       = authentication.getCredentials().toString();
		logger.info("was here" + username.toString() + " , " + pw.toString());

		return new UsernamePasswordAuthenticationToken(username, pw, authentication.getAuthorities());

	}
}
