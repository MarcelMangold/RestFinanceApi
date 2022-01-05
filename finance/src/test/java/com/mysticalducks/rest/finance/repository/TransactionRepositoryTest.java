package com.mysticalducks.rest.finance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransactionRepositoryTest extends AbstractRepositoryTest {

	@BeforeEach
	void setUp() {
		this.chat = new Chat();
		this.user = new User("User", "password", 0);
		this.icon = new Icon("Icon");
		this.category = new Category("Category", user, icon);
		this.transaction = new Transaction("Transaction", 200.0, false, "note", category, user, chat);

		chatRepository.save(chat);
		userRepository.save(user);
		iconRepository.save(icon);
		categoryRepository.save(category);
		transactionRepository.save(transaction);

	}


	@Test
	public void findAllByUserId() throws Exception {
		User newUser = new User("newUser", "password", 0);
		userRepository.save(newUser);
		Transaction transactionUser = addNewTransaction("TransactionUser", user);
		addNewTransaction("TransactionNewUser", newUser);
		addNewTransaction("TransactionNewUser", newUser);

		List<Transaction> queryResult = (List<Transaction>) transactionRepository.findAllByUserId(user);

		assertFalse(queryResult.isEmpty());
		assertEquals(2, queryResult.size());
		assertEquals(transaction, queryResult.get(0));
		assertEquals(transactionUser, queryResult.get(1));
	}
	
	@Test
	public void findAllByChatId() throws Exception {
		Chat newChat = new Chat();
		chatRepository.save(newChat);
		
		User newUser = new User("newUser", "password", 0);
		userRepository.save(newUser);
		
		Transaction transactionUser = addNewTransaction("TransactionUser", chat);
		addNewTransaction("TransactionNewUserNewChat", newUser, newChat);
		Transaction transactionNewUserOldChat = addNewTransaction("TransactionNewUser", newUser);

		List<Transaction> queryResult = (List<Transaction>) transactionRepository.findAllByChatId(chat);

		assertFalse(queryResult.isEmpty());
		assertEquals(3, queryResult.size());
		assertEquals(transaction, queryResult.get(0));
		assertEquals(transactionUser, queryResult.get(1));
		assertEquals(transactionNewUserOldChat, queryResult.get(2));
	}
	
	//@TODO add logic
	@Test
	public void ITransactionInformations() throws Exception {
	}

	@Test
	public void getByUserAndChat() throws Exception {
		Chat newChat = new Chat();
		chatRepository.save(newChat);
		
		User newUser = new User("newUser", "password", 0);
		userRepository.save(newUser);
		
		Transaction transactionUser = addNewTransaction("TransactionUser", chat);
		addNewTransaction("TransactionNewUserNewChat", newUser, newChat);
		addNewTransaction("TransactionNewUser", newUser);

		List<Transaction> queryResult = (List<Transaction>) transactionRepository.getByUserAndChat(user,chat);

		assertFalse(queryResult.isEmpty());
		assertEquals(2, queryResult.size());
		assertEquals(transaction, queryResult.get(0));
		assertEquals(transactionUser, queryResult.get(1));
	}
	
	@Test
	public void getByUserAndChatAndPeriod() throws Exception {
		Date startDate = Date.from(Instant.parse("2020-01-01T00:00:00.000Z"));
		Date endDate = Date.from(Instant.parse("2020-02-01T00:00:00.000Z"));
		
		Chat newChat = new Chat();
		chatRepository.save(newChat);
		
		User newUser = new User("newUser", "password", 0);
		userRepository.save(newUser);
		
		// Case 1: no transactions are in the time period
		Transaction beforePeriodTransaction = addNewTransaction("beforePeriodTransaction", user, chat);
		beforePeriodTransaction.setCreatedAt( Date.from(Instant.parse("2019-12-31T23:59:00.000Z")));
		Transaction afterPeriodTransaction = addNewTransaction("afterPeriodTransaction", user, chat);
		afterPeriodTransaction.setCreatedAt( Date.from(Instant.parse("2021-02-01T00:00:00.001Z")));
		Transaction equalPeriodEndTime = addNewTransaction("equalPeriodEndTime", user, chat);
		equalPeriodEndTime.setCreatedAt( Date.from(Instant.parse("2021-02-01T00:00:00.000Z")));

		transactionRepository.save(equalPeriodEndTime);
		transactionRepository.save(afterPeriodTransaction);
		transactionRepository.save(beforePeriodTransaction);

		
		List<Transaction> queryResultWithoutTransaction = (List<Transaction>) transactionRepository.getByUserAndChatAndPeriod(user,chat, startDate, endDate);

		assertTrue(queryResultWithoutTransaction.isEmpty());
		
		// Case 2: transactions are in the time period
		Transaction equalPeriodStartTime = addNewTransaction("equalPeriodStartTime", user, chat);
		equalPeriodStartTime.setCreatedAt( Date.from(Instant.parse("2020-01-01T00:00:00.000Z")));
		Transaction inPeriod = addNewTransaction("inPeriod", user, chat);
		inPeriod.setCreatedAt( Date.from(Instant.parse("2020-01-05T00:00:00.000Z")));
		Transaction inPeriodWithOtherUser = addNewTransaction("inPeriodWithOtherUser", newUser, chat);
		inPeriodWithOtherUser.setCreatedAt( Date.from(Instant.parse("2020-01-05T00:00:00.000Z")));
		Transaction inPeriodWithOtherChat = addNewTransaction("inPeriodWithOtherChat", user, newChat);
		inPeriodWithOtherChat.setCreatedAt( Date.from(Instant.parse("2020-01-05T00:00:00.000Z")));
		Transaction inPeriodWithOtherUserAndChat = addNewTransaction("inPeriodWithOtherUserAndChat", newUser, newChat);
		inPeriodWithOtherUserAndChat.setCreatedAt( Date.from(Instant.parse("2020-01-05T00:00:00.000Z")));
		
		transactionRepository.save(inPeriod);
		transactionRepository.save(equalPeriodStartTime);
		transactionRepository.save(inPeriodWithOtherUser);
		transactionRepository.save(inPeriodWithOtherChat);
		transactionRepository.save(inPeriodWithOtherUserAndChat);

		List<Transaction> queryResultInPeriod = (List<Transaction>) transactionRepository.getByUserAndChatAndPeriod(user, chat, startDate, endDate);
			
		assertEquals(2, queryResultInPeriod.size());
		assertEquals(equalPeriodStartTime, queryResultInPeriod.get(0));
		assertEquals(inPeriod, queryResultInPeriod.get(1));
		
		
		List<Transaction> queryResultOtherChat = (List<Transaction>) transactionRepository.getByUserAndChatAndPeriod(user, newChat, startDate, endDate);
		
		assertEquals(1, queryResultOtherChat.size());
		assertEquals(inPeriodWithOtherChat, queryResultOtherChat.get(0));
		
		List<Transaction> queryResultOtherUser = (List<Transaction>) transactionRepository.getByUserAndChatAndPeriod(newUser, chat, startDate, endDate);
		
		assertEquals(1, queryResultOtherUser.size());
		assertEquals(inPeriodWithOtherUser, queryResultOtherUser.get(0));
		
		List<Transaction> queryResultOtherUserAndChat = (List<Transaction>) transactionRepository.getByUserAndChatAndPeriod(newUser, newChat, startDate, endDate);
		
		assertEquals(1, queryResultOtherUserAndChat.size());
		assertEquals(inPeriodWithOtherUserAndChat, queryResultOtherUserAndChat.get(0));
	}
	
	
}
