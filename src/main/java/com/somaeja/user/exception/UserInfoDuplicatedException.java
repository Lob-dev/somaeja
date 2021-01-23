package com.somaeja.user.exception;

public class UserInfoDuplicatedException extends RuntimeException {
	public UserInfoDuplicatedException(String errorMessage) {
		super(errorMessage);
	}
}
