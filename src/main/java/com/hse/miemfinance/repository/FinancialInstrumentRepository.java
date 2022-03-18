package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.FinancialInstrument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialInstrumentRepository
		extends JpaRepository<FinancialInstrument, Long>, JpaSpecificationExecutor<FinancialInstrument> {

	Optional<FinancialInstrument> findByName(String name);

	Optional<FinancialInstrument> findByTicker(String ticker);

	List<FinancialInstrument> findAllByCountry(String countryCode);

}
