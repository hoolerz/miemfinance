package com.hse.miemfinance.controller;

import com.hse.miemfinance.model.dto.news.NewsListDTO;
import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.service.FileService;
import com.hse.miemfinance.service.InstrumentService;
import com.hse.miemfinance.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/news")
public class NewsController {

	private final NewsService newsService;

	private final InstrumentService instrumentService;

	private final FileService fileService;

	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public ResponseEntity<NewsListDTO> getAll() {
		NewsListDTO dto = new NewsListDTO();
		dto.setNews(newsService.getAllNews());
		return ResponseEntity.ok().body(dto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/recent")
	public ResponseEntity<NewsListDTO> getRecent() {
		NewsListDTO dto = new NewsListDTO();
		dto.setNews(newsService.getAllRecentNews());
		return ResponseEntity.ok().body(dto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/instrument")
	public ResponseEntity<NewsListDTO> getForInstrument(@RequestParam("instrumentId") Long instrumentId) {
		NewsListDTO dto = new NewsListDTO();
		Instrument instrument = instrumentService.getInstrument(instrumentId);
		if (instrument != null ) {
			dto.setNews(newsService.getAllForInstrument(instrument));
			return ResponseEntity.ok().body(dto);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
	}

}
