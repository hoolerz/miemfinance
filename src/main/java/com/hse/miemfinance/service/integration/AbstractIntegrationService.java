package com.hse.miemfinance.service.integration;

import static com.hse.miemfinance.model.enums.Dictionaries.ExceptionMessages.INTEGRATION_FAILED;
import static com.hse.miemfinance.model.enums.Dictionaries.Predictions.BARRIER;
import static com.hse.miemfinance.model.enums.Dictionaries.Predictions.BINARY;

import com.hse.miemfinance.config.ApplicationProperties;
import com.hse.miemfinance.model.dto.integration.IndexIntegrationDTO;
import com.hse.miemfinance.model.dto.integration.NewsIntegrationDTO;
import com.hse.miemfinance.model.dto.integration.PredictionIntegrationDTO;
import com.hse.miemfinance.model.dto.integration.QuotesIntegrationDTO;
import com.hse.miemfinance.model.entity.News;
import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentIndex;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrediction;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrice;
import com.hse.miemfinance.repository.IndexRepository;
import com.hse.miemfinance.repository.InstrumentNewsRepository;
import com.hse.miemfinance.repository.InstrumentRepository;
import com.hse.miemfinance.repository.NewsRepository;
import com.hse.miemfinance.repository.PredictionRepository;
import com.hse.miemfinance.repository.PriceRepository;
import com.hse.miemfinance.util.JsonRestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public abstract class AbstractIntegrationService {

	@Autowired
	protected ApplicationProperties applicationProperties;

	@Autowired
	protected InstrumentRepository instrumentRepository;

	@Autowired
	protected IndexRepository indexRepository;

	@Autowired
	protected PriceRepository priceRepository;

	@Autowired
	protected PredictionRepository predictionRepository;

	@Autowired
	protected NewsRepository newsRepository;

	@Autowired
	protected InstrumentNewsRepository instrumentNewsRepository;

	protected List<InstrumentPrice> fetchPricesForInstrument(Instrument instrument, String api_path) {
		String TICKER = instrument.getTicker();
		String REQUEST_DOMAIN = "/quotes";
		String url = api_path + TICKER + REQUEST_DOMAIN;
		ResponseEntity<QuotesIntegrationDTO[]> response;

		try {
			JsonRestTemplate restTemplate = getRestTemplate();
			response =  restTemplate.getForEntity(url , QuotesIntegrationDTO[].class);
			return Arrays.stream(
							Optional.ofNullable(response.getBody()).orElseThrow(NullPointerException::new))
					.map(InstrumentPrice::new)
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error(String.format(INTEGRATION_FAILED, REQUEST_DOMAIN, TICKER));
			log.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	protected List<InstrumentIndex> fetchIndexForInstrument(Instrument instrument, String api_path) {
		String TICKER = instrument.getTicker();
		String REQUEST_DOMAIN = "/index";
		String url = api_path + TICKER + REQUEST_DOMAIN;
		ResponseEntity<IndexIntegrationDTO[]> response;

		try {
		JsonRestTemplate restTemplate = getRestTemplate();
		response =  restTemplate.getForEntity(url , IndexIntegrationDTO[].class);
		return Arrays.stream(
				Optional.ofNullable(response.getBody()).orElseThrow(NullPointerException::new))
					.map(index -> new InstrumentIndex(index, instrument))
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error(String.format(INTEGRATION_FAILED, REQUEST_DOMAIN, TICKER));
			log.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	protected List<News> fetchNewsForInstrument(Instrument instrument, String api_path) {
		String TICKER = instrument.getTicker();
		String REQUEST_DOMAIN = "/smartlab";
		String url = api_path + TICKER + REQUEST_DOMAIN;
		ResponseEntity<NewsIntegrationDTO[]> response;

		try {
			JsonRestTemplate restTemplate = getRestTemplate();
			response = restTemplate.getForEntity(url, NewsIntegrationDTO[].class);
			List<NewsIntegrationDTO> dtos = Arrays.stream(
							Optional.ofNullable(response.getBody()).orElseThrow(NullPointerException::new))
					.collect(Collectors.toList());
			return  dtos.stream()
					.map(News::new)
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error(String.format(INTEGRATION_FAILED, REQUEST_DOMAIN, TICKER));
			log.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	protected List<InstrumentPrediction> fetchPredictionsForInstrument(Instrument instrument, String api_path) {
		String TICKER = instrument.getTicker();
		String REQUEST_DOMAIN = "/preds";
		String url = api_path + TICKER + REQUEST_DOMAIN;
		ResponseEntity<PredictionIntegrationDTO[]> response;

		try {
			JsonRestTemplate restTemplate = getRestTemplate();
			response =  restTemplate.getForEntity(url , PredictionIntegrationDTO[].class);
			List<PredictionDataContainer> containers = Arrays.stream(
							Optional.ofNullable(response.getBody()).orElseThrow(NullPointerException::new))
					.limit(31)
					.map(dto -> predictionFromDto(dto, instrument))
					.collect(Collectors.toList());

			List<InstrumentPrediction> binaryPredictions = containers.stream()
					.map(PredictionDataContainer::getBinaryPrediction)
					.collect(Collectors.toList());

			List<InstrumentPrediction> barrierPredictions = containers.stream()
					.map(PredictionDataContainer::getBarrierPrediction)
					.collect(Collectors.toList());

			return Stream.concat(binaryPredictions.stream(), barrierPredictions.stream()).collect(Collectors.toList());
		} catch (Exception e) {
			log.error(String.format(INTEGRATION_FAILED, REQUEST_DOMAIN, TICKER));
			log.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	private PredictionDataContainer predictionFromDto(PredictionIntegrationDTO dto, Instrument instrument) {
		InstrumentPrediction binaryPrediction = new InstrumentPrediction(dto, instrument);
		InstrumentPrediction barrierPrediction = new InstrumentPrediction(dto, instrument);

		binaryPrediction.setType(BINARY);
		barrierPrediction.setType(BARRIER);

		binaryPrediction.setPrediction(String.valueOf(dto.getBinPred() == 1 ? 2 : 0)); //so that for both types 0 - down, 2 - up
		binaryPrediction.setCertainty(dto.getBinProb() > 0.5d ? dto.getBinProb() : 1d - dto.getBinProb());

		barrierPrediction.setPrediction(String.valueOf(dto.getBarrPred()));
		barrierPrediction.setCertainty((double) Math.max(
				dto.getBarrProb0(),
				Math.max(dto.getBarrProb1(), dto.getBarrProb2())
		));

		return new PredictionDataContainer(binaryPrediction, barrierPrediction);
	}

	protected JsonRestTemplate getRestTemplate() {
		int timeout = 10000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
				= new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return new JsonRestTemplate(clientHttpRequestFactory);
	}

	protected abstract String getApiPath();

	@Getter
	@Setter
	@AllArgsConstructor
	private class PredictionDataContainer {

		private InstrumentPrediction binaryPrediction;

		private InstrumentPrediction barrierPrediction;
	}

}
