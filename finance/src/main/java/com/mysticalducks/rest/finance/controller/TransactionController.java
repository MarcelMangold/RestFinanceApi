package com.mysticalducks.rest.finance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.repository.ITransactionInformations;
import com.mysticalducks.rest.finance.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

//	@GetMapping("/transactions")
//	@ResponseBody
//	public List<Transaction> findCategories() {
//		return transactionService.findAllTransactions();
//	}

	@GetMapping("/transaction/{id}")
	@ResponseBody
	public Transaction findCategory(@PathVariable int id){
		Optional<Transaction> transactions = transactionService.findTransaction(id);
		if(transactions.isPresent()) {
			return transactions.get();
		} else {
			return null;
		}
	}
	
	@GetMapping("/transactions")
	@ResponseBody
	public List<ITransactionInformations> findAllCategoriesByUserId(@RequestParam(name = "user_id") int userId){
		List<ITransactionInformations> transactions = transactionService.findAllTransactionsByUserId(userId);
		return transactions;
	}
	
//	@GetMapping("/categories")
//	@ResponseBody 
//	public List<Categorie> findAllCategoriesByChatId(@RequestParam(name = "chatId") int chatId){
//		List<Categorie> categories = categorieService.findAllCategoriesByChatId(chatId);
//		return categories;
//	}
}
