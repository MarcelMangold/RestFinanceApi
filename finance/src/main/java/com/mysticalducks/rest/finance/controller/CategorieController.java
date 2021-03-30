package com.mysticalducks.rest.finance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysticalducks.rest.finance.model.Categorie;
import com.mysticalducks.rest.finance.service.CategorieService;

import io.swagger.annotations.ApiOperation;

@Controller
public class CategorieController {

	@Autowired
	private CategorieService categorieService;

	@GetMapping("/categories")
	@ResponseBody
	public List<Categorie> findCategories() {
		return categorieService.findAllCategories();
	}

	@GetMapping("/categories/{id}")
	@ApiOperation(value = "Finds categorie by id", 
			notes = "Provide an id to look up spezific category from the finance database",
			response = Categorie.class)
	@ResponseBody
	public Categorie findCategory(@PathVariable int id){
		Optional<Categorie> categorie = categorieService.findCategorie(id);
		if(categorie.isPresent()) {
			return categorie.get();
		} else {
			return null;
		}
	}
	
}
