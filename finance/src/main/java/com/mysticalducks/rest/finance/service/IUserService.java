package com.mysticalducks.rest.finance.service;

import java.util.List;

import com.mysticalducks.rest.finance.model.User;

public interface IUserService {
	
	List<User> findAll();
	
	User findById(int id);
	
	void deleteById(int id);

	void delete(User user);
}
