package com.mysticalducks.rest.finance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysticalducks.rest.finance.exception.PartyNotFoundException;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.model.Party;
import com.mysticalducks.rest.finance.service.PartyService;

@WebMvcTest(value = PartyController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
public class PartyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PartyService partyService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void findParty() throws Exception {
        Party party = new Party(1, "Test Party", null);
        when(partyService.findById(1)).thenReturn(party);

        mvc.perform(get("/party/1").secure(false))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("name").value("Test Party"));
    }

    @Test
    public void findParties() throws Exception {
        Party party1 = new Party(1, "Party 1", null);
        Party party2 = new Party(2, "Party 2", null);
        when(partyService.findAll()).thenReturn(List.of(party1, party2));

        mvc.perform(get("/parties").secure(false))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].name").value("Party 1"))
            .andExpect(jsonPath("$[1].name").value("Party 2"));
    }
    
    @Test
    public void findPartiesByUser() throws Exception {
        Party party1 = new Party(1, "Party 1", null);
        Party party2 = new Party(2, "Party 2", null);
        List<Party> parties = List.of(party1, party2);

        when(partyService.findAllByUser(1)).thenReturn(parties);

        mvc.perform(get("/parties/1").secure(false))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Party 1"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Party 2"));
    }
    
    @Test
    public void findPartyByUserChatIdAndUserId() throws Exception {
        Party party = new Party(1, "Test Party", null);
        when(partyService.findPartyByUserChatIdAndUserId(12345, 1)).thenReturn(party);

        mvc.perform(get("/party/user/chat/12345/user/1").secure(false))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("id").value(1))
            .andExpect(jsonPath("name").value("Test Party"));
    }

    
    @Test
    public void findPartyByUserChatIdAndUserId_notFound() throws Exception {
        when(partyService.findPartyByUserChatIdAndUserId(12345, 1))
            .thenThrow(new PartyNotFoundException("No party found for user with chat ID: 12345 and user ID: 1"));

        mvc.perform(get("/party/user/chat/12345/user/1").secure(false))
            .andExpect(status().isNotFound());
    }


    @Test
    public void newParty() throws Exception {
        FinanceInformation financeInformation = new FinanceInformation(1, 100.0);

        Party party = new Party(1, "New Party", financeInformation);

        when(partyService.save(any(String.class), any(Integer.class))).thenReturn(party);

        mvc.perform(post("/party")
                .param("name", "New Party")
                .param("financeId", "1")
                .secure(false))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("name").value("New Party"))
            .andExpect(jsonPath("financeInformation.id").value(1))
            .andExpect(jsonPath("financeInformation.budget").value(100.0));
    }

    @Test
    public void replaceParty() throws Exception {
        FinanceInformation financeInformation = new FinanceInformation(1, 200.0);

        Party party = new Party(1, "Updated Party", financeInformation);

        when(partyService.save(any(Party.class))).thenReturn(party);

        mvc.perform(put("/party")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(party))
                .secure(false))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("name").value("Updated Party"))
            .andExpect(jsonPath("financeInformation.id").value(1))
            .andExpect(jsonPath("financeInformation.budget").value(200.0));
    }


    @Test
    public void deleteParty() throws Exception {
        mvc.perform(delete("/party/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .secure(false))
            .andExpect(status().isNoContent());
    }
}
