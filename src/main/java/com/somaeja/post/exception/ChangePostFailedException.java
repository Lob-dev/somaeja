package com.somaeja.post.exception;

public class ChangePostFailedException extends IllegalArgumentException {

	public ChangePostFailedException(String errorMessage) {
		super(errorMessage);
	}
}
