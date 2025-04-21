package com.mysticalducks.rest.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.PartyMember;
import com.mysticalducks.rest.finance.model.PartyMemberId;
import com.mysticalducks.rest.finance.model.User;

@Repository
public interface PartyMemberRepository extends CrudRepository<PartyMember, PartyMemberId> {
	
	@Query("select c from PartyMember c where c.user=:user")
	List<PartyMember> findAllPartiesByUserId(@Param("user") User user);
	
	
}
