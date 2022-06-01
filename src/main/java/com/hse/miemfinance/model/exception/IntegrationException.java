package com.hse.miemfinance.model.exception;

import lombok.Getter;

@Getter
public class IntegrationException extends RuntimeException {

	private final String ticker;

	private final String domain;

	public IntegrationException() {
		super();
		this.ticker = "";
		this.domain = "";
	}

	public IntegrationException(String ticker, String domain) {
		this.ticker = ticker;
		this.domain = domain;
	}

}
