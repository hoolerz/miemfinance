package com.hse.miemfinance.service.integration;

import static com.hse.miemfinance.model.enums.Dictionaries.Countries.RUSSIA;
import static com.hse.miemfinance.model.enums.Dictionaries.Countries.USA;
import static com.hse.miemfinance.model.enums.Dictionaries.Exchanges.MOEX;
import static com.hse.miemfinance.model.enums.Dictionaries.Tags.FOREIGN;
import static com.hse.miemfinance.model.enums.Dictionaries.Tags.RUSSIAN;

import com.hse.miemfinance.model.dto.integration.InstrumentIntegrationDTO;
import com.hse.miemfinance.model.entity.FileEntity;
import com.hse.miemfinance.model.entity.News;
import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentAttachment;
import com.hse.miemfinance.model.entity.instrument.InstrumentIndex;
import com.hse.miemfinance.model.entity.instrument.InstrumentNews;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrediction;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrice;
import com.hse.miemfinance.model.entity.instrument.InstrumentTag;
import com.hse.miemfinance.model.exception.IntegrationException;
import com.hse.miemfinance.repository.InstrumentAttachmentRepository;
import com.hse.miemfinance.repository.TagRepository;
import com.hse.miemfinance.service.FileService;
import com.hse.miemfinance.util.JsonRestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitializationIntegrationService extends AbstractIntegrationService {

	private final FileService fileService;

	private final InstrumentAttachmentRepository instrumentAttachmentRepository;

	private final TagRepository tagRepository;

	public void initializeData() {
		List<String> exchanges = getExchanges();
		exchanges.forEach(this::getCompanyData);
	}

	public List<String> getExchanges() {
		String REQUEST_DOMAIN = "/exchanges";
		String url = getApiPath() + REQUEST_DOMAIN;
		ResponseEntity<String[]> response;

		try {
			JsonRestTemplate restTemplate = getRestTemplate();
			response =  restTemplate.getForEntity(url , String[].class);
		} catch (Exception e) {
			throw new IntegrationException("EXCHANGES",REQUEST_DOMAIN);
		}

		return Arrays.asList(Optional.ofNullable(response.getBody()).orElseThrow(NullPointerException::new));
	}

	public void getCompanyData(String exchange) {
		log.info("Started initialization for companies from: " + exchange);
		String REQUEST_DOMAIN = "exchange/";
		String url = getApiPath() + REQUEST_DOMAIN + exchange + "/_";
		ResponseEntity<InstrumentIntegrationDTO[]> response;

		try {
			JsonRestTemplate restTemplate = getRestTemplate();
			response =  restTemplate.getForEntity(url , InstrumentIntegrationDTO[].class);
		} catch (Exception e) {
			throw new IntegrationException(exchange,REQUEST_DOMAIN);
		}

		List<InstrumentIntegrationDTO> dtos =  Arrays.stream(
						Optional.ofNullable(response.getBody()).orElseThrow(NullPointerException::new))
				.filter(dto -> dto.getFindStatus() == 1)
				.collect(Collectors.toList());

		List<Instrument> instruments  = dtos.stream()
				.filter(dto -> !instrumentRepository.existsByTicker(dto.getTicker()))
				.map(Instrument::new)
				.collect(Collectors.toList());
		instruments.forEach(instrument -> instrument.setCountry(MOEX.equals(exchange) ? RUSSIA : USA));

		Map<String, String> images = dtos.stream()
						.collect(Collectors.toMap(
								InstrumentIntegrationDTO::getTicker,
								InstrumentIntegrationDTO::getLogo
						));

		instrumentRepository.saveAllAndFlush(instruments);;
		instruments.forEach(this::addDataForInstrument);
		log.info("Parsing logos for: " + exchange);
		addLogoToInstruments(instruments, images);

		log.info("Finished initialization for companies from: " + exchange);
	}

	private void addDataForInstrument(Instrument instrument) {
		addPricesForInstrument(instrument);
		addIndexForInstrument(instrument);
		addNewsForInstrument(instrument);
		addPredictionsForInstrument(instrument);
		addTagsForInstrument(instrument);
	}

	private void addPricesForInstrument(Instrument instrument) {
		String api_path = applicationProperties.getParserHost() + "/api/v1/company/";
		List<InstrumentPrice> prices = fetchPricesForInstrument(instrument, api_path);
		prices.forEach(instrument::addPrice);
		priceRepository.saveAllAndFlush(prices);
	}

	private void addIndexForInstrument(Instrument instrument) {
		String api_path = applicationProperties.getParserHost() + "/api/v1/company/";
		List<InstrumentIndex> indices = fetchIndexForInstrument(instrument, api_path);
		indices.forEach(instrument::addIndex);
		indexRepository.saveAll(indices);
	}

	private void addNewsForInstrument(Instrument instrument) {
		String api_path = applicationProperties.getParserHost() + "/api/v1/company/";
		List<News> news = fetchNewsForInstrument(instrument, api_path);
		List<InstrumentNews> instrumentNews = news.stream()
				.map(entity -> new InstrumentNews(entity, instrument))
				.collect(Collectors.toList());
		instrumentNews.forEach(instrument::addNews);

		newsRepository.saveAll(news);
		instrumentNewsRepository.saveAll(instrumentNews);
	}

	private void addPredictionsForInstrument(Instrument instrument) {
		String api_path = applicationProperties.getParserHost() + "/api/v1/company/";
		List<InstrumentPrediction> predictions = fetchPredictionsForInstrument(instrument, api_path);
		predictions.forEach(instrument::addPrediction);
		predictionRepository.saveAll(predictions);
	}

	private void addLogoToInstruments(List<Instrument> instruments, Map<String, String> images) {
		instruments
				.stream()
				.filter(instrument -> !fileService.exists(instrument.getTicker()))
				.forEach(
						instrument -> {
							String base64 = images.get(instrument.getTicker());
							String filename = instrument.getTicker() + ".png";
							String contentType = "image/png";
							byte[] content = DatatypeConverter.parseBase64Binary(base64);

							FileEntity fileEntity = fileService.uploadFile(filename, contentType, content);

							InstrumentAttachment attachment = new InstrumentAttachment();
							attachment.setEntity(fileEntity);
							attachment.setFinancialInstrument(instrument);
							instrumentAttachmentRepository.saveAndFlush(attachment);

							instrument.setAttachment(attachment);
						}
				);
	}

	private void addTagsForInstrument(Instrument instrument) {
		InstrumentTag country_tag = new InstrumentTag();
		country_tag.setTicker(instrument.getTicker());
		if (RUSSIA.equals(instrument.getCountry())) {
			country_tag.setTagValue(RUSSIAN);
		} else {
			country_tag.setTagValue(FOREIGN);
		}
		instrument.addTag(country_tag);
		tagRepository.save(country_tag);

		List<InstrumentTag> preloadedTags = tagRepository.findAllByTicker(instrument.getTicker());
		preloadedTags.stream()
				.filter(tag -> tag.getFinancialInstrument() == null)
				.forEach(instrument::addTag);
	}

	@Override
	protected String getApiPath() {
		return applicationProperties.getParserHost() + "/api/v1/info/";
	}

}
