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
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.service.CategoryService;
import com.mysticalducks.rest.finance.service.IconService;
import com.mysticalducks.rest.finance.service.UserService;

import io.swagger.annotations.ApiOperation;

@Controller
//@V1APIController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private IconService iconService;
	
	@GetMapping("/categories")
	@ResponseBody
	public List<Category> findCategories() {
		return categoryService.findAll();
	}

	@GetMapping("/categories/{id}")
	@ApiOperation(value = "Finds categorie by id", 
			notes = "Provide an id to look up spezific category from the finance database",
			response = Category.class)
	@ResponseBody
	public Category findCategory(@PathVariable int id){
		Category category = categoryService.findById(id);
		if(category != null) {
			return category;
		} else {
			return null;
		}
	}
	
	 @PostMapping("/category")
	 @ResponseBody
	 ResponseEntity<?> newCategory(@RequestParam int userId, @RequestParam String name, @RequestParam int iconId) {
		 
		 User user = userService.findById(userId);
		 Icon icon = iconService.findById(iconId);
		 
		 if(user == null) 
			 return new ResponseEntity<String>("User " + userId + " not found",HttpStatus.NOT_FOUND);
		 
		 if(icon == null)
			 return new ResponseEntity<String>("Icon " + iconId + " not found",HttpStatus.NOT_FOUND);
		 
		 return new ResponseEntity<Category>(categoryService.save(user, name, icon), HttpStatus.OK) ;
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
