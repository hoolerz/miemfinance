package com.hse.miemfinance.service;

import static com.hse.miemfinance.model.enums.Dictionaries.ExceptionMessages.ALREADY_EXISTS;

import com.hse.miemfinance.model.dto.user.UserDTO;
import com.hse.miemfinance.model.entity.user.User;
import com.hse.miemfinance.model.exception.BusinessException;
import com.hse.miemfinance.repository.UserRepository;
import com.hse.miemfinance.security.MiemUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User register(User user) {
		return userRepository.save(user);
	}

	public User register(String username, String email, String preferredName) {
		if (!isPresent(username)) {
			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPreferredName(preferredName);
			return userRepository.save(user);
		} else throw new BusinessException().withMessage(ALREADY_EXISTS);
	}

	public boolean isPresent(String username) {
		return userRepository.findByUsername(username).isPresent();
	}

	public User getUser(String username) {
			return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}

	public User getCurrentUser() {
		MiemUserDetails miemUserDetails = (MiemUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findById(miemUserDetails.getId())
				.orElseThrow(() -> new UsernameNotFoundException(String.valueOf(miemUserDetails.getId())));
	}

	public UserDTO getUserInfo(User user) {
		return new UserDTO(user);
	}

}
