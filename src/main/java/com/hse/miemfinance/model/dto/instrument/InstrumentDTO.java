package com.hse.miemfinance.model.dto.instrument;

import com.google.common.collect.Iterables;
import com.hse.miemfinance.model.dto.DataDTO;
import com.hse.miemfinance.model.entity.Instrument;
import com.hse.miemfinance.model.entity.InstrumentIndex;
import com.hse.miemfinance.model.entity.InstrumentPrice;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstrumentDTO extends DataDTO {

	private String name;

	private String ticker;

	private Long price;

	private Long index;

	private String attachmentId;

	public InstrumentDTO(Instrument entity) {
		super(String.valueOf(entity.getId()));
		this.name = entity.getName();
		this.ticker = entity.getTicker();
		this.price = getPriceFromEntity(entity);
		this.index = getIndexFromEntity(entity);
		this.attachmentId = "0";
	}

	private Long getPriceFromEntity(Instrument entity) {
		Optional<InstrumentPrice> rawPrice = Optional.ofNullable(Iterables.getLast(entity.getPrices(),null));
		return  rawPrice.isPresent()
				? rawPrice.get().getClose().longValue()
				: 0;
	}

	private Long getIndexFromEntity(Instrument entity) {
		Optional<InstrumentIndex> rawPrice = Optional.ofNullable(Iterables.getLast(entity.getIndex(),null));
		return  rawPrice.isPresent()
				? rawPrice.get().getIndexValue()
				: 0;
	}

}
