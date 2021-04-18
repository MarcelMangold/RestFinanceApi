package com.mysticalducks.rest.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/icon/{id}")
	@ResponseBody
	public Icon findIcon(@PathVariable int id){
		return iconService.findIcon(id);
	}
	
	
	@PostMapping("/icon/")
	@ResponseBody
	public Icon newIcon(@RequestParam String iconName){
		return iconService.save(iconName);
	}
	
	@DeleteMapping("/icon/{id}")
	public void deleteIcon(@PathVariable int id) {
		iconService.delete(id);
	}
	

}
