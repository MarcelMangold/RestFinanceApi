package com.mysticalducks.rest.finance.controller;

import java.time.LocalDateTime;
import java.util.List;

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
	
	@GetMapping("/transactions/{partyId}")
	@ResponseBody
	public ResponseEntity<List<Transaction>> getAllTransactionsByPartyId(@PathVariable int partyId) {
	    return new ResponseEntity<>(transactionService.getAllTransactionsByPartyId(partyId), HttpStatus.OK);
	}

	@PostMapping("/transaction")
	@ResponseBody
	Transaction newTransaction(@RequestParam String name, @RequestParam Double amount, @RequestParam String note, @RequestParam int categoryId, @RequestParam int partyId) {
		return transactionService.save(name, amount, note, categoryId, partyId);
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
	
	@GetMapping("/totalAmount/{partyId}") 
	@ResponseBody
	ResponseEntity<Double> totalAmount(@PathVariable int partyId){
		return new ResponseEntity<Double>(transactionService.totalAmount(partyId), HttpStatus.OK);
	}
	
	@GetMapping("/totalAmountByPeriod/{partyId}") 
	@ResponseBody
	ResponseEntity<Double> totalAmountByPeriod(@PathVariable int partyId, @RequestParam @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startDate, @RequestParam @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endDate){
		return new ResponseEntity<Double>(transactionService.totalAmountByDate(partyId, startDate, endDate), HttpStatus.OK);
	}
	
	@GetMapping("/totalAmountByCurrentMonth/{partyId}") 
	@ResponseBody
	ResponseEntity<Double> totalAmountByCurrentMonth(@PathVariable int partyId){
		return new ResponseEntity<Double>(transactionService.totalAmountByCurrentMonth(partyId), HttpStatus.OK);
	}
}
