package com.mysticalducks.rest.finance.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;

public abstract class AbstractRepositoryTest {

	@Autowired
	protected ChatRepository chatRepository;

	@Autowired
	protected CategoryRepository categoryRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected IconRepository iconRepository;

	@Autowired
	protected TransactionRepository transactionRepository;

	protected User user;
	protected Icon icon;
	protected Category category;
	protected Chat chat;
	protected Transaction transaction;

	protected Category addNewCategory(String categoryName, User u) {
		return addNewCategory(categoryRepository, categoryName, u, icon);
	}

	protected Category addNewCategory(CategoryRepository repository, String categoryName, User u, Icon i) {
		Category newCategory = new Category(categoryName, u, i);
		repository.save(newCategory);

		return newCategory;
	}

	protected Category addNewCategory(CategoryRepository repository, String categoryName, User u) {
		return addNewCategory(repository, categoryName, u, icon);
	}

	protected Transaction addNewTransaction(TransactionRepository repository, String transactionName, User u, Chat c) {
		Transaction transaction = new Transaction(transactionName, 200.0, "note", category, u, c);
		repository.save(transaction);

		return transaction;
	}
	
	protected Transaction addNewTransaction(TransactionRepository repository, String transactionName, Chat c) {
		return addNewTransaction(repository, transactionName, user, c);
	}
	
	protected Transaction addNewTransaction(String transactionName, Chat c) {
		return addNewTransaction(transactionRepository, transactionName, user, c);
	}
	
	protected Transaction addNewTransaction(String transactionName, User u, Chat c) {
		return addNewTransaction(transactionRepository, transactionName, u, c);
	}

	protected Transaction addNewTransaction(String transactionName, User u) {
		return addNewTransaction(transactionRepository, transactionName, u, chat);
	}

	protected Transaction addNewTransaction(String transactionName) {
		return addNewTransaction(transactionRepository, transactionName, user, chat);
	}

}
