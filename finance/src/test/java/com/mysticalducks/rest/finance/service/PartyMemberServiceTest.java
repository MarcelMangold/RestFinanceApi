package com.mysticalducks.rest.finance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.mysticalducks.rest.finance.exception.PartyMemberNotFoundException;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.model.PartyMember;
import com.mysticalducks.rest.finance.model.PartyMemberId;
import com.mysticalducks.rest.finance.model.User;
import com.mysticalducks.rest.finance.repository.PartyMemberRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PartyMemberServiceTest {

    @Mock
    private PartyMemberRepository partyMemberRepository;
    
    @Mock
    private UserService userService;

    @InjectMocks
    private PartyMemberService partyMemberService;

    private PartyMemberId partyMemberId;
    private PartyMember partyMember;
    private Party party;
    
	User user;
	FinanceInformation financeInformation;

    @BeforeEach
    void setUp() {
    	financeInformation = new FinanceInformation();
    	party = new Party("Party", financeInformation);
		user = new User();
		financeInformation = new FinanceInformation();
	
    	partyMember = new PartyMember(user,party);
        partyMemberId = partyMember.getId();
    }

    @Test
    void testFindAll() {
        when(partyMemberRepository.findAll()).thenReturn(List.of(partyMember));
        var result = partyMemberService.findAll();
        assertNotNull(result);
        assertEquals(1, ((List<PartyMember>) result).size());
        verify(partyMemberRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        when(partyMemberRepository.findById(partyMemberId)).thenReturn(Optional.of(partyMember));
        var result = partyMemberService.findById(partyMemberId);
        assertNotNull(result);
        assertEquals(party, result.getParty());
        assertEquals(user, result.getUser());
        verify(partyMemberRepository, times(1)).findById(partyMemberId);
    }

    @Test
    void testFindById_NotFound() {
        when(partyMemberRepository.findById(partyMemberId)).thenReturn(Optional.empty());
        assertThrows(PartyMemberNotFoundException.class, () -> partyMemberService.findById(partyMemberId));
        verify(partyMemberRepository, times(1)).findById(partyMemberId);
    }
    
    
    @Test
    void testFindByUser_Success() {
        when(partyMemberRepository.findAllPartiesByUserId(user)).thenReturn(List.of(partyMember));
        when(userService.findById(user.getId())).thenReturn(user);
        var result = (List<PartyMember>) partyMemberService.findByUser(user.getId());
        assertNotNull(result);
        assertEquals(party, result.get(0).getParty());
        assertEquals(user, result.get(0).getUser());
        verify(userService, times(1)).findById(user.getId());
        verify(partyMemberRepository, times(1)).findAllPartiesByUserId(user);
    }
    
    @Test
    void testFindByUser_UserNotFound() {
        when(partyMemberRepository.findAllPartiesByUserId(user)).thenReturn(List.of(partyMember));
        var result = (List<PartyMember>) partyMemberService.findByUser(-1);
        assertNotNull(result);
        assertEquals(0,result.size());
        verify(partyMemberRepository, times(0)).findAllPartiesByUserId(user);
    }
    

    @Test
    void testSave_PartyMember() {
        when(partyMemberRepository.save(partyMember)).thenReturn(partyMember);
        var result = partyMemberService.save(partyMember);
        assertNotNull(result);
        assertEquals(party, result.getParty());
        assertEquals(user, result.getUser());
        verify(partyMemberRepository, times(1)).save(partyMember);
    }

    @Test
    void testDeleteById() {
        doNothing().when(partyMemberRepository).deleteById(partyMemberId);
        partyMemberService.deleteById(partyMemberId);
        verify(partyMemberRepository, times(1)).deleteById(partyMemberId);
    }

    @Test
    void testDelete() {
        doNothing().when(partyMemberRepository).delete(partyMember);
        partyMemberService.delete(partyMember);
        verify(partyMemberRepository, times(1)).delete(partyMember);
    }
}
