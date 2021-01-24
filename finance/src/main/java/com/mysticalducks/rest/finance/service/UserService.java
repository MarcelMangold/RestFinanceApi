package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.UserRepository;

@Service
public class UserService implements IUserService {
	
	 @Autowired
	 private UserRepository userRepository;


	public List<User> findAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}
	
	public Optional<User> findUser(int id) {
		return  userRepository.findById(id);
		
	}

}
