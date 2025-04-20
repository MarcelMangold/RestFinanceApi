package com.mysticalducks.rest.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.service.FinanceInformationService;

@Controller
public class FinanceInformationController extends AbstractController {
	
	@Autowired
	private FinanceInformationService financeService;


	@GetMapping("/financeInformation/{id}")
	@ResponseBody
	public FinanceInformation findFinanceInformation(@PathVariable int id){
		return financeService.findById(id);
	}
	
	@PostMapping(value="/financeInformation", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public FinanceInformation newFinanceInformation(@RequestBody FinanceInformation information) {
		checkIfParameterIsEmpty(information.getBudget());
		return financeService.save(information);
	}
	
	@PostMapping(value = "/financeInformation/budget", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public FinanceInformation newFinanceInformationWithBudget(@RequestBody Double budget) {
	    checkIfParameterIsEmpty(budget);
	    return financeService.save(budget);
	}
	
	
	@DeleteMapping("/financeInformation/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable int id) {
		financeService.deleteById(id);
	}

}
