package com.mysticalducks.rest.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.service.ChatService;

@Controller
//@V1APIController
public class ChatController {

	@Autowired
	private ChatService chatService;

	@GetMapping("/chats")
	@ResponseBody
	public List<Chat> findUsers() {
		return chatService.findAll();
	}

	@GetMapping("/chat/{id}")
	public @ResponseBody Chat findUser(@PathVariable int id){
		return chatService.findById(id);
	}
	
	@PostMapping("/chat/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Chat newUser(@PathVariable int id){
		return chatService.save(id);
	}
}
