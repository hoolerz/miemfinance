package com.hse.miemfinance.controller;

import com.hse.miemfinance.service.FinancialInstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/instrument")
public class InstrumentController {

	private final FinancialInstrumentService instrumentService;

}
