package com.hse.miemfinance.model.entity.user;

import com.hse.miemfinance.model.entity.instrument.Instrument;
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
@Table (name = "USER_SELECTED_INSTRUMENT")
@NoArgsConstructor
public class UserSelectedInstrument extends AbstractPersistable<Long> {

	@ManyToOne
	@JoinColumn(name = "INSTRUMENT_ID")
	private Instrument financialInstrument;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

}
