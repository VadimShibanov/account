package com.bank.account.mapper;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Маппер для преобразования {@link AccountDetails} в {@link AccountDetailsDto} и обратно
 */
@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {

    @Mapping(target = "negativeBalance", expression = "java(accountDTO.getMoney().compareTo(java.math.BigDecimal.ZERO) < 0)")
    AccountDetails toAccount(AccountDetailsDto accountDTO);
    AccountDetailsDto toAccountDto(AccountDetails account);

    List<AccountDetailsDto> toAccountDtoList(List<AccountDetails> accounts);
}
