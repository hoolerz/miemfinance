package com.hse.miemfinance.model.entity;

import java.util.ArrayList;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Getter
@Setter
@Entity
@Table (name = "FINANCIAL_INSTRUMENT")
@NoArgsConstructor
public class FinancialInstrument extends AbstractPersistable<Long> {

	@Column
	private String ticker;

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private String country;

	@OrderBy("news.publishedDate")
	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<FinancialInstrumentNews> news;

	@OrderBy("updatedDate")
	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<FinancialInstrumentIndex> index;

	@OrderBy("updatedDate")
	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<FinancialInstrumentPrice> prices;

	@OrderBy("updatedDate")
	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<FinancialInstrumentPrediction> predictions;

	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<FinancialInstrumentPrice> tags;

	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<FinancialInstrumentAttachment> attachments;

}
