package com.hse.miemfinance.repository;

import static com.hse.miemfinance.model.enums.Dictionaries.ItemTypes.COUNTRY;
import static com.hse.miemfinance.model.enums.Dictionaries.ItemTypes.QUICK_FILTER;
import static com.hse.miemfinance.model.enums.Dictionaries.ItemTypes.TAG;

import com.hse.miemfinance.model.entity.DictionaryItem;
import com.hse.miemfinance.model.entity.DictionaryItem_;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryItemRepository extends JpaRepository<DictionaryItem, Long>,
		JpaSpecificationExecutor<DictionaryItem>  {

	List<DictionaryItem> getAllByType(String type);

	default List<DictionaryItem> getAllCountries() {
		return findAll( (root,cq, cb) -> cb.and(
				cb.equal(root.get(DictionaryItem_.TYPE), COUNTRY)));
	}

	default List<DictionaryItem> getAllQuickFilters() {
		return findAll( (root,cq, cb) -> cb.and(
				cb.equal(root.get(DictionaryItem_.TYPE), QUICK_FILTER)));
	}

	default List<DictionaryItem> getAllTags() {
		return findAll( (root,cq, cb) -> cb.and(
				cb.equal(root.get(DictionaryItem_.TYPE), TAG)));
	}

	default Optional<DictionaryItem> getFilter(String filter) {
		return findOne( (root,cq, cb) -> cb.and(
				cb.equal(root.get(DictionaryItem_.TYPE), TAG),
				cb.equal(root.get(DictionaryItem_.VALUE),filter)
		));
	}

}
