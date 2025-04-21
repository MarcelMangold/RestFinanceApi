package com.mysticalducks.rest.finance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.mysticalducks.rest.finance.exception.CategoryNotFoundException;
import com.mysticalducks.rest.finance.exception.DataNotFoundException;
import com.mysticalducks.rest.finance.exception.PartyNotFoundException;
import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TransactionServiceTest {

	@InjectMocks
	TransactionService service;

	@Mock
	TransactionRepository transactionRepository;
	
	@Mock
	PartyService partyService;

	@Mock
	CategoryService categoryService;
	
	Transaction transaction;
	Party party;
	Icon icon;
	Category category;
	LocalDateTime startDate;
	LocalDateTime endDate;

	@BeforeEach
	void setUp() {
		this.party = new Party("Party", new FinanceInformation());
		this.category = new Category("Category", party, new Icon("Icon"));
		this.transaction = new Transaction("transaction", -200.0, "note", category, party);
		this.startDate = LocalDateTime.now();
		this.endDate = LocalDateTime.now();
	}

	@Test
	void findAll() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		transactions.add(new Transaction("transaction", -200.0, "note", category, party));

		when(transactionRepository.findAll()).thenReturn(transactions);

		List<Transaction> foundTransactions = service.findAll();

		verify(transactionRepository).findAll();

		assertThat(foundTransactions).hasSize(2);
	}
	
	@Test
	void getAllTransactionsByPartyId() {
	    List<Transaction> transactions = new ArrayList<>();
	    transactions.add(transaction);
	    transactions.add(new Transaction("transaction2", 300.0, "note2", category, party));

	    when(partyService.findById(party.getId())).thenReturn(party);
	    when(transactionRepository.findAllByPartyId(party)).thenReturn(transactions);

	    List<Transaction> foundTransactions = service.getAllTransactionsByPartyId(party.getId());

	    verify(partyService, times(1)).findById(party.getId());
	    verify(transactionRepository, times(1)).findAllByPartyId(party);

	    assertThat(foundTransactions).hasSize(2);
	    assertEquals(foundTransactions.get(0), transaction);
	    assertEquals(foundTransactions.get(1).getName(), "transaction2");
	}
	
	
	@Test
	void getAllTransactionsByPartyId_partyNotFound() {
	    int invalidPartyId = -1;

	    when(partyService.findById(invalidPartyId)).thenReturn(null);

	    PartyNotFoundException exception = assertThrows(PartyNotFoundException.class, 
	        () -> service.getAllTransactionsByPartyId(invalidPartyId));

	    assertEquals("Party not found with id " + invalidPartyId, exception.getMessage());
	    verify(partyService, times(1)).findById(invalidPartyId);
	    verify(transactionRepository, times(0)).findAllByPartyId(any());
	}

	@Test
	void findById() {
		when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

		Transaction foundTransaction = service.findById(1);

		assertThat(foundTransaction).isNotNull();

		verify(transactionRepository).findById(1);

		DataNotFoundException thrown = assertThrows(DataNotFoundException.class, () -> service.findById(2),
				"No data found for the id 2");

		assertTrue(thrown.getMessage().contains("2"));

	}

	
	@Test
	void findAllByPartyId() {
		List<Transaction> transactions = new ArrayList<>();
		Transaction newTransaction = new Transaction("transaction", -200.0, "note", category, party);
		transactions.add(transaction);
		transactions.add(newTransaction);

		when(transactionRepository.findAllByPartyId(party)).thenReturn(transactions);
		when(partyService.findById(party.getId())).thenReturn(party);

		List<Transaction> foundTransactions = service.findAllByPartyId(party.getId());

		verify(transactionRepository).findAllByPartyId(party);

		assertThat(foundTransactions).hasSize(2);
		assertEquals(foundTransactions.get(0), transaction);
		assertEquals(foundTransactions.get(1), newTransaction);
	}


	@Test
	void save() {
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		when(partyService.findById(party.getId())).thenReturn(party);
		when(categoryService.findById(category.getId())).thenReturn(category);
		
		Transaction savedTransaction = service.save("newTransaction", -200.0, "note", category.getId(), party.getId());

		verify(transactionRepository).save(any(Transaction.class));

		assertThat(savedTransaction).isNotNull();
	}
	
	@Test
	void save_partyIsNull() {
		assertThrows(PartyNotFoundException.class, () -> {
			when(partyService.findById(party.getId())).thenReturn(null);

			service.save("newTransaction", -200.0, "note", category.getId(), party.getId());
			}
		);
	}


	@Test
	void save_categorieIsNull() {
		assertThrows(CategoryNotFoundException.class, () -> {
			when(partyService.findById(party.getId())).thenReturn(party);
			when(categoryService.findById(category.getId())).thenReturn(null);
			
			service.save("newTransaction", -200.0, "note", category.getId(), party.getId());
			}
		);
	}
	
	@Test
	void replace() {
		when(transactionRepository.findById(0)).thenReturn(Optional.of(transaction));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		Transaction replacedTransaction = service.replace(transaction);

		verify(transactionRepository, times(1)).findById(0);
		verify(transactionRepository, times(1)).save(any(Transaction.class));

		assertThat(replacedTransaction).isNotNull();
	}

	@Test
	void replaceNew() {
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		Transaction newTransaction = new Transaction("newTransaction", -200.0, "note", category, party);
		newTransaction.setId(1);
		Transaction replacedTransaction = service.replace(newTransaction);

		verify(transactionRepository, times(1)).findById(1);
		verify(transactionRepository, times(1)).save(newTransaction);

		assertThat(replacedTransaction).isNotNull();

	}

	@Test
	void deleteById() {
		service.deleteById(0);
		verify(transactionRepository).deleteById(0);
	}

	@Test
	void totalAmount() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		transactions.add(new Transaction("transaction", 250.0, "note", category, party));

		when(transactionRepository.findAllByPartyId(party)).thenReturn(transactions);
		when(partyService.findById(party.getId())).thenReturn(party);
		
		double amount = service.totalAmount(party.getId());
		verify(transactionRepository).findAllByPartyId(party);

		assertEquals(getAmount(transactions), amount);

	}
	

	@Test
	void totalAmountByDate() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		transactions.add(new Transaction("transaction", 250.0, "note", category, party));

		when(transactionRepository.getByPartyPeriod(party, startDate, endDate))
				.thenReturn(transactions);
		when(partyService.findById(party.getId())).thenReturn(party);

		double amount = service.totalAmountByDate(party.getId(), startDate, endDate);
		verify(transactionRepository).getByPartyPeriod(party, startDate, endDate);

		assertEquals(getAmount(transactions), amount);

	}
	
	@Test
	void totalAmountByDate_partyIsNull() {
		assertThrows(PartyNotFoundException.class, () -> {
			when(partyService.findById(party.getId())).thenReturn(null);

			service.totalAmountByDate(party.getId(), startDate, endDate);;
			}
		);
	}


	@Test
	void totalAmountByCurrentMonth() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		transactions.add(new Transaction("transaction", 250.0, "note", category, party));

		when(transactionRepository.getByPartyPeriod(eq(party), any(LocalDateTime.class),  any(LocalDateTime.class)))
				.thenReturn(transactions);
		when(partyService.findById(party.getId())).thenReturn(party);

		double amount = service.totalAmountByCurrentMonth(party.getId());
		verify(transactionRepository).getByPartyPeriod(eq(party), any(LocalDateTime.class),  any(LocalDateTime.class));

		assertEquals(getAmount(transactions), amount);

	}
	
	@Test
	void totalAmountByCurrentMonth_partyIsNull() {
		assertThrows(PartyNotFoundException.class, () -> {
			when(partyService.findById(party.getId())).thenReturn(null);

			service.totalAmountByCurrentMonth(party.getId());
			}
		);
	}

	private Double getAmount(List<Transaction> transactions) {
		return transactions.stream().mapToDouble(Transaction::getAmount).sum();
	}
}
