package com.mysticalducks.rest.finance.exception;

import javax.persistence.EntityNotFoundException;

public class DataNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = -9151144634415261762L;

	public DataNotFoundException(String message) {
		super(message);
	}
	
	public DataNotFoundException(int id) {
		super("No data found for the id '" + id + "'");
	}
}
