package com.mysticalducks.rest.finance.service;

import com.mysticalducks.rest.finance.model.PartyMember;
import com.mysticalducks.rest.finance.model.PartyMemberId;

public interface IPartyMemberService {
	
	Iterable<PartyMember> findAll();
	
	PartyMember findById(PartyMemberId id);
	
	PartyMember save(PartyMember partyMember);
	
	void deleteById(PartyMemberId id);

	void delete(PartyMember partyMember);

}
