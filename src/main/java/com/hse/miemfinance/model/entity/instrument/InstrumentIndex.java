package com.hse.miemfinance.model.entity.instrument;

import com.hse.miemfinance.model.dto.integration.IndexIntegrationDTO;
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
@Table (name = "INSTRUMENT_INDEX")
@NoArgsConstructor
public class InstrumentIndex extends AbstractPersistable<Long> {

	@ManyToOne
	@JoinColumn(name = "INSTRUMENT_ID")
	private Instrument financialInstrument;

	@Column
	private Long indexValue;

	@Column
	private LocalDate updatedDate;

	public InstrumentIndex(IndexIntegrationDTO integrationDTO, Instrument instrument) {
		this.updatedDate = integrationDTO.getDate().toLocalDate();
		this.indexValue = (long) integrationDTO.getValue();
		this.financialInstrument = instrument;
	}

}
