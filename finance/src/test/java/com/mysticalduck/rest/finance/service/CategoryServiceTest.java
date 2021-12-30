package com.mysticalduck.rest.finance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.CategoryRepository;
import com.mysticalducks.rest.finance.repository.UserRepository;
import com.mysticalducks.rest.finance.service.CategoryService;
import com.mysticalducks.rest.finance.service.UserService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CategoryServiceTest {

	@Mock
	UserRepository userRepository;

	@Mock
	CategoryRepository categoryRepository;
	
	@InjectMocks
	UserService userService;

	@InjectMocks
	CategoryService service;

	Category category;
	User user;
	Icon icon;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
//		MockitoAnnotations.initMocks(this);
//		this.userService = new UserService();
		this.user = new User("User", "password", 0);
		this.category = new Category("Category", user , new Icon("Icon"));
	}

	@Test
	void deleteById() {
		service.deleteById(5);

		verify(categoryRepository).deleteById(5);
	}

	@Test
	void findById() {
		when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

		Category foundCategory = service.findById(1);

		assertThat(foundCategory).isNotNull();

		verify(categoryRepository).findById(1);
		
		service.findById(2);

	}

	@Test
	void save() {
		when(categoryRepository.save(any(Category.class))).thenReturn(category);
		
		Category savedCategory = service.save(user, "User", icon);

		verify(categoryRepository).save(any(Category.class));

		assertThat(savedCategory).isNotNull();
	}

	@Test
	void findAll() {
		List<Category> categories = new ArrayList<>();
		categories.add(category);

		when(categoryRepository.findAll()).thenReturn(categories);

		List<Category> foundCategories = service.findAll();

		verify(categoryRepository).findAll();

		assertThat(foundCategories).hasSize(1);
	}

	@Test
	void replace() {
		when(categoryRepository.findById(0)).thenReturn(Optional.of(category));
		when(categoryRepository.save(any(Category.class))).thenReturn(category);

		Category replacedCategory = service.replace(category);

		verify(categoryRepository, times(1)).findById(0);
		verify(categoryRepository, times(1)).save(any(Category.class));

		assertThat(replacedCategory).isNotNull();
	}
	
	@Test
	void replaceNew() {
		when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
		when(categoryRepository.save(any(Category.class))).thenReturn(category);
		
		Category newCategory = new Category("NewCategorie", user, icon);
		newCategory.setID(1);
		Category replacedCategory = service.replace(newCategory);

		verify(categoryRepository, times(1)).findById(1);
		verify(categoryRepository, times(1)).save(any(Category.class));
		
		assertThat(replacedCategory).isNotNull();
		
	}
}
