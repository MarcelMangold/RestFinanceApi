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
import com.mysticalducks.rest.finance.repository.UserRepository;

@Service
public class TransactionService implements ITransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ChatService chatService;

	@Autowired
	private CategoryService categoryService;

	public List<Transaction> findAllTransactions() {
		List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
		return transactions;
	}

	public Optional<Transaction> findTransaction(int id) {
		return transactionRepository.findById(id);
	}

	public List<ITransactionInformations> findAllTransactionsByUserId(int userId) {
		Optional<User> user = userService.findUser(userId);
		if (user.isPresent()) {
			List<ITransactionInformations> transactions = (List<ITransactionInformations>) transactionRepository
					.getTransactionInformations(user.get());
			return transactions;
		} else {
			return null;
		}

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

	public Transaction save(String name, double amount, Boolean isPositive, String note, int categoryId, int userId,
			int chatId) {
		Optional<Chat> chat = chatService.findChat(chatId);
		Optional<User> user = userService.findUser(userId);
		Optional<Category> category = categoryService.findCategorie(categoryId);

		if (chat.isPresent()) {
			if (user.isPresent()) {
				if (category.isPresent()) {
					return transactionRepository.save(
							new Transaction(name, amount, isPositive, note, category.get(), user.get(), chat.get()));

				} else {
					return null;
				}

			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Transaction replace(int id, Transaction newTransaction) {
		return transactionRepository.findById(id).map(transaction -> {
			transaction.setAmount(newTransaction.getAmount());
			transaction.setCategory(newTransaction.getCategory());
			transaction.setChat(newTransaction.getChat());
			transaction.setName(newTransaction.getName());
			transaction.setNote(newTransaction.getNote());
			transaction.setPositive(newTransaction.isPositive());
			transaction.setTimestamp(newTransaction.getTimestamp());
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

	public int totalAmount(int userId, int chatId) {
		Optional<Chat> chat = chatService.findChat(chatId);
		Optional<User> user = userService.findUser(userId);

		if (existsUserAndChatExists(chat, user)) {
			return getTotalAmount(transactionRepository.getTransactionByUserAndChat(user.get(), chat.get()));
		}
		return 0;
	}

	public int totalAmountByDate(int userId, int chatId, Date startDate, Date endDate) {
		Optional<Chat> chat = chatService.findChat(chatId);
		Optional<User> user = userService.findUser(userId);
		
		if (existsUserAndChatExists(chat, user)) {
			return getTotalAmount(transactionRepository.getTransactionByUserAndChatAndPeriod(user.get(), chat.get(), startDate, endDate));
		}
		return 0;
	}
	
	public int totalAmountByCurrentMonth(int userId, int chatId) {
		Optional<Chat> chat = chatService.findChat(chatId);
		Optional<User> user = userService.findUser(userId);
		
		ZoneId zoneId = ZoneId.of ( "UTC" );
		LocalDate today = LocalDate.now ( zoneId );
		LocalDate firstOfCurrentMonth = today.with ( ChronoField.DAY_OF_MONTH , 1 );
		
		if (existsUserAndChatExists(chat, user)) {
			return getTotalAmount(transactionRepository.getTransactionByUserAndChatAndPeriod(user.get(), chat.get(), Date.from(firstOfCurrentMonth.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())));
		}
		return 0;
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

	private boolean existsUserAndChatExists(Optional<Chat> chat, Optional<User> user) {
		if (chat.isPresent()) {
			if (user.isPresent()) {
				return true;
			}
		}
		return false;
	}

}
