package com.mysticalducks.rest.finance.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	@Query("select t from Transaction t where t.user=:user")
	List<Transaction> findAllByUserId(@Param("user") User user);

	@Query("select t from Transaction t where t.chat=:chat")
	List<Transaction> findAllByChatId(@Param("chat") Chat chat);

	@Query("select t from Transaction t where t.user=:user")
	List<ITransactionInformations> getInformations(@Param("user") User user);
	
	@Query("select t from Transaction t where t.user=:user and t.chat=:chat")
	List<Transaction> getByUserAndChat(@Param("user") User user, @Param("chat") Chat chat);
	
	@Query(value = "select t from Transaction t where t.user=:user and t.chat=:chat and createdAt BETWEEN :startDate AND :endDate")
	List<Transaction> getByUserAndChatAndPeriod(@Param("user") User user, @Param("chat") Chat chat, Date startDate, Date endDate);

}
