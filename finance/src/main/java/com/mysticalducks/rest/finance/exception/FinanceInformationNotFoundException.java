package com.mysticalducks.rest.finance.exception;

public class FinanceInformationNotFoundException extends RuntimeException { 

	private static final long serialVersionUID = 8020020045889095578L;

	public FinanceInformationNotFoundException(String financeInformationMessage) {
        super(financeInformationMessage);
    }
    
    public FinanceInformationNotFoundException(int id) {
		super("No data found for the finance information with id '" + id + "'");
	}
}

