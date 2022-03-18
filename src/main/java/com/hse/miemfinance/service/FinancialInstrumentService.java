package com.hse.miemfinance.service;

import com.hse.miemfinance.repository.FinancialInstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialInstrumentService {

	private final FinancialInstrumentRepository financialInstrumentRepository;

}
