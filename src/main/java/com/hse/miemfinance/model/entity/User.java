package com.hse.miemfinance.model.entity;

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
@Table (name = "USER")
@NoArgsConstructor
public class User extends AbstractPersistable<Long> {

	@Column
	private String login;

	@Column
	private String password;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<UserSelectedInstrument> selectedInstruments;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<UserAttachment> attachments;

}
