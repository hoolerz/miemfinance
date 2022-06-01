package com.hse.miemfinance.model.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsIntegrationDTO extends BaseIntegrationDTO {

	@JsonProperty("header")
	private String header;

	@JsonProperty("article")
	private String article;

	@JsonProperty("link")
	private String link;

	@JsonProperty("sentiment")
	private double sentiment;

	@JsonProperty("tags")
	private String tags;

	@JsonProperty("tickers")
	private String tickers;

}
