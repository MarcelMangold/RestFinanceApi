package com.mysticalducks.rest.finance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mysticalducks.rest.finance.model.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void findUserbyId() {
		User jane = new User(0, "Jane", "email", "pw", 0);
		entityManager.persist(jane);
		entityManager.flush();

		User found = userRepository.findById(jane.getId()).get();

		assertThat(found).isEqualTo(jane);
	}

	@Test
	public void findByTelegramUserId() {
		User user = new User(0, 12345, "John Doe", "john.doe@example.com", "password", 1);
		entityManager.persist(user);
		entityManager.flush();

		Optional<User> found = userRepository.findByTelegramUserId(12345);

		assertThat(found).isPresent();
		assertThat(found.get()).isEqualTo(user);
		
		Optional<User> notFound = userRepository.findByTelegramUserId(123);
		assertThat(notFound).isEmpty();
		
	}
}