package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.model.Categorie;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.CategorieRepository;

@Service
public class CategorieService implements ICategorieService {

	@Autowired
	private CategorieRepository categorieRepository;

	@Autowired
	private UserService userService;

	public List<Categorie> findAllCategories() {
		List<Categorie> categories = (List<Categorie>) categorieRepository.findAll();
		return categories;
	}

	public Optional<Categorie> findCategorie(int id) {
		return categorieRepository.findById(id);
	}

	public List<Categorie> findAllCategoriesByUserId(int userId) {
		Optional<User> user = userService.findUser(userId);

		if (user.isPresent()) {
			List<Categorie> categories = (List<Categorie>) categorieRepository.findAllCategoriesByUserId(user.get());
			return categories;
		} else {
			return null;
		}

	}

}
