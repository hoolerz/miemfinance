package com.hse.miemfinance.model.entity;

import java.time.LocalDateTime;
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
public class FinancialInstrumentPrice extends AbstractPersistable<Long> {

	@ManyToOne
	@JoinColumn(name = "INSTRUMENT_ID")
	private FinancialInstrument financialInstrument;

	@Column
	private Long open;

	@Column
	private Long close;

	@Column
	private Long high;

	@Column
	private Long low;

	@Column
	private LocalDateTime updatedDate;

}
