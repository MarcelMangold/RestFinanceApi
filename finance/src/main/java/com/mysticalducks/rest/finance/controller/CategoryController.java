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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.service.CategoryService;

@Controller
//@V1APIController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	@ResponseBody
	public List<Category> findCategories() {
		return categoryService.findAll();
	}

	@GetMapping("/categories/{id}")
	ResponseEntity<Category> findCategory(@PathVariable int id) {
		return new ResponseEntity<Category>(categoryService.findById(id), HttpStatus.OK);
	}

	@PostMapping("/category")
	@ResponseBody
	ResponseEntity<Category> newCategory(@RequestParam int userId, @RequestParam String name, @RequestParam int iconId) {
		return new ResponseEntity<Category>(categoryService.save(userId, name, iconId), HttpStatus.OK);
	}

	@PutMapping("/category/")
	@ResponseBody
	Category replaceCategory(@RequestBody Category category) {
		return categoryService.replace(category);
	}

	@DeleteMapping("/category/{id}")
	void deleteCategory(@PathVariable int id) {
		categoryService.deleteById(id);
	}
	

}
