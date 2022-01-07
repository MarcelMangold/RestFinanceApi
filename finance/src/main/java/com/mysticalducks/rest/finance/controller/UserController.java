package com.mysticalducks.rest.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.service.UserService;

@Controller
//@V1APIController
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	@ResponseBody
	public List<User> findUsers() {
		return userService.findAll();
	}

	@GetMapping("/user/{id}")
	@ResponseBody
	public User findUser(@PathVariable int id){
		return userService.findById(id);
	}
	
	@PostMapping(value="/user", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public User newIcon(@RequestBody User user) {
		checkIfParameterIsEmpty(user.getName());
		checkIfParameterIsEmpty(user.getPassword());
		return userService.save(user);
	}
	
	@DeleteMapping("/user/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable int id) {
		userService.deleteById(id);
	}
}
