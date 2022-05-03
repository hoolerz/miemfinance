package com.hse.miemfinance.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstrumentListDTO {

	private List<FinancialInstrumentDTO> instruments;

}
