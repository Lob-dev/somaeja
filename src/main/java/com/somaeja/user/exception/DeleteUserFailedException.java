package com.somaeja.user.exception;

public class DeleteUserFailedException extends IllegalStateException {
	public DeleteUserFailedException(String errorMessage) {
		super(errorMessage);
	}
}
