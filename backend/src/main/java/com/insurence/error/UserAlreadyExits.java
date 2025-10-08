package com.insurence.error;

public class UserAlreadyExits extends RuntimeException {

	public UserAlreadyExits() {
		super();
	}
	
	public UserAlreadyExits(String message) {
		super(message);
	}

}
