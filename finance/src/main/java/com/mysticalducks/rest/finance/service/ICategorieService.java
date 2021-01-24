package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import com.mysticalducks.rest.finance.model.Categorie;

public interface ICategorieService {
	
	List<Categorie> findAllCategories();
	
	Optional<Categorie> findCategorie(int id);
	
	List<Categorie> findAllCategoriesByUserId(int userId);

}
