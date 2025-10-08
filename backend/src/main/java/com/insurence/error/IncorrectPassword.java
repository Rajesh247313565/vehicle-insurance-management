package com.insurence.error;

public class IncorrectPassword extends RuntimeException {

	public IncorrectPassword() {
		super();
	}
	
	public IncorrectPassword(String message) {
		super(message);
	}

	
}
