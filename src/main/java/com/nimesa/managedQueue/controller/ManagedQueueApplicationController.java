package com.nimesa.managedQueue.controller;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimesa.managedQueue.dto.requests.AddDataRq;
import com.nimesa.managedQueue.dto.requests.AddSubscriberData;
import com.nimesa.managedQueue.dto.requests.AddTopicRequests;
import com.nimesa.managedQueue.dto.responses.AddMessageSuccessRs;
import com.nimesa.managedQueue.dto.responses.AddSubscriberSuccesssRs;
import com.nimesa.managedQueue.dto.responses.AddTopicSuccessRs;
import com.nimesa.managedQueue.dto.responses.ListTopics;
import com.nimesa.managedQueue.model.Consumer;
import com.nimesa.managedQueue.service.TopicService;

@RestController()
public class ManagedQueueApplicationController {

	private final Map<String, Queue<String>> topicQueues = new ConcurrentHashMap<>();
	private final Map<String, List<Consumer>> topicSubscribers = new ConcurrentHashMap<>();

	private static final Logger logger = LoggerFactory.getLogger(ManagedQueueApplicationController.class);
	@Autowired
	private TopicService topicSvc;

	@PostMapping(value = "/topic/{isProducer}")
	public ResponseEntity<AddTopicSuccessRs> addTopic(@RequestBody AddTopicRequests topicRq,
			@PathVariable Boolean isProducer) {
		if (!isProducer) {
			logger.info("Only Producer can add.remove Topic");
			return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		if (topicRq.getTopicName().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			AddTopicSuccessRs topicRs = topicSvc.addTopic(topicRq);
			logger.info(" the value is : " + topicRs.getErrCode());
			if (topicRs.getErrCode() != null) {
				return new ResponseEntity<>(topicRs, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(topicRs, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/getTopics")
	public ResponseEntity<ListTopics> getAllTopics() {
		try {
			ListTopics TopicData = topicSvc.viewAllTopics();
			return new ResponseEntity<>(TopicData, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/getData/{topic}")
	public ResponseEntity<List<String>> getMessageDataById(@PathVariable String topic) {
		logger.info("The value for TopicName : " + topic);
		try {
			List<String> messageData = topicSvc.getDataByTopicName(topic);
			return new ResponseEntity<>(messageData, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/data/{isProducer}")
	public ResponseEntity<AddMessageSuccessRs> addDataToTopic(@RequestBody AddDataRq addRq,
			@PathVariable Boolean isProducer) {
		if (!isProducer) {
			logger.info("Only Producer can add.remove Topic");
			return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		try {
			AddMessageSuccessRs addMessageSuccessRs = topicSvc.addDataToTopic(addRq, isProducer);
			if (addMessageSuccessRs.isResponseBool()) {
				return new ResponseEntity<>(addMessageSuccessRs, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(addMessageSuccessRs, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/subscriber/fetch/{topic}")
	public ResponseEntity<AddSubscriberSuccesssRs> addSubscriberToFetchMessage(
			@RequestBody AddSubscriberData addSubscriber, @PathVariable String topic) {
		AddSubscriberSuccesssRs subscriberRs = new AddSubscriberSuccesssRs();
		try {
			subscriberRs = topicSvc.addSubscriberDataToFetchMessages(addSubscriber, topic);
			if (!subscriberRs.getMessages().isEmpty() && subscriberRs.getTopicSubscribed() == topic
					&& subscriberRs.getIsAdded()) {
				return new ResponseEntity<>(subscriberRs, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(subscriberRs, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception ex) {
			return new ResponseEntity<>(subscriberRs, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/subscribe/{topic}")
	public List<String> subscribeToTopic(@PathVariable String topic) {
		List<String> mssgList = topicSvc.getDataByTopicName(topic);
		return mssgList;

	}

//		Message message = new Message(data, topic);
//		messageRepository.save(message);

//		notifySubscribers(topicName, data);

//	@PostMapping("/subscribe/{topicName}")
//	public ResponseEntity<Topic> subscribeToTopic(@PathVariable String topicName, @RequestBody Consumer consumer) {
//
//		if (!topicRepository.existsByName(topicName)) {
//			throw new TopicNotFoundException("Topic '" + topicName + "' not found");
//		}
//		List<Consumer> subscribers = topicSubscribers.computeIfAbsent(topicName, k -> new LinkedList<>());
//		subscribers.add(consumer);
//
//		// Send existing data to the new subscriber
//		List<String> messages = messageRepository.findMessagesByTopicName(topicName);
//		for (String data : messages) {
//			consumer.receiveData(data);
//		}
//	}
//
//	private void notifySubscribers(String topicName, String data) {
//		List<Consumer> subscribers = topicSubscribers.getOrDefault(topicName, new LinkedList<>());
//		for (Consumer subscriber : subscribers) {
//			subscriber.receiveData(data);
//		}
//	}

}
