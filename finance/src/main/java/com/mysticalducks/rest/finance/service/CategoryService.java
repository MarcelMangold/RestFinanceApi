package com.mysticalducks.rest.finance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.CategoryRepository;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private IconService iconService;

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

	public Category save(int userId, String name, int iconId) {
		Optional<User> user = userService.findUser(userId);
		if (user.isPresent()) {
			
			Optional<Icon> icon = iconService.findIcon(iconId);
			if (icon.isPresent()) {
				Category category = new Category(name, user.get(), icon.get());
				return categoryRepository.save(category);
			} else {
				return null; // "no icon found";
			}

		} else {
			return null; //"no User found";
		}

	}
	
	public Category replace(Category newCategory) {
		int id = newCategory.getId();
		return categoryRepository.findById(id)
				.map(category -> {
					category.setName(newCategory.getName());
					category.setIcon(newCategory.getIcon());
					category.setUser(newCategory.getUser());
					return categoryRepository.save(category);
				})
				.orElseGet(() -> {
					newCategory.setID(id);
					return categoryRepository.save(newCategory);
				});
	}
	
	public void delete(int id) {
		categoryRepository.deleteById(id);
	}

}
