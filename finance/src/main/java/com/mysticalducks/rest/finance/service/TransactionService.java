package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			List<ITransactionInformations> transactions = (List<ITransactionInformations>) transactionRepository.getTransactionInformations(user.get());
			return transactions;
		} else {
			return null;
		}

	}
	
	

	public List<Transaction> findAllTransactionsByChatId(int chatId) {
		Optional<Chat> chat = chatService.findChat(chatId);

		if (chat.isPresent()) {
			List<Transaction> transactions = (List<Transaction>) transactionRepository.findAllCategoriesByChatId(chat.get());
			return transactions;
		} else {
			return null;
		}
	}

}
