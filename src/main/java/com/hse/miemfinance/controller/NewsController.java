package com.hse.miemfinance.controller;

import com.hse.miemfinance.model.dto.NewsDTO;
import com.hse.miemfinance.service.FileService;
import com.hse.miemfinance.service.NewsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/news")
public class NewsController {

	private final NewsService newsService;

	private final FileService fileService;

	@CrossOrigin(origins = "*") //fixme
	@RequestMapping(method = RequestMethod.GET, value = "/health")
	public ResponseEntity health(Authentication authentication) {
		return ResponseEntity.ok().body("okkkk");
	}

	@CrossOrigin(origins = "*") //fixme
	@RequestMapping(method = RequestMethod.GET, value = "/heel")
	public ResponseEntity health2(Authentication authentication) {
		return ResponseEntity.ok().body(authentication.getName());
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.GET, value = "/recent")
	public ResponseEntity<List<NewsDTO>> getRecentNews(Authentication authentication) {
		return ResponseEntity.ok().body(newsService.getAllRecentNews());
	}

}
