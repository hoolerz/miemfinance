package com.hse.miemfinance.model.entity.user;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Getter
@Setter
@Entity
@Table (name = "users")
@NoArgsConstructor
public class User extends AbstractPersistable<Long> {

	@Column
	private String username;

	@Column
	private String email;

	@Column
	private String preferredName;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<UserSelectedInstrument> selectedInstruments;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private UserAttachment attachment;

}
