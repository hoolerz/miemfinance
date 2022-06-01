package com.hse.miemfinance.model.entity.instrument;

import com.hse.miemfinance.model.dto.integration.PredictionIntegrationDTO;
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
@Table(name = "INSTRUMENT_PREDICTION")
@NoArgsConstructor
public class InstrumentPrediction extends AbstractPersistable<Long> {

	@ManyToOne
	@JoinColumn(name = "INSTRUMENT_ID")
	private Instrument financialInstrument;

	@Column
	private String type;

	@Column
	private String prediction;

	@Column
	private Double certainty;

	@Column
	private LocalDate updatedDate;

	public InstrumentPrediction(PredictionIntegrationDTO integrationDTO, Instrument instrument) {
		this.updatedDate = integrationDTO.getDate().toLocalDate();
		this.financialInstrument = instrument;
	}

}
