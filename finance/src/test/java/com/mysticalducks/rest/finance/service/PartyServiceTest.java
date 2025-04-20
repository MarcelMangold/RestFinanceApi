package com.mysticalducks.rest.finance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.mysticalducks.rest.finance.exception.PartyNotFoundException;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.repository.PartyRepository;

@ExtendWith(MockitoExtension.class)
public class PartyServiceTest {
	
    @Mock
    private PartyRepository partyRepository;
    
    @InjectMocks
    private PartyService partyService;
    
    @Mock
    private FinanceInformationService financeInformationService;
    
    Party party;
    
    @BeforeEach
    void setUp() {
		this.party = new Party(1, "test", new FinanceInformation());
	}
    
    @Test
    void deleteById() {
		partyService.deleteById(1);
		
		verify(partyRepository).deleteById(1);
	}
    
    @Test
	void findById() {
		when(partyRepository.findById(1)).thenReturn(Optional.of(party));

		Party foundParty = partyService.findById(1);

		assertThat(foundParty).isNotNull();

		verify(partyRepository).findById(1);
		
		PartyNotFoundException thrown = assertThrows(PartyNotFoundException.class, () -> partyService.findById(2),
				"No data found for the id 2");

		assertTrue(thrown.getMessage().contains("2"));
	}
	
	@Test
	void save() {
		partyService.save(party);
		
		verify(partyRepository).save(party);
	}
	
	@Test
	void saveWithNameAndInformation() {
	    FinanceInformation finaceInformation = new FinanceInformation();
	    Party party = new Party("test", finaceInformation);

	    when(partyRepository.save(any(Party.class))).thenReturn(party);

	    Party savedparty = partyService.save("test", 1);

	    assertThat(savedparty).isNotNull();
	    assertThat(savedparty.getName()).isEqualTo("test");
	    assertThat(savedparty.getfinanceInformation()).isEqualTo(finaceInformation);

	    verify(partyRepository).save(any(Party.class));
	}
	
	@Test
	void delete() {
		partyService.delete(party);
		
		verify(partyRepository).delete(party);
	}
	
	@Test
	void findAll() {
		partyService.findAll();
		
		verify(partyRepository).findAll();
	}

}
