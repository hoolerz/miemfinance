package com.hse.miemfinance.model.dto.instrument;

import com.hse.miemfinance.model.entity.Instrument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SelectedInstrumentDTO extends InstrumentDTO {

	private Boolean isFavorite;

	public SelectedInstrumentDTO (Instrument entity) {
		super(entity);
		this.isFavorite = true;
	}

}
