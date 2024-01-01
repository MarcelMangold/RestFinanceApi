package com.mysticalducks.rest.finance.exception;

public class IconNotFoundException extends RuntimeException { 
    public IconNotFoundException(String iconMessage) {
        super(iconMessage);
    }
    
    public IconNotFoundException(int id) {
		super("No data found for the icon with id '" + id + "'");
	}  
    
}

