package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import com.mysticalducks.rest.finance.model.Category;

public interface ICategoryService {
	
	List<Category> findAllCategories();
	
	Optional<Category> findCategorie(int id);
	
	List<Category> findAllCategoriesByUserId(int userId);

}
