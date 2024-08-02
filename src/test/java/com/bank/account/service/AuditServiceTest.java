package com.bank.account.service;

import com.bank.account.entity.AccountDetails;
import com.bank.account.entity.Audit;
import com.bank.account.repository.AuditRepository;
import com.bank.account.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    AuditRepository auditRepository;
    @InjectMocks
    AuditService auditService;

    @Test
    void getAll() {
        List<Audit> expectedAudits = List.of(
                new Audit(1L, "audit", "test", "", null, ZonedDateTime.now(), null, "1",""),
                new Audit(2L, "audit", "test", "", null, ZonedDateTime.now(), null, "2","")
        );
        when(auditRepository.findAll()).thenReturn(expectedAudits);

        List<Audit> actualAudits = auditService.getAll();

        assertEquals(expectedAudits, actualAudits);
    }

    @Test
    void auditCreation() {
        when(auditRepository.save(any())).then(invocation -> invocation.getArgument(0));
        AccountDetails accountDetails = new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.ONE, false, 1L);
        Audit expectedAudit = new Audit(null, "AccountDetails", "CREATE", "1", null, null, null, null, JsonUtils.toJson(accountDetails));

        Audit actualAudit = auditService.auditCreation(accountDetails);

        assertThat(actualAudit)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(expectedAudit);
    }

    @Test
    void auditUpdate() {
        when(auditRepository.save(any())).then(invocation -> invocation.getArgument(0));
        AccountDetails updatedAccount = new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.TEN, false, 1L);
        AccountDetails accountDetails = new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.ONE, false, 1L);
        Audit expectedAudit = new Audit(null, "AccountDetails", "UPDATE", "1", null, null, null, JsonUtils.toJson(updatedAccount), JsonUtils.toJson(accountDetails));

        Audit actualAudit = auditService.auditUpdate(updatedAccount, accountDetails);

        assertThat(actualAudit)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(expectedAudit);
    }

    @Test
    void auditDelete() {
        when(auditRepository.save(any())).then(invocation -> invocation.getArgument(0));
        AccountDetails accountDetails = new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.ONE, false, 1L);
        Audit expectedAudit = new Audit(null, "AccountDetails", "DELETE", "1", null, null, null, null, JsonUtils.toJson(accountDetails));

        Audit actualAudit = auditService.auditDelete(accountDetails);

        assertThat(actualAudit)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(expectedAudit);
    }
}