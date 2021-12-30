package com.mysticalducks.rest.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.service.IconService;

@RestController
//@V1APIController
public class IconController {

	@Autowired
	private IconService iconService;

	@GetMapping("/icons")
	@ResponseBody
	public List<Icon> findAllIcons() {
		return iconService.findAll();
	}

	@GetMapping(value="/icon/{id}")
	@ResponseBody
	public Icon findIcon(@PathVariable int id) {
		
		return iconService.findById(id);
/*
		if(icon != null) {
			return ResponseEntity.ok(icon);
		}
		
		return new ResponseEntity(new EmptyJsonResponse() , HttpStatus.OK);*/
	}

	@PostMapping("/icon/")
	@ResponseBody
	public ResponseEntity<Icon> newIcon(@RequestBody String iconName) {
		Icon icon = iconService.save(iconName);
		return new ResponseEntity<Icon>(icon, HttpStatus.CREATED);
	}

	@DeleteMapping("/icon/{id}")
	@ResponseBody
	public void deleteIcon(@PathVariable int id) {
		iconService.deleteById(id);
	}

}
