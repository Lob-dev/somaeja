package com.somaeja.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	// HttpRequestMethodNotSupportedException : 지원되지 않는 HTTP Method 가 호출되었을 경우 발생한다.
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleNullPointerException(HttpRequestMethodNotSupportedException exception) {
		final ErrorResponse response = ErrorResponse.from(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	// 예상하지 못한 Exception Catch = 클라이언트의 White Page 를 방지하기 위함
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception exception) {
		final ErrorResponse response = ErrorResponse.from();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

}
