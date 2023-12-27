package com.mysticalducks.rest.finance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mysticalducks.rest.finance.model.Category;
import com.mysticalducks.rest.finance.model.Chat;
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.model.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoryRepositoryTest extends AbstractRepositoryTest{
	
	@BeforeEach
	void setUp() {
		this.chat = new Chat(1);
		this.user = new User("User", "email", "password", 0);
		this.icon = new Icon("Icon");
		this.category = new Category("Category", user, icon);
		
		chatRepository.save(chat);
		userRepository.save(user);
    	iconRepository.save(icon);
    	categoryRepository.save(category);
	}

    @Test
    public void findAllCategoriesByUserIdTest() throws Exception {
    	User newUser = new User("User", "email", "password", 0);
    	userRepository.save(newUser);
    	addNewCategory("newUserCategory1", newUser);
    	addNewCategory("newUserCategory2", newUser);
    	Category userCategory1 = addNewCategory("UserCategory1", user);
    	
        List<Category> queryResult = (List<Category>) categoryRepository.findAllCategoriesByUserId(user);

        assertFalse(queryResult.isEmpty());
        assertEquals(2, queryResult.size());
        assertEquals(category, queryResult.get(0));
        assertEquals(userCategory1, queryResult.get(1));
    }

    
}
