package com.nimesa.managedQueue.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimesa.managedQueue.model.Message;
import com.nimesa.managedQueue.model.Topic;
import com.nimesa.managedQueue.repository.MessageRepository;
import com.nimesa.managedQueue.repository.TopicRepository;

@Component
public class MySqlDataConfiguration {

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private MessageRepository messageRepository;

	@PostConstruct
	private void postConstruct() {
		Topic topic = new Topic("Topic1");
		if (!topic.getName().isBlank()) {
			topicRepository.save(topic);
		}
		Topic topic2 = new Topic("Topic2");
		if (!topic.getName().isBlank()) {
			topicRepository.save(topic2);
		}
		Topic topic3 = new Topic("Topic3");
		if (!topic.getName().isBlank()) {
			topicRepository.save(topic3);
		}

		List<Message> msgList = new ArrayList<>();
		msgList.add(new Message("A", topic));
		msgList.add(new Message("B", topic));
		msgList.add(new Message("C", topic));
		msgList.add(new Message("1", topic2));
		msgList.add(new Message("2", topic3));

		messageRepository.saveAll(msgList);

	}
}
