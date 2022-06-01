package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrediction;
import com.hse.miemfinance.model.entity.instrument.InstrumentPrediction_;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<InstrumentPrediction, Long>,
		JpaSpecificationExecutor<InstrumentPrediction>  {

	List<InstrumentPrediction> findAllByFinancialInstrument(Instrument instrument);

	List<InstrumentPrediction> findAllByUpdatedDate(LocalDate date);

	default List<InstrumentPrediction> findLatestForInstrument(Instrument instrument) {
		return findAll( (root,cq, cb) -> {

			Subquery<LocalDate> dateSubquery = cq.subquery(LocalDate.class);
			Root<InstrumentPrediction> dateRoot = dateSubquery.from(InstrumentPrediction.class);
			dateSubquery
					.select(cb.greatest((Expression) dateRoot.get(InstrumentPrediction_.UPDATED_DATE)))
					.where(cb.equal(dateRoot.get(InstrumentPrediction_.FINANCIAL_INSTRUMENT), instrument));

			return cb.and(
					cb.equal(root.get(InstrumentPrediction_.FINANCIAL_INSTRUMENT), instrument),
					cb.equal(root.get(InstrumentPrediction_.UPDATED_DATE), dateSubquery)
			);});
	}

}
