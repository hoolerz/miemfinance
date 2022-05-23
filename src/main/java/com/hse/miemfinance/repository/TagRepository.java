package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<InstrumentTag, Long>,
		JpaSpecificationExecutor<InstrumentTag>  {

	List<InstrumentTag> findAllByFinancialInstrument(Instrument instrument);

	List<InstrumentTag> findAllByTagValue(String tagValue);

	boolean existsByFinancialInstrumentAndTagValue(Instrument instrument, String tag);

}
