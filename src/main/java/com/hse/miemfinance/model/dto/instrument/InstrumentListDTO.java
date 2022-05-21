package com.hse.miemfinance.model.dto.instrument;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstrumentListDTO {

	private List<? extends InstrumentDTO> instruments;

}
