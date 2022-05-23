package com.hse.miemfinance.model.dto;

import com.hse.miemfinance.model.entity.instrument.InstrumentIndex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndexDTO {

	private String date;

	private String value;

	public IndexDTO(InstrumentIndex entity) {
		this.date = String.valueOf(entity.getUpdatedDate());
		this.value = String.valueOf(entity.getIndexValue());
	}

}
