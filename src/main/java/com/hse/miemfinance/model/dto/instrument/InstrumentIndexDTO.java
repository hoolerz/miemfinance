package com.hse.miemfinance.model.dto.instrument;

import com.hse.miemfinance.model.dto.IndexDTO;
import com.hse.miemfinance.model.entity.instrument.Instrument;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstrumentIndexDTO extends InstrumentDTO{

	private List<IndexDTO> indices;

	public InstrumentIndexDTO(Instrument entity) {
		super(entity);
	}

}
