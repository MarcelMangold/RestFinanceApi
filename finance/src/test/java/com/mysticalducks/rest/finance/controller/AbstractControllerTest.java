package com.mysticalducks.rest.finance.controller;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.PartyMember;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;

public class AbstractControllerTest {
	
	protected User user = new User("Hans", "email",  "password", 0);
	protected Icon icon = new Icon("Icon");
	protected Category category = new Category("category", user, icon);
	protected Transaction transaction = new Transaction("transaction", 250.0, "note", category, user);
	protected FinanceInformation financeInformation = new FinanceInformation();
	protected Party party1 = new Party(1, "party1", financeInformation);
	protected Party party2 = new Party(2, "party2", financeInformation);
	protected PartyMember partyMember1 = new PartyMember(user, party1, 1);
	protected PartyMember partyMember2 = new PartyMember(user, party2, 1);
	
	

}
