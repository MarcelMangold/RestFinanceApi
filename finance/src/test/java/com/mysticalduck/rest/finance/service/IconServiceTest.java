package com.mysticalduck.rest.finance.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.mysticalducks.rest.finance.model.Icon;
import com.mysticalducks.rest.finance.repository.IconRepository;
import com.mysticalducks.rest.finance.service.IconService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class IconServiceTest {

	@Mock
	IconRepository iconRepository;

	@InjectMocks
	IconService service;

	Icon icon;

	@BeforeEach
	void setUp() {
		this.icon = new Icon(1, "Test");
	}

	@Test
	void deleteById() {
		service.deleteById(5);

		verify(iconRepository).deleteById(5);
	}

	@Test
	void findById() {
		when(iconRepository.findById(1)).thenReturn(Optional.of(icon));

		Icon foundIcon = service.findById(1);

		assertThat(foundIcon).isNotNull();

		verify(iconRepository).findById(1);

		DataNotFoundException thrown = assertThrows(DataNotFoundException.class, () -> service.findById(2),
				"No data found for the id 2");

		assertTrue(thrown.getMessage().contains("2"));
	}

	@Test
	void delete() {
		service.delete(icon);

		verify(iconRepository).delete(any(Icon.class));
	}

	@Test
	void save() {
		when(iconRepository.save(any(Icon.class))).thenReturn(icon);

		Icon savedIcon = service.save("test");

		verify(iconRepository).save(any(Icon.class));

		assertThat(savedIcon).isNotNull();
	}

	@Test
	void findAll() {
		List<Icon> icons = new ArrayList<>();
		icons.add(icon);

		when(iconRepository.findAll()).thenReturn(icons);

		List<Icon> foundIcons = service.findAll();

		verify(iconRepository).findAll();

		assertThat(foundIcons).hasSize(1);
	}

}
