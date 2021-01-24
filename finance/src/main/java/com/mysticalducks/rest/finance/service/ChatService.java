package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.repository.ChatRepository;

@Service
public class ChatService implements IChatService {
	
	 @Autowired
	 private ChatRepository chatRepository;


	public List<Chat> findAllChats() {
		List<Chat> chats = (List<Chat>) chatRepository.findAll();
		return chats;
	}
	
	public Optional<Chat> findChat(int id) {
		return chatRepository.findById(id);
		
	}

}
