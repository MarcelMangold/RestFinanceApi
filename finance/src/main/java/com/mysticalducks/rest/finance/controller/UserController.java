package com.mysticalducks.rest.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.service.UserService;
import com.mysticalducks.rest.finance.util.EmptyJsonResponse;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	@ResponseBody
	public List<User> findUsers() {
		return userService.findAllUsers();
	}

	@GetMapping("/user/{id}")
	@ResponseBody
	public ResponseEntity<User> findUser(@PathVariable int id){
		Optional<User> user = userService.findUser(id);
		
		if(user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}
		
		return new ResponseEntity(new EmptyJsonResponse() , HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{id}")
	void delete(@PathVariable int id) {
		userService.delete(id);
	}
}
