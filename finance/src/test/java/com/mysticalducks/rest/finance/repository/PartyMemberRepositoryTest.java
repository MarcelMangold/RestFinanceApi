package com.mysticalducks.rest.finance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mysticalducks.rest.finance.model.PartyMember;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PartyMemberRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PartyMemberRepository partyMemberRepository;

	@Test
	public void findPartyMemberById() {
		var user = addNewUser();
		var financeInformation = addNewFinanceInformation();
		var party = addNewParty(financeInformation);
		
		PartyMember partyMember = new PartyMember(user, party);
		entityManager.persist(partyMember);
		entityManager.flush();

		PartyMember found = partyMemberRepository.findById(partyMember.getId()).get();
		assertThat(found).isEqualTo(partyMember);
	}
	
	@Test
	public void findallPartiesByUserId() {
		var user1 = addNewUser("User1");
		var user2 = addNewUser("User2");
		var financeInformation = addNewFinanceInformation();
		var party1 = addNewParty("Party1", financeInformation);
		var party2 = addNewParty("Party2", financeInformation);
		
		PartyMember party1AndUser1 = new PartyMember(user1, party1);
		PartyMember party1AndUser2 = new PartyMember(user2, party1);
		PartyMember party2AndUser1 = new PartyMember(user1, party2);
		entityManager.persist(party1AndUser1);
		entityManager.persist(party1AndUser2);
		entityManager.persist(party2AndUser1);
		entityManager.flush();

		var partiesUser1= partyMemberRepository.findAllPartiesByUserId(user1);
		assertThat(partiesUser1).hasSize(2);
		assertThat(partiesUser1).contains(party1AndUser1, party2AndUser1);
		
		var partiesUser2= partyMemberRepository.findAllPartiesByUserId(user2);
		assertThat(partiesUser2).hasSize(1);
		assertThat(partiesUser2).contains(party1AndUser2);
		
		
	}

}

