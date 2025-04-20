package com.mysticalducks.rest.finance.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.Party;

@Repository
public interface PartyRepository extends CrudRepository<Party, Integer> {
	
}
