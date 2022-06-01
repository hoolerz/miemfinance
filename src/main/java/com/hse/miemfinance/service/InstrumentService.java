package com.hse.miemfinance.service;

import static com.hse.miemfinance.model.enums.Dictionaries.Currencies.RUB;
import static com.hse.miemfinance.model.enums.Dictionaries.Currencies.USD;
import static com.hse.miemfinance.model.enums.Dictionaries.ExceptionMessages.NOT_FOUND;
import static com.hse.miemfinance.model.enums.Dictionaries.Tags.FOREIGN;

import com.hse.miemfinance.model.dto.IndexDTO;
import com.hse.miemfinance.model.dto.PredictionDTO;
import com.hse.miemfinance.model.dto.filters.FilterDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentIndexDTO;
import com.hse.miemfinance.model.dto.instrument.InstrumentInfoDTO;
import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentIndex;
import com.hse.miemfinance.model.entity.user.User;
import com.hse.miemfinance.model.entity.user.UserSelectedInstrument;
import com.hse.miemfinance.model.exception.BusinessException;
import com.hse.miemfinance.repository.DictionaryItemRepository;
import com.hse.miemfinance.repository.IndexRepository;
import com.hse.miemfinance.repository.InstrumentRepository;
import com.hse.miemfinance.repository.PredictionRepository;
import com.hse.miemfinance.repository.TagRepository;
import com.hse.miemfinance.repository.UserSelectedInstrumentRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstrumentService {

	private final InstrumentRepository instrumentRepository;

	private final IndexRepository indexRepository;

	private final UserSelectedInstrumentRepository userSelectedInstrumentRepository;

	private final DictionaryItemRepository dictionaryItemRepository;

	private final TagRepository tagRepository;

	private final PredictionRepository predictionRepository;

	private final SearchService searchService;

	private final UserService userService;

	public Instrument getInstrument(Long instrumentId) {
		return instrumentRepository.findById(instrumentId).orElse(null);
	}

	public InstrumentInfoDTO getInstrumentInfo(Long instrumentId) {
		Instrument instrument = instrumentRepository.findById(instrumentId)
				.orElseThrow(() -> new BusinessException().withMessage(NOT_FOUND));
		InstrumentInfoDTO dto = new InstrumentInfoDTO(instrument);
		dto.setCurrency(getInstrumentCurrency(instrument));
		dto.setIsFavorite(checkIsFavorite(instrument));
		dto.setPredictions(getInstrumentPredictions(instrument));
		return dto;
	}

	public InstrumentIndexDTO getInstrumentIndex(Long instrumentId) {
		Instrument instrument = instrumentRepository.findById(instrumentId)
				.orElseThrow(() -> new BusinessException().withMessage(NOT_FOUND));
		InstrumentIndexDTO dto = new InstrumentIndexDTO(instrument);
		dto.setIndices(
				indexRepository.getAllForInstrument(instrument).stream()
						.map(IndexDTO::new)
						.collect(Collectors.toList())
		);
		return dto;
	}

	public List<InstrumentDTO> getFavorites() {
		User user = userService.getCurrentUser();
		List<Instrument> instruments = userSelectedInstrumentRepository.findAllByUser(user)
				.stream()
				.map(UserSelectedInstrument::getFinancialInstrument)
				.collect(Collectors.toList());
		return instruments.stream()
				.map(this::dtoFromInstrumentWithCurrency)
				.collect(Collectors.toList());
	}

	public InstrumentInfoDTO addFavorite(Long instrumentId) {
		User user = userService.getCurrentUser();
		Instrument instrument = instrumentRepository.findById(instrumentId).orElse(null);
		InstrumentInfoDTO dto = new InstrumentInfoDTO(instrument);
		if (!userSelectedInstrumentRepository.existsByFinancialInstrumentAndAndUser(instrument, user)) {
			UserSelectedInstrument selectedInstrument = new UserSelectedInstrument();
			selectedInstrument.setUser(user);
			selectedInstrument.setFinancialInstrument(instrument);
			userSelectedInstrumentRepository.save(selectedInstrument);
		}
		dto.setCurrency(getInstrumentCurrency(instrument));
		dto.setIsFavorite(checkIsFavorite(instrument));
		return dto;
	}

	public void deleteFavorite(Long instrumentId) {
		User user = userService.getCurrentUser();
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
				.map(index -> dtoFromInstrumentWithCurrency(index.getFinancialInstrument()))
				.collect(Collectors.toList());
	}

	public List<InstrumentDTO> getTopFromUserFavorites() {
		User user = userService.getCurrentUser();
		List<InstrumentIndex> instrumentIndices = indexRepository.getTopForUser(user);
		return instrumentIndices.stream()
				.map(index -> dtoFromInstrumentWithCurrency(index.getFinancialInstrument()))
				.limit(1)
				.collect(Collectors.toList());
	}

	public List<InstrumentDTO> getAllInstruments() {
		return instrumentRepository.findAll().stream()
				.limit(150) // todo: yamikhaylov 1.06.2022 refactor
				.map(this::dtoFromInstrumentWithCurrency)
				.collect(Collectors.toList());
	}

	public List<FilterDTO> getAllQuickFilters() {
		return dictionaryItemRepository.getAllQuickFilters().stream()
				.map(FilterDTO::new)
				.collect(Collectors.toList());
	}

	public List<InstrumentDTO> getByFilter(String filter) {
		List<Instrument> instruments = searchService.searchByFilter(filter);
		return instruments.stream()
				.map(this::dtoFromInstrumentWithCurrency)
				.collect(Collectors.toList());
	}

	public List<InstrumentDTO> getBySearch(String searchTerm) {
		List<Instrument> instruments = searchService.searchByTerms(searchTerm);
		return instruments.stream()
				.map(this::dtoFromInstrumentWithCurrency)
				.collect(Collectors.toList());
	}

	private InstrumentDTO dtoFromInstrumentWithCurrency(Instrument entity) {
		InstrumentDTO dto = new InstrumentDTO(entity);
		dto.setCurrency(getInstrumentCurrency(entity));
		return dto;
	}

	private String getInstrumentCurrency(Instrument instrument) {
		return tagRepository.existsByFinancialInstrumentAndTagValue(instrument, FOREIGN)
				? USD
				: RUB;
	}

	private boolean checkIsFavorite(Instrument instrument) {
		return userSelectedInstrumentRepository
				.existsByFinancialInstrumentAndAndUser(instrument, userService.getCurrentUser());
	}

	private Set<PredictionDTO> getInstrumentPredictions(Instrument instrument) {
		return predictionRepository.findLatestForInstrument(instrument).stream()
				.map(PredictionDTO::new)
				.collect(Collectors.toSet());
	}

}
