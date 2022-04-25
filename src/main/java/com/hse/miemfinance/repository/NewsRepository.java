package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.News;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

}
