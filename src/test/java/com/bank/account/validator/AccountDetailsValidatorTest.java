package com.bank.account.validator;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetails;
import com.bank.account.service.AccountDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountDetailsValidatorTest {

    @Mock
    AccountDetailsService accountDetailsService;
    @InjectMocks
    AccountDetailsValidator accountDetailsValidator;

    @Test
    void supports() {
        assertTrue(accountDetailsValidator.supports(AccountDetailsDto.class));
    }

    @Test
    void validateSuccessfully() {
        when(accountDetailsService.getByAccountNumber(1L)).thenReturn(null);
        AccountDetailsDto account = new AccountDetailsDto(1L, 1L, 1L, 1L, BigDecimal.ONE, 1L);
        Errors errors = new BeanPropertyBindingResult(account, "account");

        accountDetailsValidator.validate(account, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void validateUnsuccessfully() {
        when(accountDetailsService.getByAccountNumber(1L)).thenReturn(new AccountDetails());
        AccountDetailsDto account = new AccountDetailsDto(1L, 1L, 1L, 1L, BigDecimal.ONE, 1L);
        Errors errors = new BeanPropertyBindingResult(account, "account");

        accountDetailsValidator.validate(account, errors);

        assertTrue(errors.hasErrors());
    }
}