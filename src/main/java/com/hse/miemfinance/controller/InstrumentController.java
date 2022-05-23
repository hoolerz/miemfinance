package com.hse.miemfinance.controller;

import com.hse.miemfinance.model.dto.filters.FilterListDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentIndexDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentInfoDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentListDTO;
import com.hse.miemfinance.service.InstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/instrument")
public class InstrumentController {

	private final InstrumentService instrumentService;

	@GetMapping(value = "/{instrumentId}/info")
	public ResponseEntity<InstrumentInfoDTO> getInfo(
			@PathVariable(value = "instrumentId") Long instrumentId) {
		return ResponseEntity.ok().body(instrumentService.getInstrumentInfo(instrumentId));
	}

	@GetMapping(value = "/{instrumentId}/index")
	public ResponseEntity<InstrumentIndexDTO> getIndex(
			@PathVariable(value = "instrumentId") Long instrumentId) {
		return ResponseEntity.ok().body(instrumentService.getInstrumentIndex(instrumentId));
	}

	@GetMapping(value = "/top")
	public ResponseEntity<InstrumentListDTO> getTopForToday(
			@RequestParam(value = "size", required = false) Integer size) {
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

	@GetMapping(value = "/filters")
	public ResponseEntity<FilterListDTO> getFilters(
			@RequestParam(required = false, value = "type") String type) {
		FilterListDTO dto = new FilterListDTO();
		dto.setFilters(instrumentService.getAllQuickFilters());
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/search/filtered")
	public ResponseEntity<InstrumentListDTO> getFromFilters(
			@RequestParam(value = "filter") String filter) {
		InstrumentListDTO dto = new InstrumentListDTO();
		dto.setInstruments(instrumentService.getByFilter(filter));
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/search/text")
	public ResponseEntity<InstrumentListDTO> getFromSearch(
			@RequestParam(value = "size", required = false) Integer size) {
		InstrumentListDTO dto = new InstrumentListDTO();
		dto.setInstruments(instrumentService.getAllInstruments());
		return ResponseEntity.ok().body(dto);
	}

}
