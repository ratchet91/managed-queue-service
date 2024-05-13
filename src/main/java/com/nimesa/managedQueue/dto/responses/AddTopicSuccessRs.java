package com.nimesa.managedQueue.dto.responses;

public class AddTopicSuccessRs extends BaseResponse {
	boolean responseBool;
	String topicName;

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public boolean isResponseBool() {
		return responseBool;
	}

	Long topicId;

	public void setResponseBool(boolean responseBool) {
		this.responseBool = responseBool;
	}

}
