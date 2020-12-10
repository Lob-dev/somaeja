package com.somaeja.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

	private final String errorMessage;
	private final BindingResult bindResult;

	public static ErrorResponse from(String errorMessage, BindingResult message) {
		return new ErrorResponse(errorMessage, message);
	}
}
