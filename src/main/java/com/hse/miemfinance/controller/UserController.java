package com.hse.miemfinance.controller;

import com.hse.miemfinance.model.dto.FinancialInstrumentDTO;
import com.hse.miemfinance.model.dto.MessageDTO;
import com.hse.miemfinance.model.dto.UserDTO;
import com.hse.miemfinance.model.entity.User;
import com.hse.miemfinance.service.FinancialInstrumentService;
import com.hse.miemfinance.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "/user")
public class UserController {

	private final UserService userService;

	private final FinancialInstrumentService instrumentService;

	@GetMapping(value = "/info")
	public ResponseEntity<UserDTO> getUserInfo(Authentication authentication) {
		Long userId = userService.getUserIdFromAuthentication(authentication);
		UserDTO userDTO = userService.getUserInfo(userId);
		return ResponseEntity.ok().body(userDTO);
	}

	@GetMapping(value = "/favorites")
	public ResponseEntity<List<FinancialInstrumentDTO>> getUserFavorites(Authentication authentication) {
		Long userId = userService.getUserIdFromAuthentication(authentication);
		User user = userService.getUser(userId);
		return ResponseEntity.ok(instrumentService.getFavorites(user));
	}

	@PostMapping(value = "/favorites")
	public ResponseEntity<FinancialInstrumentDTO> addUserFavorite(Authentication authentication,
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
}
