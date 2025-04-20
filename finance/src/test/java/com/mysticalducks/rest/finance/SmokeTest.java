package com.mysticalducks.rest.finance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysticalducks.rest.finance.controller.CategoryController;
import com.mysticalducks.rest.finance.controller.FinanceInformationController;
import com.mysticalducks.rest.finance.controller.IconController;
import com.mysticalducks.rest.finance.controller.PartyController;
import com.mysticalducks.rest.finance.controller.TransactionController;
import com.mysticalducks.rest.finance.controller.UserController;

@SpringBootTest
public class SmokeTest {
	
	@Autowired
	private CategoryController categoryController;
	
	@Autowired
	private TransactionController transactionController;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private IconController iconController;
	
	@Autowired
	private FinanceInformationController financeController;
	
	@Autowired
	private PartyController partyController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(categoryController).isNotNull();
		assertThat(transactionController).isNotNull();
		assertThat(userController).isNotNull();
		assertThat(iconController).isNotNull();
		assertThat(financeController).isNotNull();
		assertThat(partyController).isNotNull();
		
	}

}
