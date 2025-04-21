package com.mysticalducks.rest.finance.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;

public abstract class AbstractRepositoryTest {

	@Autowired
	protected CategoryRepository categoryRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected IconRepository iconRepository;

	@Autowired
	protected TransactionRepository transactionRepository;
	
	@Autowired
	protected FinanceInformationRepository financeInformationRepository;
	
	@Autowired PartyRepository partyRepository;

	protected User user;
	protected Icon icon;
	protected Category category;
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

	protected Transaction addNewTransaction(TransactionRepository repository, String transactionName, User u) {
		Transaction transaction = new Transaction(transactionName, 200.0, "note", category, u);
		repository.save(transaction);

		return transaction;
	}
	
	protected Transaction addNewTransaction(TransactionRepository repository, String transactionName) {
		return addNewTransaction(repository, transactionName, user);
	}
	
	protected Transaction addNewTransaction(String transactionName, User u) {
		return addNewTransaction(transactionRepository, transactionName, u);
	}

	protected Transaction addNewTransaction(String transactionName) {
		return addNewTransaction(transactionRepository, transactionName, user);
	}
	
	
	protected User addNewUser(String name) {
		User user = new User(name, "email","password", 0);
		userRepository.save(user);
		
		return user;
	}
	
	protected User addNewUser() {
		return addNewUser("name");
	}
	
	protected Party addNewParty(FinanceInformation financeInformation) {
		return addNewParty("Party", financeInformation);
	}
	
	
	protected Party addNewParty(String partyName, FinanceInformation financeInformation) {
		var party = new Party("Party", financeInformation);
		partyRepository.save(party);
		
		return party;
	}
	
	protected FinanceInformation addNewFinanceInformation() {
		FinanceInformation financeInformation = new FinanceInformation(500.0);
		financeInformationRepository.save(financeInformation);
		return financeInformation;
	}

}
