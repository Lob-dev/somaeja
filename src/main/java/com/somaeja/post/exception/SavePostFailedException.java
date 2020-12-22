package com.somaeja.post.exception;

public class SavePostFailedException extends IllegalArgumentException{

	public SavePostFailedException(String errorMessage) {
		super(errorMessage);
	}
}
