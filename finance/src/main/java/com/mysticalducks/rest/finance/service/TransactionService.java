package com.mysticalducks.rest.finance.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.exception.CategoryNotFoundException;
import com.mysticalducks.rest.finance.exception.DataNotFoundException;
import com.mysticalducks.rest.finance.exception.PartyNotFoundException;
import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.repository.ITransactionInformations;
import com.mysticalducks.rest.finance.repository.TransactionRepository;

@Service
public class TransactionService implements ITransactionService {
	
	

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private PartyService partyService;
	
	@Autowired
	private CategoryService categoryService;
	

	public List<Transaction> findAll() {
		List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
		return transactions;
	}

	public Transaction findById(int id) {
		return transactionRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
	}
	
	public List<ITransactionInformations> findAllByParty(Party party) {
			return (List<ITransactionInformations>) transactionRepository
					.getInformations(party);
	}

	public List<Transaction> getAllTransactionsByPartyId(int partyId) {
	    Party party = partyService.findById(partyId);
	    if (party == null) {
	        throw new PartyNotFoundException("Party not found with id " + partyId);
	    }
	    return transactionRepository.findAllByPartyId(party);
	}
	
	public List<Transaction> findAllByPartyId(int partyId) {
	    Party party = partyService.findById(partyId);
	    if (party == null) {
	        throw new PartyNotFoundException("Party not found with id " + partyId);
	    }
		List<Transaction> transactions = (List<Transaction>) transactionRepository
				.findAllByPartyId(party);
		return transactions;
	}

	public Transaction save(String name, double amount, String note, int categoryId, int partyId) {
		
		Party party = partyService.findById(partyId);
		Category category = categoryService.findById(categoryId);
		
		if(party == null) 
		  throw new PartyNotFoundException("Party not found with id " + partyId);
		 
		if(category == null) 
			  throw new CategoryNotFoundException("Category not found with id " + categoryId);
		 
		return transactionRepository.save(new Transaction(name, amount, note, category, party));

	}

	public Transaction replace(Transaction newTransaction) {
		return transactionRepository.findById(newTransaction.getId()).map(transaction -> {
			transaction.setAmount(newTransaction.getAmount());
			transaction.setCategory(newTransaction.getCategory());
			transaction.setName(newTransaction.getName());
			transaction.setNote(newTransaction.getNote());
			transaction.setCreatedAt(newTransaction.getCreatedAt());
			transaction.setParty(newTransaction.getParty());
			return transactionRepository.save(transaction);
		}).orElseGet(() -> {
			return transactionRepository.save(newTransaction);
		});
	}
	
	public void deleteById(int id) {
		transactionRepository.deleteById(id);
	}


	public double totalAmount(int partyId) {
	    Party party = partyService.findById(partyId);

	    if (party == null) 
	        throw new PartyNotFoundException("Party not found with id " + partyId);
		
		return getTotalAmount(transactionRepository.findAllByPartyId(party));
	}

	public double totalAmountByDate(int partyId, LocalDateTime startDate, LocalDateTime endDate) {
	    Party party = partyService.findById(partyId);

	    if (party == null) 
	        throw new PartyNotFoundException("Party not found with id " + partyId);
		
		return getTotalAmount(transactionRepository.getByPartyPeriod(party, startDate, endDate));
	}
	
	public double totalAmountByCurrentMonth(int partyId) {
	    Party party = partyService.findById(partyId);

	    if (party == null) 
	        throw new PartyNotFoundException("Party not found with id " + partyId);
			 
		ZoneId zoneId = ZoneId.of ( "UTC" );
		LocalDateTime today = LocalDateTime.now ( zoneId );
		LocalDateTime firstOfCurrentMonth = today.with ( ChronoField.DAY_OF_MONTH , 1 );
		
		return getTotalAmount(transactionRepository.getByPartyPeriod(party, firstOfCurrentMonth, today));
	}

	private double getTotalAmount(List<Transaction> transactions) {
		return transactions
				.stream()
				.mapToDouble(Transaction::getAmount)
				.sum();
	}



}
