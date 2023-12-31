package com.mysticalducks.rest.finance.exception;

public class ChatNotFoundException extends RuntimeException { 
    public ChatNotFoundException(String iconMessage) {
        super(iconMessage);
    }
}

