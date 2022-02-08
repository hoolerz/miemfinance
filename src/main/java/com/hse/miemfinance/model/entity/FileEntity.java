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
@Table (name = "FILE_ENTITY")
@NoArgsConstructor
public class FileEntity extends AbstractPersistable<Long> {

	@Column
	private String name;

	@Column
	private String contentType;

}
