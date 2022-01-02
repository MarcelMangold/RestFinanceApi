package com.mysticalducks.rest.finance.Controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.mysticalducks.rest.finance.controller.IconController;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.service.IconService;

@WebMvcTest(value = IconController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
public class IconControllerTest {

	@Autowired
	WebApplicationContext context;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IconService iconService;
	

	@MockBean
	private IconController iconController;
	
	
	@Value("${v1API}")
	private String urlPrefix;
	
	
	@Test
	public void postRequest() throws Exception {
//		given(this.iconController.findIcon(1)).willReturn(new Icon(1,"test"));
//		this.mvc.perform(post(urlPrefix + "/icon/")
//				.content("test")
//				.secure(false)
//				.contentType(MediaType.APPLICATION_JSON)
//			    .accept(MediaType.APPLICATION_JSON))
//			    .andExpect(status().isCreated())
//			    .andExpect(jsonPath("name").value("test"))
//			    .andExpect(jsonPath("id").value(1));
//
//		this.mvc.perform(post(urlPrefix +"/icon/").secure(false)).andExpect(status().isBadRequest());
	}
	

	@Test
	public void getRequest() throws Exception {
		given(this.iconService.findById(1)).willReturn(new Icon("test"));
		this.mvc.perform(get(urlPrefix + "/icon/1").secure(false)).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("id").value(0))
				.andExpect(jsonPath("name").value("test"));

		this.mvc.perform(get(urlPrefix +"/icon/").secure(false)).andExpect(status().isMethodNotAllowed());

		this.mvc.perform(get(urlPrefix + "/icon/-1").secure(false)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$").exists());
	}
	
	@Test
	public void deleteRequest() throws Exception {
		this.mvc.perform(delete(urlPrefix +"/icon/100")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.secure(false)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		this.mvc.perform(delete(urlPrefix +"/icon/").secure(false)).andExpect(status().isMethodNotAllowed());
	}

}
