package com.mysticalducks.rest.finance.exception;

public class UserNotFoundException extends RuntimeException { 
    public UserNotFoundException(String userId) {
        super(userId);
    }
}

