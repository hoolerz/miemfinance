package com.hse.miemfinance.service;

import com.hse.miemfinance.model.dto.user.UserDTO;
import com.hse.miemfinance.model.entity.User;
import com.hse.miemfinance.repository.UserRepository;
import com.hse.miemfinance.security.MiemUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User register(User user) {
		return userRepository.save(user);
	}

	public User register(String username, String email, String preferredName) throws Exception {
		if (!isPresent(username)) {
			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPreferredName(preferredName);
			return userRepository.save(user);
		} else throw new Exception("USER ALREADY EXISTS");
	}

	public boolean isPresent(String username) {
		return userRepository.findByUsername(username).isPresent();
	}

	public User getUser(String username) {
			return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}

	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(String.valueOf(userId)));
	}

	public Long getUserIdFromAuthentication(Authentication authentication) {
		MiemUserDetails miemUserDetails = (MiemUserDetails) authentication.getPrincipal();
		return miemUserDetails.getId();
	}

	public UserDTO getUserInfo(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		UserDTO userDTO = null;
		if (user != null) {
			userDTO = new UserDTO(user);
		}
		return userDTO;
	}

}
