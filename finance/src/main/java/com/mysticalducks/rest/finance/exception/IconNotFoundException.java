package com.mysticalducks.rest.finance.exception;

public class IconNotFoundException extends RuntimeException { 
    public IconNotFoundException(String iconMessage) {
        super(iconMessage);
    }
}

