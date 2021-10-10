package com.mysticalducks.rest.finance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.Icon;


@Repository
public interface IconRepository  extends CrudRepository<Icon, Integer>  {
	
	@Query(nativeQuery=true, value="DELETE FROM ICON WHERE ID = ?1")
	public void deleteById(Integer id);
	
}
