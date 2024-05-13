package com.nimesa.managedQueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimesa.managedQueue.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
	@Query(value = "SELECT * FROM topic t WHERE t.name = :topicName", nativeQuery = true)
	List<Topic> findByTopicName(@Param("topicName") String topicName);
}
