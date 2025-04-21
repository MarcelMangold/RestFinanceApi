package com.mysticalducks.rest.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Party;


@Repository
public interface CategoryRepository  extends CrudRepository<Category, Integer>  {
	
	
	@Query("select c from Category c where c.party=:party")
	List<Category> findAllCategoriesByPartyId(@Param("party") Party party);
	
}
