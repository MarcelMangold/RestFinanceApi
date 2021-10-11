package com.mysticalducks.rest.exception;

public class UserNotFoundException extends Exception { 
    public UserNotFoundException(String userId, Throwable err) {
        super(userId,err);
    }
}

