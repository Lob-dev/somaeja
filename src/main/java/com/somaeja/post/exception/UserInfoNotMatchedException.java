package com.somaeja.post.exception;

public class UserInfoNotMatchedException extends IllegalArgumentException{

	public UserInfoNotMatchedException(String errorMessage) {
		super(errorMessage);
	}
}
