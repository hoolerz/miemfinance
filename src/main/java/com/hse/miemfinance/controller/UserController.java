package com.hse.miemfinance.controller;

import com.hse.miemfinance.model.dto.MessageDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentInfoDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentListDTO;
import com.hse.miemfinance.model.dto.user.UserDTO;
import com.hse.miemfinance.model.entity.User;
import com.hse.miemfinance.service.InstrumentService;
import com.hse.miemfinance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/user")
public class UserController {

	private final UserService userService;

	private final InstrumentService instrumentService;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping(value = "/info")
	public ResponseEntity<UserDTO> getUserInfo(Authentication authentication) {
		Long userId = userService.getUserIdFromAuthentication(authentication);
		UserDTO userDTO = userService.getUserInfo(userId);
		return ResponseEntity.ok().body(userDTO);
	}

	@GetMapping(value = "/favorites")
	public ResponseEntity<InstrumentListDTO> getUserFavorites(Authentication authentication) {
		Long userId = userService.getUserIdFromAuthentication(authentication);
		User user = userService.getUser(userId);
		logger.info("In the getUserFavorites");
		return ResponseEntity.ok(instrumentService.getFavorites(user));
	}

	@PostMapping(value = "/favorites")
	public ResponseEntity<InstrumentInfoDTO> addUserFavorite(Authentication authentication,
			@RequestParam("instrumentId") Long instrumentId) {
		Long userId = userService.getUserIdFromAuthentication(authentication);
		User user = userService.getUser(userId);
		return ResponseEntity.ok(instrumentService.addFavorite(user,instrumentId));
	}

	@DeleteMapping(value = "/favorites")
	public ResponseEntity<MessageDTO> deleteUserFavorite(Authentication authentication,
			@RequestParam("instrumentId") Long instrumentId) {
		Long userId = userService.getUserIdFromAuthentication(authentication);
		User user = userService.getUser(userId);
		instrumentService.deleteFavorite(user, instrumentId);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/top")
	public ResponseEntity<InstrumentDTO> getTopForToday(Authentication authentication) {
		Long userId = userService.getUserIdFromAuthentication(authentication);
		User user = userService.getUser(userId);
		return ResponseEntity.ok().body(instrumentService.getTopFromUserFavorites(user));
	}

}
