package com.hse.miemfinance.controller;

import com.hse.miemfinance.model.dto.FinancialInstrumentDTO;
import com.hse.miemfinance.service.FinancialInstrumentService;
import com.hse.miemfinance.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/instrument")
public class InstrumentController {

	private final FinancialInstrumentService instrumentService;

	private final UserService userService;

	@GetMapping(value = "/top")
	public ResponseEntity<List<FinancialInstrumentDTO>> getTopForToday() {
		return ResponseEntity.ok().body(instrumentService.getTopInstruments());
	}

}
