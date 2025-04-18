package com.mysticalducks.rest.finance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.service.UserService;

@WebMvcTest(value = UserController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	private UserService userService;
	
	 @Autowired
	 private ObjectMapper mapper;
	
	@Test
	public void getUsers() throws Exception {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User(0, "User1", "email","password", 0));
		users.add(new User(1, "User2", "email","password", 0));
		when(this.userService.findAll()).thenReturn(users);
		
		this.mvc.perform(get("/users").secure(false)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$[0].id").value(0)).andExpect(jsonPath("$[0].name").value("User1"))
			.andExpect(jsonPath("$[1].id").value("1")).andExpect(jsonPath("$[1].name").value("User2"));

		this.mvc.perform(get("/users/-1").secure(false)).andExpect(status().isNotFound());
	}

	@Test
	public void getUser() throws Exception {
		given(this.userService.findById(1)).willReturn(new User(1, "name", "email","password", 4));
		
		this.mvc.perform(get("/user/1").secure(false)).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("id").value(1))
			.andExpect(jsonPath("name").value("name"))
			.andExpect(jsonPath("password").value("password"))
			.andExpect(jsonPath("language").value(4));

		this.mvc.perform(get("/user").secure(false)).andExpect(status().isMethodNotAllowed());
		
		this.mvc.perform(get("/user/-1").secure(false)).andExpect(status().isOk());

	}
	
	
	@Test
	public void postUser() throws Exception {
		User user = new User("name", "email","password", 4);
		when(this.userService.save(any(String.class), any(String.class), any(String.class), any(Integer.class))).thenReturn(user);
		when(this.userService.save(any(User.class))).thenReturn(user);
		this.mvc.perform(
				post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(user))
				.secure(false)
			)
			.andExpect(status().isCreated())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("name").value("name"))
			.andExpect(jsonPath("password").value("password"))
			.andExpect(jsonPath("language").value(4));


		this.mvc.perform(post("/user").secure(false)).andExpect(status().isBadRequest());
		
		this.mvc.perform(post("/user").content("").secure(false).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).
			andExpect(status().isBadRequest());
	}
	@Test
	public void deleteRequest() throws Exception {
		this.mvc.perform(delete("/user/100").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").secure(false)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		this.mvc.perform(delete("/user").secure(false)).andExpect(status().isMethodNotAllowed());
	}
	

}
