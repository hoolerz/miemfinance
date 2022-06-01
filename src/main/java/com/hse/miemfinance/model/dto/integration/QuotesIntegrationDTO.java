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
public class QuotesIntegrationDTO extends BaseIntegrationDTO {

	@JsonProperty("open")
	private double open;

	@JsonProperty("high")
	private double high;

	@JsonProperty("low")
	private double low;

	@JsonProperty("close")
	private double close;

	@JsonProperty("vol")
	private float vol;

	@JsonProperty("average_open_hourly_price")
	private float averageOpenHourlyPrice;

}
