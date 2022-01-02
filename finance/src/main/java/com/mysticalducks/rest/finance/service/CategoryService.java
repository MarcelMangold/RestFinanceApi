package com.mysticalducks.rest.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.exception.DataNotFoundException;
import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.CategoryRepository;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepository;


	public List<Category> findAll() {
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		return categories;
	}

	public Category findById(int id) {
		return categoryRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
		
	}

	public List<Category> findAllByUserId(User user) {
		return (List<Category>) categoryRepository.findAllCategoriesByUserId(user);
	}

	public Category save(User user, String name, Icon icon) {
		return categoryRepository.save(new Category(name, user, icon));
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
	
	public void deleteById(int id) {
		categoryRepository.deleteById(id);
	}


}
