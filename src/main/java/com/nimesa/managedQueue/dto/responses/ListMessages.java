package com.nimesa.managedQueue.dto.responses;

import java.util.List;

import com.nimesa.managedQueue.model.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListMessages extends BaseResponse {

	public List<Message> getListMessageRs() {
		return listMessageRs;
	}

	public void setListMessageRs(List<Message> listMessageRs) {
		this.listMessageRs = listMessageRs;
	}

	public List<Message> listMessageRs;

}
