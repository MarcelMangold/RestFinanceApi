package com.mysticalducks.rest.finance.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.mysticalducks.rest.finance.exception.DataNotFoundException;
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
	
	@Autowired
	private UserService userService;

	public List<Transaction> findAll() {
		List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
		return transactions;
	}

	public Transaction findById(int id) {
		return transactionRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
	}
	
	public List<ITransactionInformations> findAllByUser(User user) {
			return (List<ITransactionInformations>) transactionRepository
					.getInformations(user);
	}

	public List<Transaction> findAllByChatId(int chatId) {
		Chat chat = chatService.findById(chatId);
		List<Transaction> transactions = (List<Transaction>) transactionRepository
				.findAllByChatId(chat);
		return transactions;
	}
	
	public List<Transaction> findAllByUserId(int userId) {
		User user = userService.findById(userId);
		List<Transaction> transactions = (List<Transaction>) transactionRepository
				.findAllByUserId(user);
		return transactions;
	}

	public Transaction save(String name, double amount, String note, Category category, User user,
			Chat chat) {
		return transactionRepository.save(new Transaction(name, amount, note, category, user, chat));

	}

	public Transaction replace(Transaction newTransaction) {
		return transactionRepository.findById(newTransaction.getId()).map(transaction -> {
			transaction.setAmount(newTransaction.getAmount());
			transaction.setCategory(newTransaction.getCategory());
			transaction.setChat(newTransaction.getChat());
			transaction.setName(newTransaction.getName());
			transaction.setNote(newTransaction.getNote());
			transaction.setCreatedAt(newTransaction.getCreatedAt());
			transaction.setUser(newTransaction.getUser());
			return transactionRepository.save(transaction);
		}).orElseGet(() -> {
			return transactionRepository.save(newTransaction);
		});
	}
	
	public void deleteById(int id) {
		transactionRepository.deleteById(id);
	}


	public double totalAmount(User user, Chat chat) {
		return getTotalAmount(transactionRepository.getByUserAndChat(user, chat));
	}

	public double totalAmountByDate(User user, Chat chat, Date startDate, Date endDate) {
		return getTotalAmount(transactionRepository.getByUserAndChatAndPeriod(user, chat, startDate, endDate));
	}
	
	public double totalAmountByCurrentMonth(User user, Chat chat) {
		ZoneId zoneId = ZoneId.of ( "UTC" );
		LocalDate today = LocalDate.now ( zoneId );
		LocalDate firstOfCurrentMonth = today.with ( ChronoField.DAY_OF_MONTH , 1 );
		
		return getTotalAmount(transactionRepository.getByUserAndChatAndPeriod(user, chat, Date.from(firstOfCurrentMonth.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())));
	}

	private double getTotalAmount(List<Transaction> transactions) {
		return transactions
				.stream()
				.mapToDouble(Transaction::getAmount)
				.sum();
	}



}
