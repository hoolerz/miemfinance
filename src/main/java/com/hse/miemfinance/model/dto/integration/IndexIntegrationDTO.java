package com.hse.miemfinance.model.dto.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class IndexIntegrationDTO extends BaseIntegrationDTO {

	@JsonProperty("value")
	private float value;


}
