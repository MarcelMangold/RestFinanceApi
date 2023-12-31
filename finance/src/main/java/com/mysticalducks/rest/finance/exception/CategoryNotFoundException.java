package com.mysticalducks.rest.finance.exception;

public class CategoryNotFoundException extends RuntimeException { 
    public CategoryNotFoundException(String iconMessage) {
        super(iconMessage);
    }
}

