package com.bank.account.controller;

import com.bank.account.entity.Audit;
import com.bank.account.mapper.AuditMapper;
import com.bank.account.service.AuditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuditRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuditService auditService;
    @Autowired
    AuditMapper auditMapper;

    @Test
    void getAllAudits() throws Exception {
        when(auditService.getAll()).thenReturn(List.of(
                new Audit(1L, "audit", "test", "", null, ZonedDateTime.now(), null, "1",""),
                new Audit(2L, "audit", "test", "", null, ZonedDateTime.now(), null, "2","")
        ));

        mockMvc.perform(get("/audit")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)));
    }
}