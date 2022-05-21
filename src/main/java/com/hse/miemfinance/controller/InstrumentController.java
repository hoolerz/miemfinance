package com.hse.miemfinance.controller;

import com.hse.miemfinance.model.dto.instrument.InstrumentListDTO;
import com.hse.miemfinance.service.InstrumentService;
import com.hse.miemfinance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/instrument")
public class InstrumentController {

	private final InstrumentService instrumentService;

	private final UserService userService;

	@GetMapping(value = "/top")
	public ResponseEntity<InstrumentListDTO> getTopForToday(
			@RequestParam(value = "size", required = false) Integer size
	) {
		InstrumentListDTO dto = new InstrumentListDTO();
		dto.setInstruments(instrumentService.getTopInstruments(size));
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<InstrumentListDTO> getAll() {
		InstrumentListDTO dto = new InstrumentListDTO();
		dto.setInstruments(instrumentService.getAllInstruments());
		return ResponseEntity.ok().body(dto);
	}

}
