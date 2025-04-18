package com.mysticalducks.rest.finance.exception;

public class TransactionNotFoundException extends RuntimeException { 

	private static final long serialVersionUID = 8124694649162605003L;

	public TransactionNotFoundException(String transactionMessage) {
        super(transactionMessage);
    }
    
    public TransactionNotFoundException(int id) {
		super("No data found for the transcation with id '" + id + "'");
	}  
    
}

