package com.mysticalducks.rest.finance.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysticalducks.rest.finance.model.PartyMember;
import com.mysticalducks.rest.finance.service.PartyMemberService;

@WebMvcTest(value = PartyMemberController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
class PartyMemberControllerTest extends AbstractControllerTest {

	@Autowired
    private MockMvc mvc;

	@MockitoBean
    private PartyMemberService partyMemberService;
	
	@Autowired
	private ObjectMapper objectMapper;
	

    @Test
    void testFindPartyMember() throws Exception {
        PartyMember partyMember = new PartyMember(user, party1);
        var id = partyMember.getId();
        
        var userId = partyMember.getUser().getId();
        var partyId = partyMember.getParty().getId();

        when(partyMemberService.findById(id)).thenReturn(partyMember);

        mvc.perform(get("/partyMember/" + userId  + "/" + partyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.partyId").value(partyId))
                .andExpect(jsonPath("$.id.userId").value(userId));

        verify(partyMemberService, times(1)).findById(id);
    }
    
    @Test
    void testFindPartyMemberWithMissingPartyId() throws Exception {
        mvc.perform(get("/partyMember//1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindPartyMemberWithMissingUserId() throws Exception {
        mvc.perform(get("/partyMember/1/"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testFindAllPartyMembers() throws Exception {

        when(partyMemberService.findAll()).thenReturn(Arrays.asList(partyMember1, partyMember2));

        mvc.perform(get("/partyMembers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id.partyId").value(1))
                .andExpect(jsonPath("$[0].id.userId").value(0))
                .andExpect(jsonPath("$[1].id.partyId").value(2))
                .andExpect(jsonPath("$[1].id.userId").value(0));

        verify(partyMemberService, times(1)).findAll();
    }
    

    @Test
    void testFindPartyMembersByUserId() throws Exception {
        when(partyMemberService.findByUser(0)).thenReturn(Arrays.asList(partyMember1, partyMember2));

        mvc.perform(get("/partyMembers/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id.partyId").value(1))
                .andExpect(jsonPath("$[1].id.partyId").value(2));

        verify(partyMemberService, times(1)).findByUser(0);
    }

    @Test
    void testUpdatePartyMember() throws Exception {

        when(partyMemberService.save(partyMember1)).thenReturn(partyMember1);

        mvc.perform(put("/partyMember")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partyMember1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id.partyId").value(1))
                .andExpect(jsonPath("$.id.userId").value(0));

        verify(partyMemberService, times(1)).save(partyMember1);
    }

    @Test
    void testDeletePartyMember() throws Exception {
        mvc.perform(delete("/partyMember")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partyMember1)))
                .andExpect(status().isNoContent());

        verify(partyMemberService, times(1)).delete(partyMember1);
    }
}
