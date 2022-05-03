package com.hse.miemfinance.model.dto;

import com.google.common.collect.Iterables;
import com.hse.miemfinance.model.entity.FinancialInstrument;
import com.hse.miemfinance.model.entity.FinancialInstrumentIndex;
import com.hse.miemfinance.model.entity.FinancialInstrumentPrice;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FinancialInstrumentDTO extends DataDTO {

	private String name;

	private String ticker;

	//private String description;

	private String country;

	private String attachmentId;

	private Boolean isFavorite;

	private Long price;

	private Long index;

	public FinancialInstrumentDTO(FinancialInstrument entity) {
		super(String.valueOf(entity.getId()));
		this.name = entity.getName();
		this.ticker = entity.getTicker();
		//this.description = entity.getDescription();
		this.country = entity.getCountry();
		this.price = getPriceFromEntity(entity);
		this.index = getIndexFromEntity(entity);
		this.isFavorite = true;
	}

	private Long getPriceFromEntity(FinancialInstrument entity) {
		Optional<FinancialInstrumentPrice> rawPrice = Optional.ofNullable(Iterables.getLast(entity.getPrices(),null));
		return  rawPrice.isPresent()
				? rawPrice.get().getClose()
				: 0;
	}

	private Long getIndexFromEntity(FinancialInstrument entity) {
		Optional<FinancialInstrumentIndex> rawPrice = Optional.ofNullable(Iterables.getLast(entity.getIndex(),null));
		return  rawPrice.isPresent()
				? rawPrice.get().getIndexValue()
				: 0;
	}

}
