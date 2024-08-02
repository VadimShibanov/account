package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetails;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.service.AccountDetailsService;
import com.bank.account.utils.JsonUtils;
import com.bank.account.validator.AccountDetailsValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер банковских счетов, реализующий REST API
 */
@Tag(name = "Account", description = "Accounts API")
@RestController
@RequestMapping("/accounts")
public class AccountDetailsRestController {

    private final AccountDetailsService accountDetailsService;
    private final AccountDetailsMapper accountDetailsMapper;
    private final AccountDetailsValidator accountDetailsValidator;

    public AccountDetailsRestController(AccountDetailsService accountDetailsService, AccountDetailsMapper accountDetailsMapper, AccountDetailsValidator accountDetailsValidator) {
        this.accountDetailsService = accountDetailsService;
        this.accountDetailsMapper = accountDetailsMapper;
        this.accountDetailsValidator = accountDetailsValidator;
    }

    @Operation(summary = "Get all accounts")
    @GetMapping
    public List<AccountDetailsDto> getAll() {
        return accountDetailsMapper.toAccountDtoList(accountDetailsService.getAll());
    }

    @Operation(summary = "Get account by id")
    @GetMapping("/{id}")
    public AccountDetailsDto get(@PathVariable Long id) {
        return accountDetailsMapper.toAccountDto(accountDetailsService.getById(id));
    }

    @Operation(summary = "Create account")
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody AccountDetailsDto accountDto, BindingResult bindingResult) {
        accountDetailsValidator.validate(accountDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(JsonUtils.errorsToJson(bindingResult), HttpStatus.BAD_REQUEST);
        }

        AccountDetails account = accountDetailsService.create(accountDetailsMapper.toAccount(accountDto));
        return new ResponseEntity<>(accountDetailsMapper.toAccountDto(account), HttpStatus.CREATED);
    }

    @Operation(summary = "Update account by id")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,
                                         @Valid @RequestBody AccountDetailsDto accountDto, BindingResult bindingResult) {
        accountDetailsValidator.validate(accountDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(JsonUtils.errorsToJson(bindingResult), HttpStatus.BAD_REQUEST);
        }

        AccountDetails account = accountDetailsService.update(id, accountDetailsMapper.toAccount(accountDto));
        return new ResponseEntity<>(accountDetailsMapper.toAccountDto(account), HttpStatus.OK);
    }

    @Operation(summary = "Delete account by id")
    @DeleteMapping("/{id}")
    public AccountDetailsDto delete(@PathVariable Long id) {
        return accountDetailsMapper.toAccountDto(accountDetailsService.delete(id));
    }
}
