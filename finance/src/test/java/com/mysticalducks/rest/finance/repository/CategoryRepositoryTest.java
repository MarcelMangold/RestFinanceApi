package com.mysticalducks.rest.finance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Party;

@ExtendWith(SpringExtension.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoryRepositoryTest extends AbstractRepositoryTest{
	
	@BeforeEach
	void setUp() {
		this.financeInformation = new FinanceInformation();
		this.party = new Party("Party", financeInformation);
		this.icon = new Icon("Icon");
		this.category = new Category("Category", party, icon);
		
		financeInformationRepository.save(financeInformation);
		partyRepository.save(party);
    	iconRepository.save(icon);
    	categoryRepository.save(category);
	}

    @Test
    public void findAllCategoriesByPartyIdTest() throws Exception {
		Party newParty = addNewParty("newParty");
		partyRepository.save(newParty);
    	addNewCategory("newUserCategory1", newParty);
    	addNewCategory("newUserCategory2", newParty);
    	Category userCategory1 = addNewCategory("UserCategory1", party);
    	
        List<Category> queryResult = (List<Category>) categoryRepository.findAllCategoriesByPartyId(party);

        assertFalse(queryResult.isEmpty());
        assertEquals(2, queryResult.size());
        assertEquals(category, queryResult.get(0));
        assertEquals(userCategory1, queryResult.get(1));
    }

    
}
