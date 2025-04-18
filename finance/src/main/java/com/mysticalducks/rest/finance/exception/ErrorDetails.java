package com.mysticalducks.rest.finance.exception;

import java.util.Date;

public class ErrorDetails {
	
	Date timestamp;
	String message;
	String details;
	
	public ErrorDetails(Date timestamp, String message) {
		this.timestamp = timestamp;
		this.message = message;
	}
	
	public ErrorDetails(Date timestamp, String message, String details ) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
}
