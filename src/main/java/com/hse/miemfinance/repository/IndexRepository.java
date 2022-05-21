package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.InstrumentIndex;
import com.hse.miemfinance.model.entity.InstrumentIndex_;
import com.hse.miemfinance.model.entity.User;
import com.hse.miemfinance.model.entity.UserSelectedInstrument;
import com.hse.miemfinance.model.entity.UserSelectedInstrument_;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IndexRepository extends JpaRepository<InstrumentIndex, Long>,
		JpaSpecificationExecutor<InstrumentIndex>  {

	default List<InstrumentIndex> getTopForToday() {
		return findAll( (root,cq, cb) -> {
			cq.orderBy(cb.desc(root.get(InstrumentIndex_.INDEX_VALUE)));

			Subquery<LocalDate> dateSubquery = cq.subquery(LocalDate.class);
			Root<InstrumentIndex> dateRoot = dateSubquery.from(InstrumentIndex.class);
			dateSubquery.select(cb.greatest((Expression) dateRoot.get(InstrumentIndex_.UPDATED_DATE)));

			return cb.and(
					cb.equal(root.get(InstrumentIndex_.UPDATED_DATE), dateSubquery)
			);
		});
	}

	default List<InstrumentIndex> getTopForUser(User user) {
		return findAll( (root,cq, cb) -> {
			cq.orderBy(cb.desc(root.get(InstrumentIndex_.INDEX_VALUE)));

			Subquery<Long> instrumentSubquery = cq.subquery(Long.class);
			Root<UserSelectedInstrument> instrumentRoot = instrumentSubquery.from(UserSelectedInstrument.class);
			instrumentSubquery.select(instrumentRoot.get(UserSelectedInstrument_.FINANCIAL_INSTRUMENT))
					.where(cb.equal(instrumentRoot.get(UserSelectedInstrument_.USER),user));

			Subquery<LocalDate> dateSubquery = cq.subquery(LocalDate.class);
			Root<InstrumentIndex> dateRoot = dateSubquery.from(InstrumentIndex.class);
			dateSubquery.select(cb.greatest((Expression) dateRoot.get(InstrumentIndex_.UPDATED_DATE)));

			return cb.and(
					cb.equal(root.get(InstrumentIndex_.UPDATED_DATE), dateSubquery),
					root.get(InstrumentIndex_.FINANCIAL_INSTRUMENT).in(instrumentSubquery)
			);
		});
	}

}
