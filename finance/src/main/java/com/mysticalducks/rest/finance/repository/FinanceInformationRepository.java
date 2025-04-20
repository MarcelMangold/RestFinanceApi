package com.mysticalducks.rest.finance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.FinanceInformation;

@Repository
public interface FinanceInformationRepository extends CrudRepository<FinanceInformation, Integer> {
	
}