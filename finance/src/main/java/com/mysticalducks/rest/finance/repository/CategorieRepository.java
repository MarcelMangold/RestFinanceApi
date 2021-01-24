package com.mysticalducks.rest.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.Categorie;
import com.mysticalducks.rest.finance.model.User;


@Repository
public interface CategorieRepository  extends CrudRepository<Categorie, Integer>  {
	
	
	@Query("select c from Categorie c where c.user=:user")
	List<Categorie> findAllCategoriesByUserId(@Param("user") User user);
	
}
