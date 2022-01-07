package com.mysticalducks.rest.finance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mysticalducks.rest.finance.exception.DataNotFoundException;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService service;
	
	User user;
	
	@BeforeEach
	void setUp() {
		this.user = new User(1,"test", "password", 0);
				
	}

	@Test
	void deleteById() {
		service.deleteById(1);

		verify(userRepository).deleteById(1);
	}

	@Test
	void findById() {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));

		User foundUser = service.findById(1);

		assertThat(foundUser).isNotNull();

		verify(userRepository).findById(1);
		
		DataNotFoundException thrown = assertThrows(DataNotFoundException.class, () -> service.findById(2),
				"No data found for the id 2");

		assertTrue(thrown.getMessage().contains("2"));

	}

	@Test
	void delete() {
		service.delete(user);
		
		verify(userRepository).delete(any(User.class));
	}
	
	
	@Test
	void save() {
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		User savedUser = service.save(new User(2,"test", "password", 0));
		
		verify(userRepository).save(any(User.class));
		
		assertThat(savedUser).isNotNull();

		when(service.save(any(User.class))).thenReturn(user);
		
		User savedUserWithParams = service.save("test", "password", 0);

		assertThat(savedUserWithParams).isNotNull();
	}
	
	@Test
	void findAll() {
		List<User> users = new ArrayList<>();
		users.add(user);
		
		when(userRepository.findAll()).thenReturn(users);
		
		List<User> foundUsers = service.findAll();
		
		verify(userRepository).findAll();
		
		assertThat(foundUsers).hasSize(1);
	}

}
