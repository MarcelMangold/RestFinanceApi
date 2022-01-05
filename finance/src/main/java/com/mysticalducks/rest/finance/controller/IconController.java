package com.mysticalducks.rest.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mysticalducks.rest.finance.exception.EmptyBodyException;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.service.IconService;

@RestController
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
	}

	@PostMapping(value="/icon/", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Icon newIcon(@RequestBody String iconName) {
		if(iconName == null || iconName.isEmpty() || iconName.isBlank()) {
			throw new EmptyBodyException();
		}
		return iconService.save(iconName);
	}

	@DeleteMapping("/icon/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public void deleteIcon(@PathVariable int id) {
		iconService.deleteById(id);
	}

}
