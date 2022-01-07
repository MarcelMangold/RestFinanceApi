package com.mysticalducks.rest.finance.controller;

import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.service.IconService;

@WebMvcTest(value = IconController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
public class IconControllerTest {


	@Autowired
	private MockMvc mvc;

	@MockBean
	private IconService iconService;

	@Test
	public void postIcon() throws Exception {
		when(this.iconService.save(any(String.class))).thenReturn(new Icon(0, "newIcon"));

		this.mvc.perform(post("/icon/").content("newIcon").secure(false).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
			    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("id").value(0)).andExpect(jsonPath("name").value("newIcon"));

		this.mvc.perform(post("/icon/").secure(false)).andExpect(status().isBadRequest());
		
		this.mvc.perform(post("/icon/").content("").secure(false).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).
			andExpect(status().isBadRequest());
	}

	@Test
	public void getIcon() throws Exception {
		when(this.iconService.findById(1)).thenReturn(new Icon("test"));

		this.mvc.perform(get("/icon/1").secure(false)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("id").value(0))
				.andExpect(jsonPath("name").value("test"));

		this.mvc.perform(get("/icon/").secure(false)).andExpect(status().isMethodNotAllowed());

		this.mvc.perform(get("/icon/-1").secure(false)).andExpect(status().isOk());
	}

	@Test
	public void getIcons() throws Exception {
		ArrayList<Icon> icons = new ArrayList<Icon>();
		icons.add(new Icon(0, "Icon1"));
		icons.add(new Icon(1, "Icon2"));
		when(this.iconService.findAll()).thenReturn(icons);

		this.mvc.perform(get("/icons").secure(false)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$[0].id").value(0)).andExpect(jsonPath("$[0].name").value("Icon1"))
				.andExpect(jsonPath("$[1].id").value("1")).andExpect(jsonPath("$[1].name").value("Icon2"));


		this.mvc.perform(get("/icons/-1").secure(false)).andExpect(status().isNotFound());
	}

	@Test
	public void deleteRequest() throws Exception {
		this.mvc.perform(delete("/icon/100").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").secure(false)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		this.mvc.perform(delete("/icon/").secure(false)).andExpect(status().isMethodNotAllowed());
	}

}
