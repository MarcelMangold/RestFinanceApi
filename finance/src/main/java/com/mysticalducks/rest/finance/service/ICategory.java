package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.repository.ITransactionInformations;

public interface ICategory {
	
	List<Transaction> findAllTransactions();
	
	Optional<Transaction> findTransaction(int id);
	
	List<ITransactionInformations> findAllTransactionsByUserId(int userId);
	
	List<Transaction> findAllTransactionsByChatId(int chatId);

}
