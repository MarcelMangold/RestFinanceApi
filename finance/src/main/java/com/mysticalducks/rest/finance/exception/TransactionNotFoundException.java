package com.mysticalducks.rest.finance.exception;

public class TransactionNotFoundException extends RuntimeException { 
    public TransactionNotFoundException(String transactionMessage) {
        super(transactionMessage);
    }
    
    public TransactionNotFoundException(int id) {
		super("No data found for the transcation with id '" + id + "'");
	}  
    
}

