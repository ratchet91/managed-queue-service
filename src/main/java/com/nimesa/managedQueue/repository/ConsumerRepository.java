package com.nimesa.managedQueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimesa.managedQueue.model.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
	@Query("SELECT c.userName FROM Consumer c WHERE c.userName = :userName and c.isActive= :isActive")
	List<String> findConsumerByName(@Param("userName") String userName, @Param("isActive") boolean isActive);

}
