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
public class ChatController {

	@Autowired
	private ChatService chatService;

	@GetMapping("/chats")
	public @ResponseBody List<User> findUsers() {
		return chatService.findAll();
	}

	@GetMapping("/chat/{id}")
	public @ResponseBody User findUser(@PathVariable int id){
		Optional<User> user = chatService.findUser(id);
		if(user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
			
	}
}
