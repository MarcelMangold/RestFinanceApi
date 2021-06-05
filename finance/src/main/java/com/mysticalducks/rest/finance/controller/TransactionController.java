package com.mysticalducks.rest.finance.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.repository.ITransactionInformations;
import com.mysticalducks.rest.finance.service.TransactionService;

import io.swagger.annotations.ApiOperation;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

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
	
	@PostMapping("/transaction")
	@ResponseBody
	Transaction newTransaction(@RequestParam String name, @RequestParam Double amount, @RequestParam Boolean isPositive, @RequestParam String note, @RequestParam int categoryId, @RequestParam int userId, @RequestParam int chatId) {
		return transactionService.save(name, amount, isPositive, note, categoryId, userId, chatId);
	}
	
	@PutMapping("/transaction")
	@ResponseBody
	Transaction replaceTransaction(@RequestBody Transaction transaction, @RequestParam int transactionId) {
		return transactionService.replace(transactionId, transaction);
	}
	
	@DeleteMapping("/transaction/{id}") 
	void deleteTransaction(@PathVariable int id){
		transactionService.delete(id);
		
	}
	
	@GetMapping("/totalAmount/{userId}") 
	@ResponseBody
	@ApiOperation(value = "Return the total amount for user in chat", 
	notes = "Return the total amount for user in chat",
	response = Integer.class)
	int totalAmount(@PathVariable int userId, @RequestParam int chatId){
		return transactionService.totalAmount(userId, chatId);
	}
	
	@GetMapping("/totalAmountByPeriod/{userId}") 
	@ResponseBody
	@ApiOperation(value = "Return the total amount for user in chat in the specific period", 
	notes = "Return the total amount for user in chat",
	response = Integer.class)
	int totalAmountByPeriod(@PathVariable int userId, @RequestParam int chatId, @RequestParam @DateTimeFormat(iso=ISO.DATE) Date startDate, @RequestParam @DateTimeFormat(iso=ISO.DATE) Date endDate){
		return transactionService.totalAmountByDate(userId, chatId, startDate, endDate);
	}
	
	@GetMapping("/totalAmountByCurrentMonth/{userId}") 
	@ResponseBody
	@ApiOperation(value = "Return the total amount for user in chat in the current month", 
	notes = "Return the total amount for user in chat",
	response = Integer.class)
	int totalAmountByCurrentMonth(@PathVariable int userId, @RequestParam int chatId){
		return transactionService.totalAmountByCurrentMonth(userId, chatId);
	}
}
