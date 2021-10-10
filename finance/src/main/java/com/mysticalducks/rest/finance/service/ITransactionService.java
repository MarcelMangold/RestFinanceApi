package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.ITransactionInformations;

public interface ITransactionService {
	
	List<Transaction> findAllTransactions();
	
	Optional<Transaction> findTransaction(int id);
	
	List<ITransactionInformations> findAllTransactionsByUserId(User user);
	
	List<Transaction> findAllTransactionsByChatId(int chatId);

}
