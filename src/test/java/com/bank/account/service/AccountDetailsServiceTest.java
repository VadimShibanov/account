package com.bank.account.service;

import com.bank.account.entity.AccountDetails;
import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.repository.AccountDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AccountDetailsServiceTest {

    @Mock
    AccountDetailsRepository accountDetailsRepository;
    @Mock
    AuditService auditService;
    @InjectMocks
    AccountDetailsService accountDetailsService;

    @Test
    void getAll() {
        List<AccountDetails> expectedAccounts = List.of(
                new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.ONE, false, 1L),
                new AccountDetails(2L, 2L, 2L, 2L, BigDecimal.valueOf(-2), true, 2L)
        );
        when(accountDetailsRepository.findAll()).thenReturn(expectedAccounts);

        List<AccountDetails> actualAccounts = accountDetailsService.getAll();

        assertEquals(expectedAccounts, actualAccounts);
    }

    @Test
    void getById() {
        AccountDetails expectedAccount = new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.ONE, false, 1L);
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.of(expectedAccount));

        AccountDetails actualAccount = accountDetailsService.getById(1L);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void getByIdNotFound() {
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountDetailsService.getById(1L));
    }

    @Test
    void getByAccountNumber() {
        AccountDetails expectedAccount = new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.ONE, false, 1L);
        when(accountDetailsRepository.findByAccountNumber(1L)).thenReturn(expectedAccount);

        AccountDetails actualAccount = accountDetailsService.getByAccountNumber(1L);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void getByAccountNumberNotFound() {
        when(accountDetailsRepository.findByAccountNumber(1L)).thenReturn(null);

        assertNull(accountDetailsService.getByAccountNumber(1L));
    }

    @Test
    void create() {
        Long expectedId = 1L;
        AccountDetails account = new AccountDetails(null, 1L, 1L, 1L, BigDecimal.ONE, false, 1L);
        when(accountDetailsRepository.save(account)).then(invocation -> {
            AccountDetails acc = invocation.getArgument(0);
            acc.setId(expectedId);
            return acc;
        });

        AccountDetails actualAccount = accountDetailsService.create(account);

        assertEquals(expectedId, actualAccount.getId());
    }

    @Test
    void update() {
        AccountDetails expectedAccount = new AccountDetails(1L, 2L, 3L, 4L, BigDecimal.TEN, false, 5L);
        AccountDetails actualAccount = new AccountDetails(1L, null, null, null, null, null, null);
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.of(expectedAccount));

        accountDetailsService.update(1L, actualAccount);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void updateNotFound() {
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountDetailsService.update(1L, new AccountDetails()));
    }

    @Test
    void delete() {
        AccountDetails expectedAccount = new AccountDetails(1L, 1L, 1L, 1L, BigDecimal.ONE, false, 1L);
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.of(expectedAccount));

        AccountDetails actualAccount = accountDetailsService.delete(1L);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void deleteNotFound() {
        when(accountDetailsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountDetailsService.delete(1L));
    }
}