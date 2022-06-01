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
public class PredictionIntegrationDTO extends BaseIntegrationDTO {

	@JsonProperty("bin_prob")
	private float binProb;

	@JsonProperty("bin_pred")
	private int binPred;

	@JsonProperty("barr_prob_0")
	private float barrProb0;

	@JsonProperty("barr_prob_1")
	private float barrProb1;

	@JsonProperty("barr_prob_2")
	private float barrProb2;

	@JsonProperty("barr_pred")
	private int barrPred;

	@JsonProperty("barr_results")
	private int barrResults;

	@JsonProperty("bin_results")
	private int binResults;

}
