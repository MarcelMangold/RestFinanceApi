package com.mysticalducks.rest.finance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.swagger.annotations.ApiOperation;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	@ResponseBody
	public List<Category> findCategories() {
		return categoryService.findAllCategories();
	}

	@GetMapping("/categories/{id}")
	@ApiOperation(value = "Finds categorie by id", 
			notes = "Provide an id to look up spezific category from the finance database",
			response = Category.class)
	@ResponseBody
	public Category findCategory(@PathVariable int id){
		Optional<Category> category = categoryService.findCategorie(id);
		if(category.isPresent()) {
			return category.get();
		} else {
			return null;
		}
	}
	
	 @PostMapping("/category")
	 @ResponseBody
	 Category newCategory(@RequestParam int userId, @RequestParam String name, @RequestParam int iconId) {
		 return categoryService.save(userId, name, iconId);
	  }
	 
	 @PutMapping("/category/")
	 @ResponseBody
	 Category replaceCategory(@RequestBody Category category) {
		 return categoryService.replace(category);
	 }
	 
	 @DeleteMapping("/category/{id}")
	 void deleteCategory(@PathVariable int id) {
		 categoryService.delete(id);
	 }
	
	
}
