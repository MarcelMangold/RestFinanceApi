package com.mysticalducks.rest.finance.controller;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.model.User;

public class AbstractControllerTest {
	
	protected User user = new User("Hans", "email",  "password", 0);
	protected Icon icon = new Icon("Icon");
	protected Category category = new Category("category", user, icon);
	protected Chat chat = new Chat(0);
	protected Transaction transaction = new Transaction("transaction", 250.0, "note", category, user, chat);
	
	

}
