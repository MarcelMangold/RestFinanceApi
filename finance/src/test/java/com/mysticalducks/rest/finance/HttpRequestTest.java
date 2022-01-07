package com.mysticalducks.rest.finance;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
    int port;

	@Test
	public void greetingShouldReturnMessage() throws Exception {
		String body = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1", String.class);
		assertThat(body).isEqualTo("Welcome to FinanceDB Api <br> For more information see: <br> <a href=\"http://localhost:9000/api/v1/swagger-ui.html#/\">Api Documentation</a>");
	}

}
