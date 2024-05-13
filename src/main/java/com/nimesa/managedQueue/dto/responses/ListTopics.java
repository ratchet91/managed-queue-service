package com.nimesa.managedQueue.dto.responses;

import java.util.List;

import com.nimesa.managedQueue.model.Topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListTopics extends BaseResponse {

	public List<Topic> listTopicListRs;

	public List<Topic> getListTopicListRs() {
		return listTopicListRs;
	}

	public void setListTopicListRs(List<Topic> listTopicListRs) {
		this.listTopicListRs = listTopicListRs;
	}

}
