package com.hse.miemfinance.service;

import com.hse.miemfinance.model.dto.news.NewsDTO;
import com.hse.miemfinance.model.entity.Instrument;
import com.hse.miemfinance.model.entity.InstrumentNews;
import com.hse.miemfinance.model.entity.News;
import com.hse.miemfinance.repository.NewsRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {

	private final NewsRepository newsRepository;

	public List<NewsDTO> getAllNews() {
		List<News> news = newsRepository.findAllByOrderByPublishedDateDesc();
		return news.stream()
				.map(NewsDTO::new)
				.collect(Collectors.toList());
	}

	public List<NewsDTO> getAllRecentNews() {
		List<News> news = newsRepository.findAllByPublishedDateAfter(LocalDateTime.now().minusDays(3));
		return news.stream()
				.map(NewsDTO::new)
				.collect(Collectors.toList());
	}

	public List<NewsDTO> getAllForInstrument(Instrument instrument) {
		return instrument.getNews().stream()
				.map(InstrumentNews::getNews)
				.map(NewsDTO::new)
				.collect(Collectors.toList());
	}

}
