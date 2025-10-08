package com.insurence.error;

public class PolicyAlreadyExistsException extends RuntimeException {

	public PolicyAlreadyExistsException() {
		super();
	}

	public PolicyAlreadyExistsException(String message) {
		super(message);
	}

	
}
