package com.hse.miemfinance.model.entity;

import com.hse.miemfinance.model.entity.instrument.InstrumentNews;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Getter
@Setter
@Entity
@Table (name = "NEWS")
@NoArgsConstructor
public class News extends AbstractPersistable<Long> {

	@Column
	private String header;

	@Column
	private String fullText;

	@Column
	private String shortText;

	@Column
	private String source;

	@Column
	private String link;

	@Column
	private String sentiment;

	@Column
	private LocalDateTime publishedDate;

	//@OrderBy("financialInstrument.name")
	@OneToMany(mappedBy = "news", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<InstrumentNews> financialInstruments;

}
