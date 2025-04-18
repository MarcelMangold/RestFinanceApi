package com.mysticalducks.rest.finance.exception;

public class ChatNotFoundException extends RuntimeException { 
	private static final long serialVersionUID = -8068031606737125601L;

	public ChatNotFoundException(String iconMessage) {
        super(iconMessage);
    }
    
    public ChatNotFoundException(int id) {
		super("No data found for the chat with id '" + id + "'");
	}
}

