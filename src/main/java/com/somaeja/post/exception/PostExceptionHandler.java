package com.somaeja.post.exception;

import com.somaeja.global.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// Post Domain 용
@ControllerAdvice
public class PostExceptionHandler {

	// Valid Exception Handler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		final ErrorResponse response = ErrorResponse.from("Invalid Input Value", exception.getBindingResult());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	// Post create Exception Handler
	@ExceptionHandler(SavePostFailedException.class)
	protected ResponseEntity<ErrorResponse> handleSavePostFailedException(SavePostFailedException exception) {
		final ErrorResponse response = ErrorResponse.from(exception.getMessage(), null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	// Post Delete Exception Handler
	@ExceptionHandler(NoSuchPostException.class)
	protected ResponseEntity<ErrorResponse> handleNoSuchPostException(NoSuchPostException exception) {
		final ErrorResponse response = ErrorResponse.from(exception.getMessage(), null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	// Post Modify Exception Handler
	@ExceptionHandler(ModifyPostFailedException.class)
	protected ResponseEntity<ErrorResponse> handleModifyPostFailedException(ModifyPostFailedException exception) {
		final ErrorResponse response = ErrorResponse.from(exception.getMessage(), null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	// Post Find Exception Handler = Type MismatchException
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException() {
		final ErrorResponse response = ErrorResponse.from("Failed to convert value of type", null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
