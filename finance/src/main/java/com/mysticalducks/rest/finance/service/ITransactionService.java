package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import com.mysticalducks.rest.finance.model.Transaction;

public interface ITransactionService {
	
	List<Transaction> findAllTransactions();
	
	Optional<Transaction> findTransaction(int id);
	
	List<Transaction> findAllTransactionsByUserId(int userId);
	
	List<Transaction> findAllTransactionsByChatId(int chatId);

}
