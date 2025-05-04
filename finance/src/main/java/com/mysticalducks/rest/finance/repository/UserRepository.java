package com.mysticalducks.rest.finance.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.User;


@Repository
public interface UserRepository  extends CrudRepository<User, Integer>  {
	
	Optional<User> findByTelegramUserId(int telegramUserId);
	
}
