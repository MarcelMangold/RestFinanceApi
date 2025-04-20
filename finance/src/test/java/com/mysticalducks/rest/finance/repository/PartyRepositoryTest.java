package com.mysticalducks.rest.finance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Party;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PartyRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PartyRepository partyRepository;

	@Test
	public void findPartyById() {
		FinanceInformation financeInformation = new FinanceInformation();
		entityManager.persist(financeInformation);
		entityManager.flush();

		Party party = new Party("party", financeInformation);
		entityManager.persist(party);
		entityManager.flush();

		Party found = partyRepository.findById(party.getId()).get();
		assertThat(found).isEqualTo(party);
	}

}
