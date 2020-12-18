package com.somaeja.post.exception;

public class ModifyPostFailedException extends IllegalArgumentException {

	public ModifyPostFailedException(String errorMessage) {
		super(errorMessage);
	}
}
