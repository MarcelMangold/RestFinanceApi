package com.mysticalducks.rest.finance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.Transaction;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransactionRepositoryTest extends AbstractRepositoryTest {

	@BeforeEach
	void setUp() {
		this.financeInformation = new FinanceInformation();
		this.party = new Party("Party", financeInformation);
		this.icon = new Icon("Icon");
		this.category = new Category("Category", party, icon);
		this.transaction = new Transaction("Transaction", 200.0, "note", category, party);

		financeInformationRepository.save(financeInformation);
		partyRepository.save(party);
		iconRepository.save(icon);
		categoryRepository.save(category);
		transactionRepository.save(transaction);

	}


	@Test
	public void findAllByPartyId() throws Exception {
		Party newParty = addNewParty("newParty");
		partyRepository.save(newParty);
		Transaction transactionParty = addNewTransaction("TransactionParty", party);
		addNewTransaction("TransactionNewParty", newParty);
		addNewTransaction("TransactionNewParty", newParty);

		List<Transaction> queryResult = (List<Transaction>) transactionRepository.findAllByPartyId(party);

		assertFalse(queryResult.isEmpty());
		assertEquals(2, queryResult.size());
		assertEquals(transaction, queryResult.get(0));
		assertEquals(transactionParty, queryResult.get(1));
	}
	
	
	//@TODO add logic
	@Test
	public void ITransactionInformations() throws Exception {
	}

	
	@Test
	public void getByPartyPeriod() throws Exception {
		LocalDateTime startDate = Instant.parse("2020-01-01T00:00:00.000Z").atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime endDate = Instant.parse("2020-02-01T00:00:00.000Z").atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		
		Party newParty = addNewParty("newParty");
		partyRepository.save(newParty);
		
		// Case 1: no transactions are in the time period
		Transaction beforePeriodTransaction = addNewTransaction("beforePeriodTransaction", party);
		beforePeriodTransaction.setCreatedAt( Instant.parse("2019-12-31T23:59:00.000Z").atZone(ZoneId.systemDefault()).toLocalDateTime());
		Transaction afterPeriodTransaction = addNewTransaction("afterPeriodTransaction", party);
		afterPeriodTransaction.setCreatedAt( Instant.parse("2021-02-01T00:00:00.001Z").atZone(ZoneId.systemDefault()).toLocalDateTime());
		Transaction equalPeriodEndTime = addNewTransaction("equalPeriodEndTime", party);
		equalPeriodEndTime.setCreatedAt( Instant.parse("2021-02-01T00:00:00.000Z").atZone(ZoneId.systemDefault()).toLocalDateTime());

		transactionRepository.save(equalPeriodEndTime);
		transactionRepository.save(afterPeriodTransaction);
		transactionRepository.save(beforePeriodTransaction);

		
		List<Transaction> queryResultWithoutTransaction = (List<Transaction>) transactionRepository.getByPartyPeriod(party, startDate, endDate);

		assertTrue(queryResultWithoutTransaction.isEmpty());
		
		// Case 2: transactions are in the time period
		Transaction equalPeriodStartTime = addNewTransaction("equalPeriodStartTime", party);
		equalPeriodStartTime.setCreatedAt( Instant.parse("2020-01-01T00:00:00.000Z").atZone(ZoneId.systemDefault()).toLocalDateTime());
		Transaction inPeriod = addNewTransaction("inPeriod", party);
		inPeriod.setCreatedAt( Instant.parse("2020-01-05T00:00:00.000Z").atZone(ZoneId.systemDefault()).toLocalDateTime());
		Transaction inPeriodWithOtherParty = addNewTransaction("inPeriodWithOtherParty", newParty);
		inPeriodWithOtherParty.setCreatedAt( Instant.parse("2020-01-05T00:00:00.000Z").atZone(ZoneId.systemDefault()).toLocalDateTime());
		Transaction inPeriodWithOtherChat = addNewTransaction("inPeriodWithOtherChat", party);
		inPeriodWithOtherChat.setCreatedAt( Instant.parse("2020-01-05T00:00:00.000Z").atZone(ZoneId.systemDefault()).toLocalDateTime());
		Transaction inPeriodWithOtherPartyAndChat = addNewTransaction("inPeriodWithOtherPartyAndChat", newParty);
		inPeriodWithOtherPartyAndChat.setCreatedAt( Instant.parse("2020-01-05T00:00:00.000Z").atZone(ZoneId.systemDefault()).toLocalDateTime());
		
		transactionRepository.save(inPeriod);
		transactionRepository.save(equalPeriodStartTime);
		transactionRepository.save(inPeriodWithOtherParty);
		transactionRepository.save(inPeriodWithOtherChat);
		transactionRepository.save(inPeriodWithOtherPartyAndChat);

		List<Transaction> queryResultInPeriod = (List<Transaction>) transactionRepository.getByPartyPeriod(party, startDate, endDate);
			
		assertEquals(3, queryResultInPeriod.size());
		assertEquals(equalPeriodStartTime, queryResultInPeriod.get(0));
		assertEquals(inPeriod, queryResultInPeriod.get(1));
		
		
		List<Transaction> queryResultOtherParty = (List<Transaction>) transactionRepository.getByPartyPeriod(newParty, startDate, endDate);
		
		assertEquals(2, queryResultOtherParty.size());
		assertEquals(inPeriodWithOtherParty, queryResultOtherParty.get(0));
		
	}
	
	
}
