package com.nimesa.managedQueue.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateMessageException extends RuntimeException {
	public DuplicateMessageException(String message) {
		super(message);
	}
}
