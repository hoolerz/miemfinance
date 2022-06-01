package com.hse.miemfinance.service.integration;

import com.hse.miemfinance.model.entity.News;
import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentIndex;
import com.hse.miemfinance.model.entity.instrument.InstrumentNews;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrediction;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrice;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class UpdateIntegrationService extends AbstractIntegrationService {

	@Scheduled(cron = "0 30 5 ? * *")
	public void updateData() {
		log.info("Started daily update");
		List<Instrument> instruments = instrumentRepository.findAll();
		instruments.forEach(this::updateDataFor);
		log.info("Finished daily update");
	}

	private void updateDataFor(Instrument instrument) {
		log.info("Updating data for: " + instrument.getTicker());
		updatePriceFor(instrument);
		updateIndexFor(instrument);
		updatePredictionsFor(instrument);
		updateNewsFor(instrument);
	}

	private void updatePriceFor(Instrument instrument) {
		List<InstrumentPrice> prices = fetchPricesForInstrument(instrument, getApiPath());
		prices.forEach(instrument::addPrice);
		priceRepository.saveAllAndFlush(prices);
	}

	private void updateIndexFor(Instrument instrument) {
		List<InstrumentIndex> indices = fetchIndexForInstrument(instrument, getApiPath());
		indices.forEach(instrument::addIndex);
		indexRepository.saveAll(indices);
	}

	private void updatePredictionsFor(Instrument instrument) {
		List<InstrumentPrediction> predictions = fetchPredictionsForInstrument(instrument, getApiPath());
		predictions.forEach(instrument::addPrediction);
		predictionRepository.saveAll(predictions);
	}

	private void updateNewsFor(Instrument instrument) {
		List<News> news = fetchNewsForInstrument(instrument, getApiPath());
		List<InstrumentNews> instrumentNews = news.stream()
				.map(entity -> new InstrumentNews(entity, instrument))
				.collect(Collectors.toList());
		instrumentNews.forEach(instrument::addNews);

		newsRepository.saveAll(news);
		instrumentNewsRepository.saveAll(instrumentNews);
	}

	@Override
	protected String getApiPath() {
		return applicationProperties.getParserHost() + "/api/v1/latest/";
	}

}
