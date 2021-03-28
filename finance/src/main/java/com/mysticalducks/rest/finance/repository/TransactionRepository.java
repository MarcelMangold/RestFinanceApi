package com.mysticalducks.rest.finance.repository;

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
	List<Transaction> findAllCategoriesByUserId(@Param("user") User user);

	@Query("select t from Transaction t where t.chat=:chat")
	List<Transaction> findAllCategoriesByChatId(@Param("chat") Chat chat);

	@Query("select t from Transaction t where t.user=:user")
	List<ITransactionInformations> getTransactionInformations(@Param("user") User user);

}
