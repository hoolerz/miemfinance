package com.hse.miemfinance.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SelectedInstrumentDTO {

	private String userId;

	private String instrumentId;

	private String ticker;

	private String name;

	private String price;

	private String index;

	private String attachment;


}
