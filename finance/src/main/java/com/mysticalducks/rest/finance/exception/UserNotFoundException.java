package com.mysticalducks.rest.finance.exception;

public class UserNotFoundException extends RuntimeException { 
	private static final long serialVersionUID = 7257945264431026483L;

	public UserNotFoundException(String userId) {
        super(userId);
    }
    
    public UserNotFoundException(int id) {
		super("No data found for the user with id '" + id + "'");
	}  
    
}

