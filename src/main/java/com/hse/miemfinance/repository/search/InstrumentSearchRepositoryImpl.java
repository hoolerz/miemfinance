package com.hse.miemfinance.repository.search;

import com.hse.miemfinance.model.entity.instrument.Instrument;
import com.hse.miemfinance.model.entity.instrument.Instrument_;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.transaction.annotation.Transactional;

public class InstrumentSearchRepositoryImpl implements InstrumentSearchRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	@Override
	public void index() {
		SearchSession searchSession = Search.session(entityManager);
		MassIndexer indexer = searchSession.massIndexer( Instrument.class )
				.threadsToLoadObjects( 7 );
		try {
			indexer.startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Instrument> fullTextSearch(String terms, int offset, int limit) {
		return Search.session(entityManager)
				.search(Instrument.class)
				.where(entity -> entity.match()
						.fields(Instrument_.NAME, Instrument_.DESCRIPTION, Instrument_.TICKER)
						.matching(terms))
				.fetchHits(offset, limit);
	}

}
