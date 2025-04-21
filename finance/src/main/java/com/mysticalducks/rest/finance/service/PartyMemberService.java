package com.mysticalducks.rest.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysticalducks.rest.finance.exception.PartyMemberNotFoundException;
import com.mysticalducks.rest.finance.model.PartyMember;
import com.mysticalducks.rest.finance.model.PartyMemberId;
import com.mysticalducks.rest.finance.repository.PartyMemberRepository;

@Service
public class PartyMemberService implements IPartyMemberService {

    @Autowired
    private PartyMemberRepository partyMemberRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PartyService partyService;


    public Iterable<PartyMember> findAll() {
        return partyMemberRepository.findAll();
    }

    public PartyMember findById(PartyMemberId id) {
        return partyMemberRepository.findById(id).orElseThrow(() -> new PartyMemberNotFoundException(id));
    }
    
    public List<PartyMember> findByUser(Integer id) {
    	var user = userService.findById(id);
        return partyMemberRepository.findAllPartiesByUserId(user);
    }

    public PartyMember save(PartyMember partyMember) {
        return partyMemberRepository.save(partyMember);
    }
    
    public PartyMember save(int userId, int partyId) {
    	return save(userId, partyId, null);
    }
    
    public PartyMember save(int userId, int partyId, Integer chatId) {
    	var user = userService.findById(userId);
    	var party = partyService.findById(partyId);
		PartyMember partyMember = new PartyMember(user, party);
        return partyMemberRepository.save(partyMember);
    }

    public void deleteById(PartyMemberId id) {
        partyMemberRepository.deleteById(id);
    }

    public void delete(PartyMember partyMember) {
        partyMemberRepository.delete(partyMember);
    }
}
