package com.hse.miemfinance.service;

import com.hse.miemfinance.model.dto.FinancialInstrumentDTO;
import com.hse.miemfinance.model.entity.FinancialInstrument;
import com.hse.miemfinance.model.entity.User;
import com.hse.miemfinance.model.entity.UserSelectedInstrument;
import com.hse.miemfinance.repository.FinancialInstrumentRepository;
import com.hse.miemfinance.repository.UserSelectedInstrumentRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialInstrumentService {

	private final FinancialInstrumentRepository instrumentRepository;

	private final UserSelectedInstrumentRepository userSelectedInstrumentRepository;

	public List<FinancialInstrumentDTO> getFavorites(User user) {
		List<FinancialInstrument> instruments = userSelectedInstrumentRepository.findAllByUser(user)
				.stream()
				.map(UserSelectedInstrument::getFinancialInstrument)
				.collect(Collectors.toList());
		return instruments.stream()
				.map(FinancialInstrumentDTO::new)
				.collect(Collectors.toList());
	}

	public FinancialInstrumentDTO addFavorite(User user, Long instrumentId) {
		FinancialInstrument instrument = instrumentRepository.findById(instrumentId).orElse(null);
		UserSelectedInstrument selectedInstrument = new UserSelectedInstrument();
		selectedInstrument.setUser(user);
		selectedInstrument.setFinancialInstrument(instrument);
		return new FinancialInstrumentDTO(userSelectedInstrumentRepository.save(selectedInstrument).getFinancialInstrument());
	}

	public void deleteFavorite(User user, Long instrumentId) {
		FinancialInstrument instrument = instrumentRepository.findById(instrumentId).orElse(null);
		Optional<UserSelectedInstrument> selectedInstrument =
				userSelectedInstrumentRepository.findByFinancialInstrumentAndAndUser(instrument, user);
		selectedInstrument.ifPresent(userSelectedInstrumentRepository::delete);
	}

}
