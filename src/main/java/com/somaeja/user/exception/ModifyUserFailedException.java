package com.somaeja.user.exception;

public class ModifyUserFailedException extends IllegalArgumentException {
	public ModifyUserFailedException(String errorMessage) {
		super(errorMessage);
	}
}
