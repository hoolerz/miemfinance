package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.instrument.InstrumentNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentNewsRepository extends JpaRepository<InstrumentNews, Long>,
		JpaSpecificationExecutor<InstrumentNews>  {

}
