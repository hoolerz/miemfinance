package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.Instrument;
import com.hse.miemfinance.model.entity.InstrumentNews;
import com.hse.miemfinance.model.entity.InstrumentNews_;
import com.hse.miemfinance.model.entity.News;
import com.hse.miemfinance.model.entity.News_;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

	Optional<News> findByHeader(String header);

	List<News> findAllBySource(String source);

	List<News> findAllByPublishedDate(LocalDateTime publishedDate);

	List<News> findAllByPublishedDateAfter(LocalDateTime publishedDate);

	List<News> findAllByOrderByPublishedDateDesc();

	default List<News> findAllByInstrument(Instrument instrument) {
		return findAll((root, cq, cb) -> {
			Subquery<Long> instrumentSubquery = cq.subquery(Long.class);
			Root<InstrumentNews> instrumentRoot = instrumentSubquery.from(InstrumentNews.class);
			instrumentSubquery.select(instrumentRoot.get(InstrumentNews_.NEWS))
					.where(cb.equal(instrumentRoot.get(InstrumentNews_.FINANCIAL_INSTRUMENT),instrument));

			return cb.and(
					root.get(News_.ID).in(instrumentSubquery)
			);
		});
	}

}
