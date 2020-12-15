package com.somaeja.post.exception;

import com.somaeja.common.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Post Domain 용
@ControllerAdvice
public class PostExceptionHandler {

	// Valid Exception Handler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		final ErrorResponse response = ErrorResponse.from("Invalid Input Value", exception.getBindingResult());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// Post Exception Handler
	@ExceptionHandler(NoSuchPostException.class)
	protected ResponseEntity<ErrorResponse> handleNoSuchPostException(NoSuchPostException exception){
		final ErrorResponse response = ErrorResponse.from(exception.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

}
