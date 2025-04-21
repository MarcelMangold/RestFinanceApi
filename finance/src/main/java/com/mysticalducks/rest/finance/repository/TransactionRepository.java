package com.mysticalducks.rest.finance.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	@Query("select t from Transaction t where t.party=:party")
	List<Transaction> findAllByPartyId(@Param("party") Party party);

	@Query("select t from Transaction t where t.party=:party")
	List<ITransactionInformations> getInformations(@Param("party") Party party);
	
	
	@Query(value = "select t from Transaction t where t.party=:party and createdAt BETWEEN :startDate AND :endDate")
	List<Transaction> getByPartyPeriod(@Param("party") Party party, LocalDateTime startDate, LocalDateTime endDate);

}
