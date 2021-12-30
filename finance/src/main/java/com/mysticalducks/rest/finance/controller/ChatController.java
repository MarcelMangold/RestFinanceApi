package com.mysticalducks.rest.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.service.ChatService;

@Controller
//@V1APIController
public class ChatController {

	@Autowired
	private ChatService chatService;

	@GetMapping("/chats")
	public @ResponseBody List<Chat> findUsers() {
		return chatService.findAll();
	}

	@GetMapping("/chat/{id}")
	public @ResponseBody Chat findUser(@PathVariable int id){
		return chatService.findById(id);
	}
}
