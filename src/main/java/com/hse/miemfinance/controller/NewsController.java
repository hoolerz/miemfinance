package com.hse.miemfinance.controller;

import com.hse.miemfinance.service.FileService;
import com.hse.miemfinance.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsController {

	private final NewsService newsService;

	private final FileService fileService;

}
