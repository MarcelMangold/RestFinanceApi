package com.mysticalducks.rest.finance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PartyRepositoryTest extends AbstractRepositoryTest {


	@Autowired
	private PartyRepository partyRepository;

	@Test
	public void findPartyById() {
		var party = addNewParty("party");

		Party found = partyRepository.findById(party.getId()).get();
		assertThat(found).isEqualTo(party);
	}
	
	@Test
	public void findPartyByUser() {
		var financeInformation = addNewFinanceInformation();

		Party party1 = addNewParty("party", financeInformation);
		Party party2 = addNewParty("party", financeInformation);
		User user = addNewUser();
		addNewPartyMember(user, party1);
		addNewPartyMember(user, party2);

		Party foundParty1 = partyRepository.findAllPartiesByUser(user).get(0);
		Party foundParty2 = partyRepository.findAllPartiesByUser(user).get(1);
		assertThat(foundParty1).isEqualTo(party1);
		assertThat(foundParty2).isEqualTo(party2);
	}
	
	
	@Test
	public void findPartyByUserChatIdAndUserId() {
	    var financeInformation = addNewFinanceInformation();
	    Party party = addNewParty("Test Party", financeInformation);
	    User user = addNewUser();
	    var partyMember = addNewPartyMember(user, party);

	    Optional<Party> foundParty = partyRepository.findPartyByUserChatIdAndUserId(partyMember.getChatId(), user.getId());

	    assertThat(foundParty).isPresent();
	    assertThat(foundParty.get()).isEqualTo(party);
	}
	
	@Test
	public void findPartyByUserChatIdAndUserId_notFound() {
	    Optional<Party> foundParty = partyRepository.findPartyByUserChatIdAndUserId(99999, 99999);

	    assertThat(foundParty).isNotPresent();
	}



}
