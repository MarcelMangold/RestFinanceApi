package com.mysticalducks.rest.finance.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.ITransactionInformations;
import com.mysticalducks.rest.finance.repository.TransactionRepository;

@Service
public class TransactionService implements ITransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private ChatService chatService;

	public List<Transaction> findAllTransactions() {
		List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
		return transactions;
	}

	public Optional<Transaction> findTransaction(int id) {
		return transactionRepository.findById(id);
	}

	public List<ITransactionInformations> findAllTransactionsByUserId(User user) {
			return (List<ITransactionInformations>) transactionRepository
					.getTransactionInformations(user);
	}

	public List<Transaction> findAllTransactionsByChatId(int chatId) {
		Optional<Chat> chat = chatService.findChat(chatId);

		if (chat.isPresent()) {
			List<Transaction> transactions = (List<Transaction>) transactionRepository
					.findAllCategoriesByChatId(chat.get());
			return transactions;
		} else {
			return null;
		}
	}

	public Transaction save(String name, double amount, Boolean isPositive, String note, Category category, User user,
			Chat chat) {
		return transactionRepository.save(new Transaction(name, amount, isPositive, note, category, user, chat));

	}

	public Transaction replace(int id, Transaction newTransaction) {
		return transactionRepository.findById(id).map(transaction -> {
			transaction.setAmount(newTransaction.getAmount());
			transaction.setCategory(newTransaction.getCategory());
			transaction.setChat(newTransaction.getChat());
			transaction.setName(newTransaction.getName());
			transaction.setNote(newTransaction.getNote());
			transaction.setPositive(newTransaction.isPositive());
			transaction.setCreatedAt(newTransaction.getCreatedAt());
			transaction.setUser(newTransaction.getUser());
			return transactionRepository.save(transaction);
		}).orElseGet(() -> {
			newTransaction.setId(id);
			return transactionRepository.save(newTransaction);
		});
	}

	public void delete(int id) {
		transactionRepository.deleteById(id);
	}

	public int totalAmount(User user, Chat chat) {
		return getTotalAmount(transactionRepository.getTransactionByUserAndChat(user, chat));
	}

	public int totalAmountByDate(User user, Chat chat, Date startDate, Date endDate) {
		return getTotalAmount(transactionRepository.getTransactionByUserAndChatAndPeriod(user, chat, startDate, endDate));
	}
	
	public int totalAmountByCurrentMonth(User user, Chat chat) {
		ZoneId zoneId = ZoneId.of ( "UTC" );
		LocalDate today = LocalDate.now ( zoneId );
		LocalDate firstOfCurrentMonth = today.with ( ChronoField.DAY_OF_MONTH , 1 );
		
		return getTotalAmount(transactionRepository.getTransactionByUserAndChatAndPeriod(user, chat, Date.from(firstOfCurrentMonth.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())));
	}

	private int getTotalAmount(List<Transaction> transactions) {
		int totalAmount = 0;
		for (Transaction transaction : transactions) {
			if (transaction.isPositive()) {
				totalAmount += transaction.getAmount();
			} else {
				totalAmount -= transaction.getAmount();
			}
		}
		return totalAmount;
	}


}
