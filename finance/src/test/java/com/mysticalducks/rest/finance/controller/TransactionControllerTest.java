package com.mysticalducks.rest.finance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.mysticalducks.rest.finance.exception.CategoryNotFoundException;
import com.mysticalducks.rest.finance.exception.ChatNotFoundException;
import com.mysticalducks.rest.finance.exception.UserNotFoundException;
import com.mysticalducks.rest.finance.model.Transaction;
import com.mysticalducks.rest.finance.service.TransactionService;

@WebMvcTest(value = TransactionController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ActiveProfiles("test")
public class TransactionControllerTest extends AbstractControllerTest {

	@Autowired
	WebApplicationContext context;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TransactionService transactionService;
	

	@Test
	public void findTransaction() throws Exception {
		given(this.transactionService.findById(0)).willReturn(transaction);
		this.mvc.perform(get("/transaction/0").secure(false)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("id").value(0))
				.andExpect(jsonPath("name").value("transaction"))
				.andExpect(jsonPath("id").value(0))
				.andExpect(jsonPath("amount").value(transaction.getAmount()))
				.andExpect(jsonPath("user.id").value(transaction.getUser().getId()))
				.andExpect(jsonPath("user.id").value(transaction.getChat().getId()))
				.andExpect(jsonPath("category.id").value(transaction.getCategory().getId()))
				.andExpect(jsonPath("note").value(transaction.getNote()));

		this.mvc.perform(get("/transaction/").secure(false)).andExpect(status().isMethodNotAllowed());
	}
	
	
	@Test
	public void newTranscation() throws Exception {
		given(this.transactionService.save(transaction.getName(), transaction.getAmount(), transaction.getNote(), transaction.getCategory().getId(), transaction.getUser().getId(), transaction.getChat().getId())).willReturn(transaction);
		this.mvc.perform(post("/transaction/")
				.queryParam("name", "transaction")
				.queryParam("amount", "250.0")
				.queryParam("note", "note")
				.queryParam("categoryId", "0")
				.queryParam("userId", "0")
				.queryParam("chatId", "0")
				.secure(false)
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("name").value(transaction.getName()))
			    .andExpect(jsonPath("amount").value(transaction.getAmount()))
			    .andExpect(jsonPath("note").value(transaction.getNote()))
			    .andExpect(jsonPath("id").value(transaction.getId()))
			    .andExpect(jsonPath("user.id").value(transaction.getUser().getId()))
				.andExpect(jsonPath("chat.id").value(transaction.getChat().getId()))
				.andExpect(jsonPath("category.id").value(transaction.getCategory().getId()));

		this.mvc.perform(post("/category/").secure(false)).andExpect(status().isNotFound());
	}
	
	@Test
	public void newTransaction_userNotFoundException() throws Exception {
		doThrow(new UserNotFoundException("User not found with id " + -1)).when(transactionService).save(anyString(), anyDouble(), anyString(), anyInt(), anyInt(), anyInt());
		
		this.mvc.perform(post("/transaction/")
				.queryParam("name", "transaction")
				.queryParam("amount", "250.0")
				.queryParam("note", "note")
				.queryParam("categoryId", "0")
				.queryParam("userId", "-1")
				.queryParam("chatId", "0")
				.secure(false)
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isNotFound());

		verify(transactionService, times(1)).save(anyString(), anyDouble(), anyString(), anyInt(), anyInt(), anyInt());
	}
	
	@Test
	public void newTransaction_chatNotFoundException() throws Exception {
		doThrow(new ChatNotFoundException("Chat not found with id " + -1)).when(transactionService).save(anyString(), anyDouble(), anyString(), anyInt(), anyInt(), anyInt());
		
		this.mvc.perform(post("/transaction/")
				.queryParam("name", "transaction")
				.queryParam("amount", "250.0")
				.queryParam("note", "note")
				.queryParam("categoryId", "0")
				.queryParam("userId", "0")
				.queryParam("chatId", "-1")
				.secure(false)
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isNotFound());

		verify(transactionService, times(1)).save(anyString(), anyDouble(), anyString(), anyInt(), anyInt(), anyInt());
	}
	
	@Test
	public void newTransaction_categoryNotFoundException() throws Exception {
		doThrow(new CategoryNotFoundException("Category not found with id " + -1)).when(transactionService).save(anyString(), anyDouble(), anyString(), anyInt(), anyInt(), anyInt());
		
		this.mvc.perform(post("/transaction/")
				.queryParam("name", "transaction")
				.queryParam("amount", "250.0")
				.queryParam("note", "note")
				.queryParam("categoryId", "-1")
				.queryParam("userId", "0")
				.queryParam("chatId", "0")
				.secure(false)
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isNotFound());

		verify(transactionService, times(1)).save(anyString(), anyDouble(), anyString(), anyInt(), anyInt(), anyInt());
	}
	
	@Test
	public void deleteTransaction() throws Exception {
		doNothing().when(transactionService).deleteById(anyInt());
		
		this.mvc.perform(delete("/transaction/0")
		        .contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk());
	
		verify(transactionService, times(1)).deleteById(0);
	}
	
	@Test
	public void replaceCategory() throws Exception {
		when(transactionService.replace(any(Transaction.class))).thenReturn(transaction);
		String categoryJson = "{\"id\":0,\"name\":\"transaction\",\"amount\":250.0,\"note\":\"note\",\"category\":{\"id\":0,\"name\":\"category\",\"user\":{\"id\":0,\"name\":\"Hans\",\"telegramUserId\":0,\"password\":\"password\",\"email\":\"email\",\"language\":0},\"icon\":{\"id\":0,\"name\":\"Icon\"}},\"user\":{\"id\":0,\"name\":\"Hans\",\"telegramUserId\":0,\"password\":\"password\",\"email\":\"email\",\"language\":0},\"chat\":{\"id\":0},\"createdAt\":null}";;
		
		mvc.perform(put("/transaction")
		.content(categoryJson)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
		.json(categoryJson));
		
	    verify(transactionService, times(1)).replace(any(Transaction.class));
	}
	
	@Test
	public void totalAmount() throws Exception {
		Double totalAmountValue = 250.50;
		given(this.transactionService.totalAmount(0,0)).willReturn(totalAmountValue);
		this.mvc.perform(get("/totalAmount/0")
				.queryParam("chatId", "0")
				.secure(false)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				 .andExpect(content().string(Double.toString(totalAmountValue)));
	}
	
	@Test
	public void totalAmountByPeriod() throws Exception {
		Double totalAmountValue = 250.50;
		LocalDateTime startDate = LocalDateTime.of(2023, 12, 01, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 0, 0);
		given(this.transactionService.totalAmountByDate(0,0, startDate, endDate)).willReturn(totalAmountValue);
		this.mvc.perform(get("/totalAmountByPeriod/0" )
                .param("chatId", String.valueOf(0))
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(Double.toString(totalAmountValue)));

		this.mvc.perform(get("/transaction/").secure(false)).andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void totalAmountByCurrentMonth() throws Exception {
		Double totalAmountValue = 250.50;
		given(this.transactionService.totalAmountByCurrentMonth(0,0)).willReturn(totalAmountValue);
		this.mvc.perform(get("/totalAmountByCurrentMonth/0")
				.queryParam("chatId", "0")
				.secure(false)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				 .andExpect(content().string(Double.toString(totalAmountValue)));
	}

}
