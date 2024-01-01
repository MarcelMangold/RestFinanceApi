package com.mysticalducks.rest.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.exception.ChatNotFoundException;
import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.repository.ChatRepository;

@Service
public class ChatService implements IChatService {
	
	 @Autowired
	 private ChatRepository chatRepository;


	public List<Chat> findAll() {
		List<Chat> chats = (List<Chat>) chatRepository.findAll();
		return chats;
	}
	
	public Chat findById(int id) {
		return chatRepository.findById(id)
				.orElseThrow(() -> new ChatNotFoundException(id));
		
	}

	public Chat save(int id) {
		return chatRepository.save(new Chat(id));
	}

}
