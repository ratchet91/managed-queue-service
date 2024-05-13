package com.nimesa.managedQueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nimesa.managedQueue.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	@Query("SELECT m.data FROM Message m WHERE m.topic.name = :topicName")
	List<String> findMessagesByTopicName(@Param("topicName") String topicName);
}
