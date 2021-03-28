package com.mysticalducks.rest.finance.repository;

import java.sql.Timestamp;


public interface ITransactionInformations {

	int getId();
	String getName();
	Double getAmount();
	Boolean getIsPositive();
	String getNotice();
	ICategorie getCategorie();
	Timestamp getTimestamp();
	
}
