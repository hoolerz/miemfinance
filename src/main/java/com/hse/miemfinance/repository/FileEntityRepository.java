package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.FileEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, Long>, JpaSpecificationExecutor<FileEntity> {

	Optional<FileEntity> findByName(String name);

}
