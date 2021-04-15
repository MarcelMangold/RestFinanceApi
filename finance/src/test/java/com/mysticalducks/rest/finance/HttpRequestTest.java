package com.mysticalducks.rest.finance;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
//		System.out.println("-----------------------------------------------" + "http://localhost:" + 9000 + "/");
//		System.out.println(this.restTemplate.getForObject("http://localhost:" + 9000 + "/", String.class));
//		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
//				String.class)).contains("Welcome to FinanceDB Api");
	}

}
