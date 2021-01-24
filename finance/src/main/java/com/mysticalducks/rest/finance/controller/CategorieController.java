package com.mysticalducks.rest.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysticalducks.rest.finance.model.Categorie;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.service.CategorieService;
import com.mysticalducks.rest.finance.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class CategorieController {

	@Autowired
	private CategorieService categorieService;

	@GetMapping("/categories")
	@ResponseBody
	public List<Categorie> findCategories() {
		return categorieService.findAllCategories();
	}

	@GetMapping("/category/{id}")
	@ResponseBody
	public Categorie findCategory(@PathVariable int id){
		Optional<Categorie> categorie = categorieService.findCategorie(id);
		if(categorie.isPresent()) {
			return categorie.get();
		} else {
			return null;
		}
	}
	
	@GetMapping("/categories/{id}")
	@ResponseBody
	public List<Categorie> findAllCategoriesByUserId(@PathVariable int userId){
		List<Categorie> categories = categorieService.findAllCategoriesByUserId(userId);
		return categories;
	}
	
//	@GetMapping("/categories")
//	@ResponseBody 
//	public List<Categorie> findAllCategoriesByChatId(@RequestParam(name = "chatId") int chatId){
//		List<Categorie> categories = categorieService.findAllCategoriesByChatId(chatId);
//		return categories;
//	}
}
