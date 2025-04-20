package com.mysticalducks.rest.finance.service;

import java.time.LocalDateTime;
import java.util.List;

import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.ITransactionInformations;

public interface ITransactionService {

	List<Transaction> findAll();

	Transaction findById(int id);

	void deleteById(int id);

	List<ITransactionInformations> findAllByUser(User user);
	
	List<Transaction> findAllByUserId(int userId);

	Transaction save(String name, double amount, String note, int categoryId, int userId);
	
	Transaction replace(Transaction newTransaction);
	
	double totalAmount(int userId);
	
	double totalAmountByDate(int userId, LocalDateTime startDate, LocalDateTime endDate);
		
	double totalAmountByCurrentMonth(int userId);
	
}
