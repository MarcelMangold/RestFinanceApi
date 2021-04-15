package com.mysticalducks.rest.finance.service;

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
		return transactionRepository.findById(id)
		.map(transaction -> {
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

}
