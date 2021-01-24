package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import com.mysticalducks.rest.finance.model.Chat;

public interface IChatService {
	
	List<Chat> findAllChats();
	
	Optional<Chat> findChat(int id);

}
