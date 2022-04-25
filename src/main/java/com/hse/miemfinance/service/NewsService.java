package com.hse.miemfinance.service;

import com.hse.miemfinance.model.dto.NewsDTO;
import com.hse.miemfinance.model.entity.News;
import com.hse.miemfinance.repository.NewsRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
		List<News> news = newsRepository.findAllByPublishedDateAfter(LocalDateTime.now().minus(3, ChronoUnit.DAYS));
		return news.stream()
				.map(NewsDTO::new)
				.collect(Collectors.toList());
	}

}
