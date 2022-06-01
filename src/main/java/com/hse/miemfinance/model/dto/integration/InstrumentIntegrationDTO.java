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
public class InstrumentIntegrationDTO {

	@JsonProperty("ticker")
	private String ticker;

	@JsonProperty("name")
	private String name;

	@JsonProperty("text")
	private String text;

	@JsonProperty("logo")
	private String logo;

	@JsonProperty("find_status")
	private Integer findStatus;

}
