package com.hse.miemfinance.model.dto.instrument;

import com.hse.miemfinance.model.entity.Instrument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstrumentInfoDTO extends InstrumentDTO {

	private String description;

	private String country;

	public InstrumentInfoDTO(Instrument entity) {
		super(entity);
		this.description = entity.getDescription();
		this.country = entity.getCountry();
	}

}
