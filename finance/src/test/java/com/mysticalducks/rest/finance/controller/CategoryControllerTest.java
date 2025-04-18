package com.mysticalducks.rest.finance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.mysticalducks.rest.finance.exception.IconNotFoundException;
import com.mysticalducks.rest.finance.exception.UserNotFoundException;
import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.service.CategoryService;

@WebMvcTest(value = CategoryController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
public class CategoryControllerTest extends AbstractControllerTest {

	@Autowired
	WebApplicationContext context;

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	private CategoryService categoryService;
	

	@Test
	public void findCategory() throws Exception {
		given(this.categoryService.findById(0)).willReturn(new Category("categorie", new User(), new Icon("icon")));
		this.mvc.perform(get("/category/0").secure(false)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("id").value(0))
				.andExpect(jsonPath("name").value("categorie"))
				.andExpect(jsonPath("user.id").value(0))
				.andExpect(jsonPath("icon.id").value(0));

		this.mvc.perform(get("/category").secure(false)).andExpect(status().isMethodNotAllowed());

		this.mvc.perform(get("/category/-1").secure(false)).andExpect(status().isOk());
	}
	
	
	@Test
	public void newCategory() throws Exception {
		given(this.categoryService.save(category.getUser().getId(), category.getName(), category.getIcon().getId())).willReturn(category);
		this.mvc.perform(post("/category")
				.queryParam("userId", "0")
				.queryParam("name", "category")
				.queryParam("iconId", "0")
				.secure(false)
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("name").value(category.getName()))
			    .andExpect(jsonPath("id").value(category.getId()))
			    .andExpect(jsonPath("user.id").value(category.getUser().getId()))
				.andExpect(jsonPath("icon.id").value(category.getIcon().getId()));

		this.mvc.perform(post("/category").secure(false)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void newCategory_userNotFoundException() throws Exception {
		doThrow(new UserNotFoundException("User not found with id " + -1)).when(categoryService).save(anyInt(), anyString(), anyInt());
		
		this.mvc.perform(post("/category/")
				.queryParam("userId", "-1")
				.queryParam("name", "category")
				.queryParam("iconId", "0")
				.secure(false)
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isNotFound());

	}
	
	@Test
	public void newCategory_iconNotFoundException() throws Exception {
		doThrow(new IconNotFoundException("Icon not found with id " + -1)).when(categoryService).save(anyInt(), anyString(), anyInt());
		
		this.mvc.perform(post("/category/")
				.queryParam("userId", "0")
				.queryParam("name", "category")
				.queryParam("iconId", "-1")
				.secure(false)
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isNotFound());

	}
	
	@Test
	public void deleteCategory() throws Exception {
		doNothing().when(categoryService).deleteById(anyInt());
		
		this.mvc.perform(delete("/category/0")
		        .contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk());
	
		verify(categoryService, times(1)).deleteById(0);
	}
	
	@Test
	public void replaceCategory() throws Exception {
		when(categoryService.replace(any(Category.class))).thenReturn(category);
		String categoryJson = "{\"id\":0,\"name\":\"category\",\"user\":{\"id\":0,\"name\":\"Hans\",\"telegramUserId\":0,\"password\":\"password\",\"email\":\"email\",\"language\":0},\"icon\":{\"id\":0,\"name\":\"Icon\"}}";
		
		mvc.perform(put("/category")
		.content(categoryJson)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
		.json(categoryJson));
		
	    verify(categoryService, times(1)).replace(any(Category.class));
	}
	

}
