package com.mysticalducks.rest.finance.service;

import java.util.List;

import com.mysticalducks.rest.finance.model.Chat;

public interface IChatService {
	
	List<Chat> findAll();
	
	Chat findById(int id);
	
	Chat save(int id);

}
