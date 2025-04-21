package com.mysticalducks.rest.finance.exception;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;

//@EnableWebMvc
@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<String> handleException(UsernameNotFoundException e) {
		// log exception
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error Message");
	}

	@ExceptionHandler(EmptyBodyException.class)
	public void springHandleEmptyBody(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
		return new ResponseEntity<>(new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<?> handleDataNotFoundException(DataNotFoundException ex) {
		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND, ApiError.DATA_NOT_FOUND);
		
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND, ApiError.USER_NOT_FOUND);
		
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ExceptionHandler(IconNotFoundException.class)
	public ResponseEntity<?> handleUserNotFoundException(IconNotFoundException ex) {
		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND, ApiError.ICON_NOT_FOUND);
		
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException ex) {
		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND, ApiError.CATEGORY_NOT_FOUND);
		
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(PartyNotFoundException.class)
	public ResponseEntity<?> handlePartyNotFoundException(PartyNotFoundException ex) {
		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND, ApiError.PARTY_NOT_FUND);
		
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ExceptionHandler(FinanceInformationNotFoundException.class)
	public ResponseEntity<?> handleFinanceInformationNotFoundException(FinanceInformationNotFoundException ex) {
		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND, ApiError.FINANCE_INFORMATION_NOT_FOUND);
		
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	
	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<?> handleTransactionNotFoundException(TransactionNotFoundException ex) {
		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND, ApiError.TRANSACTION_NOT_FOUND);
		
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ExceptionHandler(PartyMemberNotFoundException.class)
	public ResponseEntity<?> handlePartyMemberNotFoundException(PartyMemberNotFoundException ex) {
		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND, ApiError.PARTY_MEMBER_NOT_FOUND);
		
		return new ResponseEntity<>(response, response.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

	    Map<String, Object> body = new LinkedHashMap<>();
	    body.put("timestamp", LocalDate.now());
	    body.put("status", status.value());

	    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
	            .map(x -> x.getDefaultMessage())
	            .collect(Collectors.toList());

	    body.put("errors", errors);

	    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}
