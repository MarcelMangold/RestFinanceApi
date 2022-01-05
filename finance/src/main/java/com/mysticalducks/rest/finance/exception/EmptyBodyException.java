package com.mysticalducks.rest.finance.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyBodyException extends EntityNotFoundException {
	private static final long serialVersionUID = -9151144634415261762L;

	public EmptyBodyException() {
		super("The body for the endpoint must be not null");
	}
	
}
