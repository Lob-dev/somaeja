package com.somaeja.user.exception;

import com.somaeja.global.exception.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(1)
@ControllerAdvice(basePackages = "com.somaeja.user")
public class UserExceptionHandler {

	// User email duplicate
	@ExceptionHandler(UserInfoDuplicatedException.class)
	protected ResponseEntity<ErrorResponse> handleUserInfoDuplicatedException(UserInfoDuplicatedException exception) {
		final ErrorResponse response = ErrorResponse.from(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	// User save
	@ExceptionHandler(SaveUserFailedException.class)
	protected ResponseEntity<ErrorResponse> handleSaveUserFailedException(SaveUserFailedException exception) {
		final ErrorResponse response = ErrorResponse.from(exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	// User modify
	@ExceptionHandler(ModifyUserFailedException.class)
	protected ResponseEntity<ErrorResponse> handleModifyUserFailedException(ModifyUserFailedException exception) {
		final ErrorResponse response = ErrorResponse.from(exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

}
