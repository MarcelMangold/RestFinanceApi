package com.mysticalducks.rest.finance.service;

import com.mysticalducks.rest.finance.model.FinanceInformation;

public interface IFinanceInformation {
	
	FinanceInformation findById(int id);
	
	void deleteById(int id);

	void delete(FinanceInformation financeInformation);
	
	FinanceInformation save(Double budget);
	
	FinanceInformation save(FinanceInformation information);

}
