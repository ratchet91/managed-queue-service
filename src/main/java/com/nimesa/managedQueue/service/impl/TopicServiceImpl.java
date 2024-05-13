package com.nimesa.managedQueue.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimesa.managedQueue.TopicNotFoundException;
import com.nimesa.managedQueue.Exception.DuplicateActiveSubscriber;
import com.nimesa.managedQueue.Exception.DuplicateMessageException;
import com.nimesa.managedQueue.dto.requests.AddDataRq;
import com.nimesa.managedQueue.dto.requests.AddSubscriberData;
import com.nimesa.managedQueue.dto.requests.AddTopicRequests;
import com.nimesa.managedQueue.dto.responses.AddMessageSuccessRs;
import com.nimesa.managedQueue.dto.responses.AddSubscriberSuccesssRs;
import com.nimesa.managedQueue.dto.responses.AddTopicSuccessRs;
import com.nimesa.managedQueue.dto.responses.ListTopics;
import com.nimesa.managedQueue.enums.ErrorCodeErrorMessage;
import com.nimesa.managedQueue.model.Consumer;
import com.nimesa.managedQueue.model.Message;
import com.nimesa.managedQueue.model.Topic;
import com.nimesa.managedQueue.repository.ConsumerRepository;
import com.nimesa.managedQueue.repository.MessageRepository;
import com.nimesa.managedQueue.repository.TopicRepository;
import com.nimesa.managedQueue.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {
	private TopicRepository topicRepository;

	private static final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

	private MessageRepository messageRepository;
	private ConsumerRepository consumerRepository;

	private final Lock lock = new ReentrantLock();

	public TopicServiceImpl(MessageRepository messageRepository, TopicRepository topicRepository,
			ConsumerRepository consumerRepository) {
		// TODO Auto-generated constructor stub
		this.messageRepository = messageRepository;
		this.topicRepository = topicRepository;
		this.consumerRepository = consumerRepository;
	}

	@Override
	public ListTopics viewAllTopics() {
		ListTopics listTopics = new ListTopics();
		try {
			List<Topic> topics = topicRepository.findAll();
			listTopics.setListTopicListRs(topics);
			return listTopics;
		} catch (Exception ex) {
			listTopics.setErrCode(ErrorCodeErrorMessage.APPLICATION_ERROR.getErrCode());
			listTopics.setErrMsg(ErrorCodeErrorMessage.APPLICATION_ERROR.getErrMsg());
		}
		return null;
	}

	@Override
	@Transactional
	public AddTopicSuccessRs addTopic(AddTopicRequests topicRq) {
		lock.lock();
		try {
			logger.info(" The rq topicName is : " + topicRq.getTopicName());
			List<Topic> existingTopic = topicRepository.findByTopicName(topicRq.getTopicName());
			AddTopicSuccessRs res = new AddTopicSuccessRs();

			logger.info(" ExixtingTopic is : " + existingTopic);
			if (existingTopic.isEmpty()) {
				Topic newTopic = new Topic(topicRq.getTopicName());
				topicRepository.save(newTopic);
				res.setResponseBool(true);
				res.setTopicName(newTopic.getName());
				return res;
			} else {
				res.setErrCode(ErrorCodeErrorMessage.DUPLICATE_TOPIC.getErrCode());
				res.setErrMsg(ErrorCodeErrorMessage.DUPLICATE_TOPIC.getErrMsg());
				// throw new DuplicateTopicException(topicRq.getTopicName() + " already
				// exists");
				return res;
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Topic getTopicByName(String topicName) {
		List<Topic> existingTopic = topicRepository.findByTopicName(topicName);
		if (existingTopic.isEmpty()) {
			throw new TopicNotFoundException(topicName);
		}
		return existingTopic.get(0);
	}

	@Override
	public List<String> getDataByTopicName(String topicName) {
		List<String> messageList = messageRepository.findMessagesByTopicName(topicName);
		if (messageList.isEmpty()) {
			throw new TopicNotFoundException(topicName);
		}
		return messageList;
	}

	@Override
	@Transactional
	public AddMessageSuccessRs addDataToTopic(AddDataRq addRq, boolean isProducer) {
		lock.lock();
		AddMessageSuccessRs res = new AddMessageSuccessRs();
		try {
			List<Topic> topics = topicRepository.findByTopicName(addRq.getTopicName());
			if (topics.isEmpty()) {
				throw new TopicNotFoundException(addRq.getTopicName());
			} else {
				List<String> mssgExisting = messageRepository.findMessagesByTopicName(addRq.getTopicName());
				if (mssgExisting.contains(addRq.getData())) {
					logger.info(addRq.getData() + " : Data is already present");
					throw new DuplicateMessageException(
							addRq.getTopicName() + " is already having : " + addRq.getData());
				} else {
					Message msg = new Message(addRq.getData(), topics.get(0));
					messageRepository.save(msg);
					res.setResponseBool(true);
					res.setData(addRq.getData());
					res.setTopicName(addRq.getTopicName());

				}
			}
			return res;

		} catch (Exception ex) {
			res.setErrCode(ErrorCodeErrorMessage.DUPLICATE_MESSAGE.getErrCode());
			res.setErrMsg(ex.getMessage());
			return res;
		} finally {
			lock.unlock();
		}

	}

	@Override
	public AddSubscriberSuccesssRs addSubscriberDataToFetchMessages(AddSubscriberData addSubscriber, String topicName) {
		AddSubscriberSuccesssRs successRs = new AddSubscriberSuccesssRs();

		try {

			List<String> existingUser = consumerRepository.findConsumerByName(addSubscriber.getUserName(), true);
			logger.info(" Existing user " + existingUser);
			List<Topic> topics = topicRepository.findByTopicName(topicName);
			if (topics.isEmpty()) {
				throw new TopicNotFoundException(topicName + " is not present, Please try with another topic");
			} else if (!existingUser.isEmpty()) {
				throw new DuplicateActiveSubscriber(existingUser + " is already active");
			} else {
				logger.info("Into else block " + addSubscriber.getUserName());
				Consumer user = new Consumer(addSubscriber.getUserName(), true);
				consumerRepository.save(user);
				List<String> messageData = getDataByTopicName(topicName);
				successRs.setIsAdded(true);
				successRs.setMessages(messageData);
				successRs.setTopicSubscribed(topicName);
				successRs.setUserName(addSubscriber.getUserName());
				successRs.setIsActive(false);
				user.setIsActive(false);
				user.setLastUpdatedTimeStamp(
						new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date(System.currentTimeMillis())));
				consumerRepository.save(user);
				return successRs;
			}
		} catch (Exception ex) {
			successRs.setErrCode(ErrorCodeErrorMessage.APPLICATION_ERROR.getErrCode());
			successRs.setErrMsg(ex.getMessage());
			return successRs;
		}
	}

}
