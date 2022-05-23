package com.hse.miemfinance.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Getter
@Setter
@Entity
@Table(name = "DICTIONARY_ITEM")
@NoArgsConstructor
public class DictionaryItem extends AbstractPersistable<Long>  {

	@Column
	private String type;

	@Column
	private String value;

	@Column
	private String text;

}
