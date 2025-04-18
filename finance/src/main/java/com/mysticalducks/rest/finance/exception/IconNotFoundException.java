package com.mysticalducks.rest.finance.exception;

public class IconNotFoundException extends RuntimeException { 
	private static final long serialVersionUID = 5495153203624364513L;

	public IconNotFoundException(String iconMessage) {
        super(iconMessage);
    }
    
    public IconNotFoundException(int id) {
		super("No data found for the icon with id '" + id + "'");
	}  
    
}

