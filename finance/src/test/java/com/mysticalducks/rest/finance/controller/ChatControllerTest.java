package com.mysticalducks.rest.finance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.service.ChatService;

@WebMvcTest(value = ChatController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
public class ChatControllerTest {


	@Autowired
	private MockMvc mvc;

	@MockBean
	private ChatService chatService;

	@Test
	public void postChat() throws Exception {
		when(this.chatService.save(any(Integer.class))).thenReturn(new Chat(0));

		this.mvc.perform(post("/chat/0")
				.secure(false).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
			    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("id").value(0));

		this.mvc.perform(post("/chat/").secure(false)).andExpect(status().isNotFound());
		
	}

	@Test
	public void getChat() throws Exception {
		when(this.chatService.findById(0)).thenReturn(new Chat(0));

		this.mvc.perform(get("/chat/0").secure(false)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("id").value(0));

		this.mvc.perform(get("/icon/").secure(false)).andExpect(status().isNotFound());
	}
	
	@Test
	public void getChats() throws Exception {
		ArrayList<Chat> chats = new ArrayList<Chat>();
		chats.add(new Chat(0));
		chats.add(new Chat(1));
		when(this.chatService.findAll()).thenReturn(chats);

		this.mvc.perform(get("/chats").secure(false)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$[0].id").value(0))
				.andExpect(jsonPath("$[1].id").value("1"));


		this.mvc.perform(get("/icons/-1").secure(false)).andExpect(status().isNotFound());
	}


}
