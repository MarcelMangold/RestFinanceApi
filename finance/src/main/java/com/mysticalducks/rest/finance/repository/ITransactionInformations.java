package com.mysticalducks.rest.finance.repository;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;

public interface ITransactionInformations {

	int getId();
	String getName();
	Double getAmount();
	Boolean getIsPositive();
	String getNotice();
	ICategorie getCategorie();
	Timestamp getTimestamp();
}
