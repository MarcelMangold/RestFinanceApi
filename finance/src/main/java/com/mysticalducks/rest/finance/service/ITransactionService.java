package com.mysticalducks.rest.finance.service;

import java.util.Date;
import java.util.List;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.ITransactionInformations;

public interface ITransactionService {

	List<Transaction> findAll();

	Transaction findById(int id);

	void deleteById(int id);

	List<ITransactionInformations> findAllByUser(User user);

	List<Transaction> findAllByChatId(int chatId);
	
	List<Transaction> findAllByUserId(int userId);

	Transaction save(String name, double amount, Boolean isPositive, String note, Category category, User user,
			Chat chat);
	
	Transaction replace(Transaction newTransaction);
	
	double totalAmount(User user, Chat chat);
	
	double totalAmountByDate(User user, Chat chat, Date startDate, Date endDate);
		
	double totalAmountByCurrentMonth(User user, Chat chat);
	
}
