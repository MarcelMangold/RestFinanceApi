package com.mysticalducks.rest.finance.service;

import java.util.List;

import com.mysticalducks.rest.finance.model.User;

public interface IUserService {
	
	List<User> findAllUsers();
	
	User findUser(int id);

}
