package com.sahil.smart_parking_project.globalException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 1) @Valid validation fail hone par (e.g. UserRequestDTO ke fields)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

		Map<String, String> fieldErrors = new HashMap<>();
		for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
			fieldErrors.put(fe.getField(), fe.getDefaultMessage());
		}

		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Failed",
				"One or more fields are invalid");
		response.setFieldErrors(fieldErrors);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// 2) Resource not mila (User/Slot/Vehicle/Booking) -> 404
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
		ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// 3) Slot already occupied -> 409 Conflict
	@ExceptionHandler(SlotUnavailableException.class)
	public ResponseEntity<ErrorResponse> handleSlotUnavailable(SlotUnavailableException ex) {
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), "Slot Unavailable", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	// 4) User kisi aur ka vehicle exit karne ki koshish kare -> 403 Forbidden
	@ExceptionHandler(UnauthorizedActionException.class)
	public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedActionException ex) {
		ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Forbidden", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}

	// 5) Login galat email/password -> 401 Unauthorized
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
		ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
				"Invalid email or password");
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	// 6) Role/permission ki wajah se access deny -> 403 (Spring Security se aata
	// hai)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
		ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Forbidden",
				"You do not have permission to perform this action");
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}

	// 7) Baaki sab generic RuntimeException (jab tak sab custom exception mein
	// migrate na ho)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// 8) Sabse aakhri fallback — kuch bhi unexpected crash ho (NullPointer, etc.)
	// -> 500
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
				"Something went wrong. Please try again later.");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException ex) {
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), "Duplicate Resource", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
}