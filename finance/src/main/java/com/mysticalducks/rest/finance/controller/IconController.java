package com.mysticalducks.rest.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.service.IconService;

@Controller
public class IconController {
	
	@Autowired
	private IconService iconService;
	
	@GetMapping("/icons")
	@ResponseBody
	public List<Icon> findAllIcons(){
		return iconService.findAllIcon();
	}

}
