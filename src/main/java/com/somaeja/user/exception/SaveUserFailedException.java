package com.somaeja.user.exception;

public class SaveUserFailedException extends IllegalArgumentException {

	public SaveUserFailedException(String errorMessage) {
		super(errorMessage);
	}
}
