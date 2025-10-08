package com.insurence.error;

public class NoPoliciesFoundException extends RuntimeException {

	public NoPoliciesFoundException() {
		super();
	}

	public NoPoliciesFoundException(String message) {
		super(message);
	}
	
}
