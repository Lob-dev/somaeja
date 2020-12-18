package com.somaeja.post.exception;

import java.util.NoSuchElementException;

public class NoSuchPostException extends NoSuchElementException {

	public NoSuchPostException(String errorMessage) {
		super(errorMessage);
	}
}
