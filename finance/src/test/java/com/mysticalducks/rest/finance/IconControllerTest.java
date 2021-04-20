package com.mysticalducks.rest.finance;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mysticalducks.rest.finance.controller.IconController;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.service.IconService;

@WebMvcTest(value = IconController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ActiveProfiles("test")
public class IconControllerTest {

	@Autowired
	WebApplicationContext context;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IconService iconService;


	@Test
	public void greetingShouldReturnMessage() throws Exception {
		given(this.iconService.findIcon(1)).willReturn(new Icon("test"));
		this.mvc.perform(get("/icon/1").secure(false)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("id").value(0))
				.andExpect(jsonPath("name").value("test"));
	}

}
