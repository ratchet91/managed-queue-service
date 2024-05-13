package com.nimesa.managedQueue.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimesa.managedQueue.dto.requests.AddTopicRequests;
import com.nimesa.managedQueue.dto.responses.AddTopicSuccessRs;
import com.nimesa.managedQueue.dto.responses.ListTopics;
import com.nimesa.managedQueue.model.Topic;
import com.nimesa.managedQueue.service.TopicService;

@WebMvcTest(ManagedQueueApplicationController.class)
public class ManagedQueueApplicationControllerTest {

	@MockBean
	TopicService topicSvc;

	@Autowired
	MockMvc mockMvc;

	private String date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis()));

	@Test
	public void getAllTopics_test() throws JsonProcessingException, Exception {
		List<Topic> topics = new ArrayList<>();
		topics.add(new Topic("Test Topic 1", 123L));
		topics.add(new Topic("Test Topic 2", 122L));
		ListTopics res = new ListTopics();
		res.setListTopicListRs(topics);
		when(topicSvc.viewAllTopics()).thenReturn(res);

		mockMvc.perform(get("/getTopics").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(res))).andExpect(status().isOk());

	}

	@Test
	public void getMessageDataById_test() throws JsonProcessingException, Exception {
		List<String> result = new ArrayList<>();
		result.add("Msg1");
		result.add("Msg2");
		result.add("Msg3");
		String topicName = "Topic1";
		when(topicSvc.getDataByTopicName(topicName)).thenReturn(result);

		mockMvc.perform(get("/getData/{topic}", "Topic1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(result))).andExpect(status().isOk());
	}

	@Test
	public void addTopic_test() throws JsonProcessingException, Exception {
		AddTopicSuccessRs addRs = new AddTopicSuccessRs();
		addRs.setResponseBool(true);
		addRs.setTopicName("Topic1");
		addRs.setTopicId(123L);

		AddTopicRequests addRq = new AddTopicRequests();
		addRq.setTopicName("Topic1");
		when(topicSvc.addTopic(addRq)).thenReturn(addRs);

		mockMvc.perform(post("/data/{isProducer}", false).contentType(MediaType.APPLICATION_JSON)
				.content("{\"topicName\": \"Topic1\"}")).andExpect(status().isNonAuthoritativeInformation());

	}
}
