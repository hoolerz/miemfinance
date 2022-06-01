package com.hse.miemfinance.repository;

import com.hse.miemfinance.model.entity.FileEntity;
import com.hse.miemfinance.model.entity.user.User;
import com.hse.miemfinance.model.entity.user.UserAttachment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttachmentRepository extends JpaRepository<UserAttachment, Long>,
		JpaSpecificationExecutor<UserAttachment> {

	List<UserAttachment> findAllByUser(User user);

	Optional<UserAttachment> findByUser(User user);

	void deleteAllByUser(User user);

	void deleteByUserAndEntity(User user, FileEntity entity);

	boolean existsByUserAndAndEntity(User user, FileEntity entity);

}
