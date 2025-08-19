package com.oranbyte.recipebook.exception;

public class UserNotFoundException extends Exception {

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException() {
		super("User not found");
	}

}
