package com.mysticalducks.rest.finance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysticalducks.rest.finance.model.FinanceInformation;
import com.mysticalducks.rest.finance.service.FinanceInformationService;

@WebMvcTest(value = FinanceInformationController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
public class FinanceInformationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private FinanceInformationService financeService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void findFinanceInformation() throws Exception {
        FinanceInformation financeInformation = new FinanceInformation(100.0);
        when(financeService.findById(1)).thenReturn(financeInformation);

        mvc.perform(get("/financeInformation/1").secure(false))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("budget").value(100.0));
    }

    @Test
    public void newFinanceInformation() throws Exception {
        FinanceInformation financeInformation = new FinanceInformation(100.0);
        when(financeService.save(any(FinanceInformation.class))).thenReturn(financeInformation);

        mvc.perform(post("/financeInformation")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(financeInformation))
                .secure(false))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("budget").value(100.0));
    }

    @Test
    public void newFinanceInformationWithBudget() throws Exception {
        FinanceInformation financeInformation = new FinanceInformation(50.0);
        when(financeService.save(any(Double.class))).thenReturn(financeInformation);

        mvc.perform(post("/financeInformation/budget")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("50.0")
                .secure(false))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("budget").value(50.0));
    }

    @Test
    public void deleteFinanceInformation() throws Exception {
        mvc.perform(delete("/financeInformation/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .secure(false))
            .andExpect(status().isNoContent());
    }
}
