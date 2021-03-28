package com.mysticalducks.rest.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public @ResponseBody String greeting() {
		return "Welcome to FinanceDB Api <br> For more information see: <br> <a href=\"http://localhost:9000/swagger-ui.html#/\">Api Documentation</a>";
	}

}
