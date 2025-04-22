package com.mysticalducks.rest.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.service.PartyService;

@Controller
public class PartyController extends AbstractController {

    @Autowired
    private PartyService partyService;

    @GetMapping("/party/{id}")
    @ResponseBody
    public Party findParty(@PathVariable int id) {
        return partyService.findById(id);
    }
    
    @GetMapping("/parties")
    @ResponseBody
    public Iterable<Party> findParties() {
        return partyService.findAll();
    }
    
    @GetMapping("/parties/{userId}")
    @ResponseBody
    public Iterable<Party> findPartiesByUser(@PathVariable int userId) {
    	checkIfParameterIsEmpty(userId);
        return partyService.findAllByUser(userId);
    }


    @PutMapping(value = "/party", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Party replaceParty(@RequestBody Party party) {
        checkIfParameterIsEmpty(party.getName());
        return partyService.save(party);
    }

    @PostMapping(value = "/party", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Party newParty(@RequestParam String name, @RequestParam int financeId) {
        checkIfParameterIsEmpty(name);
        return partyService.save(name, financeId);
    }

    @DeleteMapping("/party/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParty(@PathVariable int id) {
        partyService.deleteById(id);
    }

}
