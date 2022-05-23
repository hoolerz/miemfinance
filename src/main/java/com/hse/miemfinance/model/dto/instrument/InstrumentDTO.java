package com.hse.miemfinance.model.dto.instrument;

import com.hse.miemfinance.model.dto.DataDTO;
import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentIndex;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrice;
import java.util.Comparator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InstrumentDTO extends DataDTO {

	private String name;

	private String ticker;

	private String price;

	private Long index;

	private String attachmentId;

	public InstrumentDTO(Instrument entity) {
		super(String.valueOf(entity.getId()));
		this.name = entity.getName();
		this.ticker = entity.getTicker();
		this.attachmentId = "0";
		getPriceFromEntity(entity);
		getIndexFromEntity(entity);
	}

	public void setCurrency(String currency) {
		this.price = price + currency;
	}

	private void getPriceFromEntity(Instrument entity) {
		Comparator<InstrumentPrice> comparator = Comparator
				.comparing(InstrumentPrice::getUpdatedDate, Comparator.nullsLast(Comparator.reverseOrder()));
		entity.getPrices().stream()
				.min(comparator)
				.ifPresent(price -> this.price = String.valueOf(price.getClose()));
	}

	private void getIndexFromEntity(Instrument entity) {
		Comparator<InstrumentIndex> comparator = Comparator
				.comparing(InstrumentIndex::getUpdatedDate, Comparator.nullsLast(Comparator.reverseOrder()));
		entity.getIndex().stream()
				.min(comparator)
				.ifPresent(index -> this.index = index.getIndexValue());
	}

}
