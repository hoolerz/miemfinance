package com.hse.miemfinance.service;

import com.hse.miemfinance.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {

	private final NewsRepository newsRepository;

}
