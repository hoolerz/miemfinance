package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentAttachment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentAttachmentRepository extends JpaRepository<InstrumentAttachment, Long>,
		JpaSpecificationExecutor<InstrumentAttachment> {

	List<InstrumentAttachment> findAllByFinancialInstrument (Instrument instrument);

}
