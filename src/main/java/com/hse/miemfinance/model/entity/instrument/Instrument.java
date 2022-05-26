package com.hse.miemfinance.model.entity.instrument;

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
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Getter
@Setter
@Entity
@Table (name = "FINANCIAL_INSTRUMENT")
@NoArgsConstructor
@Indexed
public class Instrument extends AbstractPersistable<Long> {

	@Column
	@FullTextField(analyzer = "english")
	private String ticker;

	@Column
	@FullTextField(analyzer = "russian")
	private String name;

	@Column
	@FullTextField(analyzer = "russian")
	private String description;

	@Column
	@GenericField(projectable = Projectable.NO, sortable = Sortable.NO, searchable = Searchable.NO)
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
