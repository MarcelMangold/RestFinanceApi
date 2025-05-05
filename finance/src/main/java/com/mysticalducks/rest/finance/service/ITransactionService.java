package com.mysticalducks.rest.finance.service;

import java.time.LocalDateTime;
import java.util.List;

import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.repository.ITransactionInformations;

public interface ITransactionService {

	List<Transaction> findAll();

	Transaction findById(int id);

	void deleteById(int id);

	List<ITransactionInformations> findAllByParty(Party party);
	
	List<Transaction> findAllByPartyId(int partyId);

	Transaction save(String name, double amount, String note,  Double latitude, Double longitude, int categoryId, int partyId);
	
	Transaction replace(Transaction newTransaction);
	
	double totalAmount(int partyId);
	
	double totalAmountByDate(int partyId, LocalDateTime startDate, LocalDateTime endDate);
		
	double totalAmountByCurrentMonth(int partyId);
	
}
