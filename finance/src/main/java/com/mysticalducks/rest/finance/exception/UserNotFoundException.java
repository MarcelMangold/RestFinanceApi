package com.mysticalducks.rest.finance.exception;

public class UserNotFoundException extends Exception { 
    public UserNotFoundException(String userId, Throwable err) {
        super(userId,err);
    }
}

