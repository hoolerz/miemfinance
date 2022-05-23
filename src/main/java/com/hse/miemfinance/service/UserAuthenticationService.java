package com.hse.miemfinance.service;

import com.hse.miemfinance.model.entity.user.User;
import com.hse.miemfinance.security.MiemUserDetails;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService implements UserDetailsService {

	private final UserService userService;

	public UserDetails loadUserByUsername(String userName) {
		User user = this.userService.getUser(userName);
		return createUserDetails(user);
	}

	public void authenticateAndSave(UserDetails userDetails, Object details) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		usernamePasswordAuthenticationToken.setDetails(details);
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}

	private MiemUserDetails createUserDetails(final User user) {
		MiemUserDetails userDetails = new MiemUserDetails();
		userDetails.setUsername(user.getUsername());
		userDetails.setEmail(user.getEmail());
		userDetails.setId(user.getId());
		userDetails.setPassword("");
		userDetails.setAuthorities(Collections.emptySet());
		return userDetails;
	}

}
