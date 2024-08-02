package com.bank.account.validator;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.service.AccountDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountDetailsValidator implements Validator {

    private final AccountDetailsService accountDetailsService;

    public AccountDetailsValidator(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDetailsDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDetailsDto account = (AccountDetailsDto) target;

        if (account.getAccountNumber() != null && accountDetailsService.getByAccountNumber(account.getAccountNumber()) != null) {
            errors.rejectValue("accountNumber", "", "Account number is already used");
        }
    }
}
