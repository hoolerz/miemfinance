package com.hse.miemfinance.service;

import com.hse.miemfinance.config.search.SearchProperties;
import com.hse.miemfinance.model.entity.DictionaryItem;
import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentTag;
import com.hse.miemfinance.model.exception.BusinessException;
import com.hse.miemfinance.repository.DictionaryItemRepository;
import com.hse.miemfinance.repository.InstrumentRepository;
import com.hse.miemfinance.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final TagRepository tagRepository;

	private final DictionaryItemRepository dictionaryItemRepository;

	private final InstrumentRepository instrumentRepository;

	private final SearchProperties searchProperties;

	@EventListener(ApplicationReadyEvent.class)
	public void index() {
		if (searchProperties.getInitialIndexingEnabled()) {
			System.out.println("inside indexing");
			instrumentRepository.index();
		}
	}

	public List<Instrument> searchByFilter(String filter) {
		Optional<DictionaryItem> tag = dictionaryItemRepository.getFilter(filter);
		if (tag.isPresent()) {
			return tagRepository.findAllByTagValue(tag.get().getValue()).stream()
					.map(InstrumentTag::getFinancialInstrument)
					.collect(Collectors.toList());
		} else {
			throw new BusinessException().withMessage("Filter is not applicable");
		}
	}

	public List<Instrument> searchByTerms(String terms) {
		return instrumentRepository.fullTextSearch(terms,0,20);
	}

}
