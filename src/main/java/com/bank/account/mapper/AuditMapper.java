package com.bank.account.mapper;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.Audit;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Маппер для преобразования {@link Audit} в {@link AuditDto} и обратно
 */
@Mapper(componentModel = "spring")
public interface AuditMapper {

    List<AuditDto> toAuditDto(List<Audit> audits);
}
