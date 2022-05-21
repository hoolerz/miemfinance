package com.hse.miemfinance.model.entity;

import java.util.List;
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
public class Instrument extends AbstractPersistable<Long> {

	@Column
	private String ticker;

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private String country;

	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<InstrumentNews> news;

	@OrderBy("updatedDate DESC")
	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<InstrumentIndex> index;

	@OrderBy("updatedDate DESC")
	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<InstrumentPrice> prices;

	@OrderBy("updatedDate DESC")
	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<InstrumentPrediction> predictions;

	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<InstrumentPrice> tags;

	@OneToMany(mappedBy = "financialInstrument", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<InstrumentAttachment> attachments;

}
