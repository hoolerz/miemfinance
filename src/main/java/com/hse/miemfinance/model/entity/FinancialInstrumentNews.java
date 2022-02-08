package com.hse.miemfinance.model.entity;

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
@Table(name = "INSTRUMENT_NEWS")
@NoArgsConstructor
public class FinancialInstrumentNews extends AbstractPersistable<Long> {

	@ManyToOne
	@JoinColumn(name = "INSTRUMENT_ID")
	private FinancialInstrument financialInstrument;

	@ManyToOne
	@JoinColumn(name = "NEWS_ID")
	private News news;

}
