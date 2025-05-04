package com.mysticalducks.rest.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.exception.UserNotFoundException;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.UserRepository;

@Service
public class UserService implements IUserService {
	
	 @Autowired
	 private UserRepository userRepository;


	public List<User> findAll() {
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}
	
	public User findById(int id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
	
	public User save(String name, String email, String password, int language ) {
		return save(new User(name, email, password, language));
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}

	public User findByTelegramUserId(int telegramUserId) {
	    return userRepository.findByTelegramUserId(telegramUserId)
	            .orElseThrow(() -> new UserNotFoundException("No user found with Telegram ID: " + telegramUserId));
	}



}
