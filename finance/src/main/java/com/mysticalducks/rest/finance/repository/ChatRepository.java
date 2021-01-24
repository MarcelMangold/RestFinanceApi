package com.mysticalducks.rest.finance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.Chat;


@Repository
public interface ChatRepository  extends CrudRepository<Chat, Integer>  {
	
}
