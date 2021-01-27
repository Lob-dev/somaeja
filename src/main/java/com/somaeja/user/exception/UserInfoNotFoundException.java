package com.somaeja.user.exception;

public class UserInfoNotFoundException extends IllegalArgumentException {
	public UserInfoNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
