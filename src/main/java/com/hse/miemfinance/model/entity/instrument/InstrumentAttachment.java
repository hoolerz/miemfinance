package com.hse.miemfinance.model.entity.instrument;

import com.hse.miemfinance.model.entity.FileEntity;
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
@Table(name = "INSTRUMENT_ATTACHMENT")
@NoArgsConstructor
public class InstrumentAttachment extends AbstractPersistable<Long> {

	@ManyToOne
	@JoinColumn(name = "FILE_ENTITY_ID")
	private FileEntity entity;

	@ManyToOne
	@JoinColumn(name = "INSTRUMENT_ID")
	private Instrument financialInstrument;

}

