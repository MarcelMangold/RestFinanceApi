package com.mysticalducks.rest.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.exception.PartyNotFoundException;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.repository.PartyRepository;

@Service
public class PartyService implements IParty {

	@Autowired
	private PartyRepository partyRepository;
	
	@Autowired
	private FinanceInformationService financeInformationService;

	public Iterable<Party> findAll() {
		return partyRepository.findAll();
	}

	public Party findById(int id) {
		return partyRepository.findById(id).orElseThrow(() -> new PartyNotFoundException(id));
	}

	public Party save(String name, int financeId) {
		var financenInformation = financeInformationService.findById(financeId);
		return partyRepository.save(new Party(name, financenInformation));
	}

	public Party save(Party party) {
		return partyRepository.save(party);
	}

	public void deleteById(int id) {
		partyRepository.deleteById(id);
	}

	public void delete(Party party) {
		partyRepository.delete(party);
	}

}
