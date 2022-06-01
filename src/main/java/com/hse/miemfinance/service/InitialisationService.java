package com.hse.miemfinance.service;

import com.hse.miemfinance.config.ApplicationProperties;
import com.hse.miemfinance.service.integration.InitializationIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitialisationService {

	private final SearchService searchService;

	private final InitializationIntegrationService integrationService;

	private final ApplicationProperties applicationProperties;

	@EventListener(ApplicationReadyEvent.class)
	public void initialise() {
		//todo: yamikhaylov 31.05.22 refactor
		if (applicationProperties.getInitializeApplication()) {
			integrationService.initializeData();
			searchService.index();
		}
	}

}
