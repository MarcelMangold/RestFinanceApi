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

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FinanceInformationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FinanceInformationRepository financeRepository;

    @Test
    public void findFinanceInformationById() {
    	
    	FinanceInformation financeInformation = new FinanceInformation(5.0);
		entityManager.persist(financeInformation);
        entityManager.flush();

        FinanceInformation found = financeRepository.findById(financeInformation.getId()).get();

        assertThat(found).isEqualTo(financeInformation);
    }
}