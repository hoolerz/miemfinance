package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.user.User;
import com.hse.miemfinance.model.entity.user.UserSelectedInstrument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSelectedInstrumentRepository extends JpaRepository<UserSelectedInstrument, Long>,
		JpaSpecificationExecutor<UserSelectedInstrument> {

	List<UserSelectedInstrument> findAllByUser(User user);

	Optional<UserSelectedInstrument> findByFinancialInstrumentAndUser(Instrument financialInstrument, User user);

	boolean existsByFinancialInstrumentAndAndUser(Instrument instrument, User user);

}
