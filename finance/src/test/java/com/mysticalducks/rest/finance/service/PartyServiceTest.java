package com.mysticalducks.rest.finance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mysticalducks.rest.finance.exception.PartyNotFoundException;
import com.mysticalducks.rest.finance.exception.UserNotFoundException;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.PartyMember;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.PartyRepository;

@ExtendWith(MockitoExtension.class)
public class PartyServiceTest {
	
    @Mock
    private PartyRepository partyRepository;
    
    @InjectMocks
    private PartyService partyService;
    
    @Mock
    private PartyMemberService partyMemberService;
        
    @Mock
    private FinanceInformationService financeInformationService;
    
    @Mock
    private UserService userService;
    
    private User user;
    private FinanceInformation financeInformation;
    private Party party;
    
    
    @BeforeEach
    void setUp() {
    	this.user = new User(1, "test", "email", "password", 0);
		this.party = new Party(1, "test", new FinanceInformation());
        this.financeInformation = new FinanceInformation(100.0);
        this.party = new Party("Test Party", financeInformation);
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
    void findPartyByUserChatIdAndUserId() {
        Party party = new Party(1, "Test Party", new FinanceInformation());
        when(partyRepository.findPartyByUserChatIdAndUserId(12345, 1)).thenReturn(Optional.of(party));

        Party result = partyService.findPartyByUserChatIdAndUserId(12345, 1);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Party");

        verify(partyRepository).findPartyByUserChatIdAndUserId(12345, 1);
    }

    
    @Test
    void findPartyByUserChatIdAndUserId_notFound() {
        when(partyRepository.findPartyByUserChatIdAndUserId(12345, 1)).thenReturn(Optional.empty());

        PartyNotFoundException thrown = assertThrows(PartyNotFoundException.class, 
            () -> partyService.findPartyByUserChatIdAndUserId(12345, 1));

        assertThat(thrown.getMessage()).contains("No party found for user with chat ID: 12345 and user ID: 1");

        verify(partyRepository).findPartyByUserChatIdAndUserId(12345, 1);
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
	    assertThat(savedparty.getFinanceInformation()).isEqualTo(finaceInformation);

	    verify(partyRepository).save(any(Party.class));
	}
	
    @Test
    void createPartyWithFinanceAndMeber() {
        when(financeInformationService.save(any(FinanceInformation.class))).thenReturn(financeInformation);
        when(userService.findById(1)).thenReturn(user);
        when(partyRepository.save(any(Party.class))).thenReturn(party);

        Party createdParty = partyService.createPartyWithFinanceAndMember(1, 12345, "Test Party", 100.0);

        assertThat(createdParty).isNotNull();
        assertThat(createdParty.getName()).isEqualTo("Test Party");
        assertThat(createdParty.getFinanceInformation().getBudget()).isEqualTo(100.0);

        verify(financeInformationService).save(any(FinanceInformation.class));
        verify(userService).findById(1);
        verify(partyRepository).save(any(Party.class));
        verify(partyMemberService).save(any(PartyMember.class));
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
	
	@Test
	void findAllByUser() {
	    User user = new User();

	    Party party1 = new Party(1, "Party 1", new FinanceInformation());
	    Party party2 = new Party(2, "Party 2", new FinanceInformation());

	    List<Party> parties = List.of(party1, party2);

	    when(userService.findById(1)).thenReturn(user);
	    when(partyRepository.findAllPartiesByUser(user)).thenReturn(parties);

	    Iterable<Party> result = partyService.findAllByUser(1);

	    assertThat(result).isNotNull();
	    assertThat(result).hasSize(2);
	    assertThat(result).contains(party1, party2);

	    verify(userService).findById(1);
	    verify(partyRepository).findAllPartiesByUser(user);
	}
	
	@Test
	void findAllByUser_userNotFound() {
	    int userId = 1;
	    when(userService.findById(userId)).thenReturn(null);

	    UserNotFoundException thrown = assertThrows(UserNotFoundException.class, 
	        () -> partyService.findAllByUser(userId));

	    assertThat(thrown.getMessage()).contains(String.valueOf(userId));

	    verify(userService).findById(userId);
	    verify(partyRepository, never()).findAllPartiesByUser(any());
	}


}
