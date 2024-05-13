package com.nimesa.managedQueue.dto.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddSubscriberSuccesssRs extends BaseResponse {

	Boolean isAdded;
	String userName;
	Boolean isActive;
	String topicSubscribed;
	List<String> messages;

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getTopicSubscribed() {
		return topicSubscribed;
	}

	public void setTopicSubscribed(String topicSubscribed) {
		this.topicSubscribed = topicSubscribed;
	}

	public Boolean getIsAdded() {
		return isAdded;
	}

	public void setIsAdded(Boolean isAdded) {
		this.isAdded = isAdded;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
