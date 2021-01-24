package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import com.mysticalducks.rest.finance.model.User;

public interface IUserService {
	
	List<User> findAllUsers();
	
	Optional<User> findUser(int id);

}
