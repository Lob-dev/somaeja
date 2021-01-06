package com.somaeja.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String errorMessage;
	private BindingResult bindResult;

	public static ErrorResponse from() {
		return new ErrorResponse();
	}

	public static ErrorResponse from(String errorMessage, BindingResult message) {
		return new ErrorResponse(errorMessage, message);
	}

	public static ErrorResponse from(String errorMessage) {
		return new ErrorResponse(errorMessage);
	}

	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
