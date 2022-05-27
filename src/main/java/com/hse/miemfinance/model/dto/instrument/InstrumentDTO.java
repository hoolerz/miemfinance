package com.hse.miemfinance.model.dto.instrument;

import com.hse.miemfinance.model.dto.DataDTO;
import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentIndex;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrice;
import java.util.Comparator;
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

	private String price;

	private Long index;

	private String attachmentId;

	public InstrumentDTO(Instrument entity) {
		super(String.valueOf(entity.getId()));
		this.name = entity.getName();
		this.ticker = entity.getTicker();
		getPriceFromEntity(entity);
		getIndexFromEntity(entity);
		Optional.ofNullable(entity.getAttachment())
				.ifPresentOrElse(
						attachment -> this.attachmentId = String.valueOf(attachment.getEntity().getId()),
						() -> this.attachmentId = "0"
				);
	}

	public void setCurrency(String currency) {
		this.price = price + currency;
	}

	private void getPriceFromEntity(Instrument entity) {
		Comparator<InstrumentPrice> comparator = Comparator
				.comparing(InstrumentPrice::getUpdatedDate, Comparator.nullsLast(Comparator.reverseOrder()));
		entity.getPrices().stream()
				.min(comparator)
				.ifPresentOrElse(
						price -> this.price = String.valueOf(price.getClose()),
						() -> this.price = "0"
				);
	}

	private void getIndexFromEntity(Instrument entity) {
		Comparator<InstrumentIndex> comparator = Comparator
				.comparing(InstrumentIndex::getUpdatedDate, Comparator.nullsLast(Comparator.reverseOrder()));
		entity.getIndex().stream()
				.min(comparator)
				.ifPresentOrElse(
						index -> this.index = index.getIndexValue(),
						() -> this.index = 0L
				);
	}

}
