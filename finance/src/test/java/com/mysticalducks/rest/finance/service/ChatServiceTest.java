package com.mysticalducks.rest.finance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mysticalducks.rest.finance.exception.ChatNotFoundException;
import com.mysticalducks.rest.finance.exception.DataNotFoundException;
import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.repository.ChatRepository;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

	@Mock
	ChatRepository chatRepository;

	@InjectMocks
	ChatService service;
	
	Chat chat;
	
	@BeforeEach
	void setUp() {
		this.chat = new Chat(1);
				
	}


	@Test
	void findById() {
		when(chatRepository.findById(1)).thenReturn(Optional.of(chat));

		Chat foundChat = service.findById(1);

		assertThat(foundChat).isNotNull();

		verify(chatRepository).findById(1);
		
		ChatNotFoundException thrown = assertThrows(ChatNotFoundException.class, () -> service.findById(2),
				"No data found for the chat with id 2");

		assertTrue(thrown.getMessage().contains("2"));

	}

	@Test
	void findAll() {
		List<Chat> chats = new ArrayList<>();
		chats.add(chat);
		
		when(chatRepository.findAll()).thenReturn(chats);
		
		List<Chat> foundChats = service.findAll();
		
		verify(chatRepository).findAll();
		
		assertThat(foundChats).hasSize(1);
	}
	
	@Test
	void save() {
		when(chatRepository.save(any(Chat.class))).thenReturn(chat);

		Chat savedChat = service.save(1);

		verify(chatRepository).save(any(Chat.class));

		assertThat(savedChat).isNotNull();
	}

}
