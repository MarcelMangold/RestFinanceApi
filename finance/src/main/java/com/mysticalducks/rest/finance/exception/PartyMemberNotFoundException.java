package com.mysticalducks.rest.finance.exception;

import com.mysticalducks.rest.finance.model.PartyMemberId;

public class PartyMemberNotFoundException extends RuntimeException { 

	private static final long serialVersionUID = 3166811744859475930L;

	public PartyMemberNotFoundException(String partyMemberMessage) {
        super(partyMemberMessage);
    }
    
    public PartyMemberNotFoundException(PartyMemberId id) {
		super("No data found for the party member with party id '" + id.getPartyId() + "' and user id '" + id.getUserId() + "'");
	}
}

