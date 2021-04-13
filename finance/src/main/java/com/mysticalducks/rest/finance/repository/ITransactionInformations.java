package com.mysticalducks.rest.finance.repository;

import java.util.Date;



public interface ITransactionInformations {

	int getId();
	String getName();
	Double getAmount();
	Boolean getIsPositive();
	String getNote();
	ICategory getCategory();
	Date getTimestamp();
	
}
