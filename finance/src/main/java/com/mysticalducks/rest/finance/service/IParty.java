package com.mysticalducks.rest.finance.service;

import com.mysticalducks.rest.finance.model.Party;

public interface IParty{
	
	Iterable<Party> findAll();
	
	Party findById(int id);
	
	Party findPartyByUserChatIdAndUserId(int chatId, int userId);
	
	Party save(String name, int financeInformationId);
	
	Party save(Party party);
	
	void deleteById(int id);

	void delete(Party party);

}
