package com.mysticalducks.rest.finance.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.exception.CategoryNotFoundException;
import com.mysticalducks.rest.finance.exception.ChatNotFoundException;
import com.mysticalducks.rest.finance.exception.DataNotFoundException;
import com.mysticalducks.rest.finance.exception.UserNotFoundException;
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
	
	@Autowired
	private CategoryService categoryService;
	

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

	public List<Transaction> getAllTransactionsByUserId(int userId) {
	    User user = userService.findById(userId);
	    if (user == null) {
	        throw new UserNotFoundException("User not found with id " + userId);
	    }
	    return transactionRepository.findAllByUserId(user);
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

	public Transaction save(String name, double amount, String note, int categoryId, int userId,
			int chatId) {
		
		User user = userService.findById(userId);
		Category category = categoryService.findById(categoryId);
		Chat chat = chatService.findById(chatId);
		
		if(user == null) 
		  throw new UserNotFoundException("User not found with id " + userId);
		 
		if(chat == null) 
			  throw new ChatNotFoundException("User not found with id " + userId);
			 
		if(category == null) 
			  throw new CategoryNotFoundException("User not found with id " + userId);
		 
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


	public double totalAmount(int userId, int chatId) {
		User user = userService.findById(userId);
		Chat chat = chatService.findById(chatId);
		
		if(user == null) 
			  throw new UserNotFoundException("User not found with id " + userId);
			 
		if(chat == null) 
			  throw new ChatNotFoundException("User not found with id " + userId);
				
		
		return getTotalAmount(transactionRepository.getByUserAndChat(user, chat));
	}

	public double totalAmountByDate(int userId, int chatId, LocalDateTime startDate, LocalDateTime endDate) {
		User user = userService.findById(userId);
		Chat chat = chatService.findById(chatId);
		
		if(user == null) 
			  throw new UserNotFoundException("User not found with id " + userId);
			 
		if(chat == null) 
			  throw new ChatNotFoundException("User not found with id " + userId);
		
		
		return getTotalAmount(transactionRepository.getByUserAndChatAndPeriod(user, chat, startDate, endDate));
	}
	
	public double totalAmountByCurrentMonth(int userId, int chatId) {
		User user = userService.findById(userId);
		Chat chat = chatService.findById(chatId);
		
		if(user == null) 
			  throw new UserNotFoundException("User not found with id " + userId);
			 
		if(chat == null) 
			  throw new ChatNotFoundException("User not found with id " + userId);
		
		ZoneId zoneId = ZoneId.of ( "UTC" );
		LocalDateTime today = LocalDateTime.now ( zoneId );
		LocalDateTime firstOfCurrentMonth = today.with ( ChronoField.DAY_OF_MONTH , 1 );
		
		return getTotalAmount(transactionRepository.getByUserAndChatAndPeriod(user, chat, firstOfCurrentMonth, today));
	}

	private double getTotalAmount(List<Transaction> transactions) {
		return transactions
				.stream()
				.mapToDouble(Transaction::getAmount)
				.sum();
	}



}
