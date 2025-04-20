package com.mysticalducks.rest.finance.exception;

public class PartyNotFoundException extends RuntimeException { 
	private static final long serialVersionUID = -8068031606737125601L;

	public PartyNotFoundException(String partyMessage) {
        super(partyMessage);
    }
    
    public PartyNotFoundException(int id) {
		super("No data found for the party with id '" + id + "'");
	}
}

