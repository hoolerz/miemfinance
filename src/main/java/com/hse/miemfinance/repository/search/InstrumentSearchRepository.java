package com.hse.miemfinance.repository.search;

import com.hse.miemfinance.model.entity.instrument.Instrument;
import java.util.List;

public interface InstrumentSearchRepository {

	void index();

	List<Instrument> fullTextSearch(String terms, int offset, int limit);

}
