package com.mysticalducks.rest.finance.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.service.TransactionService;

@Controller
//@V1APIController
public class TransactionController{

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/transaction/{id}")
	@ResponseBody
	public ResponseEntity<Transaction> findTransaction(@PathVariable int id){
		return new ResponseEntity<Transaction>(transactionService.findById(id), HttpStatus.OK);
	}
	
	
	@PostMapping("/transaction")
	@ResponseBody
	Transaction newTransaction(@RequestParam String name, @RequestParam Double amount, @RequestParam String note, @RequestParam int categoryId, @RequestParam int userId, @RequestParam int chatId) {
		return transactionService.save(name, amount, note, categoryId, userId, chatId);
	}
	
	@PutMapping("/transaction")
	@ResponseBody
	Transaction replaceTransaction(@RequestBody Transaction transaction) {
		return transactionService.replace(transaction);
	}
	
	@DeleteMapping("/transaction/{id}") 
	void deleteTransaction(@PathVariable int id){
		transactionService.deleteById(id);
		
	}
	
	@GetMapping("/totalAmount/{userId}") 
	@ResponseBody
	ResponseEntity<Double> totalAmount(@PathVariable int userId, @RequestParam int chatId){
		return new ResponseEntity<Double>(transactionService.totalAmount(userId, chatId), HttpStatus.OK);
	}
	
	@GetMapping("/totalAmountByPeriod/{userId}") 
	@ResponseBody
	ResponseEntity<Double> totalAmountByPeriod(@PathVariable int userId, @RequestParam int chatId, @RequestParam @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startDate, @RequestParam @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endDate){
		return new ResponseEntity<Double>(transactionService.totalAmountByDate(userId, chatId, startDate, endDate), HttpStatus.OK);
	}
	
	@GetMapping("/totalAmountByCurrentMonth/{userId}") 
	@ResponseBody
	ResponseEntity<Double> totalAmountByCurrentMonth(@PathVariable int userId, @RequestParam int chatId){
		return new ResponseEntity<Double>(transactionService.totalAmountByCurrentMonth(userId, chatId), HttpStatus.OK);
	}
}
