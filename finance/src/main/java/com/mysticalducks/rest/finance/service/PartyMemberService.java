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
    
    public void deleteById(PartyMemberId id) {
        partyMemberRepository.deleteById(id);
    }

    public void delete(PartyMember partyMember) {
        partyMemberRepository.delete(partyMember);
    }
}
