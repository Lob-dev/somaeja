package com.somaeja.post.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class NoSuchPostException extends RuntimeException {

	private final String message;

	public NoSuchPostException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
