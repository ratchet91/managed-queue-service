package com.nimesa.managedQueue.service;

import java.util.List;

import com.nimesa.managedQueue.dto.requests.AddDataRq;
import com.nimesa.managedQueue.dto.requests.AddSubscriberData;
import com.nimesa.managedQueue.dto.requests.AddTopicRequests;
import com.nimesa.managedQueue.dto.responses.AddMessageSuccessRs;
import com.nimesa.managedQueue.dto.responses.AddSubscriberSuccesssRs;
import com.nimesa.managedQueue.dto.responses.AddTopicSuccessRs;
import com.nimesa.managedQueue.dto.responses.ListTopics;
import com.nimesa.managedQueue.model.Topic;

public interface TopicService {
	ListTopics viewAllTopics();

	AddTopicSuccessRs addTopic(AddTopicRequests topicRq);

	Topic getTopicByName(String topicName);

	List<String> getDataByTopicName(String topicName);

	AddMessageSuccessRs addDataToTopic(AddDataRq addRq, boolean isProducer);

	AddSubscriberSuccesssRs addSubscriberDataToFetchMessages(AddSubscriberData addSubscriberData, String topicName);

}
