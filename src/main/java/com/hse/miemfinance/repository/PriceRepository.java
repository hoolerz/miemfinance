package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrice;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrice_;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<InstrumentPrice, Long>,
		JpaSpecificationExecutor<InstrumentPrice>  {

	List<InstrumentPrice> findAllByFinancialInstrument(Instrument instrument);

	List<InstrumentPrice> findAllByUpdatedDate(LocalDate date);

	default List<InstrumentPrice> findLatestForInstrument(Instrument instrument) {
		return findAll( (root,cq, cb) -> {
			Subquery<LocalDate> dateSubquery = cq.subquery(LocalDate.class);
			Root<InstrumentPrice> dateRoot = dateSubquery.from(InstrumentPrice.class);
			dateSubquery.select(cb.greatest((Expression) dateRoot.get(InstrumentPrice_.UPDATED_DATE)));

			return cb.and(
					cb.equal(root.get(InstrumentPrice_.FINANCIAL_INSTRUMENT), instrument),
					cb.equal(root.get(InstrumentPrice_.UPDATED_DATE), dateSubquery)
			);});
	}

}
