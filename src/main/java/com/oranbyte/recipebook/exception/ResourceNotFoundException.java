package com.oranbyte.recipebook.exception;

public class ResourceNotFoundException extends Exception {

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException() {
		super("Not found");
	}

}
