package com.hse.miemfinance.model.dto;

import com.hse.miemfinance.model.entity.instrument.InstrumentPrediction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PredictionDTO {

	private String type;

	private String certainty;

	private String prediction;

	private String date;

	public PredictionDTO(InstrumentPrediction entity) {
		this.type = entity.getType();
		this.prediction = entity.getPrediction();
		this.certainty = String.format("%.2f", entity.getCertainty());
		this.date = String.valueOf(entity.getUpdatedDate());
	}

}
