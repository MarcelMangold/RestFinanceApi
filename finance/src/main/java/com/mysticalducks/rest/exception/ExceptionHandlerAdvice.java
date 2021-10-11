package com.mysticalducks.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


	@ControllerAdvice
	public class ExceptionHandlerAdvice {

	    @ExceptionHandler(UsernameNotFoundException.class)
	    public ResponseEntity handleException(UsernameNotFoundException e) {
	        // log exception
	        return ResponseEntity
	                .status(HttpStatus.FORBIDDEN)
	                .body("Error Message");
	    }        
	}



