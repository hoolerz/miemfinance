package com.hse.miemfinance.model.dto.instrument;

import com.hse.miemfinance.model.dto.PredictionDTO;
import com.hse.miemfinance.model.entity.instrument.Instrument;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstrumentInfoDTO extends InstrumentDTO {

	private String description;

	private String country;

	private Boolean isFavorite;

	private Set<PredictionDTO> predictions;

	public InstrumentInfoDTO(Instrument entity) {
		super(entity);
		this.description = entity.getDescription();
		this.country = entity.getCountry();
	}

}
