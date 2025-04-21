package com.mysticalducks.rest.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.exception.CategoryNotFoundException;
import com.mysticalducks.rest.finance.exception.IconNotFoundException;
import com.mysticalducks.rest.finance.exception.UserNotFoundException;
import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.repository.CategoryRepository;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PartyService partyService;
	
	@Autowired
	private IconService iconService;


	public List<Category> findAll() {
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		return categories;
	}

	public Category findById(int id) {
		return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
		
	}

	public List<Category> findAllByPartyId(Party party) {
		return (List<Category>) categoryRepository.findAllCategoriesByPartyId(party);
	}

	public Category save(Integer partyId, String name, Integer iconId) throws UserNotFoundException, IconNotFoundException {
		Party party = partyService.findById(partyId);
		Icon icon = iconService.findById(iconId);
		 
		 if(party == null) 
			  throw new UserNotFoundException("Party not found with id " + partyId);
		 
		 if(icon == null)
			 throw new IconNotFoundException("Icon not found with id " + iconId);
		
		return categoryRepository.save(new Category(name, party, icon));
	}
	
	public Category replace(Category newCategory) {
		int id = newCategory.getId();
		return categoryRepository.findById(id)
				.map(category -> {
					category.setName(newCategory.getName());
					category.setIcon(newCategory.getIcon());
					category.setParty(newCategory.getParty());
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
