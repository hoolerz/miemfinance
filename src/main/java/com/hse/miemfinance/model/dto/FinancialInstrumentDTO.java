package com.hse.miemfinance.model.dto;

import com.hse.miemfinance.model.entity.FinancialInstrument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FinancialInstrumentDTO extends DataDTO {

	private String name;

	private String ticker;

	private String description;

	private String country;

	public FinancialInstrumentDTO(FinancialInstrument entity) {
		this.id = String.valueOf(entity.getId());
		this.name = entity.getName();
		this.ticker = entity.getTicker();
		this.description = entity.getDescription();
		this.country = entity.getCountry();
	}

}
