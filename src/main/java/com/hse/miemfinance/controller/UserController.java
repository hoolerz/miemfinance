package com.hse.miemfinance.controller;

import com.hse.miemfinance.model.dto.MessageDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentInfoDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentListDTO;
import com.hse.miemfinance.model.dto.user.UserDTO;
import com.hse.miemfinance.service.InstrumentService;
import com.hse.miemfinance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<UserDTO> getUserInfo() {
		UserDTO userDTO = userService.getUserInfo(userService.getCurrentUser());
		return ResponseEntity.ok().body(userDTO);
	}

	@GetMapping(value = "/favorites")
	public ResponseEntity<InstrumentListDTO> getUserFavorites() {
		InstrumentListDTO dto = new InstrumentListDTO();
		dto.setInstruments(instrumentService.getFavorites());
		return ResponseEntity.ok(dto);
	}

	@PostMapping(value = "/favorites")
	public ResponseEntity<InstrumentInfoDTO> addUserFavorite(@RequestParam("instrumentId") Long instrumentId) {
		return ResponseEntity.ok(instrumentService.addFavorite(instrumentId));
	}

	@DeleteMapping(value = "/favorites")
	public ResponseEntity<MessageDTO> deleteUserFavorite(@RequestParam("instrumentId") Long instrumentId) {
		instrumentService.deleteFavorite(instrumentId);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/top")
	public ResponseEntity<InstrumentListDTO> getTopForToday() {
		InstrumentListDTO dto = new InstrumentListDTO();
		dto.setInstruments(instrumentService.getTopFromUserFavorites());
		return ResponseEntity.ok().body(dto);
	}

}
