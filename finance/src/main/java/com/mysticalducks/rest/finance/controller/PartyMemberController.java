package com.mysticalducks.rest.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mysticalducks.rest.finance.model.PartyMember;
import com.mysticalducks.rest.finance.model.PartyMemberId;
import com.mysticalducks.rest.finance.service.PartyMemberService;

@Controller
public class PartyMemberController extends AbstractController {

    @Autowired
    private PartyMemberService partyMemberService;

    @GetMapping("/partyMember/{partyId}/{userId}")
    @ResponseBody
    public PartyMember findPartyMember(@PathVariable int userId, @PathVariable int partyId) {
    	checkIfParameterIsEmpty(userId);
    	checkIfParameterIsEmpty(partyId);
        PartyMemberId id = new PartyMemberId(partyId, userId);
        return partyMemberService.findById(id);
    }
    
    @GetMapping("/partyMembers/{userId}")
    @ResponseBody
    public ResponseEntity<List<PartyMember>> findPartyMember(@PathVariable int userId) {
    	checkIfParameterIsEmpty(userId);
        return new ResponseEntity<>(partyMemberService.findByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/partyMembers")
    @ResponseBody
    public Iterable<PartyMember> findAllPartyMembers() {
        return partyMemberService.findAll();
    }

    @PostMapping(value = "/partyMember", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PartyMember createPartyMember(@RequestParam int userId, @RequestParam int partyId) {
        checkIfParameterIsEmpty(partyId);
        checkIfParameterIsEmpty(userId);
        return partyMemberService.save(userId, partyId);
    }

    @PutMapping(value = "/partyMember", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PartyMember updatePartyMember(@RequestBody PartyMember partyMember) {
        return partyMemberService.save(partyMember);
    }

    @DeleteMapping(value = "/partyMember", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartyMember(@RequestBody PartyMember partyMember) {
        partyMemberService.delete(partyMember);
    }
}
