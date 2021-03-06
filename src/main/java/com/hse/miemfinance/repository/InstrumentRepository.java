package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.repository.search.InstrumentSearchRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository
		extends JpaRepository<Instrument, Long>, JpaSpecificationExecutor<Instrument>, InstrumentSearchRepository {

	Optional<Instrument> findByName(String name);

	Optional<Instrument> findByTicker(String ticker);

	List<Instrument> findAllByCountry(String countryCode);

	boolean existsByTicker(String ticker);

}
