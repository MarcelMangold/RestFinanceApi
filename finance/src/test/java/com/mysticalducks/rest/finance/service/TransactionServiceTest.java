package com.mysticalducks.rest.finance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.mysticalducks.rest.finance.exception.DataNotFoundException;
import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TransactionServiceTest {
	
	@InjectMocks
	TransactionService service;

	@Mock
	TransactionRepository transactionRepository;
	
	@Mock
	ChatService chatService;

	Transaction transaction;
	User user;
	Icon icon;
	Category category;
	Chat chat;

	@BeforeEach
	void setUp() {
		this.chat = new Chat(1);
		this.user = new User("User", "password", 0);
		this.category = new Category("Category", user , new Icon("Icon"));
		this.transaction = new Transaction("transaction", 200.0, false, "note", category, user, chat);
	}
	
	@Test
	void findAll() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		transactions.add( new Transaction("transaction", 200.0, false, "note", category, user, chat));

		when(transactionRepository.findAll()).thenReturn(transactions);

		List<Transaction> foundTransactions = service.findAll();

		verify(transactionRepository).findAll();

		assertThat(foundTransactions).hasSize(2);
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
	
	/*
	 * @Test void findAllByUser() { List<ITransactionInformations> transactions =
	 * new ArrayList<>(); transactions.add(new ITransactionInformat);
	 * transactions.add( new Transaction("transaction", 200.0, false, "note",
	 * category, user, chat));
	 * 
	 * when(transactionRepository.getTransactionInformations(user)).thenReturn(
	 * transactions);
	 * 
	 * Transaction foundTransaction = service.findById(1);
	 * 
	 * assertThat(foundTransaction).isNotNull();
	 * 
	 * verify(transactionRepository).findById(1);
	 * 
	 * DataNotFoundException thrown = assertThrows(DataNotFoundException.class, ()
	 * -> service.findById(2), "No data found for the id 2");
	 * 
	 * assertTrue(thrown.getMessage().contains("2"));
	 * 
	 * }
	 */
	
	@Test
	void findAllByChatId() {
		List<Transaction> transactions = new ArrayList<>();
		Transaction newTransaction = new Transaction("transaction", 200.0, false, "note", category, user, chat);
		transactions.add(transaction);
		transactions.add(newTransaction);

		when(transactionRepository.findAllCategoriesByChatId(chat)).thenReturn(transactions);
		when(chatService.findById(1)).thenReturn(chat);

		List<Transaction> foundTransactions = service.findAllByChatId(chat.getId());

		verify(transactionRepository).findAllCategoriesByChatId(chat);

		assertThat(foundTransactions).hasSize(2);
		assertEquals(foundTransactions.get(0), transaction);
		assertEquals(foundTransactions.get(1), newTransaction);
	}
	
	@Test
	void save() {
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
		
		Transaction savedTransaction = service.save("newTransaction", 200.0, false, "note", category, user, chat);

		verify(transactionRepository).save(any(Transaction.class));

		assertThat(savedTransaction).isNotNull();
	}

	
	@Test
	void deleteById() {
		service.deleteById(5);

		verify(transactionRepository).deleteById(5);
	}

	

	
	

	@Test
	void replace() {
		when(transactionRepository.findById(0)).thenReturn(Optional.of(transaction));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		Transaction replacedTransaction = service.replace(1,transaction);

		verify(transactionRepository, times(1)).findById(0);
		verify(transactionRepository, times(1)).save(any(Transaction.class));

		assertThat(replacedTransaction).isNotNull();
	}
	
	@Test
	void replaceNew() {
		when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
		
		Transaction newTransaction = new Transaction("newTransaction", 200.0, false, "note", category, user, chat);
		Transaction replacedTransaction = service.replace(1,newTransaction);

		verify(transactionRepository, times(1)).findById(1);
		verify(transactionRepository, times(1)).save(any(Transaction.class));
		
		assertThat(replacedTransaction).isNotNull();
		
	}
}
