package com.mysticalducks.rest.finance.service;

import java.util.List;

import com.mysticalducks.rest.finance.model.Category;

public interface ICategoryService {
	
	List<Category> findAll();
	
	Category findById(int id);
	
	List<Category> findAllByPartyId(int partyId);

}
