package com.mysticalducks.rest.finance.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.PartyMember;
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
	
	@Autowired 
	protected PartyRepository partyRepository;
	
	@Autowired
	protected PartyMemberRepository partyMemberRepository;

	protected User user;
	protected Party party;
	protected Icon icon;
	protected Category category;
	protected Transaction transaction;
	protected FinanceInformation financeInformation;

	protected Category addNewCategory(String categoryName, Party party) {
		return addNewCategory(categoryRepository, categoryName, party, icon);
	}

	protected Category addNewCategory(CategoryRepository repository, String categoryName, Party party, Icon i) {
		Category newCategory = new Category(categoryName, party, i);
		repository.save(newCategory);

		return newCategory;
	}

	protected Category addNewCategory(CategoryRepository repository, String categoryName, Party party) {
		return addNewCategory(repository, categoryName, party, icon);
	}

	protected Transaction addNewTransaction(TransactionRepository repository, String transactionName, Party party) {
		Transaction transaction = new Transaction(transactionName, 200.0, "note", category, party);
		repository.save(transaction);

		return transaction;
	}
	
	protected Transaction addNewTransaction(TransactionRepository repository, String transactionName) {
		return addNewTransaction(repository, transactionName, party);
	}
	
	protected Transaction addNewTransaction(String transactionName, Party party) {
		return addNewTransaction(transactionRepository, transactionName, party);
	}

	protected Transaction addNewTransaction(String transactionName) {
		return addNewTransaction(transactionRepository, transactionName, party);
	}
	
	
	protected User addNewUser(String name) {
		User user = new User(name, "email","password", 0);
		userRepository.save(user);
		
		return user;
	}
	
	protected User addNewUser() {
		return addNewUser("name");
	}
	
	protected Party addNewParty() {
		return addNewParty("Party");
	}
	
	protected Party addNewParty(String name) {
		return addNewParty(name, addNewFinanceInformation());
	}
	
	protected Party addNewParty(FinanceInformation financeInformation) {
		return addNewParty("Party", financeInformation);
	}
	
	protected Party addNewParty(String partyName, FinanceInformation financeInformation) {
		var party = new Party("Party", financeInformation);
		partyRepository.save(party);
		
		return party;
	}
	
	protected PartyMember addNewPartyMember(User user, Party party) {
		var partyMember = new PartyMember(user, party);
		partyMemberRepository.save(partyMember);
		
		return partyMember;
	}
	
	protected FinanceInformation addNewFinanceInformation() {
		FinanceInformation financeInformation = new FinanceInformation(500.0);
		financeInformationRepository.save(financeInformation);
		return financeInformation;
	}

}
