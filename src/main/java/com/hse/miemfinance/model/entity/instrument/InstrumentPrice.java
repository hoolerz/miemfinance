package com.hse.miemfinance.model.entity.instrument;

import com.hse.miemfinance.model.dto.integration.QuotesIntegrationDTO;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Getter
@Setter
@Entity
@Table(name = "INSTRUMENT_PRICE")
@NoArgsConstructor
public class InstrumentPrice extends AbstractPersistable<Long> {

	@ManyToOne
	@JoinColumn(name = "INSTRUMENT_ID")
	private Instrument financialInstrument;

	@Column
	private Double open;

	@Column
	private Double close;

	@Column
	private Double high;

	@Column
	private Double low;

	@Column
	private LocalDate updatedDate;

	public InstrumentPrice(QuotesIntegrationDTO integrationDTO, Instrument instrument) {
		this.updatedDate = integrationDTO.getDate().toLocalDate();
		this.open = integrationDTO.getOpen();
		this.close = integrationDTO.getClose();
		this.low = integrationDTO.getLow();
		this.high = integrationDTO.getHigh();
		this.financialInstrument = instrument;
	}

	public InstrumentPrice(QuotesIntegrationDTO integrationDTO) {
		this.updatedDate = integrationDTO.getDate().toLocalDate();
		this.open = integrationDTO.getOpen();
		this.close = integrationDTO.getClose();
		this.low = integrationDTO.getLow();
		this.high = integrationDTO.getHigh();
	}

}
