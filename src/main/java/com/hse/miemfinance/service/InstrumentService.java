package com.hse.miemfinance.service;

import com.hse.miemfinance.model.dto.instrument.InstrumentDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentInfoDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentListDTO;
import com.hse.miemfinance.model.entity.Instrument;
import com.hse.miemfinance.model.entity.InstrumentIndex;
import com.hse.miemfinance.model.entity.User;
import com.hse.miemfinance.model.entity.UserSelectedInstrument;
import com.hse.miemfinance.repository.IndexRepository;
import com.hse.miemfinance.repository.InstrumentRepository;
import com.hse.miemfinance.repository.UserSelectedInstrumentRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstrumentService {

	private final InstrumentRepository instrumentRepository;

	private final IndexRepository indexRepository;

	private final UserSelectedInstrumentRepository userSelectedInstrumentRepository;

	public Instrument getInstrument(Long instrumentId) {
		return instrumentRepository.findById(instrumentId).orElse(null);
	}

	public InstrumentListDTO getFavorites(User user) {
		List<Instrument> instruments = userSelectedInstrumentRepository.findAllByUser(user)
				.stream()
				.map(UserSelectedInstrument::getFinancialInstrument)
				.collect(Collectors.toList());
		InstrumentListDTO dto = new InstrumentListDTO();
		dto.setInstruments(instruments.stream()
				.map(InstrumentInfoDTO::new)
				.collect(Collectors.toList()));
		return dto;
	}

	public InstrumentInfoDTO addFavorite(User user, Long instrumentId) {
		Instrument instrument = instrumentRepository.findById(instrumentId).orElse(null);
		UserSelectedInstrument selectedInstrument = new UserSelectedInstrument();
		selectedInstrument.setUser(user);
		selectedInstrument.setFinancialInstrument(instrument);
		return new InstrumentInfoDTO(userSelectedInstrumentRepository.save(selectedInstrument).getFinancialInstrument());
	}

	public void deleteFavorite(User user, Long instrumentId) {
		Instrument instrument = instrumentRepository.findById(instrumentId).orElse(null);
		Optional<UserSelectedInstrument> selectedInstrument =
				userSelectedInstrumentRepository.findByFinancialInstrumentAndUser(instrument, user);
		selectedInstrument.ifPresent(userSelectedInstrumentRepository::delete);
	}

	public List<InstrumentDTO> getTopInstruments(Integer topSize) {
		int DEFAULT_SIZE = 5;
		topSize = topSize == null ? DEFAULT_SIZE : topSize;
		List<InstrumentIndex> instrumentIndices = indexRepository.getTopForToday();
		return instrumentIndices.stream()
				.limit(topSize)
				.map(index -> new InstrumentDTO(index.getFinancialInstrument()))
				.collect(Collectors.toList());
	}

	public InstrumentDTO getTopFromUserFavorites(User user) {
		List<InstrumentIndex> instrumentIndices = indexRepository.getTopForUser(user);
		return instrumentIndices.stream()
				.findFirst()
				.map(index -> new InstrumentDTO(index.getFinancialInstrument()))
				.orElse(new InstrumentDTO());
	}

	public List<InstrumentDTO> getAllInstruments() {
		return instrumentRepository.findAll().stream()
				.map(InstrumentDTO::new)
				.collect(Collectors.toList());
	}

}
