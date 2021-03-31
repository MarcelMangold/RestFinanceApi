package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.CategoryRepository;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserService userService;

	public List<Category> findAllCategories() {
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		return categories;
	}

	public Optional<Category> findCategorie(int id) {
		return categoryRepository.findById(id);
	}

	public List<Category> findAllCategoriesByUserId(int userId) {
		Optional<User> user = userService.findUser(userId);

		if (user.isPresent()) {
			List<Category> categories = (List<Category>) categoryRepository.findAllCategoriesByUserId(user.get());
			return categories;
		} else {
			return null;
		}
	}
	
	public Category save(Category newCategorie) {
		return categoryRepository.save(newCategorie);
	}


}
