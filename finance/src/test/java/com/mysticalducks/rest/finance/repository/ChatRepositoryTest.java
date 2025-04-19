package com.mysticalducks.rest.finance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mysticalducks.rest.finance.model.Chat;

@ExtendWith(SpringExtension.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ChatRepositoryTest extends AbstractRepositoryTest{
	
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ChatRepository chatRepository;

    @Test
    public void findChatById() {
        Chat chat = new Chat(1);
        entityManager.persist(chat);
        entityManager.flush();

        Chat found = chatRepository.findById(chat.getId()).get();

        assertThat(found.getId()).isEqualTo(chat.getId());
     }

    
}
