package com.mysticalducks.rest.finance.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.User;

@Repository
public interface PartyRepository extends CrudRepository<Party, Integer> {
	
	@Query("select p from Party p join PartyMember pm on p.id = pm.party.id where pm.user = :user")
	List<Party> findAllPartiesByUser(@Param("user") User user);
	
	@Query("select p from Party p join PartyMember pm on p.id = pm.party.id where pm.id.userId = :userId and pm.chatId = :chatId")
	Optional<Party> findPartyByUserChatIdAndUserId(@Param("chatId") int chatId, @Param("userId") int userId);
	
}
