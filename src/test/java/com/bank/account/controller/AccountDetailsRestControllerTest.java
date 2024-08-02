package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetails;
import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.service.AccountDetailsService;
import com.bank.account.utils.JsonUtils;
import com.bank.account.validator.AccountDetailsValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountDetailsRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountDetailsService accountDetailsService;
    @Autowired
    AccountDetailsMapper accountDetailsMapper;
    @MockBean
    AccountDetailsValidator accountDetailsValidator;

    @Test
    void getAllAccounts() throws Exception {
        when(accountDetailsService.getAll()).thenReturn(List.of(
                new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.ONE, false, 1L),
                new AccountDetails(2L, 2L, 2L, 2L, BigDecimal.valueOf(-2), true, 2L),
                new AccountDetails(3L, 3L, 3L, 3L, BigDecimal.valueOf(3), false, 3L)
        ));

        mockMvc.perform(get("/accounts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].money", containsInAnyOrder(1, -2, 3)));
    }

    @Test
    void getAccount() throws Exception {
        AccountDetails account = new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.ONE, false, 1L);
        when(accountDetailsService.getById(1L)).thenReturn(account);

        mockMvc.perform(get("/accounts/{id}", account.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.money", is(1)));
    }

    @Test
    void getAccountNotFound() throws Exception {
        when(accountDetailsService.getById(any())).thenThrow(AccountNotFoundException.class);

        mockMvc.perform(get("/account/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAccount() throws Exception {
        AccountDetailsDto account = new AccountDetailsDto(null, 1L, 1L, 1L, BigDecimal.ONE, 1L);
        when(accountDetailsService.create(any())).then(invocation -> {
            AccountDetails acc = invocation.getArgument(0);
            acc.setId(1L);
            return acc;
        });

        mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(account)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.money", is(1)));
    }

    @Test
    void createAccountBadRequest() throws Exception {
        AccountDetailsDto account = new AccountDetailsDto(null, null, null, null, null, null);

        mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(account)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAccount() throws Exception {
        AccountDetailsDto account = new AccountDetailsDto(1L, 1L, 1L, 1L, BigDecimal.ONE, 1L);
        when(accountDetailsService.update(any(), any())).then(inv -> inv.getArgument(1));

        mockMvc.perform(put("/accounts/{id}", account.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.money", is(1)));
    }

    @Test
    void updateAccountBadRequest() throws Exception {
        AccountDetailsDto account = new AccountDetailsDto(null, null, null, null, null, null);

        mockMvc.perform(put("/accounts/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(account)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteAccount() throws Exception {
        AccountDetails account = new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.ONE, false, 1L);
        when(accountDetailsService.delete(1L)).thenReturn(account);

        mockMvc.perform(delete("/accounts/{id}", account.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.money", is(1)));
    }
}