package com.mysticalducks.rest.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public @ResponseBody List<User> findUsers() {
		return userService.findAll();
	}

	@GetMapping("/user/{id}")
	public @ResponseBody User findUser(@PathVariable int id){
		Optional<User> user = userService.findUser(id);
		if(user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
			
	}
}
