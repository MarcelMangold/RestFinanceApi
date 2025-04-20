package com.mysticalducks.rest.finance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mysticalducks.rest.finance.exception.FinanceInformationNotFoundException;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.repository.FinanceInformationRepository;

@ExtendWith(MockitoExtension.class)
public class FinanceInformationServiceTest {

	@Mock
	private FinanceInformationRepository financeRepository;

	@InjectMocks
	private FinanceInformationService financeService;

	private FinanceInformation financeInformation;

	@BeforeEach
	void setUp() {
		this.financeInformation = new FinanceInformation(100.0);
	}


	@Test
	void findById_Success() {
		when(financeRepository.findById(1)).thenReturn(Optional.of(financeInformation));

		FinanceInformation found = financeService.findById(1);

		assertThat(found).isNotNull();
		assertThat(found.getBudget()).isEqualTo(100.0);

		verify(financeRepository).findById(1);
	}

	@Test
	void findById_NotFound() {
		when(financeRepository.findById(1)).thenReturn(Optional.empty());

		FinanceInformationNotFoundException thrown = assertThrows(FinanceInformationNotFoundException.class,
				() -> financeService.findById(1));

		assertThat(thrown.getMessage()).contains("1");
		verify(financeRepository).findById(1);
	}

	@Test
	void save_WithBudget() {
		when(financeRepository.save(any(FinanceInformation.class))).thenReturn(financeInformation);

		FinanceInformation saved = financeService.save(100.0);

		assertThat(saved).isNotNull();
		assertThat(saved.getBudget()).isEqualTo(100.0);

		verify(financeRepository).save(any(FinanceInformation.class));
	}

	@Test
	void save_WithEntity() {
		when(financeRepository.save(financeInformation)).thenReturn(financeInformation);

		FinanceInformation saved = financeService.save(financeInformation);

		assertThat(saved).isNotNull();
		assertThat(saved.getBudget()).isEqualTo(100.0);

		verify(financeRepository).save(financeInformation);
	}

	@Test
	void deleteById() {
		financeService.deleteById(1);
		verify(financeRepository).deleteById(1);
	}

	@Test
	void delete() {
		financeService.delete(financeInformation);
		verify(financeRepository).delete(financeInformation);
	}

}
