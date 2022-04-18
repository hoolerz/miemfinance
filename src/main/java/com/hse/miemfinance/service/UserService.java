package com.hse.miemfinance.service;

import com.hse.miemfinance.model.entity.User;
import com.hse.miemfinance.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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

	public Optional<User> findByUsername(String username) {
			return userRepository.findByUsername(username);
	}


}
