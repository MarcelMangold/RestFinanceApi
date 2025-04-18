package com.mysticalducks.rest.finance.exception;

public class CategoryNotFoundException extends RuntimeException { 

	private static final long serialVersionUID = 3273156020021374668L;

	public CategoryNotFoundException(String iconMessage) {
        super(iconMessage);
    }
    
    public CategoryNotFoundException(int id) {
		super("No data found for the categorie with id '" + id + "'");
	}
}

