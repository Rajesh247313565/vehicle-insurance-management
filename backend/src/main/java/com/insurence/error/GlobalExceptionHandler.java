package com.insurence.error;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleUserNotFoundExcepetion(UserNotFoundException ex){
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date(System.currentTimeMillis()));
		body.put("error", "User not found");
		body.put("message", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date(System.currentTimeMillis()));
		body.put("error", "validation errors");
		
		Map<String, String> fieldErrors = new HashMap<>();
		ex.getFieldErrors().forEach(
				error -> fieldErrors.put(error.getField(), error.getDefaultMessage())
				);
		
		body.put("fieldErrors", fieldErrors);
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception ex){
		
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date(System.currentTimeMillis()));
		body.put("error", "internal server error");
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserAlreadyExits.class)
	public ResponseEntity<Map<String, Object>> handleUserAlredyExistException(UserAlreadyExits ex){
		
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date(System.currentTimeMillis()));
		body.put("error", "user already exists");
		body.put("message", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IncorrectPassword.class)
	public ResponseEntity<Map<String, Object>> handleIncorrectPasswordException(IncorrectPassword ex){

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date(System.currentTimeMillis()));
		body.put("error", "bad credentials");
		body.put("message", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(PolicyAlreadyExistsException.class)
	public ResponseEntity<Map<String, Object>> handlePolicyAlreadyExistException(PolicyAlreadyExistsException ex){

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date(System.currentTimeMillis()));
		body.put("error", "bad credentials");
		body.put("message", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(NoPoliciesFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNoPoliciesFoundException(NoPoliciesFoundException ex){

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date(System.currentTimeMillis()));
		body.put("error", "bad credentials");
		body.put("message", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	
	
	
}
