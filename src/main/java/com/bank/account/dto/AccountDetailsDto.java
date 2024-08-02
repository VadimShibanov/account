package com.bank.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Класс для передачи и принятия данных о банковском счёте
 * @see com.bank.account.entity.AccountDetails
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsDto {
    private Long id;
    @NotNull(message = "Passport id must not be null")
    private Long passportId;
    @NotNull(message = "Account number must not be null")
    private Long accountNumber;
    @NotNull(message = "Bank details id must not be null")
    private Long bankDetailsId;
    @NotNull(message = "Money amount must not be null")
    private BigDecimal money;
    @NotNull(message = "Profile id must not be null")
    private Long profileId;
}
