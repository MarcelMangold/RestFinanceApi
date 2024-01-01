package com.mysticalducks.rest.finance.exception;

public class UserNotFoundException extends RuntimeException { 
    public UserNotFoundException(String userId) {
        super(userId);
    }
    
    public UserNotFoundException(int id) {
		super("No data found for the user with id '" + id + "'");
	}  
    
}

