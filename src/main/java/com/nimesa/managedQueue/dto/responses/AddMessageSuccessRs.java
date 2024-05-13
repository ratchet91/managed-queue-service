package com.nimesa.managedQueue.dto.responses;

public class AddMessageSuccessRs extends BaseResponse {
	boolean responseBool;
	String topicName;
	String data;

	public boolean isResponseBool() {
		return responseBool;
	}

	public void setResponseBool(boolean responseBool) {
		this.responseBool = responseBool;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getDataList() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
